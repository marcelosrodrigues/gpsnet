package com.pmrodrigues.gnsnet.controllers.crud;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import com.pmrodrigues.gnsnet.annotations.After;
import com.pmrodrigues.gnsnet.annotations.Before;
import com.pmrodrigues.gnsnet.annotations.Insert;
import com.pmrodrigues.gnsnet.annotations.Update;
import com.pmrodrigues.gnsnet.exceptions.UniqueException;
import com.pmrodrigues.gnsnet.repository.Repository;
import com.pmrodrigues.gnsnet.repository.ResultList;
import com.pmrodrigues.gnsnet.utilities.Constante;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by Marceloo on 08/12/2014.
 */
public abstract class AbstractCRUDController<E> {

    private static final Logger logging = Logger.getLogger(AbstractCRUDController.class);

    private final Repository<E> repository;
    private final Result result;
    private final Validator validator;
    private final Class<E> persistentClass; //NOPMD

    protected AbstractCRUDController(final Repository<E> repository, final Result result, final Validator validator) {
        this.repository = repository;
        this.result = result;
        this.validator = validator;
        final ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        this.persistentClass = (Class<E>) type.getActualTypeArguments()[0];
    }

    @Post
    public void salvar(final E object){

        validator.validate(object);
        final Long idValue = getId(object);
        validator.onErrorForwardTo(this.getClass()).formulario();

        logging.debug(format("iniciando a operação de inclusão de %s",object));

        try {
            if( idValue == null || idValue == 0L ) {

                if( !doInsert(object) ) {
                    repository.add(object);
                    postExecute(object);
                }
                result.include(Constante.SUCESSO,format("%s adicionada com sucesso",persistentClass.getSimpleName()));
            } else {
                if( !doUpdate(object) ) {
                    repository.set(object);
                }
                result.include(Constante.SUCESSO, format("%s alterada com sucesso", persistentClass.getSimpleName()));
            }

            result.forwardTo(this.getClass()).index();
        } catch (UniqueException ex) {
            validator.add(new ValidationMessage(ex.getMessage(),ex.getMessage()));
            validator.onErrorForwardTo(this.getClass()).formulario();
        }
    }

    private boolean doUpdate(final E object) {
        try {
            return invoke(Update.class,object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean doInsert(final E object) {
        try {
           return invoke(Insert.class,object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    private Method getByAnnotation(Class annotation) {

        final List<Method> metodos = Arrays.asList(this.getClass().getMethods());
        for( final Method metodo : metodos ) {
            if (metodo.isAnnotationPresent(annotation)) {
                logging.debug(format("Delegado a execução para o método da classe filha %s", metodo));
                return metodo;
            }
        }
        return null;

    }

    private void postExecute(final E object) {
        try {

            invoke(After.class,object);

        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Long getId(final E object)  {
        try {
            Field id = object.getClass().getDeclaredField("id");
            id.setAccessible(true);
            return (Long) id.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Get
    public void index() {
        preExecute();
        final ResultList<E> resultlist = repository.search(null);
        result.include(Constante.RESULT_LIST , resultlist);
    }

    private void preExecute() {

        try {
            invoke(Before.class);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean invoke(Class annotation,Object... params) throws IllegalAccessException, InvocationTargetException {
        final Method metodo = this.getByAnnotation(annotation);
        if( metodo != null ) {
            metodo.invoke(this,params);
            return true;
        }
        return false;
    }

    public void search(Integer page , final E object ) {

        preExecute();

        if( page == null ){
            page = 0;
        }

        final ResultList<E> resultlist = repository.search(object,page);
        result.include(Constante.RESULT_LIST , resultlist);


    }


    @Get
    public void formulario() {
        try {
            preExecute();
            final E e = this.persistentClass.newInstance();

            List<Field> fields = Arrays.asList(e.getClass().getDeclaredFields());
            for( Field field : fields ){
                if( field.getType().isAnnotationPresent(Entity.class) ){
                    field.setAccessible(true);
                    Object value = field.getType().newInstance();
                    field.set(e,value);
                }
            }

            result.include(Constante.OBJECT,e);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Get
    public void show(final Long id){
        preExecute();
        final E e = this.repository.findById(id);
        result.include(Constante.OBJECT,e);
    }

    public Result getResult() {
        return result;
    }

    public Repository<E> getRepository() {
        return repository;
    }

    public Validator getValidator() {
        return validator;
    }
}
