<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default no-border">
	    <cnoj:table smartResp="${smartResp }" headers="名称,权限值,创建时间" isCheckbox="1" isRowSelected="1" currentUri="${currentUri }"  
	     stylesClass="text-center" addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }" refreshBtn="${refreshBtn }" 
	     page="${pageParam }" 
	    />
	</div>
</div>