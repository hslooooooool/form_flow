<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
   <div class="m-t-20 m-b-20">
      <button class="btn btn-primary cnoj-open-blank" data-uri="test/upload" data-title="上传文件" data-width="800">
         弹出窗口
      </button>
   </div>
   <div class="container-fluid">
	   <form class="form-horizontal" role="form">
	     <div class="row">
	        <div class="col-sm-6">
			      <div class="form-group">
				    <label for="input01" class="col-sm-2 control-label">输入框下拉列表</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control cnoj-input-select" data-uri="dict/item/INDEX_LAYOUT_TEMP.json" 
				      name="test" id="input01" />
				    </div>
			     </div>
			 </div>
			 <div class="col-sm-6">
			    <div class="form-group">
				    <label for="auto-input-complete2" class="col-sm-2 control-label">自动完成</label>
				    <div class="col-sm-10">
				    
				      <input type="text" class="form-control cnoj-auto-complete" id="auto-complete2" data-multiple="1" 
				      data-uri="op/autoComplete/menu_auto_complete_test.json" 
				      data-json-data='[{"id":"test","value":"测试","label":"测试内容"},{"id":"test2","value":"测试2","label":"测试内容2"}]' />
				    
				    </div>
				  </div>
			 </div>
		</div>
		<div class="row">
		   <div class="col-sm-6">
		       <div class="form-group">
				    <label for="input01" class="col-sm-2 control-label">测试样式</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" id="input03">
				    </div>
			     </div>
		   </div>
		   <div class="col-sm-6">
		       <div class="form-group">
				    <label for="input01" class="col-sm-2 control-label">状态</label>
				    <div class="col-sm-10">
				      <select class="form-control cnoj-select" data-uri="dict/item/DATA_STATE.json" data-default-value="0" id="select01">
				      </select>
				    </div>
			     </div>
		   </div>
		</div>
		
		<div class="row">
		     <div class="col-sm-6">
		        <div class="form-group">
			    <label for="time-test" class="col-sm-2 control-label">时间</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control cnoj-time" data-start-date="2014-10-09" data-end-date="2014-10-10" id="time-test">
			    </div>
			  </div>
		     
			</div>
			<div class="col-sm-6">
			  <div class="form-group">
			    <label for="date-test" class="col-sm-2 control-label">日期</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control cnoj-date" data-date-format="yyyy年mm月dd日" data-start-date="2014-10-01" data-end-date="2014-10-10" id="date-test" >
			    </div>
			  </div>
			</div>
		</div>
		<div class="row">
		     <div class="col-sm-6">
			   <div class="form-group">
			    <label for="datetime-test" class="col-sm-2 control-label">日期时间</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control cnoj-datetime" id="sss" />
			    </div>
			  </div>
			 </div>
			<div class="col-sm-6">
			   <div class="form-group">
			    <label for="datetime-test" class="col-sm-2 control-label">微调</label>
			    <div class="col-sm-10">
			         <input id="spinner" name="value" class="cnoj-num-spinner" />
			    </div>
			  </div>
			</div>
		</div>
		
		<div class="row">
		     <div class="col-sm-6">
			   <div class="form-group">
			    <label for="col-11" class="col-sm-2 control-label">自动完成关联</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control cnoj-auto-complete-relate" data-uri="op/autoComplete/menu_auto_complete_test.json"  id="col-11" />
			    </div>
			  </div>
			 </div>
			<div class="col-sm-6">
			   <div class="form-group">
			    <label for="col-12" class="col-sm-2 control-label">关联填写</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control" id="col-12" />
			    </div>
			  </div>
			</div>
		</div>
		
		
		<div class="row">
		     <div class="col-sm-6">
			   <div class="form-group">
			    <label for="col-21" class="col-sm-2 control-label">输入框下拉关联</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control cnoj-input-select-relate" data-uri="op/queryPage/menu_auto_complete_test.json"  id="col-21" />
			    </div>
			  </div>
			 </div>
			<div class="col-sm-6">
			   <div class="form-group">
			    <label for="col-22" class="col-sm-2 control-label">关联填写</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control" id="col-22" />
			    </div>
			  </div>
			</div>
		</div>
		
		<div class="row">
		     <div class="col-sm-6" id="test1">
			   <div class="form-group">
			    <label for="col-31" class="col-sm-2 control-label">关联填写</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control"  id="col-31" />
			    </div>
			  </div>
			 </div>
			<div class="col-sm-6" id="test2">
			   <div class="form-group">
			    <label for="col-32" class="col-sm-2 control-label">关联填写</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control" id="col-32" />
			    </div>
			  </div>
			</div>
		</div>
		
		<div class="row">
		     <div class="col-sm-6" id="test21">
			   <div class="form-group">
			    <label for="col-41" class="col-sm-2 control-label">关联填写</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control"  id="col-41" />
			    </div>
			  </div>
			 </div>
			<div class="col-sm-6" id="test22">
			   <div class="form-group">
			    <label for="col-42" class="col-sm-2 control-label">关联填写</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control" id="col-42" />
			    </div>
			  </div>
			</div>
		</div>
		
		<div class="row">
			 <div class="col-sm-6">
			    <div class="form-group">
				    <label for="auto-input-complete2" class="col-sm-2 control-label">自动完成URL</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control cnoj-auto-complete" id="auto-input-complete3"  
				      data-uri="op/autoComplete/menu_auto_complete_test.json" />
				    </div>
				  </div>
			 </div>
		</div>
	   </form>
   </div>
</div>
<!--  
<script type="text/javascript">
	var jsonStr=eval('[{"id":"test","value":"测试","label":"测试内容"},{"id":"test2","value":"测试2","label":"测试内容2"}]');
	$("#auto-complete2").autoComplete({jsonData:jsonStr,multiple:true});
</script>
-->
<script type="text/javascript">
 $(function(){
	/* $("#col-11").autoComplete({
		 uri:'op/autoComplete/menu_auto_complete_test.json',
		 selectCallback:function(event,data){
			 if(utils.isNotEmpty(data)) {
				 var otherValue = data.item.otherValue;
				 if(utils.isNotEmpty(otherValue)) {
					 var len = otherValue.length;
					var parentDiv = $("#col-11").parents(".form-group").parent();
					 for (var i = 0; i < len; i++) {
						 var $input = parentDiv.next().find(".form-control");
						 if(utils.isNotEmpty($input.attr("class"))) {
							 $input.val(otherValue[i]);
						 } else {
							 break;
						 }
						 parentDiv = $input.parents(".form-group").parent();
					}
				 }
			 }
		 }
	 });*/
 });
</script>
