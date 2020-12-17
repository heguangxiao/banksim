<%@page contentType="text/html; charset=utf-8" %><%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="<c:url value="/admin/index"/>" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> ${Lang['generic.home']}</a> 
            <a cl href="<c:url value="/admin/sys-account"/>" class="tip-bottom">${Lang['system.account.subMenu']}</a>
            <a cl href="<c:url value="/admin/sys-account/edit-account?id=${sysAcc.accId}"/>" class="current">${Lang['system.account.edit']}</a>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12" style="padding-top: 5px">
                <c:if test="${result!=null && result.code!=1}">
                    <div id="alert alert-error" class="alert"><button class="close" data-dismiss="alert">×</button><strong>Error!</strong> ${result.message}</div>
                </c:if>
                <div class="widget-box">
                    <div class="widget-content nopadding">
                        <form:form id="editForm" method="post" modelAttribute="sysAcc"  action="">
                            <input type="hidden" name="accId" value="${sysAcc.accId}"/>
                            <table class="table table-bordered table-striped">
                                <tbody>
                                    <tr>
                                        <td class="span3">${Lang['generic.userName']}</td>
                                        <td>
                                            <input id="username" readonly="true" value="${sysAcc.username}" type="text" class="span10" name="username"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.password']}</td>
                                        <td><input id="password" autocomplete="off" name="password" class="text-input span10" size="25" value="" type="password"></td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.fullName']}</td>
                                        <td>
                                            <input value="${sysAcc.fullName}" class="text-input span10" size="55" name="fullName" type="text">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.phone']}</td>
                                        <td><input id="phone" value="${sysAcc.phone}" type="text" class="span10" name="phone"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.email']}</td>
                                        <td><input id="email" name="email" value="${sysAcc.email}" class="text-input span10" size="25" type="text"></td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.address']}</td>
                                        <td>
                                            <textarea class="span10" name="address">${sysAcc.address}</textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>${Lang['generic.status']}</td>
                                        <td>
                                            <form:select path="status">
                                                <form:option value="${status.ACTIVE}">${Lang['generic.status.active']}</form:option>
                                                <form:option value="${status.UNACTIVE}">${Lang['generic.status.unactive']}</form:option>
                                                <form:option value="${status.LOCK}">${Lang['generic.status.lock']}</form:option>
                                            </form:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="6" class="img-center">
                                            <input id="btnSubmit" class="btn btn-success checkmobile" name="submit" value="${Lang['generic.update']}" type="submit">
                                            <input onclick="location.href = '<c:url value="/admin/sys-account/list" />'" class="btn btn-danger" name="submit" value="${Lang['generic.back']}" type="button">
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <!--</form>-->
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value='/admin/js/jquery.min.js'/>"></script>
<script src="<c:url value='/admin/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.alerts.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.validate.js'/>"></script>
<script type="text/javascript">
jQuery(document).ready(function () {
    var pasReg = /^(?=.*[0-9])(?=.*[!@#$%^&*()])(?=.*[A-Z])[a-zA-Z0-9!@#$%^&*]{8,30}$/;
    var vnf_regex = /^(84)+([0-9]{9,11})$/;
    jQuery("#editForm").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            phone: {
                required: true,
                regex: vnf_regex
            }
        },
        messages: {
            email: {
                required: "Bạn phải nhập email",
                email: "Email chưa đúng định dạng"
            },
            phone: {
                required: "Bạn phải nhập số điện thoại",
                regex: "Số điện thoại nhập theo định dạng 84xxxxxxxxx"
            }
        },

        submitHandler: function (form) {
            form.submit();
        }

    }
    );
    jQuery.validator.addMethod("regex", function (value, element, regexpr) {
        return regexpr.test(value);
    });
}
);
</script>