package cn.com.smart.web.controller.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.utils.FileUtil;
import cn.com.smart.web.bean.entity.TNAttachment;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.AttachmentService;
import cn.com.smart.web.service.OPService;

import com.mixsmart.utils.StringUtils;

/**
 * 附件控制类
 * @author lmq  2017年4月12日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/att")
public class AttachmentController extends BaseController {

	@Autowired
	private OPService opServ;
	@Autowired
	private AttachmentService attServ;
	
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
		smartResp = opServ.getDatas("get_attachment_infos_byid", param);
		if(OP_SUCCESS.equals(smartResp.getResult())) {
			List<Object> list = smartResp.getDatas();
			//处理文件大小
			for (Object obj : list) {
				Object[] objs = (Object[])obj;
				long fileSize = Long.parseLong(objs[2].toString());
				objs[2] = StringUtils.fileSize(fileSize);
			}
		}
		return smartResp;
	}
	
	/**
	 * 查看附件（直接在浏览器上打开查看）
	 * @param request
	 * @param response
	 * @param id 附件ID
	 */
	@RequestMapping("/view")
	public void view(HttpServletRequest request, HttpServletResponse response, String id) {
		if(StringUtils.isEmpty(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		TNAttachment att = attServ.find(id).getData();
		if(null == att) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String rootDir = getRootDir();
		String filePath = rootDir+FileUtil.getFileSeparator()+att.getFilePath();
		if(StringUtils.isEmpty(filePath)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		File file = new File(filePath);
		if(!file.exists()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			response.setContentType(att.getFileType());
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = bis.read(b)) != -1) {
				os.write(b, 0, n);
			} 
			os.flush();
	        os.close();
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
