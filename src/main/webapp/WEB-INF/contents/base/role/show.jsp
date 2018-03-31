<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content-dialog">
     <div class="panel-tabs-wrap m-n5 body-bg">
		<div class="panel-tabs-tab p-t-3">
			<ul class="nav nav-tabs" role="tablist">
				<li class="active"><a href="#role-has-config-menu-tab" role="presentation" data-toggle="tab">菜单</a></li>
				<li><a href="#role-has-config-res-tab" role="presentation" data-toggle="tab">资源</a></li>
			</ul>
		</div>
		<div class="tab-content panel-tab-content bg-color-white">
			<div role="tabpanel" class="m-t-n10 p-l-10 tab-pane active" id="role-has-config-menu-tab">
				<div class="cnoj-panel-tree" id="role-view-menu-tree" data-is-search="no" data-panel-height="300" data-uri="role/viewMenus.json?id=${id}" ></div>
			</div>
			<div role="tabpanel" class="m-t-n10 p-l-10 tab-pane" id="role-has-config-res-tab">
				<!--  
				<div class="cnoj-panel-tree" id="role-view-res-tree" data-is-search="no" data-panel-height="300" data-uri="role/viewRes?id=${id}&returnType=json" ></div>
				-->
				<div class="tree-content" style="height: 300px;">
				  <ul id="role-has-config-res-tree" class="ztree"></ul>
				</div>
			</div>
		</div>
	 </div>
</div><!-- wrap-content-dialog -->
<script type="text/javascript">
   setTimeout("loadJs()", 200);
   var IDMark_Switch = "_switch",
	IDMark_Icon = "_ico",
	IDMark_Span = "_span",
	IDMark_Input = "_input",
	IDMark_Check = "_check",
	IDMark_Edit = "_edit",
	IDMark_Remove = "_remove",
	IDMark_Ul = "_ul",
	IDMark_A = "_a";
   function loadJs() {
	   $.get('role/viewRes.json?id=${id}&returnType=json',function(data){
		   var jsonData = data;// $.parseJSON(data.output);
		   if(jsonData.result=='1') {
				  var setting = {check: {enable: false},data:{simpleData:{enable: true}},view: {addDiyDom: addDiyDom},callback:{beforeClick:function(treeId, treeNode) {
						var zTree = $.fn.zTree.getZTreeObj(treeId);
						zTree.checkNode(treeNode, !treeNode.checked, true, true);
						return false;
					}}};
				  $.fn.zTree.init($("#role-has-config-res-tree"), setting, jsonData.datas);
			   }
	   });
   }
   function addDiyDom(treeId, treeNode) {
	   if (treeNode.flag=="auth") {
		  $("#"+treeNode.tId+IDMark_Icon).remove();
		  $("#"+treeNode.tId).addClass("li-inline");
		  var pnId = treeNode.getParentNode().tId;
		  $("#"+pnId).next().addClass("clear");
	   } else {
		   var aObj = $("#"+treeNode.tId).addClass("clear");
	   }
   }
</script>