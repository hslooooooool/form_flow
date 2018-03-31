/**
 * @author 李湄强
 * 重新封装JqueryFileUpload插件
 * @param $
 */
(function($){
	/**
	 * jqueryFileUpload文件上传工具
	 */
	$.fn.jqueryFileUpload = function(options) {
		var setting = {
				uri:null,
				dataType:'json',
				autoUpload:true,
				formData:null,
				labelName:'上传',
				popWidth:450,
				popHeight:300,
				progressbar:false,
				limitUploadNum:0,
				acceptFileTypes:'/gif|jpg|png|txt|pdf|ppt|pptx|doc|docx|xls|xlsx|zip|tar|rar|gz|7z$/i',
				maxFileSize:104857600000,
				closeAfterFun:function() {}
		}
		var count = 0;
		setting = $.extend(true,setting,options);
		var jqXHR = null;
		var isStarting = false;
		var uploadDatas = null;
		var isSuccess = true;
		if(utils.isNotEmpty(setting.uri)) {
			//$(this).fileupload('destroy');
			var datas = null;
			jqXHR = $(this).fileupload({
		        url:setting.uri,
		        dataType: setting.dataType,
		        autoUpload:setting.autoUpload,
		        formData:setting.formData,
		        //acceptFileTypes:setting.acceptFileTypes,
		       // maxFileSize:setting.maxFileSize,
		        add:function(e,data) {
		        	count++;
		        	createShowPanel();
		        	$("#upload-wait-msg .start-upload").click(function(){
		        		if(!$(this).attr('disabled')) {
		        			if(count==0) {
			        			utils.showMsg("请选择"+setting.labelName+"文件！");
			        		} else {
			        			isStarting = true;
			        			$("#upload-wait-msg .start-upload").attr("disabled",true);
			        			for (var i = 0; i < datas.length; i++) {
			        				$("#upload-wait-msg").find("#upload-file"+datas[i].context+"-result").html(" <span class='upload-loading success'>正在"+setting.labelName+"...</span>");;
			        				datas[i].submit();
								}
			        		}
		        		}
		        	});
		        	data.context=count;
		        	var isCheck = true;
		        	if(setting.limitUploadNum>0) {
		        		isCheck = (setting.limitUploadNum == data.files.length);
		        	}
		        	if(isCheck) {
			        	$.each(data.files, function (index, file) {
			        		var ext = utils.getFileSuffix(file.name);
			        		var is = utils.checkRegexp(eval(setting.acceptFileTypes),ext);
			        		var p = "<p id='uload-file"+count+"'>"+file.name+"<span id='upload-file"+count+"-result'></span></p>";
			        		if(!is) {
			        			p = "<p id='uload-file"+count+"'>"+file.name+"<span id='upload-file"+count+"-result'> <span class='fail'><strong>该文件类型不支持"+setting.labelName+"</strong></span></span></p>";
			        			isCheck = isCheck && false;
			        		} 
			        		if(isCheck && setting.maxFileSize<file.size) {
			        			p = "<p id='uload-file"+count+"'>"+file.name+"<span id='upload-file"+count+"-result'> <span class='fail'><strong>该文件太大无法"+setting.labelName+"</strong></span></span></p>";
			        			isCheck = isCheck && false;
			        		}
			        		$("#upload-wait-msg .upload-msg-body").append(p);
			            });
		        	} else {
		        		$("#upload-wait-msg .upload-msg-body").append("只能上传"+setting.limitUploadNum+"个文件！");
		        		data.files = {};
		        	}
		        	if(!isCheck) {
		        		$("#upload-wait-msg .start-upload").attr("disabled",true);
		        	}
		        	if(datas == null) {
		        		datas = new Array();
		        	}
		        	datas.push(data);
		        	return true;
		        },
		        progressall:function(e, data){
		        	if(setting.progressbar) {
			        	var progress = parseInt(data.loaded/data.total * 100, 10);
			        	var $progressBar = $("#upload-wait-msg .progress-bar");
			        	$progressBar.css({"width":progress+"%"});
			        	$progressBar.attr("aria-valuenow",progress+"%");
			        	$progressBar.html(progress+"%");
		        	}
		        },
		        done:function(e, data) {
			        if(null != data._response.result && data._response.result != '') {
			          var output = data._response.result;//$.parseJSON(data.result);
			          count--;
			          var $uploadFileSpan = $("#upload-wait-msg").find("#upload-file"+data.context+"-result");			          
			          if(output.result == '1') {
			        	  isSuccess = isSuccess && true;
			        	  $uploadFileSpan.html(" <span class='success'>[<strong>成功</strong>]</span>");
			          } else {
			        	  $uploadFileSpan.html(" <span class='fail'>[<strong>失败</strong>]</span> <span class='fail'>原因："+output.msg+"</span>");
			          }
			          if(null == uploadDatas) {
			        	  uploadDatas = new Array();
			          }
			          var i = parseInt(data.context);
			          i = i>0?(i-1):i;
			          uploadDatas[i] = output.data;
			          if(count<1) { //判断文件是否全部上传完
			        	  jqXHR = null;
			        	  isStarting = false;
			        	  if(isSuccess) {
			        		  setTimeout(function(){
			        			  uploadWinClose(uploadDatas);
			        		  },1500);
			        	  }
			        	  datas = null;
			          }
			        }
			        data = null;
			    },
			    fail:function(e,data){
			    	count --;
			    	if(count == 0) {
			    		utils.showMsg('文件上传失败');
			    		datas = null;
			    	}
			        data = null;
			    }
		    });
		};
		
		/**
		 * 创建上传面板
		 */
		var createShowPanel = function() {
			var isCreate = true;
			if($("#upload-wait-msg").attr("id") == undefined) {
				$("body").append("<div id='upload-wait-msg'><div class='upload-msg-body'></div></div>");
				$("#upload-wait-msg").hide();
			} else {
				isCreate = false;
			}
			if($("#shadow").attr("id") == undefined) {
				$("body").append("<div id='shadow'></div>");
			}
			if(isCreate) {
				$("#upload-wait-msg .upload-msg-body").before("<div id='upload-title' style=''>"+setting.labelName+"文件</div>");
				$("#upload-wait-msg .upload-msg-body").after("<div id='upload-bottom' class='text-right p-r-5'><div>"+
				    "<span class='btn btn-primary btn-sm fileinput-button start-upload start'><i class='glyphicon glyphicon-circle-arrow-up'></i> <span>开始"+setting.labelName+"</span></span> &nbsp;&nbsp;"+
					"<span class='btn btn-warning btn-sm fileinput-button upload-close cancel'><i class='glyphicon glyphicon-ban-circle'></i> <span>&nbsp;关闭&nbsp;</span></span>"+
					"</div>");
				if(setting.progressbar) {
					$("#upload-wait-msg .upload-msg-body").after("<div class='progress m-l-5 m-r-5'>"+
					"<div class='progress-bar' role='progressbar' aria-valuenow='0%' aria-valuemin='0' aria-valuemax='100' style='width: 0%;'>0%</div>");
				}
				$("#upload-bottom .upload-close").click(function(){
					if(null != jqXHR && isStarting && typeof(jqXHR.abort) === 'function') {
						jqXHR.abort();
						jqXHR = null;
					}
					datas = null;
					uploadWinClose(uploadDatas);
			    });
				var w = setting.popWidth;
				var h = setting.popHeight;
				$("#upload-wait-msg .upload-msg-body").css({"height":(h-90)+"px"});
				
				var top = $(document).scrollTop();
				var index = $("#shadow").css("z-index")/1+1/1;
				var sw = ($(window).width()-w)/2;
				var sh = ($(window).height()-h)/2+top;
				$("#upload-wait-msg").css({"top":sh+"px","left":sw+"px","width":w+"px","height":h+"px","padding":"0px","z-index":index});
			}
			$("#shadow").show();
			$("#upload-wait-msg").show();
		};
		
		/**
		 * 
		 */
		var uploadWinClose = function(datas) {
			$("#upload-wait-msg").remove();
			$("#shadow").hide();
			var fun = eval(setting.closeAfterFun);
			if(typeof(fun) == 'function')
			     fun(datas);
		};
	}
})(jQuery)