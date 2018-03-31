/**
 *
 * 农历
 *
 */
function LunarCalendar(date) {
	this.lunarInfo = new Array(
			0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2,
			0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977,
			0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970,
			0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,0x186e3,0x092e0,0x1c8d7,0x0c950,
			0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,0x025d0,0x092d0,0x0d2b2,0x0a950,0x0b557,
			0x06ca0,0x0b550,0x15355,0x04da0,0x0a5d0,0x14573,0x052d0,0x0a9a8,0x0e950,0x06aa0,
			0x0aea6,0x0ab50,0x04b60,0x0aae4,0x0a570,0x05260,0x0f263,0x0d950,0x05b57,0x056a0,
			0x096d0,0x04dd5,0x04ad0,0x0a4d0,0x0d4d4,0x0d250,0x0d558,0x0b540,0x0b5a0,0x195a6,
			0x095b0,0x049b0,0x0a974,0x0a4b0,0x0b27a,0x06a50,0x06d40,0x0af46,0x0ab60,0x09570,
			0x04af5,0x04970,0x064b0,0x074a3,0x0ea50,0x06b58,0x055c0,0x0ab60,0x096d5,0x092e0,
			0x0c960,0x0d954,0x0d4a0,0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,0x092d0,0x0cab5,
			0x0a950,0x0b4a0,0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,0x15176,0x052b0,0x0a930,
			0x07954,0x06aa0,0x0ad50,0x05b52,0x04b60,0x0a6e6,0x0a4e0,0x0d260,0x0ea65,0x0d530,
			0x05aa0,0x076a3,0x096d0,0x04bd7,0x04ad0,0x0a4d0,0x1d0b6,0x0d250,0x0d520,0x0dd45,
			0x0b5a0,0x056d0,0x055b2,0x049b0,0x0a577,0x0a4b0,0x0aa50,0x1b255,0x06d20,0x0ada0);
	this.Animals=new Array("鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪");
	this.Gan=new Array("甲","乙","丙","丁","戊","己","庚","辛","壬","癸");
	this.Zhi=new Array("子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥");
	this.date = parseTime();
	this.SY = this.date.getYear(); 
	this.SM = this.date.getMonth();
	this.SD = this.date.getDate();
	
	function parseTime() {
		if(typeof(date) === 'number') {
			var dateTmp = new Date();
			dateTmp.setTime(date);
			return dateTmp;
		} else if(typeof(date) === 'string') {
			var dateTimeStr = date.split(" ");
			var dateTmp = new Date();
			var dateStr = dateTimeStr[0].split("-");
			dateTmp.setFullYear(dateStr[0], dateStr[1], dateStr[2]);
			return dateTmp;
		} else if(typeof(date) === 'undefined' || 
				null == date || date == ''){
			return new Date();
		} else {
			return date;
		}
	};
	
	//
	this.cyclical = function(num) {
		return (this.Gan[num%10] + this.Zhi[num%12]);
	};
	//传回农历 y年的总天数
	this.lYearDays = function(y) {
		var i, sum = 348
	    for(i=0x8000; i>0x8; i>>=1) sum += (this.lunarInfo[y-1900] & i)? 1: 0
	    return(sum+this.leapDays(y));
	};
	//传回农历 y年闰月的天数
	this.leapDays = function(y) {
		if(this.leapMonth(y))  
			return((this.lunarInfo[y-1900] & 0x10000)? 30: 29);
		else 
			return(0);
	};
	//传回农历 y年闰哪个月 1-12 , 没闰传回 0
	this.leapMonth = function(y) { 
		return (this.lunarInfo[y-1900] & 0xf);
	};
	//传回农历 y年m月的总天数
	this.monthDays = function(y,m) { 
		return ((this.lunarInfo[y-1900] & (0x10000>>m))? 30: 29 );
	};
	//
	this.cDay = function(m,d){
		//alert(m+","+parseInt(d%10));
		var nStr1 = new Array('日','一','二','三','四','五','六','七','八','九','十');
		var nStr2 = new Array('初','十','廿','卅','　');
		var s;
		if (m>10) {
			s = '十'+nStr1[m-10];
		} else {
			s = nStr1[m];
		}
		s += '月'
		switch (d) {
			case 10:s += '初十'; break;
			case 20:s += '二十'; break;
			case 30:s += '三十'; break;
			default:
				s += nStr2[Math.floor(d/10)];
			    s += nStr1[parseInt(d%10)];
		}
		return (s);
	};

	//==== 算出农历, 传入日期物件, 传回农历日期物件
	//该物件属性有 .year .month .day .isLeap .yearCyl .dayCyl .monCyl
	this.Lunar = function() {
		var i, leap=0, temp=0;
		var baseDate = new Date(1900,0,31);
		var offset = (this.date - baseDate)/86400000;
		var lunarProp = new LunarProp();
		lunarProp.dayCyl = offset + 40;
		lunarProp.monCyl = 14;
		for(i=1900; i<2050 && offset>0; i++) {
			temp = this.lYearDays(i);
			offset -= temp;
			lunarProp.monCyl += 12;
		}
		if(offset<0) {
			offset += temp;
			i--;
			lunarProp.monCyl -= 12;
		}
		lunarProp.year = i;
		lunarProp.yearCyl = i-1864;
		leap = this.leapMonth(i); //闰哪个月
		lunarProp.isLeap = false;
		for(i=1; i<13 && offset>0; i++) {
			//闰月
			if(leap>0 && i==(leap+1) && lunarProp.isLeap==false) { 
				--i; lunarProp.isLeap = true; 
				temp = this.leapDays(lunarProp.year);
			} else { 
				temp = this.monthDays(lunarProp.year, i); 
			}
		//解除闰月
			if(lunarProp.isLeap==true && i==(leap+1)) 
				lunarProp.isLeap = false;
			offset -= temp;
			if(lunarProp.isLeap == false) 
				lunarProp.monCyl++;
		}
		if(offset==0 && leap>0 && i==leap+1)
			if(lunarProp.isLeap){ 
				lunarProp.isLeap = false;
			} else { 
				lunarProp.isLeap = true; 
				--i; 
				--lunarProp.monCyl;
			}
		if(offset<0){
			offset += temp;
			--i;
			--lunarProp.monCyl;
		}
		lunarProp.month = i;
		lunarProp.day = offset + 1;
		
		return lunarProp;
	}

}


/**
 * 
 */
LunarCalendar.prototype.YYMMDD = function(){ 
	var cl = '<font color="green" STYLE="font-size:13pt;">'; 
	if (this.date.getDay() == 0) cl = '<font color="#c00000" STYLE="font-size:13pt;">'; 
	if (this.date.getDay() == 6) cl = '<font color="green" STYLE="font-size:13pt;">';
	return (cl+SY+'年'+(SM+1)+'月'+'</font>'); 
}

/**
 * 
 * @returns {String}
 */
LunarCalendar.prototype.weekday = function(){ 
	var day = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
	var cl = '<font color="green" STYLE="font-size:9pt;">'; 
	if (this.date.getDay() == 0) cl = '<font color="green" STYLE="font-size:9pt;">';
	if (this.date.getDay() == 6) cl = '<font color="red" STYLE="font-size:9pt;">'; 
	return (cl+ day[now.getDay()]+ '</font>'); 
}

/**
 * 
 */
LunarCalendar.prototype.solarDay1 = function(){
	var lDObj = this.Lunar();
	var cl = '<font color="#9933CC" STYLE="font-size:9pt;">';
	var tt = '【'+this.Animals[(SY-4)%12]+'】'+this.cyclical(lDObj.monCyl)+'月 '+this.cyclical(lDObj.dayCyl++)+'日';
	return (cl+tt+'</font>');
}

/**
 * 
 * @returns {String}
 */
LunarCalendar.prototype.solarDay2 = function(){
  var lDObj = this.Lunar();
  var cl = '<font color="green" STYLE="font-size:9pt;">'; 
  //农历BB'+(cld[d].isLeap?'闰 ':' ')+cld[d].lMonth+' 月 '+cld[d].lDay+' 日
  var tt = this.cyclical(SY-1900+36)+'年 '+this.cDay(lDObj.month,lDObj.day);
  return (cl+tt+'</font>');
}

/**
 * 
 */
LunarCalendar.prototype.solarDay3 = function(){
	var sTermInfo = new Array(0,21208,42467,63836,85337,107014,128867,150921,173149,195551,218072,240693,263343,285989,308563,331033,353350,375494,397447,419210,440795,462224,483532,504758);
	var solarTerm = new Array("小寒","大寒","立春","雨水","惊蛰","春分","清明","谷雨","立夏","小满","芒种","夏至","小暑","大暑","立秋","处暑","白露","秋分","寒露","霜降","立冬","小雪","大雪","冬至");
	var lFtv = new Array("0101*春节","0115 元宵节","0505 端午节","0707 七夕情人节","0715 中元节","0815 中秋节","0909 重阳节","1208 腊八节","1224 小年","0100*除夕");
	var sFtv = new Array("0101*元旦","0214 情人节","0308 妇女节","0309 偶今天又长一岁拉","0312 植树节","0315 消费者权益日","0401 愚人节","0418 MM的生日","0501 劳动节","0504 青年节","0512 护士节","0601 儿童节","0701 建党节 香港回归纪念","0801 建军节","0808 父亲节","0909 毛席逝世纪念","0910 教师节","0928 孔子诞辰","1001*国庆节",
	"1006 老人节","1024 联合国日","1112 孙中山诞辰","1220 澳门回归纪念","1225 圣诞节","1226 毛席诞辰");
	
	//var sDObj = new Date(this.SY,this.SM,this.SD);
	//var lDObj = new Lunar(sDObj);
	var lDObj = this.Lunar();
	var lDPOS = new Array(3);
	var festival='',solarTerms='',solarFestival='',lunarFestival='',tmp1,tmp2;
	//农历节日 
	for(i in lFtv)
	if(lFtv[i].match(/^(\d{2})(.{2})([\s\*])(.+)$/)) {
	 tmp1=Number(RegExp.$1)-lDObj.month;
	 tmp2=Number(RegExp.$2)-lDObj.day;
	 if(tmp1==0 && tmp2==0) 
		 lunarFestival=RegExp.$4;
	}
	//国历节日
	for(i in sFtv)
	if(sFtv[i].match(/^(\d{2})(\d{2})([\s\*])(.+)$/)){
	 tmp1=Number(RegExp.$1)-(SM+1);
	 tmp2=Number(RegExp.$2)-SD;
	 if(tmp1==0 && tmp2==0) 
		 solarFestival = RegExp.$4;
	}
	//节气
	tmp1 = new Date((31556925974.7*(SY-1900)+sTermInfo[SM*2+1]*60000)+Date.UTC(1900,0,6,2,5));
	tmp2 = tmp1.getUTCDate();
	if (tmp2==SD) 
		solarTerms = solarTerm[SM*2+1]; 
	tmp1 = new Date((31556925974.7*(SY-1900)+sTermInfo[SM*2]*60000)+Date.UTC(1900,0,6,2,5));
	tmp2= tmp1.getUTCDate();
	if (tmp2==SD) 
		solarTerms = solarTerm[SM*2];
	
	if(solarTerms == '' && solarFestival == '' && lunarFestival == '')
	  festival = '';
	else
	  festival = '<TABLE WIDTH=100% BORDER=0 CELLPADDING=2 CELLSPACING=0 BGCOLOR="#CCFFCC"><TR><TD align=center><marquee direction=left scrolldelay=120 behavior=alternate>'+
	  '<FONT COLOR="#FF33FF" STYLE="font-size:9pt;"><b>'+solarTerms + ' ' + solarFestival + ' ' + lunarFestival+'</b></FONT></marquee></TD>'+
	  '</TR></TABLE>';
	       
	var cl = '<font color="green" STYLE="font-size:9pt;">';
	return (cl+festival+'</font>');
}

/**
 * 显示当前时间
 * @returns {String}
 */
LunarCalendar.prototype.CurentTime = function() { 
  var now = new Date();
  var hh = now.getHours();
  var mm = now.getMinutes();
  var ss = now.getTime() % 60000; 
  ss = (ss - (ss % 1000)) / 1000; 
  var clock = hh+':'; 
  if (mm < 10) clock += '0'; 
  clock += mm+':'; 
  if (ss < 10) clock += '0'; 
  clock += ss; 
  return (clock); 
}

/**
 * 获取月日
 * @returns
 */
LunarCalendar.prototype.getMonthDay = function() {
	var lDObj = this.Lunar();
	return this.cDay(lDObj.month,lDObj.day);
}


function LunarProp() {
	this.year = 0; 
	this.month = 0;
	this.day = 0;
	this.isLeap = false;
	this.yearCyl = 0;
	this.dayCyl = 0;
	this.monCyl = 0;
}

/**
 * 显示日历
 */
$.fn.cnojCalendar = function(options) {
	var setting = {
		isShowSolarCalendar:true,
		isShowLunarCalendar:true,
		isShowHMS:true,
		showHTag:null,
		showMTag:null,
		showSTag:null
	};
	setting = $.extend(true,setting,options);
	var $this = $(this);
	initDate();
	
	/**
	 * 初始化日期时间
	 */
	function initDate() {
		var dateObj = new Date();
		var lunar = new LunarCalendar(dateObj);
	 	var lunarStr = lunar.getMonthDay();
	 	var toDateStr = utils.handleTime(dateObj,'yyyy年MM月dd日');
		if(setting.isShowSolarCalendar && setting.isShowLunarCalendar) {
		 	toDateStr += " 农历 "+lunarStr;
		} else if(!setting.isShowSolarCalendar && setting.isShowLunarCalendar) {
			toDateStr = lunarStr;
		}
		$this.html(toDateStr);
		if(setting.isShowHMS && !utils.isEmpty(setting.showHTag) 
				&& !utils.isEmpty(setting.showMTag) && !utils.isEmpty(setting.showSTag)) {
			initHMS(dateObj);
		}
	}
	
	/**
	  * 初始化时分秒
	  * @param date
	  *
	  */
	function initHMS(date) {
	 	var minutes = date.getMinutes();
	 	minutes = minutes<10?'0'+minutes:minutes;
	 	var hours = date.getHours();
	 	hours = hours<10?'0'+hours:hours;
	 	var seconds = date.getSeconds();
	 	seconds = seconds<10?'0'+seconds:seconds;
	 	
	 	$(setting.showHTag).html(hours);
	 	$(setting.showMTag).html(minutes);
	 	$(setting.showSTag).html(seconds);
	 	setInterval(function() {
	 		secondsTwinkle();
	 	}, 1000);
	 }
	
	/**
	 * 刷新秒
	 */
	function secondsTwinkle() {
	 	var date = new Date();
	 	var seconds = date.getSeconds();
	 	var minutes = date.getMinutes();
	 	var hours = date.getHours();
	 	if(seconds == 0 && minutes == 0 && hours == 0) {
	 		initDate();
	 	} else if(seconds == 0 && minutes == 0) {
	 		hours = hours<10?'0'+hours:hours;
	 		$(setting.showHTag).html(hours);
	 	} else if(seconds == 0) {
	 		minutes = minutes<10?'0'+minutes:minutes;
	 		$(setting.showMTag).html(minutes);
	 	}
	 	seconds = seconds<10?'0'+seconds:seconds;
	 	$(setting.showSTag).html(seconds);
	 }
}