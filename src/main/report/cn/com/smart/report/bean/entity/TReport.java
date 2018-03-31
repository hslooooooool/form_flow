package cn.com.smart.report.bean.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;

/**
 * 报表实体类
 * @author lmq  2017年9月10日
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_report")
public class TReport extends BaseBeanImpl implements DateBean {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4538820744371430653L;

    private String id;
    
    private String name;
    
    /**
     * 报表类型
     */
    private String type;
    
    private String userId;
    
    private Date createTime;
    
    private TReportProperties properties;
    
    private List<TReportField> fields;
    
    private TReportSqlResource sqlResource;

    @Id
    @Column(name="id", length=50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name="name", length=255, nullable=false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="type", length=127, nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name="user_id", length=50, nullable = false, updatable = false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        return "R";
    }

    @Transient
    public TReportProperties getProperties() {
        return properties;
    }

    public void setProperties(TReportProperties properties) {
        this.properties = properties;
    }

    @Transient
    public List<TReportField> getFields() {
        return fields;
    }

    public void setFields(List<TReportField> fields) {
        this.fields = fields;
    }
    
    @Transient
    public TReportSqlResource getSqlResource() {
        return sqlResource;
    }

    public void setSqlResource(TReportSqlResource sqlResource) {
        this.sqlResource = sqlResource;
    }
}
