package com.pmrodrigues.gnsnet.repository.impl;

import com.pmrodrigues.gnsnet.models.Cobranca;
import com.pmrodrigues.gnsnet.models.enums.Banco;
import com.pmrodrigues.gnsnet.repository.BillingRepository;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import static java.lang.String.format;

/**
 * Created by Marceloo on 03/10/2014.
 */
@Repository("BillingRepository")
public class BillingRepositoryImpl extends AbstractRepository<Cobranca> implements BillingRepository{

    private static final Logger logging = Logger.getLogger(BillingRepositoryImpl.class);

    @Override
    public Cobranca findBillingByNumDoc(final String num , final Banco banco) {

        logging.info(format("pesquisando o documento %s do banco %s",num,banco));

        final Cobranca cobranca = (Cobranca) this.getSession().createCriteria(Cobranca.class,"c")
                         .setFetchMode("c.cliente", FetchMode.JOIN)
                         .setFetchMode("c.empresa",FetchMode.JOIN)
                         .add(Restrictions.eq("c.numeroDoDocumento",num))
                         .add(Restrictions.eq("c.banco",banco))
                         .uniqueResult();

        if( logging.isDebugEnabled() ){
            logging.debug(format("Cobranca encontrada %s",cobranca));
        }

        return cobranca;
    }
}
