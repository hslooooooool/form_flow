<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
  <div class="panel panel-default m-b-0">
	<div class="panel-heading ui-widget-header">该【${name }】职位所拥有的角色</div>
	<div class="panel-body p-0 p-t-2 body-bg borer-top">
	   <div role="tabpanel" class="tab-pane cnoj-auto-limit-height" id="position-role-tab">
	        <div class="cnoj-load-url" data-uri="position/rolelist?id=${id }" ></div>
		</div>
	</div>
  </div><!-- panel -->
</div>