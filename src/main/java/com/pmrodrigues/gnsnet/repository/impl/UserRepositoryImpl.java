package com.pmrodrigues.gnsnet.repository.impl;

import com.pmrodrigues.gnsnet.models.Usuario;
import com.pmrodrigues.gnsnet.repository.ResultList;
import com.pmrodrigues.gnsnet.repository.UserRepository;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import static java.lang.String.format;

/**
 * Created by Marceloo on 27/09/2014.
 */
@Repository("UserRepository")
public class UserRepositoryImpl extends AbstractRepository<Usuario> implements UserRepository {

    private static final Logger logging = Logger.getLogger(UserRepositoryImpl.class);

    @Override
    public Usuario findByEmail(final String email) {

        try {
            logging.debug(format("pesquisando usuário pelo email %s",email));

            return (Usuario) this.getSession().createCriteria(Usuario.class,"u")
                             .setFetchMode("empresa", FetchMode.JOIN)
                             .setFetchMode("grupos", FetchMode.JOIN)
                             .add(Restrictions.eq("u.email",email))
                             .uniqueResult();
        } finally {
            logging.debug(format("termino na execução da pesquisa por usuario %s",email));
        }

    }

    @Override
    public ResultList<Usuario> search(Usuario usuario, Integer page) {
        try {
            logging.debug(format("pesquisando usuário como %s", usuario));

            Criteria criteria = this.getSession()
                    .createCriteria(Usuario.class, "u");
            if( usuario != null ){
                if(!GenericValidator.isBlankOrNull(usuario.getNome())){
                    criteria.add(Restrictions.like("nome", usuario.getNome() + "%"));
                }
                if(!GenericValidator.isBlankOrNull(usuario.getEmail())){
                    criteria.add(Restrictions.eq("email",usuario.getEmail()));
                }
                if( !usuario.getGrupos().isEmpty() ){
                    criteria.add(Restrictions.in("grupos",usuario.getGrupos()));
                }
            }

            return new ResultList<>(criteria,page);


        }finally{
            logging.debug(format("término da operação de pesquisa usuário como %s", usuario));
        }
    }

    @Override
    public ResultList<Usuario> search(final Usuario usuario) {

        return search(usuario,0);


    }
}
