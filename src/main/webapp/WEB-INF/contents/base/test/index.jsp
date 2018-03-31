<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="../include/common-header.jsp"></jsp:include>
<script type="text/javascript" >
  $(document).ready(function(){
  	$("#form-test").submit(function(){
	       var is = $("#form-test").validateForm();
	       if(is) {
	          BootstrapDialogUtil.infoAlert("验证成功!");
	       }
	       return false;
	   });
	  // autoHeight();
  });
  
  function autoHeight() {
     var h = $(window).height() - $(".wrap-footer").height() - $(".header").height()-13;
	 $("#cu-content").css({"height" : h + "px"});
  }
</script>
<div class="not-left-menu">
	<div class="wrap-content m-t-10">
	   <div class="container  p-t-15">
	      <fieldset>
	         <legend>测试表单验证</legend>
			   <form class="form-horizontal" role="form" id="form-test">
				  <div class="form-group">
				    <label for="input1" class="col-sm-2 control-label">学号</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" data-label-name="学号" data-regexp="/^\d+$/" id="input1" placeholder="&lt input class='require' data-lable-name='学号' data-length='8|11' data-format='num' /&gt" />
				    </div>
				  </div>
				   
				  <div class="form-group">
				    <label for="input2" class="col-sm-2 control-label">Email</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" data-label-name="Email" data-format="email" id="input2" 
				      placeholder="&lt input class='require' data-lable-name='Email' data-format='email' /&gt" />
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label for="input3" class="col-sm-2 control-label">IP地址</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" data-label-name="IP地址" data-format="ip"  id="input3" 
				      placeholder="&lt input class='require' data-lable-name='IP地址' data-format='ip' /&gt" />
				    </div>
				  </div>
				   
				  <div class="form-group">
				    <label for="input4" class="col-sm-2 control-label">姓名</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" data-label-name="姓名" data-format="chinese" data-length="2,5" id="input4" 
				      placeholder="&lt input class='require' data-lable-name='姓名' data-length='2,5' data-format='chinese'  /&gt"
				       />
				    </div>
				  </div>
				  
				  
				  <div class="form-group">
				    <label for="input5" class="col-sm-2 control-label">手机号</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" data-label-name="手机号" data-format="phone" id="input5" 
				       placeholder="&lt input class='require' data-lable-name='手机号' data-format='phone'  /&gt"
				       />
				    </div>
				  </div>
				  
				  
				  <div class="form-group">
				    <label for="input6" class="col-sm-2 control-label">QQ</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" data-label-name="QQ号" data-format="qq" id="input6" 
				      placeholder="&lt input class='require' data-lable-name='QQ号' data-format='qq'  /&gt"
				       />
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label for="input7" class="col-sm-2 control-label">身份证号</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" data-label-name="身份证号" data-format="card-no" id="input7" 
				      placeholder="&lt input class='require' data-lable-name='身份证号' data-format='card-no'  /&gt"
				       />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10">
				      <button type="submit" class="btn btn-default">确定</button>
				    </div>
				  </div>
			  </form>
		   </fieldset>
	  </div>
</div>
<jsp:include page="../include/footer.jsp" />
	