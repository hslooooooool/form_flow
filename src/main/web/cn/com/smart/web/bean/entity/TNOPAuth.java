package cn.com.smart.web.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;
import cn.com.smart.validate.Validate;

import com.mixsmart.enums.YesNoType;

/**
 * 操作权限 （实体Bean）
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Entity
@Table(name="t_n_op_auth")
public class TNOPAuth extends BaseBeanImpl implements DateBean, Cloneable {

	private static final long serialVersionUID = -8984219933222527963L;

	private String id;
	
	private String value;
	
	private String name;
	
	private Date createTime;
	
	private Double sortOrder;
	
	/**
	 * 是否选中(该属性与数据库字段无关联)
	 * 1--选中
	 * 0--未选中
	 */
	private int isChecked = YesNoType.NO.getIndex();

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Validate(nullable=false,regexExpr="\\w+")
	@Column(name="value",length=50,nullable=false,unique=true)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name="name",length=255,nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time",updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="sort_order")
	public Double getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Double sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Override
	@Transient
	public String getPrefix() {
		return "a";
	}

	@Transient
	public int getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}

	@Override
	public TNOPAuth clone() {
		TNOPAuth opAuth = null;
		try {
			opAuth = (TNOPAuth) super.clone();
		} catch(CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return opAuth;
	}
}
