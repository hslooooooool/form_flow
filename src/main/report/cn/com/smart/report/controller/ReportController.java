package cn.com.smart.report.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.report.bean.entity.TReport;
import cn.com.smart.report.service.impl.ReportService;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.constant.enums.BtnPropType;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.helper.HttpRequestHelper;
import cn.com.smart.web.service.IOPService;
import cn.com.smart.web.tag.bean.ALink;
import cn.com.smart.web.tag.bean.CustomBtn;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * 报表设计器 -- 控制类
 * @author lmq  2017年9月10日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

    private static final String VIEW_DIR = "report";
    @Autowired
    private ReportService reportServ;
    @Autowired
    private IOPService opServ;
    
    /**
     * 报表列表
     * @param session HttpServletRequest对象
     * @param searchParam 搜索对象
     * @param page 请求分页对象
     * @return 返回列表试图
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, FilterParam searchParam, RequestPage page) {
        ModelAndView modelView = new ModelAndView();
        String uri = HttpRequestHelper.getCurrentUri(request);
        SmartResponse<Object> smartResp = opServ.getDatas("get_report_mgr_list", searchParam, page.getStartNum(), page.getPageSize());
        
        CustomBtn customBtn = new CustomBtn("edit_designer", "简单报表设计", "修改报表", "report/designer");
        customBtn.setSelectedType(BtnPropType.SelectType.ONE.getValue());
        customBtn.setBtnIcon("glyphicon-pencil");
        customBtn.setOpenStyle(BtnPropType.OpenStyle.OPEN_SELF);
        customBtns = new ArrayList<CustomBtn>(1);
        customBtns.add(customBtn);
        delBtn = new DelBtn("report/mgr/delete", "确定要删除选中的报表吗？删除后数据将无法恢复.",uri,null, null);
        refreshBtn = new RefreshBtn(uri, null,null);
        pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
        alinks = new ArrayList<ALink>();
        ALink link = new ALink();
        link.setUri("report/view");
        link.setDialogTitle("查看报表设计内容");
        link.setDialogWidth("");
        alinks.add(link);

        ModelMap modelMap = modelView.getModelMap();
        modelMap.put("smartResp", smartResp);
        modelMap.put("customBtns", customBtns);
        modelMap.put("searchParam", searchParam);
        modelMap.put("delBtn", delBtn);
        modelMap.put("refreshBtn", refreshBtn);
        modelMap.put("pageParam", pageParam);
        modelMap.put("alinks", alinks);
        modelView.setViewName(VIEW_DIR+"/list");
        return modelView;
    }
    
    /**
     * 报表设计
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/designer")
    public ModelAndView designer(HttpServletRequest request, String id) {
        ModelAndView modelView = new ModelAndView();
        if(StringUtils.isNotEmpty(id)) {
            TReport report = reportServ.queryAssoc(id);
            modelView.getModelMap().put("objBean", report);
        }
        modelView.setViewName(VIEW_DIR+"/designer");
        return modelView;
    }
    
    /**
     * 保存报表设计属性
     * @param session
     * @param report
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces="application/json; chartset=UTF-8")
    @ResponseBody
    public SmartResponse<String> save(HttpSession session, TReport report) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        UserInfo userInfo = super.getUserInfoFromSession(session);
        report.setUserId(userInfo.getId());
        smartResp = reportServ.saveOrUpdate(report);
        return smartResp;
    }
    
    /**
     * 删除报表
     * @param id 报表ID
     * @return 返回删除结果
     */
    @RequestMapping(value = "/delete", produces = "application/json; chartset=UTF-8")
    @ResponseBody
    public SmartResponse<String> delete(String id) {
        return reportServ.delete(id);
    }
    
    /**
     * 查看报表属性
     * @param id 报表ID
     * @return
     */
    @RequestMapping("/view")
    public ModelAndView view(String id) {
        ModelAndView modelView = new ModelAndView();
        modelView.getModelMap().put("objBean", reportServ.queryAssocObj(id));
        modelView.setViewName(VIEW_DIR+"/view");
        return modelView;
    }
}
