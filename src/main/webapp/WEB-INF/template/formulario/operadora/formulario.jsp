<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<pmrodrigues:summary text="Não foi possível salvar a operadora." />

<form action="<c:url value='/operadora/salvar.do' />" role="form" method="post">
    <sec:csrfInput />
    <input type="hidden" name="object.id" value="${object.id}" />
    <fieldset>

            <div class="col-sm-6">
                <pmrodrigues:textfield label="operadora.nome" value="${object.nome}" id="object.nome" errorField="nome" />

                <button class="btn btn-info" type="submit">Salvar</button>
            </div>



    </fieldset>
</form>