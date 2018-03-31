<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <div class="wrap-content-dialog" id="create-form-table">
    <form class="form-horizontal" role="form" id="form-edit" action="form/table/update.json">
         <input type="hidden" name="table.id" value="${objBean.id }" />
         <div class="panel panel-default">
           <div class="panel-heading p-t-8 p-b-8 p-l-4 text-bold">基本信息</div>
			<table class="table table-bordered table-condensed table-sm">
			   <tbody>
			      <tr>
			         <th class="text-right" style="width: 80px;">表名：</th>
			         <td colspan="3">
			            <div class="col-sm-12 p-l-0 p-r-0">
			                <input type="text" class="form-control input-sm require" data-label-name="表名" name="table.tableName" value="${objBean.tableName }" />
			            </div>
			         </td>
			      </tr>
			      <tr>
			         <th class="text-right" style="width: 80px;">注释：</th>
			         <td colspan="3">
			            <div class="col-sm-12 p-l-0 p-r-0">
			               <textarea class="form-control" name="table.remark" rows="3">${objBean.remark }</textarea>
			            </div>
			         </td>
			      </tr>
			      <tr class="bg-color-pd">
			        <td colspan="4">
			           <div class="col-sm-6 p-t-5 p-b-3 p-l-0 p-r-0 color-pd text-bold">字段信息</div>
			           <div class="col-sm-6 p-t-5 p-b-3 p-r-5 text-right"><button type="button" class="add-field btn btn-primary btn-xs"><i class="glyphicon glyphicon-plus-sign"></i> 添加</button></div>
			        </td>
			      </tr>
			      <tr>
			         <td colspan="4" class="seamless-embed-table">
			            <table class="table table-bordered table-condensed table-sm">
			               <thead>
			                  <tr class="ui-state-default" style="border: none;">
			                     <td style="width: 40px;">序号</td>
			                     <td style="width: 180px;">字段名称</td>
			                     <td style="width: 150px;">数据类型</td>
			                     <td style="width: 150px;">长度/设置</td>
			                     <td colspan="2">注释</td>
			                  </tr>
			               </thead>
			           </table>
			           <div class="table-wrap-limit create-table-field">
				         <table class="table table-condensed table-sm">
				             <tbody>
				             <c:if test="${objBean.fields != null}">
				               <c:forEach var="field" items="${objBean.fields }" varStatus="st">
				                 <tr id="tr${st.index+1 }">
				                 <td class="seq-num text-right" style="width: 40px;">${st.index+1 }
				                  	<input type="hidden" name="fields[${st.index }].id" value="${field.id }" />
				                  	<input type="hidden" name="fields[${st.index }].sortOrder" value="${field.sortOrder }" />
				                 </td>
				                 <td style="width: 180px;">
					                 <div class="col-sm-12 p-l-0 p-r-0">
					                  	<input type="text" id="file-name${st.index+1 }" class="form-control input-sm require" data-label-name="字段名称" name="fields[${st.index}].fieldName" value="${field.fieldName }" />
					                 </div>
				                  </td>
				                 <td style="width: 150px;">
					                  <div class="col-sm-12 p-l-0 p-r-0">
					                  <select class="cnoj-select form-control field-type input-sm" data-uri="dict/item/TABLE_FIELD_DATA_FORMAT.json" style="width: 140px;" data-default-value="${field.dataFormat }" name="fields[${st.index }].dataFormat" ></select>
					                  </div>
				                  </td>
				                 <td style="width: 150px;">
				                    <div class="col-sm-12 p-l-0 p-r-0">
										<c:choose>
											<c:when test="${fn:indexOf(field.length,\",\")>-1}">
												<input type="text" class="field-length form-control input-sm" data-regexp="/\d+,\d+/" value="${field.length }" name="fields[${st.index }].length" />
											</c:when>
											<c:otherwise>
												<input type="text" class="field-length form-control input-sm" data-format="num" value="${field.length }" name="fields[${st.index }].length" />
											</c:otherwise>
										</c:choose>
				                    </div>
				                 </td>
				                 <td>
				                   <input type="text" class="form-control input-sm" value="${field.fieldRemark }" name="fields[${st.index }].fieldRemark" />
				                 </td>
				                  <td id="del${st.index+1 }">
				                    <button type="button" title="删除" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				                  </td>
				                 </tr>
				              </c:forEach>
				            </c:if>
				            </tbody>
				          </table>
			            </div>
			         </td>
			      </tr>
			   </tbody>
	        </table>
          </div><!-- panel -->
          <div class="text-center p-t-10">
		    <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="form/table/list">
		    <i class="glyphicon glyphicon-ok-sign"></i> 保存</button>
	      </div>
	  </form>
 </div>
 <script type="text/javascript">
    //plugins/form/js/form.table.js
    setTimeout("loadJs()", 200);
    function loadJs(){
    	$(".create-table-field").createTableListener();
    }
 </script>