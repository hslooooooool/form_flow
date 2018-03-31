package cn.com.smart.web.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNCusIndexMinWin;

/**
 * 
 * @author lmq
 *
 */
@Repository
public class CusIndexMinWinDao extends BaseDaoImpl<TNCusIndexMinWin> {
	
	private SqlMapping sqlMap;
	private Map<String, Object> params;
	
	public CusIndexMinWinDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}
	
	/**
	 * 
	 * @param cusIndexId
	 * @return
	 */
	public List<TNCusIndexMinWin> queryByCusIndex(String cusIndexId) throws DaoException  {
		List<TNCusIndexMinWin> lists = null;
		String sql = sqlMap.getSQL("cus_index_minwin");
		if(StringUtils.isNotEmpty(sql)) {
			params = new HashMap<String, Object>();
			params.put("cusIndexId", cusIndexId);
			lists = queryHql(sql, params);
			params = null;
		} else {
			throw new DaoException("[cus_index_minwin]值为空");
		}
		return lists;
	}

}
