package com.pmrodrigues.gnsnet.repository;

import com.pmrodrigues.gnsnet.models.Usuario;

/**
 * Created by Marceloo on 27/09/2014.
 */
public interface UserRepository extends Repository<Usuario> {

    Usuario findByEmail(final String email);

}
