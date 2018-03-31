<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content-dialog">
 	<form class="form-horizontal" role="form" id="form-edit" action="subSystem/update">
 		<input type="hidden" name="id" value="${objBean.id }" />
 		<input type="hidden" name="userId" value="${objBean.userId }" />
		<div class="form-group m-b-10">
			    <label for="input1" class="col-sm-2 control-label">名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" value="${objBean.name }" name="name" data-label-name="系统名称" id="input1" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input2" class="col-sm-2 control-label">地址</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" value="${objBean.url }" name="url" data-label-name="地址" id="input2" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label class="col-sm-2 control-label">系统类型</label>
			    <div class="col-sm-9 p-l-0">
			       <select class="form-control cnoj-select" data-uri="dict/item/SYS_TYPE.json" name="sysType" data-default-value="${objBean.sysType }">
			       </select>
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input4" class="col-sm-2 control-label">状态</label>
			    <div class="col-sm-9 p-l-0">
			       <select class="form-control" name="state" id="input4" >
			            <option value="1" ${objBean.state==1?'selected':''}>有效</option>
					    <option value="0" ${objBean.state==0?'selected':''}>无效</option>
			       </select>
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input5" class="col-sm-2 control-label">序号</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" id="input5" class="form-control require" data-label-name="序号" data-format="num" name="sortOrder" value="${sortOrder }" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input6" class="col-sm-2 control-label">图标</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" id="input6" class="form-control" name="icon" value="${objBean.icon }" />
			      <p class="help-block">填写<a target="_blank" href="http://fontawesome.io/icons/">Font Awesome</a>图标样式；如果不清楚图标样式，请为空！</p>
			    </div>
			</div>
			
			<div class="text-center">
			     <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="subSystem/list" ><i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
	</form>

</div>
