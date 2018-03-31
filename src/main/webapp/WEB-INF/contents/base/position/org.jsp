<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
    <div class="container-fluid">
       <div class="row">
           <div class="col-sm-3 p-l-0 p-r-0">
               <div class="panel panel-default m-b-0">
	              <div class="panel-heading ui-widget-header"><strong>组织机构</strong></div>
	              <div class="panel-body p-t-0 p-l-5 p-r-5 p-b-0">
	                  <div class="cnoj-panel-org-tree cnoj-auto-limit-height" id="org-tree-position" data-is-node-link="yes" data-is-default-load="yes" data-redirect-uri="position/list" data-target="#position-list" data-param-name="orgId" >
	                  </div>
	              </div>
	           </div>
           </div>
           <div class="col-sm-9 p-l-5 p-r-0">
              <div class="cnoj-auto-limit-height" id="position-list"></div>
           </div>
       </div>
    </div><!-- container-fluid -->
</div>