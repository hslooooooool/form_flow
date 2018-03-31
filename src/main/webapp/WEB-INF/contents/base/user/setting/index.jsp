<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="wrap-content">
	<div class="user-setting">
	   <h4 class="m-t-5 p-l-10"><i class="glyphicon glyphicon-t-2 glyphicon-cog"></i> 个人设置</h4>
	   <s:if test="smartResp.result == 1">
	        <div class="panel-tabs-wrap">
				    <div class="panel-tabs-tab">
				        <ul class="nav nav-tabs" role="tablist">
				            <c:forEach var="setMenu" items="${smartResp.datas}" varStatus="st">
				                <li class="${st.index==0?"active":""}"><a href="#setting-tab-${st.index+1}" role="presentation" data-toggle="tab"> ${setMenu.name }</a></li>
				            </c:forEach>
						</ul>
		  			</div>
		  			<div class="tab-content panel-tab-content">
		  			      <c:forEach var="setMenu" items="${smartResp.datas}" varStatus="st">
				                <div role="tabpanel" class="tab-pane ${st.index==0?"active":"" }" id="setting-tab-${st.index+1}">
				                  <script type="text/javascript">
				                     loadUri("#setting-tab-${st.index+1}",'${setMenu.uri}');
				                  </script>
				                </div>
				           </c:forEach>
			  		</div>
			</div>
		</s:if>
	</div>
</div>
<script type="text/javascript">
    $(function(){
    	var h = $("#main-content").height();
    	h = h - $(".user-setting h4").outerHeight(true)-$(".user-setting .panel-tabs-tab").outerHeight(true);
    	$(".panel-tab-content").height(h-12);
    });
</script>