/**
 * 消息推送
 */
+function(window, $) {

	var MessagePush = function(options) {
		this.options = $.extend(true, {
			cid:null,
			theme:'panel-default',
			remindCallback:null
		},options);
	}
	MessagePush.prototype = {
		init : function() {
			var that = this;
			// 引擎事件绑定
			JS.Engine.on({
				start : function(cId, channels, engine) {
					//TODO 开始时执行该方法
					//console.log("CID:",cId);
					//var style = engine.getConnector().workStyle;
					//console.log(style === 'stream'?'长连接':'长轮询');
				},
				stop : function(cause, url, cId, engine) {
					//TODO 断开时执行该方法
				},
				notice : function(data, timespan, engine) {
					//TODO 通知公告提醒
					that.createPopPanel("message-push", "", "通知公告", data);
				},
				message : function(data, timespan, engine) {
					//TODO 消息提醒
					that.createPopPanel("message-push", "", "消息提醒", data);
				},
				todo : function(data, timespan, engine) {
					//TODO 待办提醒
					that.createPopPanel("todo-push", "", "待办提醒", data);
				}
			});
			JS.Engine.start('conn',"cid="+this.options.cid);
		},
		createPopPanel : function(id,titleIcon,title,datas) {
			if(utils.isEmpty(titleIcon)) {
				titleIcon = "fa-bell";
			}
			if(utils.isEmpty(title)) {
				title = "消息提醒";
			}
			//var idTag = "mp-"+id;
			var idTag = "mp-"+utils.UUID();
			var $messagePushWrap = $("#"+idTag);
			var html = "";
			if($messagePushWrap.length == 0) {
				html += "<div id='"+idTag+"' class=\"message-push-wrap\">";
				html += "<div class=\"panel "+this.options.theme+"\">";
				html += "<div class=\"panel-heading\"><i class=\"fa "+titleIcon+"\"></i> "+title+" <div class=\"right\"><i class=\"fa fa-times pop-close\" title='关闭' aria-hidden=\"true\"></i> <!--<a href=\"javascript:void(0)\" class='pop-close' title=\"关闭\">x</a>--></div></div>";
				html += "<div class=\"panel-body\">";
				html += "</div></div></div>";
				$("body").append(html);
				$messagePushWrap = $("#"+idTag);
			} //if
			this.createContent(idTag, datas);
			this.closeListener(idTag);
			$messagePushWrap.slideDown("fast",function(){
				$messagePushWrap.draggable({handle: '.panel-heading', containment: "#main-content",cursor: "move",scroll: false });
			});
			if(typeof(this.options.remindCallback) == 'function') {
				this.options.remindCallback(id, title, datas);
			}
		},
		createContent : function(id,datas) {
			var $panelBody = $("#"+id).find(".panel-body");
			var contents = "<div class='remind-wrap'>";
			contents += "<div class='remind-title'>"+datas.title+"</div>";
			contents += "<div class='remind-content'>"+datas.content+"</div>";
			contents += "</div>";
			$panelBody.html(contents);
		},
		closeListener : function(idTag) {
			$("#"+idTag+".message-push-wrap .pop-close").click(function() {
				var $messagePushWrap = $(this).parents(".message-push-wrap:eq(0)");
				$messagePushWrap.slideUp("fast",function(){
					$messagePushWrap.remove();
				});
			});
		}
	}
	window.MessagePush = MessagePush;
}(window, jQuery)