package com.pmrodrigues.gnsnet.repository;

import com.pmrodrigues.gnsnet.models.Cobranca;
import com.pmrodrigues.gnsnet.models.enums.Banco;

/**
 * Created by Marceloo on 02/10/2014.
 */
public interface BillingRepository extends Repository<Cobranca>{
    Cobranca findBillingByNumDoc(final String num, final Banco banco);
}
