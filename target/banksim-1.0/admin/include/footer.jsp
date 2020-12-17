<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/utilities.js"></script> 

<div class="row-fluid">
    <div id="footer" class="span12"> 
        2012 &copy; Marutii Admin. Brought to you by 
        <a href="http://themedesigner.in">Themedesigner.in</a> 
    </div>
</div>

<style type="text/css" >
    #newMessForm h3{ margin: 0; }

    #newMessForm { 
        display: none;
        border: 6px solid #49cced; 
        padding: 2em;
        width: 400px;
        text-align: center;
        background: #fff;
        position: fixed;
        top:50%;
        left:50%;
        transform: translate(-50%,-50%);
        -webkit-transform: translate(-50%,-50%)
    }

    #newMessForm input {
        margin: .8em auto;
        font-family: inherit; 
        text-transform: inherit; 
        font-size: inherit;
        display: block; 
        width: 280px; 
        height: 35px;
        padding: .4em;
    }
    #newMessForm textarea { 
        margin: .8em auto;
        font-family: inherit; 
        text-transform: inherit; 
        font-size: inherit;
        display: block; 
        width: 280px; 
        padding: .4em;
    }

    #newMessForm textarea { height: 80px; resize: none; }

    #newMessForm .formBtn { 
        width: 140px;
        display: inline-block;
        color: #fff;
        font-weight: 100;
        font-size: 1.2em;
        border: none;
        height: 30px;
    }
    
    
    #listMessForm h3{ margin: 0; }

    #listMessForm { 
        display: none;
        border: 6px solid #49cced; 
        padding: 2em;
        width: 400px;
        text-align: center;
        background: #fff;
        position: fixed;
        top:50%;
        left:50%;
        transform: translate(-50%,-50%);
        -webkit-transform: translate(-50%,-50%)
    }

    #listMessForm input {
        margin: .8em auto;
        font-family: inherit; 
        text-transform: inherit; 
        font-size: inherit;
        display: block; 
        width: 280px; 
        height: 35px;
        padding: .4em;
    }
    #listMessForm textarea { 
        margin: .8em auto;
        font-family: inherit; 
        text-transform: inherit; 
        font-size: inherit;
        display: block; 
        width: 280px; 
        padding: .4em;
    }

    #listMessForm textarea { height: 80px; resize: none; }

    #listMessForm .formBtn { 
        width: 140px;
        display: inline-block;
        color: #fff;
        font-weight: 100;
        font-size: 1.2em;
        border: none;
        height: 30px;
    }
</style>

<script type="text/javascript" language="javascript">
$(document).ready(function () {
    $(document).on("click", "#newMess", function () {
        $('#userlogInfoForm').fadeToggle();
        $(document).mouseup(function (e) {
            var container = $("#newMessForm");

            if (!container.is(e.target) // if the target of the click isn't the container...
                    && container.has(e.target).length === 0) // ... nor a descendant of the container
            {
                container.fadeOut();
            }
        });
    });
    $(document).on("click", "#listMess", function () {
        $('#userlogInfoForm').fadeToggle();
        $(document).mouseup(function (e) {
            var container = $("#listMessForm");

            if (!container.is(e.target) // if the target of the click isn't the container...
                    && container.has(e.target).length === 0) // ... nor a descendant of the container
            {
                container.fadeOut();
            }
        });
    });
});

</script>

<script src="<c:url value='/admin/js/excanvas.min.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.ui.custom.js'/>"></script>
<script src="<c:url value='/admin/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.peity.min.js'/>"></script>
<script src="<c:url value='/admin/js/fullcalendar.min.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.validate.js'/>"></script>