<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog">
        <form class="form-horizontal" role="form" id="form-add" action="resource/add.json" target="#main-content">
            
            <div class="form-group m-b-10">
			    <label for="input01" class="col-sm-2 control-label">资源名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="name" data-label-name="资源名称" id="input01" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="type" class="col-sm-2 control-label">资源类型</label>
			    <div class="col-sm-9 p-l-0">
				    <select class="form-control cnoj-select select-form-control" name="type" id="type" data-uri="dict/item/RESOURCE_TYPE.json">
				    </select>
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input02" class="col-sm-2 control-label">资源URI</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="uri" data-label-name="资源URI" id="input02" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="state" class="col-sm-2 control-label">状态</label>
			    <div class="col-sm-9 p-l-0">
				    <select class="form-control cnoj-select select-form-control" name="state" id="state" data-uri="dict/item/DATA_STATE.json">
				    </select>
			    </div>
			</div>
			 
			<div class="form-group m-b-10">
			   <div class="col-sm-12 p-l-20 p-r-20">
			      <div class="cnoj-load-url" id="select-auth" data-uri="opauth/selectAuth"></div>
			   </div>
			</div>
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="resource/list">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
        </form>
</div><!-- wrap-content-dialog -->