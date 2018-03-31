<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
   <div class="panel panel-default">
	    <cnoj:table smartResp="${smartResp }" isCheckbox="1" isRowSelected="1" headers="名称,uri,状态,排序" 
	    headerWidths="40%,40%,10%,10%" stylesClass="text-center,text-center,text-center,text-center" currentUri="${currentUri }" 
	     addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }" refreshBtn="${refreshBtn }"  
	    />
	</div>
</div>