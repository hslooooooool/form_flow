<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="wrap-content-dialog">
    <form class="form-horizontal" role="form" id="form-edit" action="flow/resource/edit.json" target="#main-content">
		<input type="hidden" name="id" value="${objBean.id}" />
		<div class="form-group m-b-10">
			    <label for="input01" class="col-sm-2 control-label">流程名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="name" data-label-name="流程名称" id="input01" value="${objBean.name}" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input02" class="col-sm-2 control-label">选择流程</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control cnoj-input-select" data-uri="op/query/select_flow_res.json" data-is-show-all="no" data-default-value="${fn:replace(objBean.uri,'process/form?processName=','')}" data-param-name="name" name="uri" id="input03" />
			    </div>
			</div>
			
			<div class="form-group m-b-10">
			    <label for="menu-state" class="col-sm-2 control-label">状态</label>
			    <div class="col-sm-9 p-l-0">
			    <select class="form-control cnoj-select select-form-control" name="state" id="state" data-uri="dict/item/DATA_STATE.json" data-default-value="${objBean.state}">
				</select>
			    </div>
			</div>
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="flow/resource/list" ><i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
	</form>
</div>