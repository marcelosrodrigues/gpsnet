<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>
<div class="col-lg-12">


    <c:if test="${not empty sucesso}">
        <div class="alert alert-dismissable alert-success">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <strong>${sucesso}</strong>
        </div>
    </c:if>

    <form action="<c:url value='/operadora/pesquisar.do'/>" role="form">
        <div class="row">
            <div class="col-sm-4">
                <div class="form-group">
                    <input type="text" name="object.nome" id="object.nome" placeholder="<fmt:message key='operadora.nome' bundle='${labels}'  />" class="form-control" />
                </div>
            </div>

            <div class="col-sm-4">
                <button type="submit" value="pesquisar" class="btn btn-info"><fmt:message key="button.pesquisar" bundle="${labels}" /></button>
            </div>
        </div>
    </form>

    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th style="width:5%">#</th>
            <th style="width:35%"><fmt:message key="operadora.nome" bundle="${labels}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resultlist.list}" var="object">
            <tr>
                <td><a href='<c:url value="/operadora/${object.id}/abrir.do" />'>${object.id}</a></td>
                <td>${object.nome}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="row">
        <div class="col-sm-6">
            <div class="dataTables_info" id="dataTables-example_info" role="alert" aria-live="polite" aria-relevant="all">Página ${resultlist.page + 1} de ${resultlist.pageCount}</div>
        </div>
        <c:if test="${resultilist.pageCount gt 1}">
            <div class="col-sm-6" style="top: -30px;text-align:right;">
                <div class="dataTables_paginate paging_simple_numbers">
                    <ul class="pagination">
                        <li class="paginate_button previous <c:if test='${not resultlist.previous}'>disabled</c:if>" aria-controls="dataTables-example" tabindex="0"
                            id="dataTables-example_previous">
                            <c:if test='${resultlist.previous}'>
                                <a href="<c:url value='/usuario/pesquisar.do'/>?object.nome=${param['object.email']}&page=${resultlist.page - 1}">&lt;&lt;</a>
                            </c:if>
                        </li>

                        <c:forEach begin="1" end="${resultlist.pageCount}" var="index">
                            <li class="paginate_button" tabindex="${index - 1}">
                                <a href="<c:url value='/loja/pesquisar.do'/>?object.nome=${param['object.nome']}&page=${index -1}">${index}</a>
                            </li>
                        </c:forEach>

                        <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0"
                            id="dataTables-example_next <c:if test='${not resultlist.next}'>disabled</c:if>">
                            <c:if test='${not resultlist.next}'>
                                <a href="<c:url value='/usuario/pesquisar.do'/>?object.email=${param['object.email']}&page=${resultlist.page + 1}">&gt;&gt;</a>
                            </c:if>
                        </li>
                    </ul>
                </div>
            </div>
        </c:if>
    </div>
    <div class="row" style="margin-top: 10px;">
        <div class="col-sm-6">
            <a href="<c:url value='/operadora/novo.do' />" class="btn btn-info">Adicionar</a>
        </div>
    </div>
</div>