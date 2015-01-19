package test.com.pmrodrigues.novogps.repositories;

import com.pmrodrigues.gnsnet.models.enums.Banco;
import com.pmrodrigues.gnsnet.repository.BillingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Marceloo on 03/10/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager" , defaultRollback = true)
public class TestBillingRepository {

    @Resource(name = "BillingRepository")
    private BillingRepository repository;


    @Test
    @Transactional
    public void shouldFindBilling() {

        repository.findBillingByNumDoc("", Banco.ITAU);

    }
}
