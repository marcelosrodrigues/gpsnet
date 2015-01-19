package com.pmrodrigues.gnsnet.converters;

import static java.lang.String.format;

import java.util.ResourceBundle;

import com.pmrodrigues.gnsnet.repository.Repository;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Converter;

public class AbstractTypeConverter<E> implements Converter<E> {

    private final Repository<E> repository;

    private static final Logger logging = Logger
            .getLogger(AbstractTypeConverter.class);

    public AbstractTypeConverter(final Repository<E> repository) {
        this.repository = repository;
    }

    @Override
    public E convert(String value, Class<? extends E> type,
                     final ResourceBundle bundle) {
        logging.debug(format("Convertenado para o tipo %s", type));

        E entity = null;

        if (!GenericValidator.isBlankOrNull(value)
                && GenericValidator.isLong(value)) {
            entity = repository.findById(Long.parseLong(value));
            logging.debug(format("Objeto convertido %s", entity));
        }

        return entity;
    }

    protected Repository<E> getRepository() {
        return this.repository;
    }

}
