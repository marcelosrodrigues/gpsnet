<%@ page pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>

    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Gestor de Negócios Seguradores</title>

    <!-- Core CSS - Include with every page -->
    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">

    <!-- SB Admin CSS - Include with every page -->
    <link href="<c:url value='/css/sb-admin.css' />" rel="stylesheet">

    <!-- Page-Level Plugin CSS - Dashboard -->
    <link href="<c:url value='/css/plugins/morris/morris-0.4.3.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/timeline/timeline.css' />" rel="stylesheet">

</head>

<body>

<div id="wrapper">

    <nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0">

    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
            <span class="sr-only"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/index.do">Gestor de Negócios Seguradores</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">

        <jsp:include page="includes/topmenu/messages.jsp" />

        <jsp:include page="includes/topmenu/tasks.jsp" />

        <jsp:include page="includes/topmenu/alerts.jsp" />

        <jsp:include page="includes/topmenu/profile.jsp" />

    </ul>
    <!-- /.navbar-top-links -->

    <div class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">

                <jsp:include page="includes/leftmenu/search.jsp" />

                <jsp:include page="includes/leftmenu/dashboard.jsp" />

                <jsp:include page="includes/leftmenu/report.jsp" />

                <jsp:include page="includes/leftmenu/admin.jsp" />

                <li>
                    <a href="#"><i class="fa fa-sitemap fa-fw"></i> Multi-Level Dropdown<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="#">Second Level Item</a>
                        </li>
                        <li>
                            <a href="#">Second Level Item</a>
                        </li>
                        <li>
                            <a href="#">Third Level <span class="fa arrow"></span></a>
                            <ul class="nav nav-third-level">
                                <li>
                                    <a href="#">Third Level Item</a>
                                </li>
                                <li>
                                    <a href="#">Third Level Item</a>
                                </li>
                                <li>
                                    <a href="#">Third Level Item</a>
                                </li>
                                <li>
                                    <a href="#">Third Level Item</a>
                                </li>
                            </ul>
                            <!-- /.nav-third-level -->
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
            </ul>
            <!-- /#side-menu -->
        </div>
        <!-- /.sidebar-collapse -->
    </div>
<!-- /.navbar-static-side -->
</nav>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><tiles:getAsString name="header" /></h1>
                </div>
            </div>

            <div class="col-lg-12">
                <tiles:insertAttribute name="conteudo" />
            </div>
        </div>
</div>
<!-- /#wrapper -->

<!-- Core Scripts - Include with every page -->
<script src="<c:url value='/js/jquery-1.10.2.js' />"></script>
<script src="<c:url value='/js/bootstrap.min.js' />"></script>
<script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>

<!-- Page-Level Plugin Scripts - Dashboard -->
<script src="<c:url value='/js/plugins/morris/raphael-2.1.0.min.js' />"></script>
<script src="<c:url value='/js/plugins/morris/morris.js' />"></script>

<!-- SB Admin Scripts - Include with every page -->
<script src="<c:url value='/js/sb-admin.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.color.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.maskMoney.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-maskinput.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-datepicker-pt.js' />" ></script>

<script type="text/javascript" src="<c:url value='/js/ui.js' />" ></script>


</body>

</html>

