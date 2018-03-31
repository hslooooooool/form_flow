<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog">
        <form class="form-horizontal" role="form" id="form-add" action="flow/resource/add.json" target="#main-content">
            <div class="form-group m-b-10">
			    <label for="input01" class="col-sm-2 control-label">流程名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="name" data-label-name="流程名称" id="input01" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input02" class="col-sm-2 control-label">选择流程</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control cnoj-input-select" data-uri="op/query/select_flow_res.json" data-is-show-all="no" data-param-name="name" name="uri" id="input03" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="state" class="col-sm-2 control-label">状态</label>
			    <div class="col-sm-9 p-l-0">
				    <select class="form-control cnoj-select select-form-control" name="state" id="state" data-uri="dict/item/DATA_STATE.json">
				    </select>
			    </div>
			</div>
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="flow/resource/list">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
        </form>
</div><!-- wrap-content-dialog -->