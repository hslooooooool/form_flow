<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/flow.simple.tree.js"></script>
<div class="wrap-content">
    <div class="panel panel-default my-todo">
       <div class="panel-search">
           <form class="form-inline search-param cnoj-entry-submit" method="post" role="form" action="process/todo" target="${target }">
               <div class="form-group p-r-10">
				  <label for="search-input01">项目名称：</label>
				  <input type="text" class="form-control input-form-sm-control" style="width: 400px" id="search-input01" name="title" placeholder="请输入项目名称" value="${queryFilter.title }"/>
			  </div>
			  <div class="form-group">
				<button type="button" class="btn btn-primary btn-sm cnoj-search-submit">
					<i class="glyphicon glyphicon-search"></i>
					搜索
				</button>
			 </div>
           </form>
        </div>
       <div class="cnoj-table-wrap">
	     <table class="table table-bordered table-condensed font-size-12">
	        <thead>
	        <tr class="ui-state-default">
	           <th>标题</th>
	           <th class="text-center" style="width: 150px;">当前环节</th>
	           <th class="text-center" style="width: 130px;">起草人</th>
	           <th class="text-center" style="width: 130px;">所在部门</th>
	           <th class="text-center" style="width: 130px;">到达时间</th>
	           <th class="text-center" style="width: 80px;">流程图</th>
	        </tr>
	       </thead>
	       <tbody>
	       <c:choose>
		        <c:when test="${smartResp.result != '1'}">
		             <tr>
		                <td colspan="6" class="text-center">没有待办任务</td>
		             </tr>
		        </c:when>
		        <c:otherwise>
		           <c:forEach var="todoClassify" items="${smartResp.datas }">
		               <tr class="expand-data tr-parent-tree tr-tree" id="${todoClassify.id}">
		                  <td colspan="6" class="tr-parent-tree-td"><span class='ui-icon ui-icon-triangle-1-s left'></span> ${todoClassify.name }</td>
		               </tr>
		               <c:forEach var="workItem" items="${todoClassify.datas}">
		               <tr class="tr-tree tr-sub-tree ${todoClassify.id}">
		                  <td class="tr-sub-tree-td">
		                  <c:choose>
		                    <c:when test="${workItem.isTake=='1' }">
		                       <a href="#" class="take-task" data-title="处理待办" data-take-uri='process/takeTask?processId=${workItem.processId}&taskId=${workItem.taskId}&taskKey=${workItem.taskKey}' 
		                       data-uri="${workItem.actionUrl}?processId=${workItem.processId}&orderId=${workItem.orderId}&taskId=${workItem.taskId}&taskKey=${workItem.taskKey}&refreshUrl=${refreshUrl}">${workItem.orderTitle }</a>
		                    </c:when>
		                    <c:otherwise>
		                       <a href="javascript:void(0);" class="handle-task" data-title="处理待办" data-uri="${workItem.actionUrl}?processId=${workItem.processId}&orderId=${workItem.orderId}&taskId=${workItem.taskId}&taskKey=${workItem.taskKey}&refreshUrl=${refreshUrl}">${workItem.orderTitle }</a>
		                    </c:otherwise>
		                  </c:choose>
		                  </td>
		                  <td class="text-center">${workItem.taskName }</td>
		                  <td class="text-center">${workItem.fullName }</td>
		                  <td class="text-center">${workItem.orgName }</td>
		                  <td class="text-center" title="${workItem.taskCreateTime }">${fn:substring(workItem.taskCreateTime,0,10)}</td>
		                  <td class="text-center">
		                    <button type="button" class="btn btn-info btn-xs cnoj-open-blank" data-title="查看流程图" data-uri="process/view?processId=${workItem.processId }&orderId=${workItem.orderId}"><i class="glyphicon glyphicon-picture"></i> 查看</button>
		                  </td>
		               </tr>
		               </c:forEach>
		           </c:forEach>
		           </c:otherwise>
	        </c:choose>
	       </tbody>
	    </table>
	  </div>
	  <cnoj:panelFooter smartResp="${smartResp }" refreshBtn="${refreshBtn}" page="${pageParam }" currentUri="${currentUri }" />
	</div>
</div>
<script type="text/javascript">
   setTimeout("loadJs()", 200);
   function loadJs() {
	   handleTaskListener();
	   simpleTreeListener(".my-todo ");
	   takeTaskListener();
   }
   function handleTaskListener() {
	   $(".my-todo .handle-task").click(function() {
		   var uri = $(this).data("uri");
	       var title = $(this).data("title");
	       if (!utils.isEmpty(uri)) {
				openFlowTab(title, uri);
			}
			return false;
		});
   }
   function takeTaskListener() {
	   $(".my-todo .take-task").click(function(){
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
    * 判断是否存在我的待办tab
    */
   function isExistMyTodoTab() {
	   return true;
   }
   
   /**
    * 刷新我的待办（提交表单后会调用该方法）
    */
   function todoRefresh(refreshUrl) {
	   setTimeout(function() {
		   var title = "我的待办";
			$('#main-tab').tabs('select',title);
			var $panel = getActiveTabPanel();
			var param = $panel.find(".panel-search form").serialize();
			refreshUrl = refreshUrl+(refreshUrl.indexOf("?")>-1?"&":"?")+"1=1";
			if(utils.isNotEmpty(param)) {
				refreshUrl = refreshUrl + "&"+param;
			}
			var $page = $panel.find(".pagination");
			if($page.length > 0 ) {
				refreshUrl = refreshUrl+"&page="+$page.find("li.active").text();
			}
			reloadTab(refreshUrl);
	   }, 200);
   }
</script>