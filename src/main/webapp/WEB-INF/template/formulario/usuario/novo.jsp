<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<sec:authentication var="user" property="principal" />
<pmrodrigues:summary text="N�o foi poss�vel salvar o usu�rio." />

<form action="<c:url value='/usuario/salvar.do' />" role="form" method="post">
    <sec:csrfInput />
    <input type="hidden" name="usuario.id" value="${usuario.id}" />
    <fieldset>

        <c:if test="${user != null and user.empresa == null }">
            <pmrodrigues:combobox label="empresa" errorField="empresa" value="${empresas}" id="usuario.empresa" valueField="id" labelField="nome" selected="usuario.empresa" />
        </c:if>

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