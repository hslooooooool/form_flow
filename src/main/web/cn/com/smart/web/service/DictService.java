package cn.com.smart.web.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.helper.ObjectTreeHelper;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNDict;
import cn.com.smart.web.cache.impl.DictMemoryCache;
import cn.com.smart.web.dao.impl.DictDao;

/**
 * 
 * @author lmq
 *
 */
@Service("dictServ")
public class DictService extends MgrServiceImpl<TNDict> {

	@Autowired
	private DictDao dictDao;
	@Autowired
	private ObjectTreeHelper objTreeHelper;
	@Autowired
	private DictMemoryCache dictCache;
	
	
	/**
	 * 获取数据字典项
	 * @param busiValue
	 * @param name  按名称搜索
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<Object> getItem(String busiValue,String name) throws ServiceException {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		try {
			List<Object> lists = dictCache.getItems(busiValue, name);
			//如果缓存中没有获取到数据，则从数据库中获取
			if(lists == null) {
				LoggerUtils.debug(logger, "缓存中未获取到[数据字典]数据");
				lists = dictDao.getItems(busiValue,name);
			}
			if(CollectionUtils.isNotEmpty(lists)) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
				smartResp.setTotalNum(lists.size());
				smartResp.setSize(lists.size());
			} else {
				smartResp.setResult(OP_NOT_DATA_SUCCESS);
				smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
			}
			lists = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 获取数据字典项
	 * @param parentBusiValue
	 * @param busiValue
	 * @return
	 * @throws ServiceException
	 */
	public TNDict getDict(String parentBusiValue,String busiValue) throws ServiceException {
		TNDict dict = null;
		try {
			List<TNDict> lists = dictCache.getItems(parentBusiValue);
			//如果缓存中没有获取到数据，则从数据库中获取
			if(CollectionUtils.isEmpty(lists)) {
				LoggerUtils.debug(logger, "缓存中未获取到[数据字典]数据");
				lists = dictDao.getItems(parentBusiValue);
			}
			if(CollectionUtils.isNotEmpty(lists)) {
				for (TNDict tmp: lists) {
					if(tmp.getBusiValue().equals(busiValue)) {
						dict = tmp;
						break;
					}//if
				}//for
			}
			lists = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return dict;
	}
	
	
	/**
	 * 获取数据字典项
	 * @param id
	 * @param name  按名称搜索
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<Object> getItemById(String id,String name) throws ServiceException {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		try {
			List<Object> lists = dictCache.getItemById(id, name);
			//如果缓存中没有获取到数据，则从数据库中获取
			if(lists == null) {
				LoggerUtils.debug(logger, "缓存中未获取到[数据字典]数据");
			    lists = dictDao.getItemById(id,name);
			}
			if(CollectionUtils.isNotEmpty(lists)) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
				smartResp.setTotalNum(lists.size());
				smartResp.setSize(lists.size());
			} else {
				smartResp.setResult(OP_NOT_DATA_SUCCESS);
				smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
			}
			lists = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 获取数据字典项
	 * @param busiValue
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<TNDict> getItems(String busiValue) throws ServiceException {
		SmartResponse<TNDict> smartResp = new SmartResponse<TNDict>();
		try {
			
			List<TNDict> lists = dictCache.getItems(busiValue);
			//如果缓存中没有获取到数据，则从数据库中获取
			if(lists == null) {
				LoggerUtils.debug(logger, "缓存中未获取到[数据字典]数据");
				lists = dictDao.getItems(busiValue);
			}
			if(CollectionUtils.isNotEmpty(lists)) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
				smartResp.setTotalNum(lists.size());
				smartResp.setSize(lists.size());
			} else {
				smartResp.setResult(OP_NOT_DATA_SUCCESS);
				smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
			}
			lists = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	

	@Override
	public SmartResponse<String> save(TNDict dict) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != dict){
				String pId = dict.getParentId();
				TNDict porg = dictCache.find(pId);
				if(null == porg) 
					porg = dictDao.find(pId);
				if (porg != null && porg.getBusiLevel() != null) {
					dict.setBusiLevel(porg.getBusiLevel() + 1); // 添加等级
				} else {
					dict.setBusiLevel(1); // 添加等级
				}
				if(StringUtils.isEmpty(dict.getParentId())) {
					dict.setParentId("0");
				}
				Serializable bid = dictDao.save(dict);
				if(null != bid) {
					dictCache.refreshCache();
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("数据字典添加成功");
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}

	
	@Override
	public SmartResponse<String> update(TNDict dict) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != dict){
				String pId = dict.getParentId();
				TNDict pdict = dictCache.find(pId);
				if(null == pdict) 
					pdict = dictDao.find(pId);
				if (pdict != null && pdict.getBusiLevel() != null) {
					dict.setBusiLevel(pdict.getBusiLevel() + 1); // 添加等级
				} else {
					dict.setBusiLevel(1); // 添加等级
				}
				if(StringUtils.isEmpty(dict.getParentId())) {
					dict.setParentId("0");
				}
				if(dictDao.update(dict)){
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("数据字典修改成功");
					dictCache.refreshCache();
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> delete(String id) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("数据字典删除失败");
		try {
			if(StringUtils.isNotEmpty(id)) {
				if(dictDao.delete(id)){
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("数据字典删除成功");
					dictCache.refreshCache();
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<Object> findObjAll() throws ServiceException {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		smartResp.setResult(OP_NOT_DATA_SUCCESS);
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		try {
			List<Object> objs = dictCache.queryObjAll();
			//如果缓存中没有获取到数据，则从数据库中获取
			if(objs == null) {
				LoggerUtils.debug(logger, "缓存中未获取到[数据字典]数据");
			    objs = dictDao.queryObjAll();
			}
			if(CollectionUtils.isNotEmpty(objs)) {
				objs = objTreeHelper.outPutTree(objs);
				if(null != objs && objs.size()>0) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg(OP_SUCCESS_MSG);
					smartResp.setDatas(objs);
					smartResp.setTotalNum(objs.size());
				}
			}
			objs = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
}
