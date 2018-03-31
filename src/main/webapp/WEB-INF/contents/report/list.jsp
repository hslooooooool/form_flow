<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default no-border">
	    <div class="panel-search">
              <form class="form-inline cnoj-entry-submit" id="search-form" method="post" role="form" action="report/list">
                  <div class="form-group p-r-10">
				    <label for="search-report-name">名称：</label>
				    <input type="text" class="form-control input-form-control" id="search-report-name" name="name" placeholder="请输入表单名称" value="${searchParam.name }"/>
				  </div>
				  <div class="form-group p-r-10">
				    <label for="search-report-type">类型：</label>
				    <select class="form-control cnoj-select select-form-control" data-uri="dict/item/REPORT_TYPE.json" name="type" data-default-value="${searchParam.type }"  id="search-report-type">
				       <option value="">请选择</option>
				    </select>
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
	    <cnoj:table smartResp="${smartResp }" headers="名称,类型,是否支持导出,SQL名称,创建人,创建时间" currentUri="${currentUri }" 
	     isCheckbox="1" isRowSelected="1" alinks="${alinks }"  delBtn="${delBtn }" refreshBtn="${refreshBtn }" page="${pageParam }" customBtns="${customBtns }"
	    />
	</div>
</div>
