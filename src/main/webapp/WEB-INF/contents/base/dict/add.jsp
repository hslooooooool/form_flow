<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog">
      <form class="form-horizontal" role="form" id="form-add" action="dict/add.json">
            <div class="form-group m-b-10">
			    <label for="input1" class="col-sm-2 control-label">数据名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="busiName" data-label-name="数据名称" id="input1" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input2" class="col-sm-2 control-label">数据值</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="busiValue" data-label-name="数据值" id="input2" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input3" class="col-sm-2 control-label">父级数据</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require cnoj-input-tree" data-uri="op/queryTree/select_dict_tree.json" data-is-show-none="yes"  name="parentId" value="${id}" id="input3" />
			    </div>
			</div>
			
			<div class="form-group m-b-10">
			    <label for="input4" class="col-sm-2 control-label">状态</label>
			    <div class="col-sm-9 p-l-0">
			       <select class="form-control" name="state" id="input4" >
			            <option value="1">有效</option>
					    <option value="0">无效</option>
			       </select>
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input5" class="col-sm-2 control-label">序号</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" id="input5" class="form-control require" data-label-name="序号" data-format="num" data-length="1,5" name="sortOrder" value="${sortOrder }" />
			    </div>
			</div>
			<div class="text-center">
			     <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="dict/list" ><i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
        </form>
</div><!-- wrap-content-dialog -->