package cn.com.smart.flow.helper;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.constant.IConstant;
import cn.com.smart.flow.bean.SubmitFormData;
import cn.com.smart.flow.service.FlowService;
import cn.com.smart.form.helper.AbstractFormUploadFileHelper;
import cn.com.smart.service.SmartContextService;
import cn.com.smart.web.bean.entity.TNAttachment;

/**
 * 表单上传文件助手
 * @author lmq  2017年4月12日
 * @version 1.0
 * @since 1.0
 */
public class FlowFormUploadFileHelper extends AbstractFormUploadFileHelper {
	
	private FlowService flowServ;
	private SubmitFormData submitFormData;

	public FlowFormUploadFileHelper(MultipartHttpServletRequest multiRequest,
			Map<String, Object> formArgs, SubmitFormData submitFormData, String userId) {
	    super(multiRequest, formArgs, userId);
	    this.submitFormData = submitFormData;
		flowServ = SmartContextService.find(FlowService.class);
	}
	
	@Override
	protected String upload(MultipartFile file) throws Exception {
		TNAttachment att = uploadHandler.fileUpload(file.getInputStream(), file.getContentType(), file.getOriginalFilename(),file.getSize() ,userId);
		String id = null;
		if(null != att) {
			SmartResponse<String> chRes = flowServ.saveAttachment(att, this.submitFormData);
			if(IConstant.OP_SUCCESS.equals(chRes.getResult())) {
				id = att.getId();
			} else {
				flowServ.deleteAttachment(att.getId());
			}
			chRes = null;
		}
		return id;
	}
}
