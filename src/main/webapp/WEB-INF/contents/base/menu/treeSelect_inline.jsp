<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="wrap-content-dialog">
   <div class="panel panel-default m-b-0">
	    <div class="panel-heading ui-widget-header"><strong>菜单列表</strong></div>
	        <div class="panel-body p-t-0 p-l-5 p-r-5 p-b-0 borer-top">
	         <ul id="role-config-menu-tree" class="ztree"></ul>
	    </div>
	</div>
</div>
<script type="text/javascript">
   var jsonData = '${output}';
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
	   jsonData = $.parseJSON(jsonData);
	   if(jsonData.result=='1') {
		  var setting = {check: {enable: true},data:{simpleData:{enable: true}},view: {addDiyDom:addDiyDom}};
		  $.fn.zTree.init($("#role-config-menu-tree"), setting, jsonData.data);
	   }
   }
   
   function addDiyDom(treeId, treeNode) {
	   if (treeNode.flag=="auth") {
		   var aObj = $("#"+treeNode.getParentNode().tId+IDMark_Ul);
		   var $first = aObj.children().first();
		  if(utils.isEmpty($first.find("#"+treeNode.tId+IDMark_Check).attr("id"))) {
			  $("#"+treeNode.tId+IDMark_Switch).remove();
		  }
		  $("#"+treeNode.tId+IDMark_Icon).remove();
		  $("#"+treeNode.tId).addClass("li-inline");
		  var pnId = treeNode.getParentNode().tId;
		  $("#"+pnId).next().addClass("clear");
	   } else {
		   var aObj = $("#"+treeNode.tId).addClass("clear");
	   }
   }
</script>