<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog">
        <form class="form-horizontal" role="form" id="form-edit" action="form/helper/update.json">
        	<input type="hidden" name="id" value="${objBean.id }" />
        	<input type="hidden" name="userId" value="${objBean.userId }" />
            <div class="form-group m-b-10">
			    <label for="input01" class="col-sm-1 control-label">标题</label>
			    <div class="col-sm-11 p-l-0">
			      <input type="text" class="form-control require" name="title" data-label-name="标题" id="input01" value="${objBean.title }" />
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="input02" class="col-sm-1 control-label">状态</label>
			    <div class="col-sm-11 p-l-0">
			       <select class="form-control" name="state" id="input02" >
			            <option value="1" ${objBean.state==1?'selected':''}>有效</option>
					    <option value="0" ${objBean.state==0?'selected':''}>无效</option>
			       </select>
			    </div>
			</div>
			<div class="form-group m-b-10">
			    <label for="container" class="col-sm-1 control-label">帮助内容</label>
			    <div class="col-sm-11 p-l-0">
			      <script id="container" name="content" type="text/plain">${objBean.content}</script>
			    </div>
			</div>
			<div class="text-right p-t-3 p-r-10">
			      <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="form/helper/list">
			      <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
			</div>
        </form>
</div><!-- wrap-content-dialog -->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugins/ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
	$(function(){
		setTimeout(function() {
			var h = $(window).height() - 300;
			$("#container").height(h);
			var ue = UE.getEditor('container',{
				zIndex : 1051,
				autoHeightEnabled:false,
				toolbars: [[
				            /*'fullscreen',*/
				            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'blockquote', '|', 'forecolor', 'insertorderedlist', 'insertunorderedlist', '|',
				            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
				            'paragraph', 'fontfamily', 'fontsize', '|', 'indent', '|',
				            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
				            'link', 'unlink', 'anchor', '|','pagebreak','|',
				            'horizontal', 'date', 'time', 'spechars', '|',
				            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts'
				        ]]
			});
		}, 300);
	});
</script>