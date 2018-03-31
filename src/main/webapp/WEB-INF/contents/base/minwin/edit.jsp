<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wrap-content">
    <div class="show-content">
       <form class="form-horizontal" id="submit-data" role="form" action="minwin/edit.json" target="#main-content">
            <input type="hidden" name="id" value="${objBean.id }" />
           <div class="form-group">
		    <label for="input01" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">名称</label>
		    <div class="col-sm-9">
		      <input type="text" class="form-control require" name="name" label-name="名称" id="input01" value="${objBean.name }" />
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="input02" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">URI</label>
		    <div class="col-sm-9">
		      <input type="text" class="form-control require" name="uri" data-label-name="URI" id="input02" value="${objBean.uri }" />
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="input03" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">更多URI</label>
		    <div class="col-sm-9">
		      <input type="text" class="form-control" name="moreUri" data-label-name="更多URI" id="input03" value="${objBean.moreUri}" />
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="input04" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">高度</label>
		    <div class="col-sm-9">
		      <input type="text" class="form-control" name="height" data-label-name="高度" id="input04" value="${objBean.height}" />
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="input07" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">显示标题</label>
		    <div class="col-sm-9">
		        <select id="input07" class="form-control" name="isShowTitle">
				  <option value="1" ${objBean.isShowTitle=='1'?'selected':''}>显示</option>
				  <option value="0" ${objBean.isShowTitle=='0'?'selected':''}>隐藏</option>
				</select>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="input05" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">状态</label>
		    <div class="col-sm-9">
		        <select id="input05" class="form-control" name="state">
				  <option value="1" ${objBean.state=='1'?'selected':''}>有效</option>
				  <option value="0" ${objBean.state=='0'?'selected':''}>无效</option>
				</select>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="input06" class="col-sm-2 control-label fw-normal p-l-0 p-r-0">序号</label>
		    <div class="col-sm-9">
		      <input type="text" class="form-control" name="sortOrder" data-format="num" data-length="1,5" data-label-name="序号" value="${objBean.sortOrder }" id="input06" />
		    </div>
		  </div>
		  <div class="form-group m-b-5">
		    <div class="col-sm-10 text-right">
		        <button class="btn btn-primary cnoj-data-submit" data-refresh-uri="minwin/list"><i class="glyphicon glyphicon-ok-sign"></i> 确定</button>
		    </div>
		  </div>
       </form>
    </div>
</div>