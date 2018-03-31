package cn.com.smart.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.report.bean.entity.TReport;
import cn.com.smart.report.bean.entity.TReportField;
import cn.com.smart.report.bean.entity.TReportProperties;
import cn.com.smart.report.bean.entity.TReportSqlResource;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.service.IOPService;

@Service
public class ReportService extends MgrServiceImpl<TReport> {

    @Autowired
    private ReportFieldService reportFieldServ;
    @Autowired
    private ReportPropertiesService reportPropServ;
    @Autowired
    private ReportSqlResourceService reportSqlResServ;
    @Autowired
    private IOPService opServ;
    
    @Override
    public SmartResponse<String> delete(String id) throws ServiceException {
        SmartResponse<String> smartResp = new SmartResponse<String>("删除失败");
        if(StringUtils.isEmpty(id)) {
            return smartResp;
        }
        Map<String, Object> param = new HashMap<String, Object>(1);
        param.put("ids", id);
        smartResp = opServ.execute("delete_report", param);
        if(OP_SUCCESS.equals(smartResp.getResult())) {
            smartResp.setMsg("删除成功");
        }
        return smartResp;
    }
    
    /**
     * 保存或更新报表设计
     * @param report 报表实体类
     * @return 返回结果
     */
    public SmartResponse<String> saveOrUpdate(TReport report) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        checkRequire(report);
        if(StringUtils.isEmpty(report.getId())) {
            smartResp = this.save(report);
        } else {
            smartResp = this.update(report);
        }
        return smartResp;
    }
    
    @Override
    public SmartResponse<String> save(TReport report) throws ServiceException {
        SmartResponse<String> smartResp = super.save(report);
        if(OP_SUCCESS.equals(smartResp.getResult())) {
            TReportProperties reportProp = report.getProperties();
            reportProp.setReportId(report.getId());
            reportPropServ.save(reportProp);
            
            TReportSqlResource reportSqlRes = report.getSqlResource();
            reportSqlRes.setSql(handleSql(reportSqlRes.getSql()));
            reportSqlRes.setReportId(report.getId());
            reportSqlResServ.save(reportSqlRes);
            
            List<TReportField> reportFields = report.getFields();
            for (TReportField reportField : reportFields) {
                reportField.setReportId(report.getId());
                //标题为空的字段不保存
                if(StringUtils.isNotEmpty(reportField.getTitle())) {
                    reportFieldServ.save(reportField);
                }
            }
        }
        return smartResp;
    }

    @Override
    public SmartResponse<String> update(TReport report) throws ServiceException {
        SmartResponse<String> smartResp = super.update(report);
        TReportProperties reportProp = report.getProperties();
        reportProp.setReportId(report.getId());
        reportPropServ.update(reportProp);
        
        TReportSqlResource reportSqlRes = report.getSqlResource();
        reportSqlRes.setSql(handleSql(reportSqlRes.getSql()));
        reportSqlRes.setReportId(report.getId());
        reportSqlResServ.update(reportSqlRes);
        reportFieldServ.updateField(report.getId(), report.getFields());
        smartResp.setResult(OP_SUCCESS);
        smartResp.setMsg("修改成功");
        return smartResp;
    }
    
    /**
     * 关联查询报表（查询出与报表相关的数据，如：报表属性及字段，自定义SQL语句）
     * @param id 报表ID
     * @return 返回报表实体类（包含属性实体数据，字段实体列表，自定义SQL语句实体）
     */
    public TReport queryAssoc(String id) {
        if(StringUtils.isEmpty(id)) {
            throw new NullArgumentException();
        }
        TReport report = super.find(id).getData();
        Map<String, Object> param = new HashMap<String, Object>(1);
        param.put("reportId", id);
        assocReport(report, param);
        return report;
    }
    
    /**
     * 关联查询报表（与数据字段关联查询报表类型）
     * @param id 报表ID
     * @return 返回报表实体类（包含属性实体数据，字段实体列表，自定义SQL语句实体）
     */
    public TReport queryAssocObj(String id) {
        if(StringUtils.isEmpty(id)) {
            throw new NullArgumentException();
        }
        String sql = SQLResUtil.getOpSqlMap().getSQL("query_assoc_report");
        if(StringUtils.isEmpty(sql)) {
            throw new NullPointerException("[query_assoc_report]对应的SQL语句为空");
        }
        Map<String, Object> param = new HashMap<String, Object>(1);
        param.put("id", id);
        List<TReport> reportList = super.getDao().querySqlToEntity(sql, param, TReport.class);
        if(CollectionUtils.isEmpty(reportList)) {
            return null;
        }
        TReport report = reportList.get(0);
        param.clear();
        param.put("reportId", id);
        assocReport(report, param);
        return report;
    }
    
    private void assocReport(TReport report, Map<String, Object> param) {
        List<TReportProperties> properties = reportPropServ.findByParam(param).getDatas();
        if(CollectionUtils.isNotEmpty(properties)) {
            report.setProperties(properties.get(0));
        }
        List<TReportSqlResource> sqlResList = reportSqlResServ.findByParam(param).getDatas();
        if(CollectionUtils.isNotEmpty(sqlResList)) {
            report.setSqlResource(sqlResList.get(0));
        }
        report.setFields(reportFieldServ.findByParam(param," sortOrder asc").getDatas());
    }
    
    /**
     * 验证必填属性
     * @param report 报表实体类
     */
    private void checkRequire(TReport report) {
        if(StringUtils.isEmpty(report.getName()) 
                || null == report.getProperties() 
                || null == report.getSqlResource() 
                || StringUtils.isEmpty(report.getSqlResource().getName()) 
                || StringUtils.isEmpty(report.getSqlResource().getSql())) {
            throw new NullArgumentException("必填字段为空");
        }
    }
    
    private String handleSql(String sql) {
        if(StringUtils.isNotEmpty(sql)) {
            sql = sql.replaceAll("\n", " ");
            sql = sql.replaceAll("\r", " ");
            sql = sql.replaceAll("\t", " ");
            sql = sql.replaceAll(" +", " ");
        }
        return sql;
    }

}
