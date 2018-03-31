<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link href="${ctx}/plugins/form/css/form.css" rel="stylesheet" />
<link href="${ctx}/plugins/flow/css/flow.css" rel="stylesheet" />

<div class="wrap-content m-n5">
   <div class="flow-form-contents">
       <div class="panel-tabs-wrap">
			<div class="panel-heading p-0">
				<div class="panel-tabs-tab">
					<ul class="nav nav-tabs ui-state-default" role="tablist">
						<li class="active"><a href="#view-process-form-tab" role="presentation" data-toggle="tab"> 表单信息</a></li>
						<li><a href="#view-process-att-tab" id="view-process-att-tab-a" role="presentation" data-toggle="tab"> 附件</a></li>
						<li><a href="#view-process-record-tab" role="presentation" data-toggle="tab"> 流转记录</a></li>
					</ul>
				</div>
			</div>
			<div class="panel-body p-0">
				<div id="view-flow-form-panel-contents" class="tab-content panel-tab-content bg-color-white" style="overflow: auto;">
					<div role="tabpanel" class="tab-pane active" id="view-process-form-tab">
					    <c:if test="${isPrint==1 }">
					        <div class="pull-right">
					    	  <button class="btn btn-default btn-sm cnoj-print" data-target="#view-process-handle-form"><i class="fa fa-print" aria-hidden="true"></i> 打印</button>
					    	</div>
					    </c:if>
						<div class="form-prop view-form-prop">
					       <form id="view-process-handle-form" method="post">
					           ${smartResp.data.parseHtml}
					       </form>
					   </div>
					</div>
					<div role="tabpanel" class="tab-pane" id="view-process-att-tab">
						<div class="cnoj-load-url" data-uri="process/attachment/list?processId=${processId}&orderId=${orderId}&taskId=${taskId}&taskKey=${taskKey}&formId=${formId }&isAtt=${isAtt}&isView=1" ></div>
					</div>
					<div role="tabpanel" class="tab-pane" id="view-process-record-tab">
						<div class="cnoj-load-url" data-uri="process/processHandleInfo?orderId=${orderId }" ></div>
					</div>
				</div>
			</div>
	  </div><!-- panel-tabs-wrap -->
   </div>
</div>
<script src="${ctx}/plugins/flow/js/flow.form.js" type="text/javascript"></script>
<script type="text/javascript">
  $(function(){
	  $("#view-process-handle-form").flowForm({
	    	formFieldNames:'${taskModel.formPropIds}',
	    	formData:'${output}'
	    });
	   var mainContentH = $(window).height() - 75;
	   var flowFormContentH = mainContentH - 40 - 35;
	   $("#view-flow-form-panel-contents").height(flowFormContentH);
  });
</script>