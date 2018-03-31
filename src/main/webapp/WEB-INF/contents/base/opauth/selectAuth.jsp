<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="fieldset-nomal">
	<fieldset>
		<legend>操作权限列表</legend>
		<ul class="select-op-auth">
		    <c:choose>
		       <c:when test="${smartResp.result == 1}">
		          <c:forEach  var="opauth" items="${smartResp.datas}" varStatus="st">
					<li class="li-item">
						<div class="checkbox">
							 <label><input ${opauth.isChecked==1?'checked':'' } name="auths[${st.index }].value" value="${opauth.value }" type="checkbox"  />&nbsp;${opauth.name }</label>
						</div>
					</li>
				 </c:forEach>
		       </c:when>
		       <c:otherwise>
		         <li>没有查询操作权限信息</li>
		       </c:otherwise>
		    </c:choose>
		</ul>
	</fieldset>
</div>
