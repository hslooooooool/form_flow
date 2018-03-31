<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default no-border">
	    <cnoj:tableTree smartResp="${smartResp }" headers="名称,机构代码,类型,序号,联系人,联系电话"  isExpand="1" currentUri="${currentUri }"  
	     headerWidths="30%,30%,10%,10%,10%,10%" 
	     addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }" refreshBtn="${refreshBtn }"  
	    />
	</div>
</div>