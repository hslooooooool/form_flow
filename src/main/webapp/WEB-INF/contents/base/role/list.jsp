<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default no-border">
	    <cnoj:table smartResp="${smartResp }" headers="名称,状态,描述,创建人,创建时间" 
	    headerWidths="40%,8%,28%,10%,13%"  isCheckbox="1" isRowSelected="1" currentUri="${currentUri }" 
	    alinks="${alinks }" page="${pageParam }"
	     addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }" refreshBtn="${refreshBtn }"  
	    />
	</div>
</div>