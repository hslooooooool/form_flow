<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="wrap-content">
   <div class="setting-tab-content">
	      <div class="layout-type">
	        <form class="form-horizontal label-radio" id="index-layout-form"  role="form">
	         <input type="hidden" name="id" value="${smartResp.data.id }" />
	         <div class="left">
		         <lable><strong>布局：</strong></lable>
		         <c:choose>
		            <c:when test="${smartResp.result==1 }">
		                <c:forEach var="item" items="${dictRes.datas }">
					    <label class="fw-normal">
							<input type="radio" ${smartResp.data.layout==item.busiValue?"checked":"" } name="layout" id="index-layout-${item.busiValue}" value="${item.busiValue}"> ${item.busiName}
						</label> &nbsp;&nbsp;
					    </c:forEach>
		            </c:when>
		            <c:otherwise>
		               <c:forEach var="item" items="${dictRes.datas }" varStatus="st">
						<label class="fw-normal">
							<input type="radio" ${st.index==0?"checked":"" } name="layout" id="index-layout-${item.busiValue}" value="${item.busiValue}"> ${item.busiName}
						</label> &nbsp;&nbsp;
					   </c:forEach>
		            </c:otherwise>
		         </c:choose>
	         </div>
	         <div class="right"><button type="button" class="btn btn-info btn-sm submit-btn"> 保 存 </button></div>
	       </form>
	      </div>
	      <div class="clear"></div>
	      <div class="setting-layout-content">
	           <c:if test="${smartResp.result==1 && fn:length(smartResp.data.cusIndexMinWins)>0 }">
	           
	               <div class="layout-view active" id="layout-${smartResp.data.layout}">
		            <form class="form-horizontal layout-view-form" role="form">
		             <div class="row m-r-0 m-l-0 p-t-10">
		             <c:forEach var="cusInfoMinWinLink" items="${smartResp.data.cusIndexMinWins}" varStatus="st">
		                 <c:if test="${st.index>0 && st.index%smartResp.data.cols==0 && (st.index+1)<=fn:length(smartResp.data.cusIndexMinWins)}">
		                     </div>
		                     <div class="row m-r-0 m-l-0 p-t-10">
		                 </c:if>
			             <div class="col-sm-${(12/smartResp.data.cols)}" ${(st.index%smartResp.data.cols != 0)?'p-l-10':'p-l-0' } p-r-0">
			                <div class="border-b row-h-5 border-radius borer-hover layout-wrap">
			                    <div class="layout-content">
				                     <c:if test="${cusInfoMinWinLink.minWin != null}">
				                      <c:choose>
					                      <c:when test="${cusInfoMinWinLink.minWin.isShowTitle==1}">
					                        <div class="panel panel-info m-b-5">
						                        <div class="panel-heading ui-state-default"><div class="title">${cusInfoMinWinLink.minWin.name }</div></div>
												 <div class="panel-body default-page-body p-t-5 p-r-5 p-b-5 p-l-5" id="layout-${smartResp.data.layout}-min-win-${st.index+1 }">
													<script type="text/javascript">
														loadUri("#layout-${smartResp.data.layout}-min-win-${st.index+1}",'${cusInfoMinWinLink.minWin.uri}',false);
													</script>
												 </div>
											</div><!-- panel -->
					                        </c:when>
					                        <c:otherwise>
					                          <div id="layout-${smartResp.data.layout}-min-win-${st.index+1 }">
					                                <script type="text/javascript">
													      loadUri("#layout-${smartResp.data.layout}-min-win-${st.index+1}",'${cusInfoMinWinLink.minWin.uri}',false);
													</script>
					                           </div>
					                         </c:otherwise>
				                         </c:choose>
				                        </c:if>
			                        </div>
			                        <div class="op-btn">
			                               <input type="hidden" class="min-win-id min-win-id-${st.index+1}" name="cusIndexMinWins[${st.index}].minWinId" id="${smartResp.data.layout}-min-win-id-${st.index+1}" value="${cusInfoMinWinLink.minWinId}" />
		                                   <input type="hidden" class="cus-seq-num cus-seq-num-${st.index+1}" name="cusIndexMinWins[${st.index}].seqNum" id="${smartResp.data.layout}-cus-seq-num-${st.index+1}" value="${st.index+1}" />
		                                   <button type="button" class="btn btn-default btn-xs add"><i class="glyphicon glyphicon-plus"></i> 添加</button>
		                                   <button type="button" class="btn btn-default btn-xs edit"><i class="glyphicon glyphicon-pencil"></i> 修改</button>
		                             </div>
		                          </div>
			                 </div>
		                 </c:forEach>
		               </div><!-- row -->
		             </form>
		          </div>
	           </c:if>
	      </div><!-- setting-layout-content -->
       </form>
   </div>
</div>
<script type="text/javascript">
  $(function(){
	   var $checked = $("#index-layout-form input[type='radio']:checked");
	   var value = $checked.val();
	   createOrShow(value);
	   adjustHeight();
	   $("#index-layout-form input[type='radio']").click(function(){
		   value = $(this).val();
		   createOrShow(value);
		   adjustHeight();
	   });
	   $(".submit-btn").click(function(){
		   var $form = $(this).parents("form");
		    var divId = $form.attr("id");
		    if(typeof(divId) !== 'undefined') {
		    	var isSubmit = true;
		    	$(".layout-view.active").find(".min-win-id").each(function(){
		    		if(!isEmpty($(this).val())) {
		    			isSubmit = isSubmit && true;
		    		} else {
		    			isSubmit = isSubmit && false;
		    		}
		    	});
			    if(isSubmit) {
					var list = $form.serialize();
					var layoutViewParam = $(".layout-view.active .layout-view-form").serialize();
					list = list+"&"+layoutViewParam;
					waitLoading("正在提交数据...");
					$.post("user/custom/saveLayout.json", list, function(json) {
						closeWaitLoading();
						var output = data;//$.parseJSON(json.output);
						showMsg(output.msg);
						if(output.result == '1') {
							showMsg("保存成功！");
						} else {
							showMsg("保存失败！");
						}
					});
				} else {
					showMsg("请添加显示内容");
				}
			}
	   });
  });
  
  /**
   *
   */
  function createOrShow(value) {
	   var id = "layout-"+value;
	   var $layout = $("#"+id);
	   if(!isEmpty($layout.attr("id"))) {
		   $(".layout-view").removeClass("active");
		   $layout.addClass("active");
	   } else {
		   $(".layout-view").removeClass("active");
		   showLayout(value);
	   }
	   setBtnState();
	   listenerBtnClick();
  }
  
  /**
   * 显示布局
   */
  function showLayout(value) {
	  if(!isEmpty(value)) {
		  var valueArray = value.split("-");
		   if(valueArray.length>=3) {
			   $(".setting-layout-content").append(layout(parseInt(valueArray[1]),parseInt(valueArray[2]),value));
		   }
	   }
  }
  
  /**
   * 计算布局
   */
  function layout(rows,cols,value) {
	  var layoutContent = '';
	  if(typeof(rows) === 'number' && typeof(cols) === 'number') {
		  var colNum = parseInt(12/cols);
		  var index = 0;
		  layoutContent +="<div class='layout-view active' id='layout-"+value+"'><form class='form-horizontal layout-view-form' role='form'>";
		  for(var i=0;i<rows;i++) {
			  layoutContent += "<div class='row m-r-0 m-l-0 p-t-10'>";
			  for(var j=0;j<cols;j++) {
				  index = i*cols+j;
				  layoutContent +="<div class='col-sm-"+colNum+" "+(j>0?"p-l-10":"p-l-0")+" p-r-0'>";
				  layoutContent +="<div class='border-b row-h-5 border-radius borer-hover layout-wrap'>"+
	                 "<div class='layout-content' data-layout='"+value+"'></div>"+
	                 "<div class='op-btn'>"+
	                   "<input type='hidden' class='min-win-id min-win-id-"+(index+1)+"'' name='cusIndexMinWins["+index+"].minWinId' id='"+value+"-min-win-id-"+(index+1)+"' value='' />"+
	                   "<input type='hidden' class='cus-seq-num cus-seq-num-"+(index+1)+"'' name='cusIndexMinWins["+index+"].sortOrder' id='"+value+"-cus-seq-num-"+(index+1)+"' value='"+(index+1)+"' />"+
	                   "<button type='button' class='btn btn-default btn-xs add'>"+
	                    "<i class='glyphicon glyphicon-plus'></i> 添加"+
	                   "</button>"+
	                   "<button type='button' class='btn btn-default btn-xs edit'>"+
	                    "<i class='glyphicon glyphicon-pencil'></i> 修改"+
	                   "</button></div></div>";
	               layoutContent +="</div>";
			  }
			  layoutContent +="</div>";
		  }
		  layoutContent +="</form></div>";
	  }
	  return layoutContent;
  }
  
  
  function setWinProp(layoutSeqNum,winId,cusSeqNum) {
	  layoutSeqNum = parseInt(layoutSeqNum);
	  var $layoutViewCol = $(".layout-view.active .min-win-id-"+layoutSeqNum);
	  $layoutViewCol.val(winId);
	  var $layoutContentTag = $layoutViewCol.parent().parent().find(".layout-content");
	  if(!isEmpty(cusSeqNum) && regexInteger(cusSeqNum))
	      $(".layout-view.active .cus-seq-num-"+layoutSeqNum).val(cusSeqNum);
	  setBtnState();
	  loadChangeMinWin($layoutContentTag,layoutSeqNum,winId);
  }
  
  /**
   *
   */
  function loadChangeMinWin(tag,layoutSeqNum,winId) {
	  if(!isEmpty(winId)) {
		  $.post("minwin/view.json?id="+winId,function(data){
			  var output = data;//$.parseJSON(data.output);
			  if(output.result == '1') {
				 var layout = tag.data("layout");
				  if(!isEmpty(layout)) {
					  var idTag = "layout-"+layout+"-min-win-"+layoutSeqNum;
					  var html = "<div class='panel panel-info m-b-5'>"+ 
					  "<div class='panel-heading ui-state-default'><div class='title'>"+output.data.name+"</div></div>"+
					  "<div class='panel-body default-page-body p-t-5 p-r-5 p-b-5 p-l-5' id='"+idTag+"'>"+
					  "</div></div>";
					  tag.html(html);
					  loadUri("#"+idTag,output.data.uri,false);
				  }
			  }
		  });
	  }
  }
  
  /**
   * 设置按钮状态
   */
  function setBtnState() {
	  $(".layout-view.active .min-win-id").each(function(){
		  var $this = $(this);
		  if(!isEmpty($this.val())) {
			   $this.parent().find(".add").hide();
			   $this.parent().find(".edit").show();
		  } else {
			  $this.parent().find(".add").show();
			  $this.parent().find(".edit").hide();
		  }
	  }); 
  }
  
  
  function listenerBtnClick() {
	  $(".layout-view .add").unbind("click");
	   $(".layout-view .add,.layout-view .edit").click(function(){
		   var $parent = $(this).parent();
		   var params = "cusIndexMinWins[0].minWinId="+$parent.find(".min-win-id").val();
		   params += "&cusIndexMinWins[0].sortOrder="+$parent.find(".cus-seq-num").val();
		   var title = "添加小窗口";
		   if(isContain($(this).attr("class"),"edit")) {
			   title = "修改小窗口";
		   }
		   BootstrapDialogUtil.loadUriDialog(title,"user/custom/addLayoutProp?"+params);
	   });
  }
</script>