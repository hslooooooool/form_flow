package cn.com.smart.flow.controller;

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

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.flow.bean.TaskInfo;
import cn.com.smart.flow.service.FlowAttachmentService;
import cn.com.smart.flow.service.FlowService;
import cn.com.smart.form.service.FormAttachmentService;
import cn.com.smart.init.config.InitSysConfig;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNAttachment;
import cn.com.smart.web.controller.base.AttachmentUploadController;
import cn.com.smart.web.service.OPService;

/**
 * 流程实例--附件
 * @author lmq
 * @version 1.0 
 * @since 
 *
 */
@Controller
@RequestMapping("/process/attachment")
public class ProcessAttachmentController extends AttachmentUploadController {

	private static final String VIEW_DIR = "flow/process/attachment";
	
	@Autowired
	private FlowService flowServ;
	@Autowired
	private OPService opServ;
	@Autowired
	private FlowAttachmentService flowAttServ;
	@Autowired
	private FormAttachmentService formAttServ;
	
	/**
	 * 上传附件
	 * @param atts
	 * @param session
	 * @param taskInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<TNAttachment> upload(@RequestParam MultipartFile atts,HttpSession session,TaskInfo taskInfo) throws Exception {
		SmartResponse<TNAttachment> smartResp = new SmartResponse<TNAttachment>();
		if(null != atts && null != taskInfo) {
			String userId = getUserInfoFromSession(session).getId();
			TNAttachment att = attUploadHandler.fileUpload(atts.getInputStream(), atts.getContentType(), atts.getOriginalFilename(),atts.getSize() ,userId);
			if(null != att) {
				SmartResponse<String> chRes = flowServ.saveAttachment(att, taskInfo);
				if(OP_SUCCESS.equals(chRes.getResult())) {
					smartResp.setData(att);
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("文件上传成功");
				} else {
					log.error("文件上传失败");
					flowServ.deleteAttachment(att.getId());
				}
				chRes = null;
			}
		}
		return smartResp;
	}
	
	
	/**
	 * 附件列表
	 * @param session
	 * @param modelView
	 * @param taskInfo
	 * @param formId
	 * @param isAtt 是否附件
	 * @param isUploadBtn 是否有上传按钮
	 * @param isView 是否为查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpSession session,ModelAndView modelView,TaskInfo taskInfo,
			String isAtt,String isUploadBtn,String isView) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		UserInfo userInfo = getUserInfoFromSession(session);
		if(null != taskInfo && StringUtils.isNotEmpty(taskInfo.getProcessId())) {
			Map<String,Object> param = new HashMap<String, Object>();
			//未启动流程实例时
			if(StringUtils.isEmpty(taskInfo.getOrderId()) && StringUtils.isNotEmpty(taskInfo.getFormId())) {
				param.put("formId", taskInfo.getFormId());
				param.put("userId", userInfo.getId());
			} else if(StringUtils.isNotEmpty(taskInfo.getOrderId())) { //启动流程实例后
				param.put("orderId", taskInfo.getOrderId());
			}
			smartResp = opServ.getDatas("process_attachment_list", param);
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				List<Object> datas = smartResp.getDatas();
				for (Object obj : datas) {
					Object[] objArray = (Object[]) obj;
					long size = 0;
					try {
					  size = Long.parseLong(StringUtils.handleNull(objArray[3]));
					  objArray[3] = new String(StringUtils.fileSize(size));
					} catch (Exception e) {
						objArray[3] = new String("");
					}
				}
			}
			param = null;
		}
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("userInfo", userInfo);
		isUploadBtn = StringUtils.isEmpty(isUploadBtn)?"0":isUploadBtn;
		isAtt = StringUtils.isEmpty(isAtt)?"0":isAtt;
		isView = StringUtils.isEmpty(isView)?"0":isView;
		if("0".equals(isView)) {
			modelMap.put("attrUploadPromptMsg", InitSysConfig.getInstance().getValue("att.upload.prompt.msg"));
			String uploadFileType = StringUtils.handleNull(InitSysConfig.getInstance().getValue("upload.image.type"))+","+
			StringUtils.handleNull(InitSysConfig.getInstance().getValue("upload.doc.type"));
			if(uploadFileType.startsWith(",")) {
				uploadFileType = uploadFileType.substring(1,uploadFileType.length());
			}
			modelMap.put("uploadFileType", uploadFileType);
		}
		modelMap.put("isUploadBtn", isUploadBtn);
		modelMap.put("isAtt", isAtt);
		modelMap.put("isView", isView);
		modelMap.put("smartResp", smartResp);
		modelView.setViewName(VIEW_DIR+"/list");
		if("1".equals(isView)) {
			modelView.setViewName(VIEW_DIR+"/listView");
		}
		if("2".equals(isView)) {
			modelView.setViewName(VIEW_DIR+"/editList");
		}
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
		if(StringUtils.isEmpty(id)) {
			return smartResp;
		}
		String[] ids = id.split(MULTI_VALUE_SPLIT);
		Map<String, Object> param = new HashMap<String, Object>(1);
		param.put("ids", ids);
		smartResp = opServ.getDatas("get_flow_att_infos_byid", param);
		if(OP_SUCCESS.equals(smartResp.getResult())) {
			List<Object> list = smartResp.getDatas();
			//处理文件大小
			for (Object obj : list) {
				Object[] objs = (Object[])obj;
				long fileSize = Long.parseLong(objs[3].toString());
				objs[3] = StringUtils.fileSize(fileSize);
			}
		}
		return smartResp;
	}
	
	/**
	 * 删除表单中的附件 
	 * @param id 附件ID
	 * @param fieldId 字段ID
	 * @param formDataId 
	 * @return
	 */
	@RequestMapping("/deleteForm")
	@ResponseBody
	public SmartResponse<String> deleteForm(String id, String fieldId, String formDataId, String attId) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("附件删除失败");
		if(StringUtils.isEmpty(id)) {
			return smartResp;
		}
		if(flowAttServ.getDao().delete(id)) {
		    formAttServ.updateFormField(fieldId, formDataId, attId);
			smartResp.setResult(OP_SUCCESS);
			smartResp.setMsg("附件删除成功");
		}
		return smartResp;
	}
}
