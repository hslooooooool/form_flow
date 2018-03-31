package cn.com.smart.web.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mixsmart.security.SecurityUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.bean.TreeProp;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.helper.ObjectHelper;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNOrg;
import cn.com.smart.web.bean.entity.TNPosition;
import cn.com.smart.web.bean.entity.TNRoleUser;
import cn.com.smart.web.bean.entity.TNUser;
import cn.com.smart.web.constant.enums.OrgType;
import cn.com.smart.web.dao.impl.OrgDao;
import cn.com.smart.web.dao.impl.PositionDao;
import cn.com.smart.web.dao.impl.RoleUserDao;
import cn.com.smart.web.dao.impl.UserDao;
import cn.com.smart.web.filter.bean.UserSearchParam;
import cn.com.smart.web.helper.TreeCombinHelper;
import cn.com.smart.web.plugins.OrgUserZTreeData;
import cn.com.smart.web.plugins.ZTreeHelper;

/**
 * 
 * @author lmq
 *
 */
@Service
public class UserService extends MgrServiceImpl<TNUser> {
	
    @Autowired
	private UserDao userDao;
    @Autowired
	private OrgDao orgDao;
    @Autowired
	private RoleUserDao roleUserDao;
    @Autowired
    private TreeCombinHelper treeCombinHelper;
    @Autowired
	private OrgService orgServ;
    @Autowired
    private PositionDao posDao;
	
	/**
	 * 根据searchParam参数查询用户信息
	 * @param searchParam
	 * @param start
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<Object> findAllObj(UserSearchParam searchParam,int start,int rows) throws ServiceException {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		smartResp.setResult(OP_NOT_DATA_SUCCESS);
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		try {
			List<Object> objs = userDao.queryObjPage(searchParam, start, rows);
			if(null != objs && objs.size()>0) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				objs = ObjectHelper.handleObjDate(objs);
				smartResp.setDatas(objs);
				long total = userDao.queryObjCount(searchParam);
				smartResp.setTotalNum(total);
				smartResp.setTotalPage(getTotalPage(total, rows));
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	

	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> save(TNUser user) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != user) {
				String userName = user.getUsername()==null?"":user.getUsername();
				if(userDao.isExistUsername(userName)){
					smartResp.setResult(OP_FAIL);
					smartResp.setMsg("该用户名已存在，不能在注册！");
					return smartResp;
				}
				user.setPassword(SecurityUtils.md5(user.getPassword()));
				Serializable  id = userDao.save(user);
				if(null != id) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("用户添加成功");
					smartResp.setData(id.toString());
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
	public SmartResponse<String> update(TNUser user) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != user) {
				TNUser oldUser = userDao.queryUser(user.getUsername());
				if(null != oldUser && !oldUser.getId().equals(user.getId())){//如果用户名被修改了 ，就需要验证新名字是否冲突
					smartResp.setResult(OP_FAIL);
					smartResp.setMsg("该用户名已存在，不能在注册！");
				} else {
					boolean is = false;
					if(null != oldUser && oldUser.getId().equals(user.getId())) {
						oldUser.setUsername(user.getUsername());
						oldUser.setEmail(user.getEmail());
						oldUser.setFullName(user.getFullName());
						oldUser.setMobileNo(user.getMobileNo());
						oldUser.setOrgId(user.getOrgId());
						oldUser.setPositionId(user.getPositionId());
						oldUser.setQq(user.getQq());
						oldUser.setRemark(user.getRemark());
						oldUser.setState(user.getState());
						is = userDao.update(oldUser);
					} else {
						is = userDao.update(user);
					}
					if(is) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg("用户信息修改成功");
						smartResp.setData(user.getId());
					}
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
	 * 删除用户信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> del(String id) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != id) {
				if(userDao.delete(id)){
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("用户数据删除成功");
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
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<UserInfo> login(String username,String password) throws ServiceException {
		SmartResponse<UserInfo> smartResp = new SmartResponse<UserInfo>();
		try {
			if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
				String md5Pwd = SecurityUtils.md5(password);
				TNUser user = userDao.queryLogin(username, md5Pwd);
				if(null != user) {
					UserInfo userInfo = getUserInfo(user);
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg(OP_SUCCESS_MSG);
					smartResp.setData(userInfo);
				} else {
					LoggerUtils.info(logger, "用户名或密码错误--输入用户名["+username+"]---");
				}
			} else {
				LoggerUtils.info(logger, "用户名或密码为空--");
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	/**
	 * 获取用户信息 
	 * @param user 用户实体对象
	 * @return 返回用户信息
	 */
	public UserInfo getUserInfo(TNUser user) {
	    UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setFullName(user.getFullName());
        TNOrg org =  orgDao.find(user.getOrgId());
        String deptName = null;
        String seqDeptName = null;
        if(null != org) {
            deptName = org.getName();
            seqDeptName = org.getSeqNames();
            userInfo.setOrgId(org.getId());
            if(OrgType.DEPARTMENT.getValue().equals(org.getType())) {
                userInfo.setDepartmentId(org.getId());
            }
        } else {
            userInfo.setOrgId(user.getOrgId());
        }
        userInfo.setPositionId(user.getPositionId());
        if(StringUtils.isNotEmpty(user.getPositionId())){
            TNPosition position = posDao.find(user.getPositionId());
            if(null != position) {
                userInfo.setPositionName(position.getName());
            }
            position = null;
        }
        userInfo.setDeptName(deptName);
        userInfo.setSeqDeptNames(seqDeptName);
        userInfo.setMenuRoleIds(userDao.queryMenuRoleIds(user.getId()));
        userInfo.setRoleIds(userDao.queryRoleIds(user.getId()));
        userInfo.setOrgIds(userDao.queryOrgIds(user.getId()));
        return userInfo;
	}
	
	/**
	 * 批量修改密码
	 * @param userIds
	 * @param pwd
	 * @param confirmPwd
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public SmartResponse<String> batchChangePwd(String userIds,String pwd,String confirmPwd) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(StringUtils.isNotEmpty(userIds) && StringUtils.isNotEmpty(pwd) && StringUtils.isNotEmpty(confirmPwd)) {
				if(pwd.equals(confirmPwd)) {
					String[] userIdArray = userIds.split(",");
					pwd = SecurityUtils.md5(pwd);
					if(userDao.batchChangePwd(userIdArray, pwd)) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg("密码修改成功");
					} else 
						smartResp.setMsg("密码修改失败");
					userIdArray = null;
				} else {
					smartResp.setMsg("两次输入的密码不一致");
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
	 * 修改密码
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 * @param confirmNewPwd
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> changePwd(String userId,String oldPwd,String newPwd,String confirmNewPwd) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(oldPwd) 
					&& StringUtils.isNotEmpty(newPwd) && StringUtils.isNotEmpty(confirmNewPwd)) {
				if(newPwd.equals(confirmNewPwd)) {
					TNUser user = userDao.find(userId);
					if(null != user) {
						String md5Pwd = SecurityUtils.md5(oldPwd);
						if(user.getPassword().equals(md5Pwd)) {
							String md5NewPwd = SecurityUtils.md5(newPwd);
							if(userDao.changePwd(userId, md5NewPwd)) {
								smartResp.setResult(OP_SUCCESS);
								smartResp.setMsg("密码修改成功");
								smartResp.setData(null);
							} else {
								smartResp.setResult(OP_FAIL);
								smartResp.setMsg("密码修改失败");
							}
						} else {
							smartResp.setMsg("旧密码输入错误");
							smartResp.setData("2");
						}
					} else {
						smartResp.setMsg("用户不存在");
					}
				} else {
					smartResp.setMsg("两次输入的密码不一致");
					smartResp.setData("1");
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
	 * 用户中添加角色
	 * @param userId
	 * @param roleIds
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> addRole2User(String userId,String[] roleIds) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(StringUtils.isNotEmpty(userId) && null != roleIds && roleIds.length>0) {
				if(!roleUserDao.isRoleInUserExist(userId, roleIds)) {
					List<TNRoleUser> roleUsers = new ArrayList<TNRoleUser>();
					TNRoleUser roleUser = null;
					for (int i = 0; i < roleIds.length; i++) {
						roleUser = new TNRoleUser();
						roleUser.setRoleId(roleIds[i]);
						roleUser.setUserId(userId);
						roleUsers.add(roleUser);
					}
					List<Serializable> ids = roleUserDao.save(roleUsers);
					if(null != ids && ids.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
					}
					ids = null;
					roleUser = null;
					roleUsers = null;
				} else {
					smartResp.setMsg("角色已添加到用户里面，不能重复添加！");
				}
				roleIds = null;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 获取组织机构用户树
	 * @param orgIds
	 * @param userId 过滤用户
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<OrgUserZTreeData> getOrgUserZTree(List<String> orgIds,String userId) throws ServiceException {
		SmartResponse<OrgUserZTreeData> smartResp = new SmartResponse<OrgUserZTreeData>();
		List<TreeProp> ztreeProps = orgServ.getTree(orgIds);
		if(null != ztreeProps && ztreeProps.size()>0) {
			String[] orgIdArray = new String[ztreeProps.size()];
			int count = 0;
			for(TreeProp treeProp : ztreeProps) {
				orgIdArray[count] = treeProp.getId();
				count++;
			}
			try {
				List<TNUser> users = userDao.queryByOrgIds(orgIdArray);
				if(null != users && users.size()>0) {
					TreeProp treeProp = null;
					List<TreeProp> newTreeProp = new ArrayList<TreeProp>();
					count = 1;
					for(int i = 0; i < ztreeProps.size(); i++) {
						newTreeProp.add(ztreeProps.get(i));
						count++;
						for(TNUser userTmp : users) {
							if(ztreeProps.get(i).getId().equals(userTmp.getOrgId()) && !userId.equals(userTmp.getId())) {
								treeProp = new TreeProp();
								treeProp.setFlag("user");
								treeProp.setId(userTmp.getId());
								String fullName = userTmp.getFullName();
								if(StringUtils.isEmpty(fullName))
									fullName = userTmp.getUsername();
								treeProp.setName(fullName);
								treeProp.setSortOrder(count);
								treeProp.setParentId(ztreeProps.get(i).getId());
								newTreeProp.add(treeProp);
								count++;
							}
						}
					}//for;
					treeProp = null;
					ztreeProps = null;
					users = null;
					
					ZTreeHelper<OrgUserZTreeData> zTreeHelper = new ZTreeHelper<OrgUserZTreeData>(OrgUserZTreeData.class, newTreeProp);
					List<OrgUserZTreeData> ztreeDatas = zTreeHelper.convert("user");
					zTreeHelper = null;
					if(null != ztreeDatas && ztreeDatas.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(ztreeDatas);
					}
					ztreeDatas = null;
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			} finally {
				orgIdArray = null;
			}
		}
		return smartResp;
	}
	
	/**
	 * 获取组织机构用户树
	 * @param orgIds
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<OrgUserZTreeData> getOrgUserZTree(List<String> orgIds) throws ServiceException {
		SmartResponse<OrgUserZTreeData> smartResp = new SmartResponse<OrgUserZTreeData>();
		List<TreeProp> ztreeProps = orgServ.getTree(orgIds);
		if(null != ztreeProps && ztreeProps.size()>0) {
			String[] orgIdArray = new String[ztreeProps.size()];
			int count = 0;
			for(TreeProp treeProp : ztreeProps) {
				orgIdArray[count] = treeProp.getId();
				count++;
			}
			try {
				smartResp = getOrgUserZTree(ztreeProps, userDao.queryByOrgIds(orgIdArray));
			} catch (DaoException e) {
				throw new ServiceException(e);
			} finally {
				orgIdArray = null;
			}
		}
		return smartResp;
	}
	
	/**
	 * 获取组织机构用户树
	 * @param users
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<OrgUserZTreeData> getOrgUserZTreeByUser(List<TNUser> users) throws ServiceException {
		SmartResponse<OrgUserZTreeData> smartResp = new SmartResponse<OrgUserZTreeData>();
		if(null != users && users.size()>0) {
			Set<String> orgIds = new HashSet<String>();
			for(TNUser user : users) {
				orgIds.add(user.getOrgId());
			}
			List<TreeProp> ztreeProps = orgServ.getTree(null);
			try {
				smartResp = getOrgUserZTree(ztreeProps, users);
			} catch (ServiceException e) {
				throw new ServiceException(e);
			} finally {
				orgIds = null;
			}
		}
		return smartResp;
	}
	
	/**
	 * 
	 * @param ztreeProps
	 * @param users
	 * @return
	 * @throws ServiceException
	 */
	private SmartResponse<OrgUserZTreeData> getOrgUserZTree(List<TreeProp> ztreeProps,List<TNUser> users) throws ServiceException {
		SmartResponse<OrgUserZTreeData> smartResp = new SmartResponse<OrgUserZTreeData>();
		if(null != users && users.size()>0  && null != ztreeProps && ztreeProps.size()>0) {
			int count = 0;
			TreeProp treeProp = null;
			List<TreeProp> newTreeProp = new ArrayList<TreeProp>();
			count = 1;
			for(int i = 0; i < ztreeProps.size(); i++) {
				newTreeProp.add(ztreeProps.get(i));
				count++;
				for(TNUser userTmp : users) {
					if(ztreeProps.get(i).getId().equals(userTmp.getOrgId())) {
						treeProp = new TreeProp();
						treeProp.setFlag("user");
						treeProp.setId(userTmp.getId());
						String fullName = userTmp.getFullName();
						if(StringUtils.isEmpty(fullName))
							fullName = userTmp.getUsername();
						treeProp.setName(fullName);
						treeProp.setSortOrder(count);
						treeProp.setParentId(ztreeProps.get(i).getId());
						newTreeProp.add(treeProp);
						count++;
					}
				}
			}//for;
			treeProp = null;
			ztreeProps = null;
			users = null;
			ZTreeHelper<OrgUserZTreeData> zTreeHelper = new ZTreeHelper<OrgUserZTreeData>(OrgUserZTreeData.class, newTreeProp);
			List<OrgUserZTreeData> ztreeDatas = zTreeHelper.convert("user");
			zTreeHelper = null;
			treeCombinHelper.trimLeaf(ztreeDatas);
			if(null != ztreeDatas && ztreeDatas.size()>0) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(ztreeDatas);
			}
			ztreeDatas = null;
		}
		return smartResp;
	}

	@Override
	public UserDao getDao() {
		return (UserDao)super.getDao();
	}
	
	/**
	 * 从用户中删除角色
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public SmartResponse<String> deleteRole(String userId, String roleId) {
	    SmartResponse<String> smartResp = new SmartResponse<String>();
	    smartResp.setMsg("删除失败");
	    if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(roleId)) {
	        return smartResp;
	    }
	    Map<String, Object> param = new HashMap<String, Object>(3);
	    param.put("userId", userId);
	    param.put("id", roleId);
	    param.put("flag", "u");
	    if(roleUserDao.delete(param)) {
	        smartResp.setResult(OP_SUCCESS);
	        smartResp.setMsg("删除成功");
	    }
	    return smartResp;
	}
}
