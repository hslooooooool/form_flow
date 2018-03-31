<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link href="${ctx}/plugins/form/css/form.css" rel="stylesheet" />
<link href="${ctx}/plugins/flow/css/flow.css" rel="stylesheet" />

<div class="wrap-content m-n5">
   <div class="flow-form-contents">
   		<form id="edit-flow-process-form">
           <input type="hidden" name="formId" value="${formId}" />
           <input type="hidden" name="formDataId" value="${formDataId}" />
           <input type="hidden" id="process-id" name="processId" value="${processId }" />
           <input type="hidden" id="order-id" name="orderId" value="${orderId }" />
       </form>
       <div class="panel-tabs-wrap">
			<div class="panel-heading p-0">
				<div class="panel-tabs-tab">
					<ul class="nav nav-tabs ui-state-default" role="tablist">
						<li class="active"><a href="#edit-process-form-tab" role="presentation" data-toggle="tab"> 表单信息</a></li>
						<li><a href="#edit-process-att-tab" id="edit-process-att-tab-a" role="presentation" data-toggle="tab"> 附件</a></li>
						<li><a href="#edit-process-record-tab" role="presentation" data-toggle="tab"> 流转记录</a></li>
					</ul>
				</div>
			</div>
			<div class="panel-body p-0">
				<div id="edit-flow-form-panel-contents" class="tab-content panel-tab-content bg-color-white" style="overflow: auto;">
					<div role="tabpanel" class="tab-pane active" id="edit-process-form-tab">
						<div class="form-prop">
						   <div class="update-btn-top text-right pull-right">
					        <button type="button" class="edit-update-form btn btn-info btn-sm"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
					       </div>
					       <form id="edit-process-handle-form" method="post" data-relate-arg-form="#edit-flow-process-form" target="edit-handle-form-iframe" action="process/updateForm">
					           ${smartResp.data.parseHtml}
					       </form>
                           <iframe class="hidden" id="edit-handle-form-iframe" name="edit-handle-form-iframe" frameborder=0 width=0 height=0></iframe>
					       <div class="update-btn-bottom text-center">
					        <button type="button" class="edit-update-form btn btn-primary"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
					       </div>
					   </div>
					</div>
				    <div role="tabpanel" class="tab-pane" id="edit-process-att-tab">
						<div class="cnoj-load-url" data-uri="process/attachment/list?processId=${processId}&orderId=${orderId}&formId=${formId }&isView=2" ></div>
					</div>
					<div role="tabpanel" class="tab-pane" id="edit-process-record-tab">
						<div class="cnoj-load-url" data-uri="process/processHandleInfo?orderId=${orderId }" ></div>
					</div>
				</div>
			</div>
	  </div><!-- panel-tabs-wrap -->
   </div>
</div>
<script type="text/javascript">
    if(typeof(UE) == 'undefined') {
        var $wrap = $("#edit-flow-process-form").parent().parent();
        $wrap.append('<script type="text/javascript" charset="utf-8" src="${ctx}/plugins/ueditor/ueditor.config.js"><\/script>');
        $wrap.append('<script type="text/javascript" charset="utf-8" src="${ctx}/plugins/ueditor/ueditor.all.js"><\/script>');
        $wrap.append('<script type="text/javascript" charset="utf-8" src="${ctx}/plugins/ueditor/lang/zh-cn/zh-cn.js"><\/script>');
    }
</script>
<script src="${ctx}/plugins/flow/js/flow.form.edit.js" type="text/javascript"></script>
<script type="text/javascript">
  $(function(){
	  $("#edit-process-handle-form").flowForm({
	    	formData:'${output}',
	    	callback: function(){
	            inputPluginEvent();
	        }
	    });
	  var mainContentH = $(window).height() - 75;
	  var flowFormContentH = mainContentH - 40 - 35;
	  $("#edit-flow-form-panel-contents").height(flowFormContentH);
	  richtextListener();
  });
</script>