package com.pmrodrigues.gnsnet.controllers;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import com.pmrodrigues.gnsnet.annotations.CRUD;
import com.pmrodrigues.gnsnet.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.gnsnet.models.Operadora;
import com.pmrodrigues.gnsnet.repository.OperadoraRepository;

/**
 * Created by Marceloo on 21/01/2015.
 */
@CRUD
@Resource
public class OperadoraController extends AbstractCRUDController<Operadora> {
    protected OperadoraController(OperadoraRepository repository, Result result, Validator validator) {
        super(repository, result, validator);
    }
}
