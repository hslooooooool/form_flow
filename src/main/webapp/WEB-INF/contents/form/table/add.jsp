<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="wrap-content-dialog" id="create-form-table">
    <form class="form-horizontal" role="form" id="form-add" action="form/table/create.json" target="#main-content">
         <div class="panel panel-default">
           <div class="panel-heading p-t-8 p-b-8 p-l-4 text-bold">基本信息</div>
			<table class="table table-bordered table-condensed table-sm">
			   <tbody>
			      <tr>
			         <th class="text-right" style="width: 80px;">表名：</th>
			         <td colspan="3">
			            <div class="col-sm-12 p-l-0 p-r-0">
			                <input type="text" class="form-control input-sm require" data-label-name="表名" name="table.tableName" placeholder="请输入表名,表名由“t_pf_”后跟字母、数字或下划线组成；如：t_pf_leave" />
			            </div>
			         </td>
			      </tr>
			      <tr>
			         <th class="text-right" style="width: 80px;">注释：</th>
			         <td colspan="3">
			            <div class="col-sm-12 p-l-0 p-r-0">
			               <textarea class="form-control" name="table.remark" rows="3"></textarea>
			            </div>
			         </td>
			      </tr>
			      <tr class="bg-color-pd">
			        <td colspan="4">
			           <div class="col-sm-6 p-t-5 p-b-3 p-l-0 p-r-0 color-pd text-bold">字段信息</div>
			           <div class="col-sm-6 p-t-5 p-b-3 p-r-5 text-right"><button type="button" class="add-field btn btn-primary btn-xs"><i class="glyphicon glyphicon-plus-sign"></i> 添加</button></div>
			        </td>
			      </tr>
			      <tr>
			         <td colspan="4" class="seamless-embed-table">
			            <table class="table table-bordered table-condensed table-sm">
			               <thead>
			                  <tr class="ui-state-default" style="border: none;">
			                     <td style="width: 40px;">序号</td>
			                     <td style="width: 180px;">字段名称</td>
			                     <td style="width: 150px;">数据类型</td>
			                     <td style="width: 150px;">长度/设置</td>
			                     <td colspan="2">注释</td>
			                  </tr>
			               </thead>
			           </table>
			           <div class="table-wrap-limit create-table-field">
				           <table class="table table-condensed table-sm">
				               <tbody>
				               </tbody>
				            </table>
			            </div>
			         </td>
			      </tr>
			   </tbody>
	        </table>
          </div><!-- panel -->
          <div class="text-center p-t-10">
		    <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="form/table/list">
		    <i class="glyphicon glyphicon-ok-sign"></i> 保存</button>
	      </div>
	  </form>
 </div>
 <script type="text/javascript">
    //plugins/form/js/form.table.js
    setTimeout("loadJs()", 200);
    function loadJs(){
    	$(".create-table-field").createTableListener();
    }
 </script>