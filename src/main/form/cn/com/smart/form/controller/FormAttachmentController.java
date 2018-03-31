package cn.com.smart.form.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.form.service.FormAttachmentService;
import cn.com.smart.init.config.InitSysConfig;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNAttachment;
import cn.com.smart.web.controller.base.AttachmentUploadController;
import cn.com.smart.web.service.AttachmentService;
import cn.com.smart.web.service.OPService;

/**
 * 表单附件
 * 
 * @author lmq 2017年8月14日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/form/attachment")
public class FormAttachmentController extends AttachmentUploadController {

    private static final String VIEW_DIR = "form/attachment";
    
    @Autowired
    private FormAttachmentService formAttServ;
    @Autowired
    private AttachmentService attServ;
    @Autowired
    private OPService opServ;

    /**
     * 上传表单附件
     * @param atts 附件对象
     * @param session
     * @param formId 表单ID
     * @param formDataId 表单数据ID
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = { "application/json" })
    @ResponseBody
    public SmartResponse<TNAttachment> upload(@RequestParam MultipartFile atts, HttpSession session, String formId,
            String formDataId) {
        SmartResponse<TNAttachment> smartResp = new SmartResponse<TNAttachment>();
        if (StringUtils.isEmpty(formId) || StringUtils.isEmpty(formDataId)) {
            return smartResp;
        }
        String userId = getUserInfoFromSession(session).getId();
        try {
            TNAttachment att = attUploadHandler.fileUpload(atts.getInputStream(), atts.getContentType(),
                    atts.getOriginalFilename(), atts.getSize(), userId);
            if (null == att) {
                LoggerUtils.debug(log, "表单附件上传失败");
                return smartResp;
            }
            SmartResponse<String> resultResp = formAttServ.saveAttachment(att, formId, formDataId, userId);
            if (OP_SUCCESS.equals(resultResp.getResult())) {
                smartResp.setData(att);
                smartResp.setResult(OP_SUCCESS);
                smartResp.setMsg("文件上传成功");
            } else {
                LoggerUtils.error(log, "文件上传失败");
                attServ.delete(att.getId());
            }
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
            LoggerUtils.error(log, e.getMessage());
        }
        return smartResp;
    }

    /**
     * 表单附件列表
     * @param session
     * @param formId
     * @param formDataId
     * @param isView 是否为查看；1--表示查看（不能上传文件），0--表示非查看（可以上传文件）
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpSession session, String formId, String formDataId, String isView) {
        ModelAndView modelView = new ModelAndView();
        SmartResponse<Object> smartResp = new SmartResponse<Object>();
        UserInfo userInfo = getUserInfoFromSession(session);
        if (StringUtils.isNotEmpty(formId)) {
            Map<String, Object> param = new HashMap<String, Object>(2);
            //表单未提交时
            if(StringUtils.isEmpty(formDataId)) {
                param.put("formId", formId);
                param.put("userId", userInfo.getId());
            } else {
                param.put("formDataId", formDataId);
            }
            smartResp = opServ.getDatas("form_attachment_list", param);
            if (OP_SUCCESS.equals(smartResp.getResult())) {
                List<Object> datas = smartResp.getDatas();
                for (Object obj : datas) {
                    Object[] objArray = (Object[]) obj;
                    try {
                        long size = Long.parseLong(StringUtils.handleNull(objArray[3]));
                        objArray[3] = StringUtils.fileSize(size);
                    } catch (Exception e) {
                        objArray[3] = "";
                    }
                }
            }
            param = null;
        }
        ModelMap modelMap = modelView.getModelMap();
        modelMap.put("userInfo", userInfo);
        isView = StringUtils.isEmpty(isView) ? "0" : isView;
        if ("0".equals(isView)) {
            modelMap.put("attrUploadPromptMsg", InitSysConfig.getInstance().getValue("att.upload.prompt.msg"));
            String uploadFileType = StringUtils.handleNull(InitSysConfig.getInstance().getValue("upload.image.type"))
                    + "," + StringUtils.handleNull(InitSysConfig.getInstance().getValue("upload.doc.type"));
            if (uploadFileType.startsWith(",")) {
                uploadFileType = uploadFileType.substring(1, uploadFileType.length());
            }
            modelMap.put("uploadFileType", uploadFileType);
        }
        modelMap.put("isView", isView);
        modelMap.put("smartResp", smartResp);
        modelView.setViewName(VIEW_DIR + "/list");
        return modelView;
    }

    /**
     * 获取附件信息通过附件ID
     * @param id 附件ID；多个附件ID直接用英文逗号分隔
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public SmartResponse<Object> info(String id) {
        SmartResponse<Object> smartResp = new SmartResponse<Object>();
        if (StringUtils.isEmpty(id)) {
            return smartResp;
        }
        String[] ids = id.split(MULTI_VALUE_SPLIT);
        Map<String, Object> param = new HashMap<String, Object>(1);
        param.put("ids", ids);
        smartResp = opServ.getDatas("get_form_att_infos_byid", param);
        if (OP_SUCCESS.equals(smartResp.getResult())) {
            List<Object> list = smartResp.getDatas();
            // 处理文件大小
            for (Object obj : list) {
                Object[] objs = (Object[]) obj;
                long fileSize = Long.parseLong(objs[3].toString());
                objs[3] = StringUtils.fileSize(fileSize);
            }
        }
        return smartResp;
    }

    /**
     * 删除表单中的附件
     * @param id 表单附件ID
     * @param fieldId 字段ID
     * @param formDataId 表单数据ID
     * @param attId 附件ID
     * @return
     */
    @RequestMapping(value="/deleteForm", produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public SmartResponse<String> deleteForm(String id, String fieldId, String formDataId, String attId) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        smartResp.setMsg("附件删除失败");
        if (StringUtils.isEmpty(id)) {
            return smartResp;
        }
        smartResp = formAttServ.delete(id);
        if (OP_SUCCESS.equals(smartResp.getResult())) {
            formAttServ.updateFormField(fieldId, formDataId, attId);
            smartResp.setResult(OP_SUCCESS);
            smartResp.setMsg("附件删除成功");
        }
        return smartResp;
    }
    
    
    /**
     * 删除附件列表中的附件
     * @param id 表单附件ID
     * @return
     */
    @RequestMapping(value="/delete", produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public SmartResponse<String> delete(String id) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        smartResp.setMsg("附件删除失败");
        if (StringUtils.isEmpty(id)) {
            return smartResp;
        }
        smartResp = formAttServ.delete(id);
        if (OP_SUCCESS.equals(smartResp.getResult())) {
            smartResp.setResult(OP_SUCCESS);
            smartResp.setMsg("附件删除成功");
        }
        return smartResp;
    }

}
