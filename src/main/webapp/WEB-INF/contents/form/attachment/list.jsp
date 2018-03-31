<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
   <form id="form-att-form">
		<input type="hidden" name="formDataId" value="${formDataId }" />
		<input type="hidden" name="formId" value="${formId }" />
	</form>
	<div class="panel panel-default">
		<table class="handle-info-form table table-striped table-bordered table-condensed" id="form-attachment">
			<thead>
				<tr>
				   <th class="hidden"></th>
				   <th style="width: 30px;">序号</th>
				   <th style="width: 50%">附件名称</th>
				   <th style="width: 100px;">大小</th>
				   <th class="text-center" style="width: 130px;">上传时间</th>
				   <th class="text-center" style="width: 130px;">上传人</th>
				   <c:if test="${isView != '1' }">
				        <th class="text-center" style="width: 80px;">操作</th>
				   </c:if>
				   
				</tr>
			</thead>
			<body>
			<c:choose>
			   <c:when test="${smartResp.result != 1 }">
			      <tr class="text-center"><td colspan="7">没有查询到相关附件！</td></tr>
			      <script type="text/javascript">
				      var $attTabA = $("#form-att-tab-a");
			          if($attTabA.find("span").hasClass("badge")) {
			        	  $attTabA.find(".badge").remove();
			          }
			      </script>
			   </c:when>
			   <c:otherwise>
			       <c:forEach var="datas" items="${smartResp.datas }" varStatus="st" >
			         <tr>
				      <td class="hidden">
				          <input type="hidden" class="form-att-id" name="formAttId" value="${datas[0]}" />
				      </td>
				      <td class="seq-no">${st.index+1 }</td>
				      <td><a href="download/att?id=${datas[1]}" target="_blank">${datas[2]}</a></td>
				      <td>${datas[3]}</td>
				      <td class="text-center">${datas[5]}</td>
				      <td class="text-center">${datas[6]}</td>
				      <c:if test="${isView != '1' }">
					        <td class="text-center">
	                        <c:if test="${datas[7] == userInfo.id && empty datas[8]}">
	                             <span class="btn btn-danger btn-xs att-del">
	                                 <i class="glyphicon glyphicon-trash"></i>
	                                 <span>删除</span>
	                              </span>
	                        </c:if>
	                      </td>
				      </c:if>
				    </tr>
			       </c:forEach>
			       <script type="text/javascript">
			         var $attTabA = $("#form-att-tab-a");
			          if($attTabA.find("span").hasClass("badge")) {
			        	  $attTabA.find(".badge").html('${fn:length(smartResp.datas)}');
			          } else {
			        	  $attTabA.append(" <span class='badge'>${fn:length(smartResp.datas)}</span>");
			          }
			       </script>
			   </c:otherwise>
			</c:choose>
			 </body>
		</table>
		<c:if test="${isView != '1' }">
			<form id="file-upload-form">
				<div class="panel-footer upload-panel" id="form-att-op">
					  <span class="btn btn-success btn-sm fileinput-button upload-add">
						<i class="glyphicon glyphicon-plus"></i> <span>添加附件</span>
						<input id="upload-m-file" type="file" name="atts" multiple />
					  </span> 
					  <span class="help-block text-inline-block">单个文件大小不超过100M，文件类型为：${fn:replace(uploadFileType,",","，")}.</span>
					   <div class="attr-upload-prompt-msg help-block">${attrUploadPromptMsg }</div>
				</div>
			</form>
		</c:if>
   </div>
<script type="text/javascript">
   setTimeout("loadAtt()", 500);
   var attListUri = "form/attachment/list?formId=${formId}&formDataId=${formDataId}";
   var uploadFileType = '${uploadFileType}';
   function loadAtt() {
	   uploadFileType = uploadFileType.replace(/,/g, '|');
	   $("#upload-m-file").jqueryFileUpload({
	          uri:'form/attachment/upload.json',
	          formData:$("#form-att-form").serializeArray(),
	          acceptFileTypes:'/'+uploadFileType+'$/i',
	          maxFileSize:104857600,//100M
	          closeAfterFun:function(){
	             loadUri("#form-att-tab",attListUri,false);
	          }
	    });
      //上传文件
      listenerAttDel('#form-attachment', '#form-att-tab', 'form/attachment/delete');
   }
   
   /*
   function refreshList() {
	   loadUri("#form-att-tab",formAttUri,false);
   }*/
</script>