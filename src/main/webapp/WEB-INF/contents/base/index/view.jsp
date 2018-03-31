<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lunar-calendar.js"></script>
<script type="text/javascript">
$(function(){
	initDate();
	setInterval("secondsTwinkle()", 1000);
});

function initDate() {
	var date = new Date();
	var lunar = new LunarCalendar(date);
	var lunarStr = lunar.getMonthDay();
	var toDateStr = handleTime(date,'yyyy年MM月dd日');
	toDateStr += " 农历 "+lunarStr;
	$(".today-date").html(toDateStr);
	initHMS(date);
}

function initHMS(date) {
	var minutes = date.getMinutes();
	minutes = minutes<10?'0'+minutes:minutes;
	var hours = date.getHours();
	hours = hours<10?'0'+hours:hours;
	var seconds = date.getSeconds();
	seconds = seconds<10?'0'+seconds:seconds;
	
	$("#hours").html(hours);
	$("#minute").html(minutes);
	$("#seconds-twinkle").html(seconds);
}

function secondsTwinkle() {
	var date = new Date();
	var seconds = date.getSeconds();
	var minutes = date.getMinutes();
	var hours = date.getHours();
	if(seconds == 0 && minutes == 0 && hours == 0) {
		initDate();
	} else if(seconds == 0 && minutes == 0) {
		hours = hours<10?'0'+hours:hours;
		$("#hours").html(hours);
	} else if(seconds == 0) {
		minutes = minutes<10?'0'+minutes:minutes;
		$("#minute").html(minutes);
	}
	seconds = seconds<10?'0'+seconds:seconds;
	$("#seconds-twinkle").html(seconds);
}

</script>
<div class="menu">
   <div class="cnoj-ui-col">
      <div class="menu-calendar">
         <div class="today-time">
            <div class="cal-btn right">
               <div class="btn1">
                 <button class="btn btn-default btn-xs">月历</button>
               </div>
               <div class="btn1 m-t-10">
                  <button class="btn btn-default btn-xs">日历</button>         
               </div>
            </div>
            <div class="time">
                <span id="hours">12</span>:<span id="minute">30</span>:<span id="seconds-twinkle">:</span>
            </div>
         </div>
         <div class="today-date">2014年11月17日</div>
      </div>
   </div>
   <div class="cnoj-ui-col m-t-10">
      <div class="menu-schedule">
         <div class="cnoj-ui-title">日程安排</div>
         <div class="cnoj-ui-content">
            <ul>
               <li><a href="#">10月22日  16:30 项目例会[会议室]</a></li>
               <li><a href="#">10月22日   出差北京</a></li>
               <li><a href="#">10月22日  16:30 项目例会[会议室]</a></li>
               <li><a href="#">10月22日  16:30 项目例会[会议室]</a></li>
               <li><a href="#">10月22日  16:30 项目例会[会议室]</a></li>
            </ul>
         </div>
      </div>
   </div>
   
   <div class="cnoj-ui-col m-t-10">
      <div class="menu-schedule">
         <div class="cnoj-ui-title">日程安排</div>
         <div class="cnoj-ui-content">
            <ul>
               <li><a href="#">10月22日  16:30 项目例会[会议室]</a></li>
               <li><a href="#">10月22日   出差北京</a></li>
               <li><a href="#">10月22日  16:30 项目例会[会议室]</a></li>
               <li><a href="#">10月22日  16:30 项目例会[会议室]</a></li>
               <li><a href="#">10月22日  16:30 项目例会[会议室]</a></li>
            </ul>
         </div>
      </div>
   </div>
</div>
<div class="content">
	<div class="wrap-content m-l-5 m-r-0">
	        <div class="row m-l-0 m-r-0">
	            <div class="col-sm-6 p-r-20">
	               <div class="cnoj-ui-col">
		               <div class="cnoj-ui-title">我的待办</div>
		               <div class="cnoj-ui-content">
		                   <ul class="list-col index-list">
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                   </ul>
		               </div>
	               </div>
	            </div>
	            <div class="col-sm-6">
	               <div class="cnoj-ui-col">
		               <div class="cnoj-ui-title">新闻公告</div>
		               <div class="cnoj-ui-content">
		                   <ul class="list-col index-list">
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                   </ul>
		               </div>
		           </div>
	            </div>
	        </div>
	        
	        <div class="row m-t-10 m-l-0 m-r-0">
	            <div class="col-sm-6 p-r-20">
	                <div class="cnoj-ui-col">
		               <div class="cnoj-ui-title">我的经办</div>
		               <div class="cnoj-ui-content">
		                   <ul class="list-col index-list">
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                   </ul>
		               </div>
	               </div>
	            </div>
	            <div class="col-sm-6">
	               <div class="cnoj-ui-col">
		               <div class="cnoj-ui-title">邮件信息</div>
		               <div class="cnoj-ui-content">
		                    <ul class="list-col index-list">
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                      <li>
		                         <div class="col-sm-9 line-ell"><a href="#">关于放假通知事项通知新闻信息测试</a></div>
		                         <div class="col-sm-3 line-ell">2014年11月17日</div>
		                      </li>
		                   </ul>
		               </div>
	               </div>
	            </div>
	        </div>
	</div>
</div>