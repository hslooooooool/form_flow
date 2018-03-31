<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="<%=basePath%>">
    <title>${project.name }</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="-1">
    <meta name="description" content="">
    <meta name="author" content="">   
	<meta http-equiv="keywords" content="${project.name }">
	<meta http-equiv="description">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="renderer" content="webkit"><!-- 国产浏览器默认采用高速模式渲染页面 -->
	<!-- jquery -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/utils.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.mousewheel.js" type="text/javascript"></script>
	<!-- jQuery UI 插件 -->
	<link href="${pageContext.request.contextPath}/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/plugins/jquery-ui/jquery-ui.theme.min.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/plugins/jquery-ui/jquery-ui.structure.min.css" rel="stylesheet" />  
	<script src="${pageContext.request.contextPath}/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/plugins/jquery-ui/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
	
	<!-- bootstrap插件 -->
	<link href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet" /> 
    <script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- Bootstrap 开关（switch）控件 -->
    <link href="${pageContext.request.contextPath}/plugins/bootstrap-switch/css/bootstrapSwitch.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/plugins/bootstrap-switch/js/bootstrapSwitch.js" type="text/javascript"></script>
    <!-- Bootstrap iCheck 插件 -->
    <link href="${pageContext.request.contextPath}/plugins/icheck/skins/all.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/plugins/icheck/icheck.min.js" type="text/javascript"></script>
    
    <!-- Font-Awesome -->
	<link href="${pageContext.request.contextPath}/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" /> 