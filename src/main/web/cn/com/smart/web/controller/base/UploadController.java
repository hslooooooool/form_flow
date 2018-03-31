package cn.com.smart.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.smart.web.upload.FileUploadHandler;

/**
 * 上传文件 基类
 * @author lmq
 * @version 1.0 2015年8月30日
 * @since 1.0
 *
 */
public abstract class UploadController extends BaseController {

	@Autowired
	protected FileUploadHandler fileUploadHandler;
}
