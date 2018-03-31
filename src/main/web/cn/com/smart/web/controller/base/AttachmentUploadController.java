package cn.com.smart.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.smart.web.upload.AttachmentUploadHandler;

/**
 * 上传附件
 * @author lmq
 * @version 1.0 2015年8月30日
 * @since 1.0
 *
 */
public abstract class AttachmentUploadController extends UploadController {

	@Autowired
	protected AttachmentUploadHandler attUploadHandler;
	
}
