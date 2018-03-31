<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content-dialog">
  <form class="form-horizontal" id="submit-data" role="form">
           <input type="hidden"  id="layout-seq-num" value="${customIndex.cusIndexMinWins[0].sortOrder }" />
           <div class="form-group">
		    <label for="input01" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">显示内容</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control require input-select" name="uri" label-name="名称" readonly="readonly" id="input01" value="" />
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="input02" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">序号</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" name="sortOrder" label-name="序号" value="${customIndex.cusIndexMinWins[0].sortOrder }" id="input02" />
		    </div>
		  </div>
		
		  <div class="form-group m-b-5">
		    <div class="col-sm-11 text-right">
		        <button class="btn btn-primary" type="button" id="btn-submit"><i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
		    </div>
		  </div>
       </form>
</div>
<script type="text/javascript">
	setTimeout("dialogLoadJs()", 200);
	function dialogLoadJs() {
		$("#input01").inputSelect({
			uri:'minwin/items.json',
			paramName:"name",
			defaultValue:'${customIndex.cusIndexMinWins[0].minWinId}',
			isShow:false,
			isShowAll:false
		});
		$(".input-select").click(function(event){
			$(this).inputSelect({
				uri:'minwin/items.json',
				paramName:"name",
				defaultValue:'${customIndex.cusIndexMinWins[0].minWinId}',
				isShow:true,
				isShowAll:false
			});
			event.stopPropagation();
		});
		
		$("#btn-submit").click(function(){
			 if($("#submit-data").validateForm('bottom')) {
				var winId = $("#input01-value").val();
				var layoutSeqNum = $("#layout-seq-num").val();
				var cusSeqNum = $("#input02").val();
				setWinProp(layoutSeqNum,winId,cusSeqNum);
				BootstrapDialogUtil.close();
			}
		});
	}
</script>