package com.pmrodrigues.gnsnet.repository;

import com.pmrodrigues.gnsnet.models.Cliente;

/**
 * Created by Marceloo on 02/10/2014.
 */
public interface ClientRepository extends Repository<Cliente>{
    Cliente findByCPF(String cpf);
}
