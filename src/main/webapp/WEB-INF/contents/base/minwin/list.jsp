<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default">
	    <cnoj:table smartResp="${smartResp }" headers="名称,uri,更多uri,高度,标题,状态,序号,创建时间" isCheckbox="1" isRowSelected="1" currentUri="${currentUri }"  
	     stylesClass="text-center" addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }" refreshBtn="${refreshBtn }" 
	     page="${pageParam }" 
	    />
	</div>
</div>