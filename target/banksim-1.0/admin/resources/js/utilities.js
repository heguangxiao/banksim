/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * ===========================================
 *      readyState
 *	0 (Uninitialized): Đối tượng mới đựơc tạo nhưng hàm open chưa được gọi.
 *	1 (Loading): Hàm open  mới được gọi nhưng request chưa được gởi
 *	2 (Loaded): Request vừa mới được gởi
 *	3 (Interactive): Client đã nhận được một phần response từ server
 *	4 (Complete): Tất cả dữ liệu đã được server gởi về client và kết nối đã đóng lại.
 *
 */
var context = "/banksim";
var fail = 0;
var success = 1;
var nologin = -1;
var noRight = 3;
function urlEncode(str) {
    str = escape(str);
    str = str.replace(new RegExp('\\+', 'g'), '%2B');
    return str.replace(new RegExp('%20', 'g'), '+');
}
function GetXmlHttpObject() {
    var objXMLHttp = null;
    if (window.XMLHttpRequest) {
        objXMLHttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        objXMLHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return objXMLHttp;
}
function AjaxAction(url, where) {
    var xmlHttp = new GetXmlHttpObject();
    if (xmlHttp === null) {
        return;
    }
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 || xmlHttp.readyState === 200) {
            document.getElementById(where).innerHTML = xmlHttp.responseText;
        }
    };
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
}
function AjaxActionExpire(url, where) {
    var xmlHttp = new GetXmlHttpObject();
    if (xmlHttp === null) {
        return;
    }
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 || xmlHttp.readyState === 200) {
            var data = xmlHttp.responseText;
            try {
                data = JSON.parse(data);
            } catch (e) {
                console.log(e.toString());
            }
            if (data.result != null && data.result != 'undefined') {
                var result = data.result;
                if (result.code == nologin) {
                    location.href = context + '/admin/login';
                } else {
                    document.getElementById(where).innerHTML = xmlHttp.responseText;
                }
            } else {
                document.getElementById(where).innerHTML = xmlHttp.responseText;
            }
        }
    };
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
}
function AjaxStr(url) {
    var xmlHttp = new GetXmlHttpObject();
    if (xmlHttp === null) {
        return "";
    }
    xmlHttp.open("GET", url, false);
    xmlHttp.send();
    return xmlHttp.responseText;
}
function AjaxStrExpire(url) {
    var xmlHttp = new GetXmlHttpObject();
    if (xmlHttp === null) {
        return;
    }
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 || xmlHttp.readyState === 200) {
            var data = xmlHttp.responseText;
            try {
                data = JSON.parse(data);
            } catch (e) {
                console.log(e.toString());
            }
            if (data.result != null && data.result != 'undefined') {
                var result = data.result;
                if (result.code == nologin) {
                    location.href = context + '/admin/login';
                } else {
                    return xmlHttp.responseText;
                }
            } else {
                return xmlHttp.responseText;
            }
        }
    };
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
}
function submitForm(formId) {
    document.getElementById(formId).submit();
}
function closePopup(id) {
    $("#lean_overlay").fadeOut(200);
    $("#" + id).css({display: "none"});
}
function ajaxLoad(url, divid) {
    if (url.length === 0) {
        return;
    }
    $.get(url, divid, function success(responseText) {
        if (responseText !== "error" && responseText !== "") {
            document.getElementById(divid).innerHTML = responseText;
        } else {
            alert("Xử lý thất bại!");
        }
    });
}
function checkBoxClick(obj) {
    if (obj.value === 1) {
        obj.value = 0;
    } else {
        obj.value = 1;
    }
}
function checkall(name) {
    var chk = document.getElementById("check" + name);
    if (chk.checked) {
        var chkmove = $('input[name^=' + name + ']');
        for (var i = 0; i < chkmove.length; i++)
            chkmove[i].checked = true;
    } else {
        var chkmove = $('input[name^=' + name + ']');
        for (var i = 0; i < chkmove.length; i++)
            chkmove[i].checked = false;
    }
}
function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}
function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}
function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}
//Ham chuyen chu co dau sang khong dau
function locdau(str) {
    str = str.toLowerCase();
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
    str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
    str = str.replace(/đ/g, "d");
    return str;
}

function customMatcher(term, text) {
//    // Always return the object if there is nothing to compare
    if ($.trim(term) === '') {
        return true;
    }
    term = locdau(term);
    text = locdau(text);
    return text.toUpperCase().indexOf(term.toUpperCase()) >= 0;
}
function isGSMAlphabet(text) {
    var regexp = new RegExp("^[A-Za-z0-9 \\r\\n@£$¥èéùìòÇØøÅå\u0394_\u03A6\u0393\u039B\u03A9\u03A0\u03A8\u03A3\u0398\u039EÆæßÉ!\"#$%&'()*+,\\-./:;<=>?¡ÄÖÑÜ§¿äöñüà^{}\\\\\\[~\\]|\u20AC]*$");
    return regexp.test(text);
}

function randomString(length) {
    var result = '';
    for (var i = length; i > 0; --i)
        result += rString[Math.floor(Math.random() * rString.length)];
    return result;
}
function blink_text(clcss, second) {
    for (var i = 1; i < 5; i++) {
        $('.' + clcss).fadeOut(500);
        $('.' + clcss).fadeIn(500);
        setTimeout(function () {
            //do what you need here
        }, second);
    }
}
function delay(second) {
    setTimeout(function () {
        //do what you need here
    }, second);
}
var rString = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
Date.prototype.hhmiss = function () {
    var yy = this.getFullYear();
    var mon = this.getMonth();
    var dd = this.getDaysInMonth();
    var hh = this.getHours();
    var mi = this.getMinutes();
    var ss = this.getSeconds();
    return [
        yy,
        (mon < 10 ? "0" : "") + mon,
        (dd < 10 ? "0" : "") + dd,
        (hh < 10 ? "0" : "") + hh,
        (mi < 10 ? "0" : "") + mi,
        (ss < 10 ? "0" : "") + ss
    ].join('');
};

String.prototype.toDate = function (format) {
    var normalized = this.replace(/[^a-zA-Z0-9]/g, '-');
    var normalizedFormat = format.toLowerCase().replace(/[^a-zA-Z0-9]/g, '-');
    var formatItems = normalizedFormat.split('-');
    var dateItems = normalized.split('-');

    var monthIndex = formatItems.indexOf("MM");
    var dayIndex = formatItems.indexOf("dd");
    var yearIndex = formatItems.indexOf("yyyy");
    var hourIndex = formatItems.indexOf("HH");
    var minutesIndex = formatItems.indexOf("mm");
    var secondsIndex = formatItems.indexOf("ss");

    var today = new Date();

    var year = yearIndex > -1 ? dateItems[yearIndex] : today.getFullYear();
    var month = monthIndex > -1 ? dateItems[monthIndex] - 1 : today.getMonth();
    var day = dayIndex > -1 ? dateItems[dayIndex] : today.getDate();

    var hour = hourIndex > -1 ? dateItems[hourIndex] : today.getHours();
    var minute = minutesIndex > -1 ? dateItems[minutesIndex] : today.getMinutes();
    var second = secondsIndex > -1 ? dateItems[secondsIndex] : today.getSeconds();

    return new Date(year, month, day, hour, minute, second);
};