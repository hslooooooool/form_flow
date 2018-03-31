package cn.com.smart.report.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.report.bean.entity.TReportSqlResource;
import cn.com.smart.web.bean.RequestPage;

/**
 * 报表实例服务接口
 * @author lmq  2017年10月12日
 * @version 1.0
 * @since 1.0
 */
public interface IReportInstanceService {

    /**
     * 获取数据
     * @param sqlResource 报表SQL资源对象
     * @param params Map参数对象
     * @param start 开始位置
     * @param rows 每次请求返回数据量
     * @return 返回SmartResponse对象
     */
    SmartResponse<Object> getDatas(TReportSqlResource sqlResource, Map<String,Object> params, int start, int rows);
    
    /**
     * 处理列表视图
     * @param reportId 报表ID
     * @param modelMap ModelMap对象
     * @param page 请求对象
     * @param request HttpServlet请求对象
     */
    void handleListView(String reportId, ModelMap modelMap, RequestPage page, HttpServletRequest request);
    
    /**
     * 导出报表
     * @param reportId 报表ID
     * @param reuqest HttpServlet请求对象
     * @return 返回ResponseEntity对象
     */
    ResponseEntity<byte[]> export(String reportId, HttpServletRequest reuqest);
}
