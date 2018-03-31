<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../base/include/common-header.jsp" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/bootstrap/js/html5shiv.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/bootstrap/js/respond.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/jquery-ui-bootstrap/css/custom-theme/jquery.ui.1.10.0.ie.css"/>
    <![endif]-->
    
    <!--[if lte IE 6]>
	  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap-ie6.css">
	  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/bootstrap/css/ie.css">
      <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap-ie.js"></script>
     <![endif]-->
</head>
  <body>
  	<div class="container-fluid">
		<div class="wrap-content">
	     <c:choose>
		 		<c:when test="${smartResp.result == '1' }">
		 			<div class="form-helper-view">${smartResp.data.content }</div>
		 		</c:when>
		 		<c:otherwise>
		 			<h4 class="fail">获取表单帮助信息失败</h4>
		 		</c:otherwise>
		 	</c:choose>
		</div><!-- wrap-content -->
	</div>
 </body>
</html>