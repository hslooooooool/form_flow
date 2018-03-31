<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
	<div class="panel panel-default no-border">
	    <div class="panel-search">
              <form class="form-inline cnoj-entry-submit" id="search-form" method="post" role="form" action="resource/list" target="#main-content">
                  <div class="form-group p-r-10">
				    <label for="search-input01">名称：</label>
				    <input type="text" class="form-control input-form-control" id="search-input01" name="name" placeholder="请输入名称" value="${searchParam.name }"/>
				  </div>
				  <div class="form-group p-r-10">
				    <label for="search-select02">类型：</label>
				    <select class="form-control cnoj-select select-form-control" data-uri="dict/item/RESOURCE_TYPE.json" name="type" data-default-value="${searchParam.type }"  id="search-select02">
				       <option value="">请选择</option>
				    </select>
				  </div>
				  <div class="form-group p-r-10">
				    <label for="search-select01">状态：</label>
				    <select class="form-control cnoj-select select-form-control" data-uri="dict/item/DATA_STATE.json" name="state" data-default-value="${searchParam.state }"  id="search-select01">
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
	    <cnoj:table smartResp="${smartResp }" headers="资源名称,资源URI,资源类型,拥有的操作权限,资源状态" 
		headerWidths="20%,30%,10%,25%,12%" isCheckbox="1" isRowSelected="1" currentUri="${currentUri }" 
		  addBtn="${addBtn }" editBtn="${editBtn }" delBtn="${delBtn }" refreshBtn="${refreshBtn }" 
		  customBtns="${customBtns}" page="${pageParam }" 
		/>
	</div>
</div>