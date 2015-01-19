package test.com.pmrodrigues.novogps.controllers;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;
import com.pmrodrigues.gnsnet.controllers.UserController;
import com.pmrodrigues.gnsnet.exceptions.DuplicateRegisterException;
import com.pmrodrigues.gnsnet.models.Grupo;
import com.pmrodrigues.gnsnet.models.Usuario;
import com.pmrodrigues.gnsnet.repository.ResultList;
import com.pmrodrigues.gnsnet.services.GroupService;
import com.pmrodrigues.gnsnet.services.UserService;
import com.pmrodrigues.gnsnet.utilities.Constante;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import test.com.pmrodrigues.novogps.mock.ValidatorMocked;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Marceloo on 01/10/2014.
 */
public class TestUserController {

    private Mockery context = new Mockery(){{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    private MockResult result = new MockResult();
    private MockValidator validator = new ValidatorMocked();
    private UserService service = context.mock(UserService.class);
    private GroupService groupService = context.mock(GroupService.class);
    private UserController controller = new UserController(service,groupService,result,validator);


    @Test
    public void shouldAddRoleList() {

        final Collection<Grupo> grupos = context.mock(Collection.class);

        context.checking(new Expectations(){{
            oneOf(groupService).list();
            will(returnValue(grupos));
        }});

        controller.prepareNewUser();

        assertNotNull(result.included(Constante.ROLE));

    }

    @Test
    public void shouldAddUser() throws Exception {

        context.checking(new Expectations(){{
            oneOf(service).createUser(with(aNonNull(Usuario.class)));
        }});

        final Usuario usuario = new Usuario();
        usuario.setNome("TESTE");
        usuario.setEmail("marcelosrodrigues@globo.com");

        controller.create(usuario, new Grupo[0]);

        assertNotNull(result.included(Constante.MESSAGE));

    }

    @Test(expected = ValidationException.class)
    public void shouldntAddUser() throws Exception {

        context.checking(new Expectations(){{
            oneOf(service).createUser(with(aNonNull(Usuario.class)));
            will(throwException(new DuplicateRegisterException("TESTE")));
        }});

        final Usuario usuario = new Usuario();
        usuario.setNome("TESTE");
        usuario.setEmail("marcelosrodrigues@globo.com");

        controller.create(usuario, new Grupo[0]);

        assertTrue(validator.hasErrors());

    }

    @Test
    public void shouldList() {

        final ResultList<Usuario> usuarios = context.mock(ResultList.class);

        context.checking(new Expectations(){{
            oneOf(groupService).list();
            will(returnValue(new ArrayList()));
            oneOf(service).search(with(aNonNull(Usuario.class)));
            will(returnValue(usuarios));
        }});

        controller.search(new Usuario());

        assertNotNull(result.included(Constante.RESULT_LIST));

    }

    @Test
    public void shouldPrepareSearchPage() {

        final Collection<Grupo> grupos = context.mock(Collection.class);
        final ResultList<Usuario> usuarios = context.mock(ResultList.class);

        context.checking(new Expectations(){{
            oneOf(groupService).list();
            will(returnValue(grupos));

            oneOf(service).search(null);
            will(returnValue(usuarios));
        }});

        controller.index();

        assertNotNull(result.included(Constante.ROLE));

    }

}
