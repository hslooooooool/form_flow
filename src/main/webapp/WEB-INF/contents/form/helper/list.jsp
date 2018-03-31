<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default no-border">
	    <cnoj:table smartResp="${smartResp }" headers="标题,状态,创建人,创建时间" isCheckbox="1" isRowSelected="1" currentUri="${currentUri }" 
	    alinks="${alinks }" page="${pageParam }" addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }" refreshBtn="${refreshBtn }"  
	    />
	</div>
	<script type="text/javascript">
		window.UEDITOR_HOME_URL = "${pageContext.request.contextPath}/plugins/ueditor/";
	</script>
</div>