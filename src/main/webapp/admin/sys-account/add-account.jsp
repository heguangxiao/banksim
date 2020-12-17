<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="<c:url value="/admin/index"/>" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> ${Lang['generic.home']}</a> 
            <a cl href="<c:url value="/admin/sys-account/list"/>" class="tip-bottom">${Lang['system.account.subMenu']}</a>
            <a cl href="#" class="current">${Lang['system.account.subMenu.add']}</a>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12" style="padding-top: 5px">
                <c:if test="${result!=null && result.code!=1}">
                    <div id="alert alert-error" class="alert">
                        <button class="close" data-dismiss="alert">×</button>
                        <strong>Error!</strong> 
                        ${result.message}
                    </div>
                </c:if>
                <div class="widget-box">
                    <div class="widget-content nopadding">
                        <form:form id="addAccfrm" action="" modelAttribute="sysAcc" method="post">
                            <table class="table table-bordered table-striped">
                                <tbody>
                                    <tr>
                                        <td class="span3">${Lang['generic.userName']}</td>
                                        <td>
                                            <input id="username" autocomplete="off" type="text" name="username" class="span7" placeholder="username"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.password']}</td>
                                        <td>
                                            <input id="password" autocomplete="off"  type="password" name="password" cssClass="span7" placeholder="password"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.fullname']}</td>
                                        <td>
                                            <input value="${sysAcc.fullname}" id="fullname" type="text" name="fullname" size="55" class="text-input span7" placeholder="Nguyễn Văn A hoặc Jhon Smith"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.phone']}</td>
                                        <td>
                                            <input id="phone" value="${sysAcc.phone}" type="text" name="phone" class="span7" placeholder="84988988988"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.email']}</td>
                                        <td>
                                            <input id="email" value="${sysAcc.email}" type="text" name="email" size="25" class="text-input span7" placeholder="abc@aef.xyz"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.address']}</td>
                                        <td><textarea class="span7" name="address">${sysAcc.address}</textarea></td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.status']}</td>
                                        <td>
                                            <form:select path="status">
                                                <form:option value="${status.ACTIVE}">Kích hoạt </form:option>
                                                <form:option value="${status.UNACTIVE}">Chưa kích hoạt</form:option>
                                                <form:option value="${status.LOCK}">Khóa</form:option>
                                            </form:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="img-center" colspan="2" align="center">
                                            <input value="${Lang['generic.addNew']}" type="submit" id="btnSubmit" class="btn btn-success" name="submit" />
                                            &nbsp;&nbsp;&nbsp;
                                            <input value="${Lang['generic.back']}" class="btn btn-danger" onclick="location.href = '<c:url value="/admin/sys-account/list" />'" type="button">
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value='/admin/js/jquery.min.js'/>"></script>
<script src="<c:url value='/admin/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.validate.js'/>"></script>
<script type="text/javascript">
jQuery(document).ready(function () {
    var pasReg = /^(?=.*[0-9])(?=.*[!@#$%^&*()])(?=.*[A-Z])[a-zA-Z0-9!@#$%^&*]{8,30}$/;
    jQuery("#addAccfrm").validate({
        rules: {
            username: {
                required: true,
                minlength: 4,
                maxlength: 20
            },
            password: {
                required: true,
                minlength: 8,
                regex: pasReg
            },
            email: {
                required: true,
                email: true
            },
            phone: {
                required: true
            }
        },
        messages: {
            username: {
                required: "Bạn phải nhập username",
                minlength: "Tối thiểu 4 ký tự",
                maxlength: "Tối đa 20 ký tự"
            },
            password: {
                required: "Bạn phải nhập password",
                minlength: "Tối thiểu 8 ký tự",
                regex: "Mật khẩu phải bao gồm chữ hoa chữ thường, ký tự đặc biệt và số Ex: kdie#Ak12dieE$"
            },
            email: {
                required: "Bạn phải nhập email",
                email: "Email chưa đúng định dạng"
            },
            phone: {
                required: "Bạn phải nhập số điện thoại"
            }
        },

        submitHandler: function (form) {
            form.submit();
        }

    });
    
    jQuery.validator.addMethod("regex", function (value, element, regexpr) {
        return regexpr.test(value);
    });
}
);
</script>
