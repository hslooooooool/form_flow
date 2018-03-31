/**
 * @author lmq
 */
/**
 * 弹出式提示窗口
 * @param width
 * @param height
 * @returns {PopoversToolTip}
 */
function PopoversToolTip(width,height) {
	this.width = utils.isEmpty(width)?200:width;
    this.height = utils.isEmpty(height)?100:height;
}

/**
 * 
 * @param title
 * @param content
 * @param typeId
 */
PopoversToolTip.prototype.create = function(title,content,typeId){
	var $popoversTooltip = $(".panel-popovers-tooltip").last();
	var isCreatePanel = true;
	var zIndex = 1000;
	if(!utils.isEmpty($popoversTooltip.attr("class"))) {
		zIndex = $popoversTooltip.css("z-index");
		zIndex = parseInt(zIndex)+1;
	}
	if(!utils.isEmpty(typeId)) {
		var $typeTag = $("#"+typeId);
		if(typeof($typeTag.attr("id")) === 'undefined') {
			isCreatePanel = true;
		} else {
			isCreatePanel = false;
			$typeTag.find(".panel-body").html(content);
			if(typeof($popoversTooltip.attr("id")) !== 'undefined' && 
					$popoversTooltip.attr("id") != typeId) {
				$typeTag.css({"z-index":zIndex,"height":"0"});
				$typeTag.show();
				$typeTag.animate({height:this.height+"px"},'slow');
			}
		}
	} else {
		typeId = "popovers-tooltip-"+new Date().getTime()+randomNum(5);
	}
	if(isCreatePanel) {
		var html = '<div class="panel-popovers-tooltip panel panel-primary" id="'+typeId+'" style="width:'+this.width+'px;z-index:'+zIndex+'">'+
		'<div class="panel-heading">'+title+'<a class="close"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></a></div>'+
		'<div class="panel-body">'+content+'</div></div>';
		$("body").append(html);
		var $html = $("#"+typeId);
		$html.animate({height:this.height+"px"},'slow');
	}
	$(".panel-popovers-tooltip .close").click(function(){
		$popoversTooltip = $(this).parents(".panel-popovers-tooltip");
		$popoversTooltip.animate({height:"0px"},'slow',function(){
			$popoversTooltip.hide();
			$popoversTooltip.remove();
		});
	});
}