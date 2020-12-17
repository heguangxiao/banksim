<%@page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
    <head>
        <title><tiles:insertAttribute name="title" /></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        
        <link href="<c:url value='/css/bootstrap.min.css' />"  rel="stylesheet" type="text/css"/>
        <link href="<c:url value='/css/bootstrap-responsive.min.css' />"  rel="stylesheet" type="text/css"/>
        <link href="<c:url value='/css/maruti-login.css' />"  rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <tiles:insertAttribute name="body" />
    </body>
</html>
