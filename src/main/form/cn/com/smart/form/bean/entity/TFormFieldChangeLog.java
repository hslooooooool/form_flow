package cn.com.smart.form.bean.entity;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;
import javax.persistence.*;
import java.util.Date;

/**
 *
 * 表单字段修改记录
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_form_field_change_log")
public class TFormFieldChangeLog extends BaseBeanImpl implements DateBean {

    /**
     * 
     */
    private static final long serialVersionUID = 4049404840509581244L;

    private String id;

    private String tableFieldId;

    private String formId;

    /**
     * 原来的值
     */
    private String sourceValue;

    /**
     * 修改后的值
     */
    private String value;

    private String formDataId;

    private Date createTime;

    private String userId;

    private String flag;

    /**
     * 备注
     */
    private String remarks;

    @Id
    @Column(name="id", length = 50)
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Column(name="table_field_id", length = 50, nullable = false)
    public String getTableFieldId() {
        return tableFieldId;
    }

    public void setTableFieldId(String tableFieldId) {
        this.tableFieldId = tableFieldId;
    }

    @Column(name = "form_id", length = 50, nullable = false)
    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    @Column(name="source_value", length = 255)
    public String getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(String sourceValue) {
        if(null != sourceValue && sourceValue.length() > 250) {
            sourceValue = sourceValue.substring(0,250)+"...";
        }
        this.sourceValue = sourceValue;
    }

    @Column(name="value", length = 255)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if(null != value && value.length() > 250) {
            value = value.substring(0,250)+"...";
        }
        this.value = value;
    }

    @Column(name="form_data_id", length = 50, nullable = false)
    public String getFormDataId() {
        return formDataId;
    }

    public void setFormDataId(String formDataId) {
        this.formDataId = formDataId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time")
    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name="user_id", length = 50, nullable = false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name="flag",length = 50)
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name="remarks", length = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
