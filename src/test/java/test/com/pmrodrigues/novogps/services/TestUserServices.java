package test.com.pmrodrigues.novogps.services;

import com.pmrodrigues.gnsnet.exceptions.DuplicateRegisterException;
import com.pmrodrigues.gnsnet.exceptions.UserIsBlockedException;
import com.pmrodrigues.gnsnet.models.Empresa;
import com.pmrodrigues.gnsnet.models.Usuario;
import com.pmrodrigues.gnsnet.repository.ResultList;
import com.pmrodrigues.gnsnet.repository.UserRepository;
import com.pmrodrigues.gnsnet.services.EmailService;
import com.pmrodrigues.gnsnet.services.UserService;
import com.pmrodrigues.gnsnet.utilities.MD5;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Marceloo on 27/09/2014.
 */
public class TestUserServices {

    private Mockery context = new Mockery() {{
       setImposteriser(ClassImposteriser.INSTANCE);
    }};

    private UserRepository repository;

    private UserService service;

    @Before
    public void setup() {

        repository = context.mock(UserRepository.class);
        service = new UserService(repository);

    }

    @Test
    public void shouldAutenticate() throws Exception {

        final Usuario usuario = context.mock(Usuario.class);
        final Empresa empresa = context.mock(Empresa.class);

        context.checking( new Expectations() {{
            oneOf(repository).findByEmail(with(aNonNull(String.class)));
            will(returnValue(usuario));

            oneOf(usuario).getPassword();
            will(returnValue(MD5.encrypt("123456")));

            oneOf(usuario).getEmpresa();
            will(returnValue(empresa));

            oneOf(empresa).isBloqueado();
            will(returnValue(Boolean.FALSE));

            oneOf(usuario).isBloqueado();
            will(returnValue(Boolean.FALSE));
        }});

        final Usuario authenticaded = service.doAuthentication("marcelosrodrigues@globo.com", "123456");
        assertNotNull(authenticaded);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldntAutenticate() throws Exception {

        context.checking( new Expectations() {{

            oneOf(repository).findByEmail(with(aNonNull(String.class)));
            will(returnValue(null));
        }});

        service.doAuthentication("marcelosrodrigues@globo.com", "123456");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldntAutenticatedBecauseThePasswordIsWrong() throws Exception {

        final Usuario usuario = context.mock(Usuario.class);
        final Empresa empresa = context.mock(Empresa.class);

        context.checking( new Expectations() {{
            oneOf(repository).findByEmail(with(aNonNull(String.class)));
            will(returnValue(usuario));

            oneOf(usuario).getEmpresa();
            will(returnValue(empresa));

            oneOf(empresa).isBloqueado();
            will(returnValue(Boolean.FALSE));

            oneOf(usuario).getPassword();
            will(returnValue(MD5.encrypt("123456")));

            oneOf(usuario).incrementarTentativasInvalidas();

            oneOf(usuario).isBloqueado();
            will(returnValue(Boolean.FALSE));
        }});

        service.doAuthentication("marcelosrodrigues@globo.com", "abc");
    }

    @Test(expected = UserIsBlockedException.class)
    public void shouldntAutenticatedBecauseTheCompanyIsBlocked() throws Exception {

        final Usuario usuario = context.mock(Usuario.class);
        final Empresa empresa = context.mock(Empresa.class);

        context.checking( new Expectations() {{
            oneOf(repository).findByEmail(with(aNonNull(String.class)));
            will(returnValue(usuario));

            oneOf(usuario).getPassword();
            will(returnValue(MD5.encrypt("123456")));

            oneOf(usuario).getEmpresa();
            will(returnValue(empresa));

            oneOf(empresa).isBloqueado();
            will(returnValue(Boolean.TRUE));
        }});

        service.doAuthentication("marcelosrodrigues@globo.com", "123456");

    }

    @Test(expected = UserIsBlockedException.class)
    public void shouldntAutenticateUserBlocked() throws Exception {

        final Usuario usuario = context.mock(Usuario.class);
        final Empresa empresa = context.mock(Empresa.class);

        context.checking( new Expectations() {{
            oneOf(repository).findByEmail(with(aNonNull(String.class)));
            will(returnValue(usuario));

            oneOf(usuario).getPassword();
            will(returnValue(MD5.encrypt("123456")));

            oneOf(usuario).getEmpresa();
            will(returnValue(empresa));

            oneOf(empresa).isBloqueado();
            will(returnValue(Boolean.FALSE));

            oneOf(usuario).isBloqueado();
            will(returnValue(Boolean.TRUE));
        }});

        service.doAuthentication("marcelosrodrigues@globo.com", "123456");

    }

    @Test(expected = UserIsBlockedException.class)
    public void shouldBlockUserIfExceedChances() throws Exception {

        final Usuario usuario = new Usuario();
        final Empresa empresa = new Empresa();

        Field bloqueado = empresa.getClass().getDeclaredField("bloqueado");
        bloqueado.setAccessible(true);
        bloqueado.setBoolean(empresa,false);

        usuario.setEmpresa(empresa);

        Field tentativas = usuario.getClass().getDeclaredField("tentativas");
        tentativas.setAccessible(true);
        tentativas.setLong(usuario,4L);

        Field senha = usuario.getClass().getDeclaredField("password");
        senha.setAccessible(true);
        senha.set(usuario,MD5.encrypt("123456"));

        bloqueado = usuario.getClass().getDeclaredField("bloqueado");
        bloqueado.setAccessible(true);
        bloqueado.setBoolean(usuario,false);

        context.checking( new Expectations() {{

            oneOf(repository).findByEmail(with(aNonNull(String.class)));
            will(returnValue(usuario));
        }});

        service.doAuthentication("marcelosrodrigues@globo.com", "abc");

    }

    @Test
    public void shouldSave() throws Exception {

        final Usuario usuario = new Usuario();
        usuario.setEmail("");
        final EmailService email = context.mock(EmailService.class);
        Field field = service.getClass().getDeclaredField("email");
        field.setAccessible(true);
        field.set(service,email);

        context.checking( new Expectations() {{
            oneOf(repository).findByEmail(with(aNonNull(String.class)));
            will(returnValue(null));

            oneOf(repository).add(with(aNonNull(Usuario.class)));

            oneOf(email).to(with(aNonNull(String.class)));
            will(returnValue(email));

            oneOf(email).subject(with(aNonNull(String.class)));
            will(returnValue(email));

            oneOf(email).template(with(aNonNull(String.class)),with(aNonNull(Map.class)));
            will(returnValue(email));

            oneOf(email).send();
        }});

        service.createUser(usuario);
    }

    @Test(expected = DuplicateRegisterException.class)
    public void shouldntSaveIfExistOtherWithSameEmail() throws Exception {
        final Usuario usuario = new Usuario();
        usuario.setEmail("");

        context.checking( new Expectations() {{

            oneOf(repository).findByEmail(with(aNonNull(String.class)));
            will(returnValue(new Usuario()));

        }});

        service.createUser(usuario);
    }

    @Test
    public void shouldSearchUser() {

        final Usuario usuario = new Usuario();
        final ResultList<Usuario> usuarios = context.mock(ResultList.class);

        context.checking(new Expectations() {{
            oneOf(repository).search(usuario,0);
            will(returnValue(usuarios));
        }});

        assertNotNull(service.search(usuario));
    }
}