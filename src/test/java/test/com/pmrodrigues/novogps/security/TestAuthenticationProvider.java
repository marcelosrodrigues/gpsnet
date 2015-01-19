package test.com.pmrodrigues.novogps.security;

import com.pmrodrigues.gnsnet.exceptions.UserIsBlockedException;
import com.pmrodrigues.gnsnet.models.Usuario;
import com.pmrodrigues.gnsnet.security.AuthenticationProvider;
import com.pmrodrigues.gnsnet.services.UserService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.Field;

/**
 * Created by Marceloo on 27/09/2014.
 */
public class TestAuthenticationProvider {

    private Mockery context = new Mockery() {{
       setImposteriser(ClassImposteriser.INSTANCE);
    }};

    private UserService service;
    private AuthenticationProvider provider = new AuthenticationProvider();

    @Before
    public void setup() throws Exception {

        service = context.mock(UserService.class);
        Field service = provider.getClass().getDeclaredField("service");
        service.setAccessible(true);
        service.set(provider,this.service);


    }

    @Test
    public void autenticadoComSucesso() {


        final Usuario usuario = new Usuario();

        context.checking(new Expectations(){{
            oneOf(service).doAuthentication(with(aNonNull(String.class)),with(aNonNull(String.class)));
            will(returnValue(usuario));
        }});

        Authentication authentication = provider.authenticate(new UsernamePasswordAuthenticationToken("marcelosrodrigues@globo.com","12345"));

    }

    @Test(expected = UsernameNotFoundException.class)
    public void usuarioNaoEncontrado() {
        context.checking(new Expectations(){{
            oneOf(service).doAuthentication(with(aNonNull(String.class)),with(aNonNull(String.class)));
            will(throwException(new UsernameNotFoundException("")));
        }});

        provider.authenticate(new UsernamePasswordAuthenticationToken("marcelosrodrigues@globo.com","12345"));
    }

    @Test(expected = AuthenticationException.class)
    public void usuarioBloqueado() {

        context.checking(new Expectations(){{
            oneOf(service).doAuthentication(with(aNonNull(String.class)),with(aNonNull(String.class)));
            will(throwException(new UserIsBlockedException("")));
        }});

        provider.authenticate(new UsernamePasswordAuthenticationToken("marcelosrodrigues@globo.com","12345"));

    }
}
