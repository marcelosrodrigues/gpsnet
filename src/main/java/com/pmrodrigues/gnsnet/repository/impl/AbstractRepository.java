package com.pmrodrigues.gnsnet.repository.impl;

import static java.lang.String.format;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.pmrodrigues.gnsnet.repository.Repository;
import com.pmrodrigues.gnsnet.repository.ResultList;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PrePersist;

@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractRepository<E> implements Repository<E> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory; //NOPMD

	private final Class<E> persistentClass; //NOPMD

	private static final Logger LOGGER = Logger
			.getLogger(AbstractRepository.class);

	@SuppressWarnings("unchecked")
	public AbstractRepository() {
		final ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		this.persistentClass = (Class<E>) type.getActualTypeArguments()[0];
	}

	@Override
	public void add(final E e) {

		LOGGER.debug(format("Tentando inserir %s novo valor no banco de dados",
				e));

		this.getSession().save(e);
		LOGGER.debug(format(" %s salvo com sucesso", e));
	}

	@Override
	public void set(final E entity) {
		LOGGER.debug(format("Atualizando o valor %s no banco de dados", entity));
		this.getSession().update(entity);
		LOGGER.debug(format("%s salvo com sucesso", entity));
	}

	@Override
	public void remove(final E entity) {
		LOGGER.debug(format("Removendo o valor %s do banco de dados", entity));
		this.getSession().delete(entity);
		LOGGER.debug(format("%s removido do banco de dados", entity));
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public E findById(final Serializable id) { //NOPMD
		LOGGER.debug(format(
				"Recuperando o valor de %s do banco de dados pela chave %s",
				persistentClass.getName(), id));
		final E entity = (E) this.getSession().get(persistentClass, id);
		LOGGER.debug(format("Valor encontrado %s", entity));
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<E> list() {
		LOGGER.debug(format(
				"Listando todos os valores de %s do banco de dados",
				persistentClass.getCanonicalName()));

        return this.getSession().createCriteria(persistentClass)
                .addOrder(Order.asc("id"))
                .list();
	}

	protected Session getSession() {
		Session session = this.sessionFactory.getCurrentSession();
		if (session == null || !session.isOpen()) { //NOPMD
			session = this.sessionFactory.openSession();
		}
		return session;
	}

    public ResultList<E> search(E e, Integer page){
        return null;
    }

    public ResultList<E> search(E e) {
        return search(e,0);
    }

}
