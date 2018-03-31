<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="panel panel-info m-t-5">
		<table class="table table-striped table-bordered table-condensed" id="process-attachment">
			<thead>
				<tr class="ui-state-default" data-height="36">
				   <th>序号</th>
				   <th>附件名称</th>
				   <th>大小</th>
				   <th>类型</th>
				   <th>上传时间</th>
				   <th>上传人</th>
				</tr>
			</thead>
			<body>
			<c:choose>
			   <c:when test="${smartResp.result != 1 }">
			      <tr><td colspan="6">没有查询到相关数据！</td></tr>
			      <script type="text/javascript">
				      var $attTabA = $("#view-process-att-tab-a");
			          if($attTabA.find("span").hasClass("badge")) {
			        	  $attTabA.find(".badge").remove();
			          }
			      </script>
			   </c:when>
			   <c:otherwise>
			       <c:forEach var="datas" items="${smartResp.datas }" varStatus="st" >
			         <tr>
				      <td class="seq-no">${st.index+1 }</td>
				      <td><a href="download/att?id=${datas[1]}" target="_blank">${datas[2]}</a></td>
				      <td>${datas[3]}</td>
				      <td>${datas[4]}</td>
				      <td>${datas[5]}</td>
				      <td>${datas[6]}</td>
				    </tr>
			       </c:forEach>
			       <script type="text/javascript">
			         var $attTabA = $("#view-process-att-tab-a");
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
   </div>