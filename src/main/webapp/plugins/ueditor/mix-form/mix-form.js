/**
 * 扩展ueditor插件
 */
UE.mixFormUrl = 'mix-form';
/**
 * tabs插件
 * dependencies[utils.js,ueditor.all.js,jquery]
 */
UE.plugins['tabs'] = function() {
	var editor = this,thePlugins = 'tabs';
	editor.commands[thePlugins] = {
			execCommand:function () {
				var dialog = new UE.ui.Dialog({
					iframeUrl:this.options.UEDITOR_HOME_URL + UE.mixFormUrl+'/tabs.html',
					name:thePlugins,
					editor:this,
					title: '选项卡',
					cssRules:"width:600px;height:300px;",
					buttons:[
					{
						className:'edui-okbutton',
						label:'确定',
						onclick:function () {
							dialog.close(true);
						}
					},
					{
						className:'edui-cancelbutton',
						label:'取消',
						onclick:function () {
							dialog.close(false);
						}
					}]
				});
				dialog.render();
				dialog.open();
			}
	};
	var popup = new baidu.editor.ui.Popup({
		editor:this,
		content: '',
		className: 'edui-bubble',
		_delete:function(){
			var labelName = this.anchorEl.innerText;
			if( window.confirm('确认删除“'+labelName+'”选项卡吗？') ) {
				var href = this.anchorEl.getAttribute('href');
				if(utils.isEmpty(href))
					return false;
				var html = editor.getContent();
			    var htmlObj = $('<div>'+html+'</div>');
			    var $a = htmlObj.find("#"+this.anchorEl.getAttribute('id'));
			    var $li = $a.parent();
			    var $ul = $li.parent();
			    var active = $li.hasClass("active");
			    $li.remove();
			    var $tabPane = htmlObj.find(href);
			    var $tabContent = null;
			    if($tabPane.attr("class")) {
			    	$tabContent = $tabPane.parent();
			    	$tabPane.remove();
			    }
			    //判断ul中是否还有li
			    $li = $ul.find("li:eq(0)");
			    if($li.is("li")) {
			    	if(active) {
			    		//设置下一个tab为激活状态
				    	$li.addClass("active");
				    	if($tabContent)
				    		$tabContent.find(".tab-pane:eq(0)").addClass("active");
			    	}
			    } else {
			    	//删除tabs
			    	$tabContent = (null == $tabContent)?$ul.next():$tabContent;
			    	if($tabContent.hasClass("tab-content")) 
			    		$tabContent.remove();
			    	$ul.remove();
			    }
			    editor.setContent(htmlObj.html());
			}//if
			this.hide();
		},
		_addtext: function() {
			var html = editor.getContent();
		    var htmlObj = $('<div>'+html+'</div>');
		    var $a = htmlObj.find("#"+this.anchorEl.getAttribute('id'));
			var $li = $a.parent();
			var $tabContent = htmlObj.find(this.anchorEl.getAttribute('href')).parent();

			uuid = utils.UUID();
			var labelName = '新增选项卡';
			$li.after('<li role="presentation"><a id="'+uuid+'" mixform-plugins="a_tabs" href="#tab-'+uuid+'" role="tab" data-toggle="tab"> '+labelName+' </a></li>');
			$tabContent.append('<div role="tabpanel" class="tab-pane" id="tab-'+uuid+'"><br/><p>这里添加“'+labelName+'”选项卡的内容</p></div>');
			editor.setContent(htmlObj.html());
			/*baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			editor.execCommand(thePlugins);
			this.hide();*/
		}
	} );
	popup.render();
	editor.addListener('mouseover', function( t, evt ) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
        var leipiPlugins = el.getAttribute('mixform-plugins');
		if (leipiPlugins=='a_tabs') {
			var html = popup.formatHtml(
				'<nobr>文本框: <span onclick=$$._addtext() class="edui-clickable">添加</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>' );
			if ( html ) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor( popup.anchorEl );
			} else {
				popup.hide();
			}
		}
	});
}