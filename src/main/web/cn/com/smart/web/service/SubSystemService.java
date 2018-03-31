package cn.com.smart.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNSubSystem;
import cn.com.smart.web.dao.impl.SubSystemDao;

/**
 * 子系统信息服务类
 * @author lmq <br />
 * 2016年12月23日
 * @version 1.0
 * @since 1.0
 */
@Service
public class SubSystemService extends MgrServiceImpl<TNSubSystem> {

	@Autowired
	private SubSystemDao subSysDao;
	
	/**
	 * 获取子系统
	 * @return
	 */
	public List<TNSubSystem> getSubSystems() {
		return subSysDao.getSubSystems();
	}
	
}
