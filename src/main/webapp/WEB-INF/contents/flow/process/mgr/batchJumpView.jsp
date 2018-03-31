<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="wrap-content">
   <div class="p-t-15 p-l-15 p-r-15">
	<form id="batch-jump-form" class="form-horizontal" role="form" method="post" action="process/mgr/batchJump.json">
		       <input type="hidden" name="nextAssigner" id="batch-jump-next-assigner" />
		       <input type="hidden" name="isBack" id="batch-jump-is-back" />
		       <div class="form-group">
		            <label class="col-sm-2 control-label p-r-5" for="batch-jump-process-name">选择流程：</label>
		            <div class="col-sm-10 p-l-0">
			            <select class="form-control" name="processId" id="batch-jump-process">
					        <c:forEach var="process" items="${processList}" varStatus="st">
					           <option id="batch-jump-process-${process.id }" value="${process.id }">${process.displayName }（版本号:${process.version }）</option>
						    </c:forEach>
					    </select>
					</div>
		        </div>
		        <div class="form-group">
		            <label class="col-sm-2 control-label p-r-5" for="batch-jump-node-name">选择跳转节点：</label>
		            <div class="col-sm-10 p-l-0">
			            <select class="form-control" name="jumpNodeName" id="batch-jump-node">
					        <c:forEach var="jumpNodeInfo" items="${jumpNodeInfos}" varStatus="st">
					           <option id="next-line-${jumpNodeInfo.value }" data-select-style="${jumpNodeInfo.selectStyle}" value="${jumpNodeInfo.value }" data-is-select="${jumpNodeInfo.isSelectAssigner }" data-is-back="${jumpNodeInfo.isBackNode }">${jumpNodeInfo.name }</option>
						    </c:forEach>
					    </select>
					</div>
		        </div>
		        <div class="form-group">
				    <label class="col-sm-2 control-label p-r-5" for="batch-content">项目编号：</label>
				    <div class="col-sm-10 p-l-0">
				    	<textarea class="form-control require" data-label-name="项目编号" rows="8" id="batch-content" name="batchContent" placeholder="请填写项目编号"></textarea>
				    	<span class="help-block">注：多个项目编号用英文分号“;”分隔；如：1;2;3</span>
					</div>
				</div>
		       
		        <div  class="form-group hide" id="batch-jump-next-assigner-wrap">
		            <label class="col-sm-2 control-label p-r-5" for="jump-node-name">下一步处理者：</label>
		            <div class="col-sm-10 p-l-0">
		            	<div id="select-assigner-content"></div>
		            </div>
		        </div>
		        <div class="form-group">
				    <label class="col-sm-2 control-label p-r-5" for="handle-suggest">处理意见：</label>
				    <div class="col-sm-10 p-l-0">
				    	<textarea class="form-control" data-label-name="处理意见" rows="5" id="handle-suggest" name="handleSuggest" placeholder="请填写处理意见"></textarea>
					</div>
				</div>
		<div class="form-group text-center m-b-20">
			<button type="button" class="btn btn-primary" data-refresh-uri="${currentUri }" id="batch-jump-data-submit"><i class="glyphicon glyphicon-ok-sign"></i> 提交 </button>
		</div>
	 </form>
   </div>
   <script type="text/javascript">
        var isAssigner = false;
   		$(function(){
   			selectProcessListener();
   			var $selectedOpt = $("#batch-jump-node-name option:selected");
   			handleNextAssigner($selectedOpt);
   			selectNodeListener();
   			//点击提交按钮
   			$("#batch-jump-data-submit").click(function(){
   				var isSubmit = true;
   				if(isAssigner) {
   					var nextAssigner = getCheckedAssigners();
   					if(utils.isNotEmpty(nextAssigner)) {
   						$("#batch-jump-next-assigner").val(nextAssigner);
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
   		 * 选择流程监听
   		 */
   		function selectProcessListener() {
   			$("#batch-jump-process").change(function(){
   				var value = $(this).val();
   				var url = "process/mgr/jumpNodeInfo.json?processId="+value;
   				$.get(url,function(datas){
   					var output = datas;
   					if(output.result == '1') {
   						$("#batch-jump-node").find("option").remove();
   						var datas = output.datas;
   						var len = datas.length;
   						var options = '';
   						for(var i=0; i<len;i++) {
   							options += '<option id="next-line-'+datas[i].value+'" data-select-style="'+datas[i].selectStyle+'" value="'+datas[i].value+'" data-is-select="'+datas[i].isSelectAssigner+'" data-is-back="'+datas[i].isBackNode+'">'+datas[i].name+'</option>';
   						}
   						$("#batch-jump-node").html(options);
   						selectNodeListener();
   					}//if
   				});
   			});
   		}
   		
   		/**
   		 * 选择节点监听
   		 */
   		function selectNodeListener() {
   			$("#batch-jump-node").unbind('change');
   			$("#batch-jump-node").change(function(){
   				var $selectedOpt = $("#batch-jump-node option:selected");
   				var selectUserTreeId = $selectedOpt.attr("id")+"-user-org-tree";
   				if(utils.isEmpty($("#"+selectUserTreeId).attr("id"))) {
   					//摧毁之前建立的树
   					var beforeTreeId = null;
   					$("#batch-jump-node option").each(function(){
   						beforeTreeId = $(this).attr("id")+"-user-org-tree";
   						$("#"+beforeTreeId).zTreeUtil({destory:true});
   					});
   					$("#select-assigner-content").html("");
   				}
   				handleNextAssigner($selectedOpt);
   			});
   		}
   		
   		/**
   		 * 处理跳转节点处理人
   		 * @param $selectedOpt
   		 */
   		function handleNextAssigner($selectedOpt) {
   			var value = $selectedOpt.attr("value");
   			var isSelect = $selectedOpt.data("is-select");
   			var isBack = $selectedOpt.data("is-back");
   			var selectStyle = $selectedOpt.data("select-style");
   			var processId = $("#batch-jump-process").val();
   			$("#jump-is-back").val(isBack);
   			if(isSelect == utils.YES_OR_NO.YES) {
   				$("#batch-jump-next-assigner-wrap").removeClass("hide");
   				createNextAssignerTree($selectedOpt,processId,"",selectStyle);
   				isAssigner = true;
   			} else {
   				$("#batch-jump-next-assigner-wrap").addClass("hide");
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
   			var params = "processId="+processId+"&taskKey="+taskKey;
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
   			var $selectedOpt = $("#batch-jump-node option:selected");
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
   						}//if
   					}//fun
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