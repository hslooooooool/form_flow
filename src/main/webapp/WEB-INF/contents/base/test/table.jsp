<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
    <script type="text/javascript">
    	function beforeCheck2(paramName,value) {
	    	return false;
	    }
    </script>
	<div class="panel panel-default">
	    <div class="panel-search">
              <form class="form-inline" id="search-form" method="post" role="form" action="user/list" target="#main-content">
                  <div class="form-group p-r-10">
				    <label for="search-input01">名称：</label>
				    <input type="text" class="form-control input-form-control" id="search-input01" name="name" placeholder="请输入用户名或姓名" value="${searchParam.name }"/>
				  </div>
				  <div class="form-group p-r-10">
				    <label for="org-id">组织机构：</label>
				    <input type="text" class="form-control input-form-control cnoj-input-org-tree" id="org-id" data-is-show-none="yes" name="orgId" value="${searchParam.orgId }" />
				  </div>
				  <div class="form-group p-r-10">
				    <label for="search-input02">联系信息：</label>
				    <input type="text" class="form-control input-form-control" id="search-input02" name="info" placeholder="请输入手机号码或邮箱地址" value="${searchParam.info }"/>
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
		<cnoj:table smartResp="${smartResp }" headers="用户名,姓名,单位,手机号码,QQ,邮箱,职位,状态,创建时间" 
		headerWidths="10%,12%,15%,10%,10%,16%,10%,5%,12%" 
		  isCheckbox="1" isRowSelected="1" currentUri="${currentUri }" 
		  addBtn="${addBtn }" editBtn="${editBtn }" refreshBtn="${refreshBtn }" 
		  customBtns="${customBtns}" page="${pageParam }" 
		/>
   </div>
</div>
<script type="text/javascript">
   setTimeout(function() {
   	   $("#xxx").click(function(){
   		  alert("1111"); 
   		  return false;
   	   });
   }, 500);
</script>