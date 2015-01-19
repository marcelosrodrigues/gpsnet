package test.com.pmrodrigues.novogps.repositories;

import com.pmrodrigues.gnsnet.models.Cliente;
import com.pmrodrigues.gnsnet.models.Empresa;
import com.pmrodrigues.gnsnet.models.Estado;
import com.pmrodrigues.gnsnet.models.embeddables.Endereco;
import com.pmrodrigues.gnsnet.repository.ClientRepository;
import com.pmrodrigues.gnsnet.repository.CompanyRepository;
import com.pmrodrigues.gnsnet.repository.StateRepository;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Marceloo on 11/10/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager" , defaultRollback = true)
public class TestClientRepository extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource(name = "ClientRepository")
    private ClientRepository repository;

    @Before
    public void setup() {

        cleanUP();
        jdbcTemplate.update("insert into empresa ( nome , bloqueado ) values ( 'TESTE' , false )");

        Long id = jdbcTemplate.queryForObject("select id from empresa where nome = 'TESTE'" , Long.class);

        jdbcTemplate.update("insert into usuario ( bloqueado , email , empresa_id , nome , password , tentativas) values ( false , 'marsilvarodrigues@gmail.com',?,'teste','12345678',0)",id);
        Long usuarioId = jdbcTemplate.queryForObject("select id from usuario where email = 'marsilvarodrigues@gmail.com'",Long.class);

        jdbcTemplate.update("insert into cliente ( cpf , bairro , cep , cidade , uf , logradouro , id ) values ( '111' , 'teste' , 'teste' , 'teste' , 'RJ', 'teste' , ?)",usuarioId);


    }

    @After
    public void cleanUP() {

        this.jdbcTemplate.query("select id from empresa where nome = 'TESTE'" , new RowMapper<Void>() {
            @Override
            public Void mapRow(ResultSet resultSet, int i) throws SQLException {

                jdbcTemplate.query("select id from usuario where empresa_id = ?" , new RowMapper<Void>() {
                    @Override
                    public Void mapRow(ResultSet rs, int i) throws SQLException {
                        jdbcTemplate.update("delete from cliente where id = ?" , rs.getLong("id"));
                        jdbcTemplate.update("delete from usuario where id = ?",rs.getLong("id"));
                        return null;
                    }
                },resultSet.getLong("id"));
                return null;
            }
        });
        jdbcTemplate.update("delete from empresa where nome = 'TESTE'");
    }

    @Test
    @Transactional()
    public void shouldFoundClientByCPF() {

        final Cliente founded = repository.findByCPF("111");
        assertNotNull(founded);

    }
}
