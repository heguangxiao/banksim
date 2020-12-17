<%@page contentType="text/html" pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="loginbox">
    <form id="loginform" method="post" class="form-vertical" action="<c:url value="/admin/login"/>">
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
            <span class="pull-left"><a href="#" class="flip-link btn btn-inverse" id="to-recover">Quên Mật Khẩu?</a></span>
            <span class="pull-right"><input type="submit" class="btn btn-success" value="Đăng nhập" /></span>
        </div>
    </form>
    <form id="recoverform" action="#" class="form-vertical">
        <p class="normal_text">Enter your e-mail address below and we will send you instructions how to recover a password.</p>

        <div class="controls">
            <div class="main_input_box">
                <span class="add-on"><i class="icon-envelope"></i></span><input type="text" placeholder="E-mail address" />
            </div>
        </div>
        <div class="form-actions">
            <span class="pull-left"><a href="#" class="flip-link btn btn-inverse" id="to-login">&laquo; Back to login</a></span>
            <span class="pull-right"><input type="submit" class="btn btn-info" value="Recover" /></span>
        </div>
    </form>
</div>
<script src="<c:url value='/admin/js/jquery.min.js'/>"></script>  
<script src="<c:url value='/admin/js/maruti.login.js'/>"></script>  