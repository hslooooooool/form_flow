/**
 * @author lmq
 * 监听事件
 * @param $
 */
(function($) {
	/**
	 * 监听增删改按钮组
	 * 标识 
	 *  class="cnoj-op-btn-list"
	 *  会监听class为"cnoj-op-btn-list" 元素下面class为"add","edit","del","refresh","open-pop","open-self","open-new-tab","open-blank"的按钮；
	 *  class="add" 添加按钮
	 *  参数
	 *     必须
	 *         data-uri  点击该按钮时显示的页面（是一个弹出窗口）
	 *         data-title 弹出窗口的标题
	 *      可选
	 *         selected-value 选中表格行时获取的值
	 *         data-busi  指定页面处理类；java dao
	 *         data-width 指定弹出窗口宽度
	 *         data-before-check 检查方法；该方法有两个参数；参数名及参数值；
	 *         如果返回：false则不执行后续操作；返回true；进行执行
	 *         
	 * class="edit" 编辑按钮 
	 *  参数
	 *     必须
	 *         data-uri  点击该按钮时显示的页面（是一个弹出窗口）
	 *         data-title 弹出窗口的标题
	 *         selected-value 选中表格行时获取的值(要修改数据的Id)
	 *         data-busi  指定页面处理类；java dao
	 *      可选
	 *         data-width 指定弹出窗口宽度
	 *         data-before-check 检查方法；该方法有两个参数；参数名及参数值；
	 *         如果返回：false则不执行后续操作；返回true；进行执行
	 *       
	 * class="del"  删除按钮
	 *  参数
	 *     必须
	 *         data-uri  删除uri
	 *         data-msg 删除数据时，提示的信息
	 *         selected-value 选中表格行时获取的值(要删除的数据ID，多个ID直接用英文逗号隔开)
	 *         data-busi  指定页面处理类；java dao
	 *      可选
	 *         data-del-after 删除成功后执行的js函数;它的优先级高于"data-refresh-uri"和"data-target";
	 *         data-refresh-uri 删除成功后要刷新页面的uri；当data-del-after设置了，该参数忽略
	 *         data-target 指定刷新页面显示的地方，默认为"#main-content";改成参数和“data-refresh-uri”成对出现；当data-del-after设置了，该参数忽略
	 * class="refresh"  刷新按钮
	 *  参数
	 *     必须
	 *        data-uri 要刷新uri
	 *     可选
	 *        data-target 刷新显示的地方，默认为"#main-content";
	 *        
	 * class="open-pop" 自定义按钮，打开指定uri页面（弹出窗口）
	 *  参数
	 *     必须
	 *       data-uri  显示uri
	 *       data-title 弹出窗口的标题
	 *     可选
	 *       selected-value 选中表格行时获取的值(要修改数据的Id)
	 *       data-width 指定弹出窗口宽度
	 *       data-param-name 指定节点ID的参数名称，默认名称为："id"
	 *       data-before-check 检查方法；该方法有两个参数；参数名及参数值；
	 *         如果返回：false则不执行后续操作；返回true；进行执行
	 *
	 *class="open-new-tab" 自定义按钮，在Tab中打开指定URL页面
	 *  参数
	 *     必须
	 *       data-uri  显示uri
	 *       data-title Tab窗口的标题
	 *     可选
	 *       selected-value 选中表格行时获取的值(要修改数据的Id)
	 *       data-width 指定弹出窗口宽度
	 *       data-param-name 指定节点ID的参数名称，默认名称为："id"
	 *       data-before-check 检查方法；该方法有两个参数；参数名及参数值；
	 *         如果返回：false则不执行后续操作；返回true；进行执行
	 */
	$.fn.btnListener = function() {
		var $idTag = $(this);
		//add
		$idTag.find(".add").each(function(){
			var $this = $(this);
			if(!$this.hasClass("add-listener")) {
				$this.addClass("add-listener");
				$this.click(function(){
					openProp($(this),"add");
				});
			}
		});
		//edit
		$idTag.find(".edit").each(function(){
			var $this = $(this);
			if(!$this.hasClass("edit-listener")) {
				$this.addClass("edit-listener");
				$this.click(function(){
					var value = $(this).attr("selected-value");
					if(!utils.isEmpty(value)) {
						if((value).indexOf(',')>0) {
							BootstrapDialogUtil.warningAlert("编辑只能选择一条数据!");
							return;
						} else {
							openProp($(this),"edit");
						} 
					} else {
						BootstrapDialogUtil.warningAlert("请选择一条数据!");
					}
				});
			}
		});
		//del
		$idTag.find(".del").each(function(){
			var $this = $(this);
			if($this.hasClass("del-listener")) {
				return true;
			}
			$this.addClass("del-listener");
			$this.click(function(){ 
				var uri = $(this).data("uri");
				var value = $(this).attr("selected-value");
				var busiName = $(this).data("busi");
				var msg = $(this).data("msg");
				var successFun = $(this).data("del-after");
				var refreshUri = $(this).data("refresh-uri");
				var target = $(this).data("target");
				if(utils.isNotEmpty(value)) {
					if(utils.isEmpty(uri)) {
						return;
					}
					if(uri.indexOf("?")>0)
						uri = uri+"&id="+value+"&busiName="+busiName;
					else 
						uri = uri+"?id="+value+"&busiName="+busiName;
					
					BootstrapDialogUtil.confirmDialog(msg,function(){
						$.post(uri,function(data){
							var output = data;//$.parseJSON(data.output);
							utils.showMsg(output.msg+"！");
							if(output.result !='1') {
								return;
							}
							if(!utils.isEmpty(successFun)) {
									setTimeout(successFun, 0);
							} else {
								if(utils.isNotEmpty(refreshUri)) {
									if(utils.isNotEmpty(target)) {
										loadUri(target, refreshUri, true);
									} else {
										loadActivePanel(refreshUri);
									}
								}
							}
						});
					});
				} else {
					BootstrapDialogUtil.warningAlert("请选择数据!");
				}
			});
		});
		//refresh
		$idTag.find(".refresh").each(function(){
			var $this = $(this);
			if(!$this.hasClass("refresh-listener")) {
				$this.addClass("refresh-listener");
				$this.click(function(){
					var uri = $(this).data("uri");
					var target = $(this).data("target");
					if(utils.isNotEmpty(uri)) {
						if(!utils.isEmpty(target)) {
							loadUri(target,uri);
						} else {
							loadLocation(uri);
						}
					}//if
				});
			}
		});
		
		$idTag.find(".open-pop").each(function(){
			var $this = $(this);
			if(!$this.hasClass("open-pop-listener")) {
				$this.addClass("open-pop-listener");
				$this.click(function(){
					openProp($(this),null,'open-pop');
				});
			}
		});
		$idTag.find(".open-self").each(function(){
			var $this = $(this);
			if(!$this.hasClass("open-self-listener")) {
				$this.addClass("open-self-listener");
				$this.click(function(){
					openProp($(this),null,'open-self');
				});
			}
		});
		$idTag.find(".open-new-tab").each(function(){
			var $this = $(this);
			if(!$this.hasClass("open-new-tab-listener")) {
				$this.addClass("open-new-tab-listener");
				$this.click(function(){
					openProp($(this),null,'open-new-tab');
				});
			}
		});
		$idTag.find(".open-blank").each(function(){
			var $this = $(this);
			if(!$this.hasClass("open-blank-listener")) {
				$this.addClass("open-blank-listener");
				$this.click(function(){
					openProp($(this),null,'open-blank');
				});
			}
		});
	}
})(jQuery)


/**
 * 监听单击全选(复选框)
 * @param $elementWrap 元素对象
 * 标识
 *  class="cnoj-checkbox-all" 
 *  参数
 *    必须
 *      data-target 指定要选中的复选框
 *  单机该复选框会选中(或取消)data-target指定的所有class为"cnoj-op-checkbox"的复选框
 *  对应的值会赋值到指定 "<div class='btn-list'><div class='cnoj-op-btn-list'></div></div>" 
 *  里面class为"param"元素里面.
 *  
 */
function checkboxAllListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || $elementWrap.length == 0) {
		$(".cnoj-checkbox-all").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-checkbox-all").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理复选框全选情况
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-checkbox-all-listener")) {
			$element.addClass("cnoj-checkbox-all-listener");
			$element.click(function(event) {
				_clickElement(event, $(this));
			});
		}
	}
	
	/**
	 * 单机全选复选框时候处理的方法（单击元素）
	 * @param event
	 * @param $this
	 */
	function _clickElement(event,$this) {
		var target = $this.data("target");
		var $panel = $this.parents(".panel:eq(0)");
		if(utils.isNotEmpty(target)) {
			if($this.prop("checked")) {
				$panel.find(target).each(function(){
					if(!$(this).prop("disabled")) {
						var $tr = $(this).parents("tr.tr-mutil-selected:eq(0)");
						if($tr.length>0) {
							$tr.addClass("ui-state-focus");
							$tr.find("td").addClass("ui-state-focus");
						}
						$(this).prop("checked",true);
					}
				});
			} else {
				$panel.find(target).each(function(){
					var $tr = $(this).parents("tr.tr-mutil-selected:eq(0)");
					if($tr.length>0) {
						$tr.removeClass("ui-state-focus");
						$tr.find("td").removeClass("ui-state-focus");
					}
					$(this).prop("checked",false);
				});
			}
		} 
		if($this.hasClass("cnoj-op-checkbox")) {
			var ids = "";
			$panel.find(target).each(function(){
				if($(this).prop("checked")) {
					var id = $(this).val();
					if(!utils.isEmpty(id))
					   ids += id+",";
				}
			});
			if(utils.isNotEmpty(ids))
				ids = ids.substring(0, ids.length-1);
			var $param = null;
			if($panel.length>0) {
				$param = $panel.find(".cnoj-op-btn-list .param");
				if($param.length == 0) {
					$param = null;
				}
			}
			if(null != $param) {
				$param.attr("selected-value",ids);
			}
		}
	}
}


/**
 * 监听单击单个复选框
 * @param $elementWrap 元素对象
 * class="cnoj-op-checkbox"
 * 点击该复选框时，会把对应的值赋值到指定的地方；"<div class='btn-list'><div class='cnoj-op-btn-list'></div></div>" 
 * 下面class为"param"元素里面.
 * 
 */
function checkboxListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || $elementWrap.length == 0) {
		$(".cnoj-op-checkbox").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-op-checkbox").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理复选框
	 * @param $element
	 * 
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-op-checkbox-listener")) {
			$element.addClass("cnoj-op-checkbox-listener");
			if(!$element.hasClass("cnoj-checkbox-all")) {
				$element.click(function(event){
					_clickElement(event, $(this));
					
				});
			}
		}
	}
	
	/**
	 * 单击复选框时处理的方法
	 * @param event
	 * @param $this
	 */
	function _clickElement(event, $this) {
		var ids = "";
		$this.parents(".cnoj-checkbox-wrap:eq(0)").find(".cnoj-op-checkbox").each(function(){
			if($(this).prop("checked")) {
				var id = $(this).val();
				if(!utils.isEmpty(id))
				   ids += id+",";
			}
		});
		if(utils.isNotEmpty(ids))
			ids = ids.substring(0, ids.length-1);
		var $panel = $this.parents(".panel:eq(0)");
		var $param = null;
		if($panel.length > 0) {
			$param = $panel.find(".cnoj-op-btn-list .param");
			if($param.length == 0) {
				$param = null;
			}
		}
		if(null != $param) {
			$param.attr("selected-value",ids);
		}
	}
}

/**
 * 表单必填监听
 * @param $elementWrap
 * 标识
 * class="require"
 */
function formRequireListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$("input[type=text].require,input[type=file].require,select.require,textarea.require").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find("input[type=text].require,input[type=file].require,select.require,textarea.require").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理必填元素
	 * @param $element
	 */
	function _handler($element) {
		var id = $element.attr("id");
		if(utils.isEmpty(id)) {
		    id = utils.UUID();
		} 
		var newIdTag = id+"-"+"require";
		if(!$element.prop("disabled") && !$element.prop("readonly")) {
			if($element.parent().find("#"+newIdTag).length == 0) {
				$element.after("<span id='"+newIdTag+"' class='star-require hidden-print'> * </span>");
			}
		} else if($element.prop("readonly") && $element.hasClass("require")) {  
			//如果输入框是只读，并且有必填标记时，去掉必填标记，去掉输入框后面的星号
			$element.removeClass("require");
			$element.parent().find("#"+newIdTag).remove();
		}
	}
}

/**
 * 链接监听,也可以是按钮或其他
 * @param $elementWrap 元素对象
 * class="cnoj-change-page" 该标识主要是用来标记分页，点击页面时触发的事件
 *    参数:必须 data-uri 分页uri
 *       可选 data-target 显示地方(一般为一个div层)
 *            data-search-panel-tag 搜索面板标识
 *   
 * class="cnoj-open-self" 点击时，指定的uri显示到当前"#main-content"里面
 *    参数：必须 data-uri 显示uri
 *        可选 data-target 显示地方(一般为一个div层)
 *        
 * class="cnoj-open-blank" 点击时，会弹出一个新窗口（弹出窗口）;  
 *    参数： 必须 data-uri 弹出页面的uri
 *         可选 data-title 弹出窗口的标题;
 *              data-width 弹出窗口的宽度;
 *              data-open-target 弹出窗口的方式；针对有iframe的情况；
 *                  如：值为：parent;会在父页面上提出窗口
 *
 * class="cnoj-open-tabs" 点击时，指定的uri在新的tab中打开
 *    参数：必须 data-uri 显示uri
 *           data-title tabs的名称
 *         
 */
function hrefListener($elementWrap) {
	changePageListener($elementWrap);
	openSelfListener($elementWrap);
	openBlankListener($elementWrap);
	openTabsListener($elementWrap);
	
	/**
	 * 改变页面监听
	 * @param $elementWrap
	 */
	function changePageListener($elementWrap) {
		if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
			$(".cnoj-change-page").each(function(){
				_handler($(this));
			});
		} else {
			$elementWrap.find(".cnoj-change-page").each(function(){
				_handler($(this));
			});
		}

		/**
		 * 处理分页元素
		 * @param $element
		 */
		function _handler($element) {
			if(!$element.hasClass("cnoj-change-page-listener")) {
				$element.addClass("cnoj-change-page-listener");
				$element.click(function(event) {
					_clickElement(event, $(this));
					return false;
				});
			}
		}
		
		/**
		 * 点击元素处理方法
		 * @param event
		 * @param $this
		 */
		function _clickElement(event, $this) {
			var uri = $this.data("uri");
			uri = utils.isEmpty(uri)?$this.attr("href"):uri;
			//获取搜索参数
			var searchPanelTag = $this.data("search-panel-tag");
			var $searchPanel = null;
			if(utils.isEmpty(searchPanelTag)) {
				$searchPanel = $this.parents(".panel:eq(0)").find(">.panel-search");
			} else {
				$searchPanel = $(searchPanelTag);
			}
			if(utils.isExist($searchPanel)) {
				var $form = $searchPanel.find("form");
				if(utils.isExist($form)) {
					uri = uri+"&"+$form.serialize();
				}
			}
			//获取页面显示数量;即每页显示数
	    	var $pageSize = $this.parents(".panel-footer-page:eq(0)").find(".cnoj-change-pagesize");
	    	if($pageSize.length>0) {
	    		uri +="&pageSize="+$pageSize.val();
	    	}
			var target = $this.data("target");
			if (utils.isNotEmpty(uri)) {
				if(utils.isNotEmpty(target))
					loadUri(target,uri,true);
				else
					loadActivePanel(uri);
			}
		}
	} //end fun
	
	/**
	 * open-self 事件监听
	 * @param $elementWrap
	 */
	function openSelfListener($elementWrap) {
		if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
			$(".cnoj-open-self").each(function(){
				_handler($(this));
			});
		} else {
			$elementWrap.find(".cnoj-open-self").each(function(){
				_handler($(this));
			});
		}

		/**
		 * 处理必填元素
		 * @param $element
		 */
		function _handler($element) {
			if(!$element.hasClass("cnoj-open-self-listener")) {
				$element.addClass("cnoj-open-self-listener");
				$element.click(function(event) {
					_clickElement(event, $(this));
					return false;
				});
			}
		}
		
		/**
		 * 点击元素处理方法
		 * @param event
		 * @param $this
		 */
		function _clickElement(event, $this) {
			var uri = $this.data("uri");
			var target = $this.data("target");
			var title = $this.data("title");
			if(utils.isEmpty(uri)) {
				uri = $this.attr("href");
			}
			if (!utils.isEmpty(uri)) {
				/*if(!utils.isEmpty(target) && target != '#main-content')
					loadUri(target, uri, true);
				else */
				    loadActivePanel(uri);
					//openTab(title, uri, true);
			}
		}
	}
	
	/**
	 * open-blank 监听
	 * @param $elementWrap
	 */
	function openBlankListener($elementWrap) {
		if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
			$(".cnoj-open-blank").each(function(){
				_handler($(this));
			});
		} else {
			$elementWrap.find(".cnoj-open-blank").each(function(){
				_handler($(this));
			});
		}

		/**
		 * 处理元素超链接新开窗口（弹出窗口）
		 * @param $element
		 */
		function _handler($element) {
			if(!$element.hasClass("cnoj-open-blank-listener")) {
				$element.addClass("cnoj-open-blank-listener");
				$element.click(function(event) {
					_clickElement(event, $(this));
					return false;
				});
			}
		}
		
		/**
		 * 点击元素处理方法
		 * @param event
		 * @param $this
		 */
		function _clickElement(event, $this) {
			var uri = $this.data("uri");
	        var title = $this.data("title");
	        var w = $this.data("width");
	        var openTarget = $this.data("open-target");
	        if(utils.isEmpty(uri)) {
	        	uri = $this.attr("href");
	        }
	        if(!utils.isEmpty(uri)) {
	          if(utils.isEmpty(w)) {
	        	  w = $(window).width()-50;
	          }
	          if(openTarget == 'parent') {
	        	  parent.BootstrapDialogUtil.loadUriDialog(title, uri, w, "#fff", false, function(dialog){
						setTimeout(function(){
							initEvent(dialog.getModal());
						}, 200);
				  });
	          } else {
		          BootstrapDialogUtil.loadUriDialog(title, uri, w, "#fff", false, function(dialog){
						setTimeout(function(){
							initEvent(dialog.getModal());
						}, 200);
				  });
	          }
	        }
		}
	}
	
	/**
	 * open-tabs 监听
	 * @param $elementWrap
	 */
	function openTabsListener($elementWrap) {
		if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
			$(".cnoj-open-tabs").each(function(){
				_handler($(this));
			});
		} else {
			$elementWrap.find(".cnoj-open-tabs").each(function(){
				_handler($(this));
			});
		}

		/**
		 * 处理元素超链接打开一个tabs
		 * @param $element
		 */
		function _handler($element) {
			if(!$element.hasClass("cnoj-open-tabs-listener")) {
				$element.addClass("cnoj-open-tabs-listener");
				$element.click(function(event) {
					_clickElement(event, $(this));
					return false;
				});
			}
		}
		
		/**
		 * 点击元素处理方法
		 * @param event
		 * @param $this
		 */
		function _clickElement(event, $this) {
			var uri = $this.data("uri");
	        var title = $this.data("title");
	        if(utils.isEmpty(uri)) {
	        	uri = $this.attr("href");
	        }
	        if(!utils.isEmpty(uri) && utils.isNotEmpty(title)) {
	        	openTab(title, uri, true);
	        } else {
	        	alert("uri或title属性不能为空");
	        }
		}
	}
	
}

/**
 * 加载到当前激活的面板中
 * @param uri
 */
function loadActivePanel(uri) {
	reloadTab(uri); 
}

/**
 * 单击搜索按钮提交数据
 * @param $elementWrap 元素对象
 * 标识
 *   class="cnoj-search-submit" 标记在触发按钮上
 *   参数
 *     必须
 *       action 该参数为form表单的action属性；提交的路径url
 *     可选
 *       target 该参数为form表单的target属性；提交数据请求之后，返回内容显示的位置，默认为"#main-content"
 *       data-loading-target-tag 要获取载入页面指定的内容
 */
function searchSubmitListener($elementWrap){
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-search-submit").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-search-submit").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-search-submit-listener")) {
			$element.addClass("cnoj-search-submit-listener");
			$element.click(function(event) {
				_clickElement(event, $(this));
				return false;
			});
		}
	}
	
	/**
	 * 点击元素处理方法
	 * @param event
	 * @param $this
	 */
	function _clickElement(event, $this) {
		var $form = $this.parents("form:eq(0)");
		var param = $form.serialize();
	    var uri = $form.attr("action");
	    var target = $form.attr("target");
	    var loadingTargetTag = $form.data("loading-target-tag");
	    //param = encodeURI(param);
	    if(!utils.isEmpty(uri)) {
	    	if(utils.isContain(uri, "?")) {
	    		 uri = uri+"&"+param;
	    	} else {
	    		 uri = uri+"?"+param;
	    	}
	    	//获取页面显示数量;即每页显示数
	    	var $pageSize = $form.parents(".panel:eq(0)").find(".cnoj-change-pagesize");
	    	if($pageSize.length>0) {
	    		uri +="&pageSize="+$pageSize.val();
	    	}
	    	uri = utils.isEmpty(loadingTargetTag)?uri:uri+" "+loadingTargetTag;
	 	    if(!utils.isEmpty(target) && mainTag != target) {
	 		    loadUri(target,uri);
	 	    } else {
	 	    	loadActivePanel(uri);
	 	    }
	    }
	}
}

/**
 * 载入指定的uri到DIV标识为id的层中
 * @param id
 * @param uri
 * @param isLoadProcess 是否有加载等待提示,默认为:true
 * @param isCheckLogin 是否验证用户登录,默认为:true(访问页面时验证用户是否已登录)
 */
function loadUri(id,uri,isLoadProcess,isCheckLogin) {
	if(false != isLoadProcess) {
		isLoadProcess = true;
	}
	if(utils.isEmpty(isCheckLogin)) {
		isCheckLogin = true;
	}
	isCheckLogin = isCheckLogin == false ? false : true;
	if(isCheckLogin) {
		$.get("user/islogin.json",function(data){
			if(data.result=='1') {
				handleLoading(uri,$(id));
			} else {
				location.reload();
			}
		});
	} else {
		handleLoading(uri,$(id));
	}
}

/**
 * 加载处理
 * @param uri
 * @param obj
 */
function handleLoading(uri,obj) {
	var array = utils.parseUri(uri);
	if(null != array) {
		obj.html('<div class="cnoj-loading"><i class="fa fa-spinner fa-spin fa-lg"></i> 正在加载，请稍候...</div><div class="loading-content"></div>');
		var $target = obj.find(".loading-content");
		$target.css("visibility","hidden");
		$("body").css({"overflow":"hidden"});
		$target.load(array[0],array[1],function() {
			setTimeout(function(){
				obj.find(".cnoj-loading").remove();
				initEvent($target);
				$target.css("visibility","visible");
				$("body").css("overflow","auto");
			}, 200);
		});
	}
}

/**
 *  
 * 表格树监听
 * @param $elementWrap
 * 标识
 * table class为:"cnoj-tree-table"
 * tr class为:"tr-tree"
 * td class为:"op-tree"
 */
function tableTreeListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-tree-table .tr-tree .op-tree").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-tree-table .tr-tree .op-tree").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理必填元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("op-tree-listener")) {
			$element.addClass("op-tree-listener");
			$element.click(function(event) {
				_clickElement(event, $(this));
				autoTableWidth();
				return false;
			});
		}
	}
	
	/**
	 * 点击元素处理方法
	 * @param event
	 * @param $this
	 */
	function _clickElement(event, $this) {
		if($this.hasClass("shrink-data")) {
			var $spanIcon = $this.find("span.ui-icon");
			var trId = $this.parent().attr("id");
			if(trId !== 'undefined') {
				$this.parents("table:eq(0)").find("."+trId).show();
				$spanIcon.removeClass("ui-icon-triangle-1-e");
				$spanIcon.addClass("ui-icon-triangle-1-s");
				$this.removeClass("shrink-data");
				$this.addClass("open-data");
			}
		} else {
			var stackArray = new Array();
			var id = $this.parent().attr("id");
			if(!utils.isEmpty(id)) {
				$this.parents("table:eq(0)").find("."+id).hide();
				var $spanIcon = $this.find("span.ui-icon");
				$spanIcon.removeClass("ui-icon-triangle-1-s");
				$spanIcon.addClass("ui-icon-triangle-1-e");
				
				$this.removeClass("open-data");
				$this.addClass("shrink-data");
				stackArray.push(id);
			}
			var $table = $this.parents("table:eq(0)");
			while(stackArray.length>0) {
				stackArray = stackArray.concat(_shrinkTableTree(stackArray.pop(), $table));
			}
			stackArray = null;
		}
	}
	
	/**
	 * 收缩表格树
	 * @param id
	 * @param $table
	 * @returns {Array}
	 */
	function _shrinkTableTree(id, $table) {
		var array = new Array();
		function __handler($this) {
			var parentId = $this.parent().attr("parentid");
			$table.find("."+id).hide();
			var $spanIcon = $table.find("#"+id+" span.ui-icon");
			$spanIcon.removeClass("ui-icon-triangle-1-s");
			$spanIcon.addClass("ui-icon-triangle-1-e");
			
			$table.find("#"+id+" .op-tree").removeClass("open-data");
			$table.find("#"+id+" .op-tree").addClass("shrink-data");
			if(id == parentId) {
				var trId = $this.parent().attr("id");
				array.push(trId);
			}
		}
		if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
			$(".cnoj-tree-table .open-data").each(function(){
				__handler($(this));
			});
		} else {
			$elementWrap.find(".cnoj-tree-table .open-data").each(function(){
				__handler($(this));
			});
		}
		return array;
	}
}



/**
 * 树形表格行选中监听
 * @param $elementWrap
 * 标识
 * table为：class="cnoj-tree-table"
 * tr为：class="tr-tree"
 */
function tableTreeSelectListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-tree-table .tr-tree").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-tree-table .tr-tree").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("tr-tree-listener")) {
			$element.addClass("tr-tree-listener");
			$element.click(function(event) {
				_clickElement(event, $(this));
				//autoTableWidth();
				return false;
			});
		}
	}
	
	/**
	 * 点击元素处理方法
	 * @param event
	 * @param $this
	 */
	function _clickElement(event, $this) {
		var $wrap = $this.parents("table:eq(0)").parent();
		$wrap.find(".cnoj-tree-table .tr-tree").each(function(){
			$(this).removeClass("ui-state-focus");
			$(this).find("td").removeClass("ui-state-focus");
		});
		var $param = null;
		var $panel = $this.parents(".panel:eq(0)");
		if($panel.length>0) {
			$param = $panel.find(".cnoj-op-btn-list .param");
			if($param.length == 0) {
				$param = null;
			}
		}
		if(!$this.hasClass("ui-state-focus")) {
			$this.addClass("ui-state-focus");
			$this.find("td").addClass("ui-state-focus");
			var id = $this.attr("id");
			id = id.substring(2,id.length);
			if(null != $param) {
				$param.attr("selected-value",id);
			}
		} else {
			$this.removeClass("ui-state-focus");
			$this.find("td").removeClass("ui-state-focus");
			if(null != $param) {
				$param.attr("selected-value","");
			}
		}
	}
}


/**
 * 表格行选中监听
 * 表格标识
 *    class="cnoj-table" 如:<table class="cnoj-table"></table>
 * 该表格最好放到一个叫class="panel"的div里面，如:<div class="panel"><table class="cnoj-table"></table></div>
 *    选中行的class必须为"tr-selected";如 <tr class="tr-selected"></tr>
 *    如果只能选中单行，则class中要有"tr-one-selected";可以选中多行，则要有"tr-mutil-selected";如:<tr class="tr-selected tr-one-selected"></tr>
 *    或<tr class="tr-selected tr-mutil-selected"></tr>
 *要获取选中的参数，则，放到<div class="btn-list"><div class="cnoj-op-btn-list"></div></div>里面
 *并且class设置为param
 *    
 */
function tableSelectListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-table .tr-selected").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-table .tr-selected").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("tr-selected-listener")) {
			$element.addClass("tr-selected-listener");
			$element.click(function(event) {
				_clickElement(event, $(this));
			});
		}
	}
	
	/**
	 * 点击元素处理方法
	 * @param event
	 * @param $this
	 */
	function _clickElement(event, $this) {
		var $panel = $this.parents(".panel:eq(0)");
		var $param = null;
		if($panel) {
			$param = $panel.find(".btn-list .cnoj-op-btn-list .param");
			if(typeof($param.attr("class")) === 'undefined') {
				$param = null;
			}
		}
		var ids = null;
		var $wrap = $this.parents("table:eq(0)").parent(); 
		//单选
		if($this.hasClass("tr-one-selected")) {
			$wrap.find(".cnoj-table .tr-one-selected").each(function(){
				 $(this).removeClass("ui-state-focus");
				 $(this).find("td").removeClass("ui-state-focus");
			});
			if(!$this.hasClass("ui-state-focus")) {
				$this.addClass("ui-state-focus");
				$this.find("td").addClass("ui-state-focus");
				ids = $this.attr("id");
				ids = ids.substring(2,ids.length);
				if(null != $param) {
					$param.attr("selected-value",ids);
				}
			} else {
				$this.removeClass("ui-state-focus");
				$this.find("td").removeClass("ui-state-focus");
				if(null != $param) {
					$param.attr("selected-value","");
				}
			}
		} else if($this.hasClass("tr-mutil-selected")) {
			if(!$this.hasClass("ui-state-focus")) {
				$this.addClass("ui-state-focus");
				$this.find("td").addClass("ui-state-focus");
				var $opcheckbox = $this.find(".cnoj-op-checkbox");
				if(!$opcheckbox.prop("checked")) {
					$opcheckbox.prop("checked",true);
				}
			} else {
				$this.removeClass("ui-state-focus");
				$this.find("td").removeClass("ui-state-focus");
				var $opcheckbox = $this.find(".cnoj-op-checkbox");
				if($opcheckbox.prop("checked")) {
					$opcheckbox.prop("checked",false);
				}
			}
			ids = "";
			$this.parents(".cnoj-checkbox-wrap:eq(0)").find(".cnoj-op-checkbox").each(function(){
				if($(this).prop("checked")) {
					var id = $(this).val();
				    if(!utils.isEmpty(id))
				        ids += id+",";
				}
			});
			if(!utils.isEmpty(ids))
				ids = ids.substring(0, ids.length-1);
			if(null != $param) {
				$param.attr("selected-value",ids);
			}
		}
		//执行选中触发事件
		var selectedEventType = $this.data("selected-type");
		if(!utils.isEmpty(selectedEventType)) {
			var selectedUri = $this.data("selected-uri");
			var selectedTarget = $this.data("selected-target");
			var selectedVarName = $this.data("selected-varname");
			selectedVarName = utils.isEmpty(selectedVarName)?"id":selectedVarName;
			if(!utils.isEmpty(selectedUri) && !utils.isEmpty(ids)) {
				selectedUri = selectedUri+(selectedUri.indexOf("?")>0?"&":"?")+selectedVarName+"="+ids;
				cnoj.selectedEvent(selectedEventType,selectedUri,selectedTarget);
			}
		}
	}
}


/**
 * 监听表单输入框树
 * @param $elementWrap
 * 标识
 *   class='cnoj-input-tree'
 * 参数
 *   必须
 *     data-uri 指定数据来源uri 
 *   可选
 *     data-is-show-none 是否显示"无"数据节点;默认为:no(不显示) 可选的值为:"yes"或"no"
 *     data-is-ajax-async 加载数据时是ajax否异步加载；默认为:no(不显示) 可选的值为:"yes"或"no"
 *     data-is-async 是否异步加载（即：分步加载）；默认为:no(不显示) 可选的值为:"yes"或"no"
 *     data-async-url 异步加载的话（即：data-is-async="yes"），异步加载URL;
 *     data-on-click 列表单机时除非该事件；传递js方法名称 
 */
function inputTreeListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-input-tree").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-input-tree").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if($element.prop("disabled") || $element.hasClass("hide")) {
			return;
		}
		if(!$element.hasClass("cnoj-input-tree-listener")) {
			$element.addClass("cnoj-input-tree-listener");
			var uri = $element.data("uri");
			var isShowNode = $element.data("is-show-none");
			isShowNode = (isShowNode == 'yes'?true:false);
			var isAjaxAsync = $element.data("is-ajax-async");
			isAjaxAsync = (isAjaxAsync == 'yes'?true:false);
			var onClickFun = $element.data("on-click");
			onClickFun = utils.isEmpty(onClickFun)?null:onClickFun;
			var isAsync = $element.data("is-async");
			isAsync = (isAsync == 'yes'?true:false);
			var asyncUrl = null;
			if(isAsync) {
				asyncUrl = $element.data("async-url");
				if(utils.isEmpty(asyncUrl)) {
					asyncUrl = uri;
				}
			}
			if(!utils.isEmpty(uri)) {
				$element.zTreeUtil({
					uri:uri,
					isAsync : isAsync,
					isAjaxAsync : isAjaxAsync,
					getAsyncUri: asyncUrl,
					isInput:true,
					isInputTreeShow:false,
					isShowNone:isShowNode,
					onClick:onClickFun
				});
			}
			$element.click(function(event) {
				if(!utils.isEmpty(uri)) {
					$(this).zTreeUtil({
						uri:uri,
						isInput:true,
						isShowNone:isShowNode,
						onClick:onClickFun
					});
				}
				event.stopPropagation();
			});
		}
	}
}

/**
 * 监听表单组织机构树
 * @param $elementWrap
 * 标识
 *   class='cnoj-input-org-tree'
 *   参数
 *   可选
 *     data-is-show-none 是否显示"无"数据节点;默认为:no(不显示) 可选的值为:"yes"或"no"
 */
function inputOrgTreeListener($elementWrap) {
	var uri = 'org/tree.json';
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-input-org-tree").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-input-org-tree").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if($element.prop("disabled") || $element.hasClass("hide")) {
			return;
		}
		if(!$element.hasClass("cnoj-input-org-tree-listener")) {
			$element.addClass("cnoj-input-org-tree-listener");
			var isShowNode = $element.data("is-show-none");
			isShowNode = (isShowNode == 'yes'?true:false);
			var isAjaxAsync = $element.data("is-ajax-async");
			isAjaxAsync = (isAjaxAsync == 'yes'?true:false);
			
			var isAsync = $element.data("is-async");
			isAsync = (isAsync == 'yes'?true:false);
			if(!utils.isEmpty(uri)) {
				$element.zTreeUtil({
					uri:uri,
					isInput:true,
					isAsync : isAsync,
					isAjaxAsync : isAjaxAsync,
					isInputTreeShow:false,
					isShowNone:isShowNode
				});
			}
			$element.click(function(event){
				if(!utils.isEmpty(uri)) {
					$(this).zTreeUtil({
						uri:uri,
						isInput:true,
						isShowNone:isShowNode
					});
				}
				event.stopPropagation();
			});
		}
	}
}


/**
 * 监听面板树
 * @param $elementWrap
 * 标识
 *   class='cnoj-panel-tree'
 * 参数
 *   必须
 *     data-uri 指定数据来源uri
 *   可选
 *     data-redirect-uri 点击节点之后触发的uri
 *     data-is-search 是否支持搜索功能;yes--支持；no--不支持;默认为：no
 *     data-is-node-link 节点是否支持超链接;yes--支持;no--不支持；默认为：no
 *     data-is-default-load 是否默认加载data-redirect-uri指定的uri，该选项设置了data-is-node-link为"yes"的时候才生效
 *     data-target 指定uri内容显示的位置；如果该值为空或没设置，则默认显示到"#main-content"层，一般与"data-redirect-uri"成对出现;
 *     data-param-name 指定节点ID的参数名称，默认名称为："id"
 */
function panelTreeListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-panel-tree").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-panel-tree").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-panel-tree-listener")) {
			$element.addClass("cnoj-panel-tree-listener");
			var uri = $element.data("uri");
			var redirectUri = $element.data("redirect-uri");
			var target = $element.data("target");
			var isSearch = $element.data("is-search");
			isSearch = utils.isEmpty(isSearch)?false:(isSearch=='yes'?true:false);
			var paramName = $element.data("param-name");
			var isNodeLink = $element.data("is-node-link");
			var panelHeight = $element.data("panel-height");
			panelHeight = utils.regexInteger(panelHeight)?panelHeight:0;
			isNodeLink = utils.isEmpty(isNodeLink)?false:(isNodeLink == 'yes'?true:false);
			var isDefaultLoad = false;
			if(isNodeLink) {
				isDefaultLoad = $element.data("is-default-load");
				isDefaultLoad = utils.isEmpty(isDefaultLoad)?false:(isDefaultLoad == 'yes'?true:false);
			}
			
			var isAjaxAsync = $element.data("is-ajax-async");
			isAjaxAsync = (isAjaxAsync == 'yes'?true:false);
			
			var isAsync = $element.data("is-async");
			isAsync = (isAsync == 'yes'?true:false);
			
			if(utils.isNotEmpty(uri)) {
				$element.zTreeUtil({
					uri:uri,
					isInput:false,
					isAsync : isAsync,
					isAjaxAsync : isAjaxAsync,
					redirectUri:redirectUri,
					target:target,
					isSearch:isSearch,
					panelHeight:panelHeight,
					isNodeLink:isNodeLink,
					isDefaultLoad:isDefaultLoad,
					paramName:paramName
				});
			}
		}
	}
}

/**
 * 监听还有复选框面板树
 * 标识
 *   class='cnoj-panel-check-tree'
 * 参数
 *   必须
 *     data-uri 指定数据来源uri
 *   可选
 *     data-is-search 是否支持搜索功能;值为"yes"或"no"
 *     data-param-name 指定节点ID的参数名称，默认名称为："id"
 *     data-check-opt 选项json格式;如:{check:{chkStyle:"radio",radioType: "level/all"}};
 *     data-is-ajax-async 加载数据时是ajax否异步加载；默认为:yes(异步加载) 可选的值为:"yes"或"no"
 *     data-is-async 是否异步加载（即：分步加载）；默认为:no(否) 可选的值为:"yes"或"no"
 *     data-async-url 异步加载的话（即：data-is-async="yes"），异步加载URL
 */
function panelCheckTreeListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-panel-check-tree").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-panel-check-tree").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-panel-check-tree-listener")) {
			$element.addClass("cnoj-panel-check-tree-listener");
			var uri = $element.data("uri");
			var isSearch = $element.data("is-search");
			    isSearch = utils.isNotEmpty(isSearch)?("yes"==isSearch?true:false):false;
			var checkOpt = $element.data("check-opt");
			    checkOpt = utils.isNotEmpty(checkOpt)?checkOpt:null;
			var paramName = $element.data("param-name");
			    paramName = utils.isEmpty(paramName)?"id":paramName;
			var isAjaxAsync = $element.data("is-ajax-async");
			isAjaxAsync = (isAjaxAsync == 'no'?false:true);
			var isAsync = $element.data("is-async");
			isAsync = (isAsync == 'yes'?true:false);
			var asyncUrl = null;
			if(isAsync) {
				asyncUrl = $element.data("async-url");
				if(utils.isEmpty(asyncUrl)) {
					asyncUrl = uri;
				}
			}
			if(!utils.isEmpty(uri)) {
				$element.zTreeUtil({
					uri:uri,
					isInput:false,
					isAsync : isAsync,
					isAjaxAsync : isAjaxAsync,
					getAsyncUri: asyncUrl,
					isCheck:true,
					checkOpt:checkOpt,
					isSearch:isSearch,
					paramName:paramName
				});
			}
		}
	}
}

/**
 * 监听面板组织结构树
 * 标识
 *   class='cnoj-panel-org-tree'
 * 参数
 *   data-redirect-uri 添加节点之后触发的uri
 *   data-target 指定uri内容显示的位置；如果该值为空或没设置，则默认显示到"#main-content"层，一般与"data-redirect-uri"成对出现;
 *   data-param-name 指定节点ID的参数名称，默认名称为："id"
 * 
 */
function panelOrgTreeListener($elementWrap) {
	var uri = 'org/tree';
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-panel-org-tree").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-panel-org-tree").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-panel-org-tree-listener")) {
			$element.addClass("cnoj-panel-org-tree-listener");
			var redirectUri = $element.data("redirect-uri");
			var target = $element.data("target");
			var isSearch = $element.data("is-search");
			isSearch = utils.isEmpty(isSearch)?false:(isSearch=='yes'?true:false);
			var paramName = $element.data("param-name");
			var isNodeLink = $element.data("is-node-link");
			var panelHeight = $element.data("panel-height");
			panelHeight = utils.regexInteger(panelHeight)?panelHeight:0;
			isNodeLink = utils.isEmpty(isNodeLink)?false:(isNodeLink == 'yes'?true:false);
			var isDefaultLoad = false;
			if(isNodeLink) {
				isDefaultLoad = $element.data("is-default-load");
				isDefaultLoad = utils.isEmpty(isDefaultLoad)?false:(isDefaultLoad == 'yes'?true:false);
			}
			var isAjaxAsync = $element.data("is-ajax-async");
			isAjaxAsync = (isAjaxAsync == 'yes'?true:false);
			var isAsync = $element.data("is-async");
			isAsync = (isAsync == 'yes'?true:false);
			if(!utils.isEmpty(uri)) {
				$element.zTreeUtil({
					uri:uri,
					isInput:false,
					isAsync : isAsync,
					isAjaxAsync : isAjaxAsync,
					redirectUri:redirectUri,
					target:target,
					isSearch:isSearch,
					panelHeight:panelHeight,
					isNodeLink:isNodeLink,
					isDefaultLoad:isDefaultLoad,
					paramName:paramName
				});
			}
		}
	}
}

/**
 * 提交按钮监听
 * 标识
 * class="cnoj-data-submit"
 * 该标识是提交表单触发的事件
 * 除了action参数在form表单里面填写之外，其他的参数都在该标识元素中填写
 * 参数
 *   必须
 *      action 该参数就是form表单的action属性,在提交的form的action里面填写该值,指定提交的路径(action)
 *   可选
 *      data-target-form 该参数，指定提交哪个form表单的ID（有多个form表单时）;如:target-form="#add-form";
 *      data-fun 该参数是一个回调函数，提交成功后执行的方法;
 *      data-refresh-uri 该参数指定数据指定成功后，刷新的uri;
 *      target 该参数为form表单的target属性；提交数据请求之后，返回的内容显示到哪个里面，默认为"#main-content"
 *      与data-refresh-uri成对出现.
 *      
 */
function submitBtnListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-data-submit").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-data-submit").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-data-submit-listener")) {
			$element.addClass("cnoj-data-submit-listener");
			$element.click(function(event){
				_clickElement(event, $(this));
				event.stopPropagation();
				return false;
			});
		}
	}
	
	/**
	 * 点击元素处理方法
	 * @param event
	 * @param $this
	 */
	function _clickElement(event, $this) {
		var target = $this.data("target-form");
		var fun = $this.data("fun");
		var $form = null;
		if(!utils.isEmpty(target)) {
			if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
				$form = $("#"+target);
			} else {
				$form = $elementWrap.find("#"+target);
			}
			if(typeof($form.attr("id")) === 'undefined') {
				$form = null;
			}
		} else {
			 $form = $this.parents("form:eq(0)");
		}
		if($form.validateForm()) {
			var param = $form.serialize();
		    var uri = $form.attr("action");
		    cnoj.submitDialogData(uri,param,fun,$this,$form);
		}
	}
}

/**
 * 监听表格内容的高度
 * @param $elementWrap
 * @param isResize 是否改变窗口大小
 * 标识
 * class="cnoj-table-wrap"
 * 该标识是用于包裹表格而设计的，非表格请使用"auto-limit-height"标识
 */
function tableWrapListener($elementWrap, isResize) {
	isResize = utils.isEmpty(isResize) ? false : (isResize == true);
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-table-wrap").each(function(){
			return _handler($(this), isResize);
		});
	} else {
		$elementWrap.find(".cnoj-table-wrap").each(function(){
			var $this = $(this);
			//判断是否在bootstrap的tabs隐藏panel中；如果在则不处理
			var $tabpanel = $this.parents(".tab-pane:eq(0)");
			if($tabpanel.length == 0 || $tabpanel.css("display") !='none') {
				_handler($this, isResize);
			}
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 * @param isResize
	 */
	function _handler($element, isResize) {
		if(!isResize && $element.hasClass("cnoj-table-wrap-listener")) {
			return true;
		}
		$element.addClass("cnoj-table-wrap-listener");
		var $tableWrap = $element;
		var h = 0;
		var $autoLimitHeiht = $tableWrap.parents(".cnoj-auto-limit-height:eq(0)");
		if(utils.isExist($autoLimitHeiht)) {
			limitHeightListener($elementWrap, false);
			h = $autoLimitHeiht.height();
			var $table = $tableWrap.find("table");
			var borderWidth = $table.css("border-width");
			if(utils.isEmpty(borderWidth)) {
			    borderWidth = 0;
			} else {
			    borderWidth = parseInt($table.css("border-width"));
			}
			h = h - borderWidth * 2 - 1;
		} else {
			//判断是否是弹出窗口中的表格
			var $modalDialog = $tableWrap.parents(".bootstrap-dialog-message:eq(0)");
			if(utils.isExist($modalDialog)) {
				h = $(window).height();
				h = h - 60 - 40 - 10;
			} else {
				if(utils.isIframe) {
					h = $(window).height();
				} else {
					h = getMainHeight();
					//去掉tabs高度
					h = h - getTabHeaderHeight();
				}
				h = h - 10;
			}
		}
		var $parentWrap = $tableWrap.parent();
		var subtractHeight = $parentWrap.data("subtract-height");
		subtractHeight = utils.isEmpty(subtractHeight)?0:subtractHeight;
		h = h - subtractHeight;
		
		var panelHeadingHeight = 0;
		var $panelHeading = $parentWrap.find(".panel-heading");
		if(utils.isExist($panelHeading)) {
			panelHeadingHeight = Math.ceil($panelHeading.outerHeight(true));
			var panelHeadingDataH = $panelHeading.data("height");
			if(panelHeadingHeight == 0 && utils.isNotEmpty(panelHeadingDataH)) {
				panelHeadingHeight = panelHeadingDataH;
			}
		}
		h = h - panelHeadingHeight;
		var $panelFooter = $parentWrap.find(".panel-footer");
		var panelFooterDataH = $panelFooter.data("height");
		var panelFooterHeight = Math.ceil($panelFooter.outerHeight(true));
		if(panelFooterHeight == 0 && utils.isNotEmpty(panelFooterDataH)) {
			panelFooterHeight = panelFooterDataH;
		}
		panelFooterHeight = utils.isEmpty(panelFooterHeight)?0:panelFooterHeight;
		h = h - panelFooterHeight;
		var $panelSearch = $parentWrap.find(".panel-search");
		if(utils.isExist($panelSearch)) {
			var searchH = $panelSearch.outerHeight(true);
			searchH = Math.ceil(searchH);
			var dataSearchH = $panelSearch.data("height");
			searchH = (dataSearchH>searchH)?dataSearchH:searchH;
			searchH = utils.isEmpty(searchH)?0:searchH;
			h = h - searchH;
		}
		$tableWrap.height(h - 5);
		$tableWrap.css({"overflow":"auto"});
		if($tableWrap.hasClass("table-body-scroll")) {
			_separateTableHeaderAndBody($tableWrap, $panelFooter, h, isResize);
		}
	}
	
	/**
	 * 表头和表内容分离，即thead头作为一个表格，tbody作为一个表格
	 * @param $tableWrap
	 * @param $panelFooter
	 * @param h
	 * @param isResize
	 */
	function _separateTableHeaderAndBody($tableWrap, $panelFooter, h, isResize) {
		var $tableTheader = null;
		//调整表格；使表格头和表格内容分离
		var $table = $tableWrap.find("table");
		if(isResize) {
			$tableTheader = $tableWrap.prev().find(".table-theader");
			__autoTableWidth($tableWrap, $tableTheader, $table);
			__autoTableHeight($tableWrap, $tableTheader, $panelFooter, h);
			return;
		}
		var $theadTr = $table.find("thead").clone(true);
		if(!utils.isEmpty($theadTr)) {
			var dataHeight = $table.find("thead").find("tr").data("height");
			if(utils.isNotEmpty(dataHeight)) {
				dataHeight = "data-height='"+dataHeight+"'";
			} else {
				dataHeight = "";
			}
			$tableWrap.before("<div class='table-theader-bg "+$table.find("thead").find("tr").attr("class")+"'><div class='table-theader' "+dataHeight+"><table class='"+$table.attr("class")+"'></table></div></div>");
			$table.find("thead").remove();
			$tableTheader = $tableWrap.prev().find(".table-theader");
			$tableTheader.find("table").append($theadTr);
			__autoTableHeight($tableWrap, $tableTheader, $panelFooter, h);	
		}
		$tableWrap.css({"overflow":"auto"});
		__autoTableWidth($tableWrap, $tableTheader, $table);
		
		/**
		 * 自动计算表格高度
		 * @param $tableWrap
		 * @param $tableTheader
		 * @param $panelFooter
		 */
		function __autoTableHeight($tableWrap, $tableTheader, $panelFooter, h) {
			//$tableTheader = $tableWrap.prev().find(".table-theader");
			var panelHeadingHeight = Math.ceil($tableTheader.parent().outerHeight(true));
			var panelHeadingDataH = $tableTheader.data("height");
			if(panelHeadingHeight == 0 && utils.isNotEmpty(panelHeadingDataH)) {
				panelHeadingHeight = panelHeadingDataH;
			}
			panelHeadingHeight = utils.isEmpty(panelHeadingHeight)?0:panelHeadingHeight;
			h = h - panelHeadingHeight;
			$tableWrap.height(h);
		}
		
		/**
		 * 自动计算表格宽度
		 * @param $tableWrap
		 * @param $tableTheader
		 * @param $table
		 */
		function __autoTableWidth($tableWrap, $tableTheader, $table) {
			/*var panelW =  $tableWrap.parent().width();
			var tableWidth = panelW - utils.getScrollWidth();			
			if(tableWidth > 0) {
				$tableTheader.width(tableWidth);
				$tableWrap.width(tableWidth);
			}
			if(!__autoScrollTableWidth($tableWrap, panelW)) {
				setTimeout(function() {
					__autoScrollTableWidth($tableWrap, panelW);
				}, 10);
			}*/
		    setTimeout(function() {
                var $panel = $tableWrap.parent();
                var panelW = $panel.width();
                var scrollWidth = utils.getScrollWidth();
                if(scrollWidth < 20) {
                    scrollWidth = 20;
                }
                var tableWidth = panelW - scrollWidth;
                if(tableWidth > 0) {
                    $tableTheader.width(tableWidth);
                    $tableWrap.width(tableWidth);
                }
                if(!__autoScrollTableWidth($tableWrap, panelW)) {
                    setTimeout(function() {
                        __autoScrollTableWidth($tableWrap, panelW);
                    }, 10);
                }
            }, 10);
		}
		
		/**
		 * 自动设置有滚动条的表格宽度
		 * @param $tableWrap
		 * @return {Boolean} 
		 */
		function __autoScrollTableWidth($tableWrap, panelW) {
			var w = $tableWrap.width();
			var tableW = $tableWrap.find("table").width();
			if(w - tableW > 2) {
			    var tw = w + (w - tableW - 3);
				$tableWrap.width(tw);
				return true;
			}
			return false;
		}
	}
}

/**
 * 监听限制高度标识
 * @param $elementWrap
 * @param isResize
 * 标识
 * class="cnoj-auto-limit-height"
 * 该标识会根据浏览器高度，自适应被该标识标记的DIV层
 */
function limitHeightListener($elementWrap, isResize) {
	isResize = utils.isEmpty(isResize) ? false : (isResize == true);
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-auto-limit-height").each(function(){
			_handler($(this), isResize);
		});
	} else {
		$elementWrap.find(".cnoj-auto-limit-height").each(function(){
			var $this = $(this);
			//判断是否在bootstrap的tabs隐藏panel中；如果在则不处理
			var $tabpanel = $this.parents(".tab-pane:eq(0)");
			if($tabpanel.length == 0 || $tabpanel.css("display") !='none') {
				_handler($this, isResize);
			}
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 * @param isResize
	 */
	function _handler($element, isResize) {
		if(isResize || !$element.hasClass("cnoj-auto-limit-height-listener")) {
			$element.addClass("cnoj-auto-limit-height-listener");
			var h = 0;
			var mainTop = 0;
			if(utils.isIframe) {
				h = $(window).height();
			} else {
				h = getMainHeight();
				mainTop = getMainTop();
			}
			var subtractHeight = $element.data("subtract-height");
			subtractHeight = utils.isEmpty(subtractHeight)?0:subtractHeight;
			var top = $element.offset().top;
			h = h - (top - mainTop) - subtractHeight - 8; 
			$element.height(h);
			$element.css({"overflow":"auto"});
		}
	}
}

/**
 * 监听文件上传标识
 * @param $elementWrap
 * 标识
 * class="cnoj-upload"
 * 参数
 *   必须
 *     data-uri 上传到服务器上的action路径,如:data-uri="uploadAttr/uploadAttr"
 *   可选
 *     data-form-data 表单参数;填写表单ID；如:data-form-data="#form-id"
 *     data-accept-file-types 支持上传的文件类型;多个类型之间用英文逗号","分开;如:data-accept-file-types="gif,png,jpg"
 *     data-max-file-size 支持上传的文件最大大小;单位为:byte;如:data-max-file-size="1024000000000"
 *     data-pop-width 上传文件时弹出窗口的宽度
 *     data-pop-height 上传文件时弹出窗口的高度
 *     data-progressbar 是否显示上传进度条;1--显示；0--不显示
 *     data-close-after 文件上传结束后，关闭窗口时执行的回调函数;返回一个数组
 *     
 */
function uploadFileListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-upload").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-upload").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-upload-listener")) {
			$element.addClass("cnoj-upload-listener");
			
			var uri = $element.data("uri");
			var formData = $element.data("form-data");
			var acceptFileTypes = $element.data("accept-file-types");
			var maxFileSize = $element.data("max-file-size");
			var popWidth = $element.data("pop-width");
			var popHeight = $element.data("pop-height");
			var progressBar = $element.data("progressbar");
			var closeAfterFun = $element.data("close-after");
			if(utils.isNotEmpty(uri)) {
				formData = utils.isEmpty(formData)?null:$(formData).serializeArray();
				popWidth = utils.isEmpty(popWidth)?450:popWidth;
				popHeight = utils.isEmpty(popHeight)?300:popHeight;
				progressBar = (utils.isEmpty(progressBar) || progressBar=='0')?false:true;
				closeAfterFun = utils.isEmpty(closeAfterFun)?null:eval(closeAfterFun);
				var param = {
						uri:uri,
						formData:formData,
						popWidth:popWidth,
						popHeight:popHeight,
						progressBar:progressBar,
						closeAfterFun:closeAfterFun
				};
				if(utils.isNotEmpty(acceptFileTypes)) {
					acceptFileTypes = acceptFileTypes.replace(/\,/g,'|');
					param = $.extend(param,{acceptFileTypes:'/'+acceptFileTypes+'$/i'});
				}
				if(utils.isNotEmpty(maxFileSize) && utils.regexInteger(maxFileSize)) {
					param = $.extend(param,{maxFileSize:maxFileSize});
				}
				$element.jqueryFileUpload(param);
			}
		}
	}
}

/**
 * 监听日期时间标识
 * @param $elementWrap
 * 标识
 * class="cnoj-datetime"(yyyy-mm-dd hh:ii:ss)或
 * class="cnoj-date"(yyyy-mm-dd) 或
 * class="cnoj-time" (hh:ii:ss)
 * 可选参数
 * data-start-date="" 设置时间范围--开始时间
 * data-end-date=""   设置时间范围--结束时间
 * data-date-format="" 日期格式；如：yyyy-mm-dd 或yyyy年mm月dd日
 */
function inputDateListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-datetime,.cnoj-date,.cnoj-time").each(function(){
			return _handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-datetime,.cnoj-date,.cnoj-time").each(function(){
			return _handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if($element.hasClass("cnoj-datetime-listener") || 
				$element.hasClass("cnoj-date-listener") || 
				$element.hasClass("cnoj-time-listener") || 
				$element.prop("readonly") || $element.prop("disabled") || $element.hasClass("hide")) {
			return true;
		}
		if($element.hasClass("cnoj-datetime")) {
			$element.addClass("cnoj-datetime-listener");
		} else if($element.hasClass("cnoj-date")) {
			$element.addClass("cnoj-date-listener");
		} else if($element.hasClass("cnoj-time")) {
			$element.addClass("cnoj-time-listener");
		}
		var $formGroup = $element.parents(".form-group:eq(0)");
		if($formGroup.hasClass("has-feedback")) {
			return true;
		}
		_addFeedback($formGroup, $element);
		_initDatetimePicker($element);
		return true;
	}
	
	/**
	 * 添加日期图标
	 * @param $formGroup
	 * @param $element
	 */
	function _addFeedback($formGroup,$element) {
		if($formGroup.length > 0) {
			$formGroup.addClass("has-feedback");
			$element.after("<span class='glyphicon glyphicon-calendar form-control-feedback hidden-print'></span>");
		} else {
			var $parent = $element.parent();
			var count = 0;
			$parent.find("input").each(function(){
				count++;
			});
			var starRequire = '';
			if(count>1) {
				if(!$parent.hasClass("form-group")) {
					var $next = $element.next();
					if($next.hasClass("star-require")) {
						starRequire = $next.clone();
						$next.remove();
					}
					$element.wrap("<div class='text-inline-block' style='width:"+($element.outerWidth(true)+10)+"px'></div>");
					$element.parent().append(starRequire);
				}
				$element.parent().addClass('has-feedback');
				$element.after("<span class='glyphicon glyphicon-calendar inline-icon form-control-feedback hidden-print' style='right:3px;line-height: 30px;'></span>");
			} else {
				var $next = $element.next();
				if($next.hasClass("star-require")) {
					starRequire = $next.clone();
					$next.remove();
				}
				var parentW = $parent.width();
				var thisW = $element.outerWidth();
				var w = thisW;//((thisW+30)<parentW)?(thisW+10):parentW;
				//$element.wrap("<div class='text-inline-block' style='width:"+w+"px'></div>");
				$element.wrap("<div class='text-inline'></div>");
				$element.parent().append(starRequire);
				$element.parent().addClass('has-feedback');
				var inputH = $element.outerHeight();
				var style = "";
				//判断是否在iframe中；如果相等，不是iframe；否则是iframe
				/*var notIframe = (top.location == location);
				if(notIframe) {
					style = style+"right:5%;";
				}*/
				$element.after("<span class='glyphicon glyphicon-calendar inline-icon form-control-feedback hidden-print' style='"+style+"'></span>");
			}
			var $icon = $element.find(".inline-icon");
		 }
	}
	
	/**
	 * 初始化日期控件
	 * @param $element
	 */
	function _initDatetimePicker($element) {
		var setting = {};
		if($element.hasClass('cnoj-datetime')) {
			setting = {format: 'yyyy-mm-dd hh:ii:ss',startView:2};
		} else if($element.hasClass('cnoj-date')) {
			setting = {format: 'yyyy-mm-dd',minView: 2};
		} else if($element.hasClass('cnoj-time')) {
			var date = new Date();
			var month = date.getMonth()+1;
			month = month<10?('0'+month):month;
			var day = date.getDate();
			day = day<10?('0'+day):day;
			var startDate = date.getFullYear()+"-"+month+"-"+day;
			setting = {format: 'hh:ii:ss',startDate:startDate,startView: 1,minView: 0,maxView:1};
		}
		var startDate = $element.data("start-date");
		var endDate = $element.data("end-date");
		if(utils.isNotEmpty(startDate)) {
			setting = $.extend(setting,{startDate:startDate});
		}
		if(utils.isNotEmpty(endDate)) {
			setting = $.extend(setting,{endDate:endDate});
		}
		//获取日期格式
		var dateFormat = $element.data("date-format");
		if(utils.isNotEmpty(dateFormat)) {
			setting = $.extend(setting,{format:dateFormat});
		}
		var pickerHeight = 230;
		var offset = $element.offset();
		var windowHeight = $(window).height();
		if((offset.top+pickerHeight)>windowHeight) {
			setting = $.extend(true, setting,{pickerPosition:'top-right'});
		}
		setting = $.extend(true, setting,{language:'zh-CN',autoclose:true});
		$element.datetimepicker(setting).on('show',function(){
			$element.prop("readonly",true);
		}).on('hide',function(){
			$element.prop("readonly",false);
		});
		if($element.hasClass("cnoj-date-defvalue") && utils.isEmpty($element.val())) {
			$element.val($element.datetimepicker('getFormattedDate'));
		}
	}
}

/**
 * 下拉列表监听
 * @param $elementWrap 
 * 标识
 *   class="cnoj-select"
 *  参数
 *    必须
 *       data-uri 提供数据uri(数据来源)；数据格式为json数组格式;第一个字段为ID;第二个字段为名称
 *    可选
 *       data-default-value  默认值
 *       data-edit-enable 是否可编辑；默认为：可以编辑
 *       data-is-null 是否有空值（如：请选择）默认为:no(没有) 可选的值为:"yes"或"no"
 */
function selectListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-select").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-select").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-select-listener")) {
			$element.addClass("cnoj-select-listener");
			var uri = $element.data("uri");
			var isNull = $element.data("is-null");
			isNull = (isNull == 'yes'?true:false);
			var defaultValue = $element.data("default-value");
			if(utils.isEmpty(defaultValue)) {
				defaultValue = $element.attr("value");
			}
			var editEnable = $element.data("edit-enable");
			var isEditEnable = (utils.isEmpty(editEnable) || editEnable == 1) ? true : false;
			if(isNull) {
				$element.append("<option value=''>请选择</option>");
			}
			if(!utils.isEmpty(uri)) {
				utils.selectItem($element, uri, defaultValue,function(){
					if(!isEditEnable) {
						$element.prop("disabled",true);
					}
				});
			}
		}
	}
}

/**
 * 复选框监听
 * @param $elementWrap
 * 标识
 *   class="cnoj-checkbox"
 *  参数
 *    必须
 *       data-uri 提供数据uri(数据来源)；数据格式为json数组格式;第一个字段为ID;第二个字段为名称
 *       data-name 复选框名称
 *    可选
 *       data-default-value  默认值
 *       data-is-horizontal 是否横排;默认为：yes；可设置的值为:"yes"或"no" <br />
 *       data-require 是否必填
 */
function inputCheckboxListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-checkbox").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-checkbox").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-checkbox-listener")) {
			$element.addClass("cnoj-checkbox-listener");
			var uri = $element.data("uri");
			var defaultValue = $element.data("default-value");
			var isH = $element.data("is-horizontal");
			isH = (isH == 'no'?false:true);
			var require = $element.data("require");
			var name = $element.data("name");
			name = utils.handleNull(name);
			var editEnable = $element.data("edit-enable");
			var isEditEnable = (utils.isEmpty(editEnable) || editEnable == 1) ? true : false;
			if(!utils.isEmpty(uri)) {
				utils.checkboxItem($element, uri, defaultValue, name, isH, require, function(){
					if(!isEditEnable) {
						$element.find(":checkbox").prop("disabled",true);
					}
				});
			}
		}
	}
}

/**
 * 单选框监听
 * @param $elementWrap
 * 标识
 *   class="cnoj-radio"
 *  参数
 *    必须
 *       data-uri 提供数据uri(数据来源)；数据格式为json数组格式;第一个字段为ID;第二个字段为名称
 *       data-name 单选框名称
 *    可选
 *       data-is-horizontal 是否横排;默认为：yes；可设置的值为:"yes"或"no" <br />
 *       data-default-value  默认值
 *       data-require 是否必填
 */
function inputRadioListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-radio").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-radio").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-radio-listener")) {
			$element.addClass("cnoj-radio-listener");
			var uri = $element.data("uri");
			var defaultValue = $element.data("default-value");
			var isH = $element.data("is-horizontal");
			isH = (isH == 'no'?false:true);
			var require = $element.data("require");
			var name = $element.data("name");
			name = utils.handleNull(name);
			var editEnable = $element.data("edit-enable");
			var isEditEnable = (utils.isEmpty(editEnable) || editEnable == 1) ? true : false;
			if(!utils.isEmpty(uri)) {
				utils.radioItem($element, uri, defaultValue, name, isH, require, function(){
					if(!isEditEnable) {
						$element.find(":radio").prop("disabled",true);
					}
				});
			}
		}
	}
}


/**
 * 级联下拉列表监听
 * @param $elementWrap
 * 标识
 *   class="cnoj-cascade-select"
 *  参数
 *    必须
 *       data-uri 提供数据uri(数据来源)；数据格式为json数组格式;第一个字段为ID;第二个字段为名称
 *       data-cascade-id 级联的ID值
 *    可选
 *       data-default-value  默认值
 *       data-param-name 参数名称
 *       data-change-id 父级的数据改变时，要获取值的ID；如果data-cascade-id和data-change-id为同一个ID时，data-change-id属性可以不设置
 */
function cascadeSelectListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-cascade-select").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-cascade-select").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-cascade-select-listener")) {
			$element.addClass("cnoj-cascade-select-listener");
			var uri = $element.data("uri");
			var defaultValue = $element.data("default-value");
			var cascadeId = $element.data("cascade-id");
			var paramName = $element.data("param-name");
			var changeId = $element.data("change-id");
			changeId = utils.isEmpty(changeId)?null:changeId;
			if(utils.isNotEmpty(uri) && utils.isNotEmpty(cascadeId)) {
				utils.selectCascadeItem($element,cascadeId, uri,paramName, defaultValue,changeId);
			}
		}
	}
}


/**
 * 输入表单实现下拉列表 <br />
 * @param $elementWrap
 * 标识 <br />
 *    class="cnoj-input-select" <br />
 *  参数  <br />
 *     必须 <br />
 *       data-uri 提供数据uri(数据来源)；数据格式为json数组格式;第一个字段为ID;第二个字段为名称 <br />
 *     可选 <br />
 *       data-is-show-all 是否显示"全部链接";默认为yes,可设置的值为:"yes"或"no" <br />
 *       data-param-name 搜索的参数名 <br />
 *       data-is-show-none 是否显示“无”;默认为：“no”；可设置的值为:"yes"或"no" <br />
 *       data-default-value 默认值
 */
function inputSelectListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-input-select").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-input-select").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if($element.prop("disabled") || $element.hasClass("hide")) {
			return;
		}
		if(!$element.hasClass("cnoj-input-select-listener")) {
			$element.addClass("cnoj-input-select-listener");
			var uri = $element.data("uri");
			if(utils.isNotEmpty(uri)) {
				var paramName = $element.data("param-name");
				var defaultValue = $element.data("default-value");
				if(utils.isEmpty(defaultValue))
					defaultValue = $element.val();
				var isShowAll = $element.data("is-show-all");
				isShowAll = (isShowAll == 'yes'?true:false);
				var isShowNone = $element.data("is-show-none");
				isShowNone = (isShowNone == 'yes'?true:false);
				var setting = {uri:uri,isShow:false,isShowAll:isShowAll,isShowNone:isShowNone};
				if(utils.isNotEmpty(paramName)) 
					setting = $.extend(setting,{paramName:paramName});
				if(utils.isNotEmpty(defaultValue)) 
					setting = $.extend(setting,{defaultValue:defaultValue});
				$element.inputSelect(setting);
		   }
		   $element.on("click focus",function(event){
			   var $this = $(this);
			   $this.prop("readonly",true);
				if(utils.isNotEmpty(uri)) {
					var paramName = $this.data("param-name");
					var defaultValue = $this.data("default-value");
					var isShowAll = $this.data("is-show-all");
					isShowAll = (isShowAll == 'yes'?true:false);
					var setting = {uri:uri,isShow:true};
					if(utils.isNotEmpty(paramName)) 
						setting = $.extend(setting,{paramName:paramName});
					if(utils.isNotEmpty(defaultValue)) 
						setting = $.extend(setting,{defaultValue:defaultValue});
					$this.inputSelect(setting);
				}
				event.stopPropagation();
			});
		}//if
	}
}

/**
 * 输入表单实现下拉列表 <br />
 * @param $elementWrap
 * 标识 <br />
 *    class="cnoj-input-select-relate" <br />
 *  参数  <br />
 *     必须 <br />
 *       data-uri 提供数据uri(数据来源)；数据格式为json数组格式;第一个字段为ID;第二个字段为名称 <br />
 *     可选 <br />
 *       data-is-show-all 是否显示"全部链接";默认为yes,可设置的值为:"yes"或"no" <br />
 *       data-param-name 搜索的参数名 <br />
 *       data-is-show-none 是否显示“无”;默认为：“no”；可设置的值为:"yes"或"no" <br />
 *       data-default-value 默认值
 */
function inputSelectRelateListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-input-select-relate").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-input-select-relate").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if($element.prop("disabled")) {
			return;
		}
		if(!$element.hasClass("cnoj-input-select-relate-listener")) {
			$element.addClass("cnoj-input-select-relate-listener");
			var uri = $element.data("uri");
			if(utils.isNotEmpty(uri)) {
				var paramName = $element.data("param-name");
				var defaultValue = $element.data("default-value");
				if(utils.isEmpty(defaultValue))
					defaultValue = $element.val();
				var isShowAll = $element.data("is-show-all");
				isShowAll = (isShowAll == 'yes'?true:false);
				var isShowNone = $element.data("is-show-none");
				isShowNone = (isShowNone == 'yes'?true:false);
				var setting = {uri:uri,isShow:false,isShowAll:isShowAll,isShowNone:isShowNone,
						selectCallback:function(obj,datas){
							selectRelate(datas, $element,2);
				}};
				if(utils.isNotEmpty(paramName)) 
					setting = $.extend(setting,{paramName:paramName});
				if(utils.isNotEmpty(defaultValue)) 
					setting = $.extend(setting,{defaultValue:defaultValue});
				$element.inputSelect(setting);
		    }
			$element.on("click focus",function(event){
				$(this).prop("readonly",true);
				$(this).inputSelect({isShow:true});
			   event.stopPropagation();
			});
		}
	}
}

/**
 * 输入框自动完成监听 <br />
 * @param $elementWrap
 * 标识 <br /> 
 *   class="cnoj-auto-complete" <br />
 *   参数  <br />
 *     必须 <br />
 *       data-uri 提供数据uri(数据来源)；数据格式为json数组格式;第一个字段为ID;第二个字段为名称 <br />
 *     或data-json-data 提供数据uri(数据来源)；数据格式为json数组格式;第一个字段为ID;第二个字段为名称;第三个为显示内容; <br />
 *       “data-uri”属性的优先级高于“data-json-data”属性; <br />
 *     可选 <br />
 *       data-multiple 是否输入多个输入值;1--是；0--否，默认为：0 <br />
 *       data-multiple-separator 多个值时，多个值之间的分隔符；默认为英文分号:";"；<br />
 *       “data-multiple”与“data-multiple-separator”成对出现 
 */
function autoCompleteListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-auto-complete").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-auto-complete").each(function(){
			_handler($(this));
		});
	}

	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if($element.prop("disabled") || $element.hasClass("hide")) {
			return;
		}
		if(!$element.hasClass("cnoj-auto-complete-listener")) {
			$element.addClass("cnoj-auto-complete-listener");
			var uri = $element.data("uri");
			var jsonData = $element.data("json-data");
			var multiple = $element.data("multiple");
			var multipleSeparator = $element.data("multiple-separator");
			var options = {};
			var is = false;
			if(utils.isNotEmpty(uri)) {
				options=$.extend(options,{uri:uri});
				is = true;
			}
			if(!is && utils.isNotEmpty(jsonData)) {
				options=$.extend(options,{jsonData:jsonData});
				is = true;
			}
			if(is && utils.isNotEmpty(multiple) && (multiple=='1' || multiple=='0')) 
				options=$.extend(options,{multiple:(multiple==1?true:false)});
			if(is && utils.isNotEmpty(multiple) && multiple=='1' && utils.isNotEmpty(multipleSeparator)) 
				options=$.extend(options,{multipleSeparator:multipleSeparator});
			if(is)
				$element.autoComplete(options);
		}
	}
}

/**
 * 输入框自动完成关联监听 <br />
 * @param $elementWrap
 * 标识 <br /> 
 *   class="cnoj-auto-complete-relate" <br />
 *   参数  <br />
 *     必须 <br />
 *       data-uri 提供数据uri(数据来源)；数据格式为json数组格式;第一个字段为ID;第二个字段为名称 <br />
 *     或data-json-data 提供数据uri(数据来源)；数据格式为json数组格式;第一个字段为ID;第二个字段为名称;第三个为显示内容; <br />
 *       “data-uri”属性的优先级高于“data-json-data”属性; <br />
 */
function autoCompleteRelateListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-auto-complete-relate").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-auto-complete-relate").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if($element.prop("disabled") || $element.hasClass("hide")) {
			return;
		}
		if(!$element.hasClass("cnoj-auto-complete-listener")) {
			$element.addClass("cnoj-auto-complete-listener");
			var uri = $element.data("uri");
			var jsonData = $element.data("json-data");
			var options = {
					selectCallback:function(event,data){
						 if(utils.isNotEmpty(data)) {
							 var otherValue = data.item.otherValue;
							 selectRelate(otherValue, $element, 0);
						 }
					 }
			};
			var is = false;
			if(utils.isNotEmpty(uri)) {
				options=$.extend(options,{uri:uri});
				is = true;
			}
			if(!is && utils.isNotEmpty(jsonData)) {
				options=$.extend(options,{jsonData:jsonData});
				is = true;
			}
			if(is)
				$element.autoComplete(options);
		}
	}
}

/**
 * 微调数据 数据类型为number <br />
 * @param $elementWrap
 * 标识 <br />
 *   class="cnoj-num-spinner" <br />
 *   参数  <br />
 *     可选 <br />
 *       data-min 最小值 <br />
 *       data-max 最大值 <br />
 *       data-step 每一次变化数值
 */
function spinnerNumListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-num-spinner").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-num-spinner").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if($element.prop("disabled") || $element.hasClass("hide")) {
			return;
		}
		if(!$element.hasClass("cnoj-num-spinner-listener")) {
			$element.addClass("cnoj-num-spinner-listener");
			var min = $element.data("min");
			min = utils.isEmpty(min)?0:(utils.regexNum(min)?min:0);
			var max = $element.data("max");
			max = utils.isEmpty(max)?0:(utils.regexNum(max)?max:0);
			var step = $element.data("step");
			step = utils.isEmpty(step)?1:(utils.regexNum(step)?step:1);
			var options = {min:min,step:step,numberFormat:"n",change: function(event, ui ) {
				var value = $(event.target).val();
				if(!utils.regexNum(value) || value<min || (max>0 && value>max)) {
					utils.showMsg("输入的数据格式错误！");
					$element.focus();
				}
			}};
			if(max>0) {
				options = $.extend(options,{max:max});
			}
			$element.spinner(options);
		}
	}
}


/**
 * URL载入标记监听 <br />
 * @param $elementWrap
 * 标识 <br />
 *   class="cnoj-load-url" <br />
 * 参数  <br />
 *   必须 <br />
 *    data-uri 提供数据uri
 */
function loadUrlListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-load-url").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-load-url").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-load-url-listener")) {
			$element.addClass("cnoj-load-url-listener");
			var uri = $element.data("uri");
			if(utils.isNotEmpty(uri)) {
				$element.append('<div class="cnoj-loading"><i class="fa fa-spinner fa-spin fa-lg"></i> 正在加载，请稍候...</div>');
				$element.load(uri,function() {
					$element.find(".cnoj-loading").remove();
					initEvent($element);
					//loadUrlListener($element);
				});
			}
		}
	}
}


/**
 * 处理回车提交表单功能
 * @param $elementWrap
 * 标识
 *   class="cnoj-entry-submit"
 */
function handleEntrySubmit($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-entry-submit input").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-entry-submit input").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-entry-submit-listener")) {
			$element.addClass("cnoj-entry-submit-listener");
			$element.keydown(function(e) {
				if(e.keyCode == 13) {
					var $form = $(this).parents("form:eq(0)");
					var $btn = $form.find(".cnoj-search-submit,.cnoj-data-submit,.login-btn");
					if(!utils.isEmpty($btn.attr("class"))) {
						$btn.trigger("click");
						return false;
					}
				}
			});
		}
	}
}

/**
 * 数据提交监听
 * @param $elementWrap
 * class="cnoj-post-data" 该标识主要是用来标post提交数据，点击标该class的按钮或链接时触发
 *   参数:必须 data-uri 分页uri
 *            data-refresh-uri刷新URI
 *       可选 data-target 显示地方(一般为一个div层)
 *           data-param 参数
 *           data-del-alert 删除后提醒信息
 */
function submitDataListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-post-data").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-post-data").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-post-data-listener")) {
			$element.addClass("cnoj-post-data-listener");
			$element.click(function(event){
				_clickElement(event, $(this));
				return false;
			});
		}
	}
	
	/**
	 * 处理单击元素
	 * @param event
	 * @param $this
	 */
	function _clickElement(event,$this) {
		var uri = $this.data("uri");
		var params = $this.data("param");
		params = utils.isEmpty(params)?"":params;
		var refreshUri = $this.data("refresh-uri");
		var target = $this.data("target");
		var delAlertMsg = $this.data("del-alert");
		if(utils.isNotEmpty(uri)) {
			if(utils.isNotEmpty(delAlertMsg)) {
				BootstrapDialogUtil.confirmDialog(delAlertMsg,function(){
					cnoj.postData(uri,params,target,refreshUri);
				});
				return;
			}
			cnoj.postData(uri,params,target,refreshUri);
		}
	}
}

/**
 * 提交表单监听（含有附件时使用）
 * @param $elementWrap
 * 标识
 * class="cnoj-form-submit"
 * 该标识是提交表单触发的事件
 * 除了action参数在form表单里面填写之外，其他的参数都在该标识元素中填写
 * 参数
 *   必须
 *      action 该参数就是form表单的action属性,在提交的form的action里面填写该值,指定提交的路径(action)
 *   可选
 *      data-target-form 该参数，指定提交哪个form表单的ID（有多个form表单时）;如:target-form="#add-form";
 *      data-fun 该参数是一个回调函数，提交成功后执行的方法;
 *      data-refresh-uri 该参数指定数据指定成功后，刷新的uri;
 *      data-show-target 该参数为form表单的data-show-target属性；提交数据请求之后，返回的内容显示到哪个里面，默认为"#main-content"
 *      与data-refresh-uri成对出现.
 *      
 */
function submitFormListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-form-submit").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-form-submit").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-form-submit-listener")) {
			$element.addClass("cnoj-form-submit-listener");
			$element.click(function(event){
				_clickElement(event, $(this));
				event.stopPropagation();
				return false;
			});
		}
	}
	
	/**
	 * 处理单击元素
	 * @param event
	 * @param $this
	 */
	function _clickElement(event, $this) {
		var target = $this.data("target-form");
		var fun = $this.data("fun");
		var $form = null;
		if(utils.isNotEmpty(target)) {
			if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
				$form = $(target);
			} else {
				$form = $elementWrap.find(target);
			}
			if($form.length == 0) {
				$form = null;
			}
		} else {
			 $form = $this.parents("form:eq(0)");
		}
		if($form.validateForm()) {
			var param = $form.serialize();
		    var uri = $form.attr("action");
		    cnoj.submitFormDialogData(uri,param,fun,$this,$form);
		}
	}
}

/**
 * 跳转到指定的页面<br />
 * @param $elementWrap
 * 标识 <br />
 *   class="cnoj-goto-page" <br />
 *   参数  <br />
 *     必须 <br />
 *        data-uri 跳转到的页面 <br />
 *     可选 <br />
 *       data-target 跳转内容显示位置 <br />
 *       data-search-panel-tag 搜索面板标识
 */
function gotoPageListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-goto-page").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-goto-page").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-goto-page-listener")) {
			$element.addClass("cnoj-goto-page-listener");
			$element.click(function(event){
				_clickElement(event, $(this));
				event.stopPropagation();
				return false;
			});
			//回车跳转到指定页面
			$element.parent().find(".goto-page-input").keydown(function(event){
				if(event.keyCode == 13) {
					_clickElement(event, $(this).next());
				}
			});
		}
	}
	
	/**
	 * 处理单击元素
	 * @param event
	 * @param $this
	 */
	function _clickElement(event, $this) {
		var uri = $this.data("uri");
		var page = $this.prev().val();
		if (utils.isEmpty(uri) || utils.isEmpty(page) || !utils.regexInteger(page)) {
			return;
		}
		var $btnPage = $this.parents(".btn-page:eq(0)");
		var $pageInfo = $btnPage.next().find("span:first");
		var pageInfoText = $pageInfo.text();
		if(utils.isNotEmpty(pageInfoText)) {
			var reg = new RegExp("(\\d+) - (\\d+)","gmi")
			pageInfoText = pageInfoText.replace(reg, "$2");
		} else {
			pageInfoText = 1;
		}
		var totalPage = parseInt(pageInfoText);
		page = (page > totalPage)?totalPage:page;
		var target = $this.data("target");
		uri = uri+page;
			
		//获取搜索参数
		var searchPanelTag = $this.data("search-panel-tag");
		var $searchPanel = null;
		if(utils.isEmpty(searchPanelTag)) {
			$searchPanel = $this.parents(".panel:eq(0)").find(">.panel-search");
		} else {
			$searchPanel = $(searchPanelTag);
		}
		if(utils.isExist($searchPanel)) {
			var $form = $searchPanel.find("form");
			if(utils.isExist($form)) {
				uri = uri+"&"+$form.serialize();
			}
		}
		//获取页面显示数量;即每页显示数
    	var $pageSize = $this.parents(".panel-footer-page:eq(0)").find(".cnoj-change-pagesize");
    	if($pageSize.length>0) {
    		uri +="&pageSize="+$pageSize.val();
    	}
		if(!utils.isEmpty(target))
			loadUri(target,uri,true);
		else 
			loadActivePanel(uri);
	}
}

/**
 * 改变显示页面数量<br />
 * @param $elementWrap
 * 标识 <br />
 *   class="cnoj-change-pagesize" <br />
 *   参数  <br />
 *     必须 <br />
 *        data-uri 刷新的页面 <br />
 *     可选 <br />
 *       data-target 刷新内容显示位置 <br />
 *       data-search-panel-tag 搜索面板标识
 */
function changePageSizeListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-change-pagesize").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-change-pagesize").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-change-pagesize-listener")) {
			$element.addClass("cnoj-change-pagesize-listener");
			$element.change(function(event){
				_clickElement(event, $(this));
			});
		}
	}
	
	/**
	 * 处理单击元素
	 * @param event
	 * @param $this
	 */
	function _clickElement(event, $this) {
		var uri = $this.data("uri");
		if (utils.isEmpty(uri)) {
			return;
		}
		var target = $this.data("target");
		if(uri.indexOf("?")>-1) {
			uri += "&pageSize="+$this.val();
		} else {
			uri += "?pageSize="+$this.val();
		}
		//获取搜索参数
		var searchPanelTag = $this.data("search-panel-tag");
		var $searchPanel = null;
		if(utils.isEmpty(searchPanelTag)) {
			$searchPanel = $this.parents(".panel:eq(0)").find(">.panel-search");
		} else {
			$searchPanel = $(searchPanelTag);
		}
		if(utils.isExist($searchPanel)) {
			var $form = $searchPanel.find("form");
			if(utils.isExist($form)) {
				uri = uri+"&"+$form.serialize();
			}
		}
		if(!utils.isEmpty(target))
			loadUri(target,uri,true);
		else 
			loadActivePanel(uri);
	}
}

/**
 * 打印标识监听<br />
 * @param $elementWrap
 * 标识 <br />
 *   class="cnoj-print" <br />
 *   参数  <br />
 *     必须 <br />
 *        data-target 打印目标对象 <br />
 */
function printListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-print").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".cnoj-print").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("cnoj-print-listener")) {
			$element.addClass("cnoj-print-listener");
			$element.click(function(event){
				_clickElement(event, $(this));
				event.stopPropagation();
				return false;
			});
		}
	}
	
	/**
	 * 处理单击元素
	 * @param event
	 * @param $this
	 */
	function _clickElement(event, $this) {
		var target = $this.data("target");
		if (utils.isNotEmpty(target)) {
			utils.handleFormPrintLabel(target, $elementWrap);
			var $target = null;
			if(utils.isNotEmpty($elementWrap) && !utils.isExist($elementWrap)) {
				$target = $elementWrap.find(target);
			} else {
				$target = $(target);
			}
			var options = {};
			if(utils.isIE()) {
				var popHt = $target.outerHeight(true);
		   		var popWd = $target.outerWidth(true);
		   		options = {
		   			mode:'popup',
		   			popHt: popHt,
		            popWd: popWd,
		            popClose: true,
		            popX: 10,
		            popY: 10
		   		};
			}
			$target.printArea(options);
		}
	}
}

/**
 * popover标识监听<br />
 * @param $elementWrap
 * 标识 <br />
 *   class="mix-popover" <br />
 *   参数  <br />
 *     必须 <br />
 *        data-uri 打印目标对象 <br />
 */
function popoverListener($elementWrap) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".mix-popover").each(function(){
			_handler($(this));
		});
	} else {
		$elementWrap.find(".mix-popover").each(function(){
			_handler($(this));
		});
	}
	
	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if(!$element.hasClass("mix-popover-listener")) {
			$element.addClass("mix-popover-listener");
			$element.click(function(event){
				_clickElement(event, $(this));
				return false;
			});
		}
	}
	
	/**
	 * 处理单击元素
	 * @param event
	 * @param $this
	 */
	function _clickElement(event, $this) {
		var id = $this.attr("id");
		var uri = $this.data("uri");
		var content = $this.data("content");
		if(utils.isNotEmpty(uri)) {
			$.ajax({type:"get",url:uri,async:false,success:function(data){
				content = data;
			}});
		} 
		if(utils.isNotEmpty(content)){
			$this.popover({
				placement:'auto',
				content:content,
				trigger:"manual",
				html:true
			});
			$this.popover('show');
		}
		$(document).click(function(event){
			if ($(event.target).closest('.popover').length === 0) {
				$this.popover('hide');
			}
		});
	}
}

/**
 * 富文本编辑器监听<br />
 * @param $elementWrap
 * 标识 <br />
 *   class="cnoj-richtext" <br />
 *   参数  <br />
 *     无
 */
function richtextListener($elementWrap) {
	if(typeof(UE) != 'undefined') {
		if(UEDITOR_CONFIG.UEDITOR_HOME_URL.indexOf("/plugins/ueditor/")<0) {
			UEDITOR_CONFIG.UEDITOR_HOME_URL = UEDITOR_CONFIG.UEDITOR_HOME_URL+"plugins/ueditor/";
		}
		if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
			$(".cnoj-richtext").each(function(){
				_handler($(this));
			});
		} else {
			$elementWrap.find(".cnoj-richtext").each(function(){
				_handler($(this));
			});
		}
	}
	
	/**
	 * 处理元素
	 * @param $element
	 */
	function _handler($element) {
		if($element.prop("disabled") || $element.hasClass("hide")) {
			return false;
		}
		if(!$element.hasClass("cnoj-richtext-listener")) {
			$element.addClass("cnoj-richtext-listener");
			var id = $element.attr("id");
			if(utils.isEmpty(id)) {
				return false;
			}
			UE.delEditor(id);
			UE.getEditor(id,{
				toolbars: [[
				            'fullscreen',
				            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'blockquote', '|', 'forecolor', 'insertorderedlist', 'insertunorderedlist', '|',
				            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
				            'paragraph', 'fontfamily', 'fontsize', '|', 'indent', '|',
				            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
				            'link', 'unlink', 'anchor', '|','pagebreak','|',
				            'horizontal', 'date', 'time', 'spechars', '|',
				            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts'
				        ]]
			});
		}
	}
}

/**
 * 列表面板监听<br />
 * @param $elementWrap
 * @param isResize 
 * 标识 <br />
 *   class="cnoj-list-panel" <br />
 *   参数  <br />
 *     无
 */
function listPanelListener($elementWrap, isResize) {
	if(utils.isEmpty($elementWrap) || !utils.isExist($elementWrap)) {
		$(".cnoj-list-panel").each(function(){
			_handler($(this), isResize);
		});
	} else {
		$elementWrap.find(".cnoj-list-panel").each(function(){
			_handler($(this), isResize);
		});
	}
	
	/**
	 * 处理元素
	 * @param $element
	 * @param isResize
	 */
	function _handler($element, isResize) {
		if(isResize) {
			_handleElement($element);
		} else {
			if(!$element.hasClass("cnoj-list-panel-listener")) {
				$element.addClass("cnoj-list-panel-listener");
				_handleElement($element);
			}
		}
	}
	
	/**
	 * 处理元素
	 * @param $this
	 */
	function _handleElement($this) {
		var $panelBody = $this.parents(".panel-body:eq(0)");
    	var $rowTitleWrap = $this.find(".row-title-wrap");
    	var $listColBody = $this.find(".list-col-body");
    	var $listBodyWrap = $this.find(".list-col-body-wrap");
    	var h = $panelBody.height() - $rowTitleWrap.outerHeight(true);
    	$listColBody.height(h);
    	var bwH = $listBodyWrap.height();
    	if(bwH>h) {
    		var scrollW = $rowTitleWrap.width() - $listBodyWrap.width();
    		var w = $rowTitleWrap.width() - scrollW;
    		$rowTitleWrap.find(".title-row").width(w);
    	}
	}
} 

/**
 * 初始化
 */
function initEvent($elementWrap) {
	formRequireListener($elementWrap);
	if($elementWrap instanceof jQuery) {
		$elementWrap.find(".cnoj-op-btn-list").btnListener();
	} else {
		$(".cnoj-op-btn-list").btnListener();
	}
	submitDataListener($elementWrap);
	submitFormListener($elementWrap);
	checkboxAllListener($elementWrap);
	checkboxListener($elementWrap);
	tableTreeListener($elementWrap);
	tableTreeSelectListener($elementWrap);
	tableSelectListener($elementWrap);
	
	tableAsyncTreeListener($elementWrap);
	tableAsyncTreeSelectListener($elementWrap);
	
	panelTreeListener($elementWrap);
	panelCheckTreeListener($elementWrap);
	panelOrgTreeListener($elementWrap);
	submitBtnListener($elementWrap);
	searchSubmitListener($elementWrap);
	limitHeightListener($elementWrap);
	tableWrapListener($elementWrap);
	
	hrefListener($elementWrap);
	gotoPageListener($elementWrap);
	uploadFileListener($elementWrap);
	selectListener($elementWrap);
	cascadeSelectListener($elementWrap);
	loadUrlListener($elementWrap);
	inputPluginEvent($elementWrap);
	printListener($elementWrap);
	popoverListener($elementWrap);
	handleEntrySubmit($elementWrap);
	changePageSizeListener($elementWrap);
	listPanelListener($elementWrap, false);
}

function inputPluginEvent($elementWrap) {
	inputTreeListener($elementWrap);
	inputOrgTreeListener($elementWrap);
	inputDateListener($elementWrap);
	inputSelectListener($elementWrap);
	inputSelectRelateListener($elementWrap);
	autoCompleteListener($elementWrap);
	autoCompleteRelateListener($elementWrap);
	inputCheckboxListener($elementWrap);
	inputRadioListener($elementWrap);
	spinnerNumListener($elementWrap);
}

/**
 * 
 * @param obj
 * @param op
 * @param flag
 */
function openProp(obj,op,flag) {
	if(utils.isEmpty(obj)) {
		return;
	}
	var $obj = null;
	if(obj instanceof jQuery) {
		$obj = obj;
	} else {
		$obj = $(obj);
	}
	var uri = $obj.data("uri");
	var title = $obj.data("title");
	var value = $obj.attr("selected-value");
	var busiName = $obj.data("busi");
	var paramName = $obj.data("param-name");
	var selectedType = $obj.data("selected-type");
	var beforeCheck = $obj.data("before-check");
	selectedType = utils.isEmpty(selectedType)?'none-selected':selectedType;
	var width = $obj.data("width");
	if(utils.isEmpty(width) || !utils.regexNum(width)) {
        width = getDialogAutoWidth();
    } 
	//width = utils.regexNum(width)?width:600;
	paramName = utils.isEmpty(paramName)?'id':paramName;
	if(utils.isEmpty(selectedType)) {
		return;
	}
	if(selectedType=='one-selected') {
		if(!utils.isEmpty(value) && value.indexOf(',')>0){
			BootstrapDialogUtil.warningAlert("只能选择一条数据!");
			return;
		} else if(utils.isEmpty(value)) {
			BootstrapDialogUtil.warningAlert("请选择一条数据!");
			return;
		}
	} else if(selectedType== 'multi-selected') {
		if(utils.isEmpty(value)) {
			BootstrapDialogUtil.warningAlert("请选择数据!");
			return;
		}
	} else {
		value = utils.isEmpty(value)?"":value;
	}
	var params = '';
	if(!utils.isEmpty(value)) {
		params = paramName+"="+value;
	}
	if(!utils.isEmpty(busiName)) {
		params += "&busiName="+busiName;
		if(!utils.isEmpty(op)) {
			params = params+"&op="+op;
		}
	}
	var is = true;
	if(utils.isNotEmpty(beforeCheck)) {
		beforeCheck = eval(beforeCheck);
		if(typeof(beforeCheck) == 'function') {
			is = beforeCheck(paramName,value);
		}
	}
	if(utils.isEmpty(uri) || !is) {
		return;
	}
	if(!utils.isEmpty(params)) {
		if(utils.isContain(uri, "?")) {
			uri = uri+"&"+params;
		} else {
			uri = uri+"?"+params;
		}
	}
	if(!utils.isEmpty(flag) && flag == 'open-self') {
		openTab(title, uri,true);
	} else if(!utils.isEmpty(flag) && flag == 'open-new-tab') {
		openTab(title, uri,true);
	} else {
		BootstrapDialogUtil.loadUriDialog(title,uri,width,"#fff",false,function(dialog){
			initEvent(dialog.getModal());
		});
	}
}

/**
 * 选择关联
 * @param datas
 * @param $this
 * @param start
 */
function selectRelate(datas,$this,start) {
	if(utils.isEmpty(datas)) {
		return;
	}
	var otherValue = datas;
	var len = otherValue.length-start;
	var $parentDiv = $this.parents("td:eq(0)");
	var isTd = true;
	if(utils.isEmpty($parentDiv.html())) {
		 isTd = false;
		 $parentDiv = $this.parents(".form-group:eq(0)").parent();
	}
	var $currentDiv = $parentDiv;
	for (var i = 0; i < len; i++) {
		$parentDiv = $currentDiv.next();
		var $input = $parentDiv.find(".form-control");
		if(utils.isNotEmpty($input.attr("class"))) {
			$input.val(otherValue[i+start]);
			$currentDiv = $parentDiv;
		} else {
			var isBr = false;
			var $tmp = $parentDiv.next();
			if(utils.isNotEmpty($tmp.attr("class")) || utils.isNotEmpty($tmp.html())) {
				$currentDiv = $tmp;
				$input = $currentDiv.find(".form-control");
				if(utils.isNotEmpty($input.attr("class"))) { 
					$input.val(otherValue[i+start]);
				} else {
					isBr = true;
				}
			} else {
				isBr = true;
			}
			if(isBr) {
				$currentDiv = findNextInput($currentDiv,otherValue[i+start]);
				if(null == $currentDiv) {
					break;
				} 
			}
		}
		if(isTd && !isBr) {
			$currentDiv = $input.parents("td:eq(0)");
		}
	}
}

/**
 * 查询下个INPUT
 * @param $parentDiv
 * @param value
 * @returns
 */
function findNextInput($parentDiv,value){
	$parentDiv = $parentDiv.parent().next();
	var $input = _hasInput($parentDiv);
	if(null != $input) {
		var $tmp = $parentDiv.children().eq(0);
		$input = _hasInput($tmp);
		if(null != $input) {
			$input.val(value);
			$parentDiv = $tmp;
		} else {
			$parentDiv = $parentDiv.children().eq(1);
			$input = _hasInput($parentDiv);
			if(null != $input) {
				$input.val(value);
			} else {
				$parentDiv = null;
			}
		}
	} else {
		$parentDiv = null;
	}
	return $parentDiv;
	
	/**
	 * 判断是否有input[class=form-control]
	 * @param $div
	 * @returns
	 */
	function _hasInput($div) {
		var $input = $div.find(".form-control");
		var className = $input.attr("class");
		if(utils.isEmpty(className)) {
			$input = null;
		}
		return $input;
	}
}

/**
 * 自动宽度
 */
function getDialogAutoWidth() {
    return $(window).width()-50;
}
