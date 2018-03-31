(function($){
	"use strict";
	var rp_ge = {};
	$.jgrid.extend({
		navGrid : function (elem, o, pEdit,pAdd,pDel,pSearch, pView) {
			var btnStyleClass="btn-default";
			o = $.extend(true,{
				currentUri:null,
				edit: true,
				editIcon: "glyphicon-pencil",
				edittext:"编辑",
				editTitle:null,
				editUri:null,
				editWidth:600,
				editBtnStyle: btnStyleClass,
				editSelectedType:'one-selected', //选择数据的方式：none-selected表示不用选择数据，one-selected表示只能选中一条数据,mutil-selected表示可以选中多条数据
				
				add: true,
				addIcon:"glyphicon-plus",
				addtext:"添加",
				addWidth:600,
				addTitle:null,
				addUri:null,
				addSelectedType:'none-selected',
				addBtnStyle: btnStyleClass,
				
				del: true,
				delIcon:"glyphicon-trash",
				deltext:"删除",
				delMsg:null,
				delCallback:null,
				delBtnStyle: btnStyleClass,
				
				search: false,
				searchIcon:"glyphicon-search",
				searchtext:"搜索",
				searchTitle:null,
				searchWidth:600,
				searchUri:null,
				
				refresh: true,
				refreshIcon:"glyphicon-refresh",
				refreshtext:"刷新",
				refreshstate: 'firstpage',
				refreshBtnStyle: btnStyleClass,
				
				view: false,
				viewIcon : "glyphicon-file",
				viewtext:"查看",
				viewWidth:600,
				viewTitle:null,
				viewUri:null,
				viewBtnStyle: btnStyleClass,
				
				position : "left",
				closeOnEscape : true,
				beforeRefresh : null,
				afterRefresh : null,
				cloneToTop : false,
				alertwidth : 200,
				width:600,
				busi:null,
				
				alertheight : 'auto',
				alerttop: null,
				alertleft: null,
				alertzIndex : null
			}, $.jgrid.nav, o ||{});
			return this.each(function() {
				if(this.nav) {return;}
				var alertIDs = {themodal: 'alertmod_' + this.p.id, modalhead: 'alerthd_' + this.p.id,modalcontent: 'alertcnt_' + this.p.id},
				$t = this, twd, tdw;
				if(!$t.grid || typeof elem !== 'string') {return;}
				if ($("#"+alertIDs.themodal)[0] === undefined) {
					if(!o.alerttop && !o.alertleft) {
						if (window.innerWidth !== undefined) {
							o.alertleft = window.innerWidth;
							o.alerttop = window.innerHeight;
						} else if (document.documentElement !== undefined && document.documentElement.clientWidth !== undefined && document.documentElement.clientWidth !== 0) {
							o.alertleft = document.documentElement.clientWidth;
							o.alerttop = document.documentElement.clientHeight;
						} else {
							o.alertleft=1024;
							o.alerttop=768;
						}
						o.alertleft = o.alertleft/2 - parseInt(o.alertwidth,10)/2;
						o.alerttop = o.alerttop/2-25;
					}
					$.jgrid.createModal(alertIDs,
						"<div>"+o.alerttext+"</div><span tabindex='0'><span tabindex='-1' id='jqg_alrt'></span></span>",
						{ 
							gbox:"#gbox_"+$.jgrid.jqID($t.p.id),
							jqModal:true,
							drag:true,
							resize:true,
							caption:o.alertcap,
							top:o.alerttop,
							left:o.alertleft,
							width:o.alertwidth,
							height: o.alertheight,
							closeOnEscape:o.closeOnEscape, 
							zIndex: o.alertzIndex
						},
						"#gview_"+$.jgrid.jqID($t.p.id),
						$("#gbox_"+$.jgrid.jqID($t.p.id))[0],
						true
					);
				}
				var clone = 1, i,
				onHoverIn = function () {
					if (!$(this).hasClass('ui-state-disabled')) {
						$(this).addClass("ui-state-hover");
					}
				},
				onHoverOut = function () {
					$(this).removeClass("ui-state-hover");
				};
				if(o.cloneToTop && $t.p.toppager) {clone = 2;}
				for(i = 0; i<clone; i++) {
					var tbd,
					navtbl = $("<div class='btn-group btn-group-sm'></div>"),
					pgid, elemids;
					if(i===0) {
						pgid = elem;
						elemids = $t.p.id;
						if(pgid === $t.p.toppager) {
							elemids += "_top";
							clone = 1;
						}
					} else {
						pgid = $t.p.toppager;
						elemids = $t.p.id+"_top";
					}
					if($t.p.direction === "rtl") {$(navtbl).attr("dir","rtl").css("float","right");}
					if (o.add) {
						if(cnoj.checkAuth(o.currentUri,'add')) {
							pAdd = pAdd || {};
							tbd = "<button type='button' data-selected-type='"+o.addSelectedType+"' data-title='"+o.addTitle+"' data-width='"+o.addWidth+"' data-busi='"+utils.handleNull(o.busi)+
							"' data-uri='"+utils.handleNull(o.addUri)+"' class='btn "+o.addBtnStyle+" add'><i class='glyphicon "+o.addIcon+"'> </i> "+
							(utils.isEmpty(o.addtext)?'添加':o.addtext)+"</button>";
							$(navtbl).append(tbd);
							$(".add",navtbl).attr({"title":o.addtitle || "",id : pAdd.id || "add_"+elemids}).click(function(){
								var id = $t.p.selrow;
								if(!utils.isEmpty(id)) {
									$(this).attr("selected-value",id);
								}
								openProp($(this),'add');
								return false;
							});
							tbd = null;
						}
					}
					if (o.edit) {
						if(cnoj.checkAuth(o.currentUri,'edit')) {
							pEdit = pEdit || {};
							tbd = "<button type='button' data-selected-type='"+o.editSelectedType+"' data-title='"+o.editTitle+"' data-width='"+o.editWidth+"' data-busi='"+utils.handleNull(o.busi)+
							"' class='btn "+o.editBtnStyle+" edit' data-uri='"+utils.handleNull(o.editUri)+"' ><i class='glyphicon "+o.editIcon+"'></i> "+
							(utils.isEmpty(o.edittext)?'编辑':o.edittext)+"</button>";
							$(navtbl).append(tbd);
							$(".edit",navtbl).attr({"title":o.edittitle || "",id: pEdit.id || "edit_"+elemids}).click(function(){
								var id = $t.p.selrow;
								if(!utils.isEmpty(id)) {
									$(this).attr("selected-value",id);
								}
								if(utils.isEmpty(id)) {
									BootstrapDialogUtil.warningAlert("请选择一条数据!");
									return false;
								}
								openProp($(this),'edit');
								return false;
							});
							tbd = null;
						}
					}
					if (o.view) {
						if(cnoj.checkAuth(o.currentUri,'view')) {
							pView = pView || {};
							tbd = "<button type='button' data-title='"+o.viewTitle+"' data-width='"+o.viewWidth+"' data-busi='"+utils.handleNull(o.busi)+"' class='btn "+o.viewBtnStyle+" view' data-uri='"+utils.handleNull(o.viewUri)+"' ><i class='glyphicon "+o.viewIcon+"'></i> "+(utils.isEmpty(o.viewtext)?'查看':o.viewtext)+"</button>";
							$(navtbl).append(tbd);
							$(".view",navtbl).attr({"title":o.viewTitle || "",id: pView.id || "view_"+elemids}).click(function(){
								var id = $t.p.selarrrow;
								if(!utils.isEmpty(id)) {
									$(this).attr("selected-value",id);
								}
								openProp($(this),null);
								return false;
							});
							tbd = null;
						}
					}
					if (o.del) {
						if(cnoj.checkAuth(o.currentUri,'del')) {
							pDel = pDel || {};
							tbd = "<button type='button' data-msg='"+o.delMsg+"' data-uri='"+utils.handleNull(o.delUri)+"' data-busi='"+utils.handleNull(o.busi)+"' class='btn "+o.delBtnStyle+" del'><i class='glyphicon "+o.delIcon+"'></i> "+(utils.isEmpty(o.deltext)?'删除':o.deltext)+"</button>";
							$(navtbl).append(tbd);
							$(".del",navtbl).attr({"title":o.deltitle || "",id: pDel.id || "del_"+elemids}).click(function(){
								var ids = null;
								if($t.p.multiselect) {
									ids = $t.p.selarrrow;
								} else {
									ids = $t.p.selrow;
								}
								if(utils.isEmpty(ids)) {
									BootstrapDialogUtil.warningAlert("请选择一条数据!");
								} else {
									var uri = $(this).data("uri");
									var busiName = $(this).data("busi");
									var refreshUri = $(this).data("refresh-uri");
									var target = $(this).data("target");
									var msg = o.delMsg;
									msg = utils.isEmpty(msg)?"您确定要删除选择的数据吗？":msg;
								    uri = utils.isEmpty("op/del.json");
									if(isContain(uri, "?")) {
										uri = uri+"&id="+ids+"&busiName="+busiName;
									} else {
										uri = uri+"?id="+ids+"&busiName="+busiName;
									}
									BootstrapDialogUtil.confirmDialog(o.delMsg,function(){
										$.post(uri,function(data){
											var output = $.parseJSON(data.output);
											showMsg(output.msg+"！");
											if(null != o.delCallback && typeof(o.delCallback) === 'function') {
												o.delCallback(output.result);
											} else {
												if(output.result=='1') {
													$($t).trigger("reloadGrid", [{current:true}]);
												}//if
										   }//else
										});
									});
								}
								return false;
							});
							tbd = null;
						}
					}
					if (o.search) {
						if(cnoj.checkAuth(o.currentUri,'search')) {
							pSearch = pSearch || {};
							tbd = "<button type='button' data-width='"+o.searchWidth+"' data-busi='"+utils.handleNull(o.busi)+"' class='btn "+btnStyleClass+" search' data-uri='"+utils.handleNull(o.searchUri)+"' ><i class='glyphicon "+o.searchIcon+"'></i> "+(utils.isEmpty(o.searchtext)?'搜索':o.searchtext)+"</button>";
							$(navtbl).append(tbd);
							$(".search",navtbl).attr({"title":o.searchTitle  || "",id:pSearch.id || "search_"+elemids}).click(function(){
								if (!$(this).hasClass('ui-state-disabled')) {
									if($.isFunction( o.searchfunc )) {
										o.searchfunc.call($t, pSearch);
									} else {
										$($t).jqGrid("searchGrid",pSearch);
									}
								}
								return false;
							});
							if (pSearch.showOnLoad && pSearch.showOnLoad === true) {
								$(tbd,navtbl).click();
							}
							tbd = null;
						}
					}
					if (o.refresh) {
						if(cnoj.checkAuth(o.currentUri,'refresh')) {
							tbd = "<button type='button' class='btn "+o.refreshBtnStyle+" refresh'><i class='glyphicon "+o.refreshIcon+"'></i> "+(utils.isEmpty(o.refreshtext)?'搜索':o.refreshtext)+"</button>";
							$(navtbl).append(tbd);
							$(".refresh",navtbl).attr({"title":o.refreshTitle  || "",id: "refresh_"+elemids}).click(function(){
								if (!$(this).hasClass('ui-state-disabled')) {
									if($.isFunction(o.beforeRefresh)) {o.beforeRefresh.call($t);}
									$t.p.search = false;
									$t.p.resetsearch =  true;
									try {
										if( o.refreshstate !== 'currentfilter') {
											var gID = $t.p.id;
											$t.p.postData.filters ="";
											try {
												$("#fbox_"+$.jgrid.jqID(gID)).jqFilter('resetFilter');
											} catch(ef) {}
											if($.isFunction($t.clearToolbar)) {$t.clearToolbar.call($t,false);}
										}
									} catch (e) {}
									switch (o.refreshstate) {
										case 'firstpage':
											$($t).trigger("reloadGrid", [{page:1}]);
											break;
										case 'current':
										case 'currentfilter':
											$($t).trigger("reloadGrid", [{current:true}]);
											break;
									}
									if($.isFunction(o.afterRefresh)) {o.afterRefresh.call($t);}
								}
								return false;
							});
							tbd = null;
					    }
					}
					tdw = $(".ui-jqgrid").css("font-size") || "11px";
					$('body').append("<div id='testpg2' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"+tdw+";visibility:hidden;' ></div>");
					twd = $(navtbl).clone().appendTo("#testpg2").width();
					$("#testpg2").remove();
					$(pgid+"_"+o.position,pgid).append(navtbl);
					if($t.p._nvtd) {
						if(twd > $t.p._nvtd[0] ) {
							$(pgid+"_"+o.position,pgid).width(twd);
							$t.p._nvtd[0] = twd;
						}
						$t.p._nvtd[1] = twd;
					}
					tdw =null;twd=null;navtbl =null;
					this.nav = true;
				}
			});
		},
		navButtonAdd : function (elem, p) {
			p = $.extend(true,{
				authId:null,
				currentUri:null,
				caption : "newButton",
				btnStyle:'btn-default',
				title: '自定义',
				buttonIcon : 'glyphicon-file',
				buttonId:null,
				onClickButton: null,
				isAuth: true,
				position : "last",
				cursor : 'pointer',
				busi:null,
				selectedType:'none-selected',
				width:600,
				uri:null
			}, p ||{});
			return this.each(function() {
				if( !this.grid)  {return;}
				if( typeof elem === "string" && elem.indexOf("#") !== 0) {elem = "#"+$.jgrid.jqID(elem);}
				var findnav = $(".btn-group",elem)[0], $t = this;
				if (findnav) {
					var isAuth = (utils.isEmpty(p.authId) || !p.isAuth || cnoj.checkAuth(p.currentUri,p.authId));
					if(isAuth) {
						if( p.id && $("#"+$.jgrid.jqID(p.id), findnav)[0] !== undefined )  {return;}
						var tbd = null;
						var icon = "";
						if(p.buttonIcon.toString().toUpperCase() === "NONE") {
							icon = "";
						} else	{
							icon = "<i class='glyphicon "+p.buttonIcon+" "+p.buttonId+"'></i> ";
						}
						tbd = "<button type='button' data-title='"+p.title+"' data-selected-type='"+p.selectedType+"' data-width='"+p.width+
						"' data-busi='"+utils.handleNull(p.busi)+"' data-uri='"+utils.handleNull(p.uri)+"' class='btn cus "+p.btnStyle+" "+p.buttonId+"'>"+icon+p.caption+"</button>";
						if(p.id) {$(tbd).attr("id","cus_"+p.id);}
						if(p.position==='first'){
							if(findnav.rows[0].cells.length ===0 ) {
								$(findnav).append(tbd);
							} else {
								$("button:eq(0)",findnav).before(tbd);
							}
						} else {
							$(findnav).append(tbd);
						}
						$(".cus",findnav).click(function(e){
							var id = $t.p.selarrrow;
							if(!utils.isEmpty(id)) {
								$(this).attr("selected-value",id);
							}
							if(!utils.isEmpty(p.onClickButton) && typeof(p.onClickButton)==='function') {
								p.onClickButton(id,$t);
							} else {
								openProp($(this),null);
							}
							return false;
						});
				    }//if	
				}
			});
		}
	});
})(jQuery);