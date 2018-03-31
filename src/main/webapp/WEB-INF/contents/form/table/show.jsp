<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <div class="wrap-content-dialog">
    <div class="panel panel-default">
           <div class="panel-heading p-t-8 p-b-8 p-l-4 text-bold">基本信息</div>
			<table class="table table-bordered table-condensed table-sm">
			   <tbody>
			      <tr>
			         <th class="text-right" style="width: 80px;">表名：</th>
			         <td colspan="3">
			            ${table.tableName }
			         </td>
			      </tr>
			      <tr>
			         <th class="text-right" style="width: 80px;">注释：</th>
			         <td colspan="3">
			            ${table.remark }
			         </td>
			      </tr>
			      <tr class="bg-color-pd">
			        <td colspan="4">
			           <div class="p-t-5 p-b-3 p-l-0 p-r-0 color-pd text-bold">字段信息</div>
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
			                     <td>注释</td>
			                  </tr>
			               </thead>
			           </table>
			           <div class="table-wrap-limit create-table-field">
				           <table class="table table-striped table-bordered table-condensed">
				               <tbody>
				                 <c:choose>
				                    <c:when test="${table.fields == null}">
				                       <tr>
				                        <td colspan="5" class="text-center">没有相关字段信息</td>
				                       </tr>
				                    </c:when>
				                    <c:otherwise>
				                        <c:forEach var="field" items="${table.fields }" varStatus="st" >
				                            <tr>
				                               <td class="seq-num text-right" style="width: 40px;">${st.index+1 }</td>
						                       <td style="width: 180px;">${field.fieldName }</td>
						                       <td style="width: 150px;">${field.dataFormat }</td>
						                       <td style="width: 150px;">${field.length }</td>
						                       <td>${field.fieldRemark }</td>
				                            </tr>
				                        </c:forEach>
				                    </c:otherwise>
				                 </c:choose>
				               </tbody>
				            </table>
			            </div>
			         </td>
			      </tr>
			   </tbody>
	        </table>
     </div><!-- panel -->
 </div>