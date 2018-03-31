<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
	<div class="container-fluid m-t-20 m-b-20 ">
		<div class="file-upload">
			<form class="form-horizontal" role="form">
			   <div class="form-group">
			    <label for="datetime-test" class="col-sm-2 control-label">日期时间</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control cnoj-datetime" id="sss" />
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="date-test" class="col-sm-2 control-label">日期</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control cnoj-date" data-start-date="2014-10-01" data-end-date="2014-10-10" id="date-test" >
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="time-test" class="col-sm-2 control-label">时间</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control cnoj-time" data-start-date="2014-10-09" data-end-date="2014-10-10" id="time-test">
			    </div>
			  </div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	//setTimeout("jsLoad()", 200);
	 /* function jsLoad() {
		  $(".cnoj-datetime,.cnoj-date,.cnoj-time").each(function(){
			  var $this = $(this);
			  $this.datetimepicker('remove');
			  var $formGroup = $(this).parents(".form-group");
			  var classParentNames = $formGroup.attr("class");
			  var classNames = $this.attr("class");
			  if(typeof(classParentNames) !== 'undefined' && !isContain(classParentNames,'has-feedback')) {
				  $formGroup.addClass("has-feedback");
				  $(this).after("<span class='glyphicon glyphicon-calendar form-control-feedback'></span>");
				  var setting = {};
				  if(isContain(classNames,'cnoj-datetime')) {
					  setting = {format: 'yyyy-mm-dd hh:ii:ss',startView:2};
				  } else if(isContain(classNames,'cnoj-date')) {
					  setting = {format: 'yyyy-mm-dd',minView: 2};
				  } else if(isContain(classNames,'cnoj-time')) {
					  var date = new Date();
					  var month = date.getMonth()+1;
					  month = month<10?('0'+month):month;
					  var day = date.getDate();
					  day = day<10?('0'+day):day;
					 var startDate = date.getFullYear()+"-"+month+"-"+day;
					  setting = {format: 'hh:ii:ss',startDate:startDate,startView: 1,minView: 0,maxView:1};
				  }
				  $this.datetimepicker(setting).on('show',function(){
					  $this.prop("readonly",true);
				  }).on('hide',function(){
					  $this.prop("readonly",false);
				  });
			  }
		  });
	  }*/
	</script>
</div>