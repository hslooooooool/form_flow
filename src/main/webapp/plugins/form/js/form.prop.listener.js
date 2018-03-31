/**
 * @author lmq
 * @since 1.0
 * 表单属性监听
 */
(function($){
	
	var FormPropListener = function(element) {
		this.element = element;
	};
	FormPropListener.prototype = {
			/**
			 * 开始监听属性
			 */
			startPropListener: function() {
				this.hidePropListener();
				this.controlFieldListener();
			},
			/**
			 * 监听表单隐藏属性
			 */
			hidePropListener: function() {
				var handledClassFlag = "mix-form-hide-listener";
				this.element.find("input.hide,textarea.hide").each(function(){
					var $this = $(this);
					if(!$this.hasClass(handledClassFlag)) {
						$this.addClass(handledClassFlag);
						var $parent = $this.parent();
						//判断
						if(utils.isTagName($parent, 'td')) {
							$parent.hide();
						}
					}//if
				});
			},
			/**
			 * 监听控制字段
			 */
			controlFieldListener: function() {
				var controlClassFlag = "mix-showhide";
				var handledControlClassFlag = controlClassFlag+"-listener";
				var self = this;
				this.element.find("."+controlClassFlag).each(function(){
					var $this = $(this);
					if(!$this.hasClass(handledControlClassFlag)) {
						$this.addClass(handledControlClassFlag);
						self.controlFieldChange($this);
						$this.change(function(){
							self.controlFieldChange($(this));
						});
					}
				});
			},
			/**
			 * 控制字段的内容发生变化时，进行相应处理
			 * @param $controlEle
			 */
			controlFieldChange : function($controlEle){
				var fieldId = $controlEle.attr("id");
				var currentValue = $controlEle.val();
				$("input[relate-field="+fieldId+"],textarea[relate-field="+fieldId+"]").each(function() {
					var $controledEle = $(this);
					var $parent = $controledEle.parent();
					var relateFieldValue = $controledEle.attr("relate-field-value");
					var times = 3;
					var isSuccess = false;
					var count = 0;
					var parentObj = $parent;
					if(currentValue == relateFieldValue) {
						while(!isSuccess && count<times) {
							if(utils.isTagName(parentObj, 'td')) {
								parentObj.show();
								$controledEle.removeClass("hide");
								isSuccess = true;
								inputPluginEvent();
							} else {
								parentObj = parentObj.parent();
							}
							count++;
						}
						if(!isSuccess) {
							$controledEle.removeClass("hide");
							inputPluginEvent();
						}
					} else {
						while(!isSuccess && count<times) {
							if(utils.isTagName(parentObj, 'td')) {
								parentObj.hide();
								$controledEle.addClass("hide");
								$controledEle.val("");
								isSuccess = true;
							} else {
								parentObj = parentObj.parent();
							}
							count++;
						}
						if(!isSuccess) {
							$controledEle.addClass("hide");
							$controledEle.val("");
						}
					}//if
				});
			}
	}
	
	/**
	 * 表单属性监听
	 */
	$.fn.formPropListener = function(options) {
		var $this = $(this);
		if(null != $this) 
			new FormPropListener($this).startPropListener();
	}
	
})(jQuery)