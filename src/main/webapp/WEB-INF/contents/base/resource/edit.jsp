<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="wrap-content-dialog">
    <form class="form-horizontal" role="form" id="form-edit" action="resource/edit.json" target="#main-content">
		<input type="hidden" name="id" value="${objBean.id}" />
		
		<div class="form-group m-b-10">
			    <label for="input01" class="col-sm-2 control-label">资源名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="name" data-label-name="资源名称" id="input01" value="${objBean.name}" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="type" class="col-sm-2 control-label">资源类型</label>
			    <div class="col-sm-9 p-l-0">
				    <select class="form-control cnoj-select select-form-control" name="type" id="type" data-uri="dict/item/RESOURCE_TYPE.json" data-default-value="${objBean.type}">
				    </select>
			    </div>
			</div>
			
			<div class="form-group m-b-10">
			    <label for="input02" class="col-sm-2 control-label">资源URI</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control" name="uri" id="input02" value="${objBean.uri }"  />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="menu-state" class="col-sm-2 control-label">状态</label>
			    <div class="col-sm-9 p-l-0">
			    <select class="form-control cnoj-select select-form-control" name="state" id="state" data-uri="dict/item/DATA_STATE.json" data-default-value="${objBean.state}">
				</select>
			    </div>
			</div>
			<div class="form-group m-b-10">
			   <div class="col-sm-12 p-l-20 p-r-20">
			      <div class="cnoj-load-url" id="select-auth" data-uri="opauth/selectAuth?ids=${objBean.opAuths}"></div>
			   </div>
			</div>
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="resource/list" ><i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
	</form>
</div>