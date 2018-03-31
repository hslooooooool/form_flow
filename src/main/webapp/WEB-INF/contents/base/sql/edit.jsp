<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="wrap-content-dialog">
    <form class="form-horizontal" role="form" id="form-edit" action="sql/resource/update">
		<input type="hidden" name="id" value="${objBean.id}" />
		 <div class="form-group m-b-10">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10 p-l-0">
                  <input type="text" class="form-control require" name="resName" data-label-name="名称" 
                  placeholder="字母、数字、下划线组成" data-regexp="/[a-zA-Z0-9_]/" value="${objBean.resName }" />
                </div>
            </div>
            <div class="form-group m-b-10">
                <label class="col-sm-2 control-label">简单描述</label>
                <div class="col-sm-10 p-l-0">
                    <input type="text" class="form-control require" name="descr" value="${objBean.descr }" data-label-name="简单描述" />
                </div>
            </div>
            <div class="form-group m-b-10">
                <label class="col-sm-2 control-label">SQL语句</label>
                <div class="col-sm-10 p-l-0">
                    <textarea class="form-control require" name="sql" rows="8" data-label-name="SQL语句">${objBean.sql }</textarea>
                </div>
            </div>
            <div class="text-center">
                  <button type="button" class="btn btn-primary cnoj-data-submit" data-refresh-uri="sql/resource/list">
                  <i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
            </div>
	</form>
</div>