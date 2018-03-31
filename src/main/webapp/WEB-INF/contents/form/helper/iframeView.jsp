<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	Object showUrlObj = request.getAttribute("url");
	if(null != showUrlObj) {
		String showUrl = showUrlObj.toString();
		showUrl = URLDecoder.decode(showUrl,"UTF-8");
		pageContext.setAttribute("showUrl", showUrl);
	}
%>
<div class="wrap-content-dialog">
	<c:choose>
		<c:when test="${empty url }">
			<iframe width="100%" height="100%" id="view-iframe"  src="form/helper/blank?id=${id }" frameborder="no" border="0" scrolling="no"></iframe>
		</c:when>
		<c:otherwise>
			<iframe width="100%" height="100%" id="view-iframe" src="${showUrl }" frameborder="no" border="0" scrolling="no"></iframe>
		</c:otherwise>
	</c:choose>
<script type="text/javascript">
	$("#view-iframe").load(function() {
		var iframeContentHeight = $(this).contents().find("body").height();
		$(this).height(iframeContentHeight);
	});
</script>
</div><!-- wrap-content-dialog -->