package com.pmrodrigues.gnsnet.repository;


import com.pmrodrigues.gnsnet.utilities.Constante;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

import java.util.List;

/**
 * Created by Marceloo on 10/12/2014.
 */
public class ResultList<E> {

    private final Long recordCount;
    private final Criteria criteria;
    private final Integer page;

    private Long pageCount;

    public ResultList( final Criteria criteria ) {
        this(criteria, 0);
    }

    public ResultList( final Criteria criteria , final Integer page ) {

        this.recordCount = (Long) criteria.setProjection(Projections.rowCount())
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        this.page = page;
        this.pageCount = recordCount / Constante.TAMANHO_PAGINA;
        if( recordCount % Constante.TAMANHO_PAGINA > 0L ){
            pageCount++;
        }

        criteria.setProjection(null);
        this.criteria = criteria;

    }

    public boolean isPrevious() {
        return page > 0L && page < pageCount;
    }

    public boolean isNext() {
        return pageCount > 1 && ( page + 1 ) < pageCount;
    }

    public Integer getPage() {
        return page;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public List<E> getList() {
        return this.criteria.setFirstResult( page * Constante.TAMANHO_PAGINA )
                            .setMaxResults( ( page * Constante.TAMANHO_PAGINA ) + Constante.TAMANHO_PAGINA )
                            .list();
    }

    public Long getRecordCount() {
        return recordCount;
    }
}
