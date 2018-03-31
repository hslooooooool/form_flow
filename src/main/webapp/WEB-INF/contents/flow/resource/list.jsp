<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default no-border">
		<!-- table -->
	    <cnoj:table smartResp="${smartResp }" headers="名称,URI,状态,创建时间" 
		headerWidths="30%,40%,15%,15%" isCheckbox="1" isRowSelected="1" currentUri="${currentUri }" 
		  addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }" refreshBtn="${refreshBtn }" 
		  page="${pageParam }" 
		/>
	</div>
</div>