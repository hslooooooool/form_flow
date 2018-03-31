package cn.com.smart.report.poi;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * 写Excel接口
 * @author lmq  2017年10月14日
 * @version 1.0
 * @since 1.0
 */
public interface IWriteExcel {
    /**
     * 数据写到excel中
     * @return 返回Workbook对象
     */
    Workbook write();
    
}
