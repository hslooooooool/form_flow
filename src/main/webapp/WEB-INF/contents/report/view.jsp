<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="wrap-content report-designer">
    <div class="report-designer p-5">
        <div class="panel panel-default">
            <div class="panel-heading p-r-0" style="padding-top:7px; padding-bottom: 7px;"><label>报表属性</label></div>
            <div class="panel-body">
		        <form method="post" id="report-designer-form">
		          <table class="table table-condensed table-bordered table-sm">
		              <tbody>
		                  <tr>
		                      <th style="width: 100px;">名称</th>
		                      <td>${objBean.name }</td>
		                      <th style="width: 100px;">类型</th>
                              <td>${objBean.type }</td>
                              <th style="width: 100px;">是否支持导出</th>
                              <td>${objBean.properties.isImport=='1'?'支持':'不支持' }</td>
                              <th style="width: 100px;">是否固定标题</th>
                              <td>${objBean.properties.isFixedHeader=='1'?'是':'否' }</td>
		                  </tr>
		                  <tr>
                              <th>是否有ID</th>
                              <td>${objBean.properties.isHasId=='1'?'有':'无' }</td>
                              <th>是否显示ID</th>
                              <td>${objBean.properties.isShowId=='1'?'显示':'不显示' }</td>
                              <th>是否有复选框</th>
                              <td>${objBean.properties.isCheckbox=='1'?'有':'无' }</td>
                              <th></th>
                              <td></td>
                          </tr>
                          <tr class="bg-color-pd">
                            <td colspan="8"><div class="p-t-5 p-b-3 color-pd text-bold">自定义SQL语句</div></td>
                          </tr>
                          <tr>
                              <th style="width: 80px;">名称</th>
                              <td colspan="7">${objBean.sqlResource.name }
                              </td>
                          </tr>
                          <tr>
                            <th style="width: 80px;">SQL语句</th>
                            <td colspan="7">${objBean.sqlResource.sql }
                            </td>
                          </tr>
                          <tr class="bg-color-pd">
		                    <td colspan="8">
		                       <div class="col-sm-6 p-t-5 p-b-3 p-l-0 p-r-0 color-pd text-bold">字段属性</div>
		                       <div class="col-sm-6 p-t-5 p-b-3 p-r-5 text-right"></div>
		                    </td>
		                  </tr>
		                  <tr>
		                     <td colspan="8" class="seamless-embed-table">
		                        <table class="table table-bordered table-condensed table-sm">
		                           <thead>
		                              <tr class="ui-state-default text-center" style="border: none;">
		                                 <th style="width: 40px;">序号</th>
		                                 <th style="width: 120px;">标题</th>
		                                 <th style="width: 80px;">宽度</th>
		                                 <th style="width: 200px;">超链接URL</th>
		                                 <th style="width: 100px;">打开方式</th>
		                                 <th style="width: 120px;">链接参数</th>
		                                 <th style="width: 120px;">链接参数值</th>
		                                 <th style="width: 120px;">搜索变量名</th>
		                                 <th>自定义单元格</th>
		                              </tr>
		                           </thead>
		                       </table>
		                       <div class="cnoj-auto-limit-height report-setting-prop" data-subtract-height="50">
		                           <table class="table table-bordered table-sm">
		                               <tbody>
		                                  <c:if test="${not empty objBean  }">
			                                  <c:forEach items="${objBean.fields }" var="field" varStatus="st">
			                                      <tr>
			                                          <td class="seq-num text-right" style="width: 40px;">
			                                              <span class="sort-order-label">${field.sortOrder }</span>
			                                          </td>
			                                          <td style="width: 120px;">${field.title }</td>
			                                          <td style="width: 80px;">${field.width }</td>
	                                                  <td style="width: 200px;">${field.url }</td>
	                                                  <td style="width: 100px;">${field.openUrlType}</td>
	                                                  <td style="width: 120px;">${field.paramName }</td>
	                                                  <td style="width: 120px;">${field.paramValue }</td>
	                                                  <td style="width: 120px;">${field.searchName }</td>
	                                                  <td>${field.customClass }</td>
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
		        </form>
	        </div>
	    </div>
    </div>
</div>