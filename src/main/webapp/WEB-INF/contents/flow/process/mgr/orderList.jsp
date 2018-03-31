<%@page import="cn.com.smart.web.helper.WebSecurityHelper"%>
<%@page import="cn.com.smart.web.tag.bean.CustomBtn"%>
<%@page import="cn.com.smart.web.tag.bean.DelBtn"%>
<%@page import="cn.com.smart.web.bean.UserInfo"%>
<%@page import="cn.com.smart.web.helper.HttpRequestHelper"%>
<%@page import="cn.com.smart.web.spring.util.SpringBeanFactoryUtil"%>
<%@page import="cn.com.smart.web.service.OPAuthService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    OPAuthService authServ = (OPAuthService)SpringBeanFactoryUtil.getInstance().getBean("opAuthServ");
    int delAuth = 0;
    int jumpNode = 0;
    int formEdit = 0;
    String delUrl = "process/mgr/delete.json";
    if(null != authServ) {
    	UserInfo userInfo = HttpRequestHelper.getUserInfoFromSession(request);
    	DelBtn delBtn = new DelBtn();
    	String currentUri = "process/mgr/orderList";
    	if(authServ.isAuth(currentUri, delBtn, userInfo.getRoleIds())) {
    		delAuth = 1;
    		delUrl = WebSecurityHelper.addUriAuth(currentUri, delBtn.getId(), delUrl);
    	}
    	CustomBtn jumpNodeBtn = new CustomBtn("jump_task","任意跳转","选择节点","");
    	if(authServ.isAuth(currentUri, jumpNodeBtn, userInfo.getRoleIds())) {
    		jumpNode = 1;
    	}
    	CustomBtn formEditBtn = new CustomBtn("flow_form_edit","流程表单编辑","修改流程表单信息","");
    	if(authServ.isAuth(currentUri, formEditBtn, userInfo.getRoleIds())) {
    		formEdit = 1;
    	}
    }
    pageContext.setAttribute("delAuth", delAuth);
    pageContext.setAttribute("delUrl", delUrl);
    pageContext.setAttribute("jumpNode", jumpNode);
    pageContext.setAttribute("formEdit", formEdit);
%>
<div class="wrap-content-order">
    <div class="panel no-border">
        <div class="panel-search">
              <form class="form-inline search-param cnoj-entry-submit" id="search-form-order" method="post" role="form" action="process/mgr/orderList" target="${target }">
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
		<div class="cnoj-auto-limit-height" id="order-list" data-subtract-height="35">
		    <table class="table table-bordered table-condensed">
		        <thead>
		           <tr class="ui-state-default">
		              <th style="width: 30%"><div class="checkbox td-bs-checkbox"><label><input class="cnoj-checkbox-all cnoj-op-checkbox" data-target=".one-checkbox" value="" type="checkbox"> 标题</label></div></th>
                      <th class="no-wrap" style="width:120px">起草人</th>
		              <th class="no-wrap" style="width:100px">起草日期</th>
		              <th class="no-wrap" style="width:150px">待处理节点</th>
		              <th class="no-wrap" style="width:150px">待处理人</th>
		              <th class="text-center no-wrap" style="width:100px">处理信息</th>
		              <th class="text-center no-wrap" style="width:60px">流程图</th>
		              <th class="text-center" style="width:100px">操作</th>
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
			                    <tr class="tr-selected tr-mutil-selected tr-tree tr-sub-tree ${orderClassify.id}" id="t-${datas[1]}">
                                  <td class="tr-sub-tree-td">
                                  <div class="checkbox td-bs-checkbox"><input type="checkbox" class="one-checkbox cnoj-op-checkbox" value="${datas[1]}" /> ${datas[3] }</div>
                                  </td>
                                  <td>${datas[4] }</td>
			                      <td title="${datas[6] }">${fn:substring(datas[6],0,10)}</td>
			                      
			                      <c:choose>
			                         <c:when test="${fn:length(datas[8])>1 }">
			                           <td colspan="2" style="padding: 0;">
			                             <table class="table table-bordered table-condensed" style="border: none">
				                             <c:forEach var="taskActors" items="${datas[8]}">
				                               <tr>
				                               <td style="border-top: none;border-left: none;">${taskActors[0]}</td>
				                               <td style="border-top: none;border-left: none;border-right: none;">${taskActors[1]}</td>
				                               </tr>
				                             </c:forEach>
			                              </table>
			                           </td>
			                         </c:when>
			                         <c:otherwise>
			                             <td>${datas[8][0][0]}</td>
			                             <td>${datas[8][0][1]}</td>
			                         </c:otherwise>
			                      </c:choose>
			                      <td class="text-center">
			                         <button type="button" class="btn btn-success btn-xs cnoj-open-blank" data-title="【${datas[3]}】处理信息" data-uri="process/viewForm?processId=${datas[0] }&orderId=${datas[1] }&isAtt=${datas[7]}"><i class="glyphicon glyphicon-list"></i> 查看</button>
			                      	 <c:if test="${formEdit == 1}">
			                      	 	<button type="button" class="btn btn-warning btn-xs cnoj-open-blank" data-title="【${datas[3]}】编辑表单信息" data-uri="process/editForm?processId=${datas[0] }&orderId=${datas[1] }"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</button>
			                      	 </c:if>
			                      </td>
			                      <td class="text-center">
			                         <button type="button" class="btn btn-primary btn-xs cnoj-open-blank" data-title="【${datas[3]}】流转情况" data-uri="process/view?processId=${datas[0]}&orderId=${datas[1] }"><i class="glyphicon glyphicon-picture"></i> 查看</button>
			                      </td>
			                       <td class="text-center">
			                          <c:if test="${jumpNode==1 }">
			                              <button type="button" class="btn btn-primary btn-xs cnoj-open-blank" data-title="任意跳转" data-width="600" 
			                              data-uri="process/mgr/jumpNodeSet?processId=${datas[0]}&orderId=${datas[1] }&refreshUri=${refreshUri}" data-target="${target }"><i class="glyphicon glyphicon-random"></i> 跳转</button>
			                          </c:if>
			                          <c:if test="${delAuth==1}">
				                         <button type="button" class="btn btn-danger btn-xs cnoj-post-data" data-del-alert="您确定要删除，该流程实例吗？" data-param="id=${datas[1]}" data-uri="${delUrl }" data-target="${target }" data-refresh-uri="${searchUri}&page=${page}"><i class="glyphicon glyphicon-trash"></i> 删除</button>
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
		<cnoj:panelFooter smartResp="${smartResp}" currentUri="${currentUri }" refreshBtn="${refreshBtn }" page="${pageParam }" delBtn="${delBtn }" />
	</div>
</div>
<script type="text/javascript">
   setTimeout("loadOrderJs()", 200);
   function loadOrderJs() {
	   simpleTreeListener("#order-list");
   }
</script>