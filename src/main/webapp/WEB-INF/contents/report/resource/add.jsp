<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog">
        <form class="form-horizontal" role="form" id="form-add" action="report/resource/add.json">
            <div class="form-group m-b-10">
			    <label for="form-res-input01" class="col-sm-2 control-label">资源名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="name" data-label-name="资源名称" id="form-res-input01" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="form-res-input02" class="col-sm-2 control-label">选择报表</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control cnoj-input-select" data-uri="op/query/select_report_list.json" data-is-show-all="no" data-param-name="name" name="uri" id="form-res-input03" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="res-state" class="col-sm-2 control-label">状态</label>
			    <div class="col-sm-9 p-l-0">
				    <select class="form-control cnoj-select select-form-control" name="state" id="res-state" data-uri="dict/item/DATA_STATE.json">
				    </select>
			    </div>
			</div>
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="report/resource/list">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
        </form>
</div><!-- wrap-content-dialog -->