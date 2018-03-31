<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog">
      <div class="row p-t-5 m-l-0 m-r-0">
          <form class="form-horizontal" role="form" id="form-edit" method="post" action="user/changePwd.json">
                <div class="form-group m-b-10">
				    <label for="input1" class="col-sm-3 control-label fw-normal">当前用户</label>
				    <div class="col-sm-8 p-l-0">
				       <input class="form-control" autocomplete="off" id="input1" type="text" value="${userInfo.username }" disabled />
				    </div>
			    </div>
                <div class="form-group m-b-10">
				    <label for="input2" class="col-sm-3 control-label fw-normal">旧密码</label>
				    <div class="col-sm-8 p-l-0">
				      <input type="password" id="input2" autocomplete="off" class="form-control require" data-label-name="旧密码" placeholder="" name="oldPwd" />
				    </div>
			    </div>
			     <div class="form-group m-b-10">
				    <label for="new-pwd" class="col-sm-3 control-label fw-normal">新密码</label>
				    <div class="col-sm-8 p-l-0">
				      <input type="password" id="new-pwd" autocomplete="off" class="form-control require" data-label-name="新密码" placeholder="" name="newPwd" />
				    </div>
			    </div>
			     <div class="form-group m-b-10">
				    <label for="confirm-new-pwd" class="col-sm-3 control-label fw-normal">确认新密码</label>
				    <div class="col-sm-8 p-l-0">
				      <input type="password" id="confirm-new-pwd" autocomplete="off" class="form-control require" data-label-name="确认新密码" placeholder="" name="confirmNewPwd" />
				    </div>
			    </div>
			    <div class="form-group m-b-0">
				    <div class="col-sm-12 text-center">
				      <button type="submit" class="btn btn-primary"><i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
				    </div>
				 </div>
          </form>
      </div>
</div><!-- wrap-content-dialog -->
<script type="text/javascript">
setTimeout("loadJs()", 200);
var popoverTmp = null;
function loadJs() {
	listenerPwdFocus();
	$("#form-edit").submit(function(){
	   if($(this).validateForm()) {
	       var newPwd = $("#new-pwd").val();
	       var cnp  = $("#confirm-new-pwd");
	       var confirmNewPwd = cnp.val();
	       if(newPwd != confirmNewPwd) {
	          cnp.popover({placement:'top',
						content:"两次输入的密码不一致!",
						trigger:"manual"});
			  cnp.popover('show');
			  popoverTmp = cnp;
			  if(cnp.hasClass("form-control")) {
                 cnp.parent().addClass("has-error");
			 }
	       } else {
	          var params = $(this).serialize();
	          var uri = $(this).attr("action");
	          if(utils.trim(uri) != '') {
	        	  utils.waitLoading("正在提交数据...");
	              $.post(uri,params,function(data) {
	            	    utils.closeWaitLoading()
		                var output = data;//$.parseJSON(data.output);
		                if(output.result=='1') {
		                   BootstrapDialogUtil.close();
		                   utils.showMsg(output.msg);
		                } else if(output.data=='1') {
		                    cnp.popover({placement:'top',
							content:output.msg,
							trigger:"manual"});
						    cnp.popover('show');
						    if(cnp.hasClass("form-control")) {
                    	         cnp.parent().addClass("has-error");
					         }
						    popoverTmp = cnp;
		                } else if(output.data=='2') {
		                    var input = $("#input2");
		                    input.popover({placement:'top',
							content:output.msg,
							trigger:"manual"});
						    input.popover('show');
						    if(input.hasClass("form-control")) {
                    	         input.parent().addClass("has-error");
					         }
						    popoverTmp = input;
		                } else {
		                	utils.showMsg(output.msg);
		                }
	             });
	          }//uri
	       }//==
	   }//validateForm
	  return false;
	});
}

function listenerPwdFocus() {
      $("#new-pwd,#confirm-new-pwd").focus(function(){
	       if(null != popoverTmp) {
	           popoverTmp.popover('destroy');
	           if($(popoverTmp.hasClass("form-control"))) {
        	      popoverTmp.parent().removeClass("has-error");
		       }
	           popoverTmp = null;
	       }
	   });
}
</script>