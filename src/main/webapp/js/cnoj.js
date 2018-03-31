/**
 * @author lmq
 */
var cnoj = function() {}
var mainTag = "#main-content";
/**
 * post提交数据
 * @param uri
 * @param params
 * @param target
 * @param refreshUri
 */
cnoj.postData = function(uri,params,target,refreshUri) {
	utils.waitLoading("正在处理数据...");
	$.post(uri,params,function(data){
		utils.closeWaitLoading();
		utils.showMsg(data.msg);
		if(data.result == '1') {
			if(!utils.isEmpty(refreshUri)) {
				if(!utils.isEmpty(target) && target != mainTag) {
					loadUri(target, refreshUri, true, false);
				} else {
					//loadLocation(refreshUri);
					loadActivePanel(refreshUri);
				}
			}
		}
	});
}

/**
 * 弹出窗口提交数据
 * @param uri
 * @param param
 * @param fun
 * @param $this
 * @param $form
 * @param isObj $this和$form传进来的参数是否为对象;默认为true
 */
cnoj.submitDialogData = function(uri,param,fun,$this,$form,isObj) {
	if(!utils.isEmpty(uri)) {
	    utils.waitLoading("正在提交数据...");
	    $.post(uri,param,function(data){
	    	utils.closeWaitLoading();
	    	var output = data;//$.parseJSON(data.output);
			utils.showMsg(output.msg+"！");
			var jqGridId = null;
			if(typeof($this) !== 'string') {
				jqGridId = $this.data("jqgrid-id");
			}
			if(output.result == '1') {
				if(!utils.isEmpty(fun) && typeof(eval(fun)) === 'function') {
					fun = eval(fun);
					fun();
				} else if(!utils.isEmpty(jqGridId)){
					$(jqGridId).trigger("reloadGrid",[{current:true}]);
				} else {
					if(utils.isEmpty(isObj)) 
						isObj = true;
					else
						isObj = (isObj==true)?true:false;
					var refreshUri = '';
					var target = '';
					if(!isObj) {
						refreshUri = $this;
						target = $form;
					} else {
						refreshUri = $this.data("refresh-uri");
						target = $form.attr("target");
					}
					
					if(!utils.isEmpty(refreshUri)) {
						if(!utils.isEmpty(target) && mainTag != target) {
							loadUri(target, refreshUri, true);
						} else {
							//loadLocation(refreshUri);
							loadActivePanel(refreshUri);
						}
					}
				}
				BootstrapDialogUtil.close();
			}
			data = null;
	    });
    } else {
    	utils.showMsg("提交URL未指定！");
    }
};

/**
 * 提交form表单数据（含有附件时）
 * @param uri
 * @param param
 * @param fun
 * @param $this
 * @param $form
 */
cnoj.submitFormDialogData = function(uri,param,fun,$this,$form) {
	var iframeName = null;
	iframeName = $form.parent().find("iframe").attr("id");
	if(utils.isEmpty(iframeName)) {
		iframeName = "hide_iframe_"+utils.UUID();
		$form.after("<iframe class='hide' name='"+iframeName+"' id='"+iframeName+"'></iframe>");
	}
	$form.attr("target", iframeName);
	if(!utils.isEmpty(uri)) {
		$form.submit();
	    utils.waitLoading("正在提交数据...");
	    $("#"+iframeName).load(function(){
	    	utils.closeWaitLoading();
	    	var result = $(this).contents().text();
	    	if(utils.isNotEmpty(result)) {
	    		var output = $.parseJSON(result);
	    		utils.showMsg(output.msg);
	    		var jqGridId = null;
				if(typeof($this) !== 'string') {
					jqGridId = $this.data("jqgrid-id");
				}
	    		if(output.result == '1') {
	    			if(!utils.isEmpty(fun) && typeof(eval(fun)) === 'function') {
						fun = eval(fun);
						fun();
					} else if(!utils.isEmpty(jqGridId)){
						$(jqGridId).trigger("reloadGrid",[{current:true}]);
					} else {
						var refreshUri = $this.data("refresh-uri");
						var	target = $form.data("show-target");
						if(!utils.isEmpty(refreshUri)) {
							if(!utils.isEmpty(target) && mainTag != target) {
								loadUri(target, refreshUri, true);
							} else {
								loadActivePanel(refreshUri);
							}
						}
					}
					BootstrapDialogUtil.close();
	    		}
	    	}
	    });
    } else {
    	utils.showMsg("提交URL未指定！");
    }
};


/**
 * 选择触发事件
 * @param type
 * @param uri
 * @param target
 */
cnoj.selectedEvent = function(type,uri,target) {
	if(!utils.isEmpty(type)) {
		switch (type) {
		case 'open_to_target':
			if(!utils.isEmpty(uri)) {
				if(!utils.isEmpty(target) && mainTag != target) {
					loadUri(target, uri, true, true);
				} else {
					//loadLocation(uri);
					loadActivePanel(uri);
				}
			}
			break;
		default:
			break;
		}
	}
}

/**
 * 获取复选框的值
 * @param divTag 标识,如class或ID值,也可以是一个对象
 * @param returnType 返回值类型 0--数组,1--字符串(多个值直接用逗号分隔开);默认返回数组
 */
cnoj.getCheckboxValues = function(divTag,returnType) {
	var $obj = null;
	var values = null;
	if(!utils.isEmpty(divTag)) {
		$obj = (typeof(divTag)==='string'?$(divTag):divTag);
		var isArray = (!utils.isEmpty(returnType) && returnType=='1')?false:true;
		values = isArray?(new Array()):'';
		$obj.find("input[type='checkbox']:checked").each(function(){
			var value = $(this).val();
			if(!utils.isEmpty(value)) {
				isArray?(values.push(value)):(values += value+",");
			}
		});
		if(!isArray && values != '') {
			values = values.substring(0,values.length-1);
		}
	}
	return values;
}


/**
 * 检测权限
 * @param uri
 * @param authId
 * @returns {Boolean}
 */
cnoj.checkAuth = function(uri,authId) {
	var is = false;
	if(!utils.isEmpty(uri) && !utils.isEmpty(authId)) {
		var url = "auth/checkAuth.json?uri="+encodeURIComponent(uri)+"&authId="+authId;
		$.ajax({
			url:url,
			type:"GET",
			async:false,
			dataType:"json",
			timeout:1000*10,
			success:function(data) {
				var output = data;//$.parseJSON(data.output);
				if(output.result=='1') {
					is = true;
				}
			}
		});
	}
	return is;
}

/**
 * 判断是否登录
 * @param success 已登录的回调函数
 * @param fail  未登录的回调函数;当该值为null或未定义时，默认刷新页面
 * 
 */
cnoj.isLogin = function(success,fail) {
	if(!utils.isEmpty(success) && typeof(success) === 'function') {
		//判断是否登录
		$.get("user/islogin.json",function(data){
			if(data.result=='1') {
				//登录成功后，执行
				success();
			} else {
				//未登录，执行
				if(!utils.isEmpty(fail) && typeof(success) === 'function') {
					fail();
				} else {
					location.reload();
				}
			}
		});
	}
}
