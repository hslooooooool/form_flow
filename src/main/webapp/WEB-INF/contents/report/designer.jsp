<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/report/js/report.js"></script>
<div class="wrap-content report-designer cnoj-auto-limit-height">
    <div class="report-designer p-5">
        <div class="panel panel-default">
            <div class="panel-heading p-r-0" style="padding-top:7px; padding-bottom: 7px;"><label>报表设置</label>
                <div class="pull-right" style="margin-top: -7px;"><button id="save-designer" type="button" class="btn btn-primary"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button></div>
            </div>
            <div class="panel-body">
		        <form method="post" id="report-designer-form">
		          <input type="hidden" name="id" value="${objBean.id }" />
		          <input type="hidden" name="properties.id" value="${objBean.properties.id }" />
		          <input type="hidden" name="sqlResource.id" value="${objBean.sqlResource.id }" />
		          <table class="table table-condensed table-bordered table-sm">
		              <tbody>
		                  <tr>
		                      <th style="width: 80px;">名称</th>
		                      <td>
		                          <input type="text" name="name" data-label-name="名称" class="form-control require" placeholder="请输入报表名称" value="${objBean.name }" />
		                      </td>
		                      <th style="width: 80px;">类型</th>
                              <td style="width: 200px;">
                                  <select id="report-type" class="form-control cnoj-select select-form-control" name="type" data-uri="dict/item/REPORT_TYPE.json" data-default-value="${objBean.type }" >
                                  </select>
                              </td>
                              <th style="width: 100px;">支持导出</th>
                              <td>
                                  <select class="form-control cnoj-select select-form-control" name="properties.isImport" data-uri="dict/item/YES_OR_NO.json" data-default-value="${objBean.properties.isImport==null?'0':objBean.properties.isImport }" >
                                  </select>
                              </td>
                              <th style="width: 70px;">固定标题</th>
                              <td>
                                  <select class="form-control cnoj-select select-form-control" name="properties.isFixedHeader" data-uri="dict/item/YES_OR_NO.json" data-default-value="${objBean.properties.isFixedHeader==null?'1':objBean.properties.isFixedHeader }" >
                                  </select>
                              </td>
		                  </tr>
		                  <tr>
                              <th>是否有ID</th>
                              <td>
                                  <select class="form-control cnoj-select select-form-control" name="properties.isHasId" data-uri="dict/item/YES_OR_NO.json" data-default-value="${objBean.properties.isHasId==null?'1':objBean.properties.isHasId }" >
                                  </select>
                              </td>
                              <th>是否显示ID</th>
                              <td>
                                  <select id="report-type" class="form-control cnoj-select select-form-control" name="properties.isShowId" data-uri="dict/item/YES_OR_NO.json" data-default-value="${objBean.properties.isShowId==null?'0':objBean.properties.isShowId }" >
                                  </select>
                              </td>
                              <th>是否有复选框</th>
                              <td>
                                  <select class="form-control cnoj-select select-form-control" name="properties.isCheckbox" data-uri="dict/item/YES_OR_NO.json" data-default-value="${objBean.properties.isCheckbox==null?'0':objBean.properties.isCheckbox }" >
                                  </select>
                              </td>
                              <th></th>
                              <td></td>
                          </tr>
                          <tr class="bg-color-pd">
                            <td colspan="8"><div class="p-t-5 p-b-3 color-pd text-bold">自定义SQL语句</div></td>
                          </tr>
                          <tr>
                              <th style="width: 80px;">名称</th>
                              <td colspan="7">
                                  <input type="text" name="sqlResource.name" class="form-control require" placeholder="请输入SQL语句的名称，注：名称不要重复" value="${objBean.sqlResource.name }" />
                              </td>
                          </tr>
                          <tr>
                            <td colspan="8">
                                <textarea name="sqlResource.sql" class="form-control require" style="width: 99%;" rows="5" placeholder="请输入自定义SQL语句">${objBean.sqlResource.sql }</textarea>
                            </td>
                          </tr>
                          <tr class="bg-color-pd">
		                    <td colspan="8">
		                       <div class="col-sm-6 p-t-5 p-b-3 p-l-0 p-r-0 color-pd text-bold">字段设置</div>
		                       <div class="col-sm-6 p-t-5 p-b-3 p-r-5 text-right"><button type="button" class="add-field btn btn-info btn-xs"><i class="glyphicon glyphicon-plus-sign"></i> 添加</button></div>
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
		                                 <th style="width: 40px">操作</th>
		                              </tr>
		                           </thead>
		                       </table>
		                       <div class="cnoj-auto-limit-height report-setting-prop" data-subtract-height="15">
		                           <table class="table table-condensed table-bordered table-sm">
		                               <tbody>
		                                  <c:if test="${not empty objBean  }">
			                                  <c:forEach items="${objBean.fields }" var="field" varStatus="st">
			                                      <tr>
			                                          <td class="seq-num text-right" style="width: 40px;">
			                                              <input type="hidden" name="fields[${st.index}].id" value="${field.id }" />
			                                              <input type="hidden" class="sort-order" name="fields[${st.index}].sortOrder" value="${field.sortOrder }" />
			                                              <span class="sort-order-label">${field.sortOrder }</span>
			                                          </td>
			                                          <td style="width: 120px;">
			                                              <input name="fields[${st.index }].title" class="form-control" placeholder="请填写标题，必填" title="必填项" type="text" value="${field.title }"  />
			                                          </td>
			                                          <td style="width: 80px;">
	                                                      <input name="fields[${st.index }].width" class="form-control" placeholder="自动宽度" type="text" value="${field.width }"  />
	                                                  </td>
	                                                  <td style="width: 200px;">
	                                                      <input name="fields[${st.index }].url" class="form-control" placeholder="URL地址" type="text" value="${field.url }"  />
	                                                  </td>
	                                                  <td style="width: 100px;">
	                                                      <select class="form-control cnoj-select" name="fields[${st.index}].openUrlType" data-uri="dict/item/OPEN_URL_TYPE.json" data-default-value="${field.openUrlType}">
	                                                      <option value="">请选择</option>
	                                                      </select>
	                                                  </td>
	                                                  <td style="width: 120px;">
	                                                      <input name="fields[${st.index }].paramName" class="form-control" title="多个参数用英文逗号分隔，如果没有请为空" placeholder="多个参数用英文逗号分隔，如果没有请为空" type="text" value="${field.paramName }"  />
	                                                  </td>
	                                                  <td style="width: 120px;">
	                                                      <input name="fields[${st.index }].paramValue" class="form-control" title="多个参数引用下标用英文逗号分隔，如果没有请为空" placeholder="多个参数引用下标用英文逗号分隔，如果没有请为空" type="text" value="${field.paramValue }"  />
	                                                  </td>
	                                                  <td style="width: 120px;">
	                                                      <input name="fields[${st.index }].searchName" class="form-control" title="填写搜索变量，如果该标题不是搜索项，请为空" placeholder="填写搜索变量，如果该标题不是搜索项，请为空" type="text" value="${field.searchName }"  />
	                                                  </td>
	                                                  <td>
	                                                      <input name="fields[${st.index }].customClass" class="form-control" title="自定义实现类，需要实现ICustomCellCallback接口" placeholder="自定义实现类，需要实现ICustomCellCallback接口" type="text" value="${field.customClass }"  />
	                                                  </td>
	                                                  <td class="del-td" id="del${st.index+1 }" style="width: 40px" class="text-center">
	                                                    <button type="button" title="删除" class="close text-center" style="float: none;font-size: 18px;" data-dismiss="tr1" aria-label="Close"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
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
		        </form>
	        </div>
	    </div>
    </div>
</div>
 <script type="text/javascript">
    //plugins/report/js/report.js
    setTimeout("loadJs()", 200);
    function loadJs(){
        $(".report-setting-prop").reportPropListener();
    }
 </script>