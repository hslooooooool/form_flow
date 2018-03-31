<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content-dialog">
	<form class="form-horizontal" role="form" id="form-add" action="org/edit.json" target="#main-content">
	        <input type="hidden" name="id" value="${objBean.id }" />
	        <input type="hidden" name="seqParentIds" value="${objBean.seqParentIds }" />
		    <div class="form-group m-b-10">
			    <label for="input01" class="col-sm-2 control-label">机构名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="name" data-label-name="机构名称" id="input01" value="${objBean.name }" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input02" class="col-sm-2 control-label">机构代码</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="code" data-label-name="机构代码" id="input02" value="${objBean.code }"  />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input03" class="col-sm-2 control-label">父级机构</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require cnoj-input-tree" data-uri="op/queryTree/select_org_tree.json" data-is-show-none="yes" name="parentId" value="${objBean.parentId }" id="input03" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input04" class="col-sm-2 control-label">机构类型</label>
			    <div class="col-sm-9 p-l-0">
			       <select class="form-control cnoj-select" data-uri="dict/item/ORG_TYPE.json" name="type" data-default-value="${objBean.type }" id="input04"></select>
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input05" class="col-sm-2 control-label">联系人</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" id="input05" class="form-control" name="contacts" value="${objBean.contacts }" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input06" class="col-sm-2 control-label">联系电话</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" id="input06" class="form-control" name="contactNumber" value="${objBean.contactNumber}" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input07" class="col-sm-2 control-label">序号</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" id="input07" class="form-control require" data-label-name="序号" data-format="num" data-length="1,5" name="sortOrder" value="${objBean.sortOrder }" />
			    </div>
			</div>
			
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="org/list">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
	</form>
</div><!-- wrap-content-dialog -->