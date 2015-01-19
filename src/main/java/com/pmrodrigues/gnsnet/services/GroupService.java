package com.pmrodrigues.gnsnet.services;

import com.pmrodrigues.gnsnet.models.Grupo;
import com.pmrodrigues.gnsnet.repository.GroupRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Created by Marceloo on 01/10/2014.
 */
@Service("GroupService")
public class GroupService {

    private static final Logger logging = Logger.getLogger(GroupService.class);

    @Resource(name = "GroupRepository")
    private GroupRepository repository;

    public Collection<Grupo> list() {
        logging.debug("listando todos os grupos");
        return repository.list();
    }
}
