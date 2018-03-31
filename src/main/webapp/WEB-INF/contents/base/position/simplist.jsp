<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
    <div class="panel no-border">
        <div class="panel-search borer-bottom">
              <form class="form-inline cnoj-entry-submit" id="search-form-position" method="post" role="form" action="position/simplist" target="#position-tab">
                  <div class="form-group p-r-10">
				    <label for="search-input01">名称：</label>
				    <input type="text" class="form-control input-form-sm-control" id="search-input01" name="name" placeholder="请输入职位名称" value="${searchParam.name }"/>
				  </div>
				  <div class="form-group p-l-10">
					  <span class="btn btn-info btn-sm cnoj-search-submit">
						<i class="glyphicon glyphicon-search"></i>
						<span>搜索</span>
					  </span>
				  </div>
              </form>
          </div>
		<cnoj:tableItem smartResp="${smartResp }" headers="名称,所属组织机构" isRowSelected="1" 
		  selectedEventProp="${selectedEventProp }" page="${pageParam }" 
		 />
	</div>
</div>