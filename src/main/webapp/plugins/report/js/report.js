/**
 * 创建表
 */
(function($){
	
	/**
	 * 报表属性监听
	 */
	$.fn.reportPropListener = function() {
		var $this = $(this);
		var seqNum= 1;
		//添加字段
		$(".add-field").click(function(){
			var $tbody = $this.find("table tbody");
			if(!utils.isEmpty($tbody.html())) {
				var $lastTr = $tbody.find("tr:last");
				var lastSeqNum = $lastTr.find(".seq-num").text();
				seqNum = parseInt(lastSeqNum)+1;
			}
			var fieldElementTr = "<tr id=\"tr"+seqNum+"\"><td class=\"seq-num text-right\" style=\"width: 40px;\">"+
			"<input type=\"hidden\" class=\"sort-order\" name=\"fields["+(seqNum-1)+"].sortOrder\" value=\""+seqNum+"\" /><span class=\"sort-order-label\">"+seqNum+"</span></td>"+
            "<td style=\"width: 120px;\"><input id=\"title"+seqNum+"\" name=\"fields["+(seqNum-1)+"].title\" class=\"form-control\" placeholder=\"请填写标题，必填\" title=\"必填项\" type=\"text\"  /></td>"+
            "<td style=\"width: 80px;\"><input class=\"form-control\" name=\"fields["+(seqNum-1)+"].width\" type=\"text\" placeholder=\"自动宽度\" /></td>"+
            "<td style=\"width: 200px;\"><input class=\"form-control\" type=\"text\" name=\"fields["+(seqNum-1)+"].url\" placeholder=\"URL地址\" /></td>"+
            "<td style=\"width: 100px;\"><select class=\"form-control cnoj-select\" name=\"fields["+(seqNum-1)+"].openUrlType\" data-uri=\"dict/item/OPEN_URL_TYPE.json\"><option value=''>请选择</option></select></td>"+
            "<td style=\"width: 120px;\"><input class=\"form-control\" name=\"fields["+(seqNum-1)+"].paramName\" title=\"多个参数用英文逗号分隔，如果没有请为空\" placeholder=\"多个参数用英文逗号分隔，如果没有请为空\" type=\"text\"  /></td>"+
            "<td style=\"width: 120px;\"><input class=\"form-control\" name=\"fields["+(seqNum-1)+"].paramValue\" title=\"多个参数引用下标用英文逗号分隔，如果没有请为空\" placeholder=\"多个参数引用下标用英文逗号分隔，如果没有请为空\" type=\"text\"  /></td>"+
            "<td style=\"width: 120px;\"><input class=\"form-control\" name=\"fields["+(seqNum-1)+"].searchName\" title=\"填写搜索变量，如果该标题不是搜索项，请为空\" placeholder=\"填写搜索变量，如果该标题不是搜索项，请为空\" type=\"text\"  /></td>"+
            "<td><input class=\"form-control\" type=\"text\" name=\"fields["+(seqNum-1)+"].customClass\" title=\"自定义实现类，需要实现ICustomCellCallback接口\" placeholder=\"自定义实现类，需要实现ICustomCellCallback接口\" /></td>"+
            "<td class=\"del-td\" id=\"del"+seqNum+"\" style=\"width: 40px;\" class=\"text-center\"></td>"+
            "</tr>";
			$tbody.append(fieldElementTr);
            $("<button type=\"button\" title=\"删除\" class=\"close text-center\" style=\"float: none;font-size: 18px;\" data-dismiss=\"tr1\" aria-label=\"Close\"><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i></button>").click(function(){
                $(this).parent().parent().remove();
                resort();
            }).appendTo("#del"+seqNum);
            selectListener($this);
		});

		//删除字段
		$(".close").click(function(){
			$(this).parent().parent().remove();
			resort();
		});
		
		/**
		 * 重新排序
		 */
		function resort() {
		  //重新排序
            var index = 1;
            var $tbody = $this.find("table tbody");
            $tbody.find("tr").each(function(){
                var $tr = $(this);
                $tr.attr("id", "tr"+index);
                $tr.find(".sort-order").val(index);
                $tr.find(".sort-order-label").text(index);
                $tr.find(".del-td").attr("id","del"+index);
                $tr.find("input,select").each(function(){
                    var $element = $(this);
                    var name = $element.attr("name");
                    name = name.replace(/fields\[\d+\]\./, "fields["+(index-1)+"].");
                    $element.attr("name", name);
                });
                index++;
            });
		}
		
		/**
		 * 保存报表设计
		 */
		$("#save-designer").click(function(){
		    if($("#report-designer-form").validateForm()) {
		        var formData = $("#report-designer-form").serialize();
		        utils.waitLoading("正在提交数据...");
		        $.post('report/save', formData, function(response){
		            utils.closeWaitLoading();
		            utils.showMsg(response.msg);
		            if(response.result==1) {
		                closeActivedTab();
		            }
		        });
		    }
		});
	}
})(jQuery);