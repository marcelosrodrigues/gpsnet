<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<pmrodrigues:summary text="Não foi possível salvar a empresa." />

<form action="<c:url value='/empresa/salvar.do' />" role="form" method="post">
    <sec:csrfInput />
    <input type="hidden" name="object.id" value="${object.id}" />
    <fieldset>
        <pmrodrigues:textfield label="empresa.nome" value="${object.nome}" id="object.nome" errorField="nome" />

        <div class="row">
            <div class="col-sm-4">
                <pmrodrigues:combobox label="estado"
                     errorField="estado" value="${estados}"
                     id="object.endereco.estado"
                     valueField="uf"
                     labelField="nome"
                     selected="${object.endereco.estado}" />
            </div>
            <div class="col-sm-4">
                <pmrodrigues:textfield label="cidade" value="${object.endereco.cidade}" id="object.endereco.cidade" errorField="" />
            </div>

            <div class="col-sm-4">
                <pmrodrigues:textfield label="bairro" value="${object.endereco.bairro}" id="object.endereco.bairro" errorField="" />
            </div>

        </div>

        <div class="row">
            <div class="col-sm-3">
                <pmrodrigues:textfield label="cep" value="${object.endereco.cep}" id="object.endereco.cep" errorField="" />
            </div>
            <div class="col-sm-9">
                <pmrodrigues:textfield label="logradouro" value="${object.endereco.logradouro}" id="object.endereco.logradouro" errorField="" />
            </div>
        </div>

        <div class="form-group" style="margin-left: 15px;">
            <input type="hidden" id="object.bloqueado" name="object.bloqueado" value="${object.bloqueado}" />
            <label class="checkbox-inline">
                <input type="checkbox" id="bloqueado" name="bloqueado"
                       <c:if test="${object.bloqueado}">checked="checked"</c:if>/>Bloqueado
            </label>
        </div>

        <button class="btn btn-info" type="submit">Salvar</button>

    </fieldset>
</form>