<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
  <div class="panel panel-default m-b-0">
	<div class="panel-heading ui-widget-header">拥有该角色【${name }】的用户或组织机构</div>
	<div class="panel-body p-0 p-t-2 body-bg borer-top">
	   <div class="panel-tabs-wrap" id="has-role-content">
			<div class="panel-tabs-tab">
				<ul class="nav nav-tabs" role="tablist">
				   <li class="active"><a href="#role-user-tab" role="presentation" data-toggle="tab"> 用户列表</a></li>
				   <li><a href="#role-org-tab" role="presentation" data-toggle="tab"> 组织机构</a></li>
				   <li><a href="#role-position-tab" role="presentation" data-toggle="tab"> 职位列表</a></li>
				</ul>
		  	</div>
		  	<div class="tab-content panel-tab-content bg-color-white p-0 cnoj-auto-limit-height">
				<div role="tabpanel" class="tab-pane active" id="role-user-tab">
				    <div class="cnoj-load-url" data-uri="role/userlist?id=${id }" ></div>
				</div>
				<div role="tabpanel" class="tab-pane" id="role-org-tab">
				     <div class="cnoj-load-url" data-uri="role/orglist?id=${id }" ></div>
				</div>
				<div role="tabpanel" class="tab-pane" id="role-position-tab">
				    <div class="cnoj-load-url" data-uri="role/positionlist?id=${id }" ></div>
				</div>
			</div>
		</div>
	</div>
  </div><!-- panel -->
  <script type="text/javascript">
	  $(function(){
		  var isResize = false;
		  $("#has-role-content").find('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			  var id = $(e.target).attr("href");
			  tableWrapListener($(id), isResize);
		  });
		  $(window).resize(function(){
			 isResize = true; 
		  });
	  });
	</script>
</div>