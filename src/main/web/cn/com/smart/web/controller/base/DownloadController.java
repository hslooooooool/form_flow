package cn.com.smart.web.controller.base;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.utils.FileUtil;
import cn.com.smart.web.bean.entity.TNAttachment;
import cn.com.smart.web.service.AttachmentService;

/**
 * 下载
 * @author lmq
 *
 */
@Controller
@RequestMapping("/download")
public class DownloadController extends BaseController {

	@Autowired
	private AttachmentService attServ;
	
	/**
	 * 下载附件
	 * @param id 附件ID
	 * @return 返回下载实体对象
	 */
	@RequestMapping("/att")
	public ResponseEntity<byte[]> att(HttpServletRequest request,String id) {
		ResponseEntity<byte[]> responseEntity = null;
		if(StringUtils.isNotEmpty(id)) {
			SmartResponse<TNAttachment> smartResp = attServ.findAtt(id);
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				TNAttachment att = smartResp.getData();
				String rootDir = getRootDir();
				String filePath = rootDir+FileUtil.getFileSeparator()+att.getFilePath();
				if(StringUtils.isNotEmpty(filePath)) {
					File file = new File(filePath);
					if(file.exists()) {
						try {
							BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
							HttpHeaders headers = new HttpHeaders();
							
							String userAgent = request.getHeader("USER-AGENT");
							headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
							//IE
							if(StringUtils.isNotEmpty(userAgent) && userAgent.indexOf("Windows NT ")>-1 && userAgent.indexOf("Firefox")==-1) {
								String fileName = URLEncoder.encode(att.getFileName(), "UTF-8");    
								fileName = StringUtils.replace(fileName, "+", "%20");//替换空格 
								headers.setContentDispositionFormData("attachment", fileName);
							    responseEntity = new ResponseEntity<byte[]>(StreamUtils.copyToByteArray(bis),headers, HttpStatus.OK);
							} else {
								headers.setContentDispositionFormData("attachment", new String(att.getFileName().getBytes("UTF-8"), "ISO8859-1"));  
							    responseEntity = new ResponseEntity<byte[]>(StreamUtils.copyToByteArray(bis),headers, HttpStatus.CREATED);
							}
						    bis.close();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							att = null;
							smartResp = null;
							file = null;
						}
					}//if
				}//if
			}//if
		}
		return responseEntity;
	}
	
}
