<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default no-border">
	    
	    <cnoj:tableTree smartResp="${smartResp }" headers="菜单名称,资源名称,状态,排序,图标" currentUri="${currentUri }" 
	    headerWidths="30%,30%,10%,10%,20%" addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }" 
	    refreshBtn="${refreshBtn }"  
	    />
	</div>
</div>