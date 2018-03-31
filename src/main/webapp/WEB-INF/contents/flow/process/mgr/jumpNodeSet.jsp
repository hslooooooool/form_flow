<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="wrap-content-dialog">
   <div class="p-l-8">
      <c:if test="${smartResp.result=='1' }">
		   <form id="jump-node-form" role="form" action="process/mgr/jumpToTask.json" target="#process-order-tab" >
		       <input type="hidden" name="orderId" value="${orderId }" />
		       <input type="hidden" name="processId" value="${processId }" />
		       <input type="hidden" name="nextAssigner" id="jump-next-assigner" />
		       <input type="hidden" name="isBack" id="jump-is-back" />
		       <div class="form-group">
		            <label for="jump-node-name">选择跳转节点：</label>
		            <select class="form-control require" name="jumpNodeName" id="jump-node-name">
				        <c:forEach var="jumpNodeInfo" items="${smartResp.datas}" varStatus="st">
				           <option id="next-line-${jumpNodeInfo.value }" data-select-style="${jumpNodeInfo.selectStyle}" value="${jumpNodeInfo.value }" data-is-select="${jumpNodeInfo.isSelectAssigner }" data-is-back="${jumpNodeInfo.isBackNode }">${jumpNodeInfo.name }</option>
					    </c:forEach>
				    </select>
		        </div>
		        <div class="form-group hide" id="jump-next-assigner-wrap">
		            <label for="jump-node-name">下一步处理者：</label>
		            <div id="select-assigner-content"></div>
		        </div>
		        <div class="form-group">
				    <label for="handle-suggest">处理意见：</label>
				    <textarea class="form-control require" data-label-name="处理意见" rows="5" id="handle-suggest" name="handleSuggest" placeholder="请填写处理意见"></textarea>
				</div>
				<div class="form-group text-center m-b-n5">
				<button type="button" class="btn btn-primary" id="jump-data-submit" data-refresh-uri="${refreshUri }"><i class="glyphicon glyphicon-ok-sign"></i> 提交 </button>
				</div>
		   </form>
	   </c:if>
   </div>
   <script type="text/javascript">
        var isAssigner = false;
   		$(function(){
   			var $selectedOpt = $("#jump-node-name option:selected");
   			handleNextAssigner($selectedOpt);
   			$("#jump-node-name").change(function(){
   				$selectedOpt = $("#jump-node-name option:selected");
   				var selectUserTreeId = $selectedOpt.attr("id")+"-user-org-tree";
   				if(utils.isEmpty($("#"+selectUserTreeId).attr("id"))) {
   					//摧毁之前建立的树
   					var beforeTreeId = null;
   					$("#jump-node-name option").each(function(){
   						beforeTreeId = $(this).attr("id")+"-user-org-tree";
   						$("#"+beforeTreeId).zTreeUtil({destory:true});
   					});
   					$("#select-assigner-content").html("");
   				}
   				handleNextAssigner($selectedOpt);
   			});
   			//点击提交按钮
   			$("#jump-data-submit").click(function(){
   				var isSubmit = true;
   				if(isAssigner) {
   					var nextAssigner = getCheckedAssigners();
   					if(utils.isNotEmpty(nextAssigner)) {
   						$("#jump-next-assigner").val(nextAssigner);
   					} else {
   						isSubmit = false;
   					}
   				}//if
   				if(isSubmit) {
   					var $form = $(this).parents("form:eq(0)");
   					if($form.validateForm()) {
   						var param = $form.serialize();
   					    var uri = $form.attr("action");
   					    cnoj.submitDialogData(uri,param,null,$(this),$form);
   					}
   				}
   				return false;
   			}); 
   		});
   		
   		/**
   		 * 处理跳转节点处理人
   		 * @param $selectedOpt
   		 */
   		function handleNextAssigner($selectedOpt) {
   			var value = $selectedOpt.attr("value");
   			var isSelect = $selectedOpt.data("is-select");
   			var isBack = $selectedOpt.data("is-back");
   			var selectStyle = $selectedOpt.data("select-style");
   			$("#jump-is-back").val(isBack);
   			if(isSelect == utils.YES_OR_NO.YES) {
   				$("#jump-next-assigner-wrap").removeClass("hide");
   				createNextAssignerTree($selectedOpt,"${processId}","${orderId}",selectStyle);
   				isAssigner = true;
   			} else {
   				$("#jump-next-assigner-wrap").addClass("hide");
   				isAssigner = false;
   			}
   		}
   		
   		/**
   		 * 
   		 */
   		function createNextAssignerTree(tagObj,processId,orderId,selectStyle) {
   			var selectUserTreeId = tagObj.attr("id")+"-user-org-tree";
   			if(utils.isEmpty($("#"+selectUserTreeId).attr("id"))) {
   			    $("#select-assigner-content").append("<div id='"+selectUserTreeId+"'><div class='next-title ui-state-default'>"+tagObj.text()+"</div></div>");
   			}
   			var taskKey = tagObj.val();
   			var params = "processId="+processId+"&orderId="+orderId+"&taskKey="+taskKey;
   			var url = "process/selectNextAssigner.json?"+params;
   			var checkOpt = null;
   			if(selectStyle == 'radio') {
   				checkOpt = {check: {
   					enable: true,
   					chkStyle: "radio",
   					radioType: "all"
   				}};
   			}
   			$("#"+selectUserTreeId).zTreeUtil({
   				uri:url,
   				isCheck:true,
   				isAjaxAsync:false,
   				isSearch:false,
   				isLoading:true,
   				checkOpt:checkOpt,
   				callback:function(zTreeObj){
   					if(selectStyle=='checkbox' && !isShow) 
   						zTreeObj.checkAllNodes(true);
   				}
   			});
   		}
   		
   		/**
   		 * 获取选中的参与者
   		 * @param $selectedOpt
   		 */
   		function getCheckedAssigners() {
   			var $selectedOpt = $("#jump-node-name option:selected");
   			var checkedAssigner = null;
   			var selectUserTreeId = $selectedOpt.attr("id")+"-user-org-tree";
   			$("#"+selectUserTreeId).zTreeUtil({
   					getTreeObj:function(zTreeObj){
   						if(null != zTreeObj) {
   							var nodes = zTreeObj.getCheckedNodes(true);
   							if(null != nodes && nodes.length>0) {
   								checkedAssigner = "";
   								for (var i = 0; i < nodes.length; i++) {
   									checkedAssigner += nodes[i].id+",";
   								}
   								checkedAssigner = checkedAssigner.substring(0,checkedAssigner.length-1);
   							}
   						}
   					}
   			});
   			if(utils.isEmpty(checkedAssigner)) {
   				$("#"+selectUserTreeId).addClass("border-color-red");
   				utils.showMsg("请选择下一步处理者！");
   				setTimeout(function(){
   			    		$("#"+selectUserTreeId).removeClass("border-color-red");
   				}, 2000);
   			}
   			return checkedAssigner;
   		}
   </script>
</div>