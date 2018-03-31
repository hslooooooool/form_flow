<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
   <!-- 表单设计 -->
<link href="${pageContext.request.contextPath}/plugins/form/css/form-designer.css" rel="stylesheet" />
<script type="text/javascript">
window.UEDITOR_HOME_URL = "${pageContext.request.contextPath}/plugins/ueditor/";
</script>
<div class="wrap-content" id="form-designer">
   <input type="hidden" id="root-path" value="${ctx}">
   <div class="row m-l-0 m-r-0 p-l-5 p-r-5">
      <form method="post" id="form-prop" class="form-horizontal">
	     <input type="hidden" name="id" value="${form.id }">
	      <div class="col-sm-4 p-l-0 p-r-0">
		    <div class="form-group">
				<label for="input01" class="col-sm-2 control-label fw-normal p-t-0 p-l-0">表单名称</label>
				<div class="col-sm-10 p-t-0 p-l-0">
				   <input type="text" class="form-control require" name="name" data-label-name="表名称" id="input01" placeholder="表单名称" value="${form.name }" />
				</div>
			</div>
		</div>
		<div class="col-sm-4 p-l-10 p-r-0">
		    <div class="form-group">
				<label for="input03" class="col-sm-2 control-label fw-normal p-t-0 p-l-0">表单类型</label>
				<div class="col-sm-10 p-t-0 p-l-0">
				  <select class="form-control cnoj-select select-form-control" name="type" id="input03" data-uri="dict/item/FORM_TYPE.json" data-default-value="${form.type }" >
				  </select>
				</div>
			</div>
		</div>
	  </form>
   </div>
   <div class="row m-l-0 m-r-0 p-t-5">
       <form method="post" id="saveform" name="saveform" action="">
          <input type="hidden" name="fields" id="fields" value="${form.fieldNum }">
         <div class="col-sm-1 p-l-5 p-r-0">
            <div class="panel panel-default">
	           <div class="panel-heading ui-widget-header"><strong>表单控件</strong></div>
	           <div class="panel-body">
	               <ul class="nav nav-list form-designer-tools">
	                   <li><button type="button" onclick="leipiFormDesign.exec('text');" class="btn btn-info btn-sm btn-block">文本框</button></li>
	                   
		               <li><button type="button" onclick="leipiFormDesign.exec('textarea');" class="btn btn-info btn-sm btn-block">文本域</button></li>
		               <li><button type="button" onclick="leipiFormDesign.exec('select');" class="btn btn-info btn-sm btn-block">下拉框</button></li>
		               <li><button type="button" onclick="leipiFormDesign.exec('radios');" class="btn btn-info btn-sm btn-block">单选框</button></li>
		               <li><button type="button" onclick="leipiFormDesign.exec('checkboxs');" class="btn btn-info btn-sm btn-block">复选框</button></li>
		                <!--  
		                <li><a href="javascript:void(0);" onclick="leipiFormDesign.exec('macros');" class="btn btn-link">宏控件</a></li>
		                <li><a href="javascript:void(0);" onclick="leipiFormDesign.exec('progressbar');" class="btn btn-link">进度条</a></li>
		                <li><a href="javascript:void(0);" onclick="leipiFormDesign.exec('qrcode');" class="btn btn-link">二维码</a></li>
		                -->
		                <li><button type="button" onclick="leipiFormDesign.exec('listctrl');" class="btn btn-info btn-sm btn-block">列表控件</button></li>
		                <li><button type="button" onclick="leipiFormDesign.exec('button');" class="btn btn-info btn-sm btn-block">按钮</button></li>
		                <li><button type="button" onclick="leipiFormDesign.exec('helpers');" class="btn btn-info btn-sm btn-block">帮助</button></li>
		                <li class="new-up"><button type="button" onclick="leipiFormDesign.exec('tabs');" class="btn btn-info btn-sm btn-block">选项卡 <i class="new"></i></button></li>
		                
                        <li class="new-up"><button type="button" onclick="leipiFormDesign.exec('file');" class="btn btn-info btn-sm btn-block">上传文件 <i class="new"></i></button>
                           <p class="help-block text-center small">单文件上传</p>
                        </li>
                        <li class="new-up"><button type="button" onclick="leipiFormDesign.exec('files');" class="btn btn-info btn-sm btn-block">上传文件插件 <i class="new"></i></button>
                            <p class="help-block text-center small">支持多文件上传</p>
                        </li>
	               </ul>
	           </div>
	        </div>
         </div>
         <div class="col-sm-11 p-r-5">
             <script id="formEditor" type="text/plain" style="width:100%;">${form.originalHtml}</script>
         </div>
    </form>
  </div><!--end row-->
</div>
<script type="text/javascript">
    if(typeof(UE) == 'undefined') {
        var $wrap = $("#form-designer").parent();
        $wrap.append('<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugins/ueditor/ueditor.config.js"><\/script>');
        $wrap.append('<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugins/ueditor/ueditor.all.js"><\/script>');
        $wrap.append('<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugins/ueditor/lang/zh-cn/zh-cn.js"><\/script>');
    }
</script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugins/ueditor/formdesign/leipi.formdesign.v4.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugins/ueditor/mix-form/mix-form.js"></script>

<script type="text/javascript">
   var tmplH = $("#form-prop").parent().outerHeight(true);
   var autoHeight = getMainHeight() - tmplH - 55 - 45;
   UE.delEditor('formEditor');
   var formDesingerEditor = UE.getEditor('formEditor',{
	   toolleipi : true,//是否显示，设计器的 toolbars
	    textarea: 'design_content',
	    //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
	    /*toolbars:[['fullscreen', 'source', '|', 
	               'undo', 'redo', '|',
	               'bold', 'italic', 'underline', 'fontborder', 'strikethrough',  'removeformat', '|', 
	               'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist','|', 
	               'fontfamily', 'fontsize', '|', 'indent', '|', 
	               'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',  
	               'link', 'unlink',  '|',  'horizontal',  'spechars', 'wordimage', '|', 
	               'inserttable', 'deletetable',  'mergecells', 'splittocells']],*/
	     toolbars: [[
	                 'fullscreen','source','|',
	                 'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'removeformat','blockquote', '|', 
	                 'forecolor', 'backcolor','insertorderedlist', 'insertunorderedlist', '|',
					  'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
					  'paragraph', 'fontfamily', 'fontsize', '|', 'indent', '|',
					  'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
					  'link', 'unlink', 'anchor', '|','pagebreak','spechars', 'wordimage','|',
					  'horizontal', 'date', 'time', 'spechars', '|',
					  'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts'
		]],  
	    //focus时自动清空初始化时的内容
	    //autoClearinitialContent:true,
	    //关闭字数统计
	    wordCount:false,
        enableAutoSave: false,
	    //关闭elementPath
	    elementPathEnabled:false,
	    //默认的编辑区域高度
	    initialFrameHeight:autoHeight,
	    autoHeightEnabled:false,
	    UEDITOR_HOME_URL:'${ctx}/plugins/ueditor/',
	    //iframeCssUrl:"${ctx}/plugins/ueditor/formdesign/bootstrap/css/bootstrap.css" //引入自身 css使编辑器兼容你网站css
	    iframeCssUrl:["${ctx}/plugins/bootstrap/css/bootstrap.min.css",
	                  "${ctx}/plugins/ueditor/themes/iframe.css"] //引入自身 css使编辑器兼容你网站css
	    ,iframeJsUrl:["${ctx}/js/jquery-1.11.3.min.js","${ctx}/plugins/bootstrap/js/bootstrap.min.js"]
	    //更多其他参数，请参考ueditor.config.js中的配置项
	});
   
	var leipiFormDesign = {
	    /*执行控件*/
	    exec : function (method) {
	    	formDesingerEditor.execCommand(method);
	    },
	    /*
	     Javascript 解析表单
	     template 表单设计器里的Html内容
	     fields 字段总数
	     */
	    parse_form:function(template,fields) {
	    	//加上bootstrap表格样式
	    	template = template.replace(/<table(.*?)>/gi,"<table class=\"table table-bordered table-condensed\" $1>");
	        //正则  radios|checkboxs|select 匹配的边界 |--|  因为当使用 {} 时js报错 (plugins|fieldname|fieldflow)
	        var preg =  /(\|-<span(((?!<span).)*leipiplugins=\"(radios|checkboxs|select|helpers)\".*?)>(.*?)<\/span>-\||<(img|input|textarea|select|button).*?(<\/select>|<\/textarea>|<\/button>|\/>))/gi;
	        var preg_attr =/(\w+)=\"(.?|.+?)\"/gi;
	        var preg_group =/<input.*?\/>/gi;
	        if(!fields) fields = 0;
	        var template_parse = template,template_data = new Array(),add_fields=new Object(),checkboxs=0,radios=0;
	        var pno = 0;
	        template.replace(preg, function(plugin,p1,p2,p3,p4,p5,p6){
	            var parse_attr = new Array(),attr_arr_all = new Object(),name = '', select_dot = '' , is_new=false;
	            var p0 = plugin;
	            var tag = p6 ? p6 : p4;
	            //console.log(tag);
	            if(tag == 'radios' || tag == 'checkboxs'){
	                plugin = p2;
	            } else if(tag == 'select' || tag == 'helpers') {
	                plugin = plugin.replace('|-','');
	                plugin = plugin.replace('-|','');
	            }
	            plugin.replace(preg_attr, function(str0,attr,val) {
	                if(attr=='name') {
	                    if(val=='leipiNewField' || val.startWith('data_')) {
	                        is_new=true;
	                        fields++;
	                        val = 'data_'+fields;
	                    }
	                    name = val;
	                }
	                if(tag=='select' && attr=='value') {
	                    if(!attr_arr_all[attr]) attr_arr_all[attr] = '';
	                    attr_arr_all[attr] += select_dot + val;
	                    select_dot = ',';
	                } else {
	                    attr_arr_all[attr] = val;
	                }
	                var oField = new Object();
	                oField[attr] = val;
	                parse_attr.push(oField);
	            });
	            /*复选组  多个字段 */
	            if(tag =='checkboxs') {
	                plugin = p0;
	                plugin = plugin.replace('|-','');
	                plugin = plugin.replace('-|','');
	                var name = 'checkboxs_'+checkboxs;
	                attr_arr_all['parse_name'] = name;
	                attr_arr_all['name'] = '';
	                attr_arr_all['value'] = '';
	                attr_arr_all['text'] = '';
	                //attr_arr_all['content'] = '<span leipiplugins="checkboxs"  title="'+attr_arr_all['title']+'">';
	                var dot_name ='', dot_value = '';
	                var isParse = false;
	                var isAdd = false;
	                p5.replace(preg_group, function(parse_group) {
	                    var is_new=false,option = new Object();
	                    parse_group.replace(preg_attr, function(str0,k,val) {
	                        if(k=='name') {
	                        	if(val=='leipiNewField' || val.startWith('data_') || val.startWith('checkboxs_')) {
	                                is_new=true;
	                                fields++;
	                                isAdd = true;
	                                val = 'data_'+fields;
	                            }
	                            attr_arr_all['name'] += dot_name + val;
	                            dot_name = ',';
	                        } else if(k=='value') {
	                            if(val != '') {
	                            	var values = val.split("#");
	                            	if(values.length>1) {
	                            		val = values[0];
	                            		option['text'] = values[1];
	                            	}
	                            }
	                            attr_arr_all['value'] += dot_value + val;
	                            attr_arr_all['text'] += dot_value + option['text'];
	                            dot_value = ',';
	                        }
	                        option[k] = val;
	                    });
	                    if(!attr_arr_all['options']) attr_arr_all['options'] = new Array();
	                    attr_arr_all['options'].push(option);
	                    if(!option['checked']) option['checked'] = '';
	                    var checked = option['checked'] ? 'checked="checked"' : '';
	                    //attr_arr_all['content'] +='<input type="checkbox" name="'+option['name']+'" id="'+option['name']+'_'+option['value']+'" value="'+option['value']+'" fieldname="' + attr_arr_all['fieldname'] + option['fieldname'] + '" fieldflow="' + attr_arr_all['fieldflow'] + '" '+checked+'/><label for="'+option['name']+'_'+option['value']+'">'+option['text']+'</label>&nbsp;';
	                    if(is_new && !isParse) {
	                        var arr = new Object();
	                        arr['name'] = option['name'];
	                        arr['title'] = attr_arr_all['title'];
	                        arr['leipiplugins'] = attr_arr_all['leipiplugins'];
	                        arr['fieldname'] = attr_arr_all['fieldname'] + option['fieldname'];
	                        arr['fieldflow'] = attr_arr_all['fieldflow'];
	                        arr['bindTable'] = attr_arr_all['bind_table'];
		                    arr['bindField'] = attr_arr_all['bind_table_field'];
		                    arr['institle'] = attr_arr_all['institle'];
		                    arr['orghide'] = attr_arr_all['orghide'];
                            arr['islog'] = attr_arr_all['is_log'];
                            
	                        add_fields[option['name']] = arr;
	                        isParse = true;
		                    //add_fields[name] = arr;
	                    }
	                });
	                //attr_arr_all['content'] += '</span>';
	                attr_arr_all['content'] = isAdd ? plugin.replace(/leipiNewField/g,name) : plugin;
	                //parse
	                template = template.replace(plugin,attr_arr_all['content']);
	                template_parse = template_parse.replace(plugin,'{'+name+'}');
	                template_parse = template_parse.replace('{|-','');
	                template_parse = template_parse.replace('-|}','');
	                template_data[pno] = attr_arr_all;
	                checkboxs++;
	            } else if(name) {
	            	/*单选组  一个字段*/
	                if(tag =='radios') {
	                    plugin = p0;
	                    plugin = plugin.replace('|-','');
	                    plugin = plugin.replace('-|','');
	                    attr_arr_all['value'] = '';
	                    attr_arr_all['text'] = '';
	                    var isAdd = false;
	                    //attr_arr_all['content'] = '<span leipiplugins="radios" name="'+attr_arr_all['name']+'" title="'+attr_arr_all['title']+'">';
	                    var dot='';
	                    p5.replace(preg_group, function(parse_group) {
	                        var option = new Object();
	                        parse_group.replace(preg_attr, function(str0,k,val) {
	                            if(k=='value')  {
	                            	if(val != '') {
	                                	var values = val.split("#");
	                                	if(values.length>1) {
	                                		val = values[0];
	                                		option['text'] = values[1];
	                                	}
	                                }
	                                attr_arr_all['value'] += dot + val;
	                                attr_arr_all['text'] += dot + option['text'];
	                                dot = ',';
	                            }
	                            option[k] = val;
	                        });
	                        option['name'] = attr_arr_all['name'];
	                        if(!attr_arr_all['options']) attr_arr_all['options'] = new Array();
	                        attr_arr_all['options'].push(option);
	                        if(!option['checked']) option['checked'] = '';
	                        var checked = option['checked'] ? 'checked="checked"' : '';
	                        //attr_arr_all['content'] +='<input type="radio" id="'+option['name']+'_'+option['value']+'" name="'+attr_arr_all['name']+'" value="'+option['value']+'"  '+checked+'/><label for="'+option['name']+'_'+option['value']+'">'+option['text']+'</label>&nbsp;';
	                    });
	                    //attr_arr_all['content'] += '</span>';
	                } else {
	                    //attr_arr_all['content'] = is_new ? plugin.replace(/leipiNewField/,name) : plugin;
	                }
	                attr_arr_all['content'] = is_new ? plugin.replace(/leipiNewField/,name) : plugin;
	                template = template.replace(plugin,attr_arr_all['content']);
	                template_parse = template_parse.replace(plugin,'{'+name+'}');
	                template_parse = template_parse.replace('{|-','');
	                template_parse = template_parse.replace('-|}','');
	                //console.log(name);
	               // console.log(plugin);
	                if(is_new) {
	                    var arr = new Object();
	                    arr['name'] = name;
	                    arr['leipiplugins'] = attr_arr_all['leipiplugins'];
	                    arr['title'] = (typeof(attr_arr_all['title']) === 'undefined')?attr_arr_all['orgtitle']:attr_arr_all['title'];
	                    arr['orgtype'] = attr_arr_all['orgtype'];
	                    arr['fieldname'] = attr_arr_all['fieldname'];
	                    arr['fieldflow'] = attr_arr_all['fieldflow'];
	                    arr['bindTable'] = attr_arr_all['bind_table'];
	                    arr['bindField'] = attr_arr_all['bind_table_field'];
	                    arr['orgtitle'] = attr_arr_all['orgtitle'];
	                    arr['institle'] = attr_arr_all['institle'];
	                    arr['orghide'] = attr_arr_all['orghide'];
                        arr['islog'] = attr_arr_all['is_log'];
                        
                        //统计字段
                        arr['sumBindTable'] = attr_arr_all['csum_bind_table'];
                        arr['sumBindTableField'] = attr_arr_all['csum_bind_table_field'];
	                    //alert(JSON.stringify(attr_arr_all));
	                    add_fields[arr['name']] = arr;
	                }
	                template_data[pno] = attr_arr_all;
	            }
	            pno++;
	        })
	       // var view = template.replace(/{\|-/g,'');
	       // view = view.replace(/-\|}/g,'');
	        //alert(view);
	        var parse_form = new Object({
	            'fields':fields,//总字段数
	            'template':template,//完整html
	            'parse':template_parse,
	            'data':template_data,//控件属性
	            'add_fields':add_fields//新增控件
	        });
	        return JSON.stringify(parse_form);
	    },
	    /*type  =  save 保存设计 versions 保存版本  close关闭 */
	    fnCheckForm : function ( type ) {
	    	if(!$("#form-prop").validateForm({callback:function(event,obj){
	    		if(utils.isNotEmpty(event.target)) {
	    			if ($(event.target).closest("#form-prop").length > 0) {
	                    if(!utils.isEmpty(obj)) {
	                        $(obj).popover('destroy');
	                        obj = null;
	                    }
	                    if($(event.target).hasClass("form-control")) {
	                        $(event.target).parent().removeClass("has-error");
	                    }
	                }
	    		}
	    	}})) {
	    		return false;
	    	}
	        if(formDesingerEditor.queryCommandState( 'source' )) {
	        	formDesingerEditor.execCommand('source');//切换到编辑模式才提交，否则有bug
	        }
	        if(formDesingerEditor.hasContents()){
	        	formDesingerEditor.sync();/*同步内容*/
	            //--------------以下仅参考-----------------------------------------------------------------------------------------------------
	            var type_value='',formid=0,fields=$("#fields").val(),formeditor='';
	            if( typeof type!=='undefined' ){
	                type_value = type;
	            }
	            //获取表单设计器里的内容
	            formeditor=formDesingerEditor.getContent();
	            //解析表单设计器控件
	            //alert(formeditor);
	            var parseForm = this.parse_form(formeditor,fields);
	            //alert(parseForm);
	            var params = $("#form-prop").serialize();
	            var url = '${ctx}/form/designer/processor'+"?"+params;
	            //alert(parseForm);
	            utils.waitLoading("正在处理表单数据...");
	            //异步提交数据
	            $.ajax({
	                type: 'POST',
	                url : url,
	                data : {'parseForm':parseForm},
	                success : function(data){
	                	utils.closeWaitLoading();
	                	utils.showMsg(data.msg);
	        			if(data.result == '1') {
	        				//loadLocation('form/list');
	        				closeActivedTab();
	        				openTab("表单列表", 'form/list',true);
						}
	                }
	            });
	        } else {
	        	BootstrapDialogUtil.warningAlert('表单内容不能为空！')
	            $('#submitbtn').button('reset');
	            return false;
	        }
	    } ,
	    /*预览表单*/
	    fnReview : function (){
	        if(formDesingerEditor.queryCommandState( 'source' ))
	        	formDesingerEditor.execCommand('source');/*切换到编辑模式才提交，否则部分浏览器有bug*/
	        if(formDesingerEditor.hasContents()){
	        	formDesingerEditor.sync();       /*同步内容*/
	            alert("你点击了预览,请自行处理....");
	            return false;
	            //--------------以下仅参考-------------------------------------------------------------------
	            /*设计form的target 然后提交至一个新的窗口进行预览*/
	            document.saveform.target="mywin";
	            window.open('','mywin',"menubar=0,toolbar=0,status=0,resizable=1,left=0,top=0,scrollbars=1,width=" +(screen.availWidth-10) + ",height=" + (screen.availHeight-50) + "\"");
	            document.saveform.action="";
	            document.saveform.submit(); //提交表单
	        } else {
	            alert('表单内容不能为空！');
	            return false;
	        }
	    }
	};
	function getUeditorContents() {
		return formDesingerEditor.getContent();
	}
</script>