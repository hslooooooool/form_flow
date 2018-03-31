package cn.com.smart.form.helper;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.constant.IConstant;
import cn.com.smart.form.service.FormAttachmentService;
import cn.com.smart.service.SmartContextService;
import cn.com.smart.web.bean.entity.TNAttachment;
import cn.com.smart.web.service.AttachmentService;

/**
 * 表单上传文件助手类
 * @author lmq  2017年8月14日
 * @version 1.0
 * @since 1.0
 */
public class FormUploadFileHelper extends AbstractFormUploadFileHelper {
    
    private FormAttachmentService formAttServ;
    private AttachmentService attServ;
    private String formId;
    private String formDataId;
    private String userId;
    
    public FormUploadFileHelper(MultipartHttpServletRequest multiRequest, 
            Map<String, Object> formArgs, String formId, String formDataId,String userId) {
        super(multiRequest, formArgs, userId);
        this.userId = userId;
        this.formId = formId;
        this.formDataId = formDataId;
        formAttServ = SmartContextService.find(FormAttachmentService.class);
        attServ = SmartContextService.find(AttachmentService.class);
    }

    @Override
    protected String upload(MultipartFile file) throws Exception {
        TNAttachment att = uploadHandler.fileUpload(file.getInputStream(), file.getContentType(), file.getOriginalFilename(),file.getSize() ,userId);
        String id = null;
        if(null != att) {
            SmartResponse<String> samrtResp = formAttServ.saveAttachment(att, formId, formDataId, userId);
            if(IConstant.OP_SUCCESS.equals(samrtResp.getResult())) {
                id = att.getId();
            } else {
                attServ.delete(att.getId());
            }
            samrtResp = null;
        }
        return id;
    }
    
}
