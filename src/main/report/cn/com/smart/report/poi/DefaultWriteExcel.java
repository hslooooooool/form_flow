package cn.com.smart.report.poi;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.exception.NullArgumentException;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.report.bean.entity.TReport;
import cn.com.smart.report.bean.entity.TReportSqlResource;
import cn.com.smart.report.helper.ReportHelper;
import cn.com.smart.report.service.IReportSqlResourceService;
import cn.com.smart.report.service.impl.ReportService;
import cn.com.smart.service.SmartContextService;
import cn.com.smart.web.ISmartWeb;
import cn.com.smart.web.bean.RequestPage;

/**
 * 默认写入Excel的方法（xlsx格式）
 * @author lmq  2017年10月15日
 * @version 1.0
 * @since 1.0
 */
public class DefaultWriteExcel extends AbstractWriteExcel {
    
    public static final int DEFAULT_PER_SIZE = 100;
    
    private TReport report;
    
    private Map<String, Object> params;
    
    private IReportSqlResourceService reportSqlResServ;

    public DefaultWriteExcel(TReport report, Map<String, Object> params) {
        super(new XSSFWorkbook());
        this.report = report;
        this.params = params;
    }
    
    public DefaultWriteExcel(String reportId, Map<String, Object> params) {
        super(new XSSFWorkbook());
        if(StringUtils.isNotEmpty(reportId)) {
            ReportService reportServ = SmartContextService.find(ReportService.class);
            this.report = reportServ.find(reportId).getData();
        } else {
            throw new NullArgumentException();
        }
        this.params = params;
    }

    @Override
    public String[] getHeaderTitle() {
        String title = ReportHelper.getHeaderTitles(report.getProperties(), report.getFields());
        if(StringUtils.isNotEmpty(title)) {
            return title.split(ISmartWeb.MULTI_VALUE_SPLIT);
        }
        return null;
    }

    @Override
    public String[] getCellWidth() {
        String width = ReportHelper.getWidths(report.getFields());
        if(StringUtils.isNotEmpty(width)) {
            return width.split(ISmartWeb.MULTI_VALUE_SPLIT);
        }
        return null;
    }

    @Override
    public List<Object> getDatas() {
        return null;
    }

    @Override
    public boolean isShowId() {
        YesNoType yesNoType = YesNoType.getObj(this.report.getProperties().getIsShowId());
        yesNoType = (null == yesNoType) ? YesNoType.NO : yesNoType;
        return yesNoType.getValue();
    }

    @Override
    protected void createDataRow(int startRowNum, List<Object> datas, Sheet sheet, int titleLength) {
        if(null == reportSqlResServ) {
            reportSqlResServ = SmartContextService.find(IReportSqlResourceService.class);
        }
        int page = 1;
        RequestPage requestPage = new RequestPage();
        requestPage.setPage(page);
        requestPage.setPageSize(DEFAULT_PER_SIZE);
        TReportSqlResource sqlResp = this.report.getSqlResource();
        SmartResponse<Object> smartResp = reportSqlResServ.getDatas(sqlResp, params, requestPage.getStartNum(), requestPage.getPageSize());
        while(ISmartWeb.OP_SUCCESS.equals(smartResp.getResult()) && page <= smartResp.getTotalPage()) {
            super.createDataRow(page - 1, smartResp.getDatas(), sheet, titleLength);
            page++;
            if(page <= smartResp.getTotalPage()) {
                requestPage.setPage(page);
                smartResp = reportSqlResServ.getDatas(sqlResp, params, requestPage.getStartNum(), requestPage.getPageSize());
            }
        }
    }
    
}
