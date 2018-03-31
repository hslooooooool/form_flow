<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog">
        <form class="form-horizontal" role="form" id="form-add" action="role/add.json">
            <div class="form-group m-b-10">
			    <label for="input01" class="col-sm-2 control-label">角色名称</label>
			    <div class="col-sm-9 p-l-0">
			      <input type="text" class="form-control require" name="name" data-label-name="角色名称" id="input01" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input02" class="col-sm-2 control-label">状态</label>
			    <div class="col-sm-9 p-l-0">
			       <select class="form-control" name="state" id="input02" >
			            <option value="1">有效</option>
					    <option value="0">无效</option>
			       </select>
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input03" class="col-sm-2 control-label">描述</label>
			    <div class="col-sm-9 p-l-0">
			      <textarea class="form-control" name="descr" rows="3" id="input03"></textarea>
			    </div>
			</div>
			<div class="form-group m-b-10">
			   <label for="input04" class="col-sm-2 control-label">角色权限</label>
			   <div class="col-sm-10 p-l-0">
			      <div class="panel panel-default m-b-0">
					    <div class="panel-tabs-wrap">
					       <div class="panel-heading p-0 p-t-3">
								<div class="panel-tabs-tab">
									<ul class="nav nav-tabs" role="tablist">
										<li class="active"><a href="#config-role-menu-tab" role="presentation" data-toggle="tab">菜单</a></li>
										<li><a href="#config-role-res-tab" role="presentation" data-toggle="tab">资源</a></li>
									</ul>
								</div>
							</div>
							<div class="panel-body p-0">
								<div class="tab-content panel-tab-content bg-color-white">
									<div role="tabpanel" class="tab-pane active" id="config-role-menu-tab">
										<div class="cnoj-load-url" data-uri="showPage/base_menu_treeSelect"></div>
									</div>
									<div role="tabpanel" class="tab-pane" id="config-role-res-tab">
										<div class="m-t-n10" id="role-config-res">
										   <div class="cnoj-load-url" data-uri="showPage/base_resource_selectResAuth"></div>
										</div>
									</div>
								</div>
							</div><!-- panel-body -->
					    </div>
					</div>
			   </div>
			</div>
			<div class="text-right p-t-3 p-r-10">
			      <button type="button" class="btn btn-primary" id="role-submit" data-refresh-uri="role/list">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
        </form>
</div><!-- wrap-content-dialog -->
<script type="text/javascript">
   setTimeout("loadRoleJs()", 200);
   function loadRoleJs() {
	   $("#role-submit").click(function(event){
		   var $form = $(this).parents("form");
		   if($form.validateForm({placement:"bottom"})) {
			   //获取菜单树
			   var $ul = $("#config-role-menu-tab").find("ul");
			   var treeId = $ul.attr("id");
			   var treeObj = $.fn.zTree.getZTreeObj(treeId);
			   var checkedNodes = treeObj.getCheckedNodes(true);
			   var menuParams = "";
			   if(checkedNodes.length>0) {
				   for(var i=0;i<checkedNodes.length;i++) {
					   menuParams = menuParams+"menuId="+checkedNodes[i].id+"&";
				   }
				   menuParams = menuParams.substring(0, menuParams.length-1);
		       }
			   
			   //获取资源树
			   treeObj = $.fn.zTree.getZTreeObj("role-config-res-tree");
			   checkedNodes = treeObj.getCheckedNodes(true);
			   var resAuthParams = "";
			   if(checkedNodes.length>0) {
				   for(var i=0;i<checkedNodes.length;i++) {
					   resAuthParams = resAuthParams+"treeProps["+i+"].id="+checkedNodes[i].id+"&"+
					   "treeProps["+i+"].name="+checkedNodes[i].name+"&"+
					   "treeProps["+i+"].parentId="+checkedNodes[i].pId+"&treeProps["+i+"].flag="+checkedNodes[i].flag+"&";
				   }
				   resAuthParams = resAuthParams.substring(0, resAuthParams.length-1);
			   }
			   
			   //提交数据
			   var params = $form.serialize();
			   if(menuParams.length>0)
				   params = params+"&"+menuParams;
			   if(resAuthParams.length>0) {
				   params = params+"&"+resAuthParams;
			   }
			   var uri = $form.attr("action");
			   cnoj.submitDialogData(uri,params,null,$(this),$form);
		   }
		   event.stopPropagation();
		   return false;
	   });
   }
</script>