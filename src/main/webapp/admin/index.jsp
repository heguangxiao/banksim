<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a href="#" title="Go to Home" class="tip-bottom">
                <i class="icon-home"></i>
                Trang chủ
            </a> 
        </div>
    </div>
    <div class="container-fluid" style="align-content: center;">        
        <div class="center"> 
            <strong></strong>
            <br/>
            <br/>
            <br/>
            <br/>
        </div>
    </div>
    <div class="container-fluid">
        <div class="widget-box widget-plain">
        </div>
        <div class="row-fluid" style="visibility: hidden;">
            <div class="span12">
                <div class="widget-box">
                    <div class="widget-title"> <span class="icon"> <i class="icon-signal"></i> </span>
                        <h5>Bar chart</h5>
                    </div>
                    <div>
                        <div class="bars"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row-fluid" style="visibility: hidden;">
            <div class="span6">
                <div class="widget-box">
                    <div class="widget-title"> <span class="icon"> <i class="icon-signal"></i> </span>
                        <h5>Line chart</h5>
                    </div>
                    <div class="widget-content">
                        <div class="chart"></div>
                    </div>
                </div>
            </div>
            <div class="span6">
                <div class="widget-box">
                    <div class="widget-title"> <span class="icon"> <i class="icon-signal"></i> </span>
                        <h5>Pie chart</h5>
                    </div>
                    <div>
                        <div class="pie"></div>
                    </div>
                </div>
            </div>
        </div>                    
        
    </div>
</div>
<script src="<c:url value='/admin/js/jquery.min.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.alerts.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.flot.min.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.flot.pie.min.js'/>"></script>
<script src="<c:url value='/admin/js/jquery.flot.resize.min.js'/>"></script>
<script src="<c:url value='/admin/js/maruti.js'/>"></script>
<script src="<c:url value='/admin/js/maruti.charts.js'/>"></script>
<script src="<c:url value='/admin/js/maruti.dashboard.js'/>"></script>