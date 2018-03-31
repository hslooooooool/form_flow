<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <div class="wrap-content-dialog">
	<div class="container-fluid" id="assignee-dialog-wrap">
	    <c:if test="${flag=='assignee' }">
	        <div class="row m-b-5 checkbox-align" id="create_user">
               <label class="cursor-pointer"><input type="checkbox" ${selectedValues=='create_user'?'checked':'' } id="assignee_flow_create_user" value="create_user" /> 起草人</label>
            </div>
	    </c:if>
	   <div class="row ${(flag=='assignee' && selectedValues == 'create_user')?'hidden':'' }" id="assignee-select-config">
	      <div class="col-sm-6 p-r-0 p-l-0">
	          <div class="panel panel-default">
	               <div class="panel-heading ui-widget-header">未选择的参与者</div>
	               <div class="panel-body p-0 p-t-5 body-bg borer-top" id="assignee-select-data">
	                   <div class="panel-tabs-wrap">
							<div class="panel-tabs-tab">
								<ul class="nav nav-tabs" role="tablist">
								   <li class="active"><a href="#select-user-tab" role="presentation" data-toggle="tab"> 用户列表</a></li>
								   <li><a href="#select-org-tab" role="presentation" data-toggle="tab"> 部门列表</a></li>
								   <li><a href="#select-position-tab" role="presentation" data-toggle="tab"> 职位列表</a></li>
								</ul>
						  	</div>
						  	<div class="tab-content panel-tab-content bg-color-white p-0">
								<div role="tabpanel" class="tab-pane active" data-prefix-flag="u_" id="select-user-tab">
								    <div class="cnoj-panel-check-tree p-t-5" data-uri="user/orgTree.json" data-is-search="yes" id="select-user-tree"></div>
								</div>
								<div role="tabpanel" class="tab-pane" data-prefix-flag="d_" id="select-org-tab">
								    <div class="cnoj-panel-check-tree p-t-5" data-uri="org/tree.json?treeType=department" data-is-search="yes" id="flow-select-org-tree"></div>
								</div>
								<div role="tabpanel" class="tab-pane" data-prefix-flag="p_" id="select-position-tab">
								    <div class="cnoj-panel-check-tree p-t-5" data-uri="position/orgTree.json" data-is-search="yes" id="select-position-tree"></div>
								</div>
							</div>
					  </div>
	               </div><!-- panel-body -->
	          </div><!-- panel -->
	      </div><!-- cols-sm-* -->
	      <div class="col-sm-1 p-r-0 p-l-0">
	         <div class="btn-item">
	            <p><button type="button" class="btn btn-default" id="add-assignee" title="添加">&gt;&gt;</button></p>
	            <p><button type="button" class="btn btn-default" id="del-assignee" title="删除">&lt;&lt;</button></p>
	         </div>
	      </div>
	      <div class="col-sm-5 p-r-0 p-l-0">
	           <div class="panel panel-default">
	               <div class="panel-heading ui-widget-header">已选择的参与者</div>
	               <div class="panel-body p-0 p-t-5 borer-top" id="assignee-selected-data"></div>
	          </div>
	      </div>
	   </div>
	</div>
</div><!-- wrap-content-dialog -->
<script type="text/javascript">
  var treeParentIds = ["select-user-tree","flow-select-org-tree","select-position-tree"];
  var treeIds = new Array(3);
  //监听树是否全部生成完成
  var listenerId = setInterval(function(){
	  var isExits = true;
	  for(var i=0; i<treeParentIds.length;i++) {
		  if($("#"+treeParentIds[i]).find("ul.ztree").attr("loaded") == 1) {
			  isExits = isExits && true;
			  treeIds[i] = $("#"+treeParentIds[i]).find("ul.ztree").attr("id");
		   } else {
			  isExits = isExits && false;
			  break;
		   }
	  }
	  if(isExits) {
		  clearInterval(listenerId);
		  loadDialogJs();
	  }
  }, 300);
   function loadDialogJs() {
	   var selectedValues = '${selectedValues}';
	   if(!utils.isEmpty(selectedValues) && "create_user" != selectedValues) {
		   var idArray = selectedValues.split(",");
		   $("#assignee-select-config").removeClass("hidden");
		   initDefaultValues(idArray);
	   } else if("create_user" == selectedValues) {
		   $("#assignee_flow_create_user").prop("checked",true);
	   }
	   var $assigneeCreateUser = $("#assignee_flow_create_user");
	   if(!utils.isEmpty($assigneeCreateUser)) {
		   if($assigneeCreateUser.prop("checked")) {
			   $("#assignee-select-config").addClass("hidden");
			} else {
				$("#assignee-select-config").removeClass("hidden");
			}
		   $assigneeCreateUser.click(function(){
				if($(this).prop("checked")) {
					$("#assignee-select-config").addClass("hidden");
				} else {
					$("#assignee-select-config").removeClass("hidden");
				}
			});
	   } else {
		   $("#assignee-select-config").removeClass("hidden");
	   }
	   btnItemListener();
   }
   
   
   /**
    * 监听增、删按钮(点击事件)
    *
    */
   function btnItemListener() {
	   $("#add-assignee").click(function(){
		   var activeTabId = getCurrentSelectedTree();
		   if(!utils.isEmpty(activeTabId)) {
			    var activeTreeId = $("#"+activeTabId).find("ul.ztree").attr("id");
				var treeObj = $.fn.zTree.getZTreeObj(activeTreeId);
				var nodes = treeObj.getCheckedNodes(true);
				var prefixFlag = $("#"+activeTabId).parent().data("prefix-flag");
			    if(nodes.length>0) {
			    	var showNameArray = new Array();
			    	var id = '';
			    	for(var i=0;i<nodes.length;i++) {
			    		if(nodes[i].isParent)
			    			continue;
			    		id = prefixFlag+nodes[i].id;
			    		showNameArray[id] = nodes[i].name;
			    		treeObj.checkNode(nodes[i],false);
			    		treeObj.setChkDisabled(nodes[i], true);
			    	}
			    	addDataToHasSelectedData(showNameArray);
			    } else {
			    	utils.showMsg("请选择数据！");
			    	$("#"+activeTabId).parents(".panel").addClass("border-color-red");
			    	setTimeout(function(){
			    		$("#"+activeTabId).parents(".panel").removeClass("border-color-red");
					}, 2000);
			    }
		   }
		   return false;
	   });
       $("#del-assignee").click(function(){
    	    var checkedValues = new Array();
	   		var checkedNames = new Array();
	   		$("#assignee-selected-data input[type='checkbox']:checked").each(function(){
	   			checkedValues.push($(this).val());
	   			checkedNames.push($(this).text());
	   		});
	   		if(checkedValues.length<1) {
	   			utils.showMsg("请选择要删除的数据！");
	   			$("#assignee-selected-data").parents(".panel").addClass("border-color-red");
	   			setTimeout(function(){
	   				$("#assignee-selected-data").parents(".panel").removeClass("border-color-red");
	   			}, 2000);
	   		} else {
	   			removeDataFromHasSelectedData(checkedValues);
	   		}
		   return false;
	   });
   }
   
   
   /**
    * 获取当前选中树的ID
    */
   function getCurrentSelectedTree() {
   	var $activeTab = $("#assignee-select-data .tab-content .active");
   	var activeTabId = $activeTab.attr("id");
   	if(!utils.isEmpty(activeTabId)) {
   		activeTabId = $("#"+activeTabId).find(".cnoj-panel-check-tree").attr("id");
   	} else {
   		activeTabId = null;
   	}
   	return activeTabId;
   }

   /**
    * 添加数据到已选择域内
    * @param showNameArray
    */
   function addDataToHasSelectedData(showNameArray) {
   	var html = "";
   	if(null != showNameArray && typeof(showNameArray) != 'undefined') {
   		for(var id in showNameArray) {
   			html += "<div class='has-selected-data checkbox-align' id='"+id+"'><label class='fw-normal'>"+
   			"<input type='checkbox' value='"+id+"'/> "+showNameArray[id]+"</label></div>";
   		}
   	}
   	if(!utils.isEmpty(html))
   	   $("#assignee-selected-data").append(html);
   }

   /**
    * 删除数据，从已选中的数据区域中删除数据
    * @param idArray
    */
   function removeDataFromHasSelectedData(idArray) {
   	  if((idArray instanceof Array) && idArray.length>0) {
   		var treeObj = null;
   		var node = null;
   		for(var i=0;i<idArray.length;i++) {
   			for(var j=0; j<treeIds.length;j++) {
   				treeObj = $.fn.zTree.getZTreeObj(treeIds[j]);
   				node = treeObj.getNodeByParam("id",handleId(idArray[i]));
   				if(!utils.isEmpty(node) && !node.isParent) {
   					treeObj.setChkDisabled(node,false);
   					$("#"+idArray[i]).remove();
   					break;
   			    }
   			}
   		}
   		treeObj = null;
   		node = null;
   	 }
   }
   
   /**
    * 初始化默认值
    * @param idArray
    */
   function initDefaultValues(idArray) {
	   if((idArray instanceof Array) && idArray.length>0) {
		   var treeObj = null;
		   var node = null;
		   var showNameArray = new Array();
		   for(var i=0;i<idArray.length;i++) {
			   for(var j=0; j<treeIds.length;j++) {
				   treeObj = $.fn.zTree.getZTreeObj(treeIds[j]);
				   node = treeObj.getNodeByParam("id",handleId(idArray[i]));
				   if(!utils.isEmpty(node) && !node.isParent) {
					   treeObj.setChkDisabled(node,true);
					   showNameArray[idArray[i]] = node.name;
					   break;
					}
	  			}//for;
	  	   }//for;
	  	   addDataToHasSelectedData(showNameArray);
		   treeObj = null;
		   node = null;
	   }
   }
   
   /**
    * 处理ID值
    */
   function handleId(value) {
	   return value.substring(2, value.length)
   }
</script>