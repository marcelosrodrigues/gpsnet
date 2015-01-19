package com.pmrodrigues.gnsnet.repository;

import java.io.Serializable;
import java.util.List;

public interface Repository<E> extends Serializable {

	void add(E e);

	void set(E e);

	void remove(E e);

	E findById(Serializable e);

	List<E> list();

    ResultList<E> search(E e, Integer page);

    ResultList<E> search(E e);
}
