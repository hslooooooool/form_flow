package cn.com.smart.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNRoleUser;
import cn.com.smart.web.dao.impl.RoleUserDao;

/**
 * 
 * @author lmq
 *
 */
@Service("roleUserServ")
public class RoleUserService extends MgrServiceImpl<TNRoleUser> {

	@Autowired
	private RoleUserDao roleUserDao;
	
	/**
	 * 删除
	 * @param id
	 * @param roleId
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> delete(String id,String roleId) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(roleId)) {
				String[] ids = id.split(",");
				String sql = "delete from t_n_role_user where role_id=:roleId and user_id=:userId";
				Map<String, Object> param = null;
				List<Map<String, Object>> params = new ArrayList<Map<String,Object>>();
				for (String idTmp : ids) {
					param = new HashMap<String, Object>();
					param.put("roleId", roleId);
					param.put("userId", idTmp);
					params.add(param);
				}
				if(roleUserDao.executeSql(sql, params)>0) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg(OP_SUCCESS_MSG);
				}
				param = null;
				params = null;
				ids = null;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}


	
}
