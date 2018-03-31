<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content-dialog">
	<form class="form-horizontal" role="form" id="form-add" action="position/edit.json" target="#position-list">
		    <input type="hidden" name="id" value="${objBean.id}" />
		    <div class="form-group m-b-10">
			    <label for="input01" class="col-sm-2 control-label">职位名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="name" data-label-name="职位名称" id="input01" value="${objBean.name}" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input02" class="col-sm-2 control-label">组织机构</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require cnoj-input-tree" data-uri="op/queryTree/select_org_tree.json" data-is-show-none="yes" name="orgId" value="${objBean.orgId}" id="input02" />
			    </div>
			</div>
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="position/list?orgId=${objBean.orgId }">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
	</form>
</div><!-- wrap-content-dialog -->