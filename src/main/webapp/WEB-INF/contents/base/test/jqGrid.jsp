<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
$(document).ready(function(){
   /* var gidData = [
           {action:"", id: "1", orderdate: "2013-10-01", customer: "customer",  price: "200.00", vat: "10.00", completed: true, shipment: "TN", total: "210.00"},
           {action:"", id: "2", orderdate: "2013-10-01", customer: "customer2",  price: "300.00", vat: "20.00", completed: false, shipment: "FE", total: "320.00"},
           {action:"", id: "3", orderdate: "2011-07-30", customer: "customer3",  price: "400.00", vat: "30.00", completed: false, shipment: "FE", total: "430.00"},
           {action:"", id: "4", orderdate: "2013-10-04", customer: "customer4",  price: "200.00", vat: "10.00", completed: true, shipment: "TN", total: "210.00"},
           {action:"", id: "5", orderdate: "2013-11-31", customer: "customer5",  price: "300.00", vat: "20.00", completed: false, shipment: "FE", total: "320.00"},
           {action:"", id: "6", orderdate: "2013-09-06", customer: "customer6",  price: "400.00", vat: "30.00", completed: false, shipment: "FE", total: "430.00"},
           {action:"", id: "7", orderdate: "2011-08-30", customer: "customer7",  price: "200.00", vat: "10.00", completed: true, shipment: "TN", total: "210.00"},
           {action:"", id: "8", orderdate: "2013-10-03", customer: "customer8",  price: "300.00", vat: "20.00", completed: false, shipment: "FE", total: "320.00"},
           {action:"", id: "9", orderdate: "2013-09-01", customer: "customer9",  price: "400.00", vat: "30.00", completed: false, shipment: "TN", total: "430.00"},
           {action:"", id: "10", orderdate: "2013-09-08", customer: "customer10", price: "702.00", vat: "30.00", completed: true, shipment: "IN", total: "530.00"},
           {action:"", id: "11", orderdate: "2013-09-08", customer: "customer11",  price: "500.00", vat: "30.00", completed: false, shipment: "FE", total: "530.00"},
           {action:"", id: "12", orderdate: "2013-09-10", customer: "customer12",  price: "500.00", vat: "30.00", completed: false, shipment: "FE", total: "530.00"},
           
           {action:"", id: "13", orderdate: "2013-10-01", customer: "customer",  price: "200.00", vat: "10.00", completed: true, shipment: "TN", total: "210.00"},
           {action:"", id: "14", orderdate: "2013-10-01", customer: "customer2",  price: "300.00", vat: "20.00", completed: false, shipment: "FE", total: "320.00"},
           {action:"", id: "15", orderdate: "2011-07-30", customer: "customer3",  price: "400.00", vat: "30.00", completed: false, shipment: "FE", total: "430.00"},
           {action:"", id: "16", orderdate: "2013-10-04", customer: "customer4",  price: "200.00", vat: "10.00", completed: true, shipment: "TN", total: "210.00"},
           {action:"", id: "17", orderdate: "2013-11-31", customer: "customer5",  price: "300.00", vat: "20.00", completed: false, shipment: "FE", total: "320.00"},
           {action:"", id: "18", orderdate: "2013-09-06", customer: "customer6",  price: "400.00", vat: "30.00", completed: false, shipment: "FE", total: "430.00"},
           {action:"", id: "19", orderdate: "2011-08-30", customer: "customer7",  price: "200.00", vat: "10.00", completed: true, shipment: "TN", total: "210.00"},
           {action:"", id: "20", orderdate: "2013-10-03", customer: "customer8",  price: "300.00", vat: "20.00", completed: false, shipment: "FE", total: "320.00"},
           {action:"", id: "21", orderdate: "2013-09-01", customer: "customer9",  price: "400.00", vat: "30.00", completed: false, shipment: "TN", total: "430.00"},
           {action:"", id: "22", orderdate: "2013-09-08", customer: "customer10", price: "702.00", vat: "30.00", completed: true, shipment: "IN", total: "530.00"},
           {action:"", id: "23", orderdate: "2013-09-08", customer: "customer11",  price: "500.00", vat: "30.00", completed: false, shipment: "FE", total: "530.00"},
           {action:"", id: "24", orderdate: "2013-09-10", customer: "customer12",  price: "500.00", vat: "30.00", completed: false, shipment: "FE", total: "530.00"}
       ];
       var $theGrid = $("#theGrid");
       var numberTemplate = {formatter: 'number', align: 'right', sorttype: 'number'};
       $theGrid.jqGridUtil({
        datatype: 'local',
        data: gidData,
        colNames: ['动作','客户', '日期',  '价格', '增值税', '总计', '已完成', '载货量'],
        colModel: [
              {name:'action',index:'action',sortable:false, formatter: displayButtons},
              {name: 'customer', index: 'customer', width: 90, editable:true},
              {name: 'orderdate', index: 'orderdate', width: 100, align: 'center', sorttype: 'date',
                formatter: 'date', formatoptions: {newformat: 'd-M-Y'}, datefmt: 'd-M-Y'},
            {name: 'price', index: 'price', width: 55, template: numberTemplate},
            {name: 'vat', index: 'vat', width: 42, template: numberTemplate},
            {name: 'total', index: 'total', width: 50, template: numberTemplate},
            {name: 'completed', index: 'completed', width: 30, align: 'center', formatter: 'checkbox',
                edittype: 'checkbox', editoptions: {value: 'Yes:No', defaultValue: 'Yes'}},
            {name: 'shipment', index: 'shipment', width: 80, align: 'center', formatter: 'select',
               edittype: 'select', editoptions: {value: 'FE:FedEx;TN:TNT;IN:Intime;us:USPS', defaultValue: 'Intime'}}                  
        ],
        colModel: [
           {name:'action',index:'action'},
           {name:'customer',index:'customer'},
           {name:'orderdate',index:'orderdate'},
           {name:'price',index:'price'},
           {name:'vat',index:'vat'},
           {name:'total',index:'total'},
           {name:'completed',index:'completed'},
           {name:'shipment',index:'shipment'}
        ],
        gridview: true,
        pager: '#gridPager',
        viewrecords: true, 
        multiselect:true,
        caption: '测试表格列表'
    });*/
    $("#theGrid").jqGridUtil({
    	url:'jqGrid/query/filter/user_mgr_list_test.json',
    	datatype: "json",
       	colNames:['ID','用户名','姓名','单位','手机号码','QQ','邮箱','岗位','状态','创建时间'],
       	colModel:[
       		{name:'ID',hidden:true},
            {name:'username'},
            {name:'name'},
            {name:'unit'},
            {name:'phone'},
            {name:'qq'},
            {name:'email'},
            {name:'pos'},
            {name:'state'},
            {name:'createTime'}
       	],
       	gridview: true,
       	autoRow:true,
        pager: '#gridPager',
        multiselect:true,
        search:true,
        viewrecords: true
        //caption: '测试表格列表'
        //subtractHeight:100
    });
       $("#theGrid").jqGrid("navGrid", "#gridPager", {
    	currentUri:'${currentUri}',
    	refresh : false,
   		edit : false,
   		editUri:'showPage/base_user_edit',
   		editBusi:'user',
   		editTitle:'修改用户',
   		
   		add : false,
   		addtext:'添加',
   		addUri:'showPage/base_user_add',
   		addTitle:'添加用户',
   		del : false,
   		view:false,
   		viewtext:'查看'
   	}); 
       
       $("#theGrid").jqGrid("navButtonAdd","#gridPager", {
    	    caption : "下载",
			title: '下载',
			authId:'add',
			isAuth:false,
			//currentUri: 'menu/list',
			buttonIcon : 'glyphicon-save',
			position : "last",
			cursor : 'pointer',
			buttonId:'new_btn',
			btnStyle: 'btn-success',
			onClickButton: function(id,obj){
				alert("1111");
			}
      	}); 
});
/*
function displayButtons(cellvalue, options, rowObject)
{
    var edit = "<input style='' type='button' value='Edit' onclick=\"jQuery('#theGrid').editRow('" + options.rowId + "');\"  />", 
         save = "<input style='' type='button' value='Save' onclick=\"jQuery('#theGrid').saveRow('" + options.rowId + "');\"  />", 
         restore = "<input style='' type='button' value='Restore' onclick=\"jQuery('#theGrid').restoreRow('" + options.rowId + "');\" />";
    return edit+save+restore;
}*/
</script>
<table id="theGrid" class="cnoj-jq-grid"></table>
<div id="gridPager"></div>

