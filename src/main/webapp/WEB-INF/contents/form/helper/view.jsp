<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="wrap-content-dialog">
     <c:choose>
	 		<c:when test="${smartResp.result == '1' }">
	 			<div class="form-helper-view">${smartResp.data.content }</div>
	 		</c:when>
	 		<c:otherwise>
	 			<h4 class="fail">获取表单帮助信息失败</h4>
	 		</c:otherwise>
	 	</c:choose>
</div><!-- wrap-content-dialog -->