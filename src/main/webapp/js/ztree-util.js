/**
 * @author lmq 
 * 重新封装zTree插件
 */
var zTreeInstance = new Array();
(function($) {
	/**
	 * zTree助手(重新封装zTree插件)
	 */
	$.fn.zTreeUtil = function(options) {
		var setting = {
				uri:null,
				isAjaxAsync:true,
				isAsync:false,
				getAsyncUri:null,
				jsonData:null,
				isInput:false,
				isInputTreeShow:true,
				inputSelectWidth:0,
				inputSelectHeight:0,
				redirectUri:null,
				rediretBeforeCallback:null,
				isCheck:false,
				checkOpt:null,
				paramName:null,
				isNodeLink:true,
				isDefaultLoad:false,
				target:null,
				panelHeight:0,
				isShowNone:false,
				isSearch:true,
				destory:false,
				prompMsg:null,
				isLoading:false,
				getTreeObj:null,
				callback:null,
				onClick:null,
		}
		setting = $.extend(setting,options);
		var $showContainer = null;
		var $ulTree = null;
		var divTagId = null;
		var newInputId = null;
		var divTag = null;
		var $this = $(this);
		var thisId = $(this).attr("id");
		if(utils.isEmpty(thisId)) {
			var generateId = "input-select"+utils.randomNum(5);
			$(this).attr("id",generateId);
			thisId = generateId;
		}
		var menuIndex = 1;
		if(typeof(getActiveTabIndex) === 'function') {
			menuIndex = getActiveTabIndex();
		}
		divTag = thisId+menuIndex;
		var isCreate = false;
		var defaultValue = null;
		var h = 0;
		var w = 0;
		if(setting.isInput) {
			$this.attr("readonly",true);
			defaultValue = $this.val();
			divTagId = "input-tree-"+divTag;
			newInputId = divTag+"-value"+menuIndex;
			if(!utils.isEmpty($("#"+divTagId).attr("id")) && utils.isEmpty($("#"+newInputId).attr("id"))) {
				destory();
				$("#"+divTagId).remove();
			}
			var isBody = true;
			//判断输入框是否在弹出窗口内
			var $modelDialog = $this.parents(".modal-dialog");
			if($modelDialog.length > 0) {
				isBody = false;
			}
			if($("#"+divTagId).length == 0) {
				isCreate = true;
				//判断输入框是否在弹出窗口内
				if(!isBody) {
					$this.after("<div id='"+divTagId+"' data-target-inputid='"+newInputId+"' class='panel panel-default input-select-tree'></div>");
				} else {
					$("body").append("<div id='"+divTagId+"' data-target-inputid='"+newInputId+"' class='panel panel-default input-select-tree'></div>");
				}
				$showContainer = $("#"+divTagId);
				var top=0,left=0;
				if(!isBody) {
					var pos = $this.position();
					top  = pos.top + $this.outerHeight(true);
					left = pos.left;
				} else {
					var offset = $this.offset();
					top  = offset.top + $this.outerHeight(true);
					left = offset.left;
				}
				var winW = $(window).width();
				var minWidth = 300;
				w = setting.inputSelectWidth<1?$this.outerWidth():setting.inputSelectWidth;
				if(w<minWidth) {
					left = left-parseInt((minWidth-w)/2)-10;
					if((minWidth+left)>(winW-10)) {
						left = left-(minWidth+left-winW);
						isWidthPass = true;
					}
					w = minWidth;
				}
				h = setting.inputSelectHeight<1?200:setting.inputSelectHeight;
				$showContainer.css({"top":top+"px","left":left+"px","width":w+"px"});
				
				var inputName = $this.attr("name");
				$this.attr("name","");
				$this.after("<input type='hidden' id='"+newInputId+"' name='"+utils.handleNull(inputName)+"' />");
			}
			if(!setting.isInputTreeShow) {
				$("#"+divTagId).hide();
			} else {
				$("#"+divTagId).show();
			}
		} else {
			divTagId = "tree-"+divTag;
			w = $this.width();
			if(typeof($("#"+divTagId).attr("id")) === 'undefined') {
				isCreate = true;
				/*if(setting.isLoading) {
					$this.append("<div class='upload-loading'>正在加载处理人...</div>");
				}*/
				$this.append("<div id='"+divTagId+"' class='tree-wrap'></div>");
				$showContainer = $this.find("#"+divTagId);
			}
		}
		if(isCreate) {
			//ui-widget-header 
			var searchContent = null;
			if(setting.isInput) {
				var searchContent = "<div class='panel-heading ui-widget-header tree-search' style='padding-left:"+parseInt((w/3*1)/2)+"px'>"+
				"<div class='input-group input-group-xs' style='width:"+parseInt(w/3*2)+"px'><input type='text' class='form-control input-sm search-value' />"+
				"<span class='input-group-btn'>"+
				"<button type='button' class='btn btn-default search-tree-btn'>搜索</button></span></div>"+
				"</div>";
			} else {
				var searchContent = "<div class='tree-search p-l-10 p-r-10'>"+
				"<div class='input-group input-group-xs' ><input type='text' class='form-control search-value' />"+
				"<span class='input-group-btn'>"+
				"<button type='button' class='btn btn-default search-tree-btn'>搜索</button></span></div>"+
				"</div>";
			}
			if(setting.isSearch) {
				$showContainer.append(searchContent);
			}
			//ui-widget-content
			if(setting.isInput) {
				$showContainer.append("<div class='panel-body tree-content' id='"+divTag+"-tree-content' style='height:"+h+"px'></div>");
				if(setting.isShowNone) {
					$("#"+divTag+"-tree-content").append("<div class='tree-none'><a class='tree-none-a' href=''>无</a></div>");
					$(".tree-none").click(function(){
						var hiddenInputId = $(this).parents(".input-select-tree").data("target-inputid");
						var $hiddenInput= $("#"+hiddenInputId);
						var $input = utils.findPrevTag($hiddenInput,"input");
						$input.val("无");
						$hiddenInput.val("0");
						$(this).parents(".input-select-tree").hide();
						$input.trigger("change");
						return false;
					});
				}
			} else {
				var heightStyle = "";
				if(setting.panelHeight>0) {
					heightStyle = "style='height:"+setting.panelHeight+"px;'";
				}
				$showContainer.append("<div class='tree-content' id='"+divTag+"-tree-content' "+heightStyle+"></div>");
			}
			$showContainer = $showContainer.find(".tree-content");
			if(setting.isLoading) {
				$showContainer.append('<div class="cnoj-loading"><i class="fa fa-spinner fa-spin fa-lg"></i> 正在加载，请稍候...</div>');
			}
			$showContainer.append("<ul class='ztree' id='"+divTag+"-z-tree-ul' loaded='0'></ul>");
			$ulTree = $showContainer.find(".ztree");
			loadData(defaultValue);
			if(setting.isSearch) {
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
		} else {
			if(!utils.isEmpty(defaultValue)) {
				var objTree = getTree();
				if(null != objTree) {
					var treeNode = objTree.getNodeByParam("id",defaultValue,null);
					if(null != treeNode) {
						var name = treeNode.name;
						var parentNode = treeNode.getParentNode();
						if(null != parentNode) {
							name = parentNode.name+">>"+name;
						}
						var hiddenInputId = $("#"+divTagId).data("target-inputid");
						var $hiddenInput= $("#"+hiddenInputId);
						$hiddenInput.val(defaultValue);
						var $input = utils.findPrevTag($hiddenInput,"input");
						$input.val(name);
						$input.attr("title",name);
						$input.attr("readonly",false);
						$input.trigger("change");
					}
				}
			}
			if(null != setting.getTreeObj && typeof(setting.getTreeObj) === 'function') {
                setting.getTreeObj(getTree());
            }
		}
		//关闭弹出下拉框
		if(setting.isInput) {
			$(document).on("mousedown",function(event){
				if ($(event.target).closest('#'+divTagId).length === 0 && 
						$(event.target).closest('#'+thisId).length === 0) {
					$("#"+divTagId).hide();
					$("#"+thisId).attr("readonly",false);
				}
			});
		}
		
		if(setting.destory) {
			destory();
		}
		if(null != setting.getTreeObj && typeof(setting.getTreeObj) === 'function') {
			setting.getTreeObj(getTree());
		}

		/**
		 * 加载数据
		 * @param defaultValue 默认值
		 */
		function loadData(defaultValue) {
			if(null != $ulTree && setting.uri != null && setting.uri != '') {
				$.ajax({
					url: setting.uri,
					//async: setting.isAjaxAsync,
					success: function(data){
						if(setting.isLoading) {
							$("#"+divTagId).find(".cnoj-loading").remove();
						}
						var output = data;//$.parseJSON(data.output);
						if(output.result == '1') {
							createTree(defaultValue,output.datas);
						}
						$ulTree.attr("loaded","1");
				}});
			} else if(utils.isNotEmpty(setting.jsonData)) {
				createTree(defaultValue,setting.jsonData);
			}
		};
		
		/**
		 * 创建树
		 * @param defaultValue
		 * @param datas 数据
		 */
		function createTree(defaultValue, datas) {
			var zTree = null;
			var treeSetting = {data:{simpleData:{enable: true}},view: {fontCss: getFontCss}};
			if(setting.isCheck) {
				if(utils.isEmpty(setting.checkOpt))
					setting.checkOpt = {check:{enable:true}};
				else {
					if(typeof(setting.checkOpt) == 'string') {
						setting.checkOpt = eval('(' + setting.checkOpt + ')');
					}
					setting.checkOpt = $.extend(true,{check:{enable:true}},setting.checkOpt);
				}
				treeSetting = $.extend(true,treeSetting,setting.checkOpt,{callback:{beforeClick:function(treeId, treeNode) {
					//var treeId = $ulTree.attr("id");
					var zTree = $.fn.zTree.getZTreeObj(treeId);
					zTree.checkNode(treeNode, !treeNode.checked, true, true);
					return false;
				}}});
			} else {
				if(setting.isInput)
					treeSetting = $.extend(true,treeSetting,{callback:{onClick:onInputClick}});
				else {
					if(setting.isNodeLink) {
					  treeSetting = $.extend(true,treeSetting,{callback:{onClick:onRedirectClick}});
					  if(setting.isDefaultLoad) {
						  loadUriToPanel("0");
					  }
					}
				}
			}
			if(setting.isAsync) {
				treeSetting = $.extend(true,treeSetting,{
					async: {
						enable: true,
						url: setting.uri,
						autoParam: ["id"],
						dataFilter : function(treeId, parentNode, responseData) {
							return responseData.datas;
						}
					}
				});
				//if(utils.isNotEmpty(setting.getAsyncUri) && typeof(setting.getAsyncUri) == 'function') {
				if(utils.isNotEmpty(setting.getAsyncUri)) {
					if(typeof(setting.getAsyncUri) == 'string') {
						setting.getAsyncUri = setting.getAsyncUri.replace(/id=(.*?)&|&id=([^&]*)|\?id=([^&]*)$/,"");
					}
					treeSetting = $.extend(true,treeSetting,{async: {url: setting.getAsyncUri}});
				}
			}
			zTree = $.fn.zTree.init($ulTree,treeSetting, datas);
			zTreeInstance[divTagId] = zTree;
			if(!utils.isEmpty(setting.callback) && typeof(setting.callback) === 'function') {
				setting.callback(zTree);
			}
			//输入树
			if(!setting.isCheck && setting.isInput) {
				if(!utils.isEmpty(defaultValue)) {
					var treeNode = zTree.getNodeByParam("id",defaultValue,null);
					if(null != treeNode) {
						var name = treeNode.name;
						var parentNode = treeNode.getParentNode();
						if(null != parentNode) {
							name = parentNode.name+">>"+name;
						}
						var hiddenInputId = $("#"+divTagId).data("target-inputid");
						
						var $hiddenInput= $("#"+hiddenInputId);
						$hiddenInput.val(defaultValue);
						var $input = utils.findPrevTag($hiddenInput,"input");
						$input.val(name);
						$input.attr("title",name);
						$input.attr("readonly",false);
						$input.trigger("change");
					} else {
						var hiddenInputId = $("#"+divTagId).data("target-inputid");
						var $hiddenInput= $("#"+hiddenInputId);
						$hiddenInput.val(defaultValue);
						var $input = utils.findPrevTag($hiddenInput,"input");
						if(!utils.isEmpty(setting.prompMsg)) {
							$input.val("");
							$input.attr("placeholder",setting.prompMsg);
						} else {
							$input.val("无");
						}
						$input.attr("readonly",false);
						$input.trigger("change");
					}
			    } else {
			    	var hiddenInputId = $("#"+divTagId).data("target-inputid");
					var $hiddenInput= $("#"+hiddenInputId);
					$hiddenInput.val(defaultValue);
					var $input = utils.findPrevTag($hiddenInput,"input");
			    	if(!utils.isEmpty(setting.prompMsg)) {
			    		$input.val("");
			    		$input.attr("placeholder",setting.prompMsg);
					} else {
						$input.val("无");
					}
					$input.attr("readonly",false);
					$input.trigger("change");
			    }
			}
		}
		
		function onInputClick(event, treeId, treeNode, clickFlag) {
			var is = true;
			if(utils.isNotEmpty(setting.onClick)) {
				is = false;
				if(typeof(setting.onClick) == 'function') {
					is = setting.onClick(event, treeId, treeNode, clickFlag, divTagId);
				} else {
					setting.onClick = eval(setting.onClick);
					is = setting.onClick(event, treeId, treeNode, clickFlag, divTagId);
				}
			} 
			if(is) {
				var name = treeNode.name;
				var parentNode = treeNode.getParentNode();
				if(null != parentNode) {
					name = parentNode.name+">>"+name;
				}
				var hiddenInputId = $("#"+divTagId).data("target-inputid");
				
				var $hiddenInput= $("#"+hiddenInputId);
				$hiddenInput.val(treeNode.id);
				var $input = utils.findPrevTag($hiddenInput,"input");
				$input.val(name);
				$input.attr("title",name);
				$input.attr("readonly",false);
				$input.trigger("change");
				$("#"+divTagId).hide();
			}
		};
		
		function onRedirectClick(event, treeId, treeNode, clickFlag) {
			var is = true;
			if(utils.isNotEmpty(setting.rediretBeforeCallback) 
					&& typeof(setting.rediretBeforeCallback) == 'function') {
				is = setting.rediretBeforeCallback(event, treeId, treeNode, clickFlag);
			}
			if(is)
				loadUriToPanel(treeNode.id);
		}
		
		function getFontCss(treeId, treeNode) {
			return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
		};
		
		/**
		 * 加载uri到指定的div中
		 * @param id
		 */
		function loadUriToPanel(id) {
			if(!utils.isEmpty(setting.redirectUri)) {
				var paramName = utils.isEmpty(setting.paramName)?"id":setting.paramName;
				var uri = utils.isContain(setting.redirectUri, "?")?setting.redirectUri+"&"+paramName+"="+id:setting.redirectUri+"?"+paramName+"="+id;
				if(!utils.isEmpty(setting.target)) {
					loadUri(setting.target,uri);
				} else {
					loadLocation(uri);
				}
			}
		}
		
		/**
		 * 获取zTree对象
		 */
		function getTree() {
			return zTreeInstance[divTagId];
		}
		
		/**
		 * 销毁树
		 */
		function destory() {
			var zTreeObj = zTreeInstance[divTagId];
			if(null != zTreeObj) {
				zTreeObj.destroy();
			}
			$this.attr("name", $("#"+newInputId).attr("name"));
			$this.attr("readonly",false);
			$this.attr("title","");
			$this.val("");
			$("#"+divTagId).remove();
			$("#"+newInputId).remove();
		}
	}
})(jQuery)