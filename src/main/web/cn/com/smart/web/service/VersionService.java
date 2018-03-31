package cn.com.smart.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNVersion;
import cn.com.smart.web.constant.enums.VersionType;
import cn.com.smart.web.dao.impl.VersionDao;

/**
 * 版本服务类
 * @author lmq <br />
 * 2016年9月15日
 * @version 1.0
 * @since 1.0
 */
@Service
public class VersionService extends MgrServiceImpl<TNVersion> {

	@Override
	public VersionDao getDao() {
		return (VersionDao)super.getDao();
	}

	/**
	 * 获取版本信息
	 * @param versionType
	 * @return
	 */
	public List<TNVersion> getVersions(VersionType versionType) {
		return getDao().getVersions(versionType);
	}
	
	/**
	 * 获取最新版本号加1根据版本类型
	 * @param versionType
	 * @return
	 */
	public String getLastVersionAddOne(VersionType versionType) {
		return getDao().getLastVersionAddOne(versionType);
	}
	
	/**
	 * 获取最新版本号根据版本类型
	 * @param versionType
	 * @return
	 */
	public TNVersion getNewVersion(VersionType versionType) {
		return getDao().getNewVersion(versionType);
	}
	
	/**
	 * 获取版本信息
	 * @param versionType
	 * @param version
	 * @return
	 */
	public TNVersion getVersion(VersionType versionType, String version) {
		return getDao().getVersion(versionType, version);
	}
	
	/**
	 * 获取版本信息
	 * @param versionType
	 * @param limit
	 * @return
	 */
	public List<TNVersion> getVersion(VersionType versionType, int limit) {
		return getDao().getVersion(versionType, limit);
	}
}
