package com.pmrodrigues.gnsnet.repository.impl;

import com.pmrodrigues.gnsnet.models.Grupo;
import com.pmrodrigues.gnsnet.repository.GroupRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Marceloo on 01/10/2014.
 */
@Repository("GroupRepository")
public class GroupRepositoryImpl extends AbstractRepository<Grupo> implements GroupRepository {
}
