package cn.com.smart.report.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.report.service.IReportInstanceService;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.controller.base.BaseController;

/**
 * 报表实例
 * @author lmq  2017年10月11日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/report/instance")
public class ReportInstanceController extends BaseController {
    
    private static final String VIEW_DIR = "report/instance";
    
    @Autowired
    private IReportInstanceService reportInstServ;
    
    /**
     * 报表列表
     * @param request HttpServlet请求对象
     * @param reportId 报表ID
     * @param page 分页对象
     * @return 返回列表视图
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, String reportId, RequestPage page) {
        ModelAndView modelView = new ModelAndView();
        if(StringUtils.isEmpty(reportId)) {
            throw new NullArgumentException("reportId参数为空");
        }
        ModelMap modelMap = modelView.getModelMap();
        reportInstServ.handleListView(reportId, modelMap, page, request);
        modelView.setViewName(VIEW_DIR+"/list");
        return modelView;
    }
    
    /**
     * 导出简单的报表
     * @param request HttpServletRequest请求对象
     * @param reportId 报表ID
     * @return 
     */
    @RequestMapping("/export")
    public ResponseEntity<byte[]> export(HttpServletRequest request, String reportId) {
        return reportInstServ.export(reportId, request);
    }
    
}
