<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<jsp:include page="../base/include/common-header.jsp" />
<!-- 封装 bootstrap 弹出对话框 -->  
<link href="${pageContext.request.contextPath}/css/bootstrap-dialog.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/js/bootstrap-dialog.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-dialog-util.js" type="text/javascript"></script>

	<!-- zTree插件 -->
	<link href="${pageContext.request.contextPath}/plugins/zTree/css/zTreeStyle.css" type="text/css" rel="stylesheet"  />
	<script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.core-3.5.min.js" type="text/javascript" ></script>
	<script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.excheck-3.5.min.js" type="text/javascript" ></script>
	<script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.exedit-3.5.min.js" type="text/javascript" ></script>
	<script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.exhide-3.5.min.js" type="text/javascript" ></script>
	
	<!-- 自定义样式 -->
	<link href="${pageContext.request.contextPath}/css/ztree-rewrite.css" type="text/css" rel="stylesheet"  />
	<link href="${pageContext.request.contextPath}/css/bootstrap-extend.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/bootstrap-rewrite.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/jquery-ui-rewrite.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/cnoj-ui.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" />
	<!-- 与流程相关的样式 -->
	<link href="${pageContext.request.contextPath}/plugins/flow/css/flow.css" rel="stylesheet" />
	
	<!-- 自定义js -->
	<script src="${pageContext.request.contextPath}/js/check-card-no.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/check-form.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/ztree-util.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/input-select.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/auto-complete.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/table-async-tree.js" type="text/javascript"></script>
	
	<script src="${pageContext.request.contextPath}/js/cnoj.js" type="text/javascript"></script>
	
	<script src="${pageContext.request.contextPath}/js/cnoj.event.listener.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/adjust-ie-height.js" type="text/javascript"></script>
	
	<link href="${pageContext.request.contextPath}/plugins/flow/css/snaker.css" rel="stylesheet" />
	<!-- 流程设计 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/org-ztree.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/raphael-min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/snaker.designer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/snaker.model.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/snaker.editors.js"></script>
    
    <!--  
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/left-menu.js"></script>
   -->
   <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/flow.designer.js"></script>
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
  <body>
    <div class="wrap-content">
	<!-- 工具栏 -->
   <div id="toolbox">
       <div class="panel-group" id="toolbox-accordion" role="tablist" aria-multiselectable="true">
	   <div class="panel panel-primary">
	      <div class="panel-heading toolbox-handle" role="tab" id="toolbox-handing">
			<h4 class="panel-title">
				<a role="button" data-toggle="collapse" data-parent="#toolbox-accordion" href="#collapse-toolbox" aria-expanded="true" aria-controls="collapse-toolbox">
				    <i class="fa fa-wrench" aria-hidden="true"></i> 工具集 <div class="pull-right" title="隐藏属性面板"><i class="fa fa-caret-up"></i></div>
				</a>
			</h4>
		   </div><!-- end panel-heading -->
		 <div id="collapse-toolbox" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="toolbox-handing">
      	   <div class="panel-body" id="toolbox-content">
			   <div class="list-group deploy-btns">
			        <c:choose>
			           <c:when test="${empty processId }">
			              <a href="javascript:void(0)" class="list-group-item node" id="save">
					        <i class="fa fa-floppy-o" aria-hidden="true"></i> 流程部署
					      </a>
			           </c:when>
			           <c:otherwise>
			              <a href="javascript:void(0)" title="该按钮会部署一个新版流程，只有新的流程，才会按新的流转；如果您想修改的流程立即生效，请选择“覆盖部署”按钮！" class="list-group-item node" id="save">
				       		<i class="fa fa-floppy-o" aria-hidden="true"></i> 升级部署
					     </a>
					     <a href="javascript:void(0)" title="该按钮，会覆盖以前老的流程，如果您是想升级流程（以前老的流程按老的流转，新的流程按新的流转），请选择“升级部署”按钮！" class="list-group-item node" id="update">
					       <i class="fa fa-floppy-o" aria-hidden="true"></i> 覆盖部署
					     </a>
			           </c:otherwise>
			        </c:choose>
				    <a href="javascript:void(0)" class="list-group-item node" id="export">
				       <i class="fa fa-arrow-circle-o-right" aria-hidden="true"></i> 导出流程
				    </a>
				    <a href="javascript:void(0)" class="list-group-item node" id="import" data-toggle="modal" data-target="#import-modal">
				       <i class="fa fa-arrow-circle-o-left" aria-hidden="true"></i>
				       <span>导入流程 </span>
				    </a>
			  </div>
			  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
			  	<div class="panel panel-default toolbox-panel m-t-0">
			  		<div class="panel-heading toolbox-panel-heading" role="tab" id="select-handing">
				      <h4 class="panel-title">
				        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse-select" aria-expanded="true" aria-controls="collapse-select">
				          选择 <div class="pull-right"><i class="fa fa-angle-double-up"></i></div>
				        </a>
				      </h4>
				    </div><!-- end panel-heading -->
				    <div id="collapse-select" class="panel-collapse in collapse" role="tabpanel" aria-labelledby="select-handing">
				      <div class="list-group">
						   <a href="javascript:void(0)" class="list-group-item node selectable" id="pointer">
						     <img src="${ctx}/plugins/flow/images/select16.gif" />&nbsp;&nbsp;选择
						   </a>
						   <a href="javascript:void(0)" class="list-group-item node selectable" id="path">
						       <img src="${ctx}/plugins/flow/images/16/flow_sequence.png" />&nbsp;&nbsp;连线
						   </a>
				      </div>
				    </div>
			  	</div><!-- end panel -->
			  	<div class="panel panel-default toolbox-panel m-t-0">
			  		<div class="panel-heading toolbox-panel-heading" role="tab" id="event-handing">
				      <h4 class="panel-title">
				        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse-event" aria-expanded="false" aria-controls="collapse-event">
				          事件 <div class="pull-right"><i class="fa fa-angle-double-down"></i></div>
				        </a>
				      </h4>
				    </div><!-- end panel-heading -->
				    <div id="collapse-event" class="panel-collapse collapse" role="tabpanel" aria-labelledby="event-handing">
				      <div class="list-group">
						   <a href="javascript:void(0)" class="list-group-item node state" id="start" type="start">
				        	 <img src="${ctx}/plugins/flow/images/16/start_event_empty.png" />&nbsp;&nbsp;开始
						   </a>
						   <a href="javascript:void(0)" class="list-group-item node state" id="end" type="end">
						      <img src="${ctx}/plugins/flow/images/16/end_event_terminate.png" />&nbsp;&nbsp;结束
						   </a>
				      </div>
				    </div>
			  	</div><!-- end panel -->
			  	<div class="panel panel-default toolbox-panel m-t-0">
			  		<div class="panel-heading toolbox-panel-heading" role="tab" id="task-handing">
				      <h4 class="panel-title">
				        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse-task" aria-expanded="false" aria-controls="collapse-task">
				          任务 <div class="pull-right"><i class="fa fa-angle-double-down"></i></div>
				        </a>
				      </h4>
				    </div><!-- end panel-heading -->
				    <div id="collapse-task" class="panel-collapse collapse" role="tabpanel" aria-labelledby="task-handing">
				      <div class="list-group">
						   <a href="javascript:void(0)" class="list-group-item node state" id="task" type="task">
				        	<img src="${ctx}/plugins/flow/images/16/task_empty.png" />&nbsp;&nbsp;任务
						   </a>
						   <a href="javascript:void(0)" class="list-group-item node state" id="custom" type="custom">
						        <img src="${ctx}/plugins/flow/images/16/task_empty.png" />&nbsp;&nbsp;自定义
						   </a>
						   <a href="javascript:void(0)" class="list-group-item node state" id="subprocess" type="subprocess">
				        		<img src="${ctx}/plugins/flow/images/16/task_empty.png" />&nbsp;&nbsp;子流程
						   </a>
				      </div>
				    </div>
			  	</div><!-- end panel -->
			  	
			  	<div class="panel panel-default toolbox-panel m-t-0">
			  		<div class="panel-heading toolbox-panel-heading" role="tab" id="gateway-handing">
				      <h4 class="panel-title">
				        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse-gateway" aria-expanded="false" aria-controls="collapse-gateway">
				          网关 <div class="pull-right"><i class="fa fa-angle-double-down"></i></div>
				        </a>
				      </h4>
				    </div><!-- end panel-heading -->
				    <div id="collapse-gateway" class="panel-collapse collapse" role="tabpanel" aria-labelledby="gateway-handing">
				      <div class="list-group">
						   <a href="javascript:void(0)" class="list-group-item node state" id="fork" type="decision">
				        		<img src="${ctx}/plugins/flow/images/16/gateway_exclusive.png" />&nbsp;&nbsp;判断
						   </a>
						   <a href="javascript:void(0)" class="list-group-item node state" id="fork" type="fork">
						        <img src="${ctx}/plugins/flow/images/16/gateway_parallel.png" />&nbsp;&nbsp;并发
						   </a>
						   <a href="javascript:void(0)" class="list-group-item node state" id="join" type="join">
						        <img src="${ctx}/plugins/flow/images/16/gateway_parallel.png" />&nbsp;&nbsp;合并
						   </a>
				     </div>
				   </div>
			  	 </div><!-- end panel -->
		       </div>
		     </div>
	       </div><!-- collapse-toolbox panel-collapse-->
	     </div><!-- panel -->
       </div><!-- panel-group -->
     </div>

	<!-- 属性面板 -->
	<div id="properties">
	     <div class="container-fluid">
	         <div class="panel-group" id="properties-accordion" role="tablist" aria-multiselectable="true">
	             <div class="panel panel-default" id="properties_handle">
				    <div class="panel-heading" role="tab" id="headingOne">
				         <h4 class="panel-title">
					        <a role="button" data-toggle="collapse" data-parent="#properties-accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
					          <i class="fa fa-cog" aria-hidden="true"></i> 属性 <div class="pull-right" title="隐藏属性面板"><i class="fa fa-caret-up"></i></div>
					        </a>
					      </h4>
				    </div>
				    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
      					<div class="panel-body">
      					   <div class="properties_box designer-box">
						     <form class="properties_panel form-horizontal">
						     </form>
						  </div>
      					</div>
      			    </div>
				 </div><!-- end panel -->
	         </div>
		</div>
	</div>
	<div id="snakerflow"></div>
	<!-- 用于导出流程 -->
	<form id="export-form" class="hidden" action="flow/export" method="post">
	     <textarea id="export-model" name="model"></textarea>
	</form>
	<!-- 用于导入流程 -->
	<div class="modal fade" id="import-modal" data-keyboard="false" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="modal-label">
	   <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close" title="关闭窗口"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="modal-label">导入流程</h4>
		      </div>
		      <div class="modal-body">
		          <form action="flow/import" id="import-file-form" method="post" target="importFlowIframe" enctype="multipart/form-data">
		          	<input type="file" id="import-file" name="file" />
		          	<span class="help-block">只支持导入XML格式的文件.</span>
		          </form>
		          <iframe hidden="hidden" id="import-flow-iframe" name="importFlowIframe" ></iframe>
		      </div>
		      <div class="modal-footer">
		        <button type="button" id="start-import-flow" class="btn btn-primary"><i class="fa fa-arrow-circle-o-left" aria-hidden="true"></i> 开始导入</button>
		      </div>
		    </div>
		</div>
   </div>
</div>

<script type="text/javascript">

function initFlowDesigner(json) {
	//$("#pformId_input-input-select").remove();
	var model;
	var formId = '';
	if(json) {
		model=eval("(" + json + ")");
		formId = model.props.props.formId.value;
	} else {
		model="";
	}
	$("#snakerflow").snakerflow({
		basePath : "${ctx}/plugins/flow/",
		ctxPath : "${ctx}",
		restore : model,
		formPath : "forms/",
		formId: formId,
		tools : {
			save : {
				onclick : function(data, id) {
					switch(id) {
						case 'save':
						case 'update':
							parent.saveModel(data,id);
							break;
						case 'export':
							FlowDesigner.exportModel(data);
							break;
					}
				}
		    }
		}
	});
}

$(function() { 
	FlowDesigner.initToolsHeight();
	FlowDesigner.toolToggleListener();
	FlowDesigner.propertyPanelListener();
	$("#snakerflow").width($(window).width()*1.5);
	var msg = '${msg}';
	if(utils.isNotEmpty(msg)) {
		utils.showMsg(msg);
	}
	$("#start-import-flow").click(function() {
		var importFile = $("#import-file").val();
		if(utils.isNotEmpty(importFile)) {
			$("#import-modal").modal('hide');
			utils.waitLoading("正在导入流程...");
			$("#import-file-form").submit();
			$("#import-flow-iframe").load(function(){
				utils.closeWaitLoading();
				var result = $(this).contents().text();
				if(utils.isNotEmpty(result)) {
		    		var output = $.parseJSON(result);
		    		utils.showMsg(output.msg);
		    		initFlowDesigner(output.data);
				}
			});
		} else {
			utils.showMsg('请选择要导入的流程文件！');
		}
	});
});

</script>	
  </body>
</html>
