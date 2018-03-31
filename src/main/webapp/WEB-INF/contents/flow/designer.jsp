<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="wrap-content">
	<iframe id="designer-iframe" width="100%" frameborder="0" src="showPage/flow_designerIframe?processId=${id}"></iframe>
</div>

<script type="text/javascript">
$(function() {
	var mainH = getMainHeight();
	$("#designer-iframe").height(mainH-40);
	$("#designer-iframe").load(function(){
		var iframe = document.getElementById("designer-iframe");
		iframe.contentWindow.initFlowDesigner("${process}");
		iframe.focus();
	});
});

function saveModel(data, deployType) {
	utils.waitLoading("正在处理流程数据...");
	$.ajax({
		type:'POST',
		url:"${ctx}/flow/deploy",
		data:"model=" + data + "&id=${id}&deployType="+deployType,
		async: false,
		globle:false,
		error: function(){
			utils.closeWaitLoading();
			BootstrapDialogUtil.warningAlert('数据处理错误！');
			return false;
		},
		success: function(data){
			utils.closeWaitLoading();
			utils.showMsg(data.msg);
			if(data.result == utils.YES_OR_NO.YES) {
				closeActivedTab();
				openTab("流程列表", 'flow/list', true);
			}
		}
	});
}
</script>	
