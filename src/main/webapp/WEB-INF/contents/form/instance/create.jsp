<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <jsp:include page="../../base/include/common-header.jsp" />
    <!-- 封装 bootstrap 弹出对话框 -->  
    <link href="${pageContext.request.contextPath}/css/bootstrap-dialog.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/js/bootstrap-dialog.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-dialog-util.js" type="text/javascript"></script>

    <!-- 日期插件  -->
    <link href="${pageContext.request.contextPath}/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" type="text/javascript" ></script>
    <script src="${pageContext.request.contextPath}/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8" type="text/javascript" ></script>

    <!-- zTree插件 -->
    <link href="${pageContext.request.contextPath}/plugins/zTree/css/zTreeStyle.css" type="text/css" rel="stylesheet"  />
    <script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.core-3.5.min.js" type="text/javascript" ></script>
    <script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.excheck-3.5.min.js" type="text/javascript" ></script>
    <script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.exedit-3.5.min.js" type="text/javascript" ></script>
    <script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.exhide-3.5.min.js" type="text/javascript" ></script>
    
    <!-- 上传文件插件 --> 
    <link href="${pageContext.request.contextPath}/plugins/jqueryFileUpload/css/jquery.fileupload.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/plugins/jqueryFileUpload/css/jquery.fileupload-ui.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/plugins/jqueryFileUpload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/jqueryFileUpload/js/jquery.iframe-transport.js" type="text/javascript" ></script>
    <script src="${pageContext.request.contextPath}/plugins/jqueryFileUpload/js/jquery.fileupload.js" type="text/javascript" ></script>
    
    <!-- 打印 -->
    <link href="${pageContext.request.contextPath}/plugins/printArea/css/jquery.printarea.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/plugins/printArea/js/jquery.printarea.js" type="text/javascript" ></script>
    <link href="${pageContext.request.contextPath}/css/print.css" rel="stylesheet" />
    
    <!--ueditor编辑器  -->
    <script type="text/javascript" charset="utf-8" src="${ctx}/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/plugins/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
    
    <!-- 自定义样式 -->
    <link href="${pageContext.request.contextPath}/css/ztree-rewrite.css" type="text/css" rel="stylesheet"  />
    <link href="${pageContext.request.contextPath}/css/bootstrap-extend.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/bootstrap-rewrite.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/jquery-ui-rewrite.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/cnoj-ui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/file-upload-util.css" rel="stylesheet" />
    <!-- 与表单相关的样式 -->
    <link href="${ctx}/plugins/form/css/form.css" rel="stylesheet" />
    
    <!-- 自定义js -->
    <script src="${pageContext.request.contextPath}/js/check-card-no.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/check-form.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/ztree-util.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/input-select.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/auto-complete.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/table-async-tree.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-fileupload-util.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/cnoj.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/cnoj.event.listener.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/adjust-ie-height.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.autotextarea.js" type="text/javascript" charset="UTF-8"></script>
    <script src="${ctx}/plugins/form/js/form.prop.listener.js" type="text/javascript"></script>
    <script src="${ctx}/plugins/form/js/init.form.js" type="text/javascript"></script>
    
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/bootstrap/js/html5shiv.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/bootstrap/js/respond.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/jquery-ui-bootstrap/css/custom-theme/jquery.ui.1.10.0.ie.css"/>
    <![endif]-->
    
    <!--[if (gte IE 8)&(lt IE 10)]>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/jqueryFileUpload/js/cors/jquery.xdr-transport.js"></script>
    <![endif]-->
    
    <!--[if IE 7]>
       <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/jquery-ui-bootstrap/assets/css/font-awesome-ie7.min.css">
    <![endif]-->
    
    <!--[if lte IE 6]>
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap-ie6.css">
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/bootstrap/css/ie.css">
      <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap-ie.js"></script>
     <![endif]-->
</head>
<div class="wrap-content">
   <div class="form-header">
       <form id="create-form-param">
           <input type="hidden" id="form-data-id" name="formDataId" value="${formDataId}" />
           <input type="hidden" id="form-id" name="formId" value="${formId}" />
       </form>
       <div class="form-header-btn">
			<div class="navbar-nar-right m-r-5 m-t-1">
			    <button type="button" class="btn btn-primary btn-sm" id="form-submit" data-uri="form/instance/submit">
			    <i class="fa fa-floppy-o" aria-hidden="true"></i> 提交 </button>
			</div>
        </div>
   </div>
   <div class="form-contents">
       <div class="panel-tabs-wrap">
			<div class="panel-heading p-0">
				<div class="panel-tabs-tab">
					<ul class="nav nav-tabs ui-state-default" role="tablist">
						<li class="active"><a href="#form-content-tab" role="presentation" data-toggle="tab"> 表单信息</a></li>
						<li><a href="#form-att-tab" id="form-att-tab-a" role="presentation" data-toggle="tab"> 附件 <span class="badge"></span></a></li>
					</ul>
				</div>
			</div>
			<div class="panel-body p-0">
				<div id="form-panel-contents" class="tab-content panel-tab-content bg-color-white cnoj-auto-limit-height">
					<div role="tabpanel" class="tab-pane active" id="form-content-tab">
						<div class="form-prop">
					       <form id="create-form" method="post" data-relate-arg-form="#create-form-param" enctype="multipart/form-data">
					           ${smartResp.data.parseHtml}
					       </form>
                           <iframe class="hidden" id="handle-form-iframe" name="handle-form-iframe" frameborder=0 width=0 height=0></iframe>
					   </div>
					</div>
					<div role="tabpanel" class="tab-pane p-t-10" id="form-att-tab">
						<div class="cnoj-load-url" data-uri="form/attachment/list?formId=${formId }&formDataId=${formDataId}" ></div>
					</div>
				</div>
			</div>
	  </div><!-- panel-tabs-wrap -->
   </div>
</div>

<script type="text/javascript">
    utils.isIframe = true;
    var initForm = $("#create-form").initForm({
        username:'${userInfo.fullName}',
        deptName: '${userInfo.deptName}',
        formData:'${output}',
        isIframe: utils.isIframe,
        initDataAfter: function(){
            formRequireListener();
            printListener();
            inputPluginEvent();
            hrefListener();
            limitHeightListener();
            loadUrlListener();
        },
        callback:function(element){
            var self = this;
            $("#form-submit").click(function(){
                var $this = $(this);
                $this.prop("disabled",true);
                if($(element).validateForm()) {
                    var url = $this.data("uri");
                    if(utils.isEmpty(url)) {
                        utils.showMsg("提交地址为空...");
                        $this.prop("disabled",false);
                        return false;
                    }
                    var formParam = $("#create-form-param").serialize();
                    self.submitForm(url, formParam, function(){
                        $this.prop("disabled",false);
                    });
                } else {
                    $this.prop("disabled",false);
                }
            });
        }
    });
    initForm.init();
    $(".form-contents").resize(function(){
       setTimeout(function() {
          limitHeightListener();
       }, 200);
    });
</script>