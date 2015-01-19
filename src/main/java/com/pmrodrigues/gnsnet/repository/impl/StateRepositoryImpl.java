package com.pmrodrigues.gnsnet.repository.impl;

import com.pmrodrigues.gnsnet.models.Estado;
import com.pmrodrigues.gnsnet.repository.StateRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Marceloo on 11/10/2014.
 */
@Repository("StateRepository")
public class StateRepositoryImpl extends AbstractRepository<Estado> implements StateRepository {
}
