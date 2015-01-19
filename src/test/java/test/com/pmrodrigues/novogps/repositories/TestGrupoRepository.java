package test.com.pmrodrigues.novogps.repositories;

import com.pmrodrigues.gnsnet.models.Grupo;
import com.pmrodrigues.gnsnet.repository.GroupRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.Collection;

import static java.lang.String.format;

/**
 * Created by Marceloo on 01/10/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager" , defaultRollback = true)
public class TestGrupoRepository {

    @Resource(name = "GroupRepository")
    private GroupRepository repository;

    @Test
    @Transactional
    public void shouldList() {

        for( int i = 0 ; i < 10 ; i++ ){
            final Grupo grupo = new Grupo();
            grupo.setNome(format("TESTE%s",i));
            repository.add(grupo);
        }

        Collection<Grupo> grupos = repository.list();

    }
}