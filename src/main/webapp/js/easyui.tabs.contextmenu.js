/**
 * easyui Tabs右键菜单
 * 依赖jQuery及easyui
 */
(function($){
	/**
	 * tabs右键菜单功能
	 */
	$.fn.tabsContextMenu = function(options) {
		this.options = $.extend({},{
			tabsId:$(this).attr("id"),
			tabsMenuSuffix:'-menu',
			refresh: true,
		},options);
		createContextMenu(this.options);
		addContextMenuEvent(this.options);
	}
	/**
	 * 创建右键菜单显示的内容
	 * @param options
	 */
	function createContextMenu(options) {
		var tabsMenuId = options.tabsId+options.tabsMenuSuffix;
		var $tabsContextMenu = $("#"+tabsMenuId);
		if(!$tabsContextMenu.is("div")) {
			$tabsContextMenu = $('<ul id="'+tabsMenuId+'" class="dropdown-menu tabs-menu">'+
				 '<li><a href="#" data-type="close-current"><i class="fa fa-times" aria-hidden="true"></i> 关闭当前窗口</a></li>'+
				 '<li><a href="#" data-type="close-not-current"><i class="fa fa-times" aria-hidden="true"></i> 关闭非当前窗口</a></li>'+
				 '<li><a href="#" data-type="close-all"><i class="fa fa-times" aria-hidden="true"></i> 关闭全部</a></li>'+
				 '<li role="separator" class="divider"></li>'+
				 '<li><a href="#" data-type="close-right"><i class="fa fa-times" aria-hidden="true"></i> 关闭右边所有</a></li>'+
				 '<li><a href="#" data-type="close-left"><i class="fa fa-times" aria-hidden="true"></i> 关闭左边所有</a></li>'+
				 '<li role="separator" class="divider"></li>'+
				 '<li><a href="#" data-type="refresh-current"><i class="fa fa-refresh" aria-hidden="true"></i> 刷新当前窗口</a></li></ul>');
			$tabsContextMenu.find("a").click(function() {
				var $ul = $(this).closest("ul");
				$ul.hide();
				handleContextMenuTabs($ul, $(this).data("type"));
				return false;
			});
			$tabsContextMenu.mouseleave(function(){
				$(this).hide();
			});
			$tabsContextMenu.appendTo("body");
		}
		if(!options.refresh) {
			$tabsContextMenu.find("#refresh-current").hide();
		}
	}
	/**
	 * 添加tabs右键事件
	 * @param options
	 */
	function addContextMenuEvent(options) {
		var tabsMenuId = options.tabsId + options.tabsMenuSuffix;
		$('#'+options.tabsId).tabs({
			onContextMenu: function(e, title, index) {
				e.preventDefault();
				//var tab = $('#'+options.tabsId).tabs('getSelected');
				//var selectedIndex = $('#'+options.tabsId).tabs('getTabIndex',tab);
				if(index>0) {
					$('#'+tabsMenuId).css({"left":e.pageX,"top":e.pageY}).show()
					.data("tabTitle", title).data("tabs-id",options.tabsId).data("tabIndex", index);
					var $currentTarget = $(e.target).parent();
					$currentTarget.mouseleave(function(event){
						var $relatedTarget = $(event.relatedTarget);
						if(!$relatedTarget.hasClass("tabs-menu")) {
							$('#'+tabsMenuId).hide();
						}
						$currentTarget.unbind("mouseleave");
					});
				}
			}
		});
	}
	
	/**
	 * 处理触发右键事件
	 * @param menu
	 * @param type
	 */
	function handleContextMenuTabs(menu, type) {
		var $menu = $(menu);
		var tabsId = $menu.data("tabs-id");
		var $tabs = $("#"+tabsId);
		var allTabs = $tabs.tabs('tabs');
		var allTabsTitle = new Array();
		$.each(allTabs,function(i, n){
			var opt = $(n).panel('options');
			if(opt.closable) 
				allTabsTitle.push(opt.title);
		});
		var currentTabTitle = $menu.data("tabTitle");
		var currentTabIndex = $menu.data("tabIndex");
		switch(type) {
			case 'close-current':
				$tabs.tabs('close', currentTabIndex);
				break;
			case 'close-not-current':
				for(var i=0; i < allTabsTitle.length; i++) {
					if(currentTabTitle != allTabsTitle[i])
						$tabs.tabs('close', allTabsTitle[i]);
				}
				$tabs.tabs('select',currentTabTitle);
				break;
			case 'close-all':
				for(var i=0; i < allTabsTitle.length; i++) {
					$tabs.tabs('close', allTabsTitle[i]);
				}
				break;
			case 'close-right':
				for(var i=currentTabIndex; i < allTabsTitle.length; i++) {
					$tabs.tabs('close', allTabsTitle[i]);
				}
				$tabs.tabs('select',currentTabTitle);
				break;
			case 'close-left':
				for(var i=0; i < currentTabIndex - 1; i++) {
					$tabs.tabs('close', allTabsTitle[i]);
				}
				$tabs.tabs('select',currentTabTitle);
				break;
			case 'refresh-current':
				$tabs.tabs("getTab", currentTabTitle).panel("refresh");
				break;
		}
	}
})(jQuery);