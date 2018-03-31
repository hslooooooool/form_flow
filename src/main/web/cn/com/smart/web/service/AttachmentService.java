package cn.com.smart.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNAttachment;
import cn.com.smart.web.dao.impl.AttachmentDao;

import com.mixsmart.utils.StringUtils;

/**
 * 附件  Service
 * @author lmq
 *
 */
@Service("attServ")
public class AttachmentService extends MgrServiceImpl<TNAttachment> {

	@Autowired
	private AttachmentDao attDao;
	
	/**
	 * 查找附件
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<TNAttachment> findAtt(String id) throws ServiceException {
		SmartResponse<TNAttachment> smartResp = new SmartResponse<TNAttachment>();
		try {
			if(StringUtils.isNotEmpty(id)) {
				TNAttachment  att = attDao.find(id);
				if(null != att) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg(OP_SUCCESS_MSG);
					smartResp.setData(att);
				}
				att = null;
			}
		}catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 删除附件
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> delete(String id) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(StringUtils.isNotEmpty(id)) {
				if(attDao.delete(id)) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg(OP_SUCCESS_MSG);
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
}
