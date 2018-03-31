<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content m-t-5">
    <div class="container-fluid">
       <div class="row">
           <div class="col-sm-3 p-l-0 p-r-0">
               <div class="panel panel-default m-b-0">
	              <div class="panel-heading ui-widget-header"><strong>组织机构</strong></div>
	              <div class="panel-body p-t-5 p-l-5 p-r-5 p-b-5">
	                  <div class="cnoj-panel-tree cnoj-auto-limit-height" id="org-tree-user" data-uri="op/queryTree?resId=select_org_tree" data-redirect-uri="menu/list" data-target="#user-list" data-param-name="orgId" >
	                  </div>
	              </div>
	           </div>
           </div>
           <div class="col-sm-9 p-l-5 p-r-0">
               <div class="panel panel-default m-b-0">
	              <div class="panel-heading ui-widget-header"><strong>用户列表</strong></div>
	              <div class="panel-body p-t-5 p-l-5 p-r-5 p-b-5">
	                 <div class="cnoj-auto-limit-height" id="user-list"></div>
	              </div>
	           </div>
           </div>
       </div>
    </div><!-- container-fluid -->
</div>