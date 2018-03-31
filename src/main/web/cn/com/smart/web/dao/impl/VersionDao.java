package cn.com.smart.web.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.web.bean.entity.TNVersion;
import cn.com.smart.web.constant.enums.VersionType;

import com.mixsmart.utils.CollectionUtils;

/**
 * 版本DAO
 * @author lmq <br />
 * 2016年9月15日
 * @version 1.0
 * @since 1.0
 */
@Repository
public class VersionDao extends BaseDaoImpl<TNVersion> {

	/**
	 * 获取版本信息根据版本类型
	 * @param versionType 版本类型
	 * @return 返回版本对象集合
	 */
	public List<TNVersion> getVersions(VersionType versionType) {
		List<TNVersion> versions = null;
		if(null != versionType) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", versionType.getValue());
			versions = super.queryByField(param, " version desc");
		}
		return versions;
	}
	
	/**
	 * 获取最新版本号加1根据版本类型
	 * @param versionType 版本类型
	 * @return 返回版本对象集合
	 */
	public String getLastVersionAddOne(VersionType versionType) {
		String strVersion = "v1.0.0";
		if(null != versionType) {
			TNVersion version = getNewVersion(versionType);
			if(null == version) {
				return strVersion;
			}
			try {
				String num = version.getVersion().replaceAll("[A-Za-z]|_|", "");
				String[] nums = num.split("\\.");
				strVersion = "V";
				for (int i = 0; i < nums.length; i++) {
					if(i == nums.length-1) {
						strVersion += Integer.parseInt(nums[i])+1;
					} else {
						strVersion += nums[i]+".";
					}
				}
			} catch (Exception ex) {
				strVersion = version.getVersion();
			}
		}
		return strVersion;
	}
	
	/**
	 * 获取最新版本号根据版本类型
	 * @param versionType 版本类型
	 * @return 返回版本对象集合
	 */
	public Long getVersionNum(VersionType versionType) {
		Long versionNum = 0l;
		if(null != versionType) {
			String sql = "select * from t_n_version where type_=:type order by create_time desc limit 1";
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", versionType.getValue());
			List<TNVersion> versions = querySqlToEntity(sql, param, TNVersion.class);
			if(CollectionUtils.isNotEmpty(versions)) {
				if(null != versions.get(0)) {
					versionNum = versions.get(0).getNumVersion();
				}
			}
		}
		return versionNum;
	}
	
	/**
	 * 获取最新版本号根据版本类型
	 * @param versionType 版本类型
	 * @return 返回版本对象集合
	 */
	public String getVersion(VersionType versionType) {
		String version = "V1.0.0";
		if(null != versionType) {
			String hql = "select max(version) from "+TNVersion.class.getName()+" where type=:type";
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", versionType.getValue());
			List<Object> objs = queryObjHql(hql, param);
			if(CollectionUtils.isNotEmpty(objs) && null != objs.get(0)) {
				if(null != objs.get(0)) {
					version = objs.get(0).toString();
				}
			}
		}
		return version;
	}
	
	/**
	 * 获取最新版本实体对象
	 * @param versionType 版本类型
	 * @return 返回版本对象集合
	 */
	public TNVersion getNewVersion(VersionType versionType) {
		TNVersion versionObj = null;
		if(null != versionType) {
			Long numVersion = getVersionNum(versionType);
			if(null != numVersion) {
				versionObj = getVersionByNum(versionType, numVersion);
			} else {
				String version = getVersion(versionType);
				versionObj = getVersion(versionType, version);
			}
		}
		return versionObj;
	}
	
	/**
	 * 获取版本信息根据版本类型及版本号
	 * @param versionType 版本类型
	 * @param version 版本号
	 * @return 返回版本对象
	 */
	public TNVersion getVersionByNum(VersionType versionType, Long numVersion) {
		TNVersion versionObj = null;
		if(null != versionType) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", versionType.getValue());
			param.put("num_version", numVersion);
			List<TNVersion> versions = super.queryByField(param);
			if(CollectionUtils.isNotEmpty(versions)) {
				versionObj = versions.get(0);
			}
			versions = null;
		}
		return versionObj;
	}
	
	/**
	 * 获取版本信息根据版本类型及版本号
	 * @param versionType 版本类型
	 * @param version 版本号
	 * @return 返回版本对象
	 */
	public TNVersion getVersion(VersionType versionType, String version) {
		TNVersion versionObj = null;
		if(null != versionType) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", versionType.getValue());
			param.put("version", version);
			List<TNVersion> versions = super.queryByField(param);
			if(CollectionUtils.isNotEmpty(versions)) {
				versionObj = versions.get(0);
			}
			versions = null;
		}
		return versionObj;
	}
	
	/**
	 * 获取版本信息根据版本类型及限制的数量 
	 * @param versionType 版本类型
	 * @param limit
	 * @return 返回版本对象集合
	 */
	public List<TNVersion> getVersion(VersionType versionType, int limit) {
		List<TNVersion> versions = null;
		if(null != versionType) {
			String hql = " from "+TNVersion.class.getName()+" where type=:type order by createTime desc";
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", versionType.getValue());
			versions = queryHql(hql, param, 0, limit);
		}
		return versions;
	}
	
}
