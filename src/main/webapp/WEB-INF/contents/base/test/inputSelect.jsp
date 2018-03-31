<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
   <div class="container-fluid">
	   <form class="form-horizontal" role="form">
	     <div class="row">
	        <div class="col-sm-6">
			      <div class="form-group">
				    <label for="input01" class="col-sm-3 control-label">输入框下拉列表</label>
				    <div class="col-sm-9">
				      <input type="text" class="form-control cnoj-input-select" data-uri="dict/item/TABLE_FIELD_DATA_FORMAT.json" 
				      name="test" id="input01" />
				    </div>
			     </div>
			 </div>
			 <div class="col-sm-6">
			    <div class="form-group">
				    <label for="input01" class="col-sm-3 control-label">输入框下拉列表2</label>
				    <div class="col-sm-9">
				      <input type="text" class="form-control cnoj-input-select" data-uri="dict/item/PROJECT_ORG.json" 
				      name="test" id="input02" />
				    </div>
			     </div>
			 </div>
		</div>
		
		<div class="row">
	        <div class="col-sm-6">
			      <div class="form-group">
				    <label for="input01" class="col-sm-3 control-label">分页测试</label>
				    <div class="col-sm-9">
				      <input type="text" class="form-control cnoj-input-select" data-uri="op/queryPage/input_select_page_test.json" 
				      name="test" id="input03" />
				    </div>
			     </div>
			 </div>
		</div>
		<div class="row">
	        <div class="col-sm-6">
			      <div class="form-group">
				    <label for="input01" class="col-sm-3 control-label">单选框</label>
				    <div class="col-sm-9">
				      <div class="cnoj-radio radio" data-uri="dict/item/YES_OR_NO.json" data-name="test1" id="input03"></div>
				    </div>
			     </div>
			 </div>
			 <div class="col-sm-6">
			      <div class="form-group">
				    <label for="input01" class="col-sm-3 control-label">复选框</label>
				    <div class="col-sm-9">
				      <div class="cnoj-checkbox checkbox" data-uri="dict/item/YES_OR_NO.json" data-is-horizontal="no" data-name="test2" id="input03"></div>
				    </div>
			     </div>
			 </div>
		</div>
		<div class="row">
	        <div class="col-sm-6">
	        	<textarea id="test-auto-height" class="form-control" rows="3">根据内容自适应高度</textarea>
	        </div>
	    </div>
	   </form>
   </div>
   <script type="text/javascript">
   	$("#test-auto-height").autoTextarea();
   </script>
</div>
