package cn.com.smart.report.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.constant.ResourceConstant;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.entity.TNResource;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.helper.HttpRequestHelper;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.service.ResourceService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * 报表资源管理控制类
 * @author lmq  2017年10月11日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/report/resource")
public class ReportResourceController extends BaseController {
    
    private static final String VIEW_DIR = "report/resource";
    
    @Autowired
    private OPService opServ;
    @Autowired
    private ResourceService resServ;

    /**
     * 表单资源管理列表
     * @param request HttpServletRequest请求对象
     * @param page 分页对象
     * @return 返回列表视图
     * @throws Exception
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, RequestPage page) {
        ModelAndView modelView = new ModelAndView();
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("orgIds", StringUtils.list2Array(getUserInfoFromSession(request).getOrgIds()));
        SmartResponse<Object> smartResp = opServ.getDatas("report_resource_mgr_list",params, page.getStartNum(), page.getPageSize());
        params = null;
        String uri = HttpRequestHelper.getCurrentUri(request);
        addBtn = new EditBtn("add","showPage/report_resource_add", null, "添加报表资源", "600");
        editBtn = new EditBtn("edit","showPage/report_resource_edit", "resource", "修改报表资源", "600");
        delBtn = new DelBtn("resource/delete.json", "确定要删除选中的报表资源吗？",uri,null, null);
        refreshBtn = new RefreshBtn(uri, "resource",null);
        pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
        
        ModelMap modelMap = modelView.getModelMap();
        modelMap.put("smartResp", smartResp);
        modelMap.put("addBtn", addBtn);
        modelMap.put("editBtn", editBtn);
        modelMap.put("delBtn", delBtn);
        modelMap.put("refreshBtn", refreshBtn);
        modelMap.put("pageParam", pageParam);
        
        modelView.setViewName(VIEW_DIR+"/list");
        return modelView;
    }
    
    /**
     * 添加表单资源
     * @param resource 资源实体对象
     * @return 返回添加结果
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public SmartResponse<String> add(TNResource resource) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        if(null != resource) {
            resource.setType(ResourceConstant.REPORT_RESOURCE);
            String uri = "report/instance/list?reportId="+resource.getUri();
            resource.setUri(uri);
            smartResp = resServ.save(resource);
        }
        return smartResp;
    }
    
    /**
     * 编辑表单资源
     * @param resource 资源实体对象
     * @return 返回添加结果
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public SmartResponse<String> edit(TNResource resource) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        if(null != resource) {
            resource.setType(ResourceConstant.FORM_RESOURCE);
            String uri = "report/instance/list?reportId="+resource.getUri();
            resource.setUri(uri);
            smartResp = resServ.update(resource);
        }
        return smartResp;
    }
}
