<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="wrap-content">
    <div class="panel panel-default">
		   <table class="table table-bordered">
		       <tbody>
		          <tr>
		              <th style="width: 100px;">名称</th>
		              <td>${objBean.resName }</td>
		          </tr>
		          <tr>
		              <th style="width: 100px;">是否过滤</th>
                      <td>${objBean.isFilter ? '是' : '否' }</td>
		           </tr>
		           <tr>
                       <th style="width: 100px;">简单描述</th>
                       <td>${objBean.descr}</td>
                   </tr>
                   <tr>
                       <th style="width: 100px;">SQL语句</th>
                       <td>${objBean.sql} </td>
                   </tr>
                   <tr>
                       <th style="width: 100px;">创建人</th>
                       <td>${objBean.userId } </td>
                   </tr>
                   <tr>
                       <th style="width: 100px;">创建时间</th>
                       <td>
                       <fmt:formatDate value="${objBean.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                       </td>
                    </tr>
                    <tr>
                        <th style="width: 120px;">最近一次修改人</th>
                        <td>${objBean.lastUserId } </td>
                     </tr>
                     <tr>
                        <th style="width: 130px;">最近一次修改时间</th>
                        <td>
                        <fmt:formatDate value="${objBean.lastModifyTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                     </tr>
		        </tbody>
		    </table>
    </div>
</div>