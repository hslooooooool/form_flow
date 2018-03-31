/**
 * @author lmq
 * 表单验证
 * @param $
 */
(function($){
	/**
	 * 表单验证
	 * placement属性包含：top,bottom,left,right,auto
	 */
	$.fn.validateForm = function(options) {
		var setting = {
				placement:'auto',
				callback:null,
				radioCheckboxPopoverCallback:null,
				emptyPopoverCallback:null,
				errorPopoverCallback:null
		};
		setting = $.extend(setting,options);
		$this = $(this);
		var result = true;
		var popoverTmp = null;
		var checkboxRadioName = null;
		var self = this;
		var $currentNode = null;
		var $availableElement = null;
		$this.find("input,select,textarea").each(function(){
			$currentNode = $(this);
			if($currentNode.prop("readonly") || $currentNode.prop("disabled") || $currentNode.hasClass("hide")) {
				return true;
		    }
			if(result) {
				var isRequire = $currentNode.hasClass("require");
				var tagName = $currentNode.prop("tagName").toLowerCase();
				//验证checkbox及radio必填项
				if(isRequire && tagName == 'input' 
					&& !$currentNode.prop("disabled") 
					&& ($currentNode.attr("type") == 'radio' || $currentNode.attr("type") == 'checkbox')) {
					$currentNode.attr("title","");
					$currentNode.attr("data-original-title","");
					var name = $currentNode.attr("name");
					var inputType = $currentNode.attr("type");
					if(name != checkboxRadioName) {
						var $parent = null;
						if(inputType == 'radio') {
							$parent = $currentNode.parents(".radio-parent:eq(0)");
						} else if(inputType == 'checkbox') {
							$parent = $currentNode.parents(".checkbox-parent:eq(0)");
						}
						var isCheck = false;
						$parent.find("input[name="+name+"]").each(function(){
							if($(this).prop("checked")) {
								isCheck = true;
								return true; //跳出each
							}
						});
						checkboxRadioName = name;
						if(!isCheck) {
							var labelName = $parent.data("label-name");
							labelName = utils.handleNull(labelName);
							if(utils.isNotEmpty(setting.radioCheckboxPopoverCallback) && typeof(setting.radioCheckboxPopoverCallback) == 'function') {
								var resultObj = setting.radioCheckboxPopoverCallback(this,labelName, setting, self);
								result = resultObj.result;
								popoverTmp = resultObj.popoverObj;
								$availableElement = resultObj.element;
							} else {
								$parent.popover({
									placement:setting.placement,
									content:labelName+"不能为空!",
									trigger:"manual"});
								popoverTmp = $parent;
								$availableElement = $parent;
								result = false;
							}
						}
						return result;
					}
				}
				var labelName = $(this).data("label-name");
				if(typeof(labelName) === 'undefined') {
					labelName = '';
				}
				var value = utils.handleNull($(this).val());
				if(isRequire && !$(this).prop("disabled")) {
					if(utils.isEmpty(value)) {
	                    if($($(this).hasClass("form-control"))) {
	                    	$(this).parent().addClass("has-error");
						}
	                    if(utils.isNotEmpty(setting.emptyPopoverCallback) && typeof(setting.emptyPopoverCallback) == 'function') {
							var resultObj = setting.emptyPopoverCallback(this,labelName, setting, self);
							result = resultObj.result;
							popoverTmp = resultObj.popoverObj;
							$availableElement = resultObj.element;
						} else {
							var $tag = $(this);
							if(tagName == 'textarea' && $tag.hasClass("cnoj-richtext")) {
								$tag = $tag.prev();
							}
							$tag.popover({
								placement:setting.placement,
								content: labelName+"不能为空!",
								trigger: "manual"});
							popoverTmp = $tag;
							$availableElement = $tag;
							result = false;
						}
						return result;
					}
				}
				//验证长度、格式
				if(value != '') {
					var lenStr = $(this).data("length");
					var dataFormat = $(this).data("format");
					var regexp = $(this).data("regexp");
					if(typeof(lenStr) !== 'undefined' || typeof(dataFormat) !== 'undefined' || typeof(regexp) !== 'undefined') {
						if(typeof(lenStr) === 'undefined')
							lenStr = '';
						if(typeof(dataFormat) === 'undefined')
							dataFormat = '';
						if(typeof(regexp) === 'undefined')
							regexp = '';
						if(result && utils.trim(regexp) != '') {
							result = utils.checkRegexp(regexp,value);
						} else if(!utils.isEmpty(lenStr) && !utils.isEmpty(dataFormat)) {
							result = utils.checkLen(value, lenStr);
							if(result) {
								result = result && checkDataFormat(value, dataFormat);
							}
						} else if(utils.trim(lenStr) != '' && utils.trim(dataFormat) == '') {
						   result = utils.checkLen(value,lenStr);
						} else if(utils.trim(dataFormat) != '' && utils.trim(lenStr) == '') {
							result = checkDataFormat(value,dataFormat);
						}
						if(!result) {
							result = result && false;
							if($($(this).hasClass("form-control"))) {
		                    	$(this).parent().addClass("has-error");
							}
							if(utils.isNotEmpty(setting.errorPopoverCallback) && typeof(setting.errorPopoverCallback) == 'function') {
								var resultObj = setting.errorPopoverCallback(this,labelName, setting, self);
								result = resultObj.result;
								popoverTmp = resultObj.popoverObj;
								$availableElement = resultObj.element;
							} else {
								var $tag = $(this);
								if(tagName == 'textarea' && $tag.hasClass("cnoj-richtext")) {
									$tag = $tag.prev();
								}
								$tag.popover({placement:setting.placement,
									content:labelName+"输入错误!",
									trigger:"manual"});
								$availableElement = $tag;
								popoverTmp = $tag;
							}
						}
					}
				}
			} else {
				return result;
			}
		});
		if(!result) {
			var $tabPane = $currentNode.parents(".tab-pane:eq(0)");
			if(utils.isExist($tabPane)) {
				var is = $tabPane.hasClass("active");
				if(!is) {
					var id = $tabPane.attr("id");
					var aId = id.replace("tab-","");
					var $a = $("#"+aId);
					$a.tab('show');
				}
			}
			var offset = $availableElement.offset();
			if(typeof(offset) != 'undefined') {
				var top = offset.top;
				var $panel = $(this).parents(".panel-tab-content:eq(0)");
				if(!utils.isExist($panel)) {
					$panel = $("body");
				} else {
					top = top - $panel.offset().top;
				}
				top = (top>40)?(top-50):top;
				if(top != 0) {
					$panel.animate({scrollTop: top}, 300);
					setTimeout(function() {
					    popoverTmp.popover('show');
					}, 310);
				} else {
					popoverTmp.popover('show');
				}
			}
		}
		$(document).click(function(event){
			if(!utils.isEmpty(setting.callback) && typeof(setting.callback) === 'function') {
				setting.callback(event,popoverTmp);
			} else {
			    var name = $(event.target).attr("name");
			    var thisName = $currentNode.attr("name");
			    if(name == thisName) {
			        removePopover();
			    }
			}
		});
		return result;
		
		function removePopover() {
			if(popoverTmp != null) {
				popoverTmp.popover('destroy');
				popoverTmp = null;
			}
			if($($this.find("input,textarea").hasClass("form-control"))) {
				$this.find("input,textarea").parent().removeClass("has-error");
			}
		}
		
		/**
		 * 验证数据格式
		 * @param value
		 * @param dataFormat
		 * @returns {Boolean}
		 */
		function checkDataFormat(value,dataFormat) {
			var is = false;
			switch (dataFormat) {
			case 'num':
				is = utils.regexNum(value);
				break;
			case 'decimal':
				is = utils.regexDecimal(value);
				break;
			case 'integer':
				is = utils.regexInteger(value);
				break;
			case 'ip':
				is = utils.regexIp(value);
				break;
			case 'chinese':
				is = utils.checkChinese(value);
				break;
			case 'email':
				is = utils.checkEmail(value);
				break;
			case 'phone':
				is = utils.checkPhoneNo(value);
				break;
			case 'telephone':
				is = utils.checkTelephone(value);
				break;
			case 'qq':
				is = utils.checkQQ(value);
				break;
			case 'idcard':
				is = utils.checkCardNo(value);
				break;
			case 'date': 
				is = utils.checkDate(value);
			    break;
			case 'time':
				is = utils.checkTime(value);
				break;
			case 'datetime':
				is = utils.checkDateTime(value);
				break;
			default:
				is = false;
				break;
			}
			return is;
		}

	}
})(jQuery)