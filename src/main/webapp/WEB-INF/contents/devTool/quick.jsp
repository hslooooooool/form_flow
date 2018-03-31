<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
    <div class="panel panel-default">
	    <div class="panel-heading ui-widget-header panel-heading-border"><strong>快速生成页面</strong></div>
	    <div class="panel-body">
	       <div class="container-fluid">
			<form class="form-horizontal" role="form">
			   <div class="row">
			       <div class="col-sm-10">
					   <div class="form-group">
					    <label for="class-bean" class="col-sm-1 control-label p-l-0 p-r-0">Bean名称：</label>
					    <div class="col-sm-11">
					      <input type="text" class="form-control" id="class-bean" />
					    </div>
					  </div>
					</div>
		
					<div class="col-sm-2">
					  <div class="form-group">
					    <div class="col-sm-10">
					      <button type="button" class="btn btn-default">确定</button>
					    </div>
					  </div>
					</div>
			   </div>
			</form>
		</div>
	  </div><!-- panel-body -->
	  <div class="ui-widget-header list-header-title">属性列表</div>
	 <!--  <div class="bean-prop"></div>--> 
	  <table class="table table-bordered table-condensed">
	     <thead>
	        <tr class="text-center">
		        <th colspan="2">属性</th>
		        <th rowspan="2" width="150px">标签名称</th>
		        <th rowspan="2" style="width:100px">
		          <input type="checkbox" /> 列表
		        </th>
		        <th colspan="2">添加</th>
	        </tr>
	        <tr class="text-center">
	           <th width="100px">名称</th>
	           <th width="100px">数据类型</th>
	           <th width="20px">
	              <input type="checkbox" />
	           </th>
	           <th >表单数据类型</th>
	        </tr>
	     </thead>
	     <tbody>
	         <tr>
	            <td>id</td>
	            <td>String</td>
	            <td><input type="text" class="" /></td>
	            <td class="text-center"><input type="checkbox" /></td>
	            <td class="text-center"><input type="checkbox" /></td>
	            <td class="text-center">
	               <select>
	                  <option>表单数据类型</option>
	               </select>
	            </td>
	         </tr>
	          <tr>
	            <td>name</td>
	            <td>String</td>
	            <td><input type="text" class="" /></td>
	             <td class="text-center"><input type="checkbox" /></td>
	              <td class="text-center"><input type="checkbox" /></td>
	              <td class="text-center">
	               <select>
	                  <option>表单数据类型</option>
	               </select>
	            </td>
	         </tr>
	          <tr>
	            <td>value</td>
	            <td>String</td>
	            <td><input type="text" class="" /></td>
	            <td class="text-center"><input type="checkbox" /></td>
	            <td class="text-center"><input type="checkbox" /></td>
	            <td class="text-center">
	               <select>
	                  <option>表单数据类型</option>
	               </select>
	            </td>
	         </tr>
	         <tr>
	            <td>createTime</td>
	            <td>Date</td>
	            <td><input type="text" class="" /></td>
	             <td class="text-center"><input type="checkbox" /></td>
	              <td class="text-center"><input type="checkbox" /></td>
	              <td class="text-center">
	               <select>
	                  <option>表单数据类型</option>
	               </select>
	            </td>
	         </tr>
	     </tbody>
	  </table>
   </div><!-- panel -->
</div>