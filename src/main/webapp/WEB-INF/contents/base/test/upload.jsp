<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
	<div class="m-t-20 m-b-20">
		<div class="file-upload">
			<span class="btn btn-success btn-sm fileinput-button upload-add">
				<i class="glyphicon glyphicon-plus"></i> <span>添加附件</span>
				<input class="cnoj-upload" data-uri="pictrue/upload.json" data-close-after="test" 
				data-accept-file-types="jpg,gif,png,xls,xlsx" id="upload-m-file" type="file" name="file" multiple />
			</span> 
			<div class="clear"></div>
			<div class="help-block">文件大小不超过1000M，文件类型为：zip，rar，pdf，ppt，pptx，doc，docx，xls，xlsx，txt，png，jpg，gif.</div>
		    <br />
		</div>
		<br />
	</div>
	<script type="text/javascript">
    function test(datas) {
    	if(utils.isNotEmpty(datas))
	    	for(var i=0;i<datas.length;i++) {
	    		alert(JSON.stringify(datas[i]));
	    	}
    }
	</script>
</div>