<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Header-part-->
<div id="header">
    <h1><a href="/"></a></h1>
</div>
<!--close-Header-part--> 

<!--top-Header-messaages-->
<div class="btn-group rightzero"> 
    <a class="top_message tip-left" title="Manage Files">
        <i class="icon-file"></i>
    </a> 
    
    <a class="top_message tip-bottom" title="Manage Users">
        <i class="icon-user"></i>
    </a> 
    
    <a class="top_message tip-bottom" title="Manage Comments">
        <i class="icon-comment"></i>
        <span class="label label-important">5</span>
    </a> 
    <a class="top_message tip-bottom" title="Manage Orders">
        <i class="icon-shopping-cart"></i>
    </a> 
</div>
<!--close-top-Header-messaages--> 

<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
    <ul class="nav">
        <li class="dropdown" id="menu-messages">
            
        </li>
        
        <li class="dropdown" id="menu-settings">
            
        </li>
        
        <li class="">
            <a title="" href="<c:url value="/admin/logout"/>">
                <i class="icon icon-share-alt"></i> 
                <span class="text">Logout</span>
            </a>
        </li>
    </ul>
</div>
<!--close-top-Header-menu-->

<div id="sidebar">
    
    <a href="#" class="visible-phone">
        <i class="icon icon-signal"></i> Charts &amp; graphs
    </a>
    
    <ul>
        <li class="submenu"> 
            <a href="<c:url value="/admin/index"/>">
                <i class="icon icon-th-list"></i> 
                <span>Trang chủ</span> 
            </a> 
        </li>
            
        <li class="submenu"> 
            <a href="#">
                <i class="icon icon-th-list"></i>
                <span>${Lang['menu.system.main']}</span> 
                <span class="label label-important"></span>
            </a>
            <ul>
                <li>
                    <a href="<c:url value="/admin/sys-account/list"/>">
                        Quản lý Tài khoản hệ thống
                    </a>
                </li>
                
                <li>
                    <a href="<c:url value="/admin/user-action/list"/>">
                        Lịch sử người dùng thao tác</a>
                </li>
            </ul>
        </li>
        
    </ul>
            
</div>
