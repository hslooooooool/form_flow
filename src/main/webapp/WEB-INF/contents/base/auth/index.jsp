<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
  <div class="container-fluid">
	   <div class="row">
		   <div class="col-sm-4 p-l-0 p-r-0">
		      <div class="panel panel-default m-b-0">
				    <div class="panel-tabs-wrap" id="config-auth-tabs">
				         <div class="panel-heading p-0 p-t-3">
							<div class="panel-tabs-tab">
								<ul class="nav nav-tabs" role="tablist">
									<li class="active"><a href="#role-tab" role="presentation" data-toggle="tab">角色</a></li>
									<li><a href="#user-tab" role="presentation" data-toggle="tab">用户</a></li>
									<li><a href="#org-tab" role="presentation" data-toggle="tab">组织机构</a></li>
									<li><a href="#position-tab" role="presentation" data-toggle="tab">职位</a></li>
								</ul>
							</div>
						</div>
						<div class="panel-body p-0">
							<div class="tab-content panel-tab-content bg-color-white cnoj-auto-limit-height" style="padding: 0">
							  	<div role="tabpanel" class="tab-pane active" id="role-tab">
							  	   <div class="cnoj-load-url" data-uri="role/simplist" ></div>
								</div>
								<div role="tabpanel" class="tab-pane" id="user-tab">
								   <div class="cnoj-load-url" data-uri="user/simplist" ></div>
								</div>
								<div role="tabpanel" class="tab-pane" id="org-tab">
							  	   <div class="cnoj-panel-org-tree" id="org-tree-auth" data-is-node-link="yes" data-is-default-load="no" data-redirect-uri="auth/orgHas" data-target="#has-auth-list" data-param-name="id" ></div>
								</div>
								<div role="tabpanel" class="tab-pane" id="position-tab">
								   <div class="cnoj-load-url" data-uri="position/simplist" ></div>
								</div>
							</div>
						</div>
					</div>
				</div><!-- panel -->
			</div><!-- col -->
			<div class="col-sm-8 p-l-5 p-r-0">
			    <div id="has-auth-list">
			       <div class="panel panel-default m-b-0">
			        <div class="panel-heading ui-widget-header">已拥有的权限</div>
			        <div class="panel-body p-b-0">
			           <div class="cnoj-auto-limit-height"></div>
			        </div>
			      </div>
			  </div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	  $(function(){
		  var isResize = false;
		  $("#config-auth-tabs").find('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			  var id = $(e.target).attr("href");
			  var $tag = $(id);
			  tableWrapListener($tag, isResize);
			  limitHeightListener($tag, isResize);
		  });
		  $(window).resize(function(){
			  isResize = true; 
	      });
	  });
	</script>
</div>
