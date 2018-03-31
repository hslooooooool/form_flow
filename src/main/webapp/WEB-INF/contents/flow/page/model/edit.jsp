<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog">
	<form class="form-horizontal" role="form" id="form-add" action="flow/page/model/edit.json">
	<input type="hidden" name="id" value="${objBean.id}" />
	<div class="form-group m-b-10">
	<label for="form-ele-1" class="col-sm-2 control-label">名称</label>
	<div class="col-sm-9 p-l-0">
	   <input type="text" class="form-control require" name="name" data-format="" data-label-name="名称" value="${objBean.name}" id="form-ele-1" />
	</div>
 </div>
  <div class="form-group m-b-10">
	<label for="form-ele-2" class="col-sm-2 control-label">URI</label>
	<div class="col-sm-9 p-l-0">
	   <input type="text" class="form-control require" name="uri" data-format="" data-label-name="URI" value="${objBean.uri}" id="form-ele-2" />
	</div>
 </div>
  <div class="form-group m-b-10">
	<label for="form-ele-3" class="col-sm-2 control-label">显示URI</label>
	<div class="col-sm-9 p-l-0">
	   <input type="text" class="form-control require" name="viewUri" data-format="" data-label-name="显示URI" value="${objBean.viewUri}" id="form-ele-3" />
	</div>
 </div>
  <div class="form-group m-b-10">
	<label for="form-ele-4" class="col-sm-2 control-label">状态</label>
	<div class="col-sm-9 p-l-0">
	   <select class="form-control cnoj-select" data-uri="dict/item/DATA_STATE.json" name="state" data-default-value="${objBean.state}" id="form-ele-4"></select>
	</div>
 </div>

			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="flow/page/model/list">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
	</form>
</div><!-- wrap-content-dialog -->
