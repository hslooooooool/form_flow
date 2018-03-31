package cn.com.smart.web.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.bean.TreeProp;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNOrg;
import cn.com.smart.web.bean.entity.TNPosition;
import cn.com.smart.web.bean.entity.TNRolePosition;
import cn.com.smart.web.dao.impl.OrgDao;
import cn.com.smart.web.dao.impl.PositionDao;
import cn.com.smart.web.dao.impl.RolePositionDao;
import cn.com.smart.web.helper.TreeCombinHelper;
import cn.com.smart.web.plugins.OrgPositionZTreeData;
import cn.com.smart.web.plugins.ZTreeHelper;

import com.mixsmart.utils.StringUtils;

/**
 * 
 * @author lmq
 *
 */
@Service("positionServ")
public class PositionService extends MgrServiceImpl<TNPosition> {

	@Autowired
	private OrgDao orgDao;
	@Autowired
	private RolePositionDao rolePosDao;
	@Autowired
	private OrgService orgServ;
	@Autowired
    private TreeCombinHelper treeCombinHelper;
	@Autowired
	private PositionDao positionDao;
	
	
	@Override
	public SmartResponse<String> save(TNPosition position) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != position) {
				TNOrg org = orgDao.find(position.getOrgId());
				if(null != org) {
					position.setSeqName(org.getSeqNames()+">"+position.getName());
				}
				org = null;
				smartResp = super.save(position);
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
	 * @param position
	 * @param roleId
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> save(TNPosition position,String roleId) throws ServiceException {
		SmartResponse<String> smartResp = this.save(position);
		try {
			if(smartResp.getResult().equals(OP_SUCCESS) && StringUtils.isNotEmpty(roleId)) {
				TNRolePosition rplink = new TNRolePosition();
				rplink.setPositionId(smartResp.getData().toString());
				rplink.setRoleId(roleId);
				rolePosDao.save(rplink);
				rplink = null;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	@Override
	public SmartResponse<String> update(TNPosition position) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != position) {
				TNOrg org = orgDao.find(position.getOrgId());
				if(null != org) {
					position.setSeqName(org.getSeqNames()+">"+position.getName());
				}
				org = null;
				smartResp = super.update(position);
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	/**
	 * 岗位中添加角色
	 * @param positionId
	 * @param roleIds
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> addRole2Position(String positionId,String[] roleIds) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(StringUtils.isNotEmpty(positionId) && null != roleIds && roleIds.length>0) {
				if(!rolePosDao.isRoleInPositionExist(positionId, roleIds)) {
					List<TNRolePosition> rolePositions = new ArrayList<TNRolePosition>();
					TNRolePosition rolePosition = null;
					for (int i = 0; i < roleIds.length; i++) {
						rolePosition = new TNRolePosition();
						rolePosition.setRoleId(roleIds[i]);
						rolePosition.setPositionId(positionId);
						rolePositions.add(rolePosition);
					}
					List<Serializable> ids = rolePosDao.save(rolePositions);
					if(null != ids && ids.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
					}
					ids = null;
					rolePosition = null;
					rolePositions = null;
				} else {
					smartResp.setMsg("角色已添加到该岗位里面，不能重复添加！");
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
	 * 获取组织机构岗位树
	 * @param orgIds
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<OrgPositionZTreeData> getOrgPositionZTree(List<String> orgIds) throws ServiceException {
		SmartResponse<OrgPositionZTreeData> smartResp = new SmartResponse<OrgPositionZTreeData>();
		List<TreeProp> ztreeProps = orgServ.getTree(orgIds);
		if(null != ztreeProps && ztreeProps.size()>0) {
			String[] orgIdArray = new String[ztreeProps.size()];
			int count = 0;
			for(TreeProp treeProp : ztreeProps) {
				orgIdArray[count] = treeProp.getId();
				count++;
			}
			try {
				Map<String,Object> param = new HashMap<String, Object>();
				param.put("orgId", orgIdArray);
				List<TNPosition> positions = positionDao.queryByOrgIds(orgIdArray);
				if(null != positions && positions.size()>0) {
					TreeProp treeProp = null;
					List<TreeProp> newTreeProp = new ArrayList<TreeProp>();
					count = 1;
					for(int i = 0; i < ztreeProps.size(); i++) {
						newTreeProp.add(ztreeProps.get(i));
						count++;
						for(TNPosition positionTmp : positions) {
							if(ztreeProps.get(i).getId().equals(positionTmp.getOrgId())) {
								treeProp = new TreeProp();
								treeProp.setFlag("position");
								treeProp.setId(positionTmp.getId());
								treeProp.setName(positionTmp.getName());
								treeProp.setSortOrder(count);
								treeProp.setParentId(ztreeProps.get(i).getId());
								newTreeProp.add(treeProp);
								count++;
							}
						}
					}//for;
					treeProp = null;
					ztreeProps = null;
					positions = null;
					
					ZTreeHelper<OrgPositionZTreeData> zTreeHelper = new ZTreeHelper<OrgPositionZTreeData>(OrgPositionZTreeData.class, newTreeProp);
					List<OrgPositionZTreeData> ztreeDatas = zTreeHelper.convert("position");
					zTreeHelper = null;
					treeCombinHelper.trimLeaf(ztreeDatas);
					if(null != ztreeDatas && ztreeDatas.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(ztreeDatas);
					}
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return smartResp;
	}
	
	/**
     * 从职位中删除角色
     * @param positionId
     * @param id
     * @return
     */
    public SmartResponse<String> deleteRole(String positionId, String id) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        smartResp.setMsg("删除失败");
        if(StringUtils.isEmpty(positionId) || StringUtils.isEmpty(id)) {
            return smartResp;
        }
        Map<String,Object> param = new HashMap<String, Object>(3);
        param.put("positionId", positionId);
        param.put("id", id);
        param.put("flag", "p");
        if(rolePosDao.delete(param)) {
            smartResp.setResult(OP_SUCCESS);
            smartResp.setMsg("删除成功");
        }
        return smartResp;
    }
}
