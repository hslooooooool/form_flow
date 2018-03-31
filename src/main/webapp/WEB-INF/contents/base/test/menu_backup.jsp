<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<s:action name="include/header" executeResult="true" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/left-menu.js"></script>
<!----------左侧菜单--------------->
<div class="middle">
<div class="menu">
  <div class="breadLine"> </div>
  <ul class="navigation">
    <li class="active"><a href="index" class="retract"><span class="text"><i class="glyphicon glyphicon-th-large"></i> <label>面板</label></span></a></li>
    <li class="openable"><a href="#"><span class="font-size-12"><i class="glyphicon glyphicon-chevron-right menu-open"></i></span> <span class="text"><i class="glyphicon glyphicon-list"></i> <label>UI 元素</label></span> </a>
      <ul>
        <li> <a href="#" ><span class="text"><i class="glyphicon glyphicon-th"></i> <label>UI 元素</label></span> </a> </li>
        <li> <a href="widgets.html"><span class="text"><i class="glyphicon glyphicon-th-large"></i><label>组件</label></span> </a> </li>
        <li> <a href="buttons.html"><span class="text"><i class="glyphicon glyphicon-chevron-right"></i> <label>按钮</label></span> </a> </li>
        <li> <a href="icons.html"><span class="text"><i class="glyphicon glyphicon-fire"></i> <label>图标</label></span> </a> </li>
      </ul>
    </li>
    <li> <a href="test.html"><span class="text"><span class="glyphicon glyphicon-briefcase"></span> <label>表单</label></span> </a> </li>
    <li class="openable"><a href="#"><span class="font-size-12"><i class="glyphicon glyphicon-chevron-right menu-open"></i></span> <span class="text"><i class="glyphicon glyphicon-comment"></i> <label>消息</label></span> </a>
      <ul>
        <li> <a href="messages.html"> <span class="icon icon-comment"></span><span class="text">消息组件</span></a> </li>
      </ul>
    </li>
    <li> <a href="statistic.html"><span class="text"><span class="glyphicon glyphicon-signal"></span> 统计</span> </a> </li>
    <li> <a href="tables.html"><span class="text"><span class="glyphicon glyphicon-list-alt"></span> 表格</span> </a> </li>
    <li class="openable"><a href="#"><span class="font-size-12"><i class="glyphicon glyphicon-chevron-right menu-open"></i></span> <span class="text"><i class="glyphicon glyphicon-comment"></i> 其他</span> </a>
      <ul>
        <li> <a href="gallery.html"> <span class="icon icon-picture"></span><span class="text">Gallery</span> </a> </li>
        <li> <a href="typography.html"> <span class="icon icon-pencil"></span><span class="text">Typography</span> </a> </li>
        <li> <a href="users.html"> <span class="icon icon-user"></span><span class="text">Users</span> </a> </li>
      </ul>
    </li>
  </ul>
  <div class="dr"><span></span></div>
</div>
<!----------右侧内容区域--------------->
<div class="content">
  <div class="breadLine"> ···</div>
</div>
</div>
</body>