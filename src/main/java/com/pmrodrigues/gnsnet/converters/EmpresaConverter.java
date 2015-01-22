package com.pmrodrigues.gnsnet.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.gnsnet.models.Empresa;
import com.pmrodrigues.gnsnet.repository.CompanyRepository;

/**
 * Created by Marceloo on 21/01/2015.
 */
@Convert(Empresa.class)
@RequestScoped
public class EmpresaConverter extends AbstractTypeConverter<Empresa> {
    public EmpresaConverter(CompanyRepository repository) {
        super(repository);
    }
}
