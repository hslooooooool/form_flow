<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="../include/common-header.jsp" />
<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/cnoj-ui.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" />
</head>
  
  <body>
     <div class="text-center" style="padding-top: 50px;">
     	<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i> 正在进行自动登录，请稍后...
     </div>
     <iframe id="sso-request-iframe" class="hidden" src="${serverUrl }"></iframe>
     <script type="text/javascript">
        var interval = null;
     	$(function(){
     		 $("#sso-request-iframe").load(function() {
     			//var $that = $(this);
     			interval = setInterval(function() {
     				try {
     					var result = $("#sso-request-iframe").contents().text();
            	    	if(utils.isNotEmpty(result)) {
            	    		clearInterval(interval);
            	    		var output = $.parseJSON(result);
            	    		if(output.result == '1') {
            	    			utils.showMsg("登录成功");
            	    			var uri = output.data;
            	    			if(utils.isNotEmpty(uri)) {
            	    				location.href = uri;
            	    			} else {
            	    				location.href = "index";
            	    			}
            	    		} else {
            	    			utils.showMsg("自动登录失败");
            	    			location.href = "login";
            	    		}
            	    	}/* else {
            	    		utils.showMsg("自动登录失败");
            	    		location.href = "login";
            	    	}*/
     				} catch(error) {
     					
     				}
     			 }, 1000);
     		 });
     		 setTimeout(function() {
     			clearInterval(interval);
     			utils.showMsg("自动登录失败");
     			location.href = "login";
     		 }, 5000);
     	});
     </script>
  </body>
</html>
