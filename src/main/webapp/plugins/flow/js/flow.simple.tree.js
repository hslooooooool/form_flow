/**
 * 简单树形监听(主要是用来实现树形的展开、关闭)，监听HTML标签
 */
function simpleTreeListener(tag) {
	tag = utils.isEmpty(tag)?"":tag+" ";
	$(tag+" .tr-parent-tree").click(function(){
 	   var $this = $(this);
	   //var classNames = $this.attr("class");
 	   if($this.hasClass("shrink-data")) {
		   $this.removeClass("shrink-data");
		   $this.addClass("expand-data");
		   var $uiIcon = $this.find("span.ui-icon");
		   $uiIcon.removeClass("ui-icon-triangle-1-e");
		   $uiIcon.addClass("ui-icon-triangle-1-s");
		   $(tag+" ."+$this.attr("id")).show();
	   } else {
		   $this.removeClass("expand-data");
		   $this.addClass("shrink-data");
		   var $uiIcon = $this.find("span.ui-icon");
		   $uiIcon.removeClass("ui-icon-triangle-1-s");
		   $uiIcon.addClass("ui-icon-triangle-1-e");
		   $(tag+" ."+$this.attr("id")).hide();
	   }
   });
	
}