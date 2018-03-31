package cn.com.smart.report.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.report.bean.entity.TReport;
import cn.com.smart.report.bean.entity.TReportField;
import cn.com.smart.report.bean.entity.TReportProperties;
import cn.com.smart.report.bean.entity.TReportSqlResource;
import cn.com.smart.report.helper.ReportHelper;
import cn.com.smart.report.service.IReportExport;
import cn.com.smart.report.service.IReportInstanceService;
import cn.com.smart.report.service.IReportSqlResourceService;
import cn.com.smart.service.impl.BaseServiceImpl;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.constant.enums.BtnPropType;
import cn.com.smart.web.constant.enums.PageOpenStyle;
import cn.com.smart.web.helper.HttpRequestHelper;
import cn.com.smart.web.tag.bean.ALink;
import cn.com.smart.web.tag.bean.CustomBtn;
import cn.com.smart.web.tag.bean.CustomTableCell;
import cn.com.smart.web.tag.bean.ICustomCellCallback;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * 报表实例服务类
 * @author lmq  2017年10月11日
 * @version 1.0
 * @since 1.0
 */
@Service
public class ReportInstanceService extends BaseServiceImpl implements IReportInstanceService {

    @Autowired
    private ReportService reportServ;
    @Autowired
    private IReportSqlResourceService reportSqlResServ;
    @Autowired
    @Qualifier("simpleExport")
    private IReportExport reportExport;
    
    @Override
    public SmartResponse<Object> getDatas(TReportSqlResource sqlResource, Map<String,Object> params, int start, int rows) {
        return reportSqlResServ.getDatas(sqlResource, params, start, rows);
    }
    
    @Override
    public void handleListView(String reportId, ModelMap modelMap, RequestPage page, HttpServletRequest request) {
        TReport report = reportServ.queryAssoc(reportId);
        List<TReportField> fields = report.getFields();
        if(CollectionUtils.isEmpty(fields)) {
            throw new IllegalArgumentException("报表没有字段信息");
        }
        String uri = HttpRequestHelper.getCurrentUri(request);
        uri += "?reportId="+StringUtils.handleNull(reportId);
        UserInfo userInfo = HttpRequestHelper.getUserInfoFromSession(request);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orgIds", userInfo.getOrgIds().toArray());
        List<TReportField> searchFields = getSearchFields(fields);
        //设置搜索字段
        handleSearchParam(params, request, searchFields);
        SmartResponse<Object> smartResp = this.getDatas(report.getSqlResource(), params, page.getStartNum(), page.getPageSize());
        //设置搜索值（用于在列表页面回写搜索的值）
        modelMap.put("searchValues", getSearchParamValues(request, searchFields));
        //设置链接
        List<TReportField> alinkFields = getALinkFields(fields);
        List<ALink> alinks = null;
        if(CollectionUtils.isNotEmpty(alinkFields)) {
            alinks = new ArrayList<ALink>();
            for(TReportField alinkField : alinkFields) {
                ALink alink = new ALink();
                alink.setUri(alinkField.getUrl());
                if("_blank".equals(alinkField.getOpenUrlType())) {
                    alink.setaTarget("_blank");
                } else if("popup_win".equals(alinkField.getOpenUrlType())){
                    alink.setClassTarget(PageOpenStyle.OPEN_BLANK);
                } else {
                    alink.setClassTarget(PageOpenStyle.valueOf(alinkField.getOpenUrlType()));
                }
                alink.setDialogTitle("查看"+alinkField.getTitle());
                alink.setParamIndex(alinkField.getParamValue());
                alink.setParamName(alinkField.getParamName());
                alink.setLinkPostion(alinkField.getSortOrder().toString());
                alink.setDialogWidth("");
                alinks.add(alink);
            }
        }
        //设置自定义列
        List<TReportField> customCellFields = getCustomCellFields(fields);
        List<CustomTableCell> customTableCells = null;
        if(CollectionUtils.isNotEmpty(customCellFields)) {
            customTableCells = new ArrayList<CustomTableCell>();
            for(TReportField cellField : customCellFields) {
                ICustomCellCallback callback = getCustomCellCallback(cellField.getCustomClass());
                if(null != callback) {
                    CustomTableCell cell = new CustomTableCell();
                    cell.setCellCallback(callback);
                    customTableCells.add(cell);
                }
            }
            customTableCells = customTableCells.size() > 0 ? customTableCells : null;
        }
        CustomBtn customBtn = new CustomBtn("export", "导出", "导出", "report/instance/export?reportId="+StringUtils.handleNull(reportId));
        customBtn.setBtnIcon("fa-file-excel-o");
        customBtn.setBtnStyle("btn-primary btn-sm report-export");
        customBtn.setOpenStyle(BtnPropType.OpenStyle.NONE);
        
        PageParam pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
        RefreshBtn refreshBtn  = new RefreshBtn(uri, null, null);
        refreshBtn.setIsAuth(YesNoType.NO.getValue());
        TReportProperties reportProp = report.getProperties();
        String isOriginalTable = "0";
        if(YesNoType.NO.getIndex() == reportProp.getIsFixedHeader()) {
            isOriginalTable = "1";
        }
        modelMap.put("uri", uri);
        modelMap.put("headerWidths", ReportHelper.getWidths(fields));
        modelMap.put("headerTitles", ReportHelper.getHeaderTitles(reportProp, fields));
        modelMap.put("reportProp", reportProp);
        modelMap.put("searchFields", searchFields);
        modelMap.put("smartResp", smartResp);
        modelMap.put("alinks", alinks);
        modelMap.put("customCells", customTableCells);
        modelMap.put("pageParam", pageParam);
        modelMap.put("refreshBtn", refreshBtn);
        modelMap.put("isOriginalTable", isOriginalTable);
        modelMap.put("customBtn", customBtn);
        modelMap.put("currentUri", uri);
    }
    
    @Override
    public ResponseEntity<byte[]> export(String reportId, HttpServletRequest request) {
        LoggerUtils.debug(logger, "正在导出报表列表,报表ID为["+reportId+"]...");
        UserInfo userInfo = HttpRequestHelper.getUserInfoFromSession(request);
        TReport report = reportServ.queryAssoc(reportId);
        List<TReportField> fields = report.getFields();
        if(CollectionUtils.isEmpty(fields)) {
            LoggerUtils.error(logger, "报表没有字段信息...");
            throw new IllegalArgumentException("报表没有字段信息");
        }
        List<TReportField> searchFields = getSearchFields(fields);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orgIds", userInfo.getOrgIds().toArray());
        //设置搜索字段
        handleSearchParam(params, request, searchFields);
        //导出报表
        byte[] bytes = reportExport.export(report, params);
        if(null == bytes) {
            throw new RuntimeException("导出接口返回的结果为：null");
        }
        HttpHeaders headers = new HttpHeaders();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = null;
        try {
            fileName = new String((report.getName()+"_"+sdf.format(new Date())+".xlsx").getBytes("UTF-8"),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }//为了解决中文名称乱码问题  
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
        return responseEntity;
    }
    
    /**
     * 处理搜索参数
     * @param params Map参数
     * @param searchFields 搜索字段列表
     * @param request HttpServletRequest请求对象
     */
    private void handleSearchParam(Map<String, Object> params, HttpServletRequest request, List<TReportField> searchFields) {
        //设置搜索字段
        if(CollectionUtils.isNotEmpty(searchFields)) {
            for (TReportField reportField : searchFields) {
                params.put(reportField.getSearchName(), request.getParameter(reportField.getSearchName()));
            }
        }
    }
    
    /**
     * 获取搜索参数的值
     * @param request HttpServletRequest请求对象
     * @param searchFields 搜索字段列表
     * @return 返回搜索参数值列表
     */
    private List<String> getSearchParamValues(HttpServletRequest request, List<TReportField> searchFields) {
        List<String> values = null;
        //设置搜索字段
        if(CollectionUtils.isNotEmpty(searchFields)) {
            values = new ArrayList<String>(searchFields.size());
            for (TReportField reportField : searchFields) {
                values.add(request.getParameter(reportField.getSearchName()));
            }
        }
        return values;
    }
    
    /**
     * 获取搜索字段列表
     * @param fields 字段列表
     * @return 返回搜索字段列表
     */
    private List<TReportField> getSearchFields(List<TReportField> fields) {
        List<TReportField> searchFields = new ArrayList<TReportField>();
        for (TReportField reportField : fields) {
            if(StringUtils.isNotEmpty(reportField.getSearchName())) {
                searchFields.add(reportField);
            }
        }
        return searchFields.size() > 0 ? searchFields : null;
    }
    
    /**
     * 获取链接字段
     * @param fields 字段列表
     * @return 返回链接字段列表
     */
    private List<TReportField> getALinkFields(List<TReportField> fields) {
        List<TReportField> alinkFields = new ArrayList<TReportField>();
        for (TReportField reportField : fields) {
            if(StringUtils.isNotEmpty(reportField.getUrl()) 
                    || StringUtils.isNotEmpty(reportField.getParamName()) 
                    || StringUtils.isNotEmpty(reportField.getParamValue())) {
                alinkFields.add(reportField);
            }
        }
        return alinkFields.size() > 0 ? alinkFields : null;
    }
    
    /**
     * 获取自定义单元格的字段列表
     * @param fields 报表字段列表
     * @return 返回设置了自定义类的字段列表
     */
    private List<TReportField> getCustomCellFields(List<TReportField> fields) {
        List<TReportField> customCellFields = new ArrayList<TReportField>();
        for (TReportField reportField : fields) {
            if(StringUtils.isNotEmpty(reportField.getCustomClass())) {
                customCellFields.add(reportField);
            }
        }
        return customCellFields.size() > 0 ? customCellFields : null;
    }

    /**
     * 获取自定义单元格对象
     * @param customClass 自定义类
     * @return 返回自定义类的实例
     */
    private ICustomCellCallback getCustomCellCallback(String customClass) {
        try {
            Class<?> clazz = Class.forName(customClass);
            if(clazz.isAssignableFrom(ICustomCellCallback.class)) {
                return (ICustomCellCallback)clazz.newInstance();
            } else {
                return null;
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        } 
        return null;
    }

}
