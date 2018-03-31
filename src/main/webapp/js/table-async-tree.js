/**
 * 表格树监听
 * 标识
 * table class为:"cnoj-async-tree-table"
 * tr class为:"tr-tree"
 * td class为:"op-tree"
 */
function tableAsyncTreeListener() {
	//$(".cnoj-tree-table .tr-tree .op-tree").unbind("click");
	$(".cnoj-async-tree-table .tr-tree .op-tree").each(function(){
		var $this = $(this);
		if(!utils.isContain($this.attr("class"), "op-tree-listener")) {
			$this.addClass("op-tree-listener");
			$this.click(function(){
				var classNames = $(this).attr("class");
				if(utils.isContain(classNames," shrink-data")) {
					var $spanIcon = $(this).find("span.ui-icon");
					var trId = $(this).parent().attr("id");
					if(typeof(trId) != 'undefined') {
						//trId = "t-"+trId;
						var $trId = $("."+trId);
						$spanIcon.removeClass("ui-icon-triangle-1-e");
						$spanIcon.addClass("ui-icon-triangle-1-s");
						
						$(this).removeClass("shrink-data");
						$(this).addClass("open-data");
						if(utils.isNotEmpty($trId.attr("class"))) {
							$("."+trId).show();
						} else {
							//异步加载数据
							var $parent = $this.parent();
							var asyncUrl = $parent.data("async-url");
							if(utils.isNotEmpty(asyncUrl)) {
								asynLoadingTreeData(asyncUrl, $parent);
							}
						}
					}
				} else {
					var stackArray = new Array();
					var id = $(this).parent().attr("id");
					if(!utils.isEmpty(id)) {
						$("."+id).hide();
						var $spanIcon = $(this).find("span.ui-icon");
						$spanIcon.removeClass("ui-icon-triangle-1-s");
						$spanIcon.addClass("ui-icon-triangle-1-e");
						
						$(this).removeClass("open-data");
						$(this).addClass("shrink-data");
						stackArray.push(id);
					}
					while(stackArray.length>0) {
						stackArray = stackArray.concat(shrinkTableAsyncTree(stackArray.pop()));
					}
					stackArray = null;
				}
			});
		}
	});
}
/**
 * 收缩表格树
 * @param id
 * @returns {Array}
 */
function shrinkTableAsyncTree(id) {
	var array = new Array();
	$(".cnoj-async-tree-table .open-data").each(function(){
		var parentId = $(this).parent().attr("parentid");
		$("."+id).hide();
		var $spanIcon = $("#"+id).find("span.ui-icon");
		$spanIcon.removeClass("ui-icon-triangle-1-s");
		$spanIcon.addClass("ui-icon-triangle-1-e");
		
		$("#"+id+" .op-tree").removeClass("open-data");
		$("#"+id+" .op-tree").addClass("shrink-data");
		if(id == parentId) {
			var trId = $(this).parent().attr("id");
			array.push(trId);
		}
	});
	return array;
}


/**
 * 树形表格行选中监听
 * 标识
 * table为：class="cnoj-async-tree-table"
 * tr为：class="tr-tree"
 */
function tableAsyncTreeSelectListener() {
	$(".cnoj-async-tree-table .tr-tree").each(function(){
		var $this = $(this);
		if(!utils.isContain($this.attr("class"), "tr-tree-listener")) {
			$this.click(function(){
				var classNames = $(this).attr("class");
				$(".cnoj-async-tree-table .tr-tree").each(function(){
					$(this).removeClass("ui-state-focus");
					$(this).find("td").removeClass("ui-state-focus");
				});
				var $panel = $(this).parents(".panel:eq(0)");
				var $param = null;
				if(typeof($panel.attr("class")) !== 'undefined') {
					$param = $panel.find(".cnoj-op-btn-list .param");
					if(typeof($param.attr("class")) === 'undefined') {
						$param = null;
					}
				}
				if(!utils.isContain(classNames," ui-state-focus")) {
					$(this).addClass("ui-state-focus");
					$(this).find("td").addClass("ui-state-focus");
					var id = $(this).attr("id");
					id = id.substring(2,id.length);
					if(null != $param) {
						$param.attr("selected-value",id);
					}
				} else {
					$(this).removeClass("ui-state-focus");
					$(this).find("td").removeClass("ui-state-focus");
					if(null != $param) {
						$param.attr("selected-value","");
					}
				}
			});
			$this.addClass("tr-tree-listener");
		}
	});
}

/**
 * 异步加载数据
 * @param url
 * @param $tr
 */
function asynLoadingTreeData(url,$tr) {
	var colNum = $tr.data("col-num");
	var layer = $tr.data("layer") + 1;
	$afterTr = $tr.after();
	//判断数据是否还在加载（避免重复点击时出现多个“正在加载...”）
	if($afterTr.hasClass("tr-asyn-loading")) {
		return;
	}
	$tr.after("<tr class='tr-asyn-loading'><td colspan='"+colNum+"'><i class='fa fa-spinner fa-spin'></i> 正在加载...</td></div>");
	$.get(url,function(output){
		$(".tr-asyn-loading").remove();
		if(output.result == 1) {
			var datas = output.datas;
			var contents = "";
			var isParent = false;
			//url = url.substring(0,url.indexOf("?"));
			if(url.indexOf("?") == -1) {
				url = url+"?1=1";
			}
			var p = url.indexOf("&parentId=");
			if(p>-1) {
				url = url.substring(0,p);
			}
			for(var i=0; i<datas.length; i++) {
				if(datas[i][datas[i].length-1] == '0') {
					isParent = false;
					contents += "<tr id='t-"+datas[i][0]+"' class='tr-tree tr-async-tree t-tree-layer"+(layer)+" t-"+datas[i][1]+"' parentid='t-"+datas[i][1]+"'>";
				} else {
					contents += "<tr data-col-num='"+colNum+"' data-layer='"+layer+"' data-async-url='"+url+"&parentId="+datas[i][0]+"' id='t-"+datas[i][0]+"' class='tr-tree tr-async-tree t-tree-layer"+(layer)+" t-"+datas[i][1]+"' parentid='t-"+datas[i][1]+"'>";
					isParent = true;
				}
				if(isParent) {
					contents += "<td class='op-tree open-data td-tree'><span class='ui-icon ui-icon-triangle-1-e left'></span> &nbsp;"+datas[i][datas[i].length-colNum-1]+"</td>";
				} else {
					contents += "<td class='td-tree'><span class='ui-icon ui-icon-radio-on left'></span> &nbsp;"+datas[i][datas[i].length-colNum-1]+"</td>";
				}
				for(var j=(datas[i].length-colNum); j<datas[i].length-1; j++) {
					contents += "<td>"+utils.handleNull(datas[i][j])+"</td>";
				}
				contents += "</tr>";
			}
			$tr.after(contents);
			tableAsyncTreeListener();
			tableAsyncTreeSelectListener();
		}
	});
}