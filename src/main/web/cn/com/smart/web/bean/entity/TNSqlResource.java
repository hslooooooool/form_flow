package cn.com.smart.web.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mixsmart.enums.YesNoType;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;

/**
 * 定义SQL语句资源实体类
 * @author lmq  2017年10月25日
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_n_sql_resource")
public class TNSqlResource extends BaseBeanImpl implements DateBean {

    /**
     * 
     */
    private static final long serialVersionUID = -6234167658878098775L;

    private String id;
    
    private String resName;
    
    private Boolean isFilter = YesNoType.YES.getValue();
    
    private String sql;
    
    private String descr;
    
    private String userId;
    
    private Date createTime;
    
    /**
     * 上次修改时间
     */
    private Date lastModifyTime;
    
    /**
     * 上次修改人
     */
    private String lastUserId;

    @Id
    @Column(name="id", length=50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name="res_name", length=127, nullable=false)
    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    @Column(name="is_filter", nullable=false)
    public Boolean getIsFilter() {
        return isFilter;
    }

    public void setIsFilter(Boolean isFilter) {
        this.isFilter = isFilter;
    }

    @Column(name="sql_", length=1024, nullable=false)
    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
    
    @Column(name="descr", length = 255, nullable = false)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
    
    @Column(name="user_id", length=50, nullable=false, updatable = false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", updatable = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name="last_user_id", length = 50)
    public String getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(String lastUserId) {
        this.lastUserId = lastUserId;
    }
    
    
}
