package com.pmrodrigues.gnsnet.resolvers.crud;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.http.route.RoutesConfiguration;
import br.com.caelum.vraptor.http.route.Rules;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.HttpMethod;
import com.pmrodrigues.gnsnet.controllers.crud.AbstractCRUDController;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

import static java.lang.String.format;

/**
 * Created by Marceloo on 09/12/2014.
 */
@Component
@ApplicationScoped
public class CRUDRoutes implements RoutesConfiguration {

    private static final Logger logging = Logger.getLogger(CRUDRoutes.class);

    @Override
    public void config(final Router router) {

        final Reflections reflections = new Reflections("com.pmrodrigues");
        final Set<Class<? extends AbstractCRUDController>> classes = reflections.getSubTypesOf(AbstractCRUDController.class);

        new Rules(router) {
            @Override
            public void routes() {
              for(Class<? extends AbstractCRUDController> clazz : classes ){

                      final ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
                      Class persistedType = (Class) type.getActualTypeArguments()[0];

                      final String entityName = persistedType.getSimpleName().toLowerCase();

                      for(Method metodo : clazz.getMethods() ){

                          switch (metodo.getName()){
                              case "index":
                                  logging.debug(format("criando a chamada para url /%s/index.do executando a classe %s metodo %s",entityName,clazz,metodo));
                                  routeFor(format("/%s/index.do", entityName)).is(clazz,metodo);
                                  break;
                              case "formulario":
                                  logging.debug(format("criando a chamada para url /%s/novo.do executando a classe %s metodo %s",entityName,clazz,metodo));
                                  routeFor(format("/%s/novo.do", entityName)).with(HttpMethod.GET).is(clazz, metodo);
                                  break;
                              case "salvar":
                                  logging.debug(format("criando a chamada para url /%s/salvar.do executando a classe %s metodo %s", entityName, clazz, metodo));
                                  routeFor(format("/%s/salvar.do", entityName)).with(HttpMethod.POST).is(clazz, metodo);
                                  break;
                              case "show":
                                  logging.debug(format("criando a chamada para url /%s/abrir.do executando a classe %s metodo %s",entityName,clazz,metodo));
                                  routeFor(format("/%s/{id}/abrir.do",entityName)).with(HttpMethod.GET).is(clazz, metodo);
                                  break;
                              case "search":
                                  logging.debug(format("criando a chamada para url /%s/pesquisar.do executando a classe %s metodo %s",entityName,clazz,metodo));
                                  routeFor(format("/%s/pesquisar.do",entityName)).is(clazz, metodo);
                                  break;
                              default :
                                  logging.debug("criando url default para os métodos que anotados");

                                  HttpMethod httpMethod = HttpMethod.GET;
                                  if( metodo.isAnnotationPresent(Post.class) ){
                                      httpMethod = HttpMethod.POST;
                                  }
                                  if( metodo.isAnnotationPresent(Path.class) ){
                                      Path path = metodo.getAnnotation(Path.class);
                                      routeFor(path.value()[0]).with(httpMethod).is(clazz, metodo);
                                  } else if (metodo.isAnnotationPresent(Post.class) || metodo.isAnnotationPresent(Get.class) ){
                                      routeFor(format("/%s/%s.do",entityName,metodo.getName())).with(httpMethod).is(clazz, metodo);
                                  }
                                  break;
                          }

                      }

              }
            }
        };
    }
}
