<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content-dialog">
   <form class="form-horizontal" role="form" id="form-add" action="user/add.json" target="#main-content">
		    <div class="form-group m-b-10">
			    <label for="input01" class="col-sm-2 control-label">用户名</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="username" data-label-name="用户名" id="input01" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input02" class="col-sm-2 control-label">密码</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="password" class="form-control require" name="password" data-label-name="密码" id="input02" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input03" class="col-sm-2 control-label">真实姓名</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="fullName" data-label-name="真实姓名" id="input03" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input04" class="col-sm-2 control-label">手机号码</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control" name="mobileNo" data-label-name="手机号码" id="input04" />
			    </div>
			</div>
			
			<div class="form-group m-b-10">
			    <label for="input05" class="col-sm-2 control-label">所属机构</label>
			    <div class="col-sm-9 p-l-0">
			       <input type="text" class="form-control require cnoj-input-tree" data-uri="op/queryTree/select_org_tree.json" data-is-show-none="yes" name="orgId" id="input05" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input06" class="col-sm-2 control-label">职位</label>
			    <div class="col-sm-9 p-l-0">
			       <select class="form-control cnoj-cascade-select" data-cascade-id="#input05" data-change-id="#input05-value" data-uri="op/query/select_position.json" data-param-name="orgId" name="positionId" id="input06">
			           <option value="">请选择</option>
			       </select>
			    </div>
			</div>
			
			<div class="form-group m-b-10">
			    <label for="input07" class="col-sm-2 control-label">QQ号</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control" name="qq" data-label-name="QQ号" id="input07" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input08" class="col-sm-2 control-label">邮箱地址</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control" name="email" data-format="email" data-label-name="邮箱地址" id="input08" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input09" class="col-sm-2 control-label">状态</label>
			    <div class="col-sm-9 p-l-0">
			       <select class="form-control cnoj-select" data-uri="dict/item/DATA_STATE.json" name="state" id="input09"></select>
			    </div>
			</div>
            <div class="form-group m-b-10">
                <label for="input11" class="col-sm-2 control-label">序号</label>
                <div class="col-sm-9 p-l-0">
                    <input type="text" class="form-control" name="sortOrder" data-format="integer" data-label-name="序号" id="input11" />
                </div>
            </div>
			<div class="form-group m-b-10">
			    <label for="input10" class="col-sm-2 control-label">备注</label>
			    <div class="col-sm-9 p-l-0">
			       <textarea class="form-control" name="remark" rows="3" id="input10" cols="60" />
			    </div>
			</div>
			<div class="text-center">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="user/list">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
	</form>
</div><!-- wrap-content-dialog -->