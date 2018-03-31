<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    int jumpNode = 1;
    pageContext.setAttribute("jumpNode", jumpNode);
%>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/flow/js/flow.simple.tree.js"></script>
<div class="wrap-content">
    <div>
        <div class="panel-search" data-height="37">
              <form class="form-inline search-param" id="search-form-order" method="post" role="form" action="process/mgr/abnormal" target="${target }">
                   <div class="form-group">
				    <label for="search-input02">流程名称：</label>
				    <select class="form-control input-form-sm-control cnoj-select" data-uri="op/query/select_flow_list.json" 
				    data-default-value="${processId }" name="processId">
				    	<option value="">全部</option>
				    </select>
				  </div>
                  <div class="form-group p-r-10">
				    <label for="search-input01">标题：</label>
				    <input type="text" class="form-control input-form-sm-control" id="search-input01" name="name" placeholder="请输入标题" value="${searchParam.name }"/>
				  </div>
				  <div class="form-group">
					  <span class="btn btn-primary btn-sm cnoj-search-submit">
						<i class="glyphicon glyphicon-search"></i>
						<span>搜索</span>
					  </span>
				  </div>
              </form>
        </div>
		<div class="cnoj-auto-limit-height" data-subtract-height="111" id="abnormal-order-list">
		    <table class="table table-bordered table-condensed">
		        <thead>
		           <tr class="ui-state-default" data-height="36">
		              <th style="width: 30%">标题</th>
		              <th class="no-wrap" style="width:120px">起草人</th>
		              <th class="no-wrap" style="width:100px">起草日期</th>
		              <th class="no-wrap" style="width:150px">待处理节点</th>
		              <th class="no-wrap" style="width:150px">待处理人</th>
		              <th class="text-center no-wrap" style="width:60px">处理信息</th>
		              <th class="text-center no-wrap" style="width:60px">流程图</th>
		              <c:if test="${jumpNode==1}">
		                 <th class="text-center" style="width:100px">操作</th>
		              </c:if>
		           </tr>
		        </thead>
		        <tbody>
		           <c:choose>
		               <c:when test="${smartResp.result != 1 }">
		                  <tr><td colspan="${delAuth==1?'9':'8'}" class="text-center">${smartResp.msg}</td></tr>
		               </c:when>
		               <c:otherwise>
		                   <c:forEach var="orderClassify" items="${smartResp.datas }" varStatus="st" >
			                   <tr class="expand-data tr-parent-tree tr-tree" id="${orderClassify.id}">
			                      <td colspan="${delAuth==1?'9':'8'}" class="tr-parent-tree-td"><span class='ui-icon ui-icon-triangle-1-s left'></span> ${orderClassify.name }</td>
			                   </tr>
			                   <c:forEach var="datas" items="${orderClassify.datas}">
			                    <tr class="tr-tree tr-sub-tree ${orderClassify.id}" id="t-${datas[1]}">
			                      <td class="tr-sub-tree-td" style="width: 30%">${datas[3] }</td>
			                      <td style="width:120px">${datas[4] }</td>
			                      <td style="width:100px" title="${datas[6] }">${fn:substring(datas[6],0,10)}</td>
			                      
			                      <c:choose>
			                         <c:when test="${fn:length(datas[8])>1 }">
			                           <td colspan="2" style="padding: 0;">
			                             <table class="table table-bordered table-condensed" style="border: none">
				                             <c:forEach var="taskActors" items="${datas[8]}">
				                               <tr>
				                               <td style="width:150px;border-top: none;border-left: none;">${taskActors[0]}</td>
				                               <td style="width:150px;border-top: none;border-left: none;border-right: none;">${taskActors[1]}</td>
				                               </tr>
				                             </c:forEach>
			                              </table>
			                           </td>
			                         </c:when>
			                         <c:otherwise>
			                             <td style="width:150px">${datas[8][0][0]}</td>
			                             <td style="width:150px">${datas[8][0][1]}</td>
			                         </c:otherwise>
			                      </c:choose>
			                      <td class="text-center" style="width:60px">
			                         <button type="button" class="btn btn-success btn-xs cnoj-open-blank" data-title="【${datas[3]}】处理信息" data-uri="process/viewForm?processId=${datas[0] }&orderId=${datas[1] }&isAtt=${datas[7]}"><i class="glyphicon glyphicon-list"></i> 查看</button>
			                      </td>
			                      <td class="text-center" style="width:60px">
			                         <button type="button" class="btn btn-primary btn-xs cnoj-open-blank" data-title="【${datas[3]}】流转情况" data-uri="process/view?processId=${datas[0]}&orderId=${datas[1] }"><i class="glyphicon glyphicon-picture"></i> 查看</button>
			                      </td>
			                       <td class="text-center" style="width:100px">
			                          <c:if test="${jumpNode==1 }">
			                              <button type="button" class="btn btn-primary btn-xs cnoj-open-blank" data-title="任意跳转" data-width="600" 
			                              data-uri="process/mgr/jumpNodeSet?processId=${datas[0]}&orderId=${datas[1] }&refreshUri=${refreshUri}" data-target="${target }"><i class="glyphicon glyphicon-random"></i> 跳转</button>
			                          </c:if>
				                  </td>
			                    </tr>
		                       </c:forEach>
		                   </c:forEach>
		               </c:otherwise>
		           </c:choose>
		        </tbody>
		    </table>
		</div>
		<div class="panel-footer ui-state-default panel-footer-page" data-height="34">
		     <div class="btn-list">
		         <div class="btn-group btn-group-sm cnoj-op-btn-list">
		            <cnoj:refreshBtn currentUri="${uri}" uri="${searchUri}" target="${target}" name="刷新"  />
		         </div>
		     </div>
		     <c:if test="${smartResp.totalPage>1 }">
		     <div class="btn-page">
		         <div class="page">
		              <ul class="pagination pagination-sm">
		                <li class="${page==1?'disabled':'' }">
		                  <c:choose>
		                     <c:when test="${page==1}">
		                        <a href="javascript:void(0)" class="pre-page">&laquo;</a>
		                     </c:when>
		                     <c:otherwise>
		                        <a href="#" data-uri="${searchUri}&page=${page-1}" class="cnoj-change-page pre-page" data-target="${target }">&laquo;</a>
		                     </c:otherwise>
		                  </c:choose> 
		                </li>
		                <c:forEach var="pageNum" items="${pageNums }">
		                    <li class="${page==pageNum?'active':'' }"><a class="cnoj-change-page" href="#" data-uri="${searchUri}&page=${pageNum}" data-target="${target }">${pageNum}</a></li>
		                </c:forEach>
					    
					    <li class="${page>=smartResp.totalPage?'disabled':'' }">
					    <c:choose>
		                  <c:when test="${page>=smartResp.totalPage}">
		                     <a href="javascript:void(0)" class="next-page">&raquo;</a>
		                  </c:when>
		                  <c:otherwise>
		                     <a href="#" data-uri="${searchUri}&page=${page+1}" class="cnoj-change-page pre-page" data-target="${target }">&raquo;</a>
		                  </c:otherwise>
		                 </c:choose>
		                 <li>&nbsp;到<input class="form-control input-sm goto-page-input" name="page" value="" />页
		                 <button data-uri="${searchUri}&page=" class="btn btn-default btn-xs cnoj-goto-page" data-target="${target}">确定</button></li>
		              </ul>
		         </div>
		     </div>
		     </c:if>
		     <div class="page-info"><span>${smartResp.totalPage>0?page:'0'} - ${smartResp.totalPage}</span><span>&nbsp;&nbsp; 共${smartResp.totalNum}条(每页显示${smartResp.perPageSize}条)</span></div>
		</div>
	</div>
</div>
<script type="text/javascript">
   setTimeout("loadOrderJs()", 200);
   function loadOrderJs() {
	   simpleTreeListener("#abnormal-order-list");
   }
</script>