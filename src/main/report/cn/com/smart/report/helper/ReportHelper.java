package cn.com.smart.report.helper;

import java.util.List;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.report.bean.entity.TReportField;
import cn.com.smart.report.bean.entity.TReportProperties;

public class ReportHelper {

    /**
     * 获取头标题
     * @param reportProp 报表属性实体类
     * @param fields 字段实体对象列表
     * @return 返回标题，多个之间用英文逗号分隔
     */
    public static String getHeaderTitles(TReportProperties reportProp, List<TReportField> fields) {
        StringBuilder titleBuilder = new StringBuilder();
        if(YesNoType.YES.getIndex() == reportProp.getIsHasId() && 
                YesNoType.YES.getIndex() == reportProp.getIsShowId()) {
            titleBuilder.append("ID,");
        }
        for (TReportField reportField : fields) {
            titleBuilder.append(reportField.getTitle()).append(",");
        }
        titleBuilder.delete(titleBuilder.length()-1, titleBuilder.length());
        return titleBuilder.toString();
    }
    
    /**
     * 获取字段宽度
     * @param fields 报表字段列表
     * @return 返回字段宽度；多个宽度之间用英文逗号分隔
     */
    public static String getWidths(List<TReportField> fields) {
        StringBuilder widthBuilder = new StringBuilder();
        for (TReportField reportField : fields) {
            widthBuilder.append(StringUtils.handleNull(reportField.getWidth())).append(",");
        }
        widthBuilder.delete(widthBuilder.length()-1, widthBuilder.length());
        if(!widthBuilder.toString().matches(".*\\d+.*")) {
            return null;
        } else {
            return widthBuilder.toString();
        }
    }
}
