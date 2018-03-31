/**
 * 
 */
package cn.com.smart.web.cache.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.smart.cache.CacheException;
import cn.com.smart.cache.ICache;
import cn.com.smart.cache.ICacheManager;
import cn.com.smart.cache.ICacheManagerAware;
import cn.com.smart.cache.InitCache;
import cn.com.smart.web.bean.entity.TNDict;
import cn.com.smart.web.dao.IDictDao;
import cn.com.smart.web.dao.impl.DictDao;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.StringUtils;

/**
 * 数据字典缓存
 * @author lmq
 * @version 1.0 2015年8月30日
 * @since 1.0
 *
 */
@Component
public class DictMemoryCache implements InitCache,ICacheManagerAware,IDictDao {

	private static final Logger log = Logger.getLogger(DictMemoryCache.class);
	
	public static final String DICT_CACHE = "dictCache";
	
	@Autowired
	private DictDao dictDao;
	
	private ICacheManager cacheManager;
	
	@Override
	public void setCacheManager(ICacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	@Override
	public void initCache() {
		try {
			log.info("正在初始化数据字典....");
			ICache<String, List<TNDict>> dictCaches = cacheManager.getCache(DICT_CACHE);
			List<TNDict> datas = dictDao.findAll();
			if(null != datas && datas.size()>0) {
				dictCaches.put(DICT_CACHE, datas);
			}
			datas = null;
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 从内存缓存中获取数据字典数据
	 * @param busiValue
	 * @param name
	 * @return 返回对象集合
	 */
	@Override
	public List<Object> getItems(String busiValue,String name) {
		List<Object> lists = null;
		try {
			List<TNDict> dicts = getDicts();
			if(null != dicts && dicts.size()>0) {
				String dictId = getIdByValue(dicts, busiValue);
				if(StringUtils.isNotEmpty(dictId)) {
					lists= getItemById(dictId, name);
				}
			}
		} catch (CacheException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	
	/**
	 * 从内存缓存中获取数据字典数据
	 */
	@Override
	public List<TNDict> getItems(String busiValue) {
		List<TNDict> lists = null;
		if(StringUtils.isEmpty(busiValue)) {
			return lists;
		}
		try {
			List<TNDict> dicts = getDicts();
			if(null != dicts && dicts.size()>0) {
				String dictId = getIdByValue(dicts, busiValue);
				if(StringUtils.isNotEmpty(dictId)) {
					lists = new ArrayList<TNDict>();
					for (TNDict dict : dicts) {
						if(dict.getParentId().equals(dictId)) {
							lists.add(dict);
						}//if
					}//for
				}//if
			}
		} catch (CacheException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 从内存缓存中获取数据字典数据
	 */
	@Override
	public List<Object> getItemById(String id, String name) {
		List<Object> lists = null;
		if(StringUtils.isNotEmpty(id)) {
			String[] datas = null;
			lists = new ArrayList<Object>();
			boolean isAdd = false;
			try {
				List<TNDict> dicts = getDicts();
				for (TNDict dict : dicts) {
					isAdd = false;
					if(StringUtils.isNotEmpty(name)) {
						isAdd = id.equals(dict.getParentId()) && dict.getBusiName().contains(name);
					} else {
						isAdd = id.equals(dict.getParentId());
					}
					if(isAdd && YesNoType.YES.getStrValue().equals(dict.getState())) {
						datas = new String[3];
						datas[0] = dict.getBusiValue();
						datas[1] = dict.getBusiName();
						datas[2] = String.valueOf(dict.getSortOrder());
						if(lists.size()==0) {
							lists.add(datas);
						} else {
							Object[] objs = (Object[])lists.get(0);
							if(Integer.parseInt(objs[2].toString()) > dict.getSortOrder().intValue()) {
								lists.add(0, datas);
							} else {
								lists.add(datas);
							}
						}
					}
				}//for
			} catch (CacheException ex) {
				ex.printStackTrace();
			}
		}
		return lists;
	}


	/**
	 * 从内存缓存中获取数据字典数据
	 */
	@Override
	public List<Object> queryObjAll() {
		List<Object> lists = null;
		try {
			List<TNDict> dicts = getDicts();
			List<TNDict> states = getItems("DATA_STATE");
			if(null != dicts && !dicts.isEmpty() && 
					null != states && !states.isEmpty()) {
				lists = new ArrayList<Object>();
				String[] datas = null;
				for (TNDict dict : dicts) {
					datas = new String[8];
					datas[0] = dict.getId();
					datas[1] = dict.getParentId();
					datas[2] = dict.getSortOrder().toString();
					datas[3] = dict.getId();
					datas[4] = dict.getBusiName();
					datas[5] = dict.getBusiValue();
					datas[6] = "";
					for (TNDict state : states) {
						if(dict.getState().equals(state.getBusiValue())) {
							datas[6] = state.getBusiName();
							break;
						}
					}
					datas[7] = dict.getSortOrder().toString();
					lists.add(datas);
				}
				datas = null;
			}
			dicts = null;
			states = null;
		} catch (CacheException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	
	
	/**
	 * 根据ID从内存缓存中获取数据
	 * @param id
	 * @return 返回数据字典对象
	 */
	public TNDict find(String id) {
		TNDict dict = null;
		if(StringUtils.isEmpty(id)) {
			return dict;
		}
		try {
			List<TNDict> dicts = getDicts();
			if(null != dicts && !dicts.isEmpty()) {
				for (TNDict d : dicts) {
					if(d.getId().equals(id)) {
						dict = d;
						break;
					}
				}
			}
		} catch(CacheException ex) {
			ex.printStackTrace();
		}
		return dict;
	}
	
	/**
	 * 通过业务值获取数据字典ID
	 * @param dicts
	 * @param busiValue
	 * @return 数据字典ID
	 */
	private String getIdByValue(List<TNDict> dicts,String busiValue) {
		String dictId = null;
		for (TNDict dict : dicts) {
			if(dict.getBusiValue().equals(busiValue)) {
				dictId = dict.getId();
				break;
			}
		}
		return dictId;
	}
	
	
	/**
	 * 从内存中获取数据字典数据
	 * @return 数据字典实体集合
	 * @throws CacheException
	 */
	private List<TNDict> getDicts() throws CacheException {
		ICache<String, List<TNDict>> dictCaches = cacheManager.getCache(DICT_CACHE);
		return dictCaches.get(DICT_CACHE);
	}


	@Override
	public void refreshCache() {
		log.debug("重新初始化[数据字典]数据...");
		initCache();
	}
	
}
