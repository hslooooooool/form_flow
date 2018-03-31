<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default no-border">
	    <cnoj:table smartResp="${smartResp }" headers="名称,URI,显示URI,状态,创建用户,创建时间" currentUri="${currentUri }" 
	     isCheckbox="1" isRowSelected="1" 
	     addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }" refreshBtn="${refreshBtn }" page="${pageParam }"  
	    />
	</div>
</div>
