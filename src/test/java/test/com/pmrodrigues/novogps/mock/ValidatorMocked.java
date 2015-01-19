package test.com.pmrodrigues.novogps.mock;

import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.I18nMessage;

import javax.validation.*;
import java.util.Set;

/**
 * Created by Marceloo on 01/10/2014.
 */
public class ValidatorMocked extends MockValidator {

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @Override
    public void validate(Object object, Class<?>... groups) {

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> constraints =  validator.validate(object, groups);

        for(ConstraintViolation<Object> constraint : constraints ){
            super.add(new I18nMessage("error",constraint.getMessage()));
        }

    }
}
