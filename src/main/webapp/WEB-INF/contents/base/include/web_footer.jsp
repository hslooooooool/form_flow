<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy");
  String year = dateFormater.format(new Date());
%>
    <div class="wrap-footer">
		  <div class="footer">
		    <div class="text-center">
		          <p>${project.copyright }</p>
		     </div>
		  </div>
    </div>
  </body>
</html>