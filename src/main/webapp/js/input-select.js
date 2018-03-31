/**
 * @author lmq
 * 输入框下来列表
 * @param $
 */
(function($){
	/**
	 * 输入框下来列表
	 */
	$.fn.inputSelect = function(options) {
		//var defaultW = 200;
		//var defaultH = 200;
		var setting = {
				uri:null,
				paramName:"name",
				defaultValue:null,
				panelWidth:200,
				panelHeight:200,
				isShow:true,
				isShowAll:true,
				isShowNone:false,
				selectCallback:null
		};
		setting = $.extend(setting,options);
		var menuIndex = 0;
		if(typeof(getActiveTabIndex) != 'undefined') {
			if(utils.isIframe) {
				menuIndex = 1;
			} else {
				menuIndex = getActiveTabIndex();
			}
		}
		var $this = $(this);
		var thisId = $(this).attr("id");
		if(utils.isEmpty(setting.defaultValue)) {
		    setting.defaultValue = $this.val();
		}
		if(utils.isEmpty(thisId)) {
			var generateId = "input-select"+utils.randomNum(5);
			$(this).attr("id",generateId);
			thisId = generateId;
		}
		var newId = thisId+"-input-select"+menuIndex;
		var inputName = $this.attr("name");
		if(utils.isEmpty(inputName)) {
			inputName = "";
		}
		$this.attr("name","");
		var page = 1;
		var hiddenInputId = thisId+"-value"+menuIndex;
		if(!utils.isEmpty($("#"+newId).attr("id")) && utils.isEmpty($("#"+hiddenInputId).attr("id"))) {
			destory();
		}
		var $newDiv = $("#"+newId);
		var isBody = true;
		//判断输入框是否在弹出窗口内
		var $modelDialog = $this.parents(".modal-dialog");
		if($modelDialog.length > 0) {
			isBody = false;
		}
		if($newDiv.length == 0) {
			if(!isBody) {
				$this.after("<div id='"+newId+"' data-target-inputid='"+hiddenInputId+"' class='input-select-panel'></div>");
			} else {
				$("body").append("<div id='"+newId+"' data-target-inputid='"+hiddenInputId+"' class='input-select-panel'></div>");
			}
			$newDiv = $("#"+newId);
			$("#"+hiddenInputId).remove();
			var hiddenInput = "<input type='hidden' id='"+hiddenInputId+"' name='"+inputName+"' />";
			//$newDiv.append(hiddenInput);
			$this.after(hiddenInput);
			//$hiddenInput = $("#"+hiddenInputId);
			var styleSize = "input-group-sm";
			if(utils.isContain($(this).attr("class"),'input-sm')) {
				styleSize = "input-group-xs";
			}
			var searchContents = "<div class='input-select-search'><div class='input-group "+styleSize+"'>"+
			"<input type='text' class='form-control search-input' /><span class='input-group-btn'>"+
			"<button class='btn btn-default search-btn' type='button'>搜索</button></span></div></div>";
			$newDiv.append(searchContents);
			var h = setting.panelHeight;
			$newDiv.append("<div class='input-select-content' style='height:"+h+"px'></div>");
			
			if(utils.isEmpty(setting.uri)) {
				$newDiv.find(".input-select-content").html("没有搜索到相关数据");
			} else {
				loadData(setting.uri);
				$newDiv.find(".search-btn").click(function(){
					_search();
					return false;
				});
				//如果在输入框中，按回车，则触发搜索
				$newDiv.find(".search-input").keydown(function(event){
					if(event.keyCode == 13) {
						_search();
					}
				});
			}
			$newDiv.append("<div class='input-select-footer'></div>");
			var $footerWrap = $newDiv.find(".input-select-footer");
			$footerWrap.append("<div class='input-select-footer-page'><nav><ul class='pager'>"+
					"<li class='previous'><a href='javascript:void(0)'>上一页</a></li>"+
					"<li class='next'><a href='javascript:void(0)'>下一页</a></li>"
					+"</ul></nav></div>");
			if(setting.isShow) {
				resize(isBody);
				$newDiv.show();
			} else {
				$newDiv.hide();
			}
		} else {
			if(setting.isShow) {
				var hasShow = $newDiv.data("has-show");
				if(hasShow != 1) {
					resize(isBody);
				}
				$newDiv.show();
			} else {
				$newDiv.hide();
			}
		}
		/*$newDiv.click(function(event){
			event.stopPropagation();
		});
		$(document).click(function(){
			$(".input-select-panel").hide();
			$this.prop("readonly",false);
		});*/
		$(document).on("mousedown",function(event){
			if ($(event.target).closest('#'+newId).length === 0) {
				$("#"+newId).hide();
				$this.prop("readonly",false);
			}
		});
		
		function _search() {
			var uri = setting.uri;
			if(utils.isContain(setting.uri,"?")) {
				if(setting.uri.startWith("op/queryReq")) {
					uri = setting.uri+"&"+setting.paramName+"="+$newDiv.find(".search-input").val();
				} else if(utils.isContain(setting.uri,"op/"))
					uri = setting.uri+"&paramName="+setting.paramName+"&paramValue="+$newDiv.find(".search-input").val();
				else 
					uri = setting.uri+"&"+setting.paramName+"="+$newDiv.find(".search-input").val();
			} else {
				if(setting.uri.startWith("op/queryReq")) {
					uri = setting.uri+"?"+setting.paramName+"="+$newDiv.find(".search-input").val();
				} else if(utils.isContain(setting.uri,"op/"))
					uri = setting.uri+"?paramName="+setting.paramName+"&paramValue="+$newDiv.find(".search-input").val();
				else 
					uri = setting.uri+"?"+setting.paramName+"="+$newDiv.find(".search-input").val();
			}
			loadData(uri);
		}
		
		function resize(isBody) {
			var thisW = $this.outerWidth(true);
			var width = thisW<setting.panelWidth?setting.panelWidth:thisW;
			var top=0,left=0;
			if(!isBody) {
				var pos = $this.position();
				top  = pos.top+$this.outerHeight(true);
				left = pos.left;
			} else {
				var offset = $this.offset();
				top  = offset.top+$this.outerHeight(true);
				left = offset.left;
			}
			$newDiv.css({"left":left+"px","width":width+"px"});
			var $form = $this.parents("form:eq(0)");
			
			$newDiv.data("has-show",1);
			
			var windowHeight = $(window).outerHeight(true);
			var h = $newDiv.outerHeight(true);
			if((top+h)>(windowHeight-10)) {
				top = top-h-$this.outerHeight(true);
				if(top<0) {
					top = 0;
				}
				$newDiv.css("top",top+"px");
			} else {
				$newDiv.css("top",top+"px");
			}
		}
		
		/**
		 * 加载数据
		 */
		function loadData(uri){
			//var array = parseUri(uri);
			//if(null != array) {
			    var $showContentTag = $newDiv.find(".input-select-content");
			    $showContentTag.html('<div class="dropdown-loading"><i class="fa fa-spinner fa-spin fa-lg"></i> 正在加载，请稍候...</div>');
				$.get(uri,function(data){
					var output = data;//$.parseJSON(data.output);
					if(output.result == '1') {
						var datas = output.datas;
						var contents = '';
						if(setting.isShowAll) {
							contents = "<div class='option-row'><a href='#' data-value=''>全部</a></div>";
						}
						if(setting.isShowNone) {
							contents += "<div class='option-row'><a href='#' data-value=''>无</a></div>";
						}
						var hiddenInputId = $("#"+newId).data("target-inputid");
						for(var i=0;i<datas.length;i++) {
							if(!utils.isEmpty(setting.defaultValue) && setting.defaultValue == datas[i][0]) {
								var $hiddenInput= $("#"+hiddenInputId);
								$hiddenInput.val(setting.defaultValue);
								var $input = utils.findPrevTag($hiddenInput,"input");
								if($input != null) {
									$input.val(datas[i][1]);
								}
								//$("#"+hiddenInputId).prev().val(datas[i][1]);
							}
							var allData = '';
							for(var j=0;j<datas[i].length;j++) {
								allData +=datas[i][j]+"##";
							}
							allData = allData.substring(0, allData.length-2);
							contents += "<div class='option-row'><a href='#' data-all-data='"+allData+"' data-value='"+datas[i][0]+"'>"+datas[i][1]+"</a></div>";
						}
						$showContentTag.html(contents);
						$showContentTag.find(".option-row a").unbind("click");
						$showContentTag.find(".option-row a").click(function(){
							var $inputSelectPanel = $(this).parents(".input-select-panel:eq(0)");
							var hiddenInputId = $inputSelectPanel.data("target-inputid");
							var $hiddenInput = $("#"+hiddenInputId);
							var $input = $hiddenInput.parent().find("input[type=text]:eq(0)");
							$hiddenInput.val($(this).data("value"));
							if($input != null) {
								$input.val($(this).text());
								$input.trigger("change");
								$input.prop("readonly",false);
							}
							$inputSelectPanel.hide();
							var allData= $(this).data("all-data");
							var array = null;
							if(utils.isNotEmpty(allData)){
								array = allData.split("##");
							}
							if(utils.isNotEmpty(setting.selectCallback)) {
								setting.selectCallback(this,array);
							}
							return false;
						});
						if(output.totalPage>1) {
							var $inputSelectFooter = $newDiv.find(".input-select-footer");
							$inputSelectFooter.find(".previous a").unbind("click");
							$inputSelectFooter.find(".next a").unbind("click");
							$inputSelectFooter.show();
							if(page<=1) {
								$inputSelectFooter.find(".previous").addClass("disabled");
							} else {
								$inputSelectFooter.find(".previous").removeClass("disabled");
								$inputSelectFooter.find(".previous a").click(function(){
									page--;
									changePage(uri, page);
									return false;
								});
							}
							if(page>=output.totalPage) {
								$inputSelectFooter.find(".next").addClass("disabled");
							} else {
								$inputSelectFooter.find(".next").removeClass("disabled");
								$inputSelectFooter.find(".next a").click(function(){
									page++;
									changePage(uri, page);
									return false;
								});
							}
						} else {
							$newDiv.find(".input-select-footer").hide();
						}
					} else {
						$showContentTag.html("没有搜索到相关数据");
					}
				});
			//}
		};
		/**
		 * 
		 */
		function changePage(uri,page) {
			uri = uri.replace(/\?page=\d+|\&page=\d+/, "");
			if(utils.isContain(uri, "?")) {
				uri = uri+"&page="+page;
			} else {
				uri = uri+"?page="+page;
			}
			loadData(uri);
		};
		
		/**
		 * 销毁下拉框
		 */
		function destory() {
			$("#"+newId).find(".option-row a").unbind("click");
			var $inputSelectFooter = $("#"+newId).find(".input-select-footer");
			$inputSelectFooter.find(".previous a").unbind("click");
			$inputSelectFooter.find(".next a").unbind("click");
			$("#"+newId).remove();
			$("#"+hiddenInputId).remove();
		};
	}
})(jQuery);