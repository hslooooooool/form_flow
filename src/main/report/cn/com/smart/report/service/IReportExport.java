package cn.com.smart.report.service;

import java.util.Map;

import cn.com.smart.report.bean.entity.TReport;

/**
 * 报表导出接口
 * @author lmq  2017年10月12日
 * @version 1.0
 * @since 1.0
 */
public interface IReportExport {

    /**
     * 导出报表
     * @param TReport 报表实体类
     * @param searchParam 搜索参数Map对象
     * @return 返回报表对应的字节数组
     */
    byte[] export(TReport report, Map<String, Object> searchParam);
    
    /**
     * 导出报表
     * @param reportId 报表ID
     * @param searchParam 搜索参数Map对象
     * @return 返回报表对应的字节数组
     */
    byte[] export(String reportId, Map<String, Object> searchParam);
    
}
