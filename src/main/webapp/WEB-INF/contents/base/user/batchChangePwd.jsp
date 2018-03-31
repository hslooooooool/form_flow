<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="wrap-content-dialog">
      <div class="p-t-15">
          <form class="form-horizontal" role="form" id="batch-change-pwd" method="post" action="user/batchChangePwd.json">
                 <input type="hidden" name="id" value="${id}" />
			     <div class="form-group">
				    <label for="input01" class="col-sm-3 control-label p-r-5">新密码</label>
				    <div class="col-sm-8 p-l-5">
				      <input type="password" id="input01" autocomplete="off" class="form-control require" data-label-name="新密码"  name="newPwd" />
				    </div>
			    </div>
			    
			    <div class="form-group">
				    <label for="input02" class="col-sm-3 control-label p-r-5">确认密码</label>
				    <div class="col-sm-8 p-l-5">
				      <input type="password" id="input02" autocomplete="off"  class="form-control require" data-label-name="确认密码"  name="confirmNewPwd" />
				    </div>
			    </div>
			    <div class="form-group">
				    <div class="col-sm-12 text-center">
				      <button type="submit" class="btn btn-primary"><i class="glyphicon glyphicon-ok-sign"></i> 确定&nbsp;</button>
				    </div>
				 </div>
          </form>
      </div>
</div><!-- wrap-content-dialog -->
<script type="text/javascript">
setTimeout("loadJs()", 200);
function loadJs() {
	$("#batch-change-pwd").submit(function() {
	    if($(this).validateForm()) {
	    	var newPwd = $("#input01").val();
	    	var confirmNewPwd = $("#input02").val();
	    	if(newPwd != confirmNewPwd) {
	    		$("#input02").popover({placement:"bottom",
					content:"两次输入的密码不一致！",
					trigger:"manual"});
	    		$("#input02").popover('show');
	    		$(document).click(function(){
		    		$("#input02").popover('destroy');
	    		});
	    		return false;
	    	}
			var list = $(this).serialize();
			var uri = $(this).attr("action");
			utils.waitLoading("正在提交数据...");
			$.post(uri, list,function(data) {
				utils.closeWaitLoading();
				var output = data;
				utils.showMsg(output.msg);
				if(output.result == '1') {
					BootstrapDialogUtil.close();
				}
			 });
		 }
		return false;
	});
}
</script>