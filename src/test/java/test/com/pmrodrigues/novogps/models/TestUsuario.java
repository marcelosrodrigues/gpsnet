package test.com.pmrodrigues.novogps.models;

import com.pmrodrigues.gnsnet.models.Grupo;
import com.pmrodrigues.gnsnet.models.Usuario;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by Marceloo on 01/10/2014.
 */
public class TestUsuario {

    @Test
    public void shouldAddRole() throws Exception {

        Collection<Grupo> grupos = new ArrayList<Grupo>();
        Usuario usuario = new Usuario();
        for( Long i = 0L ; i < 10L ; i++ ){
            Grupo grupo = new Grupo();
            Field id = grupo.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(grupo, i);
            grupos.add(grupo);
        }

        usuario.associar(grupos);

        assertEquals(10L, usuario.getGrupos().size());

    }

    @Test
    public void shouldUpdateRoles() throws Exception {

        Collection<Grupo> grupos = new ArrayList<Grupo>();
        Usuario usuario = new Usuario();
        for( Long i = 0L ; i < 10L ; i++ ){
            Grupo grupo = new Grupo();
            Field id = grupo.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(grupo,i);
            grupos.add(grupo);
        }

        usuario.associar(grupos);

        grupos = new ArrayList<>();

        for( Long i = 0L ; i < 8L ; i++ ){
            Grupo grupo = new Grupo();
            Field id = grupo.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(grupo,i);
            grupos.add(grupo);
        }

        usuario.associar(grupos);

        assertEquals(8L, usuario.getGrupos().size());
    }
}
