/**
 * @author lmq
 * 重新封装jqGrid插件
 * @param $
 */
(function($){
	$.fn.jqGridUtil = function(opts){
		var defaultOpts = {
				searchPanelTag:null,
				subtractHeight:null,
				autoRow:false
		};
		opts = $.extend(defaultOpts, opts);
		var ROW_HEIGHT = 27;
		var HEADER_HEIGHT = 32;
		var FOOTER_HEIGHT = 35;
		
		var searchPanelTag = opts.searchPanelTag;
		var subtractHeight = opts.subtractHeight;
		subtractHeight = utils.isEmpty(subtractHeight)?0:subtractHeight;
		var h = getMainHeight()-subtractHeight-getTabHeaderHeight();
		var searchPanelHeight = 0;
		if(utils.isNotEmpty(searchPanelTag)) {
			var $searchPanel = $(searchPanelTag);
			if($searchPanel.length>0) {
				searchPanelHeight = $searchPanel.outerHeight(true);
				//监听搜索表单
				var self = this;
				$searchPanel.find("form").on('submit', function() {
					var param = $(this).serialize();
					if(utils.isNotEmpty(param)) {
						var url = utils.isContain(opts.url,"?")?(opts.url+"&"):(opts.url+"?");
						url = url+param;
						$(self).jqGrid("setGridParam",{url:url}).trigger("reloadGrid",[{page:1}]);
					}
					return false;
				});
			} 
		}
		var jqGridH = 0;
		if(utils.isEmpty(opts.caption)) {
			jqGridH = HEADER_HEIGHT + FOOTER_HEIGHT;
		} else {
			jqGridH = HEADER_HEIGHT * 2 + FOOTER_HEIGHT;
		} 
		h = h - searchPanelHeight - jqGridH - 3;
		var w = $("#main-content").width() - 5;
		var rowNum = 15;
		if(opts.autoRow) {
			var rowNumTmp = Math.floor(h/ROW_HEIGHT) - 1;
			rowNum = (rowNum > rowNumTmp)?rowNum:rowNumTmp;
		}
		var setting = opts;
		opts = $.extend(opts,{
			height: h,
			width: w,
			rowNum: rowNum,
			rowList: [10, rowNum, 20, 30, 40],
			sortable:false
		});
		opts = $.extend(opts,setting);
	 return $(this).jqGrid(opts);
	}
})(jQuery);