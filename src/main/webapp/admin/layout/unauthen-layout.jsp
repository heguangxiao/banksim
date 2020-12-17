<%@page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
    <head>
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        
        <link href="<c:url value='/admin/css/bootstrap.min.css'/>"  rel="stylesheet" type="text/css"/>
        <link href="<c:url value='/admin/css/bootstrap-responsive.min.css' />"  rel="stylesheet" type="text/css"/>
        <link href="<c:url value='/admin/css/maruti-login.css'/>"  rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css?family=Comfortaa:300,400,700&amp;subset=latin-ext,vietnamese" rel="stylesheet">
        
        <style type="text/css">
            body{
                font-family: 'Comfortaa', cursive !important;
            }
        </style>
    </head>
    <body>
        <tiles:insertAttribute name="body"/>
    </body>
</html>
