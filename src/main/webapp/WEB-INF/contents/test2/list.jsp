<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default">
	    <cnoj:table smartResp="${smartResp }" headers="姓名,年龄" currentUri="${currentUri }" 
	     isCheckbox="1" isRowSelected="1" 
	     addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }" refreshBtn="${refreshBtn }" page="${pageParam }"  
	    />
	</div>
</div>
