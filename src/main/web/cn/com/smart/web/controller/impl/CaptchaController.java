package cn.com.smart.web.controller.impl;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.helper.HttpRequestHelper;

import com.mixsmart.utils.Captcha;

/**
 * 验证码
 * @author lmq
 *
 */
@Controller
@RequestMapping("captcha")
public class CaptchaController extends BaseController {

	@RequestMapping(method=RequestMethod.GET)
	public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("正在生成验证码-----");
		Captcha cap = new Captcha(100,28,20);
		String code = cap.getString();
		log.info("正在生成验证码--[完成]--["+code+"]-");
		HttpRequestHelper.setSession(request, SESSION_CAPTCHA_LOGIN, code);
		ImageIO.write(cap.getBuffImage(), "JPEG", response.getOutputStream());
		cap = null;
	}
	
}
