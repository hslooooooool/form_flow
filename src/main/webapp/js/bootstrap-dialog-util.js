/**
 * @author lmq
 * 重新封装bootstrap弹出窗口
 * @returns
 */
var BootstrapDialogUtil = function() {
	
}

/**
 * 弹出一个uri窗口
 * @param title
 * @param uri
 * @param popWidth
 * @param bgColor
 * @param isFooter
 * @param callback 页面加载成功后执行回调函数
 * @param closable 是否有能力关闭
 * @param isCloseBtn 是否有关闭按钮
 */
BootstrapDialogUtil.loadUriDialog = function(title,uri,popWidth,bgColor,isFooter,callback, closable, isCloseBtn) {
	if(isNaN(popWidth) || typeof(popWidth) === 'undefined' || popWidth == null) {
		popWidth = 600;
	}
	if(typeof(isFooter) === 'undefined') {
		isFooter = false;
	}
    if(typeof(closable) === 'undefined') {
        closable = true;
    }
    if(typeof(isCloseBtn) === 'undefined') {
        isCloseBtn = true;
    }
	$.ajax({
		 url: uri,global: false,type: "POST",dataType: "html",async:false,
	     success: function(msg){
	    	 if(typeof(callback) !== 'function') {
	    		 callback = null;
		     }
	    	 BootstrapDialog.show({
	 			title:title,draggable: true,width:popWidth,backgroundColor:bgColor,
	 			message: function(dialog) {
	 				var html = dialog.getModalFooter();
	 				if(!isFooter)
	 				    $(html).find(".bootstrap-dialog-footer").parent().addClass("not-modal-footer");
	 				else 
	 					$(html).find(".bootstrap-dialog-footer").parent().removeClass("not-modal-footer");
	 				return msg;
	 			},
	 			callback:callback,
                 closable:closable,
                 isCloseBtn: isCloseBtn
	 		});
	       
	     }
	 });
}

/**
 * 弹出一个DIV对话框
 * @param title
 * @param id
 * @param popWidth
 * @param bgColor
 * @param isFooter
 */
BootstrapDialogUtil.dialog = function(title,id,popWidth,bgColor,isFooter) {
	if(isNaN(popWidth) || typeof(popWidth) === 'undefined' || popWidth == null) {
		popWidth = 600;
	}
	if(typeof(isFooter) === 'undefined') {
		isFooter = false;
	}
	 BootstrapDialog.show({
	 	title:title,draggable: true,width:popWidth,backgroundColor:bgColor,
	 	message: function(dialog) {
	 		var html = dialog.getModalFooter();
	 		if(!isFooter)
	 			$(html).find(".bootstrap-dialog-footer").parent().addClass("not-modal-footer");
	 		else 
	 			$(html).find(".bootstrap-dialog-footer").parent().removeClass("not-modal-footer");
	 		return $(id).html();
	 	}
	 });
}

/**
 * 弹出指定内容的窗口
 * @param title
 * @param contents
 * @param popWidth
 * @param bgColor
 * @param isFooter
 * @param callback
 */
BootstrapDialogUtil.dialogContent = function(title,contents,popWidth,bgColor,isFooter,callback, closable, isCloseBtn) {
	if(isNaN(popWidth) || typeof(popWidth) === 'undefined' || popWidth == null) {
		popWidth = 600;
	}
    if(typeof(closable) === 'undefined') {
        closable = true;
    }
    if(typeof(isFooter) === 'undefined') {
        isFooter = false;
    }
    if(typeof(isCloseBtn) === 'undefined') {
        isCloseBtn = true;
    }
    var dialogInstance = null;
	BootstrapDialog.show({
	 	title:title,draggable: true,width:popWidth,backgroundColor:bgColor,
	 	message: function(dialog) {
            dialogInstance = dialog;
	 		var html = dialog.getModalFooter();
	 		if(!isFooter)
	 			$(html).find(".bootstrap-dialog-footer").parent().addClass("not-modal-footer");
	 		else 
	 			$(html).find(".bootstrap-dialog-footer").parent().removeClass("not-modal-footer");
	 		return contents;
	 	},
        closable:closable,
        isCloseBtn:isCloseBtn
	 });
	 if(typeof(callback) !== 'undefined' && null != callback && typeof(callback) === 'function') {
  	   callback(dialogInstance);
     }
}

/**
 * 确认对话框
 * @param msg
 * @param callback
 */
BootstrapDialogUtil.confirmDialog = function(msg,callback) {
	BootstrapDialog.show({
		title:"提示",message:msg,
		buttons: [{
            label: '确定',cssClass: 'btn-primary btn-sm',icon:'glyphicon glyphicon-ok-sign',
            action: function(dialog) {
            	if(typeof(callback) != 'undefined' && 
            					callback != null && 
            					typeof(callback) == 'function') {
            				callback();
            	}
            	dialog.close();
            }
        }, {
            label: '取消',cssClass: 'btn-warning btn-sm',icon:'glyphicon glyphicon-remove-sign',
            action: function(dialog) {
                dialog.close();
            }
        }]
   });
}

/**
 * 删除对话框
 * @param name
 * @param uri
 * @param ids
 * @param callback
 */
BootstrapDialogUtil.delDialog = function(name,uri,ids,callback) {
	BootstrapDialog.show({
		title:"提示",message:"确定要删除<strong>“"+name+"”</strong>吗？",
		buttons: [{
            label: '确定',cssClass: 'btn-primary btn-sm',icon:'glyphicon glyphicon-ok-sign',
            action: function(dialog) {
            	$.post(uri,{id:ids},
            	    function(data){
            		if(data.result=='1') {
            			if(typeof(callback) != 'undefined' && 
            					callback != null && 
            					typeof(callback) == 'function') {
            				callback();
            			}
            		} else {
            			BootstrapDialog.show({title:"提示",message:data.msg,type:BootstrapDialog.TYPE_DANGER});
            		}
            		dialog.close();
            	});
            }
        }, {
            label: '取消',cssClass: 'btn-warning btn-sm',icon:'glyphicon glyphicon-remove-sign',
            action: function(dialog) {
                dialog.close();
            }
        }]
   });
}

/**
 * 提示信息
 * @param msg
 */
BootstrapDialogUtil.infoAlert = function(msg) {
	BootstrapDialog.show({title:"提示",message:msg,type:BootstrapDialog.TYPE_PRIMARY,
		buttons: [{
			label: '确定',cssClass: 'btn-primary btn-sm',icon:'glyphicon glyphicon-ok-sign',
	        action: function(dialogItself){
	            dialogItself.close();
	        }
	    }]
	});
}

/**
 * 提示信息
 * @param msg
 */
BootstrapDialogUtil.warningAlert = function(msg) {
	BootstrapDialog.show({title:"提示",message:msg,type:BootstrapDialog.TYPE_DANGER,
		buttons: [{
			label: '确定',cssClass: 'btn-primary btn-sm',icon:'glyphicon glyphicon-ok-sign',
	        action: function(dialogItself){
	            dialogItself.close();
	        }
	    }]	
	});
}

/**
 * 关闭弹出窗口
 */
BootstrapDialogUtil.close = function() {
	BootstrapDialog.closeAll();
}