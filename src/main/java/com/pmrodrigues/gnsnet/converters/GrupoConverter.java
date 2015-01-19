package com.pmrodrigues.gnsnet.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.gnsnet.models.Grupo;
import com.pmrodrigues.gnsnet.repository.GroupRepository;
import com.pmrodrigues.gnsnet.repository.Repository;

/**
 * Created by Marceloo on 01/10/2014.
 */
@Convert(Grupo.class)
@RequestScoped
public class GrupoConverter extends AbstractTypeConverter<Grupo> {

    public GrupoConverter(GroupRepository repository) {
        super(repository);
    }
}
