<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Header-part-->
<div id="header">
    <h1><a href="dashboard.html">Maruti Admin</a></h1>
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
        <li class="" >
            <a title="" href="#">
                <i class="icon icon-user"></i> 
                <span class="text">${userlogin.username}</span>
            </a>
        </li>
        <li class=" dropdown" id="menu-messages">
            <a href="#" data-toggle="dropdown" data-target="#menu-messages" class="dropdown-toggle">
                <i class="icon icon-envelope"></i>
                <span class="text">Messages</span> 
                <span class="label label-important">5</span>
                <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
                <li><a class="sAdd" title="" href="#">new message</a></li>
                <li><a class="sInbox" title="" href="#">inbox</a></li>
                <li><a class="sOutbox" title="" href="#">outbox</a></li>
                <li><a class="sTrash" title="" href="#">trash</a></li>
            </ul>
        </li>
        <li class="">
            <a title="" href="#">
                <i class="icon icon-cog"></i>
                <span class="text">Settings</span>
            </a>
        </li>
        <li class="">
            <a title="" href="<c:url value="/admin/logout"/>">
                <i class="icon icon-share-alt"></i> 
                <span class="text">Logout</span>
            </a>
        </li>
    </ul>
</div>
<div id="search">
    <input type="text" placeholder="Search here..."/>
    <button type="submit" class="tip-left" title="Search"><i class="icon-search icon-white"></i></button>
</div>
<!--close-top-Header-menu-->

<div id="sidebar"> 
    <a href="#" class="visible-phone">
        <i class="icon icon-signal"></i> 
        Charts &amp; graphs
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
                <span class="label label-important">2</span>
            </a>
            <ul>
                <li>
                    <a href="<c:url value="/admin/sys-account/list"/>">
                        ${Lang['menu.system.account']}
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/admin/user-action/list"/>">
                        ${Lang['menu.system.logaction']}
                    </a>
                </li>
            </ul>
        </li>
    </ul>
</div>
