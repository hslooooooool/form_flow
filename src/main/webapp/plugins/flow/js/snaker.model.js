(function($){
var snakerflow = $.snakerflow;

$.extend(true,snakerflow.config.rect,{
	attr : {
	r : 8,
	fill : '#F6F7FF',
	stroke : '#03689A',
	"stroke-width" : 2
}
});

$.extend(true,snakerflow.config.props.props,{
	
	name : {name:'name',tabs:{id:'base-prop',name:'基本属性'}, label:'名称', value:'',isRequire:true, editor:function(){return new snakerflow.editors.inputExplainEditor("使用英文“名称”（尽量避免中文）");}},
	//name : {name:'name',tabs:{id:'base-prop',name:'基本属性'}, label:'名称', value:'',isRequire:true, editor:function(){return new snakerflow.editors.inputEditor();}},
	displayName : {name:'displayName',tabs:{id:'base-prop',name:'基本属性'}, label:'显示名称',isRequire:true, value:'', editor:function(){return new snakerflow.editors.inputExplainEditor("使用中文“显示名称”");}},
	orgId: {name:'orgId', value:''},
	orgDisplay : {name:'orgDisplay', tabs:{id:'base-prop',name:'基本属性'}, label:'所属组织机构',glyphicon:'glyphicon-th-list',isRequire:true, value:'', editor:function(){return new snakerflow.editors.selectOrgEditor('orgId');}},
	flowType : {name:'flowType', tabs:{id:'base-prop',name:'基本属性'}, label:'流程类型',value:'', editor:function(){return new snakerflow.editors.selectEditor("dict/item/FLOW_TYPE.json");}},
	formId : {name:'formId', tabs:{id:'base-prop',name:'基本属性'}, label:'流程表单',glyphicon:'glyphicon-th-list',isRequire:true,styleClass:'cnoj-input-select',
		formCheck:'data-uri="op/query/select_flow_form.json"',value:'', editor:function(){return new snakerflow.editors.inputSelectEditor(snakerflow.config);}},
	attachment : {name:'attachment', tabs:{id:'base-prop',name:'基本属性'}, label:'是否有附件',value:'', editor:function(){return new snakerflow.editors.selectCfgEditor(snakerflow.config,"dict/item/YES_OR_NO.json");}},
	expireTime : {name:'expireTime',tabs:{id:'advance-prop',name:'高级属性'}, label:'期望完成时间',styleClass:'cnoj-num-spinner',formCheck:'data-step=0.1 autocomplete="off" role="spinbutton"', value:'0', editor:function(){return new snakerflow.editors.inputAPromptEditor(" 小时");}},
	instanceUrl : {name:'instanceUrl',tabs:{id:'advance-prop',name:'高级属性'}, label:'实例启动Url', value:'', editor:function(){return new snakerflow.editors.inputEditor();}},
	instanceNoClass : {name:'instanceNoClass',tabs:{id:'advance-prop',name:'高级属性'}, label:'实例编号生成类', value:'', editor:function(){return new snakerflow.editors.inputEditor();}}
});


$.extend(true,snakerflow.config.tools.states,{
			start : {
				showType: 'image',
				type : 'start',
				name : {text:'<<start>>'},
				text : {text:'开始'},
				img : {src : 'images/48/start_event_empty.png',width : 48, height:48},
				attr : {width:50 ,heigth:50 },
				props : {
					//name:{name:'name',value:'start'},
					name: {name:'name',tabs:{id:'base-prop',name:'基本属性'},label: '名称', value:'start', editor: function(){return new snakerflow.editors.inputReadonly();}},
					displayName: {name:'displayName',tabs:{id:'base-prop',name:'属性'},label: '显示名称', value:'开始', editor: function(){return new snakerflow.editors.inputEditor();}},
					preInterceptors: {name:'preInterceptors',tabs:{id:'base-prop',name:'属性'}, label : '前置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
					postInterceptors: {name:'postInterceptors',tabs:{id:'base-prop',name:'属性'}, label : '后置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}}
				}},
			end : {
				showType: 'image',
				type : 'end',
				name : {text:'<<end>>'},
				text : {text:'结束'},
				img : {src : 'images/48/end_event_terminate.png',width : 48, height:48},
				attr : {width:50 ,heigth:50 },
				props : {
					//name:{name:'name',value:'end'},
					name: {name:'name', tabs:{id:'base-prop',name:'基本属性'}, label: '名称', value:'end', editor: function(){return new snakerflow.editors.inputReadonly();}},
					displayName: {name:'displayName',tabs:{id:'base-prop',name:'属性'},label: '显示名称', value:'结束', editor: function(){return new snakerflow.editors.inputEditor();}},
					preInterceptors: {name:'preInterceptors',tabs:{id:'base-prop',name:'属性'}, label : '前置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
					postInterceptors: {name:'postInterceptors', tabs:{id:'base-prop',name:'属性'}, label : '后置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}}
				}},
			task : {
				showType: 'text',
				type : 'task',
				name : {text:'<<task>>'},
				text : {text:'任务'},
				img : {src : 'images/48/task_empty.png',width :48, height:48},
				props : {
					//name:{name:'name',value:''},
					name: {name:'name', tabs:{id:'base-prop',name:'基本属性'},label: '名称', value:'', editor: function(){return new snakerflow.editors.inputReadonly();}},
					displayName: {name:'displayName', tabs:{id:'base-prop',name:'基本属性'},isRequire:true, label: '显示名称', value:'', editor: function(){return new snakerflow.editors.textEditor();}},
					form: {name:'form', tabs:{id:'base-prop',name:'基本属性'},isRequire:true, label : '表单页面', value:'', editor: function(){return new snakerflow.editors.selectEditor("flow/page/model/item.json");}},
					assignee: {name:'assignee', value:''},
					assigneeDisplay: {name:'assigneeDisplay', tabs:{id:'base-prop',name:'基本属性'}, isRequire:true, glyphicon:'glyphicon-user', label: '参与者', value:'', editor: function(){return new snakerflow.editors.assigneeEditor('选择参与者','assignee');}},
					/*ccperson: {name:'ccperson',  value:''},
					ccpersonDisplay: {name:'ccpersonDisplay', tabs:{id:'base-prop',name:'基本属性'}, label: '抄送人', value:'', glyphicon:'glyphicon-user', editor: function(){return new snakerflow.editors.assigneeEditor('选择抄送人','ccperson');}},*/
					taskType: {name:'taskType', tabs:{id:'base-prop',name:'基本属性'}, label : '任务类型', value:'', editor: function(){return new snakerflow.editors.selectEditor([{name:'主办任务',value:'Major'},{name:'协办任务',value:'Aidant'}]);}},
					performType: {name:'performType', tabs:{id:'base-prop',name:'基本属性'}, label : '参与类型', value:'', editor: function(){return new snakerflow.editors.selectEditor([{name:'普通参与',value:'ANY'},{name:'会签参与',value:'ALL'}]);}},
					
					isExeAssigner: {name:'isExeAssigner', tabs:{id:'input-strategy-prop',name:'输入策略'}, label : '是否选人', value:'', editor: function(){return new snakerflow.editors.onCheckEditor("是否选人", utils.YES_OR_NO.NO);}},
					selectAssignerStyle: {name:'selectAssignerStyle', tabs:{id:'input-strategy-prop',name:'输入策略'}, label : '选人方式', value:'', editor: function(){return new snakerflow.editors.selectEditor("dict/item/SELECT_STYLE.json");}},
					isDepartFilter: {name:'isDepartFilter', tabs:{id:'input-strategy-prop',name:'输入策略'}, label : '是否按部门过滤', value:'', editor: function(){return new snakerflow.editors.onCheckEditor("是否按部门过滤", utils.YES_OR_NO.NO);}},
					
					
					/*isExeAssigner: {name:'isExeAssigner', tabs:{id:'input-strategy-prop',name:'输入策略'}, label : '是否选人', value:'', editor: function(){return new snakerflow.editors.selectEditor("dict/item/YES_OR_NO.json", utils.YES_OR_NO.NO);}},
					selectAssignerStyle: {name:'selectAssignerStyle', tabs:{id:'input-strategy-prop',name:'输入策略'}, label : '选人方式', value:'', editor: function(){return new snakerflow.editors.selectEditor("dict/item/SELECT_STYLE.json");}},
					isDepartFilter: {name:'isExeAssigner', tabs:{id:'input-strategy-prop',name:'输入策略'}, label : '按部门过滤', value:'', editor: function(){return new snakerflow.editors.selectEditor("dict/item/YES_OR_NO.json", utils.YES_OR_NO.NO);}},
					*/
					isConcurrent: {name:'isConcurrent', tabs:{id:'strategy-prop',name:'节点策略'}, label : '是否并发执行', value:'', editor: function(){return new snakerflow.editors.onCheckEditor("是否并发执行", utils.YES_OR_NO.NO);}},
					isTakeTask: {name:'isTakeTask', tabs:{id:'strategy-prop',name:'节点策略'}, label : '是否需要领取任务', value:'', editor: function(){return new snakerflow.editors.onCheckEditor("是否需要领取任务", utils.YES_OR_NO.NO);}},
					isSug: {name:'isSug', tabs:{id:'strategy-prop',name:'节点策略'}, label : '是否有意见域', value:'', editor: function(){return new snakerflow.editors.onCheckEditor("是否有意见域",utils.YES_OR_NO.NO);}},
					taskAttachment: {name:'taskAttachment', tabs:{id:'strategy-prop',name:'节点策略'}, label : '是否有附件', value:'', editor: function(){return new snakerflow.editors.onCheckEditor('是否有附件',utils.YES_OR_NO.NO);}},
					isPrint: {name:'isPrint', tabs:{id:'strategy-prop',name:'节点策略'}, label : '是否显示打印按钮', value:'', editor: function(){return new snakerflow.editors.onCheckEditor("是否显示打印按钮", utils.YES_OR_NO.NO);}},
					
					/*isConcurrent: {name:'isConcurrent', tabs:{id:'strategy-prop',name:'节点策略'}, label : '是否并发', value:'', editor: function(){return new snakerflow.editors.selectEditor("dict/item/YES_OR_NO.json", utils.YES_OR_NO.NO);}},
					isTakeTask: {name:'isTakeTask', tabs:{id:'strategy-prop',name:'节点策略'}, label : '是否领取任务', value:'', editor: function(){return new snakerflow.editors.selectEditor("dict/item/YES_OR_NO.json", utils.YES_OR_NO.NO);}},
					isSug: {name:'isSug', tabs:{id:'strategy-prop',name:'节点策略'}, label : '是否有意见域', value:'', editor: function(){return new snakerflow.editors.selectEditor("dict/item/YES_OR_NO.json");}},
					taskAttachment: {name:'taskAttachment', tabs:{id:'strategy-prop',name:'节点策略'}, label : '是否有附件', value:'', editor: function(){return new snakerflow.editors.selectCfgEditor(snakerflow.config,"dict/item/YES_OR_NO.json");}},
					isPrint: {name:'isPrint', tabs:{id:'strategy-prop',name:'节点策略'}, label : '是否支持打印', value:'', editor: function(){return new snakerflow.editors.selectEditor("dict/item/YES_OR_NO.json", utils.YES_OR_NO.NO);}},
					*/
					formPropIds: {name:'formPropIds', tabs:{id:'edit-area-prop',name:'表单属性'},isCustom:true, label : '可编辑域', value:'', editor: function(){return new snakerflow.editors.editAreaEditor(snakerflow.config);}},
					
					expireTime: {name:'expireTime', tabs:{id:'advance-prop',name:'高级属性'},styleClass:'cnoj-date', label: '期望完成时间', styleClass:'cnoj-num-spinner',formCheck:'data-step=0.1', value:'0', editor: function(){return new snakerflow.editors.inputAPromptEditor(" 小时");}},
					reminderTime: {name:'reminderTime', tabs:{id:'advance-prop',name:'高级属性'},styleClass:'cnoj-date', label : '提醒时间', styleClass:'cnoj-num-spinner',formCheck:'data-step=0.1', value:'0', editor: function(){return new snakerflow.editors.inputAPromptEditor(" 小时");}},
					//reminderRepeat: {name:'reminderRepeat', tabs:{id:'advance-prop',name:'高级属性'}, styleClass:'cnoj-time',label : '重复提醒间隔', styleClass:'cnoj-num-spinner',formCheck:'data-step=0.1', value:'0', editor: function(){return new snakerflow.editors.inputAPromptEditor(" 小时");}},
					autoExecute: {name:'autoExecute', tabs:{id:'advance-prop',name:'高级属性'}, label : '超时自动执行', value:'', editor: function(){return new snakerflow.editors.selectEditor("dict/item/YES_OR_NO.json", utils.YES_OR_NO.NO);}},
					callback: {name:'callback', tabs:{id:'advance-prop',name:'高级属性'}, label : '自动执行回调设置', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
					
					assignmentHandler: {name:'assignmentHandler', tabs:{id:'advance-prop',name:'高级属性'}, label: '参与者处理类', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
					preInterceptors: {name:'preInterceptors', tabs:{id:'advance-prop',name:'高级属性'}, label : '前置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
					postInterceptors: {name:'postInterceptors', tabs:{id:'advance-prop',name:'高级属性'}, label : '后置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}}
				}},
			custom : {
				showType: 'text',
				type : 'custom',
				name : {text:'<<custom>>'},
				text : {text:'自定义'},
				img : {src : 'images/48/task_empty.png',width :48, height:48},
				props : {
					//name:{name:'name',value:''},
					name: {name:'name',label: '名称', tabs:{id:'base-prop',name:'基本属性'}, value:'', editor: function(){return new snakerflow.editors.inputReadonly();}},
					displayName: {name:'displayName',label: '显示名称', tabs:{id:'base-prop',name:'基本属性'}, value:'', editor: function(){return new snakerflow.editors.textEditor();}},
					clazz: {name:'clazz', label: '类路径', value:'', tabs:{id:'base-prop',name:'基本属性'}, editor: function(){return new snakerflow.editors.inputEditor();}},
					methodName: {name:'methodName', label : '方法名称', tabs:{id:'base-prop',name:'基本属性'}, value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
					args: {name:'args', label : '参数变量', tabs:{id:'advance-prop',name:'高级属性'}, value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
				    preInterceptors: {name:'preInterceptors', tabs:{id:'advance-prop',name:'高级属性'}, label : '前置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
					postInterceptors: {name:'postInterceptors', tabs:{id:'advance-prop',name:'高级属性'}, label : '后置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}}
				}},
			subprocess : {
				showType: 'text',
				type : 'subprocess',
				name : {text:'<<subprocess>>'},
				text : {text:'子流程'},
				img : {src : 'images/48/task_empty.png',width :48, height:48},
				props : {
					name: {name:'name',label: '名称',tabs:{id:'base-prop',name:'基本属性'}, value:'', editor: function(){return new snakerflow.editors.inputReadonly();}},
					displayName: {name:'displayName',tabs:{id:'base-prop',name:'基本属性'}, label: '显示名称', value:'', editor: function(){return new snakerflow.editors.textEditor();}},
					processName: {name:'processName', tabs:{id:'base-prop',name:'基本属性'}, label: '子流程名称', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
				    preInterceptors: {name:'preInterceptors', tabs:{id:'advance-prop',name:'高级属性'}, label : '前置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
					postInterceptors: {name:'postInterceptors', tabs:{id:'advance-prop',name:'高级属性'}, label : '后置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}}
				}},
			decision : {
				showType: 'image',
				type : 'decision',
				name : {text:'<<decision>>'},
				text : {text:'判断'},
				img : {src : 'images/48/gateway_exclusive.png',width :48, height:48},
				props : {
					//name:{name:'name',value:''},
					name: {name:'name',label: '名称', tabs:{id:'base-prop',name:'基本属性'}, value:'', editor: function(){return new snakerflow.editors.inputReadonly();}},
					displayName: {name:'displayName', tabs:{id:'base-prop',name:'基本属性'},isRequire:true, label: '显示名称', value:'', editor: function(){return new snakerflow.editors.textEditor();}},
					expr: {name:'expr',label: '决策表达式', tabs:{id:'base-prop',name:'属性'}, value:'', editor: function(){return new snakerflow.editors.inputExplainEditor("例子：#days>5?'TO1':'TO2'");}},
					handleClass: {name:'handleClass', tabs:{id:'base-prop',name:'属性'}, label: '处理类名称', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
				    preInterceptors: {name:'preInterceptors', tabs:{id:'base-prop',name:'属性'}, label : '前置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
					postInterceptors: {name:'postInterceptors', tabs:{id:'base-prop',name:'属性'}, label : '后置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}}
				}},
			fork : {
				showType: 'image',
				type : 'fork',
				name : {text:'<<fork>>'},
				text : {text:'并发'},
				img : {src : 'images/48/gateway_parallel.png',width :48, height:48},
				props : {
					//name:{name:'name',value:''},
					name: {name:'name',label: '名称', tabs:{id:'base-prop',name:'基本属性'}, value:'', editor: function(){return new snakerflow.editors.inputReadonly();}},
					displayName: {name:'displayName', tabs:{id:'base-prop',name:'基本属性'},isRequire:true, label: '显示名称', value:'', editor: function(){return new snakerflow.editors.textEditor();}},
					preInterceptors: {name:'preInterceptors', tabs:{id:'base-prop',name:'属性'}, label : '前置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
					postInterceptors: {name:'postInterceptors', tabs:{id:'base-prop',name:'属性'}, label : '后置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}}
				}},
			join : {
				showType: 'image',
				type : 'join',
				name : {text:'<<join>>'},
				text : {text:'合并'},
				img : {src : 'images/48/gateway_parallel.png',width :48, height:48},
				props : {
					//name:{name:'name',value:''},
					name: {name:'name',label: '名称', tabs:{id:'base-prop',name:'基本属性'}, value:'', editor: function(){return new snakerflow.editors.inputReadonly();}},
					displayName: {name:'displayName', tabs:{id:'base-prop',name:'基本属性'},isRequire:true, label: '显示名称', value:'', editor: function(){return new snakerflow.editors.textEditor();}},
					preInterceptors: {name:'preInterceptors', tabs:{id:'base-prop',name:'属性'}, label : '前置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}},
					postInterceptors: {name:'postInterceptors', tabs:{id:'base-prop',name:'属性'}, label : '后置拦截器', value:'', editor: function(){return new snakerflow.editors.inputEditor();}}
				}}
});
})(jQuery);