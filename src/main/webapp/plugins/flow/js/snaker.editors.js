(function($){
var snakerflow = $.snakerflow;

$.extend(true, snakerflow.editors, {
	inputEditor : function(){
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;			
			var require = (typeof(props[_k].isRequire)==='undefined')?'':(props[_k].isRequire?'require':'');
			var formCheck = (typeof(props[_k].formCheck)==='undefined')?'':props[_k].formCheck;
			var styleClass = (typeof(props[_k].styleClass)==='undefined')?'':props[_k].styleClass;
			//alert(_k+","+_props[_k].value);
		    defValue = (typeof(defValue)==='undefined' || defValue=='')?'':defValue;
			$('<input type="text" id="'+_div+'_input" class="form-control input-sm '+require+" "+styleClass+'" '+formCheck+' />').val(props[_k].value).change(function(){
				_props[_k].value = $(this).val();
			}).appendTo('#'+_div);
			
			$('#'+_div).data('editor', this);
		}
		this.destroy = function(){
			$('#'+_div+' input').each(function(){
				_props[_k].value = $(this).val();
			});
		}
	},
	/**
	 * 只读属性
	 */
	inputReadonly : function(){
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;
			$('<input type="text" id="'+_div+'_input" readonly="readonly" class="form-control input-sm" />').val(props[_k].value).appendTo('#'+_div);
			$('#'+_div).data('editor', this);
		}
		this.destroy = function(){
			$('#'+_div+' input').each(function(){
				_props[_k].value = $(this).val();
			});
		}
	},
	inputSelectEditor : function(cfg){
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;			
			var require = (typeof(props[_k].isRequire)==='undefined')?'':(props[_k].isRequire?'require':'');
			var formCheck = (typeof(props[_k].formCheck)==='undefined')?'':props[_k].formCheck;
			var styleClass = (typeof(props[_k].styleClass)==='undefined')?'':props[_k].styleClass;
			if(!utils.isEmpty(_props[_k].value)) {
				formCheck = formCheck + " data-default-value="+_props[_k].value;
				if(_k == 'formId') {
					cfg.formId = _props[_k].value;
				}
			}
			$('<input type="text" id="'+_div+'_input" class="form-control input-sm '+require+" "+styleClass+'" '+formCheck+' />').val(props[_k].value).change(function(){
				_props[_k].value = $("#"+_div+" input:eq(1)").val();
				if(_k == 'formId') {
					cfg.formId = _props[_k].value;
				}
			}).appendTo('#'+_div);
			$('#'+_div).data('editor', this);
		}
		this.destroy = function(){
			$('#'+_div+' input').each(function(){
				//_props[_k].value = $(this).val();
				$("#"+_div+"_input").removeClass("cnoj-input-select-listener");
				$(".input-select-panel").remove();
				_props[_k].value = $("#"+_div+" input:eq(1)").val();
			});
		}
	},
	inputAPromptEditor : function(prompt){
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;			
			var require = (typeof(props[_k].isRequire)==='undefined')?'':(props[_k].isRequire?'require':'');
			var formCheck = (typeof(props[_k].formCheck)==='undefined')?'':props[_k].formCheck;
			var styleClass = (typeof(props[_k].styleClass)==='undefined')?'':props[_k].styleClass;
			
		    defValue = (typeof(defValue)==='undefined' || defValue=='')?'':defValue;
			$('<input type="text" id="'+_div+'_input" class="form-control input-sm '+require+" "+styleClass+'" '+formCheck+' />').val(props[_k].value).change(function(){
				_props[_k].value = $(this).val();
			}).appendTo('#'+_div);
			if(!utils.isEmpty(prompt)) {
				$("#"+_div+"_input").after("<span> "+prompt+"</span>");
			}
			$('#'+_div).data('editor', this);
		}
		this.destroy = function(){
			$('#'+_div+' input').each(function(){
				_props[_k].value = $(this).val();
			});
		}
	},
	//注释说明
	inputExplainEditor : function(msg){
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;			
			var require = (typeof(props[_k].isRequire)==='undefined')?'':(props[_k].isRequire?'require':'');
			var formCheck = (typeof(props[_k].formCheck)==='undefined')?'':props[_k].formCheck;
			var styleClass = (typeof(props[_k].styleClass)==='undefined')?'':props[_k].styleClass;
			
		    defValue = (typeof(defValue)==='undefined' || defValue=='')?'':defValue;
			$('<input type="text" id="'+_div+'_input" class="form-control input-sm '+require+" "+styleClass+'" '+formCheck+' />').val(props[_k].value).change(function(){
				_props[_k].value = $(this).val();
			}).appendTo('#'+_div);
			if(!utils.isEmpty(msg)) {
				$("#"+_div+"_input").after("<div class='help-block m-b-0'>"+msg+"</div>");
			}
			$('#'+_div).data('editor', this);
		}
		this.destroy = function(){
			$('#'+_div+' input').each(function(){
				_props[_k].value = $(this).val();
			});
		}
	},
	/**
	 * 复选框
	 * @param label
	 * @param isCheck
	 */
	onCheckEditor : function(label, isCheck) {
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;
			var checkboxHtml = '<div style="width: 85px;float: right;"><div class="switch switch-small" data-on-label="是" data-off-label="否"><input type="checkbox" id="'+_div+'_checkbox" value="1" '+(isCheck?'checked':'')+' /></div></div><div style="float: left;" class="p-t-5 p-l-5">'+label+'</div>';
			$(checkboxHtml).appendTo('#'+_div);
			$('#'+_div).data('editor', this);
			if(utils.isEmpty(_props[_k].value)) {
				_props[_k].value = isCheck?"1":"0";
			}
			$('#'+_div).removeClass("col-sm-9");
			$('#'+_div).addClass("col-sm-12 p-r-0");
			$('#'+_div).css({"border-bottom": "1px solid #dfdfdf"});
			$('#'+_div).prev().hide();
			
			$('#'+_div+'_checkbox').parents(".switch:eq(0)").on('switch-change', function (e, data) {
			    var value = data.value;
			    if(value) {
			    	_props[_k].value = "1";
			    } else {
			    	_props[_k].value = "0";
			    }
			});
			if(_props[_k].value == "1") {
				$('#'+_div+'_checkbox').prop("checked", true);
			}
			if(_k == 'taskAttachment') {
				if(snakerflow.config.attachment == '0') {
					_props[_k].value = '0';
					$("#"+_div).hide();
				}
			}
			$('#'+_div+'_checkbox').parent().bootstrapSwitch();
		}
		this.destroy = function(){
		}
	},
	//表单属性【可编辑域】
	editAreaEditor : function(cfg) {
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;
			var sle = $('<fieldset><legend>可编辑域</legend><div class="checkbox-area checkbox-align"></div>').appendTo('#'+_div);
			var defValue = _props[_k].value;
			if(!utils.isEmpty(cfg.formId)){
				var url = "op/queryReq/flow_form_prop?id="+cfg.formId;
				$.ajax({
				   type: "GET",
				   url: url,
				   async:false,
				   success: function(data){
					  var output = data;
					  if(output.result=='1' && output.datas.length>0) {
						var datas = output.datas;
						var p = sle.find(".checkbox-area");
						if(!utils.isEmpty(defValue)) {
							defValue = defValue.split(",");
						}
						for(var idx=0; idx<datas.length; idx++){
							if(typeof(defValue) === 'object' && defValue.length>0) {
								var is = false;
								for ( var i = 0; i < defValue.length; i++) {
									if(datas[idx][0] == defValue[i]) {
										 is = true;
										 break;
									}
								}
								if(is) {
									p.append('<label class="col-sm-4"><input type="checkbox" checked="checked" id="'+datas[idx][0]+'"  name="editorArea" value="'+datas[idx][0]+'" />&nbsp;'+datas[idx][1]+'</label>');
								} else {
									p.append('<label class="col-sm-4"><input type="checkbox" id="'+datas[idx][0]+'"  name="editorArea" value="'+datas[idx][0]+'" />&nbsp;'+datas[idx][1]+'</label>');
								}
							} else {
							    p.append('<label class="col-sm-4"><input type="checkbox" id="'+datas[idx][0]+'"  name="editorArea" value="'+datas[idx][0]+'" />&nbsp;'+datas[idx][1]+'</label>');
							}
						}
					 }
				   }
				});
			}
			sle.find("input[type=checkbox]").click(function(){
				var pvalue = '';
				$(".checkbox-area input[type=checkbox]:checked").each(function(){
					pvalue +=$(this).val()+",";
				});
				if(!utils.isEmpty(pvalue)) {
					pvalue = pvalue.substring(0,(pvalue.length-1));
				}
				props[_k].value = pvalue;
			});
			$('#'+_div).data('editor', this);
		};
		this.destroy = function(){
			
		}
	},
	selectCfgEditor : function(cfg,arg){
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;
			//如果是流程属性中的“是否有附件”下拉框
			if(_k == 'attachment') {
				if(utils.isNotEmpty(_props[_k].value)) {
					cfg.attachment = _props[_k].value;
				} else if(utils.isNotEmpty(cfg.attachment)){
					_props[_k].value = cfg.attachment;
				}
			}
			var sle = $('<select class="form-control input-sm" id="'+_div+'_select" />').val(props[_k].value).change(function(){
				_props[_k].value = $(this).val();
				if(_k == 'attachment') {
					cfg.attachment = _props[_k].value;
				}
			}).appendTo('#'+_div);
			const defaultValue = _props[_k].value;
			if(typeof arg === 'string'){
				$.ajax({
				   type: "GET",
				   url: arg,
				   success: function(data){
					  var opts = eval(data);
					  opts = opts.datas;
					  if(opts && opts.length>0){
						  firstValue = opts[0][0];
						  for(var idx=0; idx<opts.length; idx++){
							  if(utils.isEmpty(defaultValue) && idx==0) {
								  defaultValue = opts[idx][0];
							  }
							  sle.append('<option value="'+opts[idx][0]+'">'+opts[idx][1]+'</option>');
							}
						 }
						 sle.val(defaultValue);
						 //加""的原因是：避免整数，导致replace方法异常
						 _props[_k].value = defaultValue+"";
						 if(_k == 'attachment') {
							cfg.attachment = defaultValue;
						 }
				    }
				});
			}else {
				for(var idx=0; idx<arg.length; idx++){
					if(utils.isEmpty(defaultValue) && idx==0) {
						defaultValue = arg[idx].value;
					}
					sle.append('<option value="'+arg[idx].value+'">'+arg[idx].name+'</option>');
				}
				 sle.val(defaultValue);
				 _props[_k].value = defaultValue+"";
				 if(_k == 'attachment') {
					cfg.attachment = defaultValue;
				 }
			}
			if(_k == 'taskAttachment' && cfg.attachment==0) {
				$('#'+_div).parent().hide();
			}
			$('#'+_div).data('editor', this);
		};
		this.destroy = function(){
			$('#'+_div+' input').each(function(){
				_props[_k].value = $(this).val();
			});
		};
	},
	selectEditor : function(arg,defaultValue){
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;
			var sle = $('<select class="form-control input-sm" id="'+_div+'_select" />').val(props[_k].value).change(function(){
				_props[_k].value = $(this).val();
			}).appendTo('#'+_div);
			defaultValue = utils.handleNull(defaultValue);
			if(_props[_k].value == 'N') {
				_props[_k].value = '0';
			} else if(_props[_k].value == 'Y') {
				_props[_k].value = '1';
			}
			if(utils.isEmpty(_props[_k].value) && utils.isNotEmpty(defaultValue)) {
				_props[_k].value = defaultValue+'';
			} else if(utils.isNotEmpty(_props[_k].value)) {
				defaultValue = _props[_k].value;
			}
			if(typeof arg === 'string'){
				$.ajax({
				   type: "GET",
				   url: arg,
				   async:false,
				   success: function(data){
					  var opts = eval(data);
					  opts = opts.datas;
					 if(opts && opts.length){
						 if(utils.isEmpty(defaultValue)) {
							   _props[_k].value = opts[0][0];
						  }
						for(var idx=0; idx<opts.length; idx++){
							if(utils.isEmpty(_props[_k].value) && idx==0) {
								_props[_k].value = opts[idx][0];
							}
							for(var idx=0; idx<opts.length; idx++){
								sle.append('<option value="'+opts[idx][0]+'">'+opts[idx][1]+'</option>');
							}
							if(utils.isNotEmpty(_props[_k].value)) {
								sle.val(_props[_k].value);
							}
						}//for
					 }//if
				   }//success
				});
			}else {
				for(var idx=0; idx<arg.length; idx++){
					sle.append('<option value="'+arg[idx].value+'">'+arg[idx].name+'</option>');
				}
				if(utils.isNotEmpty(_props[_k].value)) {
					sle.val(_props[_k].value);
				}
			}
			$('#'+_div).data('editor', this);
			if(_k == "selectAssignerStyle") {
				var isAssignerVal = _props['isExeAssigner'].value;
				var thisDiv = "#p"+_k+"_select";
				if(isAssignerVal == '0') {
					$(thisDiv).parent().parent().hide();
				}
				$('#pisExeAssigner_checkbox').parents(".switch:eq(0)").on('switch-change', function (e, data) {
				    var value = data.value;
				    if(value) {
				    	$(thisDiv).parent().parent().show();
				    } else {
				    	$(thisDiv).parent().parent().hide();
				    }
				});
			}
		};
		this.destroy = function(){
			$('#'+_div+' input ').each(function(){
				_props[_k].value = $(this).val();
			});
		};
	},
	//所属组织机构
    selectOrgEditor : function(name) {
    	var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;
			var require = (typeof(props[_k].isRequire)==='undefined')?'':(props[_k].isRequire?'require':'');
			var formCheck = (typeof(props[_k].formCheck)==='undefined')?'':props[_k].formCheck;
			var styleClass = (typeof(props[_k].styleClass)==='undefined')?'':props[_k].styleClass;
			$('<input type="text" readonly="readonly" id="'+_div+'_input" class="cursor-pointer input-sm form-control '+require+" "+styleClass+'" '+formCheck+' />').val(props[_k].value).appendTo('#'+_div);
			var glyphicon = props[_k].glyphicon;
			if(!utils.isEmpty(glyphicon)) {
				$('<span class="glyphicon '+glyphicon+' form-control-feedback"></span>').appendTo('#'+_div);
			}
			var defaultValue = _props[name].value;
			$('#'+_div).data('editor', this);
			$('#'+_div+'_input').orgTree({defaultValue:defaultValue,callback:function(id,showName) {
				$('#'+_div+'_input').attr("title",showName);
				$('#'+_div+'_input').val(showName);
				_props[_k].value = showName;
				_props[name].value = id;
			}});
		}
		this.destroy = function(){
			/*$('#'+_div+' input').each(function(){
				_props[_k].value = $(this).val();
			});*/
		}
	},
	//参与者
	assigneeEditor : function(title,name){
		var _props,_k,_div,_src,_r;
		this.init = function(props, k, div, src, r){
			_props=props; _k=k; _div=div; _src=src; _r=r;			
			var require = (typeof(props[_k].isRequire)==='undefined')?'':(props[_k].isRequire?'require':'');
			var formCheck = (typeof(props[_k].formCheck)==='undefined')?'':props[_k].formCheck;
			var styleClass = (typeof(props[_k].styleClass)==='undefined')?'':props[_k].styleClass;
		    defValue = (typeof(defValue)==='undefined' || defValue=='')?'':defValue;
			$('<input type="text" readonly="readonly" id="'+_div+'_input" class="cursor-pointer input-sm form-control '+require+" "+styleClass+'" '+formCheck+' />').val(props[_k].value).appendTo('#'+_div);
			var glyphicon = props[_k].glyphicon;
			if(!utils.isEmpty(glyphicon)) {
				$('<span class="glyphicon '+glyphicon+' form-control-feedback"></span>').appendTo('#'+_div);
			}
			$('#'+_div).data('editor', this);
			$('#'+_div+"_input").on('click',function(){
				//alert("111");
				var $this = $(this);
				var checkedNames = $this.val();
				var checkedValues = props[name].value;
				var uri = "flow/node/assignee?selectedValues="+checkedValues+"&flag="+name;
				BootstrapDialogUtil.loadUriDialog(title,uri,800,"#fff",true,function(){
					setTimeout(function(){
						initEvent();
						var $dialogFooter = $(".bootstrap-dialog-footer-buttons");
						$dialogFooter.append("<button type='button' id='ok' class='btn btn-primary'><i class='glyphicon glyphicon-ok'></i> 确定</button>");
						$dialogFooter.find('#ok').click(function(){
							var $createUser = $("#assignee_flow_create_user");
							if(!utils.isEmpty($createUser) && $createUser.prop("checked")) {
								var showName = $createUser.parent().text();
								$this.attr('title',showName);
								$this.val(showName);
								props[_k].value = showName;
								props[name].value = $createUser.val();
								BootstrapDialogUtil.close();
							} else {
								checkedValues = '';
						   		checkedNames = '';
						   		$("#assignee-selected-data input[type='checkbox']").each(function(){
						   			checkedValues += $(this).val()+',';
						   			checkedNames += $(this).parent().text()+',';
						   		});
						   		if(checkedValues.length>0) {
						   			var showName = checkedNames.substring(0, checkedNames.length-1);
						   			$this.attr('title',showName);
									$this.val(showName);
									props[_k].value = showName;
									props[name].value = checkedValues.substring(0, checkedValues.length-1);
									BootstrapDialogUtil.close();
						   		} else {
						   			utils.showMsg("请选择"+title+"！");
						   		}
							}
						});
						
					}, 200);
				});
			});
		}
		this.destroy = function(){
			$('#'+_div+' input').each(function(){
				_props[_k].value = $(this).val();
			});
		}
	}
});

})(jQuery);