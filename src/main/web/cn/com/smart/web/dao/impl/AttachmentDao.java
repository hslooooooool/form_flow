package cn.com.smart.web.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.init.config.InitSysConfig;
import cn.com.smart.utils.FileUtil;
import cn.com.smart.web.bean.entity.TNAttachment;

/**
 * 附件
 * @author lmq
 *
 */
@Repository
public class AttachmentDao extends BaseDaoImpl<TNAttachment>{

	@Override
	public boolean delete(Serializable id) throws DaoException {
		boolean is = false;
		if(null != id && StringUtils.isNotEmpty(id.toString())) {
			String[] idArray = id.toString().split(",");
			List<TNAttachment> lists = find(idArray);
			if(null != lists && lists.size()>0) {
				if(super.delete(lists)) {
					is = true;
					String rootDir = InitSysConfig.getInstance().getValue("root.dir");
					for (TNAttachment att : lists) {
						String filePath = rootDir+att.getFilePath();
						FileUtil.deleteFile(filePath);
					}
				}
			}
		}
		return is;
	}
	
}
