<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content-dialog">
       <form class="form-horizontal" id="from-add" action="user/setting/add.json" role="form" target="#main-content">
           <div class="form-group">
		    <label for="input01" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">名称</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control require" name="name" label-name="名称" id="input01" />
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="input02" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">URI</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control require" name="uri" label-name="URI" id="input02" />
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="input03" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">状态</label>
		    <div class="col-sm-10">
		        <select id="input03" class="form-control" name="state">
				  <option value="1">有效</option>
				  <option value="0">无效</option>
				</select>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="input04" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">序号</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" name="sortOrder" label-name="序号" value="${sortOrder }" id="input04" />
		    </div>
		  </div>
		
		  <div class="form-group m-b-5">
		    <div class="col-sm-11 text-right">
		        <button class="btn btn-primary cnoj-data-submit" data-refresh-uri="user/setting/list"><i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
		    </div>
		  </div>
       </form>
</div>