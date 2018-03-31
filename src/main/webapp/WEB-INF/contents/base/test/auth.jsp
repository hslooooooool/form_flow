<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="cnoj" uri="/cnoj-tags" %>
<div class="wrap-content">
   <h2>测试页面</h2>
   <cnoj:addBtn currentUri="${currentUri }" name="添加测试"  /> 
   <cnoj:delBtn  currentUri="${currentUri }" name="删除测试"  />
   <cnoj:cusBtn currentUri="${currentUri }" id="changePwd" name="测试中" btnIcon="glyphicon-music" />
   <cnoj:refreshBtn currentUri="${currentUri }" name="刷新测试" />
</div>
