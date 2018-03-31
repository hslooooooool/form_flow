package cn.com.smart.form.helper;

import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.constant.IConstant;
import cn.com.smart.service.SmartContextService;
import cn.com.smart.web.upload.AttachmentUploadHandler;

/**
 * 表单上传文件助手类（抽象类）
 * @author lmq  2017年8月14日
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractFormUploadFileHelper {

    /**
     * 附件字段后缀
     */
    protected static final String ATT_FIELD_SUFFIX = "_file";

    protected MultipartHttpServletRequest multiRequest;
    protected Map<String, Object> formArgs;
    protected String userId;
    protected AttachmentUploadHandler uploadHandler;

    /**
     * 
     * @param multiRequest
     * @param formArgs
     * @param submitFormData
     * @param userId
     */
    public AbstractFormUploadFileHelper(MultipartHttpServletRequest multiRequest, Map<String, Object> formArgs, String userId) {
        this.multiRequest = multiRequest;
        this.formArgs = formArgs;
        this.userId = userId;
        uploadHandler = SmartContextService.find(AttachmentUploadHandler.class);
    }
    
    /**
     * 开始上传文件
     */
    public void upload() {
        Map<String, MultipartFile> fileMaps = multiRequest.getFileMap();
        if(null == fileMaps || fileMaps.size() == 0) {
            return;
        }
        Set<Map.Entry<String,MultipartFile>> sets = fileMaps.entrySet();
        try {
            for (Map.Entry<String,MultipartFile> set : sets) {
                String id = upload(set.getValue());
                if(StringUtils.isNotEmpty(id)) {
                    String key = set.getKey();
                    if(key.endsWith(ATT_FIELD_SUFFIX)) {
                        String fieldId = key.substring(0, id.length() - (ATT_FIELD_SUFFIX.length()-1));
                        String value = StringUtils.handleNull(formArgs.get(fieldId));
                        if(StringUtils.isNotEmpty(value)) {
                            value +=IConstant.MULTI_VALUE_SPLIT + id;
                        } else {
                            value = id;
                        }
                        formArgs.put(fieldId, value);
                    } else {
                        formArgs.put(key, id);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * 上传文件 
     * @param file 文件
     * @return 返回附件ID
     * @throws Exception
     */
    protected abstract String upload(MultipartFile file) throws Exception;
}
