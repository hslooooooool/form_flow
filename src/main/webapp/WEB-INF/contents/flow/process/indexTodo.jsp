<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
    <div id="my-todo-index">
	    <div class="container-fluid list-content">
	      <div class="row">
	       <div class="line-loading m-t-10">正在加载数据...</div>
	      </div>
	  </div>
</div>
</div>
<script type="text/javascript">
   setTimeout("loadTodoJs()", 200);
   function loadTodoJs() {
	   loadingTodoData();
   }
   
   function loadingTodoData() {
	   var $listContent = $("#my-todo-index .list-content");
	   if($listContent.find(".line-loading").length == 0) 
		   $listContent.html("<div class=\"row\"><div class=\"line-loading m-t-10\">正在加载数据...</div></div>");
       $.post('process/indexTodo.json',function(output){
          var contents = '';
          if(output.result == '1') {
              var datas = output.datas;
              var a = '';
              for(var i=0;i<datas.length;i++) {
                  if(datas[i].isTake=='1') {
                     a = "<a href='#' class='take-task' data-title='处理待办' data-take-uri='process/takeTask.json?taskId="+datas[i].taskId+"&processId="+datas[i].processId+"&taskKey="+datas[i].taskKey+"' "+
                         "data-uri='"+datas[i].actionUrl+"?processId="+datas[i].processId+"&orderId="+datas[i].orderId+"&taskId="+datas[i].taskId+"&taskKey="+datas[i].taskKey+"'><span>"+datas[i].orderTitle+"</span></a>";
                  } else {
                     a = "<a href='#' class='handle-task' data-title='处理待办' data-uri='"+datas[i].actionUrl+"?processId="+datas[i].processId+"&orderId="+datas[i].orderId+"&taskId="+datas[i].taskId+"&taskKey="+datas[i].taskKey+"'><span>"+datas[i].orderTitle+"</span></a>";
                  }
                  contents +="<div class='row list-row'><div class='col-sm-10 list-col p-l-5 p-r-0' title='"+datas[i].orderTitle+"'>"+a+"</div>"+
                  "<div class='col-sm-2 list-col p-l-5 p-r-5 text-right' title='"+utils.handleTime(datas[i].taskCreateTime)+"'>"+utils.handleShortTime(datas[i].taskCreateTime)+"</div>"+
                  //"<div class='col-sm-3 list-col p-l-0 p-r-0 text-center' title='"+datas[i].nodeName+"'>"+a+"</div></div>";
                  "</div>";
              }
          } else {
              contents = "<div class='row'><div class='p-t-20 text-center'>没有待办任务</div></div>";
          }
          $("#my-todo-index .list-content").html(contents);
          contents = null;
          takeTaskListener();
          handleTaskListener();
       });
   }
   
   function handleTaskListener() {
	   $("#my-todo-index .handle-task").click(function(event) {
		   var uri = $(this).data("uri");
	       var title = $(this).data("title");
	       if (!utils.isEmpty(uri)) {
				openFlowTab(title, uri);
			}
			return false;
		});
   }
   
   function takeTaskListener() {
      $("#my-todo-index .take-task").click(function(){
    	  var takeUri = $(this).data("take-uri");
          var uri = $(this).data("uri");
          var title = $(this).data("title");
          if(!utils.isEmpty(takeUri) && !utils.isEmpty(uri)) {
	           BootstrapDialogUtil.confirmDialog("该待办需要先领取，确定要领取吗？",function(){
	        	   utils.waitLoading("正在领取，请稍后...");
					$.post(takeUri,function(data){
						utils.closeWaitLoading();
						var output = data;
						utils.showMsg(output.msg);
						if(output.result=='1') {
							openFlowTab(title, uri);
						} else {
							loadingTodoData();
						}
					});
				});
          }
          return false;
     });
   }
   
   /**
    * 提交表单后刷新首页上的待办任务（提交表单后会调用该方法）
    */
   function submitFormRefershIndexTodo() {
	   setTimeout(function() {
		   loadingTodoData();
	   }, 200);
   }
</script>