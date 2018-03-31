package cn.com.smart.flow.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.SnakerException;
import org.snaker.engine.cache.Cache;
import org.snaker.engine.cache.CacheManager;
import org.snaker.engine.core.ProcessService;
import org.snaker.engine.entity.Process;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.parser.ModelParser;
import org.springframework.beans.factory.annotation.Autowired;

import com.mixsmart.utils.CollectionUtils;

import cn.com.smart.exception.DaoException;
import cn.com.smart.flow.bean.entity.TFlowProcess;
import cn.com.smart.flow.dao.FlowProcessDao;
import cn.com.smart.flow.ext.ExtModelParser;
import cn.com.smart.flow.ext.ExtProcess;
import cn.com.smart.flow.ext.ExtProcessModel;
import cn.com.smart.flow.helper.ProcessHelper;

/**
 * 重写 ProcessService 类
 * 重写类的目的是，根据具体业务与流程关联起来
 * deploy()部署方法和redeploy()重新部署方法，重写后流程与组织机构关联起来
 * @author lmq
 *
 */
public class RewriteProcessService extends ProcessService {

	private static final Logger log = LoggerFactory.getLogger(RewriteProcessService.class);
	
	protected static final String DEFAULT_SEPARATOR = ".";
	/**
	 * 流程定义对象cache名称
	 */
	protected static final String CACHE_ENTITY = "snaker.process.entity";
	/**
	 * 流程id、name的cache名称
	 */
	protected static final String CACHE_NAME = "snaker.process.name";
	/**
	 * cache manager
	 */
	protected CacheManager cacheManager;
	/**
	 * 实体cache(key=name,value=entity对象)
	 */
	protected Cache<String, Process> entityCache;
	/**
	 * 名称cache(key=id,value=name对象)
	 */
	protected Cache<String, String> nameCache;
	
	@Autowired
	protected FlowProcessDao flowProcessDao;
	
	@Override
	public String deploy(InputStream input, String creator) throws SnakerException {
		AssertHelper.notNull(input);
		try {
			byte[] bytes = StreamHelper.readBytes(input);
			ExtProcessModel model = ExtModelParser.parse(bytes);
			Integer version = access().getLatestProcessVersion(model.getName());
			Process entity = new Process();
			entity.setId(StringHelper.getPrimaryKey());
			if(version == null || version < 0) {
				entity.setVersion(0);
			} else {
				entity.setVersion(version + 1);
			}
			entity.setState(STATE_ACTIVE);
			entity.setModel(model);
			entity.setBytes(bytes);
			entity.setCreateTime(DateHelper.getTime());
			entity.setCreator(creator);
			saveProcess(entity);
			
			TFlowProcess flowProcess = new TFlowProcess();
			flowProcess.setOrgId(model.getOrgId());
			flowProcess.setProcessId(entity.getId());
			flowProcess.setAttachment(model.getAttachment());
		    flowProcess.setFlowType(model.getFlowType());
		    flowProcess.setFormId(model.getFormId());
		    
		    if(CollectionUtils.isNotEmpty(model.getTaskModels())) {
				flowProcess.setTotalNodeNum(model.getTaskModels().size());
				flowProcess.setNodeNameCollection(ProcessHelper.nodesToStr(model.getTaskModels()));
			}
		    
			flowProcessDao.save(flowProcess);
			flowProcess = null;
			cache(entity);
			return entity.getId();
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new SnakerException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void redeploy(String id, InputStream input) {
		AssertHelper.notNull(input);
		Process entity = access().getProcess(id);
		AssertHelper.notNull(entity);
		try {
			byte[] bytes = StreamHelper.readBytes(input);
			ExtProcessModel model = ExtModelParser.parse(bytes);
			String oldProcessName = entity.getName();
			entity.setModel(model);
			entity.setBytes(bytes);
			access().updateProcess(entity);
			if(!oldProcessName.equalsIgnoreCase(entity.getName())) {
				Cache<String, Process> entityCache = ensureAvailableEntityCache();
				if(entityCache != null) {
					entityCache.remove(oldProcessName + DEFAULT_SEPARATOR + entity.getVersion());
				}
			}
			cache(entity);
			TFlowProcess flowProcess = flowProcessDao.findByProcessId(entity.getId());
			flowProcess.setOrgId(model.getOrgId());
			if(CollectionUtils.isNotEmpty(model.getTaskModels())) {
				flowProcess.setTotalNodeNum(model.getTaskModels().size());
				flowProcess.setNodeNameCollection(ProcessHelper.nodesToStr(model.getTaskModels()));
			}
		    flowProcess.setAttachment(model.getAttachment());
		    flowProcess.setFlowType(model.getFlowType());
		    flowProcess.setFormId(model.getFormId());
			flowProcessDao.updateByProcessId(flowProcess);
			flowProcess = null;
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new SnakerException(e.getMessage(), e.getCause());
		}
	}
	
	/**
	 * 根据id获取process对象
	 * 先通过cache获取，如果返回空，就从数据库读取并put
	 */
	public ExtProcess getProcessById(String id) {
		AssertHelper.notEmpty(id);
		Process entity = null;
		String processName;
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, Process> entityCache = ensureAvailableEntityCache();
		if(nameCache != null && entityCache != null) {
			processName = nameCache.get(id);
			if(StringHelper.isNotEmpty(processName)) {
				entity = entityCache.get(processName);
			}
		}
		ExtProcess flowProcess = null;
		if(entity != null) {
			if(log.isDebugEnabled()) {
				log.debug("obtain process[id={}] from cache.", id);
			}
		} else {
			entity = access().getProcess(id);
			if(entity != null) {
				if(log.isDebugEnabled()) {
					log.debug("obtain process[id={}] from data", id);
				}
				cache(entity);
			}
		}
		if(null != entity) {
			flowProcess = new ExtProcess();
			flowProcess.convert(entity);
		}
		Map<String,Object> params = new HashMap<String, Object>(1);
		params.put("processId", id);
		try {
			List<TFlowProcess> lists = flowProcessDao.queryByField(params);
			if(null != lists && lists.size()>0) {
				flowProcess.setOrgId(lists.get(0).getOrgId());
				flowProcess.setAttachment(lists.get(0).getAttachment());
				flowProcess.setFlowType(lists.get(0).getFlowType());
				flowProcess.setFormId(lists.get(0).getFormId());
			}
			lists = null;
		} catch (DaoException e) {
			e.printStackTrace();
		} finally {
			params = null;
		}
		return flowProcess;
	}
	
	
	/**
	 * 缓存实体
	 * @param entity 流程定义对象
	 */
	private void cache(Process entity) {
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, Process> entityCache = ensureAvailableEntityCache();
		if(entity.getModel() == null && entity.getDBContent() != null) {
			entity.setModel(ModelParser.parse(entity.getDBContent()));
		}
		String processName = entity.getName() + DEFAULT_SEPARATOR + entity.getVersion();
		if(nameCache != null && entityCache != null) {
			if(log.isDebugEnabled()) {
				log.debug("cache process id is[{}],name is[{}]", entity.getId(), processName);
			}
			entityCache.put(processName, entity);
			nameCache.put(entity.getId(), processName);
		} else {
			if(log.isDebugEnabled()) {
				log.debug("no cache implementation class");
			}
		}
	}
	
	/**
	 * 清除实体
	 * @param entity 流程定义对象
	 */
	protected void clear(Process entity) {
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, Process> entityCache = ensureAvailableEntityCache();
		String processName = entity.getName() + DEFAULT_SEPARATOR + entity.getVersion();
		if(nameCache != null && entityCache != null) {
			nameCache.remove(entity.getId());
			entityCache.remove(processName);
		}
	}
	
	protected Cache<String, Process> ensureAvailableEntityCache() {
		Cache<String, Process> entityCache = ensureEntityCache();
		if(entityCache == null && this.cacheManager != null) {
			entityCache = this.cacheManager.getCache(CACHE_ENTITY);
		}
		return entityCache;
	}
	    
	protected Cache<String, String> ensureAvailableNameCache() {
		Cache<String, String> nameCache = ensureNameCache();
		if(nameCache == null && this.cacheManager != null) {
			nameCache = this.cacheManager.getCache(CACHE_NAME);
		}
		return nameCache;
	}

	@Override
	public void cascadeRemove(String id) {
		try {
			super.cascadeRemove(id);
			String hql = "delete from "+TFlowProcess.class.getName()+" where processId=:processId";
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("processId", id);
			flowProcessDao.executeHql(hql,params);
			params = null;
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
}
