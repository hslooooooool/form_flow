<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/common-header.jsp"></jsp:include>
<style type="text/css">
    .version-content {padding: 0 5px;}
    .v-num,.v-update-time {padding: 0;}
    .v-header {color: #aaa;padding-bottom:5px; overflow: hidden;}
    .v-num {color: #FF9966;font-weight: 700;}
    .version-descr {
    	padding:8px 10px;
    	border: 1px solid #efefef;
    	background-color:#fff;
    	border-radius:4px;
    }
    .version-content .row {
    	padding:5px 10px;
    	margin-bottom: 10px;
    	background-color: #efefef;
    	border: 1px solid #ddd;
    	border-radius:4px;
    }
</style>
</head>
<body>
<div class="main">
	<div class="container">
	    <h3 class="text-center">最近10次更新记录</h3>
	    <div class="version-content">
		    <c:forEach var="version" items="${versions }">
				<div class="row">
					<div class="v-header">
				   		<div class="v-num col-sm-6">版本号：${version.version }</div>
				   		<div class="v-update-time col-sm-6 text-right">更新时间：${version.updateDate }</div>
				   	</div>
				   	<div class="version-descr">
					   	<p class="v-d-content">${version.descr }</p>
				   	</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
</body>
</html>