/**
 * @author lmq
 * 适应IE低版本
 */
$(document).ready(function(){
	adjustHeight();
});

function adjustHeight() {
	if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){
		$(".row-h-1,.row-h-2,.row-h-3,.row-h-4,.row-h-5,.row-h-6,.row-h-7,.row-h-8,.row-h-9,.row-h-10").each(function(){
			var h = $(window).height();
			var wrapH = 0;
			var tagDiv = $(this).attr("class");
			if(isContain(tagDiv,"row-h-1")) {
				wrapH = parseInt(h*0.1);
			} else if(isContain(tagDiv,"row-h-2")) {
				wrapH = parseInt(h*0.2);
			} else if(isContain(tagDiv,"row-h-3")) {
				wrapH = parseInt(h*0.3);
			} else if(isContain(tagDiv,"row-h-4")) {
				wrapH = parseInt(h*0.4);
			} else if(isContain(tagDiv,"row-h-5")) {
				wrapH = parseInt(h*0.5);
			} else if(isContain(tagDiv,"row-h-6")) {
				wrapH = parseInt(h*0.6);
			} else if(isContain(tagDiv,"row-h-7")) {
				wrapH = parseInt(h*0.7);
			} else if(isContain(tagDiv,"row-h-8")) {
				wrapH = parseInt(h*0.8);
			} else if(isContain(tagDiv,"row-h-9")) {
				wrapH = parseInt(h*0.9);
			} else if(isContain(tagDiv,"row-h-10")) {
				wrapH = parseInt(h*1);
			}
			$(this).height(wrapH);
		});
	}
}