package cn.com.smart.report.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.bean.DateBean;

/**
 * 报表字段实体类
 * @author lmq  2017年9月10日
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_report_field")
public class TReportField implements BaseBean, DateBean {
    
    /**
     * 
     */
    private static final long serialVersionUID = 4789667416867643754L;

    private String id;
    
    private String reportId;
    
    private String title;
    
    private String width;
    
    private String url;
    
    private String openUrlType;
    
    private String paramName;
    
    private String paramValue;
    
    private String searchName;
    
    private String customClass;
    
    private Integer sortOrder;
    
    private Date createTime;

    @Id
    @Column(name="id", length=50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name="report_id", length=50, nullable=false)
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @Column(name="title", length=127, nullable=false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="width", length = 50)
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Column(name="url", length = 255)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name="open_url_type", length = 127)
    public String getOpenUrlType() {
        return openUrlType;
    }

    public void setOpenUrlType(String openUrlType) {
        this.openUrlType = openUrlType;
    }

    @Column(name="param_name", length=255)
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Column(name="param_value", length = 127)
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Column(name="search_name", length=127)
    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    @Column(name="sort_order")
    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    @Column(name="custom_class", length=255)
    public String getCustomClass() {
        return customClass;
    }

    public void setCustomClass(String customClass) {
        this.customClass = customClass;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", updatable=false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    @Transient
    public String getPrefix() {
        return "RF";
    }
}
