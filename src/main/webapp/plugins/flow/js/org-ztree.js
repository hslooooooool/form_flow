var zTreeInstance = new Array();
(function($) {
	/**
	 * zTree助手(重新封装zTree插件)
	 */
	$.fn.orgTree = function(options) {
		var setting = {
				uri:'org/tree.json',
				jsonData:null,
				isInput:false,
				defaultValue:null,
				callback:null
		}
		setting = $.extend(setting,options);
		var $showContainer = null;
		var $ulTree = null;
		var divTagId = null;
		var divTag = null;
		var $this = $(this);
		
		divTag = ((typeof($(this).attr("id")) === 'undefined')?$(this).attr("class"):$(this).attr("id"));
		var isCreate = false;
		var defaultValue = null;
		var h = 0;
		var w = 0;
		divTagId = "input-tree-"+divTag;
		if(typeof($("#"+divTagId).attr("id")) === 'undefined') {
			isCreate = true;
			$("body").append("<div id='"+divTagId+"' class='panel panel-default input-select-tree'></div>");
			$showContainer = $("#"+divTagId);
			var p = $this.position();
			var top = $this.offset().top+$this.outerHeight(true)+2;
			var left = $this.offset().left;
			w = $this.outerWidth();
			h = 200;
			$showContainer.css({"top":top+"px","left":left+"px","width":w+"px"});
			var inputName = $this.attr("name");
			$this.attr("name","");
		}
		$("#"+divTagId).hide();
		$this.on('focus click',function(event){
			$("#"+divTagId).show();
			event.stopPropagation();
		});
		if(isCreate) {
			var searchContent = "<div class='panel-heading ui-widget-header tree-search' style='padding-left:5px;padding-right:5px;'>"+
			"<div class='input-group input-group-xs'><input type='text' class='form-control input-sm search-value' />"+
			"<span class='input-group-btn'>"+
			"<button type='button' class='btn btn-default search-tree-btn'>搜索</button></span></div>"+
			"</div>";
			$showContainer.append(searchContent);
			$showContainer.append("<div class='panel-body ui-widget-content tree-content' id='"+divTag+"-tree-content' style='height:"+h+"px'></div>");
			$showContainer = $showContainer.find(".tree-content");
			$showContainer.append("<ul class='ztree' id='"+divTag+"-z-tree-ul'></ul>");
			$ulTree = $showContainer.find(".ztree");
			loadData(setting.defaultValue);
			$("#"+divTagId+" .search-tree-btn").click(function(){
				var value = $("#"+divTagId+" .search-value").val();
				var zTree = zTreeInstance[divTagId];
				var allNodes = zTree.getNodes();
				var allNodes = zTree.transformToArray(allNodes);
				for(var i=0;i<allNodes.length;i++) {
					allNodes[i].highlight = false;
				}
				if(!utils.isEmpty(value)) {
					var nodes = zTree.getNodesByParamFuzzy('name',value);
					zTree.hideNodes(allNodes);
					if(nodes.length>0) {
						var nodeArray = new Array();
						for(var i=0;i<nodes.length;i++) {
							nodes[i].highlight = true;
							nodeArray.push(nodes[i]);
						}
						while(nodeArray.length>0) {
							var node = nodeArray.pop();
							zTree.showNode(node);
							if(node.highlight) {
								zTree.updateNode(node);
							}
							var parentNode = node.getParentNode();
							if(null != parentNode) {
								var isPush = true;
								for ( var i = 0; i < nodeArray.length; i++) {
									if(nodeArray[i].id == parentNode.id) {
										isPush = false;
										break;
									}
								}
								if(isPush) {
									nodeArray.push(parentNode);
								}
							}
						}
						nodeArray = null;
					  } else {
						 utils.showMsg("没有搜索到相关数据！");
					  }
				} else {
					for(var i=0;i<allNodes.length;i++) {
						zTree.showNode(allNodes[i]);
						zTree.updateNode(allNodes[i]);
					}
				}
				return false;
			});
		}
		//初始化默认值
		var zTree = $.fn.zTree.getZTreeObj(divTag+"-z-tree-ul");
		if(!utils.isEmpty(zTree) && !utils.isEmpty(setting.defaultValue)) {
			var treeNode = zTree.getNodeByParam("id",setting.defaultValue,null);
			if(null != treeNode) {
				var name = treeNode.name;
				var parentNode = treeNode.getParentNode();
				if(null != parentNode) {
					name = parentNode.name+">>"+name;
				}
				$this.val(name);
				$this.attr("title",name);
			} else {
				$this.val("无");
			}
		 }
		$(document).mousedown(function(event){
			if ($(event.target).closest('#'+divTagId).length === 0) {
				$("#"+divTagId).hide();
			}
		});
		/**
		 * 加载数据
		 * @param defaultValue 默认值
		 */
		function loadData(defaultValue) {
			if(null != $ulTree && setting.uri != null && setting.uri != '') {
				$.post(setting.uri,function(data){
					var output = data;//$.parseJSON(data.output);
					if(output.result == '1') {
						var zTree = null;
						var treeSetting = {data:{simpleData:{enable: true}},view: {fontCss: getFontCss}};
						treeSetting = $.extend(true,treeSetting,{callback:{onClick:onInputClick}});
						zTree = $.fn.zTree.init($ulTree,treeSetting, output.datas);
						zTreeInstance[divTagId] = zTree;
						//输入树
						if(!utils.isEmpty(defaultValue)) {
							var treeNode = zTree.getNodeByParam("id",defaultValue,null);
							if(null != treeNode) {
								var name = treeNode.name;
								var parentNode = treeNode.getParentNode();
								if(null != parentNode) {
									name = parentNode.name+">>"+name;
								}
								$this.val(name);
								$this.attr("title",name);
								if(!utils.isEmpty(setting.callback) && typeof(setting.callback) === 'function') {
								   setting.callback(defaultValue,name);
								}
							} else {
								$this.val("无");
							}
						 } else {
						    $this.val("无");
						 }
					}
				});
			}
		};
		
		function onInputClick(event, treeId, treeNode, clickFlag) {
			var name = treeNode.name;
			var parentNode = treeNode.getParentNode();
			if(null != parentNode) {
				name = parentNode.name+">>"+name;
			}
			$("#"+divTagId).hide();
			if(!utils.isEmpty(setting.callback) && typeof(setting.callback) === 'function') {
				setting.callback(treeNode.id,name);
			}
		};
		
		function getFontCss(treeId, treeNode) {
			return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
		};
	}
})(jQuery)