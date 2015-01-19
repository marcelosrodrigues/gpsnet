package com.pmrodrigues.gnsnet.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.gnsnet.models.Estado;
import com.pmrodrigues.gnsnet.repository.StateRepository;
import org.apache.commons.validator.GenericValidator;

import java.util.ResourceBundle;

/**
 * Created by Marceloo on 19/01/2015.
 */
@Convert(Estado.class)
@RequestScoped
public class EstadoConverter implements Converter<Estado> {

    private final StateRepository repository;

    public EstadoConverter(StateRepository repository) {
        this.repository = repository;
    }

    @Override
    public Estado convert(String value, Class<? extends Estado> type, ResourceBundle bundle) {
        Estado entity = null;

        if (!GenericValidator.isBlankOrNull(value)) {
            entity = repository.findById(value);
        }

        return entity;
    }
}
