/**
 * 处理流程表单
 * @author lmq
 */
var END_NODE_KEY = "end";
(function($){
	$.fn.flowForm = function(options) {
		var defaultOptions = {
			formData:null,
			formFieldNames:null,
			username:null,
			deptName:null,
			isToLabel: false,
			callback:null
	    };
		var setting = $.extend(true,defaultOptions,options);
		var $this = $(this);
		var $parent = $this.parent();
		//if(!$this.hasClass("v-hidden")) {
		$this.addClass("v-hidden");
		$parent.prepend('<div class="cnoj-loading"><i class="fa fa-spinner fa-spin fa-lg"></i> 正在加载，请稍候...</div>');
		//}
		controlFormField();
		initFormData(setting.formData);
		setTimeout(function() {
			listenerProcessBtns();
			if(!utils.isEmpty(setting.callback) && typeof(setting.callback) === 'function') {
				setting.callback();
			}
			if(typeof(handleForm) === 'function') {
				handleForm();
			}
			//如果富文本为disable时，去掉textarea
			$this.find(".cnoj-richtext").each(function(){
				var $self = $(this);
				if($self.prop("disabled")) {
					$self.addClass("hidden");
					$self.prop("disabled", false);
					$self.parent().html("<div>"+$self.val()+"</div>");
					//$self.prop("disabled", true);
				}
			});
			richtextListener();
			$parent.find(">.cnoj-loading").remove();
			$this.removeClass("v-hidden");
			if(setting.isToLabel) {
				formValueToLabel($this);
			}
		}, 200);
		
		/**
		 * 控制表单字段
		 */
		function controlFormField() {
			$this.find("input,select,textarea,span.cnoj-checkbox,span.cnoj-radio").each(function(){
				var $findElement = $(this);
				var tagName = $findElement.prop("tagName").toLowerCase();
				if(tagName == 'span' || tagName == 'select') {
					$findElement.attr("data-edit-enable", "0");
				}
				if(tagName != 'span') {
					$findElement.prop("disabled",true);
					$findElement.attr("title","");
					if(tagName == 'input') {
						var type = $findElement.attr("type");
						if(type == 'file' && $findElement.hasClass("cnoj-upload")) {
							$findElement.parent().addClass("disabled");
						}
					}
				}
			});
			//所有list-ctrl属性改为只读
			$this.find(".list-ctrl").find("input,select,textarea").each(function(){
				$(this).prop("disabled",false);
				$(this).prop("readonly",true);
			});
			if(!utils.isEmpty(setting.formFieldNames)) {
				var fieldNames = setting.formFieldNames.split(",");
				var self = this;
				for(var i=0;i<fieldNames.length;i++) {
					$this.find("input[name='"+fieldNames[i]+"'],select[name='"+fieldNames[i]+"'],textarea[name='"+fieldNames[i]+"'],#"+fieldNames[i]+",span[data-name='"+fieldNames[i]+"']").each(function(){
						var $findElement = $(this);
						var tagName = $findElement.prop("tagName").toLowerCase();
						if(tagName == 'span') {
							$findElement.attr("data-edit-enable", "1");
						} else if(tagName == 'div'){
							if($findElement.hasClass("file-upload")) {
								var type = $findElement.find(".fileinput-button").removeClass("disabled");
								$findElement.find("input").prop("disabled",false);
							}
						} else {
							if(tagName == 'select') {
								$findElement.attr("data-edit-enable", "1");
							}
							$findElement.prop("disabled",false);
							$findElement.prop("readonly",false);
							if($findElement.hasClass("cnoj-sysuser-defvalue")) {
								$findElement.val(setting.username);
							} else if($findElement.hasClass("cnoj-sysdeptname-defvalue")) {
								$findElement.val(setting.deptName);
							}
						}
					});
				}
			}
			$this.find(".list-ctrl").find("input,select,textarea").each(function(){
				if($(this).prop("readonly")) {
					$(this).removeClass("cnoj-input-select");
					$(this).removeClass("cnoj-input-select-relate");
					$(this).removeClass("cnoj-auto-complete");
					$(this).removeClass("cnoj-auto-complete-relate");
					$(this).removeClass("cnoj-input-tree");
					
					$(this).removeClass("cnoj-datetime-listener");
					$(this).removeClass("cnoj-date-listener");
					$(this).removeClass("cnoj-time-listener");
				}
			});
		}
		
		/**
		 * 监听流程处理表单按钮
		 */
		function listenerProcessBtns() {
			$("#save-form").click(function() {  //保存表单
				var $saveBtn = $(this);
				$saveBtn.prop("disabled",true);
				var formPorcessInfo = $("#flow-process-form").serialize();//流程信息
				var uri = $(this).data("uri");
				if(!utils.isEmpty(uri)) {
				    parent.utils.waitLoading("正在保存数据...");
					if(uri.indexOf("?")==-1) {
						uri +="?";
					} else {
						uri +="&";
					}
					uri += formPorcessInfo;
					$this.attr("action", uri);
					$this.attr("target","handle-form-iframe");
					$this.submit(); //提交表单到iframe
					$("#handle-form-iframe").load(function(){
					    parent.utils.closeWaitLoading();
					    var result = $(this).contents().text();
					    if(utils.isNotEmpty(result)) {
					    	var output = $.parseJSON(result);
					    	parent.utils.showMsg(output.msg);
					    	if(output.result=='1') {
					    		$("#form-data-id").val(output.data);
					    	}
					    }
					    location.reload();
					    $saveBtn.prop("disabled",false);
					});
				} else {
					$saveBtn.prop("disabled",false);
				}
				return false;
			});
			//驳回按钮
			$("#back-process").click(function(){
				var $backBtn = $(this);
				$backBtn.prop("disabled",true);
				var $backLineRow = $("#select-back-line-row");
				$(".task-submit-form,.is-suggest").hide();
				$("#node-decision-back-prop").show();
				var isShow = false;
				if($backLineRow.data("is-show") == 1) {
					isShow = true;
					$backLineRow.removeClass("hide");
				}
				var $isSug = $backLineRow.parent().find(".is-suggest");
				if(!utils.isEmpty($isSug.attr("class"))) {
					isShow = true;
					$isSug.show();
				}
				if(isShow) {
					$('#myModal').modal('show');
				}
				$backBtn.prop("disabled",false);
				exePorcessBack($this,$(this),isShow);
				return false;
			});
			//提交按钮
			$("#submit-process").click(function(){
				var $submitBtn = $(this);
				$submitBtn.prop("disabled",true);
				//隐藏任务提交的表单及处理意见所在的元素，
				//原因是：避免于点击驳回按钮时候的冲突(显示驳回时候的处理意见或显示驳回时的表单)
				$(".task-submit-form,.is-suggest").hide();
				
				var $nextLineRow = $("#select-next-line-row");
				//默认选择下一环节
				if($nextLineRow.data("is-concurrent") == 1) {
					$nextLineRow.find("input").prop("checked",true).attr("onclick","return false");
				} else {
					$nextLineRow.find("input:first").prop("checked",true);
				}
				//新增根据配置判断，是否需要验证表单；修改于2016年10月29日
				var isNotCheck = true;
				$nextLineRow.find("input[name=nextLineName]:checked").each(function(){
					var isCheckVal = $(this).data("is-check");
					if(isCheckVal == utils.YES_OR_NO.NO) {
						isNotCheck = isNotCheck && true;
					} else {
						isNotCheck = isNotCheck && false;
					}
				});
				var isHasCheckSuccess = false; //是否已经验证成功
				if(isNotCheck) { //如果不用验证表单，则认为已经验证成功
					isHasCheck = true;
				} else {
					isHasCheck = $this.validateForm();
				}
				if(isHasCheck) {
					//显示下一步处理所在的表单（只显示提交时候的所在的表单）
				    $("#node-decision-next-prop").show();
					var isShow = false;
					//当有多个出口时，需要显示所有的下一环节；提供用户选择
					//只有一个出口时，不显示下一环节
					if($nextLineRow.data("is-show") == 1) {
						isShow = true;
						$nextLineRow.removeClass("hide");
					}
					//获取下一环节处理者所在的元素
					var $nextAssignerRow = $("#select-next-assigner-row");
					//判断处理下一环节是否需要选人
					isHandleNextAssigner($nextLineRow);
					
					//如果下一环节处理者所在元素存在，并且需要显示时，显示下一环节处理者；提供用户选择下一环节处理者
					if(!utils.isEmpty($nextAssignerRow.attr("id")) && $nextAssignerRow.data("is-show") == 1) {
						isShow = true;
						$nextAssignerRow.removeClass("hide");
					}
					var $isSug = $nextLineRow.parent().find(".is-suggest");
					if(!utils.isEmpty($isSug.attr("class"))) {
						isShow = true;
						$isSug.show();
					}
					if(isShow) {
						$('#myModal').modal('show');
					}
					$submitBtn.prop("disabled",false);
					exePorcessNext($this,$(this),isShow);
				} else {
					$submitBtn.prop("disabled",false);
				}
                return false;
			});
		}
		
		/**
		 * 初始化表单数据
		 * @param output
		 */
		function initFormData(output) {
			if(!utils.isEmpty(output)) {
				var output = $.parseJSON(output);
				if(output.result == '1') {
				   var datas = output.datas;
				   for(var i=0;i<datas.length;i++) {
					   if(null != datas[i].nameMoreValues && datas[i].nameMoreValues.length>0) {
						   var tableTag = datas[i].name+"_table";
						   var $tableTag = $this.find("#"+tableTag);
						   $tableTag.find(".listctrl-add-row").hide();
						   var datas2 = datas[i].nameMoreValues;
						   var rows = datas2[0].valueSize;
						   for(var j = 1;j<rows;j++) {
							   tbAddRow(datas[i].name, false);
						   }
						   //处理控件列表
						   $tableTag.find(".delrow").addClass("hide");
						   var isListCtrlAdd = false;
						   var isListCtrlDel = true;
						   for (var j = 0; j < datas2.length; j++) {
							   var index = 0;
							   $tableTag.find("input[name='"+datas2[j].name+"'],select[name='"+datas2[j].name+"'],textarea[name='"+datas2[j].name+"'],#"+datas2[j].name).each(function(){
									if(datas2[j].valueSize>1) {
										if(datas2[j].value[index] != 'null')
											setFormValue($(this),datas2[j].value[index]);
										index++;
									} else {
										if(datas2[j].value != 'null')
											setFormValue($(this),datas2[j].value);
									}
							   });
							   var fieldNames = setting.formFieldNames.split(",");
							   var isTr = false;
							   for(var k=0;k<fieldNames.length;k++) {
								   if(fieldNames[k] == datas2[j].name) {
									   isTr = true;break;
								   }
							   }
							   if(isTr) {
								   isListCtrlAdd = true;
							   }
							   if(datas2[j].name.endWith("_id")) {
								   isTr = true;
							   }
							   isListCtrlDel = isListCtrlDel && isTr;
						   }
						   //判断是否可以操作listctrl
						   //当能填写或修改列表中的值时，则拥有添加行的权限
						  if(isListCtrlAdd) {
							  $tableTag.find(".listctrl-add-row").show();
						  }
						  //当所有字段都有修改权限时，则拥有删除行的权限
						  if(isListCtrlDel) {
							  $tableTag.find(".delrow:gt(0)").removeClass("hide");
						  }
					   } else {
						   var index = 0;
						   var name = datas[i].name;
						   $this.find("input[name='"+name+"'],select[name='"+name+"'],textarea[name='"+name+"'],#"+name+",span[data-name='"+name+"']").each(function(){
							   var value = datas[i].value;
							   var $findElement = $(this);
							   var tagName = $findElement.prop("tagName").toLowerCase();
							   if(tagName == 'span') {
								   $findElement.attr("data-default-value", value);
							   } else if(utils.isNotEmpty(value) && value != 'null') {
									setFormValue($findElement,value, tagName);
							   } else if(tagName == 'div' && $findElement.hasClass("file-upload")) {
								   formAttPluginHandler($findElement, value);
							   }
							   index++;
						   });
					   }
				   }//for
				}
			}//if
		}
		
		/**
		 * 设置表单值
		 * @param $this
		 * @param value
		 * @param tagName 
		 */
		function setFormValue($this, value, tagName) {
			var type = $this.attr("type");
			if(type == 'checkbox' || type == 'radio') {
				if(value.indexOf(",")>-1) {
					var values = value.split(",");
				    for(var i=0; i<values.length;i++) {
				    	if($this.val() == values[i]) {
							$this.prop("checked",true);
						}
				    }
				} else {
					if($this.val() == value) {
						$this.prop("checked",true);
					}
				}
			} else if(type == 'file') {
				formAttHandler($this, value);
			} else {
				if(typeof(tagName) != 'undefined' && tagName == 'div' && $this.hasClass("file-upload")) {
					formAttPluginHandler($this, value);
				} else {
					if(utils.isNotEmpty(value)) {
                        if($this.hasClass('cnoj-datetime')) {
                            if(utils.isNotEmpty(value)) {
                                if(value.endWith(".0"))
                                    value = value.substr(0,19);
                            }
                        } else if($this.hasClass('cnoj-date')) {
                            if(utils.isNotEmpty(value)) {
                                if(value.length>10)
                                    value = value.substr(0,11);
                            }
                        } else if($this.hasClass('cnoj-time')) {
                            if(value.length>=19)
                                value = value.substr(11,19);
                        }
					}
					$this.val(value);
				}
			}
		}
	};
})(jQuery)

/**
 * 删除附件
 */
function listenerAttDel() {
	$("#process-attachment .att-del").click(function(){
		var $tr = $(this).parent().parent();
		var id = $tr.find(".process-att-id").val();
		if(utils.isNotEmpty(id)) {
			BootstrapDialogUtil.delDialog("附件",'op/del.json?busiName=flowAtt',id,function(){
				$tr.remove();
				if(utils.isNotEmpty(flowAttUri)) {
					loadUri("#process-att-tab",flowAttUri,false);
				}
			});
		}
	});
}

/**
 * 提交任务
 * @param url
 * @param param
 * 
 */
function submitTask(formObj,url,param) {
	var formPorcessInfo = $("#flow-process-form").serialize();
	//流程表单信息
	//formPorcessInfo += "&"+formObj.serialize();
	if(!utils.isEmpty(param)) {
		formPorcessInfo += "&"+param;
	}
	var refreshUrl = $("#refresh-url").val();
	refreshUrl = utils.isEmpty(refreshUrl)?"process/todo":refreshUrl;
	if(!utils.isEmpty(url)) {
		parent.utils.waitLoading("正在处理表单数据...");
		if(url.indexOf("?")==-1) {
			url +="?";
		} else {
			url +="&";
		}
		url += formPorcessInfo;
		formObj.attr("action", url);
		formObj.attr("target","handle-form-iframe");
		formObj.submit(); //提交表单到iframe
		$("#handle-form-iframe").load(function(){
			parent.utils.closeWaitLoading();
	    	var result = $(this).contents().text();
	    	if(utils.isNotEmpty(result)) {
	    		var output = $.parseJSON(result);
	    		parent.utils.showMsg(output.msg);
	    		if(output.result=='1') {
	    			if(utils.isNotEmpty(parent.isExistMyTodoTab)) {
						parent.todoRefresh(refreshUrl);
					}
					parent.submitFormRefershIndexTodo();
					parent.closeActivedTab();
				}
	    	} else {
	    		parent.utils.showMsg('处理失败');
	    	}
		});
	}
}

/**
 * 完成当前任务--驳回
 * @param objTag
 * @param isShow
 */
function exePorcessBack(formObj,objTag,isShow) {
	var $backLineRow = $("#select-back-line-row");
	var $backProp = $("#node-decision-back-prop");
	$backLineRow.find("input:first").prop("checked",true);
	var isSugArea = false;  //是否有意见域
	var sugContent = null;
	if(!utils.isEmpty($backProp.find(".is-suggest").attr("class"))) {
		isSugArea = true;
	}
	var isSubmit = true;
	if(isShow) {
		//当点击确定按钮时，处理的事件
		$("#dialog-ok").unbind('click');
		$("#dialog-ok").click(function(){
			var param = '';
			if(isSugArea) {
				sugContent = $backProp.find(".handle-suggest").val();
				if(utils.isEmpty(sugContent)) {
					$backProp.validateForm();
					isSubmit = false;
					return false;
				} else {
					isSubmit = true;
				}
			}
			if(isSubmit) {
				$('#myModal').modal('hide');
				$('#myModal').on('hidden.bs.modal', function (e) {
					handleRequest(formObj,objTag,$backLineRow,"isBack=1");
				});
			}
			return false;
		});
	} else {
		handleRequest(formObj,objTag,$backLineRow,"isBack=1");
	}
}

/**
 * 处理请求
 * @param formObj
 * @param objTag
 * @param $lineRowTag
 * @param param
 */
function handleRequest(formObj,objTag,$lineRowTag,param) {
	var params = $lineRowTag.parents("form").serialize();
	if(!utils.isEmpty(param)) 
		params = params+"&"+param;
	var url = objTag.data("uri");
	
	//判断是否起草人提交
	if(utils.isEmpty($("#form-data-id").val())) {
		//检测标题
		var titleFieldId = checkInsTitle();
		if(utils.isNotEmpty(titleFieldId)) {
			var name = $("#"+titleFieldId).data("label-name");
			BootstrapDialogUtil.confirmDialog("该"+utils.handleNull(name)+"已经存在，是否继续提交？", function() {
				submitTask(formObj,url,params);
			});
		} else {
			submitTask(formObj,url,params);
		}
	} else {
		submitTask(formObj,url,params);
	}
}

/**
 * 检测标题是否存在
 * @returns 
 * 如果标题存在则返回标题对象的字段ＩＤ
 */
function checkInsTitle() {
	var titleFieldId = null;
	var formPorcessInfo = $("#flow-process-form").serialize();
	//流程表单信息
	formPorcessInfo += "&"+$("#process-handle-form").serialize();
	if(utils.isNotEmpty(formPorcessInfo)) {
		utils.waitLoading("正在验证标题是否重复...")
		$.ajax({
			url: 'process/checkInsTitle.json',
			async: false,
			data: formPorcessInfo,
			success: function(output) {
				utils.closeWaitLoading();
				if(output.result == '1') {
					titleFieldId = output.data;
				}
			}
		})
	}
	return titleFieldId;
}

/**
 * 完成当前任务--继续往下执行
 * @param objTag
 * @param isShow
 */
function exePorcessNext(formObj,objTag,isShow) {
	var $nextLineRow = $("#select-next-line-row");
	var $nextProp = $("#node-decision-next-prop")
	
	var $nextAssignerRow = $("#select-next-assigner-row");
	var isSelectAssigner = false; //是否需要选择参与者
	var checkedAssigners = null; //选中的参与者
	if($nextAssignerRow.data("is-show") == utils.YES_OR_NO.YES) {
		isSelectAssigner = true;
		listenerOrInitNextAssigners($nextLineRow,$nextAssignerRow);
		var isShow = $nextAssignerRow.data("is-show")==1?true:false;
		if(!isShow) {
			checkedAssigners = getCheckedAssigners($nextLineRow);
		}
	}
	var isSugArea = false;  //是否有意见域
	var sugContent = null;
	if(!utils.isEmpty($nextProp.find(".is-suggest").attr("class"))) {
		isSugArea = true;
	}
	var isSubmit = true;
	var param = '';
	var nextAssigners = '';
	if(isShow) {
		//当点击确定按钮时，处理的事件
		$("#dialog-ok").unbind('click');
		$("#dialog-ok").click(function(){
			if(isSelectAssigner) {
				checkedAssigners = getCheckedAssigners($nextLineRow);
				if(utils.isEmpty(checkedAssigners)) {
					isSubmit = false;
					return false;
				} else 
					isSubmit = true;
				if(utils.isNotEmpty(checkedAssigners) && checkedAssigners != END_NODE_KEY)
					nextAssigners = "nextAssigners="+checkedAssigners;
			}
			if(isSugArea) {
				sugContent = $nextProp.find(".handle-suggest").val();
				if(utils.isEmpty(sugContent)) {
					$nextProp.validateForm();
					isSubmit = false;
					return false;
				} else 
					isSubmit = true
			}
			if(!utils.isEmpty(nextAssigners)) 
				param = param+"&"+nextAssigners;
			if(isSubmit) {
				$('#myModal').modal('hide');
				$('#myModal').on('hidden.bs.modal', function (e) {
					$("#myModal").unbind('hidden.bs.modal');
					handleRequest(formObj,objTag, $nextLineRow, param);
				});
			}
			return false;
		});
	} else {
		if(utils.isNotEmpty(checkedAssigners) && checkedAssigners != END_NODE_KEY) {
			param = param+"&nextAssigners="+checkedAssigners;
		}
		handleRequest(formObj,objTag, $nextLineRow, param);
	}
}

/**
 * 监听或初始化下一环节参与者
 * @param $nextLineRow
 * @param $nextAssignerRow
 */
function listenerOrInitNextAssigners($nextLineRow,$nextAssignerRow) {
	var selectStyle = $nextAssignerRow.data("select-style");
	var isShow = $nextAssignerRow.data("is-show")==1?true:false;
	var processId = $("#process-id").val();
	var orderId = $("#order-id").val();
	var isSelect = 0;
	$nextLineRow.find("input[name=nextLineName]:checked").each(function(){
		isSelect = $(this).data("is-select");
		selectStyle = $(this).data("select-style");
		if(isSelect == utils.YES_OR_NO.YES) {
			createNextAssignerTree($(this), processId, orderId,selectStyle,isShow);
		}
	});
	var isConcurrent = $nextLineRow.data("is-concurrent");
	if(isConcurrent == 0) {
		$nextLineRow.find("input[type=radio]").click(function(){
			var selectUserTreeId = $(this).attr("id")+"-user-org-tree";
			if(utils.isEmpty($("#"+selectUserTreeId).attr("id"))) {
				//摧毁之前建立的树
				var beforeTreeId = null;
				$nextLineRow.find("input[type=radio]").each(function(){
					beforeTreeId = $(this).attr("id")+"-user-org-tree";
					$("#"+beforeTreeId).zTreeUtil({destory:true});
				});
				$("#select-assigner-content").html("");
				isSelect = $(this).data("is-select");
				selectStyle = $(this).data("select-style");
				if(isSelect == utils.YES_OR_NO.YES) {
					createNextAssignerTree($(this), processId, orderId,selectStyle,isShow);
				}
			}
		});
	}
}

/**
 * 生成选择下一环节参与者树
 * @param tagObj
 * @param processId
 * @param orderId
 * @param selectStyle
 * @param isShow
 */
function createNextAssignerTree(tagObj,processId,orderId,selectStyle,isShow) {
	var selectUserTreeId = tagObj.attr("id")+"-user-org-tree";
	if(utils.isEmpty($("#"+selectUserTreeId).attr("id"))) {
	    $("#select-assigner-content").append("<div id='"+selectUserTreeId+"' class='select-user-tree-wrap left'><div class='next-title ui-state-default'>"+tagObj.parent().text()+"</div></div>");
	}
	var pathName = tagObj.val();
	var taskKey = pathName.split("_")[1];
	var params = "processId="+processId+"&orderId="+orderId+"&taskKey="+taskKey;
	$("#select-next-assigner-row").hide();
	if(taskKey != END_NODE_KEY) {
		$("#select-next-assigner-row").show();
		var url = "process/selectNextAssigner.json?"+params;
		var checkOpt = null;
		if(selectStyle == 'radio') {
			checkOpt = {check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			}};
		}
		$("#"+selectUserTreeId).zTreeUtil({
			uri:url,
			isCheck:true,
			isAjaxAsync:false,
			isSearch:false,
			isLoading:true,
			checkOpt:checkOpt,
			callback:function(zTreeObj){
				if(selectStyle=='checkbox' && !isShow) 
					zTreeObj.checkAllNodes(true);
			}
		});
	} else {
		$("#select-next-assigner-row").hide();
		$("#select-assigner-content").html("");
	}
}

/**
 * 获取选中的参与者
 * @param $nextLineRow
 */
function getCheckedAssigners($nextLineRow) {
	var checkedAssigners = '';
	var isNext = true;
	$nextLineRow.find("input[name=nextLineName]:checked").each(function(){
		var checkedAssigner = null;
		var selectUserTreeId = $(this).attr("id")+"-user-org-tree";
		var taskKey = $(this).val().split("_")[1];
		$("#"+selectUserTreeId).zTreeUtil({
			getTreeObj:function(zTreeObj){
				if(null != zTreeObj) {
					var nodes = zTreeObj.getCheckedNodes(true);
					if(null != nodes && nodes.length>0) {
						checkedAssigner = "";
						for (var i = 0; i < nodes.length; i++) {
							checkedAssigner += nodes[i].id+",";
						}
						checkedAssigner = checkedAssigner.substring(0,checkedAssigner.length-1);
					}
				}
			}
		});
		if(null != checkedAssigner) 
			checkedAssigners += taskKey+"("+checkedAssigner+");";
		else if(taskKey == END_NODE_KEY) {
			checkedAssigners += taskKey+";";
		} else {
			$("#"+selectUserTreeId).addClass("border-color-red");
			utils.showMsg("请选择下一步处理者！");
			setTimeout(function(){
	    		$("#"+selectUserTreeId).removeClass("border-color-red");
			}, 2000);
			isNext = isNext && false;
			return false;
		}
	});
	if(checkedAssigners != '') {
		checkedAssigners = checkedAssigners.substring(0,checkedAssigners.length-1);
	}
	checkedAssigners = isNext?checkedAssigners:'';
	return checkedAssigners;
}

/**
 * 判断处理一下环节是否需要选人
 * @param $nextLineRow
 */
function isHandleNextAssigner($nextLineRow) {
	var selectedNextTask = '';
	$nextLineRow.find("input[name=nextLineName]").each(function(){
		selectedNextTask = selectedNextTask+$(this).val().split("_")[1]+",";
	});
	selectedNextTask = utils.isNotEmpty(selectedNextTask)?selectedNextTask.substring(0, selectedNextTask.length-1):selectedNextTask;
	var processId = $("#process-id").val();
	$.ajax({
		url:'process/isSelectAssigner.json?nextTaskKeys='+selectedNextTask+"&processId="+processId,
		type:'get',
		async:false,
		success:function(data) {
			if(data.result == utils.YES_OR_NO.YES) {
				$("#select-next-assigner-row").data("is-show",data.data);
				if(data.data == utils.YES_OR_NO.YES) {
					var taskKey = null;
					var resultValues = null;
					$nextLineRow.find("input[name=nextLineName]").each(function(){
						var $this = $(this);
						taskKey = $this.val().split("_")[1];
						for(var i=0;i<data.datas.length;i++) {
							resultValues = data.datas[i].split("_");
							if(taskKey == resultValues[0]) {
								$this.data("is-select",resultValues[1]);
								$this.data("select-style",resultValues[2]);
								break;
							}
						}
					});
				}
			}
		}
	});
}

/**
 * 处理表单附件
 * @param $element
 * @param value
 */
function formAttHandler($element, value) {
	var isDisabled = $element.prop("disabled");
	var name = $element.attr("name");
	var newName = name+"_file";
	var $eleClone = null;
	if(!isDisabled) {
		$eleClone = $element.clone();
		$eleClone.attr("name", newName);
		$eleClone.attr("id", newName);
		$element.removeClass("require");
		$element.after($eleClone);
		$element.attr("type","text");
	}
	if(utils.isNotEmpty(value)) {
		$element.attr("type","text");
		$element.addClass("hidden");
		attachmentListHandler(value, $element, isDisabled);
		if(null != $eleClone) {
			$eleClone.removeClass("require");
		}
	}
}

/**
 * 附件列表处理者
 * @param value
 * @param $element
 */
function attachmentListHandler(value,$element, isDisabled) {
	$.get("process/attachment/info?id="+value, function(output){
		var attInfos = null;
		var elementId = $element.attr("name");
		var attIds = "";
		if(output.result == 1) {
			attInfos = "<ul class='file-list' id='formatt_'"+elementId+">";
			var len = output.datas.length;
			var datas = output.datas;
			var fileType = null;
			for(var i=0; i<len; i++) {
				attInfos += "<li class='att-item'><span class='visible-print-inline'>"+datas[i][2]+"</span><a class='hidden-print' href='download/att?id="+datas[i][0]+"' target='_blank'>"+datas[i][2]+"</a>（"+datas[i][3]+"）";
				attInfos += "<ul class='form-att-op hidden list-inline hidden-print'>操作：";
				fileType = utils.handleNull(datas[i][4]);
				if(utils.isNotEmpty(fileType)) {
					fileType = fileType.toLowerCase();
				}
				if(fileType == 'jpg' || fileType == 'gif' || fileType == 'png' || fileType == 'txt' || fileType == 'pdf') {
					attInfos += "<li><a href='att/view?id="+datas[i][0]+"' target='_blank'>查看</a></li>";
				}
				attInfos += "<li><a href='download/att?id="+datas[i][0]+"' target='_blank'>下载</a></li>";
				if(!isDisabled) {
					attInfos += "<li><a href='javascript:void(0)' data-input-id='"+elementId+"' onclick=deleteFormAtt(this,'"+datas[i][1]+"','"+datas[i][0]+"')><i class='fa fa-trash' aria-hidden='true'></i> 删除</a></li>";
				}
				attInfos +="</ul></li>";
				attIds += datas[i][0]+",";
			}
			attInfos += "</ul>";
			var $ul = $(attInfos);
			$ul.find(".att-item").mouseover(function() {
				var $this = $(this);
				var h = $this.height();
				var $attOp = $this.find(".form-att-op");
				var pos = $this.position();
				$attOp.css({"top":(pos.top+h)+"px","left":pos.left+"px"});
				$attOp.removeClass("hidden");
			}).mouseout(function() {
				$(this).find(".form-att-op").addClass("hidden");
			});
			//判断是否添加过，如果添加过，则删除附件列表元素
			var $parent = $element.parent();
			var $fileList = $parent.find(".file-list");
			if($fileList.length>0) {
				$fileList.remove();
			}
			$parent.prepend($ul);
		}
		if(utils.isNotEmpty(attIds)) {
			attIds = attIds.substring(0, attIds.length-1);
		}
		$element.val(attIds);
	});
}

/**
 * 删除附件
 * @param elementObj 元素对象
 * @param id 流程附件ID
 * @param attId 附件ID
 */
function deleteFormAtt(elementObj, id, attId) {
	if(utils.isNotEmpty(id)) {
		var $li = $(elementObj).parents("li:eq(0)");
		var $ul = $(elementObj).parents("ul:eq(0)");
		var inputEleId = $(elementObj).data("input-id");
		var formDataId = $("#form-data-id").val();
		BootstrapDialogUtil.delDialog("附件",'process/attachment/deleteForm?fieldId='+inputEleId+'&formDataId='+formDataId+"&attId="+attId,id,function(){
			$li.remove();
			//删除隐藏文本框内的对应的附件ID
			var $parent = $(elementObj).parents(".file-list:eq(0)").parent();
			var $inputEle = $parent.find("input[name='"+inputEleId+"']");
			if($inputEle.length>0) {
				var attIds = $inputEle.val();
				if(utils.isNotEmpty(attIds)) {
					attIds = attIds.replace(attId+",","").replace(","+attId,"").replace(attId,"");
				}
				$inputEle.val(attIds);
			}
			//判断是否还有附件
			$li = $ul.find("li");
			if($li.length == 0) {
                var $inputFile = $ul.parent().find("input:eq(0)");
                $inputFile.val("");
				//$ul.parent().find("input").removeClass("hidden");
			}
			if(utils.isNotEmpty(flowAttUri)) {
				loadUri("#process-att-tab",flowAttUri,false);
			}
		});
	}
}

/**
 * 表单附件插件处理者
 * @param $element
 * @param value
 */
function formAttPluginHandler($element, value) {
	//创建一个隐藏的输入框
	var id = $element.attr("id");
	$element.prepend("<input type='hidden' name='"+id+"' />");
	var $inputEle = $element.find("input[name='"+id+"']");
	if(utils.isNotEmpty(value)) {
		var isDisabled = $element.find(".fileinput-button").hasClass("disabled");
		if(isDisabled) {
			$element.find(".fileinput-button").addClass("hidden");
		}
		attachmentListHandler(value, $inputEle, isDisabled);
	}
		
}

/**
 * 显示表单附件列表
 * @param datas
 * @param $element
 */
function showFormAttList(datas, $element) {
	if(utils.isNotEmpty(datas) && datas.length>0 && utils.isNotEmpty($element)) {
		var id = $element.attr("id");
		var inputName = id.replace("-mfile","");
		var $parent = $("#"+inputName);
		var tagName = $parent.prop("tagName");
		console.log($parent.attr("id")+","+tagName);
		var $inputEle = $parent.find("input[name='"+inputName+"']");
		var attId = '';
		for(var i=0;i<datas.length;i++) {
			attId += datas[i].id+',';
		}
		attId = attId.substring(0, attId.length-1);
		var value = $inputEle.val();
		if(utils.isNotEmpty(value)) {
			attId = value+","+attId;
		}
		attachmentListHandler(attId, $inputEle, false);
	}
} 

/**
 * 表单值转换为label
 * @param $element
 * @returns {Boolean}
 */
function formValueToLabel($element) {
	if(utils.isEmpty($element)) {
		return false;
	}
	$element.find("input[type=text],select,textarea").each(function(){
		var $obj = $(this);
        var tagName = $obj.prop("tagName").toLowerCase();
        if(!$obj.hasClass("hidden") && !$obj.hasClass("hide")) {
            var value = $obj.val();
            if(utils.isNotEmpty(value)) {
                value = utils.replaceAll(value,'\n','<br />');
            }
            if(tagName == 'select') {
            	value = $obj.find("option:selected").text();
            } else if(tagName == 'input' && ($obj.attr("type") == 'checkbox' || $obj.attr("type") == 'radio')) {
            	if($obj.prop("checked")) {
            		$obj.addClass("hidden");
            		return;
            	} else {
            		$obj.parent().addClass("hidden");
            		return;
            	}
            }
            var width = $obj.width();
            $obj.addClass("hidden");
            var $td = $obj.parents("td:eq(0)");
            var tbColor = $td.css("border-color");
            if($td.length==0 || utils.isNotEmpty(tbColor) && 
            		(tbColor.toLowerCase() == '#fff' || tbColor.toLowerCase() == '#ffffff' 
            			|| tbColor.toLowerCase() == 'rgb(255, 255, 255)'))
            	$obj.after("<span style='border-bottom:1px solid #ccc;display:inline-block;width:"+width+"px'>"+value+"</span>");
            else {
            	$obj.after("<span>"+value+"</span>");
            }
        }
    });
}