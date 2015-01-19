<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<pmrodrigues:summary text="Não foi possível salvar o usuário." />

<form action="<c:url value='/usuario/salvar.do' />" role="form" method="post">
    <sec:csrfInput />
    <input type="hidden" name="usuario.id" value="${usuario.id}" />
    <fieldset>

        <pmrodrigues:textfield label="usuario.nome"
                               value="${usuario.nome}"
                               id="usuario.nome"
                               errorField="nome" />

        <pmrodrigues:textfield label="usuario.email"
                               value="${usuario.email}"
                               id="usuario.email"
                               errorField="email"/>

        <pmrodrigues:checkbox id="grupos"
               label="Permiss&atilde;o"
               value="${roles}"
               labelField="nome"
               valueField="id"
               checked="${usuario.grupos}" />

        <button class="btn btn-info" type="submit">Salvar</button>

    </fieldset>
</form>