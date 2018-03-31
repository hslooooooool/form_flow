package cn.com.smart.web.bean.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.validate.Validate;

/**
 * 自定义首页（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Entity
@Table(name="t_n_custom_index")
public class TNCustomIndex extends BaseBeanImpl {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String userId;
	
	/**
	 * 布局方式：支持三种
	 * l-1-1 一行一列
	 * l-1-2 一行两列
	 * l-2-1 两行一列
	 * l-2-2 两行两列
	 * 详情数据字典(首页布局模版--INDEX_LAYOUT_TEMP)
	 */
	private String layout;
	
	private int rows;
	
	private int cols;
	
	private List<TNCusIndexMinWin> cusIndexMinWins;

	@Validate(nullable=false)
	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Validate(nullable=false)
	@Column(name="user_id",length=50,nullable=false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Validate(nullable=false)
	@Column(name="layout",length=255,nullable=false)
	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Column(name="rows")
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Column(name="cols")
	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	@Transient
	public List<TNCusIndexMinWin> getCusIndexMinWins() {
		return cusIndexMinWins;
	}

	public void setCusIndexMinWins(List<TNCusIndexMinWin> cusIndexMinWins) {
		this.cusIndexMinWins = cusIndexMinWins;
	}
	
}
