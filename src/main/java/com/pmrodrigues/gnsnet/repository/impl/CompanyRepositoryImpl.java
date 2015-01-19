package com.pmrodrigues.gnsnet.repository.impl;

import com.pmrodrigues.gnsnet.models.Empresa;
import com.pmrodrigues.gnsnet.repository.CompanyRepository;
import com.pmrodrigues.gnsnet.repository.ResultList;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

/**
 * Created by Marceloo on 02/10/2014.
 */
@Repository("CompanyRepository")
public class CompanyRepositoryImpl extends AbstractRepository<Empresa> implements CompanyRepository {

    @Override
    public ResultList<Empresa> search(Empresa empresa, Integer page) {
        Criteria criteria = this.getSession().createCriteria(Empresa.class, "e")
                .setFetchMode("e.endereco.estado", FetchMode.JOIN);


        if( empresa != null ) {
            if(!GenericValidator.isBlankOrNull(empresa.getNome())){
                criteria.add(Restrictions.like("e.nome", empresa.getNome(), MatchMode.ANYWHERE));
            }

            if( empresa.getEndereco()!=null && empresa.getEndereco().getEstado() != null ) {
                criteria.add(Restrictions.eq("e.endereco.estado",empresa.getEndereco().getEstado()));
            }
        }

        return new ResultList<>(criteria,page);
    }
}
