<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link href="${ctx}/plugins/form/css/form.css" rel="stylesheet" />
<div class="wrap-content-dialog">
   <div class="form-prop view-form-prop" style="overflow: auto">
	   <c:if test="${smartResp.result == 1}">
	       ${smartResp.data.parseHtml }
	   </c:if>
   </div>
</div><!-- wrap-content-dialog -->
<script type="text/javascript">
  $(function(){
	   var mainContentH = $(window).height() - 70;
	   var flowFormContentH = mainContentH - 50;
	   $(".form-prop").height(flowFormContentH);
  });
</script>