package cn.com.smart.report.enums;

/**
 * 报表类型
 * @author lmq  2017年9月10日
 * @version 1.0
 * @since 1.0
 */
public enum ReportType {

    /**
     * 流程报表
     */
    FLOW_REPORT(0,"flow_report"),
    /**
     * 普通表单报表
     */
    FORM_REPORT(1,"form_report"),
    
    /**
     * 自定义SQL语句
     */
    CUSTOM_SQL_REPORT(2,"custom_sql_report"),
    
    /**
     * 其他报表
     */
    OTHER_REPORT(9, "other_report");
    
    private int index;
    private String value;
    
    private ReportType(int index,String value) {
        this.index = index;
        this.value = value;
    }

    /**
     * 根据 <code>index</code>参数 获取报表类型对象
     * @param index 
     * @return
     */
    public static ReportType getObj(int index) {
        ReportType reportType = null;
        for (ReportType tmp : ReportType.values()) {
            if(tmp.getIndex() == index) {
                reportType = tmp;
                break;
            }
        }
        return reportType;
    }
    
    /**
     * 根据值获取报表类型对象
     * @param value 值
     * @return 返回报表类型对象
     */
    public static ReportType getObj(String value) {
        ReportType reportType = null;
        for (ReportType tmp : ReportType.values()) {
            if(tmp.getValue().equals(value)) {
                reportType = tmp;
                break;
            }
        }
        return reportType;
    }
    
    /*****getter and setter****/
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
}
