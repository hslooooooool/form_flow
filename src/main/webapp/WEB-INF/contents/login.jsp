<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="./base/include/common-header.jsp"></jsp:include>
<%
  SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy");
  String year = dateFormater.format(new Date());
%>
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
<!--[if lt IE 9]>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/bootstrap/js/html5shiv.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/bootstrap/js/respond.min.js"></script>
    <![endif]-->
    
    <!--[if lte IE 6]>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap-ie6.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/bootstrap/css/ie.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap-ie.js"></script>
    <![endif]-->
<script type="text/javascript">
$(document).ready(function(){
	var msg = '${msg}';
    if(msg != '') {
    	utils.showMsg(msg+"！");
    }
    initCookieValue();
    var screenW = window.screen.width;
    var screenH = window.screen.height;
    //window.screen.deviceXDPI
    $("#resolution").val(screenW+"x"+screenH);
    $("#screen-width").val(screenW);
    $("#screen-height").val(screenH);
   clickImgCode();
   listenerFocus();

	$("#login-form").submit(function(){
		var userName = $("#user-name").val();
		var password = $("#pass-word").val();
		var code = $("#code").val();
		if(utils.trim(userName)=='') {
			utils.showMsg("请输入用户名！");
			$("#user-name").focus();
			return false;
		} else if(utils.trim(password) == '') {
			utils.showMsg("请输入密码！");
			$("#pass-word").focus();
			return false;
		} else if(utils.trim(code) == '') {
			utils.showMsg("请输入验证码！");
			$("#code").focus();
			return false;
		}
        if($("#remember-me").prop("checked")) {
            $.cookie('rmbuser', true, {expires:7});
            $.cookie('username',userName, {expires:7});
            $.cookie('password',password, {expires:7});
        } else {
            $.cookie('rmbuser',false, {expires:-1});
            $.cookie('username','', {expires:-1});
            $.cookie('password','',{expires:-1});
        }
		utils.waitLoading("正在登录...");
		return true;
	});
});

function clickImgCode() {
	  $("#imgCode").click(function(){
		  var num = parseInt(Math.random()*10000);
		  $(this).attr("src","captcha?num="+num);
	  });
}

function listenerFocus() {
	$("input[type=text],input[type=password]").focus(function(){
		$(".prompt-error").html("");
		var tag = $(this).attr("id");
		if(tag == 'code') {
			$(this).removeClass("input-text-code");
			$(this).addClass("input-text-code-focus ");
		} else {
			$(this).removeClass("input-text");
			$(this).addClass("input-text-focus");
		}
	}).blur(function(){
		var tag = $(this).attr("id");
		if(tag == 'code') {
			$(this).removeClass("input-text-code-focus");
			$(this).addClass("input-text-code");
		} else {
			$(this).removeClass("input-text-focus");
			$(this).addClass("input-text");
		}
	});
}

function initCookieValue() {
    var username = $.cookie('username');
    var rmbuser = $.cookie('rmbuser');
    var password = $.cookie('password');
    if(utils.isNotEmpty(username)) {
        $("#user-name").val(username);
    }
    if(utils.isNotEmpty(password)) {
        $("#pass-word").val(password);
    }
    if(rmbuser) {
        $("#remember-me").prop("checked",true);
    }
}
</script>
</head>
  <body>
      <div class="full-header">
         <div class="navbar navbar-default navbar-static-top m-b-0" role="navigation">
		        <div class="navbar-header">
		          <strong><a class="navbar-brand" href="index">
		          <img class="text-inline-block" alt="logo" src="${pageContext.request.contextPath}/images/logo.png" />
		          <h4 class="text-inline-block">${project.name }</h4></a></strong>
		        </div>
		        <div class="navbar-collapse collapse">
		             
		        </div>
		</div><!-- navbar-default -->
    </div>
	<div class="wrap">
	   <div class="login">
        <div class="container">
            <div class="login-main">
                <div class="login-main-content">
                    <div class="login-title"><i class="fa fa-user fa-lg"></i> 用户登录</div>
	                <div class="login-content">
	                    <form action="login" method="post" id="login-form">
                             <input type="hidden" name="resolution" id="resolution" />
                             <input type="hidden" name="screenWidth" id="screen-width" />
                             <input type="hidden" name="screenHeight" id="screen-height" />
							 <div class="login-input">
							    <label>用户名：</label>
								<input type="text" id="user-name" placeholder="请输入用户名" class="input-text" name="userName" value="${userName }" />
							 </div>
							 <div class="login-input">
							     <label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
								 <input type="password" id="pass-word" placeholder="请输入密码" name="password" class="input-text" value="${password }">
							 </div>
							 <div class="login-input">
								<label>验证码：</label>
								<input type="text" id="code" placeholder="请输入验证码" name="code" class="input-text-code" value="${code }" >
								<img alt="验证码" title="看不清，请点击刷新" id="imgCode" src="captcha" />
							 </div>

                            <div class="login-input" style="padding-bottom: 5px;padding-left: 50px;">
                                <div class="checkbox m-t-5 m-b-5" style="font-size: 12px;">
                                    <label>
                                        <input type="checkbox" id="remember-me"> 记住用户名和密码
                                    </label>
                                </div>
                            </div>

							 <div class="login-btn-wrap">
								<div style="width: 100px;">
									<button type="submit" class="login-btn">登录</button>
								</div>
							 </div>
						  </form>
	                   </div>
	               </div>
	           </div>
            </div><!-- container -->
        </div>
    </div><!-- wrap -->
	
	<div class="footer clear">
	   <div class="container text-center">
            <p>${project.copyright }</p>
	        <p>${project.contactInfo }</p>
	   </div>
	</div>
</body>
</html>