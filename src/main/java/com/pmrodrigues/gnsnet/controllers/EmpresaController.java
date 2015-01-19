package com.pmrodrigues.gnsnet.controllers;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import com.pmrodrigues.gnsnet.annotations.Before;
import com.pmrodrigues.gnsnet.annotations.CRUD;
import com.pmrodrigues.gnsnet.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.gnsnet.models.Empresa;
import com.pmrodrigues.gnsnet.repository.CompanyRepository;
import com.pmrodrigues.gnsnet.repository.StateRepository;
import com.pmrodrigues.gnsnet.utilities.Constante;

/**
 * Created by Marceloo on 19/01/2015.
 */
@CRUD
@Resource
public class EmpresaController extends AbstractCRUDController<Empresa> {

    private final StateRepository repository;

    protected EmpresaController(CompanyRepository repository, StateRepository stateRepository, Result result, Validator validator) {
        super(repository, result, validator);
        this.repository = stateRepository;
    }

    @Before
    public void before() {
        getResult().include(Constante.ESTADOS , repository.list());
    }
}
