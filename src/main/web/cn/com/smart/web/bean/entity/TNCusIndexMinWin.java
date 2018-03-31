package cn.com.smart.web.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseBeanImpl;

/**
 * 用户自定义小窗口（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Entity
@Table(name="t_n_cus_index_min_win")
public class TNCusIndexMinWin extends BaseBeanImpl {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String cusIndexId;
	
	private String minWinId;
	
	private Double sortOrder;
	
	private TNMinWindow minWin;

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="min_win_id",length=50,nullable=false)
	public String getMinWinId() {
		return minWinId;
	}

	public void setMinWinId(String minWinId) {
		this.minWinId = minWinId;
	}

	@Column(name="sort_order")
	public Double getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Double sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name="cus_index_id",length=50,nullable=false)
	public String getCusIndexId() {
		return cusIndexId;
	}

	public void setCusIndexId(String cusIndexId) {
		this.cusIndexId = cusIndexId;
	}

	@Transient
	public TNMinWindow getMinWin() {
		return minWin;
	}

	public void setMinWin(TNMinWindow minWin) {
		this.minWin = minWin;
	}
	
}
