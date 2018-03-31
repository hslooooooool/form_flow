/**
 * 流程设计
 */
var FlowDesigner = function(){}

/**
 * 初始化流程设计器中工具栏的高度
 */
FlowDesigner.initToolsHeight = function() {
	var height = $(window).height();
	$("#toolbox-content").css({"max-height":(height-50)+"px"});
}

/**
 * 工具栏缩放监听
 */
FlowDesigner.toolToggleListener = function() {
	var $a = null;
	$('#toolbox-accordion').on('shown.bs.collapse', function () {
		$a = $("#toolbox-handing").find("a");
		var $icon = $a.find(".pull-right .fa");
		$icon.removeClass("fa-caret-down");
		$icon.addClass("fa-caret-up");
	}).on('hide.bs.collapse',function(){
		$a = $("#toolbox-handing").find("a");
		var $icon = $a.find(".pull-right .fa");
		$icon.removeClass("fa-caret-up");
		$icon.addClass("fa-caret-down");
	});
	$('#accordion').on('shown.bs.collapse', function (event) {
		$a = $(this).find("a[aria-expanded=true]");
		var $icon = $a.find(".fa");
		$icon.removeClass("fa-angle-double-down");
		$icon.addClass("fa-angle-double-up");
		$a.addClass("text-bold");
		event.stopPropagation();
	}).on('hide.bs.collapse',function(event){
		$a = $(this).find("a[aria-expanded=true]");
		var $icon = $a.find(".fa");
		$icon.removeClass("fa-angle-double-up");
		$icon.addClass("fa-angle-double-down");
		$a.removeClass("text-bold");
		event.stopPropagation();
	});
}

/**
 * 流程属性面板收缩、展开监听
 */
FlowDesigner.propertyPanelListener = function() {
	var $a = null;
	$('#properties-accordion').on('shown.bs.collapse', function () {
		$a = $(this).find("a[aria-expanded=true]");
		var $icon = $a.find(".pull-right .fa");
		$icon.removeClass("fa-caret-down");
		$icon.addClass("fa-caret-up");
		$a.find(".pull-right").attr("title","隐藏属性面板");
	}).on('hide.bs.collapse',function(){
		$a = $(this).find("a[aria-expanded=true]");
		var $icon = $a.find(".pull-right .fa");
		$icon.removeClass("fa-caret-up");
		$icon.addClass("fa-caret-down");
		$a.find(".pull-right").attr("title","显示属性面板");
	});
}

/**
 * 导出流程
 * @param data
 */
FlowDesigner.exportModel = function(data) {
	$("#export-model").val(data);
	$("#export-form").submit();
}