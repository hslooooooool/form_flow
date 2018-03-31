<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="wrap-content-dialog">
   <div class="version-view">
       <c:choose>
       		<c:when test="${smartResp.result == '1' }">
       			<div class="v-header row">
		   			<div class="v-num col-sm-6">版本号：${smartResp.data.version }</div>
		   			<div class="v-update-time col-sm-6 text-right">更新时间：${smartResp.data.updateDate }</div>
		   		</div>
		   		<div class="version-descr">
			   		<p class="v-d-header">更新内容：</p>
			   		<p class="v-d-content">${smartResp.data.descr }</p>
			   		<p class="text-right m-b-0"><a target="__blank" href="version/updateLog">查看更多更新日志</a></p>
		   		</div>
       		</c:when>
       		<c:otherwise>
       			<div class="version-descr">
			   		<p class="v-d-header">更新内容：</p>
			   		<p class="v-d-content">没有更新日志</p>
		   		</div>
       		</c:otherwise>
       </c:choose>
   		
   </div>
</div><!-- wrap-content-dialog -->