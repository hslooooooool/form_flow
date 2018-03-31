<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>测试页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js" type="text/javascript"></script>
	
	<!-- bootstrap插件 -->
	<link href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

    <!-- 上传文件插件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/jqueryFileUpload/css/jquery.fileupload.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/jqueryFileUpload/css/jquery.fileupload-ui.css" />

<script src="${pageContext.request.contextPath}/plugins/jqueryFileUpload/js/vendor/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jqueryFileUpload/js/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jqueryFileUpload/js/jquery.fileupload.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jqueryFileUpload/js/jquery.fileupload-validate.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jqueryFileUpload/js/locale.js"></script>

<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-fileupload-util.css" />
<script src="${pageContext.request.contextPath}/js/jquery-fileupload-util.js"></script>
    <!--[if (gte IE 8)&(lt IE 10)]>
<script src="${pageContext.request.contextPath}/plugins/jqueryFileUpload/js/cors/jquery.xdr-transport.js"></script>
<![endif]-->
	<!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/bootstrap/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap/respond.min.js"></script>
    <![endif]-->
	<style type="text/css">
	  #test-main {
	     height: 500px;
	     overflow: auto;
	  }
	  .test {
	     height: 500px;
	     padding: 50px;
	  }
	</style>
  </head>
  
  <body>
    <div id="test-main">
    <div class="test">
       <h1>测试内容</h1>
       <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">弹出窗口</button>

		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">测试窗口</h4>
					</div>
					<div class="modal-body">
					     <p>哈哈哈！</p>
					     <p>爱情吻过不留痕</p>
					     <p> 你那温柔的目光 </p>
					     
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<script type="text/javascript">
	
	$("#upload-m-file").jqueryFileUploadUtil({
        uri:'uploadAtt/uploadAtt?returnType=json',
        //formData:$("#flow-att-form").serializeArray(),
        acceptFileTypes:'/gif|jpg|png|txt|pdf|ppt|pptx|doc|docx|xls|xlsx|zip|rar$/i',
        maxFileSize:104857600,//100M
        closeAfter:function(){
           alert("上传成功");
        }
    });
	</script>
  </body>
</html>
