<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default no-border">
	   <div class="panel-search">
	       <form class="form-inline cnoj-entry-submit" id="report-search-form" method="post" role="form" action="${uri }">
	              <c:if test="${not empty searchFields }">
		              <c:forEach var="searchField" items="${searchFields }" varStatus="st">
		                 <div class="form-group p-r-10">
			                 <label>${searchField.title }：</label>
			                 <input type="text" class="form-control input-form-control" name="${searchField.searchName }" placeholder="请输入${searchField.title }" value="${searchValues[st.index] }" />
		                 </div>
		              </c:forEach>
		              <div class="form-group p-l-10">
						  <span class="btn btn-primary btn-sm cnoj-search-submit">
								<i class="glyphicon glyphicon-search"></i>
								<span>搜索</span>
							  </span>
					  </div>
				  </c:if>
				  <c:if test="${reportProp.isImport == 1 }">
				      <div class="form-group pull-right">
				        <!--  
                         <button type="button" data-uri="report/instance/export?reportId=${reportId }" class="btn btn-info btn-sm report-export">
                            <i class="fa fa-file-excel-o" aria-hidden="true"></i>
                            <span>导出</span>
                          </button>
                          -->
                          <cnoj:cusBtn currentUri="${currentUri}" customBtn="${customBtn }" />
                      </div>
				  </c:if>
	       </form>
	    </div>
		<!-- table -->
	    <cnoj:table smartResp="${smartResp }" headers="${headerTitles }" currentUri="${currentUri }" 
	     isCheckbox="${reportProp.isCheckbox }" isId="${reportProp.isHasId }" isIdShow="${reportProp.isShowId }" 
	     isRowSelected="1" alinks="${alinks }" refreshBtn="${refreshBtn }" isOriginalTable="${isOriginalTable }"
	     page="${pageParam }" customBtns="${customBtns }"
	    />
	</div>
	<script type="text/javascript">
	   //导出
	   $(".report-export").click(function(){
	       var $this = $(this);
	       var uri = $this.data("uri");
	       if(utils.isNotEmpty(uri)) {
	           if(uri.indexOf('?') > -1) {
	               uri += '&';
	           } else {
	               uri += '?'
	           }
	       }
	       var $searchForm = $this.closest("form");
	       var param = $searchForm.serialize();
	       uri += param;
	       location.href = uri;
	       return false;
	   });
	</script>
</div>
