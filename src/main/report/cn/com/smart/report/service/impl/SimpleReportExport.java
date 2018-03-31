package cn.com.smart.report.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.report.bean.entity.TReport;
import cn.com.smart.report.poi.DefaultWriteExcel;
import cn.com.smart.report.poi.IWriteExcel;
import cn.com.smart.report.service.IReportExport;

/**
 * 简单报表导出实现类
 * @author lmq  2017年10月12日
 * @version 1.0
 * @since 1.0
 */
@Service("simpleExport")
public class SimpleReportExport implements IReportExport {

    private static final Logger logger = LoggerFactory.getLogger(SimpleReportExport.class);
    
    @Autowired
    private ReportService reportServ;
    
    @Override
    public byte[] export(TReport report, Map<String, Object> searchParam) {
        LoggerUtils.debug(logger, "报表列表正在导出成Excel表...");
        IWriteExcel writeExcel = new DefaultWriteExcel(report, searchParam);
        Workbook wb = writeExcel.write();
        byte[] bytes = null;
        if(null != wb) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                wb.write(outputStream);
                bytes = outputStream.toByteArray();
                outputStream.close();
                LoggerUtils.debug(logger, "报表列表导出成Excel表[成功].");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LoggerUtils.error(logger, "报表列表导出成Excel表[失败]--[wb == null].");
        }
        return bytes;
    }

    @Override
    public byte[] export(String reportId, Map<String, Object> searchParam) {
        if(StringUtils.isEmpty(reportId)) {
            throw new NullArgumentException();
        }
        return this.export(reportServ.find(reportId).getData(), searchParam);
    }
    
    
}
