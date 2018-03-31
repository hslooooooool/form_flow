<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog">
	<form class="form-horizontal" role="form" id="form-add" action="position/add.json" target="#position-list">
		    <div class="form-group m-b-10">
			    <label for="input01" class="col-sm-2 control-label">职位名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="name" data-label-name="职位名称" id="input01" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input02" class="col-sm-2 control-label">组织机构</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require cnoj-input-tree" data-uri="op/queryTree/select_org_tree.json" data-is-show-none="yes" name="orgId" value="${id }" id="input02" />
			    </div>
			</div>
			<!-- 
			<div class="form-group m-b-10">
			    <label for="input03" class="col-sm-2 control-label">拥有角色</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control cnoj-input-select" data-uri="op/query?resId=select_role_list" name="roleId" data-param-name="name" data-is-show-all="no" id="input03" />
			    </div>
			</div> -->
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="position/list?orgId=${id }">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
	</form>
</div><!-- wrap-content-dialog -->