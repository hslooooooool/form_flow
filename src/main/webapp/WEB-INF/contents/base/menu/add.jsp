<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog">
        <form class="form-horizontal" role="form" id="form-add" action="menu/add.json" target="#main-content">
            <div class="form-group m-b-10">
			    <label for="menu-name" class="col-sm-2 control-label">菜单名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="name" data-label-name="菜单名称" id="menu-name" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="parent-id" class="col-sm-2 control-label">父菜单</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require cnoj-input-tree" data-uri="op/queryTree/select_menu_tree.json" data-is-show-none="yes" name="parentId" value="${id }" id="parent-id" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input03" class="col-sm-2 control-label">选择资源</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control cnoj-input-select" data-uri="op/query/select_res_list_by_menu.json" data-is-show-all="no" data-param-name="name" name="resourceId" id="input03" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="menu-state" class="col-sm-2 control-label">状态</label>
			    <div class="col-sm-9 p-l-0">
			       <select class="form-control" name="state" id="menu-state" >
			            <option value="1">有效</option>
					    <option value="0">无效</option>
			       </select>
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="menu-seq-num" class="col-sm-2 control-label">序号</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" id="menu-seq-num" class="form-control require" data-label-name="序号" data-format="num" data-length="1,5" name="sortOrder" value="${sortOrder }" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="menu-icon" class="col-sm-2 control-label">图标</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" id="menu-icon" class="form-control" name="icon" value="" />
			      <p class="help-block">填写<a target="_blank" href="http://v3.bootcss.com/components/">Bootstrap</a>图标样式；如果不清楚图标样式，请为空！</p>
			    </div>
			</div>
			
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="menu/list">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
        </form>
</div><!-- wrap-content-dialog -->