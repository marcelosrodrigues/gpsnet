package com.pmrodrigues.gnsnet.repository.impl;

import com.pmrodrigues.gnsnet.models.Cliente;
import com.pmrodrigues.gnsnet.repository.ClientRepository;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import static java.lang.String.format;

/**
 * Created by Marceloo on 11/10/2014.
 */
@Repository("ClientRepository")
public class ClientRepositoryImpl extends AbstractRepository<Cliente> implements ClientRepository {

    private static final Logger logging = Logger.getLogger(ClientRepositoryImpl.class);

    @Override
    public Cliente findByCPF(final String cpf) {

        logging.info(format("pesquisando cliente pelo cpf %s",cpf));
        Cliente cliente =  (Cliente) this.getSession()
                                         .createCriteria(Cliente.class,"c")
                                         .createCriteria("c.empresa", JoinType.INNER_JOIN)
                                         .createCriteria("c.endereco.estado",JoinType.INNER_JOIN)
                                         .add(Restrictions.eq("c.cpf", cpf))
                                         .uniqueResult();
        if(logging.isDebugEnabled()){
            logging.debug(format("cliente encontrado %s",cliente));
        }

        return cliente;
    }
}
