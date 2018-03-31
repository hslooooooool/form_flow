/**
 * @class JS 
 * JS命名空间以及一些快捷方法引用
 * @singleton 
 * @author jinghai.xiao@gmail.com
 */
var JS = {
	version : '0.1.7'
};

JS.Runtime = (function(){
	var ua = navigator.userAgent.toLowerCase(),
    check = function(r){
        return r.test(ua);
    },
    /** 
	 * @property 
	 * @type Boolean
	 */ 
	isOpera = check(/opera/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isFirefox = check(/firefox/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isChrome = check(/chrome/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isWebKit = check(/webkit/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isSafari = !isChrome && check(/safari/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isSafari2 = isSafari && check(/applewebkit\/4/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isSafari3 = isSafari && check(/version\/3/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isSafari4 = isSafari && check(/version\/4/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isIE = !isOpera && check(/msie/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isIE7 = isIE && check(/msie 7/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isIE8 = isIE && check(/msie 8/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isIE6 = isIE && !isIE7 && !isIE8,
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isGecko = !isWebKit && check(/gecko/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isGecko2 = isGecko && check(/rv:1\.8/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isGecko3 = isGecko && check(/rv:1\.9/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isWindows = check(/windows|win32/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isMac = check(/macintosh|mac os x/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isAir = check(/adobeair/),
	/** 
	 * @property 
	 * @type Boolean
	 */ 
	isLinux = check(/linux/);
	return {
		isOpera : isOpera,
		isFirefox : isFirefox,
	    isChrome : isChrome,
	    isWebKit : isWebKit,
	    isSafari : isSafari,
	    isSafari2 : isSafari2,
	    isSafari3 : isSafari3,
	    isSafari4 : isSafari4,
	    isIE : isIE,
	    isIE7 : isIE7,
	    isIE8 : isIE8,
	    isIE6 : isIE6,
	    isGecko : isGecko,
	    isGecko2 : isGecko2,
	    isGecko3 : isGecko3,
	    isWindows :isWindows,
	    isMac : isMac,
	    isAir : isAir,
	    isLinux : isLinux
	};
}());
JS.isOpera = JS.Runtime.isOpera;
JS.isFirefox = JS.Runtime.isFirefox;
JS.isChrome = JS.Runtime.isChrome;
JS.isWebKit = JS.Runtime.isWebKit;
JS.isSafari = JS.Runtime.isSafari;
JS.isSafari2 = JS.Runtime.isSafari2;
JS.isSafari3 = JS.Runtime.isSafari3;
JS.isSafari4 = JS.Runtime.isSafari4;
JS.isIE = JS.Runtime.isIE;
JS.isIE7 = JS.Runtime.isIE7;
JS.isIE8 = JS.Runtime.isIE8;
JS.isIE6 = JS.Runtime.isIE6;
JS.isGecko = JS.Runtime.isGecko;
JS.isGecko2 = JS.Runtime.isGecko2;
JS.isGecko3 = JS.Runtime.isGecko3;
JS.isWindows =JS.Runtime.isWindows;
JS.isMac = JS.Runtime.isMac;
JS.isAir = JS.Runtime.isAir;
JS.isLinux = JS.Runtime.isLinux;

JS.Syntax = {
	log : function(str){
		if(typeof console!="undefined"){
			console.log(str);
		}
	},
	nameSpace : function(){
		if(arguments.length){
			var o, d, v;
			for(var i=0,len=arguments.length; i<len; i++){
				v = arguments[i];
				if(!v){
					continue;
				}
				d = v.split(".");
				for(var j=0,len=d.length; j<len; j++){
					if(!d[j]){
						continue;
					}
					o = window[d[j]] = window[d[j]] || {};
				}
			}
		}
		return o;
	},
	apply : function(o, c, defaults){
		// no "this" reference for friendly out of scope calls
		if(defaults){
			JS.Syntax.apply(o, defaults);
		}
		if(o && c && typeof c == 'object'){
			for(var p in c){
				o[p] = c[p];
			}
		}
		return o;
	},
	override : function(origclass, overrides){
		if(overrides){
			var p = origclass.prototype;
			JS.Syntax.apply(p, overrides);
			if(JS.Runtime.isIE && overrides.hasOwnProperty('toString')){
				p.toString = overrides.toString;
			}
		}
	},
	extend : function(){
		// siple object copy
		var io = function(o){
			for(var m in o){
				this[m] = o[m];
			}
		};
		var oc = Object.prototype.constructor;

		return function(sb, sp, overrides){
			if(JS.Syntax.isObject(sp)){
				overrides = sp;
				sp = sb;
				sb = overrides.constructor != oc ? overrides.constructor : function(){sp.apply(this, arguments);};
			}
			var F = function(){},
				sbp,
				spp = sp.prototype;

			F.prototype = spp;
			sbp = sb.prototype = new F();
			sbp.constructor=sb;
			sb.superclass=spp;
			if(spp.constructor == oc){
				spp.constructor=sp;
			}
			sb.override = function(o){
				JS.Syntax.override(sb, o);
			};
			sbp.superclass = sbp.supr = (function(){
				return spp;
			});
			sbp.override = io;
			JS.Syntax.override(sb, overrides);
			sb.extend = function(o){return JS.Syntax.extend(sb, o);};
			return sb;
		};
	}(),
	callBack : function(fn,scope,arg){
		if(JS.isFunction(fn)){
			return fn.apply(scope || window,arg || []);
		}
	},
	/**
	 * <p>Returns true if the passed value is empty.</p>
	 * <p>The value is deemed to be empty if it is<div class="mdetail-params"><ul>
	 * <li>null</li>
	 * <li>undefined</li>
	 * <li>an empty array</li>
	 * <li>a zero length string (Unless the <tt>allowBlank</tt> parameter is <tt>true</tt>)</li>
	 * </ul></div>
	 * @param {Mixed} value The value to test
	 * @param {Boolean} allowBlank (optional) true to allow empty strings (defaults to false)
	 * @return {Boolean}
	 */
	isEmpty : function(v, allowBlank){
		return v === null || v === undefined || ((JS.isArray(v) && !v.length)) || (!allowBlank ? v === '' : false);
	},

	/**
	 * Returns true if the passed value is a JavaScript array, otherwise false.
	 * @param {Mixed} value The value to test
	 * @return {Boolean}
	 */
	isArray : function(v){
		return Object.prototype.toString.apply(v) === '[object Array]';
	},

	/**
	 * Returns true if the passed object is a JavaScript date object, otherwise false.
	 * @param {Object} object The object to test
	 * @return {Boolean}
	 */
	isDate : function(v){
		return Object.prototype.toString.apply(v) === '[object Date]';
	},

	/**
	 * Returns true if the passed value is a JavaScript Object, otherwise false.
	 * @param {Mixed} value The value to test
	 * @return {Boolean}
	 */
	isObject : function(v){
		return !!v && Object.prototype.toString.call(v) === '[object Object]';
	},

	/**
	 * Returns true if the passed value is a JavaScript 'primitive', a string, number or boolean.
	 * @param {Mixed} value The value to test
	 * @return {Boolean}
	 */
	isPrimitive : function(v){
		return JS.isString(v) || JS.isNumber(v) || JS.isBoolean(v);
	},

	/**
	 * Returns true if the passed value is a JavaScript Function, otherwise false.
	 * @param {Mixed} value The value to test
	 * @return {Boolean}
	 */
	isFunction : function(v){
		return Object.prototype.toString.apply(v) === '[object Function]';
	},

	/**
	 * Returns true if the passed value is a number. Returns false for non-finite numbers.
	 * @param {Mixed} value The value to test
	 * @return {Boolean}
	 */
	isNumber : function(v){
		return typeof v === 'number' && isFinite(v);
	},

	/**
	 * Returns true if the passed value is a string.
	 * @param {Mixed} value The value to test
	 * @return {Boolean}
	 */
	isString : function(v){
		return typeof v === 'string';
	},

	/**
	 * Returns true if the passed value is a boolean.
	 * @param {Mixed} value The value to test
	 * @return {Boolean}
	 */
	isBoolean : function(v){
		return typeof v === 'boolean';
	},

	/**
	 * Returns true if the passed value is an HTMLElement
	 * @param {Mixed} value The value to test
	 * @return {Boolean}
	 */
	isElement : function(v) {
		return !!v && v.tagName;
	},

	/**
	 * Returns true if the passed value is not undefined.
	 * @param {Mixed} value The value to test
	 * @return {Boolean}
	 */
	isDefined : function(v){
		return typeof v !== 'undefined';
	},
	 /**
	 * 将任何可迭代(带有length属性)的对象转换成Array对象
	 * 不要用它转换字符串.IE doesn't support "abc"[0] which this implementation depends on.
	 * For strings, use this instead: "abc".match(/./g) => [a,b,c];
	 * @param {Iterable} the iterable object to be turned into a true Array.
	 * @return (Array) array
	 */
	 toArray : function(){
		 return JS.isIE ?
			 function(a, i, j, res){
				 res = [];
				 for(var x = 0, len = a.length; x < len; x++) {
					 res.push(a[x]);
				 }
				 return res.slice(i || 0, j || res.length);
			 } :
			 function(a, i, j){
				 return Array.prototype.slice.call(a, i || 0, j || a.length);
			 };
	 }()
};
JS.log = JS.Syntax.log;
JS.ns = JS.Syntax.nameSpace;
JS.apply = JS.Syntax.apply;
JS.override = JS.Syntax.override;
JS.extend = JS.Syntax.extend;
JS.callBack = JS.Syntax.callBack;
JS.isEmpty = JS.Syntax.isEmpty;
JS.isArray = JS.Syntax.isArray;
JS.isDate = JS.Syntax.isDate;
JS.isObject = JS.Syntax.isObject;
JS.isPrimitive = JS.Syntax.isPrimitive;
JS.isFunction = JS.Syntax.isFunction;
JS.isNumber = JS.Syntax.isNumber;
JS.isString = JS.Syntax.isString;
JS.isBoolean = JS.Syntax.isBoolean;
JS.isElement = JS.Syntax.isElement;
JS.isDefined = JS.Syntax.isDefined;
JS.toArray = JS.Syntax.toArray;

JS.DomEvent = {
	/**
	 * @method 
	 * @param {DOM} el
	 * @param {String} name
	 * @param {Function} fun
	 * @param {Object} scope
	 */
    on: function(el, name, fun, scope){
        if (el.addEventListener) {
        	//el.addEventListener(name, fun, false);
        	//TODO:若封闭成带有作用域的功能，如何删除事件，是否自己要记一下on的函数，然后在un下删除它
        	
			el.addEventListener(name, function(){
				JS.callBack(fun,scope,arguments);
			}, false);
			
		} else {
			//el.attachEvent('on' + name, fun);
			
			el.attachEvent('on' + name, function(){
				JS.callBack(fun,scope,arguments);
			});
			
		}
    },
    /**
	 * @method 
	 * @param {DOM} el
	 * @param {String} name
	 * @param {Function} fun
	 * @param {Object} scope
	 */
    un: function(el, name, fun,scope){
        if (el.removeEventListener){
            el.removeEventListener(name, fun, false);
        } else {
            el.detachEvent('on' + name, fun);
        }
    },
    /**
	 * @method 
	 * @param {DOMEvent} e
	 */
    stop: function(e){
		e.returnValue = false;
		if (e.preventDefault) {
			e.preventDefault();
		}
		JS.DomEvent.stopPropagation(e);
    },
    /**
	 * @method 
	 * @param {DOMEvent} e
	 */
    stopPropagation: function(e){
        e.cancelBubble = true;
		if (e.stopPropagation) {
			e.stopPropagation();
		}
    }
};
JS.on = JS.DomEvent.on;
JS.un = JS.DomEvent.un;
/**
 * <pre><code>
	var task = new JS.DelayedTask(function(){
	    alert(JS.getDom('myInputField').value.length);
	});
	Ext.get('myInputField').on('keypress', function(){
	    task.delay(500); 
	});
 * </code></pre> 
 */
JS.DelayedTask = function(fn, scope, args){
    var me = this,
    	id,    	
    	call = function(){
    		clearInterval(id);
	        id = null;
	        fn.apply(scope, args || []);
	    };
	    
    /**
     * Cancels any pending timeout and queues a new one
     * @param {Number} delay The milliseconds to delay
     * @param {Function} newFn (optional) Overrides function passed to constructor
     * @param {Object} newScope (optional) Overrides scope passed to constructor. Remember that if no scope
     * is specified, <code>this</code> will refer to the browser window.
     * @param {Array} newArgs (optional) Overrides args passed to constructor
     */
    me.delay = function(delay, newFn, newScope, newArgs){
        me.cancel();
        fn = newFn || fn;
        scope = newScope || scope;
        args = newArgs || args;
        id = setInterval(call, delay);
    };

    /**
     * Cancel the last queued timeout
     */
    me.cancel = function(){
        if(id){
            clearInterval(id);
            id = null;
        }
    };
};
/**
 * @class JS.Observable
 * 事件模型
 * @author jinghai.xiao@gmail.com
 */
JS.ns("JS.Observable");
JS.Observable = function(o){
	JS.apply(this,o || JS.toArray(arguments)[0]);
	if(this.events){
		this.addEvents(this.events);
	}
    if(this.listeners){
        this.on(this.listeners);
        delete this.listeners;
    }
};
JS.Observable.prototype = {
	/**
	 * 添加侦听
	 * @method 
	 * @param {String | Map<String|Function>} channelName 事件名称或多个事件名称和侦听函数的键值对
	 * @param {Function} fn 侦听函数
	 * @param {Object} scope 侦听函数作用域
	 */
	on : function(eventName, fn, scope, o){
		if(JS.isString(eventName)){
			this.addListener(eventName, fn, scope, o);
		}else if(JS.isObject(eventName)){
			this.addListeners(eventName,scope, o);
		}
	},
	/**
	 * 触发事件
	 * @method 
	 * @param {String} eventName 事件名称
	 * @param {[AnyType0~n...]} eventParam 事件参数，可以是0到N个
	 * @return {Boolean}
	 */
	fireEvent : function(){
		var arg = JS.toArray(arguments),
		    eventName = arg[0].toLowerCase(),
			e = this.events[eventName];
		if(e && !JS.isBoolean(e)){
			return e.fire.apply(e,arg.slice(1));
		}
	},
	/**
	 * 注册事件类型
	 * @method 
	 * @param {String} eventName 事件名称
	 */
	addEvent : function(eventName){
		if(!JS.isObject(this.events)){
			this.events = {};
		}
		if(this.events[eventName]){
			return;
		}
		if(JS.isString(eventName)){
			this.events[eventName.toLowerCase()] = true;
		}else if(eventName instanceof JS.Event){
			this.events[eventName.name.toLowerCase()] = eventName;
		}
	},
	/**
	 * 批量注册事件类型
	 * @method 
	 * @param {Array<String>} eventNames 事件名称
	 */
	addEvents : function(arr){
		if(JS.isArray(arr)){
			for(var i = 0,len = arr.length; i < len; i++){
				this.addEvent(arr[i]);
			}
		}
	},
	/**
	 * 注册事件侦听
	 * @method 
	 * @param {String} eventName 事件名称
	 * @param {Function} fn 侦听函数
	 * @param {Object} scope 侦听函数作用域
	 */
	addListener : function(eventName, fn, scope, o){//o配置项尚未实现
		eventName = eventName.toLowerCase();
		this.addEvent(eventName);
		var e = this.events[eventName];
		if(e){
			if(JS.isBoolean(e)){
				e = this.events[eventName] = new JS.Event(eventName,this);
			}
			e.addListener(fn, scope , o);
		}
	},
	/**
	 * 批量注册事件侦听
	 * @method 
	 * @param {Map<String,Function>} eventMap 事件名称与侦听函数的键值对
	 * @param {Function} fn 侦听函数
	 * @param {Object} scope 侦听函数作用域
	 */
	addListeners : function(obj,scope, o){
		if(JS.isObject(obj)){
			for(var p in obj){
				this.addListener(p,obj[p],scope, o);
			}
		}
	},
	/**
	 * 移除事件侦听
	 * @method 
	 * @param {String} eventName 事件名称
	 * @param {Function} fn 侦听函数
	 * @param {Object} scope 侦听函数作用域
	 */
	removeListener : function(eventName, fn, scope){
		eventName = eventName.toLowerCase();
		var e = this.events[eventName];
		if(e && !JS.isBoolean(e)){
			e.removeListener(fn, scope);
		}
	},
	/**
	 * 移除所有事件侦听
	 * @method 
	 */
	clearListeners : function(){
		var events = this.events,
			e;
		for(var p in events){
			e = events[p];
			if(!JS.isBoolean(e)){
				e.clearListeners();
			}
		}
	},
	/**
	 * 移除所有事件类型及事件侦听
	 * @method 
	 */
	clearEvents : function(){
		var events = this.events;
		this.clearListeners();
		for(var p in events){
			this.removeEvent(p);
		}
	},
	/**
	 * 移除事件类型
	 * @method 
	 * @param {String} eventName 事件类型名称
	 */
	removeEvent : function(eventName){
		var events = this.events,
			e;
		if(events[eventName]){
			e = events[eventName];
			if(!JS.isBoolean(e)){
				e.clearListeners();
			}
			delete events[eventName];
		}
		
	},
	/**
	 * 批量移除事件类型
	 * @method 
	 * @param {Array<String>} 事件类型名称列表
	 */
	removeEvents : function(eventName){
		if(JS.isString(eventName)){
			this.removeEvent(eventName);
		}else if(JS.isArray(eventName) && eventName.length > 0){
			for(var i=0, len=eventName.length; i<len ;i++){
				this.removeEvent(eventName[i]);
			}
		}
	},
	/**
	 * 检测是否具有指定的事件类型
	 * @method 
	 * @param {String} 事件类型名称
	 */
	hasEvent : function(eventName){
		return this.events[eventName.toLowerCase()]?true:false;
	},
	/**
	 * 检测是否具有指定的事件侦听
	 * @method 
	 * @param {String} 事件类型名称
	 * @param {Function} fn 侦听函数
	 * @param {Object} scope 侦听函数作用域
	 */
	hasListener : function(eventName,fn,scope){
		var events = this.events,
			e = events[eventName];
		if(!JS.isBoolean(e)){
			return e.hasListener(fn,scope);
		}
		return false;
	},
	suspendEvents : function(){
		//TODO:
	},
	resumeEvents : function(){
		//TODO:
	}
};
/**
 * 事件源，代表一类事件，负责管理事件侦听
 * @class JS.Event
 * @author jinghai.xiao@gmail.com
 */
JS.Event = function(name,caller){
	this.name = name.toLowerCase();
	this.caller = caller;
	this.listeners = [];
};
JS.Event.prototype = {
	/**
	 * @method
	 * @return {Boolean}
	 */
	fire : function(){
		var 
			listeners = this.listeners,
			//len = listeners.length,
			i = listeners.length-1;
		for(; i > -1; i--){//TODO:fix 倒序
			if(listeners[i].execute.apply(listeners[i],arguments) === false){
				return false;
			}
		}
		return true;
	},
	/**
	 * @method
	 * @param {Function} fn
	 * @param {Object} scope
	 */
	addListener : function(fn, scope,o){
        scope = scope || this.caller;
        if(this.hasListener(fn, scope) == -1){
            this.listeners.push(new JS.Listener(fn, scope ,o));
        }
    },
    /**
     * 
     * @method
     * @param {Function} fn
     * @param {Object} scope
     */
	removeListener : function(fn, scope){
        var index = this.hasListener(fn, scope);
		if(index!=-1){
			this.listeners.splice(index, 1);
		}
    },
    /**
     * 
     * @method
     * @param {Function} fn
     * @param {Object} scope
     */
	hasListener : function(fn, scope){
		var i = 0,
			listeners = this.listeners,
			len = listeners.length;
		for(; i<len; i++){
			if(listeners[i].equal(fn, scope)){
				return i;
			}
		}
		return -1;
	},
	 /**
     * 
     * @method
     */
	clearListeners : function(){
		var i = 0,
			listeners = this.listeners,
			len = listeners.length;
		for(; i<len; i++){
			listeners[i].clear();
		}
		this.listeners.splice(0);
	}

};
/**
 * 事件侦听器，包装并统一侦听的调用方式
 * @class JS.Listener
 * @author jinghai.xiao@gmail.com
 */
JS.Listener = function(fn, scope,o){
	this.handler = fn;
	this.scope = scope;
	this.o = o;//配置项，delay,buffer,once,
};
JS.Listener.prototype = {
	/**
     * 
     * @method
     * @return {Boolean}
     */
	execute : function(){
		return JS.callBack(this.handler,this.scope,arguments);
	},
	/**
     * 
     * @method
     * @return {Boolean}
     */
	equal : function(fn, scope){
		return this.handler === fn /*&& this.scope === scope*/ ? true : false;
	},
	/**
     * 
     * @method
     */
	clear : function(){
		delete this.handler;
		delete this.scope ;
		delete this.o ;
	}
};JS.ns("JS.HTTPStatus","JS.XMLHttpRequest");
/**
 * FC 2616 HTTP1.1规范的HTTP Status状态常量,详见
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10
 * @author jinghai.xiao@gmail.com
 */
JS.HTTPStatus = {
	//Informational 1xx
	'100' : 'Continue',
	'101' : 'Switching Protocols',
	//Successful 2xx
	'200' : 'OK',
	'201' : 'Created',
	'202' : 'Accepted',
	'203' : 'Non-Authoritative Information',
	'204' : 'No Content',
	'205' : 'Reset Content',
	'206' : 'Partial Content',
	//Redirection 3xx
	'300' : 'Multiple Choices',
	'301' : 'Moved Permanently',
	'302' : 'Found',
	'303' : 'See Other',
	'304' : 'Not Modified',
	'305' : 'Use Proxy',
	'306' : 'Unused',
	'307' : 'Temporary Redirect',
	//Client Error 4xx
	'400' : 'Bad Request',
	'401' : 'Unauthorized',
	'402' : 'Payment Required',
	'403' : 'Forbidden',
	'404' : 'Not Found',
	'405' : 'Method Not Allowed',
	'406' : 'Not Acceptable',
	'407' : 'Proxy Authentication Required',
	'408' : 'Request Timeout',
	'409' : 'Conflict',
	'410' : 'Gone',
	'411' : 'Length Required',
	'412' : 'Precondition Failed',
	'413' : 'Request Entity Too Large',
	'414' : 'Request-URI Too Long',
	'415' : 'Unsupported Media Type',
	'416' : 'Requested Range Not Satisfiable',
	'417' : 'Expectation Failed',
	//Server Error 5xx
	'500' : 'Internal Server Error',
	'501' : 'Not Implemented',
	'502' : 'Bad Gateway',
	'503' : 'Service Unavailable',
	'504' : 'Gateway Timeout',
	'505' : 'HTTP Version Not Supported'
};
JS.HTTPStatus.OK = 200;
JS.HTTPStatus.BADREQUEST = 400;
JS.HTTPStatus.FORBIDDEN = 403;
JS.HTTPStatus.NOTFOUND = 404;
JS.HTTPStatus.TIMEOUT = 408;
JS.HTTPStatus.SERVERERROR = 500;

/**
 * @class JS.XMLHttpRequest
 * @extends JS.Observable
 * 跨浏览器、事件驱动的XMLHTTPRequest对象，此对象完全兼容传统XMLHTTPRequest对象，在遵循
 * http://www.w3.org/TR/XMLHttpRequest/标准的前提下有所扩展。
 * @author jinghai.xiao@gmail.com
 */
JS.XMLHttpRequest = JS.extend(JS.Observable,{
	/**
	 * @cfg {Boolean} enableCache 
	 * 是否启用缓存，默认为false
	 */
	enableCache : false,
	/**
	 * @cfg {Number} timeout 
	 * 请求超时毫秒数，默认为30000(30秒)，设置为0则永不超时
	 */
	timeout : 30000,//default never time out
	/** 
	 * 是否调用了abort方法
	 * @property 
	 * @type Boolean
	 */ 
	isAbort : false,
	/**
	 * @cfg {String} specialXHR 
	 * 指定一个特定的ActiveX对象名称用于取代XMLHTTPRequest对象，默认为空。
	 */
	specialXHR : '',//指定使用特殊的xhr对象
	//propoty
	_xhr : null,
	//--------request propoty--------
	/** 
	 * @property 
	 * @type Number
	 */ 
	readyState : 0,
	//--------response propoty--------
	/** 
	 * @property 
	 * @type Number
	 */ 
	status : 0,
	/** 
	 * @property 
	 * @type String
	 */ 
	statusText : '',
	/** 
	 * @property 
	 * @type String
	 */
	responseText : '',
	/** 
	 * @property 
	 * @type DOM
	 */
	responseXML : null,
	//private method
	constructor : function(){
		var self = this;
		this.addEvents([
			/**
			 * @event readyStateChange 当readyState发生变化
			 * @param {Number} readyState 
			 * @param {Number} status  
			 * @param {JS.XMLHttpRequest} xhr 
			 * @param {XMLHTTPRequest} realXhr 实际使用的XMLHTTPRequest对象
			 */
			'readyStateChange',
			/**
			 * @event timeout 请求超时
			 * @param {JS.XMLHttpRequest} xhr 
			 * @param {XMLHTTPRequest} realXhr 实际使用的XMLHTTPRequest对象
			 */
			'timeout',
			/**
			 * @event abort 主动取消
			 * @param {JS.XMLHttpRequest} xhr 
			 * @param {XMLHTTPRequest} realXhr 实际使用的XMLHTTPRequest对象
			 */
			'abort',
			/**
			 * @event error 请求出错
			 * @param {JS.XMLHttpRequest} xhr 
			 * @param {XMLHTTPRequest} realXhr 实际使用的XMLHTTPRequest对象
			 */
			'error',
			/**
			 * @event load 接收完毕
			 * @param {JS.XMLHttpRequest} xhr 
			 * @param {XMLHTTPRequest} realXhr 实际使用的XMLHTTPRequest对象
			 */
			'load',
			/**
			 * @event progress 正在接收 
			 * @param {JS.XMLHttpRequest} xhr 
			 * @param {XMLHTTPRequest} realXhr 实际使用的XMLHTTPRequest对象
			 */
			'progress'
		]);
		JS.XMLHttpRequest.superclass.constructor.apply(this,arguments);
		this._xhr = this.createXmlHttpRequestObject();
		this._xhr.onreadystatechange = function(){
			self.doReadyStateChange();
		};
	},
	//private
	//超时任务
	timeoutTask : null,
	//延迟执行超时任务(timeoutTask)
	delayTimeout : function(){
		if(this.timeout){
			if(!this.timeoutTask){
				this.timeoutTask = new JS.DelayedTask(function(){
					//readyState=4已经停止，由doReadyStateChange来判断为何停止
					if(this._xhr.readyState != 4){
						this.fireEvent('timeout', this, this._xhr);
					}else{
						this.cancelTimeout();
					}
				},this);
			}
			this.timeoutTask.delay(this.timeout);
		}
	},
	//取消超时任务
	cancelTimeout : function(){
		if(this.timeoutTask){
			this.timeoutTask.cancel();
		}
	},
	createXmlHttpRequestObject : function(){
		var activeX = [
			'Msxml2.XMLHTTP.6.0', 
			'Msxml2.XMLHTTP.5.0', 
			'Msxml2.XMLHTTP.4.0', 
			'Msxml2.XMLHTTP.3.0', 
			'Msxml2.XMLHTTP', 
			'Microsoft.XMLHTTP'],
		xhr,
		specialXHR = this.specialXHR;
		if(specialXHR){//如果指定了xhr对象
			if(JS.isString(specialXHR)){
				return new ActiveXObject(specialXHR);
			}else{
				return specialXHR;
			}
		}
		try {
			xhr = new XMLHttpRequest();                
		} catch(e) {
			for (var i = 0; i < activeX.length; ++i) {	            
				try {
					xhr = new ActiveXObject(activeX[i]);                        
					break;
				} catch(e) {}
			}
		} finally {
			return xhr;
		}
	},
	//private
	doReadyStateChange : function(){
		this.delayTimeout();
		var xhr = this._xhr;
		try{
			this.readyState = xhr.readyState;
		}catch(e){
			this.readyState = 0;
		}
		try{
			this.status = xhr.status;
		}catch(e){
			this.status = 0;
		}
		try{
			this.statusText = xhr.statusText;
		}catch(e){
			this.statusText = "";
		}
		try {
			this.responseText = xhr.responseText;
		}catch(e){
			this.responseText = "";
		}
		try {
			this.responseXML = xhr.responseXML;
		}catch(e){
			this.responseXML = null;
		}
		
		this.fireEvent('readyStateChange',this.readyState, this.status, this, xhr );
		
		if(this.readyState == 3 && (this.status >= 200 && this.status < 300)){
			this.fireEvent('progress', this, xhr);
		}
		
		if(this.readyState == 4){
			this.cancelTimeout();
			var status = this.status ;
			if(status == 0 || status == ""){
				this.fireEvent('error', this, xhr);
			}else if(status >= 200 && status < 300){
				this.fireEvent('load', this, xhr);
			}else if(status >= 400 && status != 408){
				this.fireEvent('error', this, xhr);
			}else if(status == 408){
				this.fireEvent('timeout', this, xhr);
			}
			
		}
		this.onreadystatechange();
	},
	/**
	 * 兼容标准的onreadystatechange
	 * @method 
	 */
	onreadystatechange : function(){
	},
	//--------request--------
	/**
	 * 兼容标准的open方法
	 * @method 
	 */
	open : function(method, url, async, username, password){
		if(!url){
			return;
		}
		if(!this.enableCache){
			if(url.indexOf('?') != -1){
				url += '&ram='+Math.random();
			}else{
				url += '?ram='+Math.random();
			}
		}
		this._xhr.open(method, url, async, username, password);
	},
	/**
	 * 兼容标准的send方法
	 * @method 
	 */
	send : function(content){
		this.delayTimeout();
		this.isAbort = false;
		this._xhr.send(content);
	},
	/**
	 * 兼容标准的abort方法
	 * @method 
	 */
	abort : function(){
		this.isAbort = true;
		this.cancelTimeout();
		this._xhr.abort();
		if(JS.isIE){//IE在abort后会清空侦听
			var self = this;
			self._xhr.onreadystatechange = function(){
				self.doReadyStateChange();
			};
		}
		this.fireEvent('abort',this,this._xhr);
	},
	/**
	 * 兼容标准的setRequestHeader方法
	 * @method 
	 */
	setRequestHeader : function(header, value){
		this._xhr.setRequestHeader(header,value);
	},
	//--------request--------
	/**
	 * 兼容标准的getResponseHeader方法
	 * @method 
	 */
	getResponseHeader : function(header){
		return this._xhr.getResponseHeader(header);
	},
	/**
	 * 兼容标准的getAllResponseHeaders方法
	 * @method 
	 */
	getAllResponseHeaders : function(){
		return this._xhr.getAllResponseHeaders();
	},
	/**
	 * 设置客户端超时时间
	 * @method 
	 */
	setTimeout : function(t){
		this.timeout = t;
	}

});/**
 * @class JS.AJAX
 * AJAX常用方法封装
 * @singleton 
 * @author jinghai.xiao@gmail.com
 */
JS.ns("JS.AJAX");

JS.AJAX = (function(){
	var xhr = new JS.XMLHttpRequest();
	return {
		dataFormatError : '服务器返回的数据格式有误',
		urlError : '未指定url',
		/**
		 * 以POST方式向服务器发送请求，并得到服务返回的xhr对象。<br>
		 * <pre>如：JS.Ajax.post('/someurl.do','keyword=xxx',function(xhr){
			alert(xhr.responseText);
		   });</pre>
		 * @method 
		 * @param {String} url 网址
		 * @param {String|DOM} param 参数
		 * @param {Function} callback 回调函数 function(xhr){alert(xhr.responseText)}
		 * @param {Object} scope 作用域
		 * @param {Boolean} asyn 是否异步调用，默认true
		 */
		post : function(url,param,callback,scope,asyn){
			if(typeof url!=='string'){
				throw new Error(this.urlError);
			}
			//默认为异步请求
			var asynchronous = true;
			if(asyn===false){
				asynchronous = false;
			}
			xhr.onreadystatechange = function(){
				if(xhr.readyState==4 && asynchronous){
					JS.callBack(callback,scope,[xhr]);
				}
			};
			xhr.open('POST', url, asynchronous);
			xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF8");
			xhr.send(param || null);
			if(!asynchronous){
				JS.callBack(callback,scope,[xhr]);
			}
			
		},
		/**
		 * 以GET方式向服务器发送请求，并得到服务返回的xhr对象。<br>
		 * <pre>如：JS.Ajax.get('/someurl.do','keyword=xxx',function(xhr){
			alert(xhr.responseText);
		   });</pre>
		 * @method 
		 * @param {String} url 网址
		 * @param {String|DOM} param 参数
		 * @param {Function} callback 回调函数 function(xhr){alert(xhr.responseText)}
		 * @param {Object} scope 作用域
		 * @param {Boolean} asyn 是否异步调用，默认true
		 */
		get : function(url,param,callback,scope,asyn){
			if(typeof url!=='string'){
				throw new Error(this.urlError);
			}
			//默认为异步请求
			var asynchronous = true;
			if(asyn===false){
				asynchronous = false;
			}
			xhr.onreadystatechange = function(){
				if(xhr.readyState==4 && asynchronous){
					JS.callBack(callback,scope,[xhr]);
				}
			};
			xhr.open('GET', url, asynchronous);
			xhr.setRequestHeader("Content-Type","html/text;charset=UTF8");
			xhr.send(param || null);
			if(!asynchronous){
				JS.callBack(callback,scope,[xhr]);
			}
			
		},
		/**
		 * 以GET方式向服务器发送请求，并得到服务返回的文本信息。<br>
		 * <pre>如：JS.Ajax.getText('/someurl.do','keyword=xxx',function(text){
			alert(text);
		   });</pre>
		 * @method 
		 * @param {String} url 网址
		 * @param {String|DOM} param 参数
		 * @param {Function} callback 回调函数 function(text){alert(text)}
		 * @param {Object} asyn 作用域
		 * @param {Boolean} asyn 是否异步调用，默认true
		 */
		getText : function(url,jsonData,callback,scope,asyn){
			this.get(url,jsonData,function(xhr){
				if(scope){
					callback.call(scope,xhr.responseText);
				}else{
					callback(xhr.responseText);
				}
			},this,asyn);
		},
		/**
		 * 以GET方式向服务器发送请求，并得到服务返回的JSON对象。<br>
		 * <pre>如：JS.Ajax.getJson('/someurl.do','keyword=xxx',function(obj){
			alert(obj.someField);
		   });</pre>
		 * @method 
		 * @param {String} url 网址
		 * @param {String|DOM} param 参数
		 * @param {Function} callback 回调函数 function(obj){alert(alert(obj.someField);)}
		 * @param {Object} scope 作用域
		 * @param {Boolean} asyn 是否异步调用，默认true
		 */
		getJson : function(url,jsonData,callback,scope,asyn){
			this.get(url,jsonData,function(xhr){
				var json = null;
				try{
					json = eval("("+xhr.responseText+")");
				}catch(e){
					throw new Error(this.dataFormatError);
				}
				JS.callBack(callback,scope,[json]);
			},this,asyn);
		}
	};
})();/**
 * @class JS.Connector
 * @extends JS.Observable
 * 连接器,负责建立并维持连接，接收服务器端推送的数据。
 * @author jinghai.xiao@gmail.com
 */
JS.ns("JS.Connector");
JS.Connector = JS.extend(JS.Observable,{
   /** 
    * 版本
    * @property 
    * @type String
    */ 
	version : JS.version,
	SYSCHANNEL:'c4j', //协议常量
	/** 
	 * 长轮询工作模式常量
	 * @property 
	 * @type String
	 */ 
	LLOOPSTYLE : 'lpool',//协议常量
	/** 
	 * 长连接工作模式常量
	 * @property 
	 * @type String
	 */ 
	STREAMSTYLE : 'stream',//协议常量
	CMDTAG : 'cmd',
	/**
	 * @cfg {String} retryCount 
	 * 重试次数，连续重试指定次数仍无法正常工作则stop.
	 */
	retryCount : 3,
	/**
	 * @cfg {String} retryCount 
	 * 重试延迟，重试时间隔指定时间执行
	 */
	retryDelay : 1000,
	currRetry : 0,
	/**
	 * @cfg {String} url 
	 * 连接地址，若使用start方法中带有url，则此配置项将被覆盖。
	 */
	url : '',
	/**
	 * @cfg {Object} param 
	 * 连接参数，若使用start方法中带有param，则此配置项将被覆盖。
	 */
	param : '',
	/**
	 * @cfg {Object} revivalDelay
	 * 复活请求延时毫秒数,默认为100
	 */
	revivalDelay : 100,
    /** 
	 * 连接ID，连接后有效
	 * @property 
	 * @type String
	 */ 
	cId : '',
	/** 
	 * 通道列表，连接后有效
	 * @property 
	 * @type Array
	 */ 
	channels : [],
	/** 
	 * 连接工作模式，连接后有效。值为：LLOOPSTYLE或STREAMSTYLE
	 * @property 
	 * @type String
	 */ 
	workStyle : '',
	emptyUrlError : 'URL为空',
	runningError : '连接正在运行',
	dataFormatError : '数据格式有误',
	/** 
	 * 连接器是否处于运行状态
	 * @property 
	 * @type String
	 */ 
	running : false,
	_xhr : null,
	lastReceiveMessage : '',
	constructor:function(){
		JS.Connector.superclass.constructor.apply(this,arguments);
		this.addEvents([
			/**
			 * @event beforeConnect 即将连接
			 * @param {String} url 连接地址
			 * @param {Connector} conn 连接器
			 */
			'beforeConnect',
			/**
			 * @event connect 已连接
			 * @param {String} cId 连接ID
			 * @param {Array} channels 通道列表
			 * @param {String} workStyle 工作模式
			 * @param {Number} timeout 连接超时时间
			 * @param {Connector} conn 连接器
			 */
			'connect',
			/**
			 * @event beforeStop 即将停止
			 * @param {String} cause 停止原因
			 * @param {String} cId 连接ID
			 * @param {String} url 连接地址
			 * @param {Connector} conn 连接器
			 */
			'beforeStop',
			/**
			 * @event stop 已停止
			 * @param {String} cause 停止原因
			 * @param {String} cId 连接ID
			 * @param {String} url 连接地址
			 * @param {Connector} conn 连接器
			 */
			'stop',
			/**
			 * @event message 接收到推送数据
			 * @param {String} channel 通道标识
			 * @param {Object} data 数据对象
			 * @param {Number} time 发送时间，距1970-01-01 00:00:00毫秒数
			 * @param {Connector} conn 连接器
			 */
			'message',
			/**
			 * @event revival 请求复活，用于保持连接。
			 * @param {String} url 连接地址
			 * @param {String} cId 连接ID
			 * @param {Connector} conn 连接器
			 */
			'revival'
		]);
		if(JS.isIE7){
			this._xhr = new JS.XMLHttpRequest({
				specialXHR : 'Msxml2.XMLHTTP.6.0'
			});
		}else{
			this._xhr = new JS.XMLHttpRequest();
		}
		//this._xhr.addListener('readyStateChange',this.onReadyStateChange,this);
		
		this._xhr.addListener('progress',this.doOnProgress,this);
		this._xhr.addListener('load',this.doOnLoad,this);
		this._xhr.addListener('error',this.doOnError,this);
		this._xhr.addListener('timeout',this.doOnTimeout,this);
		
		this.addListener('beforeStop',this.doDrop,this);
		JS.on(window,'beforeunload',this.doDrop,this);

	},
	//private
	doDrop : function(url,cId,conn,xhr){
		if(!this.running || !this.cId){
			return;
		}
		try {
			var xhr = new JS.XMLHttpRequest();
			var param = this.perfectParam(this.param);
			var url = this.url + '?'+this.CMDTAG+'=drop&cid=' + this.cId + param;
			xhr.open('GET', url, true);
			xhr.send(null);
			xhr = null;
		}catch(e){};
	},
	//private distributed 派发服务器消息
	dispatchServerEvent : function(msg){
		this.fireEvent('message', msg.channel, msg.data, msg.time, this);
	},
	//private 长连接信息转换
	translateStreamData : function(responseText){
		var str = responseText;
		if(this.lastReceiveMessage && str){//剥离出接收到的数据
			str = str.split(this.lastReceiveMessage);
			str = str.length ? str[str.length-1] : "";
		}
		this.lastReceiveMessage = responseText;
		return str;
	},
	//private 消息解码
	decodeMessage : function(msg){
		var json = null;
		if(JS.isString(msg) && msg!=""){
			//解析数据格式
			if(msg.charAt(0)=="<" ){
				msg = msg.substring(1,msg.length);
			}
			if(msg.charAt(msg.length-1)==">"){
				msg = msg.substring(0,msg.length-1);
			}
			msg = decodeURIComponent(msg);
			try{
				json = eval("("+msg+")");
			}catch(e){
				this.stop('JSON转换异常');
			}			
		}
		return json;
	},
	//private
	doOnProgress : function(xhr){
		if(this.workStyle === this.STREAMSTYLE){				
			var str = this.translateStreamData(xhr.responseText);
			var msglist = str.split(">");
			if(msglist.length > 0){
				for(var i=0, len=msglist.length; i<len; i++){
					if(!msglist[i] && i!=0){
						return;
					}
					var json = this.decodeMessage(msglist[i]);
					if(json){
						this.currRetry = 0;
						this.dispatchServerEvent(json);
						//TODO 修复Firefox调用自定义方法后，无法重连问题
						if(json.channel == this.SYSCHANNEL || JS.isFirefox){
							this.revivalConnect();
						}
					}else{//非正常情况，状态为3,200并且还没有收到任何数据
						this.currRetry++;
						if(this.currRetry > this.retryCount){
							this.stop('服务器异常');
						}else{
							this.retryRevivalConnect();
						}
					}
				}
			}
		}
	},
	//private
	doOnLoad : function(xhr){
		if(this.workStyle === this.LLOOPSTYLE){
			var json = this.decodeMessage(xhr.responseText);
			if(json){
				this.currRetry = 0;
				this.dispatchServerEvent(json);
				this.revivalConnect();
			}else{//非正常情况，状态为4,200并且还没有收到任何数据
				this.currRetry++;
				if(this.currRetry > this.retryCount){
					this.stop('服务器异常');
				}else{
					this.retryRevivalConnect();
				}
			}
		}
	},
	//private
	doOnError : function(xhr){
		this.currRetry++;
		if(this.currRetry > this.retryCount){
			this.stop('服务器异常');
		}else{
			this.retryRevivalConnect();
		}
		
	},
	//private
	doOnTimeout : function(xhr){
		this.currRetry++;
		if(this.currRetry > this.retryCount){
			this.stop('请求超时');
		}else{
			this.retryRevivalConnect();
		}
	},
	//private
	startConnect : function(url){
		if(this.running){
			var connXhr = new JS.XMLHttpRequest();
			connXhr.addListener('error',function(xhr){
				this.stop("连接时发生错误");
			},this);
			connXhr.addListener('timeout',function(xhr){
				this.stop("连接超时");
			},this);
			connXhr.addListener('load',function(xhr){
				var msg = this.decodeMessage(xhr.responseText);
				if(!msg){
					this.stop('连接失败');
					return;
				}
				var data = msg.data;
				this.cId = data.cId;
				this.channels = data.channels;
				this.workStyle = data.ws;
				this._xhr.setTimeout(data.timeout + 1000/*网络延迟误差*/);
				this.fireEvent('connect', data.cId, data.channels, data.ws, data.timeout, this);
				this.revivalConnect();
			},this);
			connXhr.open('GET', url, true);
			connXhr.send(null);
			/*
			JS.AJAX.get(url,'',function(xhr){
				var msg = this.decodeMessage(xhr.responseText);
				if(!msg){
					this.stop('连接失败');
					return;
				}
				var data = msg.data;
				this.cId = data.cId;
				this.channels = data.channels;
				this.workStyle = data.ws;
				this._xhr.setTimeout(data.timeout + 1000);
				this.fireEvent('connect', data.cId, data.channels, data.ws, data.timeout, this);
				this.revivalConnect();
			},this);*/
		}
	},

	//private 重试复活连接
	retryRevivalConnect : function(){
		var self = this;
		if(this.running){
			setTimeout(function(){
				self.revivalConnect();
			},this.retryDelay);
		}
	},
	
	//private
	revivalConnect : function(){
		var self = this;
		if(this.running){
			setTimeout(revival,this.revivalDelay);
		}
		function revival(){
			var xhr = self._xhr;
			var param = self.perfectParam(self.param);
			var url = self.url + '?'+self.CMDTAG+'=revival&cid=' + self.cId + param;
			xhr.open('GET', url, true);
			xhr.send(null);
			self.fireEvent('revival',self.url, self.cId, self);
		}
		
	},
	/**
	 * 开始连接
	 * @method 
	 * @param {String} url 连接地址
	 * @param {String|DOM} param 连接参数
	 */
	start : function(url,param){
		if(this.running){
			return;
		}
		
		this.url = url || this.url;
		if(!this.url){
			throw new Error(this.emptyUrlError);
		}
		
		if(this.fireEvent('beforeConnect', this.url, this) === false){
			return;
		}
		
		this.param = param || this.param;
		param = this.perfectParam(this.param);
		var url = this.url+'?'+this.CMDTAG+'=conn&cv='+this.version+param;
		
		this.running = true;
		this.currRetry = 0;
		var self = this;
		setTimeout(function(){
			self.startConnect(url);
		},1000);
	},
	// 完善参数
	perfectParam : function(param){
		if(param && JS.isString(param)){
			if(param.charAt(0) != '&'){
				param = '&'+param;
			}
		}
		return param;
	},
	/**
	 * 停止连接
	 * @method 
	 * @param {String} cause 停止原因
	 */
	stop : function(cause){
		if(!this.running){
			return;
		}
		if(this.fireEvent('beforeStop',cause,this.cId, this.url,  this) === false){
			return;
		}
		this.running = false;
		var cId = this.cId;
		this.cId = '';
		this.channels = [];
		this.workStyle = '';
		try{
			this._xhr.abort();
		}catch(e){};
		this.fireEvent('stop',cause, cId, this.url, this);
	},
	/**
	 * 获得连接ID
	 * @method 
	 * @return {String} 连接ID
	 */
	getId : function(){
		return this.cId;
	}
});/**
 * 消息引擎,
 * 负责将服务器推送的通道数据，以事件的方式触发，事件名称与通道名称相同。
 * @class JS.Engine
 * @singleton 
 * @extends JS.Observable
 * @author jinghai.xiao@gmail.com
 */
JS.ns("JS.Engine");
JS.Engine = (function(){
	var Engine = JS.extend(JS.Observable,{
		lStore : [],//用于存放没启动状态下用户增加的侦听
		/** 
		 * 引擎是否处于工作状态
		 * @property 
		 * @type Boolean
		 */ 
		running : false,
		/** 
		 * 引擎所使用的连接器
		 * @property 
		 * @type JS.Connector
		 */ 
		connector : null,
		constructor:function(){
			this.addEvents([
				/**
				 * @event start 开始事件
				 * @param {String} cId 连接ID
				 * @param {Array<String>} channels 通道列表
				 * @param {JS.Engine} engine 事件引擎
				 */   
				'start',
				/*** 
				 * @event stop 停止事件
				 * @param {String} cause 停止原因
				 * @param {String} cId 连接ID
				 * @param {String} url 连接地址
				 * @param {JS.Engine} engine 事件引擎
				 */
				'stop'
			]);
			Engine.superclass.constructor.apply(this,arguments);
			this.connector = new JS.Connector();
			this.initEvent();
		},
		/**
		 * 注册通道侦听
		 * @method on
		 * @param {String | Map<String,Function>} channelName 通道名称或多个通道名称和侦听函数的键值对
		 * @param {Function} fn 侦听函数
		 * @param {Object} scope 侦听函数作用域
		 */
		//重载addListener函数
		addListener : function(eventName, fn, scope, o){
			if(this.running){
				Engine.superclass.addListener.apply(this,arguments);
			}else{
				this.lStore.push({
					eventName : eventName,
					fn : fn,
					scope : scope,
					o : o
				});
			}
		},
		//private 
		initEvent : function(){
			var self = this;
			this.connector.on({
				connect : function(cId, aml, conn){
					//self.running = true;
					self.addEvents(aml);
					for(var i=0,len=self.lStore.length; i<len; i++){
						var e = self.lStore[i];
						self.addListener(e.eventName,e.fn,e.scope);
					}
					self.fireEvent('start', cId, aml, self);
				},
				stop : function(cause, cId, url, conn){
					self.running = false;
					self.fireEvent('stop',cause, cId,url, self);
					self.clearListeners();
				},
				message : function(amk, data, time){
					self.fireEvent(amk, data, time, self);
				}
			});
		},
		/**
		 * 开始连接
		 * @method 
		 * @param {String} url 连接地址
		 * @param {String} param 连接参数
		 */
		start : function(url,param){
			this.running = true;
			/*
			if(this.running){
				return;
			}*/
			for(var i=0,len=this.lStore.length; i<len; i++){
				var e = this.lStore[i];
				this.addListener(e.eventName,e.fn,e.scope);
			}
			this.connector.start(url,param);
		},
		/**
		 * 停止连接
		 * @method 
		 * @param {String} cause 停止原因
		 */
		stop : function(cause){
			if(!this.running){
				return;
			}
			this.connector.stop(cause);
		},
		/**
		 * 获得连接器实例
		 * @method 
		 * @return {JS.Connector} connector 连接器实例
		 */
		getConnector : function(){
			return this.connector;
		},
		/**
		 * 获得连接ID
		 * @method 
		 * @return {String} 连接ID
		 */
		getId : function(){
			return this.connector.cId;
		}
		
	});
	return new Engine();
}());