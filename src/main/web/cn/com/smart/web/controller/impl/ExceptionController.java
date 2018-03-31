package cn.com.smart.web.controller.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.controller.base.BaseController;

import com.mixsmart.utils.StringUtils;

/**
 * 异常处理控制器
 * @version 1.0
 * @since JDK1.6以上
 * @author lmq
 * <br />
 * 2016年7月24日
 */
@Controller
@RequestMapping("/exception")
public class ExceptionController extends BaseController {

	private static final String VIEW_DIR = "error/";
	
	/**
	 * 返回JSON格式的错误信息
	 * @param msg
	 * @return
	 */
	@RequestMapping("/json")
	@ResponseBody
	public SmartResponse<String> json(String msg) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(msg)) {
			try {
				msg = URLDecoder.decode(msg, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			smartResp.setMsg(msg);
		}
		return smartResp;
	}
	
	/**
	 * 500错误
	 * @return
	 */
	@RequestMapping("/500")
	public ModelAndView error500() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName(VIEW_DIR+"500");
		return modelView;
	}
	
	/**
	 * 404错误
	 * @return
	 */
	@RequestMapping("/404")
	public ModelAndView error404() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName(VIEW_DIR+"404");
		return modelView;
	}
	
	@RequestMapping("/noaccess")
    @ResponseBody
	public SmartResponse<String> noAccess() {
	    SmartResponse<String> smartResp = new SmartResponse<String>();
	    smartResp.setMsg("没有访问权限");
	    return smartResp;
	}
}
