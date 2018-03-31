package cn.com.smart.web.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.RedirectView;

import com.mixsmart.utils.StringUtils;

/**
 * SmartWeb异常处理器
 * @author lmq <br />
 * 2016年11月7日
 * @version 1.0
 * @since 1.0
 */
public class SmartExceptionResolver extends AbstractHandlerExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest reuqest,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView modelView = new ModelAndView();
		String msg = ex.getMessage();
		if(StringUtils.isNotEmpty(msg)) {
			try {
				msg = URLEncoder.encode(msg, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(null != msg && msg.startsWith("unexpected")) {
			msg = "系统忙碌，请稍后重试";
		}
		modelView.getModelMap().put("msg", msg);
		View view = new RedirectView("/exception/json", true, true, true);
		modelView.setView(view);
		return modelView;
	}

	
	
}
