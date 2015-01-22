package com.pmrodrigues.gnsnet.repository.impl;

import com.pmrodrigues.gnsnet.models.Operadora;
import com.pmrodrigues.gnsnet.repository.OperadoraRepository;
import com.pmrodrigues.gnsnet.repository.ResultList;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by Marceloo on 21/01/2015.
 */
@Repository
public class OperadorRepositoryImpl extends AbstractRepository<Operadora> implements OperadoraRepository {

    @Override
    public ResultList<Operadora> search(final Operadora operadora, Integer page) {

        Criteria criteria = this.getSession().createCriteria(Operadora.class);
        if( operadora != null && !GenericValidator.isBlankOrNull(operadora.getNome())){
            criteria.add(Restrictions.like("nome",operadora.getNome(), MatchMode.ANYWHERE));
        }

        return new ResultList<>(criteria);
    }
}
