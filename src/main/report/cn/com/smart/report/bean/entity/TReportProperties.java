package cn.com.smart.report.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mixsmart.enums.YesNoType;

import cn.com.smart.bean.BaseBeanImpl;

/**
 * 报表属性实体类
 * @author lmq  2017年9月11日
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_report_properties")
public class TReportProperties extends BaseBeanImpl{

    /**
     * 
     */
    private static final long serialVersionUID = 7781993881697028574L;

    private String id;
    
    private String reportId;
    
    /**
     * 是否支持导出功能
     * 1 -- 是;
     * 0 -- 否
     */
    private Integer isImport = YesNoType.NO.getIndex();
    
    /**
     * 是否固定头部
     * 1 -- 是;
     * 0 -- 否
     */
    private Integer isFixedHeader = YesNoType.YES.getIndex();
    
    /**
     * 是否有ID
     * 1 -- 是;
     * 0 -- 否
     */
    private Integer isHasId = YesNoType.YES.getIndex();
    
    /**
     * 如果有ID，是否显示ID值
     * 1 -- 是;
     * 0 -- 否
     */
    private Integer isShowId = YesNoType.NO.getIndex();
    
    /**
     * 是否有复选框；
     * 1 -- 有;
     * 0 -- 无
     */
    private Integer isCheckbox = YesNoType.NO.getIndex();
    
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
    
    @Column(name="is_fixed_header")
    public Integer getIsFixedHeader() {
        return isFixedHeader;
    }

    public void setIsFixedHeader(Integer isFixedHeader) {
        this.isFixedHeader = isFixedHeader;
    }
    
    @Column(name="is_import")
    public Integer getIsImport() {
        return isImport;
    }

    public void setIsImport(Integer isImport) {
        this.isImport = isImport;
    }
    
    @Column(name="is_has_id")
    public Integer getIsHasId() {
        return isHasId;
    }

    public void setIsHasId(Integer isHasId) {
        this.isHasId = isHasId;
    }

    @Column(name="is_show_id")
    public Integer getIsShowId() {
        return isShowId;
    }

    public void setIsShowId(Integer isShowId) {
        this.isShowId = isShowId;
    }

    @Column(name="is_checkbox")
    public Integer getIsCheckbox() {
        return isCheckbox;
    }

    public void setIsCheckbox(Integer isCheckbox) {
        this.isCheckbox = isCheckbox;
    }
    
}
