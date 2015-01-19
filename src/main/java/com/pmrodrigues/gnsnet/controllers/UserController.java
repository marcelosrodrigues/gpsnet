package com.pmrodrigues.gnsnet.controllers;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.validator.ValidationMessage;
import com.pmrodrigues.gnsnet.annotations.Tiles;
import com.pmrodrigues.gnsnet.exceptions.DuplicateRegisterException;
import com.pmrodrigues.gnsnet.models.Grupo;
import com.pmrodrigues.gnsnet.models.Usuario;
import com.pmrodrigues.gnsnet.repository.ResultList;
import com.pmrodrigues.gnsnet.services.GroupService;
import com.pmrodrigues.gnsnet.services.UserService;
import com.pmrodrigues.gnsnet.utilities.Constante;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static java.lang.String.format;

/**
 * Created by Marceloo on 01/10/2014.
 */
@Resource
public class UserController {

    private final Logger logging = Logger.getLogger(UserController.class);
    private final Result result;
    private final UserService userService;
    private final GroupService groupService;
    private final Validator validator;

    public UserController(final UserService userService, GroupService groupService, final Result result, Validator validator){
        this.userService = userService;
        this.groupService = groupService;
        this.result = result;
        this.validator = validator;
    }

    @Get("/usuario/novo.do")
    @Tiles(definition = "new.usuario.prepare")
    public void prepareNewUser() {
        logging.info("preparando a tela de cadastro de usuário");
        result.include(Constante.ROLE,groupService.list());
        logging.info("tela preparada com sucesso");
    }

    @Post("/usuario/salvar.do")
    public void create(final Usuario usuario, final Grupo[] grupos) {

        try {
            logging.info(format("criando o usuario %s para acesso ao sistema", usuario));

            validator.validate(usuario);

            validator.onErrorForwardTo(UserController.class)
                     .prepareNewUser();
            Collection<Grupo> g = new ArrayList<>();
            if(grupos != null ) {
                g.addAll(Arrays.asList(grupos));

            }

            usuario.associar(g);
            if( usuario.isNovo() ) {
                userService.createUser(usuario);
            } else {
                userService.changeUser(usuario);
            }

            logging.debug(format("Usuario %s salvo com sucesso",usuario));

            result.include(Constante.MESSAGE, "Usuário salvo com sucesso");

            result.forwardTo(UserController.class).index();
        } catch (DuplicateRegisterException e) {
            validator.add(new ValidationMessage(e.getMessage(),""));
            validator.onErrorForwardTo(UserController.class)
                    .prepareNewUser();
        }

    }

    @Post("/usuario/pequisar.do")
    public void search(final Usuario usuario) {

        try {

            logging.info(format("inicio da operação para pesquisar usuario conforme o exemplo %s", usuario));
            ResultList<Usuario> usuarios = userService.search(usuario);

            if( logging.isDebugEnabled() ){
                logging.debug(format("usuarios encontrados %s",usuarios));
            }

            result.include(Constante.RESULT_LIST,usuarios);
            result.forwardTo(UserController.class).index();

        } finally {
            logging.info("método pesquisado com sucesso");
        }

    }

    @Get("/usuario/index.do")
    @Tiles(definition = "new.usuario.list")
    public void index() {
        logging.info("preparando a tela de pesquisa de usuário");
        result.include(Constante.ROLE,groupService.list());
        ResultList<Usuario> usuarios = userService.search(null);
        result.include(Constante.RESULT_LIST,usuarios);
        logging.info("tela preparada com sucesso");
    }

    @Get("/usuario/{id}/abrir.do")
    @Tiles(definition = "new.usuario.prepare")
    public void show(final Long id){

        result.include(Constante.ROLE,groupService.list());
        final Usuario e = this.userService.findById(id);
        result.include("usuario",e);
    }

    @Get("/usuario/pesquisar.do")
    @Tiles(definition = "new.usuario.list")
    public void search(Integer page , final Usuario object ) {

        result.include(Constante.ROLE,groupService.list());

        if( page == null ){
            page = 0;
        }

        final ResultList<Usuario> resultlist = userService.search(object,page);
        result.include(Constante.RESULT_LIST , resultlist);


    }
}
