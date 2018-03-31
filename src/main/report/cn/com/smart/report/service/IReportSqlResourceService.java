package cn.com.smart.report.service;

import java.util.Map;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.report.bean.entity.TReportSqlResource;

public interface IReportSqlResourceService {

    /**
     * 获取数据
     * @param sqlResource 报表SQL资源对象
     * @param params Map参数对象
     * @param start 开始位置
     * @param rows 每次请求返回数据量
     * @return 返回SmartResponse对象
     */
    SmartResponse<Object> getDatas(TReportSqlResource sqlResource, Map<String,Object> params, int start, int rows);
    
}
