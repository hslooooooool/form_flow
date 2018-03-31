<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
    <div class="panel no-border">
        <div class="panel-search">
              <form class="form-inline cnoj-entry-submit" id="search-form-user" method="post" role="form" action="role/orglist" target="#role-org-tab">
                  <input type="hidden" name="id" value="${searchParam.id }" />
                  <div class="form-group p-r-10">
				    <label for="search-input02">关键字：</label>
				    <input type="text" class="form-control input-form-sm-control" id="search-input02" name="name" placeholder="请输入名称或代码" value="${searchParam.name }"/>
				  </div>
				  <div class="form-group p-l-10">
					  <span class="btn btn-info btn-sm cnoj-search-submit">
						<i class="glyphicon glyphicon-search"></i>
						<span>搜索</span>
					  </span>
				  </div>
              </form>
          </div>
		<cnoj:table smartResp="${smartResp }" headers="名称,类型,代码" isCheckbox="1" isRowSelected="1" page="${pageParam }" currentUri="${currentUri }" 
		 addBtn="${addBtn }" delBtn="${delBtn }" refreshBtn="${refreshBtn }" 
		 />
	</div>
</div>