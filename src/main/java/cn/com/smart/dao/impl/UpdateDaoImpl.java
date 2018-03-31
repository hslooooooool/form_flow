package cn.com.smart.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.bean.DateBean;
import cn.com.smart.dao.IUpdateDao;
import cn.com.smart.exception.DaoException;
import cn.com.smart.validate.ExecuteValidator;
import cn.com.smart.validate.ValidateException;
import cn.com.smart.validate.Validator;

/**
 * 更新Dao实现类
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 
 * 2015年8月22日
 * @param <T>
 */
@Repository
public abstract class UpdateDaoImpl<T extends BaseBean> extends DeleteDaoImpl<T> implements IUpdateDao<T> {

	@Override
	public Serializable save(T o) throws DaoException {
		return saveObj(o);
	}

	@Override
	public List<Serializable> save(List<T> list) throws DaoException {
		return saveObj(list);
	}
	
	@Override
	public boolean update(T o) throws DaoException {
		return updateObj(o);
	}

	@Override
	public boolean update(List<T> list) throws DaoException {
		return updateObj(list);
	}
	
	@Override
	public boolean saveOrUpdate(T o) throws DaoException {
		boolean is = false;
		if(null != o) {
			log.info("保存或更新数据ID["+o.getId()+"]");
			if(StringUtils.isEmpty(o.getId())) {
				String prefix = o.getPrefix();
				String idNum = StringUtils.createSerialNum();
				if(StringUtils.isNotEmpty(prefix)) {
					idNum = prefix.toUpperCase()+idNum; 
				}
				o.setId(idNum);
			}
			if(o instanceof DateBean && ((DateBean)o).getCreateTime() == null) {
				((DateBean)o).setCreateTime(new Date());
			}
			Validator validator = new ExecuteValidator(o);
			try {
				log.info("正在验证数据格式...");
			    if(validator.validate()) {
					getSession().saveOrUpdate(o);
					is = true;
					log.info("保存或更新数据ID["+o.getId()+"][成功]");
			    } else {
			    	log.info("数据格式验证[失败]");
			    }
			} catch (ValidateException e) {
				log.info("数据格式验证[失败]----["+e.getMessage()+"]--");
				is = false;
				e.printStackTrace();
				throw new DaoException(e.getLocalizedMessage(), e.getCause());
			} catch (Exception e) {
				e.printStackTrace();
				throw new DaoException(e.getLocalizedMessage(), e.getCause());
			} finally {
				o = null;
				validator = null;
			}
		}
		return is;
	}
	
}
