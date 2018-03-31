<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<jsp:include page="./header.jsp"></jsp:include>

    <!-- zTree插件 -->
	<link href="${pageContext.request.contextPath}/plugins/zTree/css/zTreeStyle.css" type="text/css" rel="stylesheet"  />
	<script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.core-3.5.min.js" type="text/javascript" ></script>
	<script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.excheck-3.5.min.js" type="text/javascript" ></script>
	<script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.exedit-3.5.min.js" type="text/javascript" ></script>
	<script src="${pageContext.request.contextPath}/plugins/zTree/js/jquery.ztree.exhide-3.5.min.js" type="text/javascript" ></script>
	
	<!-- jqGrid表格插件-->
	 <link href="${pageContext.request.contextPath}/plugins/jqGrid/css/ui.jqgrid.css" rel="stylesheet"/>
	 <link href="${pageContext.request.contextPath}/css/jqGrid-bootstrap.css" rel="stylesheet" type="text/css" />
	 <script src="${pageContext.request.contextPath}/plugins/jqGrid/js/i18n/grid.locale-cn.js" type="text/javascript" ></script>
	 <script src="${pageContext.request.contextPath}/plugins/jqGrid/js/jquery.jqGrid.min.js" type="text/javascript" ></script>
	 <script src="${pageContext.request.contextPath}/plugins/jqGrid/js/jquery.jqGrid.bootstrap.js" type="text/javascript" ></script>
    
   <script src="${pageContext.request.contextPath}/js/lunar-calendar.js" type="text/javascript"></script>
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
	<!-- 与流程相关的样式 -->
	<link href="${pageContext.request.contextPath}/plugins/flow/css/flow.css" rel="stylesheet" />
	
	<!-- 自定义js -->
	<script src="${pageContext.request.contextPath}/js/check-card-no.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/check-form.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/ztree-util.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/jqGrid-util.js" type="text/javascript" ></script>
	<script src="${pageContext.request.contextPath}/js/input-select.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/auto-complete.js" type="text/javascript"></script>
	
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload-util.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/cnoj.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/table-async-tree.js" type="text/javascript"></script>
	
	<script src="${pageContext.request.contextPath}/js/cnoj.event.listener.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/adjust-ie-height.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.autotextarea.js" type="text/javascript" charset="UTF-8"></script>
	<script src="${pageContext.request.contextPath}/js/easyui.tabs.contextmenu.js" type="text/javascript" charset="UTF-8"></script>
	
   <link href="${pageContext.request.contextPath}/css/menu.css" rel="stylesheet" />
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/left-menu.js"></script>

<div class="cnoj-menu" id="menu-nav">
	<h5 class="menu-title"><i class="glyphicon glyphicon-th-large"></i> 功能菜单</h5>
	<div id="menu-sub-nav">
		<cnoj:menu smartResp="${menuRes }" />
	</div>
</div>
<div class="menu-resizer">
    <div class="menu-resizer-toggler"></div>
</div>
<script type="text/javascript">
  var forward = '${forward}';
  $(function(){
	  if(utils.isNotEmpty(forward)) {
		  var is = false;
		  $("#menu-sub-nav a").each(function() {
			  var $this = $(this);
			  var uri = $this.data("uri");
			  if(utils.isNotEmpty(uri) && ( forward == uri || forward.indexOf(uri)>-1)) { 
				  $this.parentsUntil("#menu-sub-nav","ul").trigger("click");
				  $this.data("uri",forward);
				  $this.trigger("click");
				  $this.data("uri",uri);
				  is = true;
				  return 0;
			  }
		  });
		 /* if(!is) {
			  BootstrapDialogUtil.warningAlert("对不起，您没有权限访问指定的页面！");
		  }*/
	  }
  });
</script>