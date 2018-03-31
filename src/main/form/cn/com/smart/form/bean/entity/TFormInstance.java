package cn.com.smart.form.bean.entity;

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
 * 表单实例 实体；
 * 注：该实体中，只会保存普通表单的数据（不含流程表单数据）；
 * @author lmq  2017年8月27日
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_form_instance")
public class TFormInstance implements BaseBean, DateBean {

    /**
     * 
     */
    private static final long serialVersionUID = -7221494669741691488L;

    private String id;
    
    private String formId;
    
    private String formDataId;
    
    private String title;
    
    private String userId;
    
    private String orgId;
    
    private Date createTime;

    @Id
    @Column(name="id", length=50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="form_id", length=50, nullable=false)
    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    @Column(name="form_data_id", length=50, nullable=false)
    public String getFormDataId() {
        return formDataId;
    }

    public void setFormDataId(String formDataId) {
        this.formDataId = formDataId;
    }

    @Column(name="title", length=255, nullable=false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="user_id", length=50, nullable=false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name="org_id", length=50, nullable=false)
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", updatable=false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Transient
    @Override
    public String getPrefix() {
        return "FI";
    }
    
}
