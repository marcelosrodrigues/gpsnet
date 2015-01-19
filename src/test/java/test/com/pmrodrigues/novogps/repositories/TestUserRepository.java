package test.com.pmrodrigues.novogps.repositories;

import com.pmrodrigues.gnsnet.models.Usuario;
import com.pmrodrigues.gnsnet.repository.ResultList;
import com.pmrodrigues.gnsnet.repository.UserRepository;
import com.pmrodrigues.gnsnet.utilities.Constante;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Marceloo on 29/09/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager" , defaultRollback = true)
public class TestUserRepository extends AbstractTransactionalJUnit4SpringContextTests{

    @Resource(name = "UserRepository")
    private UserRepository repository;

    @Before
    public void before() {
        jdbcTemplate.update("delete from usuario where email like 'marsilvarodrigues%'");
    }


    @Test
    @Transactional
    public void shouldGeneratePassword() {

        Usuario usuario = new Usuario();
        usuario.setNome("TESTE");
        usuario.setEmail("marsilvarodrigues@gmail.com");
        repository.add(usuario);

        assertNotNull(usuario.getPassword());

    }

    @Test
    @Transactional
    public void shouldListUser() {

        for( int i = 0 ; i < 50 ; i ++ ){
            final Usuario usuario = new Usuario();
            usuario.setNome(format("TESTE%s",i));
            usuario.setEmail(format("marsilvarodrigues_%s@gmail.com",i));
            repository.add(usuario);
        }

        Long recordCount = jdbcTemplate.queryForObject("select count(*) from usuario",Long.class);
        Long pageCount = recordCount / Constante.TAMANHO_PAGINA;
        if( recordCount % Constante.TAMANHO_PAGINA  > 0 ){
            pageCount +=1 ;
        }
        ResultList<Usuario> usuarios = repository.search(null);
        assertEquals(pageCount,usuarios.getPageCount());
        assertEquals(recordCount ,usuarios.getRecordCount());

        final Usuario usuario = new Usuario();
        usuario.setNome("TESTE");
        usuarios = repository.search(usuario);

        recordCount = jdbcTemplate.queryForObject("select count(*) from usuario where nome like 'TESTE%'",Long.class);
        assertEquals(50L,usuarios.getRecordCount().longValue());

    }

}
