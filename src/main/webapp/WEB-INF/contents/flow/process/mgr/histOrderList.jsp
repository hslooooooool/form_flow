<%@page import="cn.com.smart.web.service.OPAuthService"%>
<%@page import="cn.com.smart.web.spring.util.SpringBeanFactoryUtil"%>
<%@page import="cn.com.smart.web.helper.HttpRequestHelper"%>
<%@page import="cn.com.smart.web.tag.bean.CustomBtn"%>
<%@page import="cn.com.smart.web.bean.UserInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    OPAuthService authServ = (OPAuthService)SpringBeanFactoryUtil.getInstance().getBean("opAuthServ");
    int resume = 0;
    int formEdit = 0;
    if(null != authServ) {
    	UserInfo userInfo = HttpRequestHelper.getUserInfoFromSession(request);
    	String currentUri = "process/mgr/histOrderList";
    	CustomBtn resumeBtn = new CustomBtn("resume","重新流转","重新流转","");
    	if(authServ.isAuth(currentUri, resumeBtn, userInfo.getRoleIds())) {
    		resume = 1;
    	}
    	CustomBtn formEditBtn = new CustomBtn("flow_form_edit","流程表单编辑","修改流程表单信息","");
    	if(authServ.isAuth(currentUri, formEditBtn, userInfo.getRoleIds())) {
    		formEdit = 1;
    	}
    }
    pageContext.setAttribute("resume", resume);
    pageContext.setAttribute("formEdit", formEdit);
%>
<div class="wrap-content-hist-order">
    <div class="panel no-border">
        <div class="panel-search">
              <form class="form-inline search-param cnoj-entry-submit" id="search-form-hist-order" method="post" role="form" action="process/mgr/histOrderList" target="${target }">
                  <div class="form-group p-r-10">
				    <label for="search-input01">流程名称：</label>
				    <input type="text" class="form-control input-form-sm-control" id="search-input01" name="name" placeholder="请输入流程名称或标题" value="${searchParam.name }"/>
				  </div>
				  <div class="form-group">
				    <label for="search-input02">起草日期范围：</label>
				    <input type="text" class="form-control input-form-sm-control cnoj-date" id="search-input02" name="startDate" placeholder="开始时间" value="${searchParam.startDate }"/>
				  </div>
				  <div class="form-group">  
				    <label for="search-input03"> 至 </label>
				    <input type="text" class="form-control input-form-sm-control cnoj-date" id="search-input03" name="endDate" placeholder="结束时间" value="${searchParam.endDate }"/>
				  </div>
				  <div class="form-group">
					  <span class="btn btn-primary btn-sm cnoj-search-submit">
						<i class="glyphicon glyphicon-search"></i>
						<span>搜索</span>
					  </span>
				  </div>
              </form>
        </div>
		<div class="cnoj-auto-limit-height" data-subtract-height="35" id="hist-order-list">
		    <table class="table table-bordered table-condensed">
		        <thead>
		           <tr class="ui-state-default">
		              <th style="width: 45%">标题</th>
		              <th class="text-center no-wrap" style="width:120px">起草人</th>
		              <th class="text-center no-wrap" style="width:100px">起草日期</th>
		              <th class="text-center no-wrap" style="width:100px">结束日期</th>
		              <th class="text-center no-wrap" style="width:100px">处理信息</th>
		              <th class="text-center no-wrap" style="width:60px">流程图</th>
		              <c:if test="${resume==1}">
		              <th class="text-center no-wrap" style="width:80px">操作</th>
		              </c:if>
		           </tr>
		        </thead>
		        <tbody>
		           <c:choose>
		               <c:when test="${smartResp.result != 1 }">
		                  <tr><td colspan="7" class="text-center">${smartResp.msg}</td></tr>
		               </c:when>
		               <c:otherwise>
		                  <c:forEach var="orderClassify" items="${smartResp.datas }" varStatus="st" >
			                   <tr class="expand-data tr-parent-tree tr-tree" id="${orderClassify.id}">
			                      <td colspan="7" class="tr-parent-tree-td"><span class='ui-icon ui-icon-triangle-1-s left'></span> ${orderClassify.name }</td>
			                   </tr>
			                   <c:forEach var="datas" items="${orderClassify.datas}">
			                    <tr class="tr-tree tr-sub-tree ${orderClassify.id}" id="t-${datas[1]}">
			                      <td class="tr-sub-tree-td">${datas[3] }</td>
			                      <td class="text-center">${datas[4] }</td>
			                      <td class="text-center">${datas[5] }</td>
			                      <td class="text-center" title="${datas[6] }">${fn:substring(datas[6],0,10)}</td>
			                      <td class="text-center">
			                         <button type="button" class="btn btn-success btn-xs cnoj-open-blank" data-title="【${datas[3]}】处理信息" data-uri="process/viewForm?processId=${datas[0] }&orderId=${datas[1] }&isAtt=${datas[7]}"><i class="glyphicon glyphicon-list"></i> 查看</button>
			                      	<c:if test="${formEdit == 1}">
			                      	 	<button type="button" class="btn btn-warning btn-xs cnoj-open-blank" data-title="【${datas[3]}】编辑表单信息" data-uri="process/editForm?processId=${datas[0] }&orderId=${datas[1] }"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</button>
			                      	 </c:if>
			                      </td>
			                      <td class="text-center">
			                         <button type="button" class="btn btn-primary btn-xs cnoj-open-blank" data-title="【${datas[3]}】流转情况" data-uri="process/view?processId=${datas[0]}&orderId=${datas[1] }"><i class="glyphicon glyphicon-picture"></i> 查看</button>
			                      </td>
			                      <c:if test="${resume==1}">
			                      <th class="text-center no-wrap">
			                      <button type="button" class="btn btn-danger btn-xs cnoj-post-data" data-del-alert="您确定要【重新流转】该流程实例吗？" data-param="orderId=${datas[1]}" data-uri="process/mgr/resume.json" data-target="${target }" data-refresh-uri="${searchUri}&page=${page}"><i class="glyphicon glyphicon-random"></i> 重新流转</button>
			                      </th>
			                      </c:if>
			                    </tr>
		                       </c:forEach>
		                   </c:forEach>
		               </c:otherwise>
		           </c:choose>
		        </tbody>
		    </table>
		</div>
		<cnoj:panelFooter smartResp="${smartResp}" currentUri="${currentUri }" refreshBtn="${refreshBtn }" page="${pageParam }" />
	</div>
</div>
<script type="text/javascript">
   setTimeout("loadHistOrderJs()", 200);
   function loadHistOrderJs() {
	   simpleTreeListener("#hist-order-list");
   }
</script>