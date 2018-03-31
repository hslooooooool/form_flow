<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<link href="${pageContext.request.contextPath}/plugins/form/css/form.table.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/form/js/form.table.js"></script>
<div class="wrap-content">
	<div class="panel panel-default no-border">
	    <div class="panel-search p-t-0">
              <form class="form-inline cnoj-entry-submit" id="search-form" method="post" role="form" action="form/table/list">
                  <div class="form-group p-r-10">
				    <label for="search-input01">名称：</label>
				    <input type="text" class="form-control input-form-control" id="search-input01" name="name" placeholder="请输入表名" value="${searchParam.name }"/>
				  </div>
				  <div class="form-group p-l-10">
					  <span class="btn btn-primary btn-sm cnoj-search-submit">
						<i class="glyphicon glyphicon-search"></i>
						<span>搜索</span>
					  </span>
				  </div>
              </form>
          </div>
		<!-- table -->
	    <cnoj:table smartResp="${smartResp }" headers="表名,备注,创建人,创建时间,字段数" currentUri="${currentUri }" 
	     isCheckbox="1" isRowSelected="1" alinks="${alinks }" 
	     delBtn="${delBtn }" refreshBtn="${refreshBtn }" addBtn="${addBtn }" editBtn="${editBtn }" page="${pageParam }"
	    />
	</div>
</div>
