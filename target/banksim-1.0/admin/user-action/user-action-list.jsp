<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--Tùng thêm phần này để style select2-->
<style rel="style/sheet">
    .select2-container .select2-choice > .select2-chosen {
        margin-right: 0px;
    }

    .select2-container .select2-choice span {
        margin-right: 0px;
    }
    ._720kb-datepicker-calendar-day._720kb-datepicker-today {
        background: #0088cc;
        color:white;
    }
    ._720kb-datepicker-calendar-body, ._720kb-datepicker-calendar-days-header, ._720kb-datepicker-calendar-header, ._720kb-datepicker-calendar-years-pagination-pages, .datepicker, [datepicker], datepicker{
        /*float: none;*/
        width: 209px;
    }
    ._720kb-datepicker-calendar-month{

        color: #3258c0
    }
</style>
<div id="content">
    <div id="content-header">
        <div id="breadcrumb"><a href="<c:url value="/admin/index"/>" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
            <a href="<c:url value="/admin/user-action/"/>" class="current">Hành động của người dùng</a></div>
    </div>
    <div class="container-fluid">
        <div ng-app="UserActionApp" ng-controller="UserActionCtrl" class="row-fluid" ng-init="maxRow = '20'; crPage = '1'; user_name = ''; table_action = ''; action_type = ''; result_act = '';">
            <div class="span12">
                <div class="widget-box">
                    <div class="widget-title"> <span class="icon"><i class="icon-align-justify"></i> </span>
                        <h5>Tìm kiếm :</h5>                        
                    </div>
                    <div class="widget-content nopadding">
                        <form class="form-horizontal">
                            <div class="control-group">
                                <label class="control-label"> Tên người dùng:</label>
                                <div class="controls">
                                    <select ui-select2 ng-model="user_name" class="span3">
                                        <option value="">--- Tất cả ---</option>
                                        <option ng-repeat="ag in users" value="{{ag}}">{{ag}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label"> Bảng truy cập:</label>
                                <div class="controls">
                                    <select ui-select2 ng-model="table_action" class="span3">
                                        <option value="">--- Tất cả ---</option>
                                        <option ng-repeat="ag in tables" value="{{ag}}">{{ag}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label"> Kiểu hành động:</label>
                                <div class="controls">
                                    <select ui-select2 ng-model="action_type" class="span3">
                                        <option value="">--- Tất cả ---</option>
                                        <option ng-repeat="ag in typeAs" value="{{ag}}">{{ag}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label"> Kết quả:</label>
                                <div class="controls">
                                    <select ui-select2 style="width: 150px;vertical-align: middle" ng-model="result_act">
                                        <option value=""> Tất cả </option>
                                        <option value="SUCCESS"> SUCCESS </option>
                                        <option value="FAIL"> FAIL </option>
                                        <option value="NO_RIGHT"> NO RIGHT </option>
                                    </select>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="controls">
                                    <div ng-click="reloadFilter()" class="btn btn-success">Tìm kiếm</div>
                                    &nbsp;&nbsp;&nbsp;
                                    <input type="reset" class="btn btn-warning" ng-click="reset()" name="Reset Form" value="Làm mới"/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="widget-box">
                    <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
                        <h5>Hành động của người dùng <span id="result_model" class="callback_info">${result.message}</span>
                            <span class="callback_info">{{result.message}}</span></h5>
                        <div class="fr" style="padding: 3px">
                            Hiển thị 
                            <select ng-model="maxRow" ng-change="updateMaxRow()">
                                <option value="20">20 dòng</option>
                                <option value="50">50 dòng</option>
                                <option value="100">100 dòng</option>
                            </select>
                        </div>
                    </div>
                    <div class="widget-content nopadding">
                        <table class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>STT</th><th>Tên người dùng</th><th>Địa chỉ IP</th><th>Hành động URL</th>
                                    <th>Bảng hành động</th><th>Mã hành động</th><th>Kiểu hành động</th><th>Ngày hành động</th><th>Kết quả</th>
                                    <th>Thông tin</th>
                                    <th>Thông tin Cũ</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr ng-repeat="oneAct in datas">
                                    <td class="img-center">{{($index + 1)}}</td>
                                    <td>{{oneAct.userName}}</td>
                                    <td>{{oneAct.userIp}}</td>
                                    <td>{{oneAct.urlAction}}</td>
                                    <td>{{oneAct.tableAction}}</td>
                                    <td>{{oneAct.idAction}}</td>
                                    <td>{{oneAct.actionType}}</td>
                                    <!--<td ng-bind="formatDateTime(oneAct.actionDate)"></td>-->
                                    <td>{{oneAct.dateView}}</td>
                                    <td>{{oneAct.result}}</td>
                                    <td>{{oneAct.info}}</td>
                                    <td class="img-center">
                                        <a href="#myModal{{oneAct.id}}" data-toggle="modal">
                                            <img class="popover-result" src="<c:url value="/admin/images/detail.png"/>" alt="Xem Chi Tiết">
                                        </a>
                                        <div id="myModal{{oneAct.id}}" class="modal hide" style="display: none;" aria-hidden="true">
                                            <div class="modal-header">
                                                <button data-dismiss="modal" class="close" type="button">×</button>
                                                <h3>Thông tin đối tượng bị tác động lúc đầu
                                                    <br/>{{oneAct.classObject}}
                                                </h3>
                                            </div>
                                            <div class="modal-body">
                                                <p>
                                                    {{oneAct.lastObject}}
                                                </p>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr ng-if="totalRow > maxRow"> 
                                    <td colspan="13">
                                        <div class="pagination aalternate fr">
                                            <ul uib-pagination total-items="totalRow" ng-model="crPage" ng-change="pageChanged()" max-size="5" items-per-page="maxRow" class="" boundary-link-numbers="true"  num-pages="numPages"></ul>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value='/admin/js/jquery.min.js'/>"></script>
<script src="<c:url value='/admin/js/angular-ui-select2/select2o.js'/>"></script> 
<script src="<c:url value='/admin/js/angular-ui-select2/angular.js'/>"></script>
<script src="<c:url value='/admin/js/angular-ui-select2/select2.js'/>"></script>
<script src="<c:url value='/admin/js/ui-bootstrap-tpls-2.1.3.min.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.alerts.js'/>"></script>
<script src="<c:url value='/admin/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.blockUI.js'/>"></script>
<script src="<c:url value='/admin/js/maruti.popover.js'/>"></script>
<script src="<c:url value='/admin/controller/UseAction.js'/>"></script>
