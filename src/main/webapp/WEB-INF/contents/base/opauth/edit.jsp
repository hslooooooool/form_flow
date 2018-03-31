<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="wrap-content-dialog">
    <form class="form-horizontal" role="form" id="form-edit" action="opauth/edit.json" target="#main-content">
		<input type="hidden" name="id" value="${objBean.id}" />
		<div class="form-group m-b-10">
			    <label for="input01" class="col-sm-3 control-label">操纵权限名称</label>
			    <div class="col-sm-8 p-l-0">
			      <input type="text" class="form-control require" name="name" data-label-name="操纵权限名称" id="input01" value="${objBean.name}" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input02" class="col-sm-3 control-label">操纵权限值</label>
			    <div class="col-sm-8 p-l-0">
			      <input type="text" class="form-control require" name="value" data-label-name="操纵权限值" id="input02" value="${objBean.value }" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input03" class="col-sm-3 control-label">序号</label>
			    <div class="col-sm-8 p-l-0">
			      <input type="text" id="input03" class="form-control require" data-label-name="序号" data-format="num" data-length="1,5" name="sortOrder" value="${objBean.sortOrder }" />
			    </div>
			</div>
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="opauth/list" ><i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
	</form>
</div>