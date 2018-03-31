<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<s:action name="include/header" executeResult="true" />
<script type="text/javascript" >
  $(document).ready(function(){
  	$("#form-test").submit(function(){
	       var is = checkForm("#form-test",'bottom');
	       if(is) {
	          BootstrapDialogUtil.infoAlert("验证成功!");
	       }
	       return false;
	   });
	   autoHeight();
  });
  
  function autoHeight() {
     var h = $(window).height() - $(".wrap-footer").height() - $(".header").height()-13;
	 $("#cu-content").css({"height" : h + "px"});
  }
</script>
<div id="cu-content" class="not-left-menu">
	<div class="wrap-content">
	   <div class="container p-t-15">
	      <fieldset>
	         <legend>测试表单验证</legend>
			   <form class="form-horizontal" role="form" id="form-test">
				  <div class="form-group">
				    <label for="input1" class="col-sm-2 control-label">学号</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" label-name="学号" length="8|11" data-format="num"
				        id="input1" placeholder="&lt input class='require' lable-name='学号' length='8|11' data-format='num' /&gt" />
				    </div>
				  </div>
				   
				  <div class="form-group">
				    <label for="input2" class="col-sm-2 control-label">Email</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" label-name="Email" data-format="email" id="input2" 
				      placeholder="&lt input class='require' lable-name='Email' data-format='email' /&gt" />
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label for="input3" class="col-sm-2 control-label">IP地址</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" label-name="IP地址" data-format="ip"  id="input3" 
				      placeholder="&lt input class='require' lable-name='IP地址' data-format='ip' /&gt" />
				    </div>
				  </div>
				   
				  <div class="form-group">
				    <label for="input4" class="col-sm-2 control-label">姓名</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" label-name="姓名" data-format="chinese" length="2,5" id="input4" 
				      placeholder="&lt input class='require' lable-name='姓名' length='2,5' data-format='chinese'  /&gt"
				       />
				    </div>
				  </div>
				  
				  
				  <div class="form-group">
				    <label for="input5" class="col-sm-2 control-label">手机号</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" label-name="手机号" data-format="phone" id="input5" 
				       placeholder="&lt input class='require' lable-name='手机号' data-format='phone'  /&gt"
				       />
				    </div>
				  </div>
				  
				  
				  <div class="form-group">
				    <label for="input6" class="col-sm-2 control-label">QQ</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" label-name="QQ号" data-format="qq" id="input6" 
				      placeholder="&lt input class='require' lable-name='QQ号' data-format='qq'  /&gt"
				       />
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label for="input7" class="col-sm-2 control-label">身份证号</label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control require" label-name="身份证号" data-format="card-no" id="input7" 
				      placeholder="&lt input class='require' lable-name='身份证号' data-format='card-no'  /&gt"
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
	