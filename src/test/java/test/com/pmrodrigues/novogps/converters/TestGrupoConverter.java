package test.com.pmrodrigues.novogps.converters;

import static org.junit.Assert.assertNotNull;

import com.pmrodrigues.gnsnet.converters.GrupoConverter;
import com.pmrodrigues.gnsnet.models.Grupo;
import com.pmrodrigues.gnsnet.repository.GroupRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager" , defaultRollback = true)
public class TestGrupoConverter{

	@Autowired
	private GroupRepository repository;

	private GrupoConverter converter;

	@Before
	public void setup() {

        converter = new GrupoConverter(repository);
	}

	@Test
    @Transactional
	public void devePesquisarPeloId() {

        Grupo grupo = new Grupo();
        grupo.setNome("TESTE");

        repository.add(grupo);

		grupo = converter.convert(Long.toString(grupo.getId()),
				Grupo.class, null);

		assertNotNull(grupo);
	}
}
