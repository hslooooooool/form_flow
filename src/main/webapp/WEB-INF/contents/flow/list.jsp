<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default">
	    <div class="panel-search">
              <form class="form-inline cnoj-entry-submit" id="search-form" method="post" role="form" action="flow/list" target="#main-content">
                  <div class="form-group p-r-10">
				    <label for="search-input01">名称：</label>
				    <input type="text" class="form-control input-form-control" id="search-input01" name="name" placeholder="请输入名称" value="${name }"/>
				  </div>
				  <div class="form-group p-r-10">
				    <label for="search-select02">类型：</label>
				    <select class="form-control cnoj-select select-form-control" data-uri="dict/item/FLOW_TYPE.json" name="type" data-default-value="${type }"  id="search-select02">
				       <option value="">请选择</option>
				    </select>
				  </div>
				  <div class="form-group p-r-10">
				    <label for="org-id">组织机构：</label>
				    <input type="text" class="form-control input-form-control cnoj-input-org-tree" id="org-id" data-is-show-none="yes" name="orgId" value="${orgId }" />
				  </div>
				  <div class="form-group p-r-10">
				    <label for="search-select01">状态：</label>
				    <select class="form-control cnoj-select select-form-control" data-uri="dict/item/DATA_STATE.json" name="state" data-default-value="${state }"  id="search-select01">
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
          </div><!-- panel-search -->
	    <cnoj:table smartResp="${smartResp }" headers="名称,显示名称,类型,所属机构,状态,版本号,创建人,创建时间" currentUri="${currentUri }" 
	     isCheckbox="1" isRowSelected="1" alinks="${alinks}" 
	     delBtn="${delBtn }" refreshBtn="${refreshBtn }" page="${pageParam }" customBtns="${customBtns }"
	    />
	</div>
</div>
