/**
 * 文件域根据内容自适应高度
 */
(function($){
  $.fn.autoTextarea = function(options) {
    var defaults={
      maxHeight:null,
      minHeight:$(this).outerHeight()
    };
    var opts = $.extend({},defaults,options);
    return $(this).each(function() {
      $(this).on("paste cut keyup focus blur",function(){
        var height,style=this.style;
        this.style.height = opts.minHeight + 'px';
        if (this.scrollHeight > opts.minHeight) {
          if (opts.maxHeight && this.scrollHeight > opts.maxHeight) {
            height = opts.maxHeight;
            style.overflowY = 'scroll';
          } else {
            height = this.scrollHeight;
            style.overflowY = 'hidden';
          }
          style.height = height + 'px';
        }
      });
    });
  };
})(jQuery);