<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="wrap-content default-page m-l-5 m-t-2">
   <div class="row m-l-0 m-r-0">
         <div class="col-sm-6 p-l-0 p-r-5">
            <div class="panel panel-default">
               <div class="panel-heading ui-state-default">
               <div class="title left">我的待办</div>
               <div class="right"><a class="cnoj-open-self" data-title="我的待办" href="process/todo">更多</a></div>
               </div>
               <div class="panel-body default-page-body">
				    <div id="my-todo" class="cnoj-load-url" data-uri="showPage/flow_process_indexTodo" ></div>
			   </div>
            </div><!-- panel -->
         </div>
          <div class="col-sm-6 p-l-5 p-r-5">
             <div class="panel panel-default">
               <div class="panel-heading ui-state-default">
               <div class="title left">新闻通知</div>
               <div class="right"><a class="open-self" href="#">更多</a></div>
               </div>
               <div class="panel-body default-page-body">
				    <div id="news">
				       
				    </div>
			   </div>
            </div><!-- panel -->
         </div>
     </div>
     <div class="row m-l-0 m-r-0 m-t-8">
         <div class="col-sm-6 p-l-0 p-r-5">
             <div class="panel panel-default m-b-0">
               <div class="panel-heading ui-state-default">
               <div class="title left">收文</div>
               <div class="right" id="more-recv"></div>
               </div>
               <div class="panel-body default-page-body">
				    <div id="recvDoc">
				       
				    </div>
			   </div>
            </div><!-- panel -->
         </div>
         <div class="col-sm-6 p-l-5 p-r-5">
             <div class="panel panel-default m-b-0">
               <div class="panel-heading ui-state-default">
	               <div class="title left">待阅</div>
	               <div class="right"><a class="open-self" href="#">更多</a></div>
               </div>
               <div class="panel-body default-page-body">
				   <div id="readDoc">
				       
				    </div>
				   
			   </div>
            </div><!-- panel -->
         </div>
     </div>
</div>
<script type="text/javascript">
   $(function(){
	   autoIndexHeight();
	   $(window).resize(function(){
		   setTimeout(function() {
			   autoIndexHeight();
		   }, 200);
	   });
	   function autoIndexHeight(){
		   var h = getMainHeight();
		   var panelBodyH = h-$(".tabs-header:eq(0)").outerHeight(true)-($(".panel-heading:eq(0)").outerHeight(true)*2) - 20;
		   panelBodyH = panelBodyH/2;
		   $(".default-page-body").height(panelBodyH);
	   }
   });
</script>