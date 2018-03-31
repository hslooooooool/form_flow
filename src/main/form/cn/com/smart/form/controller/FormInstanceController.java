package cn.com.smart.form.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.snaker.engine.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.form.bean.QueryFormData;
import cn.com.smart.form.bean.entity.TForm;
import cn.com.smart.form.bean.entity.TFormInstance;
import cn.com.smart.form.helper.FormDataHelper;
import cn.com.smart.form.helper.FormUploadFileHelper;
import cn.com.smart.form.service.FormInstanceService;
import cn.com.smart.form.service.FormService;
import cn.com.smart.form.service.IFormDataService;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.helper.HttpRequestHelper;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.tag.bean.ALink;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * 表单实例 控制器类
 * @author lmq  2017年8月27日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/form/instance")
public class FormInstanceController extends BaseFormController {

    private static final String VIEW_DIR = BASE_FORM_VIEW_DIR+"instance/";
    @Autowired
    private OPService opServ;
    @Autowired
    private FormService formServ;
    @Autowired
    private IFormDataService formDataServ;
    @Autowired
    private FormInstanceService formInsServ;
    
    /**
     * 表单实例列表
     * @param request
     * @param searchFilter
     * @param page
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, FilterParam searchFilter, RequestPage page) {
        ModelAndView modelView = new ModelAndView();
        SmartResponse<Object> smartResp = opServ.getDatas("get_form_instance_list", searchFilter, page.getStartNum(), page.getPageSize());
        String uri = HttpRequestHelper.getCurrentUri(request);
        refreshBtn = new RefreshBtn(uri, null, null);
        editBtn = new EditBtn("edit", "form/instance/edit", null, "修改表单数据", null);
        delBtn = new DelBtn("form/instance/delete", "您确定要删除选中的表单数据吗？", uri, null, null);
        ALink alink = new ALink("form/instance/view", null, "查看表单信息");
        alink.setParamIndex("4,5");
        alink.setParamName("formId,formDataId");
        alinks = new ArrayList<ALink>(1);
        alinks.add(alink);
        pageParam = new PageParam(uri, null, page.getPageSize());
        
        ModelMap modelMap = modelView.getModelMap();
        modelMap.put("smartResp", smartResp);
        modelMap.put("refreshBtn", refreshBtn);
        modelMap.put("delBtn", delBtn);
        modelMap.put("editBtn", editBtn);
        modelMap.put("alinks", alinks);
        modelMap.put("pageParam", pageParam);
        modelView.setViewName(VIEW_DIR+"list");
        return modelView;
    }
    
    /**
     * 通过表单ID，创建表单视图
     * @param formId 表单ID
     * @return 
     */
    @RequestMapping("/create")
    public ModelAndView create(String formId) {
        ModelAndView modelView = new ModelAndView();
        if(StringUtils.isEmpty(formId)) {
            throw new NullArgumentException("formId参数不能为空");
        }
        SmartResponse<TForm> smartResp = formServ.find(formId);
        ModelMap modelMap = modelView.getModelMap();
        modelMap.put("smartResp", smartResp);
        modelMap.put("formDataId", FormDataHelper.createNewFormDataId());
        modelView.setViewName(VIEW_DIR+"/create");
        return modelView;
    }
    
    /**
     * 提交表单
     * @param formId
     * @param formDataId
     * @param request 
     * @param response 
     */
    @RequestMapping(value="submit", method = RequestMethod.POST)
    public void submit(HttpServletRequest request, HttpServletResponse response, String formId, String formDataId) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        SmartResponse<String> smartResp = new SmartResponse<String>();
        smartResp.setMsg("提交表单失败");
        /*if(StringUtils.isEmpty(formDataId)) {
            formDataId = FormDataHelper.createNewFormDataId();
        }*/
        ObjectMapper objMapper = new ObjectMapper();
        if(StringUtils.isNotEmpty(formId) && StringUtils.isNotEmpty(formDataId)) {
            UserInfo userInfo = getUserInfoFromSession(request);
            //处理参数
            Map<String,Object> params = getRequestParamMap(request, false);
            //处理附件
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            if(multipartResolver.isMultipart(request)) {
                new FormUploadFileHelper((MultipartHttpServletRequest) request, params, formId, FormDataHelper.handleFormDataId(formDataId), userInfo.getId()).upload();
            }
            //TODO 保存表单数据
            smartResp = formInsServ.create(params, formDataId, formId, userInfo);
        } 
        try {
            response.getWriter().print(objMapper.writeValueAsString(smartResp));
        } catch (IOException e) {
            e.printStackTrace();
            LoggerUtils.error(log, e.getMessage());
        }
    }
    
    /**
     * 删除表单实例
     * @param id 实例ID
     * @return 返回删除结果（JSON格式）
     */
    @RequestMapping(value="/delete", produces="application/json;charset=UTF-8")
    @ResponseBody
    public SmartResponse<String> delete(String id) {
        return formInsServ.delete(id);
    }
    
    /**
     * 修改表单数据
     * @param id 表单实例ID
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView edit(String id) {
        ModelAndView modelView = new ModelAndView();
        if(StringUtils.isNotEmpty(id)) {
            TFormInstance formIns = formInsServ.find(id).getData();
            handleView(modelView.getModelMap(), formIns.getFormId(), formIns.getFormDataId());
        }
        modelView.setViewName(VIEW_DIR+"edit");
        return modelView;
    }
    
    /**
     * 查看表单实例
     * @param formId 表单ID
     * @param formDataId 表单数据ID
     * @return 
     */
    @RequestMapping("/view")
    public ModelAndView view(String formId, String formDataId) {
        ModelAndView modelView = new ModelAndView();
        handleView(modelView.getModelMap(), formId, formDataId);
        modelView.setViewName(VIEW_DIR+"view");
        return modelView;
    }
    
    /**
     * 处理试图
     * @param modelMap
     * @param formId
     * @param formDataId
     */
    private void handleView(ModelMap modelMap, String formId, String formDataId) {
        if(StringUtils.isNotEmpty(formId) && StringUtils.isNotEmpty(formDataId)) {
            TForm form = formServ.find(formId).getData();
            modelMap.put("objBean", form);
            modelMap.put("formId", formId);
            modelMap.put("formDataId", formDataId);
            SmartResponse<QueryFormData> smartResp = formDataServ.getFormDataByFormDataId(formDataId, formId);
            String output = JsonHelper.toJson(smartResp);
            output = StringUtils.repaceSpecialChar(output);
            output = StringUtils.repaceSlash(output);
            modelMap.put("output", output);
        }
    }
}
