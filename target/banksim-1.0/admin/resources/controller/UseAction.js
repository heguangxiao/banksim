/* global context, urlBase, nologin, adm_login_uri */

var app = angular.module('UserActionApp', ['ui.bootstrap', "ui.select2"]);
app.controller('UserActionCtrl', function ($scope, $http, $filter) {
    $scope.reloadFilter = function (str) {
        $.blockUI({message: '<img src="' + context + '/admin/images/indicator_48.gif" />'});
        $http({
            method: "GET",
            url: urlBase + "/admin/user-action/rest/data",
            timeout: 10 * 1000,
            params: {
                crPage: $scope.crPage,
                user_name: $scope.user_name,
                table_action: $scope.table_action,
                action_type: $scope.action_type,
                result: $scope.result_act,
                maxRow: $scope.maxRow
            }
        }).then(function Succes(response) {
            if (response !== undefined && typeof response === "object") {
                $scope.result = response.data.result;
                if ($scope.result.code === nologin) {
                    location.href = context + adm_login_uri;
                } else {
                    $scope.datas = response.data.datas;
                    $scope.totalRow = response.data.totalRow;
                    $scope.roles = response.data.roles;
                    if (!angular.isUndefined(str) && str !== '') {
                        $scope.result.message = str;
                    }
                    blink_text('callback_info', 5000);
                    $.unblockUI();
                }
            }
        }, function Error(response) {
            $.unblockUI();
            $scope.message = response.status;
        });
        $http({
            method: "GET",
            url: urlBase + "/admin/user-action/rest/data2",
            timeout: 10 * 1000
        }).then(function Succes(response) {
            if (response !== undefined && typeof response === "object") {
                $scope.result = response.data.result;
                if ($scope.result.code === nologin) {
                    location.href = context + adm_login_uri;
                } else {
                    $scope.users = response.data.datas;
                    if (!angular.isUndefined(str) && str !== '') {
                        $scope.result.message = str;
                    }
                    blink_text('callback_info', 5000);
                    $.unblockUI();
                }
            }
        }, function Error(response) {
            $.unblockUI();
            $scope.message = response.status;
        });
        $http({
            method: "GET",
            url: urlBase + "/admin/user-action/rest/data3",
            timeout: 10 * 1000
        }).then(function Succes(response) {
            if (response !== undefined && typeof response === "object") {
                $scope.result = response.data.result;
                if ($scope.result.code === nologin) {
                    location.href = context + adm_login_uri;
                } else {
                    $scope.tables = response.data.datas;
                    if (!angular.isUndefined(str) && str !== '') {
                        $scope.result.message = str;
                    }
                    blink_text('callback_info', 5000);
                    $.unblockUI();
                }
            }
        }, function Error(response) {
            $.unblockUI();
            $scope.message = response.status;
        });
        $http({
            method: "GET",
            url: urlBase + "/admin/user-action/rest/data4",
            timeout: 10 * 1000
        }).then(function Succes(response) {
            if (response !== undefined && typeof response === "object") {
                $scope.result = response.data.result;
                if ($scope.result.code === nologin) {
                    location.href = context + adm_login_uri;
                } else {
                    $scope.typeAs = response.data.datas;
                    if (!angular.isUndefined(str) && str !== '') {
                        $scope.result.message = str;
                    }
                    blink_text('callback_info', 5000);
                    $.unblockUI();
                }
            }
        }, function Error(response) {
            $.unblockUI();
            $scope.message = response.status;
        });
    };
    $scope.$watch('crPage + crPage', function () {
        $scope.reloadFilter();
    });
    $scope.pageChanged = function () {
        $scope.crPage = this.crPage;
    };
    $scope.updateMaxRow = function () {
        $scope.crPage = 1;
        $scope.reloadFilter();
    };
    $scope.reset = function () {
        location.reload();
    };
    $scope.formatDate = function (date) {
        if (!angular.isUndefined(date) && date !== null)
            return $filter('date')(new Date(date), 'dd/MM/yyyy');
        else
            return "";
    };
    $scope.formatDateTime = function (date) {
        if (!angular.isUndefined(date) && date !== null)
            return $filter('date')(new Date(date), 'dd/MM/yyyy HH:mm:ss');
        else
            return "";
    };
});
//String.prototype.toDate = function (format) {
//    console.log('format:' + format);
//    var normalized = this.replace(/[^a-zA-Z0-9]/g, '-');
//    console.log('normalized:' + normalized);
//    var normalizedFormat = format.toLowerCase().replace(/[^a-zA-Z0-9]/g, '-');
//    console.log('normalizedFormat:' + normalizedFormat);
//    var formatItems = normalizedFormat.split('-');
//    console.log('formatItems:' + formatItems);
//    var dateItems = normalized.split('-');
//    console.log('dateItems:' + dateItems);
//
//    var monthIndex = formatItems.indexOf("MM");
//    var dayIndex = formatItems.indexOf("dd");
//    var yearIndex = formatItems.indexOf("yyyy");
//    var hourIndex = formatItems.indexOf("HH");
//    var minutesIndex = formatItems.indexOf("mm");
//    var secondsIndex = formatItems.indexOf("ss");
//
//    var today = new Date();
//
//    var year = yearIndex > -1 ? dateItems[yearIndex] : today.getFullYear();
//    var month = monthIndex > -1 ? dateItems[monthIndex] - 1 : today.getMonth();
//    var day = dayIndex > -1 ? dateItems[dayIndex] : today.getDate();
//
//    var hour = hourIndex > -1 ? dateItems[hourIndex] : today.getHours();
//    var minute = minutesIndex > -1 ? dateItems[minutesIndex] : today.getMinutes();
//    var second = secondsIndex > -1 ? dateItems[secondsIndex] : today.getSeconds();
//
//    return new Date(year, month, day, hour, minute, second);
//};