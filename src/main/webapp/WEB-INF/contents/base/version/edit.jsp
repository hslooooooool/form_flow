<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content-dialog">
   <form class="form-horizontal" role="form" id="form-edit" action="version/update">
		    <input type="hidden" name="id" value="${objBean.id }" />
		    <div class="form-group m-b-10">
			    <label for="input01" class="col-sm-2 control-label">版本号</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="version" data-label-name="版本号" id="input01" value="${objBean.version }" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input03" class="col-sm-2 control-label">更新日期</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control cnoj-date" name="updateDate" value="${objBean.updateDate }" id="input03" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input10" class="col-sm-2 control-label">备注</label>
			    <div class="col-sm-9 p-l-0">
			       <textarea class="form-control require" name="descr" rows="8" id="input10" cols="60">${objBean.descr}</textarea>
			    </div>
			</div>
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="version/list">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
	</form>
</div><!-- wrap-content-dialog -->