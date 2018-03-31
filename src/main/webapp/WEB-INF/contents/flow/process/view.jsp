<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link href="${pageContext.request.contextPath}/plugins/flow/css/snaker.css" rel="stylesheet" />
<!-- 流程设计 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/org-ztree.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/raphael-min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/snaker.designer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/snaker.model.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/snaker.editors.js"></script>
    
<div class="wrap-content-dialog">
<div id="snakerflow-view"></div>
</div><!-- wrap-content-dialog -->
<script type="text/javascript">
	$(function() { 
		setProcessName("${processName}");
		var json="${process }";
		var state = "${state}";//节点
		var model = null;
		if(json) {
			model=eval("(" + json + ")");
		} else {
			model="";
		}
		if(state) {
			state = eval("(" + state + ")");
		} else {
		  state = '';
		}
		var h = $(window).height()-120;
		$("#snakerflow-view").height(h);
		$('#snakerflow-view').snakerflow($.extend(true,{
			basePath : "${ctx}/plugins/flow/",
			ctxPath : "${ctx}",
			restore : model,
			editable : false
			},state));
		
		function setProcessName(processName) {
			$("#snakerflow-view").parents(".modal-dialog:eq(0)").find(".bootstrap-dialog-title").html("查看【"+processName+"】流程图");
		}
	});
  </script>