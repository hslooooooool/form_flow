<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/flow.simple.tree.js"></script>
<div class="wrap-content">
	<div class="panel panel-default">
	    <div class="panel-body p-0 p-t-2 body-bg borer-top">
	        <div class="panel-tabs-wrap" id="order-list-tabs">
			<div class="panel-tabs-tab">
				<ul class="nav nav-tabs" role="tablist">
				   <li class="active"><a href="#process-order-tab" role="presentation" data-toggle="tab"> 正在处理</a></li>
				   <li><a href="#process-hist-order-tab" role="presentation" data-toggle="tab"> 已结束</a></li>
				</ul>
		  	</div>
		  	<div class="tab-content panel-tab-content bg-color-white p-0">
				<div role="tabpanel" class="tab-pane active" id="process-order-tab">
				    <div class="cnoj-load-url" data-uri="process/mgr/orderList" ></div>
				</div>
				<div role="tabpanel" class="tab-pane" id="process-hist-order-tab">
				     <div class="cnoj-load-url" data-uri="process/mgr/histOrderList" ></div>
				</div>
			</div>
		</div>
	    </div>
	</div>
<script type="text/javascript">
$(function(){
	var isResize = false;
    $("#order-list-tabs").find('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var id = $(e.target).attr("href");
        limitHeightListener($(id), isResize);
    });
    $(window).resize(function(){
        isResize = true; 
     });
});
</script>
</div>
