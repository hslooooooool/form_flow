/**
 * 创建表
 */
(function($){
	
	/**
	 * 创建表单监听
	 */
	$.fn.createTableListener = function() {
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
			"<input type=\"hidden\" name=\"fields["+(seqNum-1)+"].sortOrder\" value=\""+seqNum+"\" />"+seqNum+"</td>"+
            "<td style=\"width: 180px;\"><div class=\"col-sm-12 p-l-0 p-r-0\"><input type=\"text\" id=\"file-name"+seqNum+"\" class=\"form-control input-sm require\" data-label-name=\"字段名称\" name=\"fields["+(seqNum-1)+"].fieldName\" placeholder=\"请输入字段名\" /></div></td>"+
            "<td style=\"width: 150px;\"><div class=\"col-sm-12 p-l-0 p-r-0\"><select class=\"cnoj-select field-type form-control input-sm\" data-uri=\"dict/item/TABLE_FIELD_DATA_FORMAT.json\" style=\"width: 140px;\" name=\"fields["+(seqNum-1)+"].dataFormat\"></select></div></td>"+
            "<td style=\"width: 150px;\"><div class=\"col-sm-12 p-l-0 p-r-0\"><input type=\"text\" class=\"form-control field-length input-sm\" data-format=\"num\" data-step=\"1\" name=\"fields["+(seqNum-1)+"].length\" /></div></td>"+
            "<td><input type=\"text\" class=\"form-control require input-sm\" name=\"fields["+(seqNum-1)+"].fieldRemark\" /></td>"+
            "<td id=\"del"+seqNum+"\"></td>"+
            "</tr>";
			$tbody.append(fieldElementTr);
			$("<button type=\"button\" title=\"删除\" class=\"close\" data-dismiss=\"tr1\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>").click(function(){
				$(this).parent().parent().remove();
			}).appendTo("#del"+seqNum);
            selectListener($this);
            formRequireListener($this);
            $(".field-type").off('change');
            _fieldTypeListner();
		});
        _fieldTypeListner();

        function _fieldTypeListner() {
            $(".field-type").on('change',function () {
                var value = $(this).val();
                var $tr = $(this).closest("tr");
                var $fieldLen = $tr.find(".field-length");
                $fieldLen.prop("disabled", false);
                $fieldLen.attr('regexp',null);
                switch (value) {
                    case 'numeric':
                        $fieldLen.attr('data-regexp','/\d+,\d+/');
                        break;
                    case 'text':
                    case 'longtext':
                    case 'datetime':
                        $fieldLen.val("");
                        $fieldLen.prop("disabled", true);
                        break;
                    default:
                        break;
                }
            });
        }
		
		//删除字段
		$(".close").click(function(){
			$(this).parent().parent().remove();
		});
	}
	
})(jQuery);