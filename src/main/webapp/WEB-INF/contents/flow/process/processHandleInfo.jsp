<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="wrap-content">
		<div class="panel panel-default">
		    <table class="table table-striped table-bordered table-condensed">
		        <thead>
		           <tr data-height="36">
		              <th style="width: 20%">环节</th>
		              <th style="width:150px">到达时间</th>
		              <th style="width:120px">处理人</th>
		              <th style="width: 20%">处理意见</th>
		              <th style="width:150px">领取时间</th>
		              <th style="width:150px">完成时间</th>
		           </tr>
		        </thead>
		        <tbody>
		           <c:choose>
		               <c:when test="${smartResp.result != 1 }">
		                  <tr><td colspan="6" class="text-center">没有相关的流转记录！</td></tr>
		               </c:when>
		               <c:otherwise>
		                   <c:forEach var="datas" items="${smartResp.datas }" varStatus="st" >
			                   <tr id="t-${datas.id}">
			                     <td style="width: 20%">${datas.displayName }</td>
			                     <td style="width:150px">${datas.createTime }</td>
			                     <td style="width:120px">${datas.operator }</td>
			                     <td style="width: 20%">${datas.variable }</td>
			                     <td style="width:150px">${datas.takeTime }</td>
			                     <td style="width:150px">${datas.finishTime }</td>
			                   </tr>
		                   </c:forEach>
		               </c:otherwise>
		           </c:choose>
		        </tbody>
		    </table>
	</div>
</div>