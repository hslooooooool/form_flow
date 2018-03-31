<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog">
	<form class="form-horizontal" role="form" id="form-add" action="test2/add.json">
		      <div class="form-group m-b-10">
	<label for="form-ele-1" class="col-sm-2 control-label">姓名</label>
	<div class="col-sm-9 p-l-0">
	   <input type="text" class="form-control " name="name" data-format="" data-label-name="姓名" value="" id="form-ele-1" />
	</div>
 </div>
  <div class="form-group m-b-10">
	<label for="form-ele-2" class="col-sm-2 control-label">年龄</label>
	<div class="col-sm-9 p-l-0">
	   <input type="text" class="form-control " name="age" data-format="integer" data-label-name="年龄" value="" id="form-ele-2" />
	</div>
 </div>

			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="test2/list">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
	</form>
</div><!-- wrap-content-dialog -->
