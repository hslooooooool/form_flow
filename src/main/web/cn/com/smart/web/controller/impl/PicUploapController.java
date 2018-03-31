package cn.com.smart.web.controller.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.bean.entity.TNAttachment;
import cn.com.smart.web.controller.base.AttachmentUploadController;

/**
 * 上传图片[测试类]
 * @author lmq
 *
 */
@Controller
@RequestMapping("/pictrue")
public class PicUploapController extends AttachmentUploadController {

	@RequestMapping("/upload")
	public @ResponseBody SmartResponse<TNAttachment> upload(@RequestParam MultipartFile file,HttpServletRequest request) throws Exception {
		SmartResponse<TNAttachment> smartResp = new SmartResponse<TNAttachment>();
		if(null != file) {
			String userId = getUserInfoFromSession(request).getId();
			TNAttachment att = attUploadHandler.fileUpload(file.getInputStream(), file.getContentType(), file.getOriginalFilename(),file.getSize() ,userId);
			if(null != att) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setData(att);
			}
		}
		return smartResp;
	}
	
}
