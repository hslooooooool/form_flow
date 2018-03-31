/**
 * @author lmq
 */
var selectedMenuIndex = 0;
var uuid = 1;
$(document).ready(function(){
	initMenuHeight();
	$(".menu-resizer-toggler").click(function() {
		var dis = $("#menu-nav").css("display");
		if (dis == 'none') {
			$("#menu-nav").css({
				"display" : "block"
			});
			$(this).removeClass("zoom-left");
			$("#main-content").css({
				"margin-left" : "212px"
			});
		} else {
			$("#menu-nav").css({
				"display" : "none"
			});
			$(this).addClass("zoom-left");
			$("#main-content").css({
				"margin-left" : "9px"
			});
		}
		autoJqGridWidth();
		var $activeTab = $('#main-tab').tabs('getSelected');
		var title = $activeTab.panel('options').title;
		$('#main-tab').tabs({  
            width: "auto",  
            height: "auto" 
        });
		$activeTab.parent().width("auto");
		$activeTab.width("auto");
		$('#main-tab').tabs('select',title);
		autoTableWidth($activeTab.panel('panel'));
		$("#main-content").trigger("resize");
	});
	$(window).resize(function(){
		uuid = utils.UUID();
		initMenuHeight();
		resizePanel(getActiveTabPanel());
		iframeResize();
	});
	menuTreeListener();
	tabListner();
	//2.0.15 版本中添加该方法；去除了cnoj.event.listener.js中的方法
	setTimeout('initEvent()', 300);
});

/**
 * 获取header高度
 * @returns
 */
function getHeaderHeight() {
	return $(".header").outerHeight(true) + $(".header-bottom").outerHeight(true);
}

/**
 * 获取主内容高度
 * @returns
 */
function getMainHeight() {
	return $("#main-content").height();
}

/**
 * 获取主内容top位置
 * @returns
 */
function getMainTop() {
	return $("#main-content").offset().top;
}

/**
 * 获取tab头高度
 * @returns {Number}
 */
function getTabHeaderHeight() {
	return 30;
}

/**
 * 获取底部版权信息的高度
 * @returns
 */
function getFooterHeight() {
	//return $(".wrap-footer").outerHeight(true);
	return 0;
}

/**
 * 表格自适应
 */
function autoTableWidth($activeTab) {
	if(utils.isEmpty($activeTab)) {
		$activeTab = getActiveTabPanel();
	}
	tableWrapListener($activeTab, true);
}

/**
 * 初始化菜单高度
 */
function initMenuHeight() {
	var h = $(window).height();
	var vh = $(".header-body-dividing").outerHeight(true);
	h = h - getFooterHeight() - $(".header").outerHeight(true) - vh - 6;
    var menuResizerH = h;
    if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){
    	menuResizerH = menuResizerH-5;
    }
	$(".menu-resizer").css({"height" : menuResizerH + "px"});
	$(".menu-resizer-toggler").css({"margin-top" : (h-50)/2+ "px"});
	$("#main-content").css({"height" : h + "px"});
	$("#main-tab").css({"height" : (h - 2) + "px"});
	$("#menu-sub-nav").css({"height" : (h-$(".menu-title").outerHeight(true)) + "px"});
}

/**
 * 自动调整jqGrid插件的宽度
 */
function autoJqGridWidth() {
	$("#main-content .cnoj-jq-grid").each(function(){
		$(this).setGridWidth($("#main-content").width()-10);
	});
}

/**
 * 监听左菜单点击事件
 * @returns
 */
function menuTreeListener() {
	$("#menu-sub-nav a").on('click',function(){
		var $this = $(this);
		var $parent = $this.parent();
		var classNames = $this.attr("class");
		selectedMenuIndex = 0;
		$("#menu-sub-nav .active").removeClass("active");
		if(!utils.isEmpty(classNames) && (utils.isEmpty($parent.attr("class")) || 
				!utils.isContain($parent.attr("class"), "no-parent"))){
				if(utils.isContain(classNames, "expand")) {
					var $uiIcon = $this.find(".ui-icon");
					$uiIcon.removeClass("ui-icon-triangle-1-s");
					$uiIcon.addClass("ui-icon-triangle-1-e");
					$parent.find("ul:first").slideUp("fast",function(){
						$this.removeClass("expand");
						$this.addClass("shrink");
						$parent.removeClass("active");
						$parent.removeClass("li-expand");
						$parent.addClass("li-shrink");
					});
				} else if(utils.isContain(classNames, "shrink")) {
					var $uiIcon = $this.find(".ui-icon");
					$uiIcon.removeClass("ui-icon-triangle-1-e");
					$uiIcon.addClass("ui-icon-triangle-1-s");
					$parent.addClass("active");
					//判断其他菜单是否展开状态，如果是，则缩回展开的菜单
					var className = $parent.attr("class");
					className = utils.getClassContain(className, "layer");
					$parent.parent().find("."+className+".li-expand").each(function(){
						var currentClassNames = $(this).attr("class");
						if(!utils.isEmpty(currentClassNames) && !utils.isContain(currentClassNames, "active")) {
							var $a = $(this).find("a");
							var $uiIcon2 = $a.find(".ui-icon");
							$uiIcon2.removeClass("ui-icon-triangle-1-s");
							$uiIcon2.addClass("ui-icon-triangle-1-e");
							var $this2 = $(this);
							$(this).find("ul:first").slideUp("fast",function(){
								$a.removeClass("expand");
								$a.addClass("shrink");
								var $ul = $this2.find(".li-expand");
								$ul.removeClass("active");
								$ul.removeClass("li-expand");
								$ul.addClass("li-shrink");
								$this2.find("ul").hide();
							});
						}
					});
					$parent.addClass("li-expand");
					$parent.find("ul:first").slideDown("fast",function(){
						$this.removeClass("shrink");
						$this.addClass("expand");
						$parent.removeClass("li-shrink");
					});
				}
		}
		var uri = $this.data("uri");
		if(!utils.isEmpty(uri) && utils.trim(uri) != '#') {
			$("#menu-sub-nav .active").removeClass("active");
			$parent.addClass("active");
			var title = $this.data("title");
			var menuType = $this.data("menu-type");
			//表单用iframe方式打开；防止元素冲突
			if(!utils.isEmpty(menuType) && (menuType=='flow_resource' || menuType == 'form_resource')) 
				openFlowTab(title,uri);
			else 
				addTab(title,uri,false);
			selectedMenuIndex = $this.data("index");
		}
		return false;
	});
}

/**
 * 获取选中菜单索引
 * @returns {Number}
 */
function getSelectMenuIndex() {
	return selectedMenuIndex;
}


/**
 * 加载到当前页面
 * @param uri
 */
function loadLocation(uri) {
	//loadUri("#main-content",uri,true);
	reloadTab(uri);
}

/**
 * 重新加载tab
 * @param uri
 */
function reloadTab(uri) {
	var tab = $('#main-tab').tabs('getSelected');
    tab.panel('refresh',uri);
}

/**
 * 更新选择tab
 * @param oldTitle
 * @param newTitle
 * @param uri
 */
function updateSelectTab(oldTitle,newTitle,uri) {
	$('#main-tab').tabs('select',oldTitle);
	var tab = $('#main-tab').tabs('getSelected');
	$("#main-tab").tabs('update',{
	    tab: tab,
	      options:{
	       title:newTitle,
	       href: uri,
	       cache:false
	   }
	 });
	tab.panel('refresh',uri);
}

/**
 * 打开tab
 * @title
 * @param uri
 * @param isReLoad 是否需要重新载入
 */
function openTab(title,uri,isReLoad) {
	addTab(title,uri,isReLoad);
}

/**
 * 添加tab
 * @param title
 * @param url
 * @param isReLoad 是否需要重新载入
 */
function addTab(title,url,isReLoad) {
	//判断是否登录
	cnoj.isLogin(function(){
		isReLoad = (utils.isEmpty(isReLoad) || !isReLoad)?false:true;
		if($('#main-tab').tabs('exists',title)) {
			$('#main-tab').tabs('select',title);
			if(isReLoad) {
				reloadTab(url);
			}
		} else {
			$('#main-tab').tabs('add',{
				title: title,
				href:url,
				closable: true
			});
		}
	});    
}

/**
 * 关闭激活的tab
 */
function closeActivedTab() {
	var tab = $('#main-tab').tabs('getSelected');
	var index = $('#main-tab').tabs('getTabIndex',tab);
	$('#main-tab').tabs('close',index);
}

/**
 * 打开流程处理窗口(只能打开一个流程窗口)
 * @param title
 */
function openFlowTab(title,url) {
	var id = "flow-process-tab";
	//判断是否登录
	cnoj.isLogin(function(){
		var allTabs = $('#main-tab').tabs('tabs');
		var flowTab = null;
		for(var i=0;i<allTabs.length;i++) {
			var options = allTabs[i].panel('options');
			if(options.id == id) {
				flowTab = allTabs[i];
				break;
			}
		}
		if(flowTab) {
			var index = $('#main-tab').tabs('getTabIndex',flowTab);
			$('#main-tab').tabs('select',index);
			$('#main-tab').tabs('update',{
				tab: flowTab,
				options: {
					title: title,
					id:id,
					content: __createFrame(url),
				}
			});
		} else {
			$('#main-tab').tabs('add',{
				title: title,
				id:id,
				content: __createFrame(url),
				closable: true
			});
		}
	});
	
	function __createFrame(url) {
	    var iframe = '<iframe class="tabs-content-iframe" scrolling="no" frameborder="0"  src="' + url + '" style="width:100%;height:'+(getMainHeight() - getTabHeaderHeight() - 20)+'px;"></iframe>';
	    return iframe;
	}
}

/**
 * tab监听;如载入；重新刷新等事件
 */
function tabListner() {
	$('#main-tab').tabs({
		onLoad: function(panel) {
			panel.wrapInner('<div class="loading-content"></div>');
			panel.prepend('<div class="cnoj-loading"><i class="fa fa-spinner fa-spin fa-lg"></i> 正在加载，请稍候...</div>');
			var $target = panel.find(".loading-content");
			$target.css("visibility","hidden");
			$("body").css({"overflow":"hidden"});
			setTimeout(function(){
				initEvent(panel);
				panel.find(">.cnoj-loading").remove();
				$target.css("visibility","visible");
				$("body").css("overflow","auto");
			}, 100);
		},
		onSelect: function(title, index) {
			var tab = $('#main-tab').tabs('getTab', index);
			resizePanel(tab.panel('panel'));
		}
	});
	$('#main-tab').tabsContextMenu();
}

/**
 * 获取选中菜单索引
 * @returns {Number}
 */
function getActiveTabIndex() {
	var tab = $('#main-tab').tabs('getSelected');
	var index = $('#main-tab').tabs('getTabIndex',tab);
	if(!utils.regexInteger(index)) {
		index = 0;
	}
	return index;
}

/**
 * 获取选中面板
 * @returns 
 */
function getActiveTabPanel() {
	var tab = $('#main-tab').tabs('getSelected');
	return tab.panel('panel');
}

/**
 * 获取选中Tab
 * @returns 
 */
function getActiveTab() {
	var tab = $('#main-tab').tabs('getSelected');
	return tab;
}

/**
 * 重写计算tab中panel自适应的高度
 * @param panel
 */
function resizePanel(panel) {
	var panelUUID = panel.attr("uuid");
	if(uuid != panelUUID) {
		tableWrapListener(panel, true);
		limitHeightListener(panel,true);
		panel.find(".cnoj-jq-grid").each(function(){
			$(this).setGridWidth($("#main-content").width()-10);
			$(this).setGridHeight(getMainHeight()-10);
		});
		panel.attr("uuid",uuid);
	}
	listPanelListener($(panel), true);
}

/**
 * 改变iframe的高度（流程表单在tabs中采用iframe）
 */
function iframeResize() {
	var h = getMainHeight() - getTabHeaderHeight() - 20;
	var $tabsContentIframe = $(".tabs-content-iframe");
	if($tabsContentIframe.length > 0) {
		$tabsContentIframe.height(h);
	}
}
