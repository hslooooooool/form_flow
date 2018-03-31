<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default">
	    <cnoj:tableAsyncTree smartResp="${smartResp }" headers="名称,机构代码,类型,序号,联系人,联系电话" currentUri="${currentUri }"  
	     headerWidths="30%,30%,10%,10%,10%,10%" asyncUrl="${asyncUrl }" customBtns="${customBtns }"
	     addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }"  refreshBtn="${refreshBtn }" 
	    />
	</div>
</div>
<script type="text/javascript">
	function beforeCheck(paramName,value) {
		alert(value);
		return false;
	}
</script>