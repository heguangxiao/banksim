<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="loginbox">
    <form id="loginform" method="post" class="form-vertical" action="<c:url value="/login"/>">
        <div class="control-group normal_text"> 
            <div class="text" style="color: red">${error}</div>
        </div>
        <div class="control-group">
            <div class="controls">
                <div class="main_input_box">
                    <span class="add-on"><i class="icon-user"></i></span><input name="username" type="text" placeholder="Tên đăng nhập" />
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <div class="main_input_box">
                    <span class="add-on"><i class="icon-lock"></i></span><input name="password" type="password" placeholder="Mật khẩu" />
                </div>
            </div>
        </div>
        <div class="form-actions">
            <span class="pull-left"><a href="#" class="flip-link btn btn-inverse" id="to-recover">${Lang['login.lostPassword']}?</a></span>
            <span class="pull-right"><input type="submit" class="btn btn-success" value="${Lang['generic.login']}" /></span>
        </div>
    </form>
    <form id="recoverform" action="#" class="form-vertical">
        <p id="alert-message" class="normal_text">${Lang['login.lostPassword.message']}</p>
        <div class="controls">
            <div class="main_input_box">
                <span class="add-on"><i class="icon-user"></i></span><input name="username" id="username" type="text" placeholder="${Lang['generic.userName']}" />
            </div>
            <div class="main_input_box">
                <span class="add-on"><i class="icon-envelope"></i></span><input type="text" name="email" id="emailRecover" placeholder="${Lang['generic.email']}" />
            </div>
        </div>
        <div class="form-actions">
            <span class="pull-left"><a href="#" class="flip-link btn btn-inverse" id="to-login">&laquo; ${Lang['login.back']}</a></span>
            <span class="pull-right"><input id="recover_btn" type="button" class="btn btn-info" value="${Lang['generic.recover']}" /></span>
        </div>
    </form>
</div>
<script src="<c:url value='/js/jquery.min.js'/>"></script>  
<script src="<c:url value='/js/maruti.login.js'/>"></script>
<script src="<c:url value='/js/utilities.js'/>"></script>
<script>
    $(document).ready(function () {
        $("#recover_btn").click(function () {
            $.ajax({
                type: "POST",
                url: context + "/gui-recover-pass",
                data: {email: $("#emailRecover").val(),username:$("#username").val(), _: Math.floor(Math.random() * 10000)},
                dataType: "json",
                success: function (data) {
                    if (data.code == nologin) {
                        location.href = context + '/login';
                    } else if (data.code == success) {
                        $("p#alert-message").html(data.message);
                    } else {
                        $("p#alert-message").html(data.message);
                    }
                }
            });
        });
    });
</script>