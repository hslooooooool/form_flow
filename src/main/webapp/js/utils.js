/**
 * @author lmq
 * 公共方法
 */

var utils = function(){}
/**
 * 设置是否为iframe；默认为:false
 */
utils.isIframe = false;
/**
 * 是否定义
 */
utils.YES_OR_NO = {
		/**
		 * 1--是
		 */
		YES: 1,
		/**
		 * 0--否
		 */
		NO: 0
};
/**
 * 获取文件后缀
 * @param fileName
 * @returns {String}
 */
utils.getFileSuffix = function(fileName) {
	var suffix = "";
	if(null != fileName && fileName != '' && fileName != undefined) {
		var suffixP = fileName.lastIndexOf(".");
		suffix = fileName.substring(suffixP+1,fileName.length).toLowerCase();
	}
	return suffix;
}

/**
 * 获取class中含有containValue指定字符串的class
 * @param value
 * @param containValue
 * @returns {String}
 */
utils.getClassContain = function(value,containValue) {
	var className = null;
	if(!this.isEmpty(value)) {
		var values = value.split(" ");
		if(values.length>0) {
			for ( var i = 0; i < values.length; i++) {
				if(this.isContain(values[i], containValue)) {
					className = this.trim(values[i]);
					break;
				}
			}
		}
	}
	return className;
}

/**
 * 判断是否包含字符串
 * @param srcValue
 * @param destValue
 * @returns {Boolean}
 */
utils.isContain = function(srcValue,destValue) {
	if(this.isEmpty(srcValue)) {
		return false;
	}
	var i = srcValue.indexOf(destValue);
	return (i==-1)?false:true;
}



/**
 * 判断是否为图片格式(gif,jpg,png)
 * @param fileName
 * @returns {Boolean}
 */
utils.isImageFormat = function(fileName) {
	var is = false;
	var suffix = this.getFileSuffix(fileName);
	if(suffix == 'gif' || suffix=='jpg' || suffix=='png') {
		is = true;
	}
	return is;
}

/**
 * 判断是否为空
 * @param value
 * @returns {Boolean}
 */
utils.isEmpty = function(value) {
	return (typeof(value) == 'undefined' || null == value 
			|| this.trim(value.toString()) == '');
}

/**
 * 判断是否不为空
 * @param value
 * @returns {Boolean}
 */
utils.isNotEmpty = function(value) {
	return !this.isEmpty(value);
}

/**
 * 判断元素是否存在
 * @param $element
 * @returns {Boolean}
 */
utils.isExist = function($element) {
	return (null != $element && typeof $element != undefined && $element.length>0);
}

/**
 * 转换null为""
 * @param str
 * @returns
 */
utils.handleNull = function(str) {
	if(typeof(str) == 'undefined' || null == str || 'null' == str) {
		return "";
	}
	return str;
}

/**
 * 判断是否为数组
 * @param obj
 * @returns {Boolean}
 */
utils.isArray = function(obj) {
	var is = false;
	if(typeof(obj) !== 'undefined' && null != obj && obj != '' 
		&& Object.prototype.toString.call(obj) === '[object Array]') {
		is = true;
	}
	return is;
}

/**
 * 解析uri,如果解析成功返回结果为一个数组
 * 第一个值为：uri
 * 第二个值为：参数
 * @param uri
 */
utils.parseUri = function(uri) {
	var array = null;
	if(!this.isEmpty(uri)) {
		var params = '';
		if(this.isContain(uri, "?")) {
			var index = uri.indexOf("?");
			params = uri.substring(index+1,uri.length);
			uri = uri.substring(0,index);
			var np = '';
			if(this.isContain(params, "#")) {
				index = params.indexOf("#");
				np = params.substring(index, params.length);
				params = params.substring(0, index);
			}
			uri = uri+np;
		}
		array = [uri,params];
	}
	return array;
}

/**
 * 去左空格
 * @param str
 * @returns {String}
 */
utils.ltrim = function(str) {
	if(typeof(str) === 'string') {
		str=str.replace( /^\s*/, "");
	}
	return str;
}

/**
 * 去右空格
 * @param str
 * @returns {String}
 */
utils.rtrim = function(str) {
	if(typeof(str) === 'string') {
	   str=str.replace(/(\s*$)/g, "");
	}
	return str;
}

/**
 * 替换字符串
 * @param str
 * @param reg1
 * @param reg2
 * @returns
 */
utils.replaceAll = function(str,reg1,reg2) {
	var reg = new RegExp(reg1,"g");
	str = str.replace(reg,reg2);
	return str;
}

/**
 * 去空格
 * @param str
 * @returns {String}
 */
utils.trim = function(str) {
	if(typeof(str) === 'string') {
	   str = str.replace(/\s+/g,"");
	}
	return str;
}

/**
 * 验证长度
 * @param value
 * @param lenStr
 * @returns {Boolean}
 */
utils.checkLen = function(value,lenStr) {
	var is = false;
	if(this.regexNum(lenStr)) {
		if(value.length==parseInt(lenStr)) {
			is = true;
		}
	} else if(lenStr.indexOf(",")>0) {
		var lens= lenStr.split(",");
		if(lens.length==2 && this.regexNum(lens[0]) && this.regexNum(lens[1])) {
			if(value.length >= parseInt(lens[0]) && 
					value.length <= parseInt(lens[1])) {
				is = true;
			}
		}
	} else if(lenStr.indexOf("|")>0) {
		var lens = lenStr.split("|");
		for(var i=0;i<lens.length;i++) {
			if(this.regexNum(lens[i]) && (value.length == parseInt(lens[i]))) {
				is = true;
				break;
			}
		}
	}
	return is;
}


/**
 * 匹配IP地址
 * @param ip
 * @returns {Boolean}
 */
utils.regexIp = function(ip) {
	var regex = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
	if(RegExp(regex).test(ip)){
		if (RegExp.$1 <= 255 && RegExp.$1 > 0 
				&& RegExp.$2 <= 255 && RegExp.$2 >= 0 
				&& RegExp.$3 <= 255 && RegExp.$3 >= 0 
				&& RegExp.$4 <= 255 && RegExp.$4 > 0
				) {
			return true;
		}
	}
	return false;
}

/**
 * 匹配数字（整数和小数）
 * @param num
 * @returns {Boolean}
 */
utils.regexNum = function(num) {
	var regex = /^\d+$|^\d+\.\d+$/;
	if(regex.test(num)) {
		return true;
	}
	return false;
}

/**
 * 匹配小数
 * 
 * @param num
 * @returns {Boolean}
 */
utils.regexDecimal = function(num) {
	var regex = /^\d+\.\d+$/;
	if(regex.test(num)) {
		return true;
	}
	return false;
}

/**
 * 匹配整数
 * @param num
 * @returns {Boolean}
 */
utils.regexInteger = function(num) {
	var regex = /^\d+$/;
	if(regex.test(num)) {
		return true;
	}
	return false;
}

/**
 * 验证是否匹配表达式
 * @param regexp
 * @param value
 */
utils.checkRegexp = function(regexp,value) {
	var is = false;
	var regex = eval(regexp);
	if(regex.test(value)) {
		is = true;
	}
	return is;
} 

/**
 * 验证汉字
 * @param value
 * @returns {Boolean}
 */
utils.checkChinese = function(value) {
	var regex = /^[\u4E00-\u9FA5]+$/gi;
	if(regex.test(value)) {
		return true;
	}
	return false;
}

/**
 * 验证Email地址
 * @param value
 * @returns {Boolean}
 */
utils.checkEmail = function(value){
	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(reg.test(value)){
		return true;
	}else{
		return false;
	}
}

/**
 * 验证邮编
 * @param value
 * @returns {Boolean}
 */
utils.checkCode = function(value) {
	var reg = /^[a-z|A-Z|0-9]{6}$/;
	if(reg.test(value)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 验证短信验证码
 * @param value
 * @returns {Boolean}
 */
utils.checkSMSCode = function(value) {
	var reg = /^[0-9]{6}$/;
	if(reg.test(value)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 验证手机号码
 * @param value
 * @returns {Boolean}
 */
utils.checkPhoneNo = function(value) {
    var reg = /(^0{0,1}1[3|5|8][0-9]{9}$)/;
    if (!reg.test(value)) {
        return false;
    }
    return true;
}

/**
 * 验证固定电话号码
 * @param value
 * @returns {Boolean}
 */
utils.checkTelephone = function(value) {
	var regex = /^[0][0-9]{2,3}\-[2-9][0-9]{6,7}(\-[0-9]{1,4})?$/;
	if(regex.test(value)) {
		return true;
	}
	return false;
}

/**
 * 验证QQ号
 * @param value
 * @returns {Boolean}
 */
utils.checkQQ = function(value) {
    var reg = /^[1-9]\d{4,11}$/;
    if (!reg.test(value)) {
        return false;
    }
    return true;
}

/**
 * 身份证号码验证 <br />
 * 需要引入check-card-no.js文件
 * @param value
 * @returns {Boolean}
 */
utils.checkCardNo = function(value) {
	return checkCard(value);
}

/**
 * 验证日期格式：yyyy-MM-dd
 * @param value
 * @returns {Boolean}
 */
utils.checkDate = function(value) {
	 var is = false;
	 var reg = /^[1-2][0-9]{3}-[0-1][0-9]-[0-3][0-9]$/;
	 var zhReg = /^[1-2][0-9]{3}年[0-1][0-9]月[0-3][0-9]日$/;
	 var zhRegYM = /^[1-2][0-9]{3}年[0-1][0-9]月$/;
	 if(!this.isEmpty(value)){
		 is = reg.test(value) || zhReg.test(value) || zhRegYM.test(value);
	 }
	 return is;
}

/**
 * 验证时间格式：HH:mm:ss
 * @param value
 * @returns {Boolean}
 */
utils.checkTime = function(value) {
	 var is = false;
	 var regFull = /^[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$/;
	 var regHM = /^[0-2][0-9]:[0-5][0-9]$/;
	 var zhRegFull = /^[0-2][0-9]时[0-5][0-9]分[0-5][0-9]秒$/;
	 var zhRegHM = /^[0-2][0-9]时[0-5][0-9]分$/;
	 
	 if(utils.isNotEmpty(value)){
		 is = regFull.test(value) || regHM.test(value) || zhRegFull.test(value) || zhRegHM.test(value);
	 }
	 return is;
}

/**
 * 验证日期时间格式：yyyy-MM-ss HH:mm:ss
 * @param value
 * @returns {Boolean}
 */
utils.checkDateTime = function(value) {
	 var is = false;
	 var regFull = /^[1-2][0-9]{3}-[0-1][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]$/;
	 var regHM = /^[1-2][0-9]{3}-[0-1][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]$/;
	 
	 var zhRegFull = /^[1-2][0-9]{3}年[0-1][0-9]月[0-3][0-9]日[0-2][0-9]时[0-5][0-9]分[0-5][0-9]秒$/;
	 var zhRegHM = /^[1-2][0-9]{3}年[0-1][0-9]月[0-3][0-9]日[0-2][0-9]时[0-5][0-9]分$/;
	 
	 if(utils.isNotEmpty(value)){
		 is = regFull.test(value) || regHM.test(value) || zhRegFull.test(value) || zhRegHM.test(value);
	 }
	 return is;
}

/**
 * 验证匿名
 * @param value
 * @returns {Boolean}
 */
utils.checkAnonymous = function(value) {
	var reg = /^[\u4e00-\u9fa5|A-Za-z]([\w|\u4e00-\u9fa5]){1,7}$/;
    if (!reg.test(value)) {
        return false;
    }
    return true;
}

/**
 * 生成一个唯一的key
 * 生成序列号
 * @returns {String}
 */
utils.generateUniqueKey = function() {
	var len = 10;
	var time = new Date().getTime();
	var random = this.randomNum(len);
	if(random.length>len) {
		random = random.substring(0, len);
	}
	return time+''+random;
}

/**
 * 生成一个n位的随机数
 * @param n
 * @returns {String}
 */
utils.randomNum = function(n) {
	if(!this.regexNum(n)) {
		n = 6;
	}
	var num = '';
	for ( var i = 0; i < n; i++) {
		num += Math.round(Math.random()*10)+'';
	}
	return num;
}

/**
 * 显示遮盖层
 * @param msg
 */
utils.showShadow = function() {
	if(typeof($("#shadow").attr("id")) === 'undefined') {
		$("body").append("<div id='shadow'></div>");
	}
	$("#shadow").show();
}

/**
 * 关闭遮盖层
 * @param msg
 */
utils.closeShadow = function() {
	$("#shadow").hide();
}

/**
 * 显示信息
 * @param msg
 */
utils.showMsg = function(msg) {
	if($("#alert-msg").attr("id") == undefined) {
		$("body").append("<div id='alert-msg'>"+msg+"</div>");
	}
	var w = 250; 
	var h = 60;
	$("#alert-msg").html(msg);
	var top = $(document).scrollTop(); 
	var sw = ($(window).width()-w)/2;
	var sh = ($(window).height()-h)/2+top;
	var index = $("#shadow").css("z-index")/1+1/1;
	if(isNaN(index)) {
		index = 9999;
	}
	$("#alert-msg").css({"top":sh+"px","left":sw+"px","width":w+"px","height":h+"px","display":"block","z-index":index});
	$("#alert-msg").fadeOut(3000);
}

/**
 * 加载数据
 * @param msg
 */
utils.waitLoading = function(msg) {
	if($("#wait-msg").attr("id") == undefined) {
		$("body").append("<div id='wait-msg'><div class='msg-body'>"+msg+"</div></div>");
	}
	this.showShadow();
	var w = 250; 
	var h = 60;
	$("#wait-msg .msg-body").html(msg);
	var top = $(document).scrollTop();
	var index = $("#shadow").css("z-index")/1+1/1;
	var sw = ($(window).width()-w)/2;
	var sh = ($(window).height()-h)/2+top;
	$("#wait-msg").css({"top":sh+"px","left":sw+"px","width":w+"px","height":h+"px","display":"block","z-index":index});
}

/**
 * 关闭等待(加载)窗口
 */
utils.closeWaitLoading = function() {
	$("#wait-msg").hide();
	this.closeShadow();
}

/**
 * 下拉框
 * @param id
 * @param datas
 * @param defaultValue
 * @param callback
 */
utils.selectDataItem = function(id,datas,defaultValue,callback) {
	if(!this.isEmpty(datas) && datas.length>0) {
		defaultValue = utils.handleNull(defaultValue);
		var options = '';
		var selectValue = '';
		for(var i=0;i<datas.length;i++) {
			if(datas[i][0] == defaultValue) {
				options +='<option value="'+datas[i][0]+'" class="cnoj-dyn-opt" selected>'+datas[i][1]+'</option>';
				selectValue = datas[i][0];
			} else {
				options +='<option value="'+datas[i][0]+'" class="cnoj-dyn-opt" >'+datas[i][1]+'</option>';
			}
		}
		if(this.isEmpty(selectValue)) {
			selectValue = datas[0][0];
		}
		$(id).find(".cnoj-dyn-opt").remove();
		$(id).append(options);
		if(typeof(callback) === 'function') {
			callback(selectValue);
		}
	} else {
		$(id).find(".cnoj-dyn-opt").remove();
	}
}


/**
 * 下拉框
 * @param id
 * @param uri
 * @param defaultValue
 * @param callback
 */
utils.selectItem = function(id,uri,defaultValue,callback) {
	if(!this.isEmpty(uri)) {
		defaultValue = utils.handleNull(defaultValue);
		var $select = (id instanceof jQuery) ? id:$(id);
		$.get(uri,function(data){
			var output = data;//$.parseJSON(data.output);
			if(output.result=='1') {
				var datas = output.datas; 
				if(datas.length>0) {
					var options = '';
					var selectValue = '';
					var selected = '';
					for(var i=0;i<datas.length;i++) {
						selected = '';
						if(datas[i][0] == defaultValue) {
							selected = 'selected="selected"';
							selectValue = datas[i][0];
						}
						options +='<option value="'+datas[i][0]+'" '+selected+' class="cnoj-dyn-opt" >'+datas[i][1]+'</option>';
					}
					$select.find(".cnoj-dyn-opt").remove();
					$select.append(options);
					if(utils.isEmpty(selectValue)) {
						selectValue = $select.children().eq(0).val();
					}
					if(typeof(callback) === 'function') {
						callback(selectValue);
					}
				}
			} else {
				$select.val("");
				$select.find(".cnoj-dyn-opt").remove();
				//showMsg(output.msg);
			}
		});
	}
}

/**
 * 复选框
 * @param id
 * @param uri
 * @param defaultValue
 * @param name
 * @param isH 是否横排
 * @param require
 * @param callback
 */
utils.checkboxItem = function(id,uri,defaultValue,name,isH,require,callback) {
	if(!this.isEmpty(uri)) {
		var defaultValueArray = new Array();
		defaultValue = utils.handleNull(defaultValue);
		if(typeof(defaultValue) == 'string' && defaultValue.indexOf(",")>-1) {
			defaultValueArray = defaultValue.split(",");
		} else {
			defaultValueArray.push(defaultValue);
		}
		if(typeof(isH) == 'undefined' || utils.isEmpty(isH)) {
			isH = true;
		}
		var $wrap = (id instanceof jQuery) ? id:$(id);
		$.get(uri,function(data){
			var output = data;//$.parseJSON(data.output);
			if(output.result=='1') {
				var datas = output.datas; 
				if(datas.length>0) {
					var options = '';
					var checkedValue = defaultValueArray;
					var labelCss = "";
					if(isH) {
						labelCss = "checkbox-inline";
					}
					var startWrap = '';
					var endWrap = '';
					if(!isH) {
						startWrap = '<div class="checkbox">';
						endWrap = '</div>';
					}
					var requireStr = utils.handleNull(require);
					var isDefault = false;
					for(var i=0;i<datas.length;i++) {
						isDefault = false;
						for(var j=0,len=defaultValueArray.length;j<len;j++) {
							if(datas[i][0] == defaultValueArray[j]) {
								isDefault = true;
								break;
							}
						}//for
						if(isDefault) {
							options += startWrap+'<label class="text-normal '+labelCss+'"><input class="'+requireStr+'" type="checkbox" id="'+name+'_'+datas[i][0]+'" value="'+datas[i][0]+'" name="'+name+'" class="cnoj-dyn-checkbox" checked="checked" /> '+datas[i][1]+'</label>'+
							'&nbsp;&nbsp;'+endWrap;
						} else {
							options += startWrap+'<label class="text-normal '+labelCss+'"><input class="'+requireStr+'" type="checkbox" id="'+name+'_'+datas[i][0]+'" value="'+datas[i][0]+'" name="'+name+'" class="cnoj-dyn-checkbox" /> '+datas[i][1]+'</label>';
							if(i<datas.length-1) {
								options += "&nbsp;&nbsp;";
							}
							options += endWrap;
						}
					}//for
					if(checkedValue.length == 0) {
						checkedValue.push($wrap.children().eq(0).val());
					}
					$wrap.find(".cnoj-dyn-checkbox").remove();
					$wrap.append(options);
					if(typeof(callback) == 'function') {
						callback(checkedValue);
					}
				}//if
			}//if
		});
	}
}

/**
 * 单选框
 * @param id
 * @param uri
 * @param defaultValue
 * @param isH 是否横排
 * @param require
 * @param callback
 */
utils.radioItem = function(id,uri,defaultValue,name,isH,require,callback) {
	if(!this.isEmpty(uri)) {
		var defaultValueArray = new Array();
		defaultValue = utils.handleNull(defaultValue);
		console.log(defaultValue);
		console.log(typeof(defaultValue));
		if(typeof(defaultValue) == 'string' && defaultValue.indexOf(",")>-1) {
			defaultValueArray = defaultValue.split(",");
		} else {
			defaultValueArray.push(defaultValue);
		}
		var $wrap = (id instanceof jQuery) ? id:$(id);
		$.get(uri,function(data){
			var output = data;//$.parseJSON(data.output);
			if(output.result=='1') {
				var datas = output.datas; 
				if(datas.length>0) {
					var options = '';
					var checkedValue = defaultValueArray;
					var labelCss = "";
					if(isH) {
						labelCss = "radio-inline";
					}
					var startWrap = '';
					var endWrap = '';
					if(!isH) {
						startWrap = '<div class="radio">';
						endWrap = '</div>';
					}
					var requireStr = utils.handleNull(require);
					var isDefault = false;
					for(var i=0;i<datas.length;i++) {
						isDefault = false;
						for(var j=0,len=defaultValueArray.length;j<len;j++) {
							if(datas[i][0] == defaultValueArray[j]) {
								isDefault = true;
								break;
							}
						}//for
						if(isDefault) {
							options += startWrap+'<label class="text-normal '+labelCss+'"><input type="radio" class="'+requireStr+'" value="'+datas[i][0]+'" name="'+name+'" id="'+name+'_'+datas[i][0]+'" class="cnoj-dyn-radio" checked="checked" /> '+datas[i][1]+'</label>'+endWrap;
							break;
						} else {
							options += startWrap+'<label class="text-normal '+labelCss+'"><input type="radio" class="'+requireStr+'" value="'+datas[i][0]+'" name="'+name+'" id="'+name+'_'+datas[i][0]+'" class="cnoj-dyn-radio" /> '+datas[i][1]+'</label>';
							options += endWrap;
						}
					}
					if(checkedValue.length == 0) {
						checkedValue[0] = $wrap.children().eq(0).val();
					}
					$wrap.find(".cnoj-dyn-radio").remove();
					$wrap.append(options);
					if(typeof(callback) == 'function') {
						callback(checkedValue);
					}
				}//if
			}//if
		});
	}
}

/**
 * 级联下拉框
 * @param id
 * @param cascadeId
 * @param uri
 * @param paramName
 * @param defaultValue
 * @param changeId 
 * @returns {Boolean}
 */
utils.selectCascadeItem = function(id,cascadeId,uri,paramName,defaultValue,changeId) {
	if(utils.isNotEmpty(uri)) {
		defaultValue = utils.handleNull(defaultValue);
		if(utils.isEmpty(cascadeId)) {
			return false;
		}
		paramName = utils.isEmpty(paramName)?"id":paramName;
		var value = $(cascadeId).val();
		if(uri.indexOf("?")>0) 
			uri = uri +"&";
		else 
			uri = uri+"?";
		if(uri.startWith("op/query/")) {
			uri = uri+"paramName="+paramName+"&paramValue=";
		} else {
			uri = uri+paramName+"=";
		}
		if(utils.isEmpty(value)) {
			value = $(cascadeId).data("default-value");
		}
		if(utils.isNotEmpty(value)) {
			this.selectItem(id, uri+value, defaultValue);
		}
		$(cascadeId).change(function(){
			if(utils.isNotEmpty(changeId)) {
			    var $elementTag = $(changeId);
	            if($elementTag.length == 0) {
	                value = $(this).next().val();
	            } else {
	                value = $elementTag.val();
	            }
			} else {
			    value = $(this).val();
			}
			//if(null != value && typeof(value) != undefined) 
			utils.selectItem(id, uri+value, defaultValue);
		});
	}
}

/**
 * 检查文件上传路径是否为空
 * @param id
 * @returns {Boolean}
 */
utils.uploadNull = function(id){
	id = "#"+id;
	var path = $(id).val();
	if(path==""||path==null){
		cu.info("请选择上传路径！");
		return false ;
	}else{
		return true;
	}
}

/**
 * 日期格式转换 X年X月X日
 * @param time  时间
 * @param txg 分隔符
 * @param hz 汉字
 * @returns {String}
 */
utils.dateFormat = function(time,txg,hz){
	var arr = new Array();
	var returnTime = "";
	arr = time.split(txg);
	if(arr.length>=3){
		returnTime +=arr[0]+"年";
		if(hz){
			var m = arr[1];
			var d = arr[2];
			if(m.length=2&&(m.charAt(0)=="0"||m.charAt(0)=="〇")){
				returnTime +=m.charAt(1)+"月";
			}else{
				returnTime +=m.charAt(0)+"十"+m.charAt(1)+"月";
			}
			if(d.length=2&&(d.charAt(0)=="0"||d.charAt(0)=="〇")){
				returnTime +=d.charAt(1)+"月";
			}else{
				returnTime +=d.charAt(0)+"十"+d.charAt(1)+"日";
			}
			
		}else{
			returnTime +=arr[1]+"月";
			returnTime +=arr[2]+"日";
		}
		return returnTime;
	}else{
		return "-1";
	}
}

/**
 * 阿拉伯数字转换中文数字 
 * @param str
 * @returns {String}
 */
utils.sumToStr = function(str){
	var sum = ["0","1","2","3","4","5","6","7","8","9"];
	var sumStr = ["〇","一","二","三","四","五","六","七","八","九"];
	var retrunStr = "";
	 for (var i=0;i<str.length;i++){
		  var ch = str.charAt(i);
		  for(var j=0;j<sum.length;j++){
			  if(ch==sum[j])
				  ch = sumStr[j];
		  }
		  retrunStr +=ch; 
	 }
	 return retrunStr;
}

/**
 * 阿拉伯数字转换中文数字 
 * @param str
 * @returns {String}
 */
utils.getChinaNum = function(num){
	var values = ["一","二","三","四","五","六","七","八","九"];
	var num = parseInt(num);
	if(num>-1 && num < 10) {
		return values[num];
	} else {
		return num+'';
	}
}

/**
 * 判断是否有滚动条
 * @param tag
 * @returns {Boolean}
 */
utils.isScroll = function(tag) {
	var is = false;
	if(!this.isEmpty(tag)) {
		var $tag = null;
		if(typeof(tag) === 'string')
			$tag = $(tag);
		else 
			$tag = tag;
		var height = $tag.height();
		var tableHeight = $tag.find("table").height();
		if(tableHeight>height) {
			is = true;
		}
	}
	return is;
}

/**
 * 获取浏览器竖向滚动条宽度
 * 首先创建一个用户不可见、无滚动条的DIV，获取DIV宽度后，
 * 再将DIV的Y轴滚动条设置为永远可见，再获取此时的DIV宽度
 * 删除DIV后返回前后宽度的差值
 * @return {Number} 竖向滚动条宽度
 */
utils.getScrollWidth = function() {
	  var noScroll, scroll, oDiv = document.createElement("DIV");
	  oDiv.style.cssText = "position:absolute; top:-1000px; width:100px; height:100px; overflow:hidden;";
	  noScroll = document.body.appendChild(oDiv).clientWidth;
	  oDiv.style.overflowY = "scroll";
	  scroll = oDiv.clientWidth;
	  document.body.removeChild(oDiv);
	  return noScroll-scroll;
}

/**
 * 
 * @param time
 * @param formate <br/>
 * 年---yyyy <br/>
 * 月---MM <br/>
 * 日---dd <br/>
 * 小时---HH <br/>
 * 分钟---mm <br/>
 * 秒---ss
 * @returns {String}
 */
utils.handleTime = function(time,formate) {
	var year = null;
	var month = null;
	var day = null;
	var hours = null;
	var minutes = null;
	var seconds = null;
	if(typeof(time) === 'number') {
		   var date = new Date();
		   date.setTime(time);
		   year = date.getFullYear();
		   month = date.getMonth()+1;
		   month = month<10?'0'+month:month;
		   day = date.getDate();
		   day = day<10?'0'+day:day;
		   hours = date.getHours();
		   hours = hours<10?'0'+hours:hours;
		   minutes = date.getMinutes();
		   minutes = minutes<10?'0'+minutes:minutes;
		   seconds = date.getSeconds();
		   seconds = seconds<10?'0'+seconds:seconds;
	 } else if(typeof(time) === 'string'){
		   var array = time.split(" ");
		   var arrayDate = array[0].split("-");
		   var arrayTime = array[1].split(":");
		   year = arrayDate[0];
		   month = arrayDate[1];
		   day = arrayDate[2];
		   hours = arrayTime[0];
		   minutes = arrayTime[1];
		   seconds = arrayTime[2];
	 } else if(typeof(time) === 'object'){
		  var date = time;
		   year = date.getFullYear();
		   month = date.getMonth()+1;
		   month = month<10?'0'+month:month;
		   day = date.getDate();
		   day = day<10?'0'+day:day;
		   hours = date.getHours();
		   hours = hours<10?'0'+hours:hours;
		   minutes = date.getMinutes();
		   minutes = minutes<10?'0'+minutes:minutes;
		   seconds = date.getSeconds();
		   seconds = seconds<10?'0'+seconds:seconds;
	} else {
		  var date = new Date();
		   year = date.getFullYear();
		   month = date.getMonth()+1;
		   month = month<10?'0'+month:month;
		   day = date.getDate();
		   day = day<10?'0'+day:day;
		   hours = date.getHours();
		   hours = hours<10?'0'+hours:hours;
		   minutes = date.getMinutes();
		   minutes = minutes<10?'0'+minutes:minutes;
		   seconds = date.getSeconds();
		   seconds = seconds<10?'0'+seconds:seconds;
	}
	if(this.isEmpty(formate)) {
		  return year+"年"+month+"月"+day+"日"+hours+"时"+minutes+"分"+seconds+"秒";
	} else {
		  var value = formate;
		  var dateTimeArray = [year,month,day,hours,minutes,seconds];
		  var formaterArray = ['yyyy','MM','dd','HH','mm','ss'];
		  for ( var i = 0; i < dateTimeArray.length; i++) {
			  value = value.replace(formaterArray[i],dateTimeArray[i]);
	       }
	   return value;
	 }
	   
}

/**
 * 处理时间
 * @param time
 * @returns {String}
 */
utils.handleShortTime = function(time) {
	var year = null;
	var month = null;
	var day = null;
	var hours = null;
	var minutes = null;
	var seconds = null;
	if(typeof(time) === 'number') {
	   var date = new Date();
	   date.setTime(time);
	   year = date.getFullYear();
	   month = date.getMonth()+1;
	   month = month<10?'0'+month:month;
	   day = date.getDate();
	   day = day<10?'0'+day:day;
	   hours = date.getHours();
	   hours = hours<10?'0'+hours:hours;
	   minutes = date.getMinutes();
	   minutes = minutes<10?'0'+minutes:minutes;
	   seconds = date.getSeconds();
	   seconds = seconds<10?'0'+seconds:seconds;
	} else if(typeof(time) === 'string'){
		   var array = time.split(" ");
		   var arrayDate = array[0].split("-");
		   var arrayTime = array[1].split(":");
		   year = arrayDate[0];
		   month = arrayDate[1];
		   day = arrayDate[2];
		   hours = arrayTime[0];
		   minutes = arrayTime[1];
		   seconds = arrayTime[2];
	} else if(typeof(time) === 'object'){
		  var date = time;
		   year = date.getFullYear();
		   month = date.getMonth()+1;
		   month = month<10?'0'+month:month;
		   day = date.getDate();
		   day = day<10?'0'+day:day;
		   hours = date.getHours();
		   hours = hours<10?'0'+hours:hours;
		   minutes = date.getMinutes();
		   minutes = minutes<10?'0'+minutes:minutes;
		   seconds = date.getSeconds();
		   seconds = seconds<10?'0'+seconds:seconds;
	} else {
		  var date = new Date();
		   year = date.getFullYear();
		   month = date.getMonth()+1;
		   month = month<10?'0'+month:month;
		   day = date.getDate();
		   day = day<10?'0'+day:day;
		   hours = date.getHours();
		   hours = hours<10?'0'+hours:hours;
		   minutes = date.getMinutes();
		   minutes = minutes<10?'0'+minutes:minutes;
		   seconds = date.getSeconds();
		   seconds = seconds<10?'0'+seconds:seconds;
	}
	   return month+"-"+day+" "+hours+":"+minutes;
}

/**
 * 
 */
String.prototype.endWith = function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str)
	  return true;
	else
	  return false;
	return true;
}

/**
 * 
 */
String.prototype.startWith = function(str) {
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
	  return true;
	else
	  return false;
	return true;
}


/**
 * 载入URL（未使用该方法）
 * @param target 载入位置；可以是对象或id或class
 * @param url 载入地址
 * @param isLoadProcess 是否加载
 * @param isCheckLogin 检测是否登录
 */
utils.loadUrl = function(target, url, isLoadProcess, isCheckLogin) {
	if(this.isNotEmpty(target) && this.isNotEmpty(url)) {
		isLoadProcess = this.isEmpty(isLoadProcess)?false:isLoadProcess;
		isCheckLogin = this.isEmpty(isCheckLogin)?false:isCheckLogin;
		target = (typeof(target) == 'string')?$(target):target;
		if(isLoadProcess) {
			target.html('<div class="cnoj-loading"><i class="fa fa-spinner fa-spin fa-lg"></i> 正在加载，请稍候...</div>');
		}
		var isLoad = true;
		if(isCheckLogin) {
			isLoad = false;
			//判断是否登录
		}
		if(isLoad) {
			target.load(url, function() {
				target.find(".cnoj-loading").remove();
			});
		}
	}
}

/**
 * 载入URL到IFrame（未使用该方法）
 * @param target 载入位置；可以是对象或id或class
 * @param url 载入地址
 * @param isLoadProcess 是否加载
 * @param isCheckLogin 检测是否登录
 */
utils.loadUrl2IFrame = function(target, url, isLoadProcess, isCheckLogin) {
	if(this.isNotEmpty(target) && this.isNotEmpty(url)) {
		isLoadProcess = this.isEmpty(isLoadProcess)?false:isLoadProcess;
		isCheckLogin = this.isEmpty(isCheckLogin)?false:isCheckLogin;
		
		target = (typeof(target) == 'string')?$(target):target;
		if(isLoadProcess) {
			target.before('<div class="cnoj-loading"><i class="fa fa-spinner fa-spin fa-lg"></i> 正在加载，请稍候...</div>');
		}
		var isLoad = true;
		if(isCheckLogin) {
			isLoad = false;
			//判断是否登录
		}
		if(isLoad) 
			target.attr("src",url);
	}
}

/**
 * iframe高度自适应（未使用该方法）
 * @param target iframe对象或id或class
 */
utils.iframeAutoListner = function(target) {
	if(this.isNotEmpty(target)) {
		target = (typeof(target) == 'string')?$(target):target;
		target.load(function() {
			$(".cnoj-loading").remove();
			var mainHeight = $(this).contents().find("html").height();
			$(this).height(mainHeight);
		});
	}
}

/**
 * 生成UUID
 * @returns {String}
 */
utils.UUID = function() {
	var len = 32; //32长度
	var radix = 16; //16进制
	var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	var uuid = [], i;
	radix = radix || chars.length;
	if(len) {
		for(i=0; i<len; i++)
			uuid[i] = chars[0|Math.random()*radix];
	} else {
		  var r;
		  uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
		  uuid[14] = '4';
		  for(i=0; i<36; i++) {
			  if(!uuid[i]) { 
				  r = 0|Math.random()*16;
				  uuid[i] = chars[(i==19)?(r&0x3)|0x8:r];
			  }
		  }
	 }
	return uuid.join('');
}

/**
 * 处理表单打印标签
 * @param element
 */
utils.handleFormPrintLabel = function(element, $root) {
	var $element = null
	if(utils.isNotEmpty($root)) {
		var $element = ($root instanceof jQuery) ? ($root.find(element)) : ($($root).find(element));
	} else {
		$element = $(element);
	}
	$element.find("input[type=text],select,textarea").each(function(){
		var $obj = $(this);
		if(!$obj.hasClass("hidden")) {
			var value = $obj.val();
			if(utils.isNotEmpty(value)) {
				value = utils.replaceAll(value,'\n','<br />');
			}
			//判断父元素是否有“hidden-print”类元素
			var isParentHiddenPrint = false;
			var $parent = $obj.parent();
			if($parent.hasClass("hidden-print")) {
				isParentHiddenPrint = true;
			}
			if(isParentHiddenPrint) {
				$parent.next().html(value);
			} else {
				$obj.after("<span class='visible-print-inline'>"+value+"</span>");
			}
			if(!isParentHiddenPrint) {
				if($obj.hasClass("cnoj-richtext") && $obj.prev().hasClass("cnoj-richtext")) {
					$obj.prev().wrap("<span class='hidden-print'></span>");
				}
				$obj.wrap("<span class='hidden-print'></span>");
			}
		}
	});
}

/**
 * 查找前面的元素,返回第一个找到的值
 * @param obj
 * @param tag
 * @returns
 */
utils.findPrevTag = function(obj, tag) {
	if(utils.isEmpty(obj) || utils.isEmpty(tag)) {
		return null;
	}
	var is = true;
	var $prev = obj;
	var num = 0;
	var targetObj = null;
	while(is && num<10 && utils.isNotEmpty($prev)) {
		$prev = $prev.prev();
		if($prev.length == 0) {
			is = false;
			break;
		}
		if(utils.isNotEmpty($prev) && $prev.prop("tagName").toLowerCase() == tag || 
				$prev.hasClass(tag) || $prev.prop("id").toLowerCase() == tag) {
			is = false;
			targetObj = $prev;
			break;
		}
		num++;
	}
	return targetObj;
}

/**
 * 判断元素名称是否与指定的名称一致
 * @param $element
 * @param tagName
 * @returns {Boolean}
 */
utils.isTagName = function($element,tagName) {
	var is = false;
	if(utils.isNotEmpty($element) && utils.isNotEmpty(tagName)) {
		return ($element.prop("tagName").toLowerCase() == tagName);
	}
	return is;
}
/**
 * 关闭浏览器窗口
 */
utils.closeWebPage = function() {
	if (navigator.userAgent.indexOf("MSIE") > 0) {
		if (navigator.userAgent.indexOf("MSIE 6.0") > 0) { 
			window.opener = null;
			window.close();
		} else {
			window.open('', '_top');
			window.top.close();
		}
	} else if (navigator.userAgent.indexOf("Firefox") > 0) {
		window.location.href = 'about:blank ';
	} else {
		window.opener = null;
		window.open('', '_self', '');
		window.close();
	}
}

/**
 * 判断是否是IE浏览器
 * 是IE浏览器，则返回：true；否则返回：false
 */
utils.isIE = function() {
	if (!!window.ActiveXObject || "ActiveXObject" in window)
		return true;
	else
		return false;
}

/**
 * 处理URL；判断URL是否有参数，如果有，则URL后面会跟“&”符号；
 * 如果没有，则URL后加“?”符号
 * @param url URL地址
 */
utils.handleUrl = function(url) {
    if(utils.isEmpty(url)) {
        return url;
    } else {
        if(url.indexOf("?") > -1) {
            url += "&";
        } else {
            url += "?";
        }
        return url;
    }
}
