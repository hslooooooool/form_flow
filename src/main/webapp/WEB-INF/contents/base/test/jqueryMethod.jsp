<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
	<h3>测试一</h3>
	<div class="test1">
		<input id="t11" value=""  />
		<input id="t12" value=""  />
		<input id="jf_test1" value="测试1" />
	</div>
	
	<h3>测试二</h3>
	<div class="test1">
	    <input id="t21" value=""  />
		<input id="t22" value=""  />
		<span>*</span>
		<input id="jf_test2" value="测试2" />
	</div>
	<script type="text/javascript">
		$(function(){
			var $test1 = $("#jf_test1");
			var $test2 = $("#jf_test2");
			var is = true;
			var $prev = utils.findPrevTag($test1,"input");
			//alert($test1.prop("tagName"));
			$prev.val($test1.val());
			$prev = utils.findPrevTag($test2,"input");
			$prev.val($test2.val());
			
			var is = false;
			var value = "2016年10月15日";
			is = utils.checkDate(value);
			 console.log(is);
			//$("#test1").
		});
	</script>
</div>