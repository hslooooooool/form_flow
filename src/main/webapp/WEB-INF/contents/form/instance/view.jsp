<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link href="${ctx}/plugins/form/css/form.css" rel="stylesheet" />
<script src="${ctx}/plugins/form/js/form.prop.listener.js" type="text/javascript"></script>
<script src="${ctx}/plugins/form/js/init.form.js" type="text/javascript"></script>
<div class="wrap-content">
   <div class="form-contents">
       <div class="panel-tabs-wrap">
            <div class="panel-heading p-0">
                <div class="panel-tabs-tab">
                    <ul class="nav nav-tabs ui-state-default" role="tablist">
                        <li class="active"><a href="#form-content-tab" role="presentation" data-toggle="tab"> 表单信息</a></li>
                        <li><a href="#form-att-tab" id="form-att-tab-a" role="presentation" data-toggle="tab"> 附件 <span class="badge"></span></a></li>
                    </ul>
                </div>
            </div>
            <div class="panel-body p-0">
                <div id="form-panel-contents" class="tab-content panel-tab-content bg-color-white cnoj-auto-limit-height" data-subtract-height="150">
                    <div role="tabpanel" class="tab-pane active" id="form-content-tab">
                        <div class="form-prop">
                           <form id="create-form" method="post" data-relate-arg-form="#create-form-param" enctype="multipart/form-data">
                               ${objBean.parseHtml}
                           </form>
                           <iframe class="hidden" id="handle-form-iframe" name="handle-form-iframe" frameborder=0 width=0 height=0></iframe>
                       </div>
                    </div>
                    <div role="tabpanel" class="tab-pane p-t-10" id="form-att-tab">
                        <div class="cnoj-load-url" data-uri="form/attachment/list?formId=${formId }&formDataId=${formDataId}&isView=1" ></div>
                    </div>
                </div>
            </div>
      </div><!-- panel-tabs-wrap -->
   </div>
</div>

<script type="text/javascript">
    
    var initForm = $("#create-form").initForm({
        username:'${userInfo.fullName}',
        deptName: '${userInfo.deptName}',
        formData:'${output}',
        isView:true,
        initDataAfter: function(){
            printListener();
            //inputPluginEvent();
            //hrefListener();
            limitHeightListener();
            //loadUrlListener();
        }
    });
    initForm.init();
    $(".form-contents").resize(function(){
       setTimeout(function() {
          limitHeightListener();
       }, 200);
    });
</script>