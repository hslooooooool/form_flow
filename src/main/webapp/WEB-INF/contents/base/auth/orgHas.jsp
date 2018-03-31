<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
  <div class="panel panel-default m-b-0">
	<div class="panel-heading ui-widget-header">该组织机构【${name }】所拥有的角色</div>
	<div class="panel-body p-0 p-t-2 body-bg borer-top">
		<div id="org-role-tab" class="cnoj-auto-limit-height">
		   <div class="cnoj-load-url" data-uri="org/rolelist?id=${id }" ></div>
		</div>
	</div>
  </div><!-- panel -->
</div>