package cn.com.smart.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.helper.ObjectHelper;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNOPAuth;
import cn.com.smart.web.bean.entity.TNRoleResource;
import cn.com.smart.web.cache.impl.OPAuthMemoryCache;
import cn.com.smart.web.cache.impl.ResourceMemoryCache;
import cn.com.smart.web.cache.impl.RoleResourceMemoryCache;
import cn.com.smart.web.dao.impl.OPAuthDao;
import cn.com.smart.web.dao.impl.RoleResourceDao;
import cn.com.smart.web.tag.bean.BaseBtn;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 
 * @author lmq
 *
 */
@Service("opAuthServ")
public class OPAuthService extends MgrServiceImpl<TNOPAuth> {

	@Autowired
	private OPAuthDao opAuthDao;
	@Autowired
	private RoleResourceDao roleResDao;
	
	@Autowired
	private OPAuthMemoryCache authCache;
	@Autowired
	private ResourceMemoryCache resCache;
	@Autowired
	private RoleResourceMemoryCache roleResCache;
	
	/**
	 * 获取按钮列表
	 * @param start
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<Object> findAll(int start,int rows) throws ServiceException {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		try {
			List<Object> lists = opAuthDao.queryAuthAll(start, rows);
			if(null != lists && lists.size()>0) {
				//处理时间
				lists = ObjectHelper.handleObjDate(lists);
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
				long totalNum = opAuthDao.countAuthAll();
				smartResp.setTotalNum(totalNum);
				smartResp.setTotalPage(getTotalPage(totalNum, rows));
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
	 * 获取全部按钮
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<TNOPAuth> findAll() throws ServiceException {
		SmartResponse<TNOPAuth> smartResp = new SmartResponse<TNOPAuth>();
		try {
			List<TNOPAuth> lists = authCache.queryAll();
			//如果缓存中没有获取到数据，则从数据库中获取
			if(lists == null) {
				LoggerUtils.debug(logger, "缓存中未获取到[操作权限]数据");
				lists = findAll().getDatas();
			}
			if(null != lists && lists.size()>0) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
			} else {
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
	 * 获取全部按钮，如果有则选中
	 * @param values
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<TNOPAuth> findAuth(String[] values) throws ServiceException {
		SmartResponse<TNOPAuth> smartResp = new SmartResponse<TNOPAuth>();
		try {
			if(null != values && values.length>0) {
				//获取所有按钮
				List<TNOPAuth> all = authCache.queryAll();
				//如果缓存中没有获取到数据，则从数据库中获取
				if(all == null) {
					LoggerUtils.debug(logger, "缓存中未获取到[操作权限]数据");
					all = super.findAll().getDatas();
				}
				List<TNOPAuth> newOpAuths = new ArrayList<TNOPAuth>();
				//获取与该资源关联的按钮
				for (TNOPAuth noCheckBtn : all) {
					TNOPAuth newOpAuth = noCheckBtn.clone();
					//newOpAuth.setIsChecked(YesNoType.NO.getIndex());
					for (int i=0;i<values.length;i++) {
						if(noCheckBtn.getValue().equals(values[i])) {
							newOpAuth.setIsChecked(YesNoType.YES.getIndex());
							break;
						}
					}
					newOpAuths.add(newOpAuth);
				}
				all = null;
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(newOpAuths);
				newOpAuths = null;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 保存
	 * @param auth
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> save(TNOPAuth auth) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != auth) {
				if(OP_SUCCESS.equals(super.save(auth).getResult())) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("按钮添加成功");
					authCache.refreshCache();
				} else {
					smartResp.setMsg("按钮添加失败");
				}
			}
			auth = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 更新
	 * @param auth
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> update(TNOPAuth auth) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != auth) {
				if(OP_SUCCESS.equals(super.update(auth).getResult())) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("按钮修改成功");
					//刷新缓存
					authCache.refreshCache();
					resCache.refreshCache();
					roleResCache.refreshCache();
				} else {
					smartResp.setMsg("按钮修改失败");
				}
			}
			auth = null;
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
	 */
	public SmartResponse<String> delete(String id) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("操作权限删除失败");
		if(StringUtils.isNotEmpty(id)) {
			try{
				if(OP_SUCCESS.equals(super.delete(id).getResult())) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("删除成功");
					//刷新缓存
					authCache.refreshCache();
					resCache.refreshCache();
					roleResCache.refreshCache();
				} 
			} catch(DaoException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return smartResp;
	}
	
	/**
	 * 判断是否有权限
	 * @param currentUri
	 * @param btn
	 * @param roleIds
	 * @return
	 * @throws ServiceException
	 */
	public boolean isAuth(String currentUri,BaseBtn btn,List<String> roleIds) throws ServiceException {
		boolean is = false;
		try {
			if(StringUtils.isNotEmpty(currentUri) && null != btn && null != roleIds && roleIds.size()>0) {
				List<TNRoleResource> lists = roleResCache.queryByUriRoles(currentUri, roleIds);
				if(null == lists)
					lists = roleResDao.queryByUriRoles(currentUri, roleIds);
				if(null != lists && lists.size()>0) {
					for (TNRoleResource roleRes : lists) {
						if(StringUtils.isNotEmpty(roleRes.getOpAuths()) && 
								ArrayUtils.isArrayContains(roleRes.getOpAuths(), btn.getId(), ",")) {
							is = true;
							break;
						}
					}
				}
				lists = null;
				roleIds = null;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return is;
	}
}
