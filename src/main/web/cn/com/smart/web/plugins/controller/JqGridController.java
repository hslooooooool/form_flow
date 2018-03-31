package cn.com.smart.web.plugins.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.plugins.JqGridData;
import cn.com.smart.web.plugins.JqGridRequest;
import cn.com.smart.web.plugins.service.JqGridService;

import com.mixsmart.utils.StringUtils;

/**
 * JqGrid插件控制器
 * @author lmq
 *
 */
@Controller
@RequestMapping("/jqGrid")
public class JqGridController extends BaseController {

	@Autowired
	private JqGridService jqGridServ;
	
	/**
	 * @param request
	 * @param resId
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query/{resId}")
	public @ResponseBody JqGridData query(HttpServletRequest request ,@PathVariable String resId,JqGridRequest gridRequest) throws Exception {
		Integer rows = gridRequest.getRows(getPerPageSize());
		Integer page = gridRequest.getPage();
		JqGridData jgGridData = new JqGridData();
		SmartResponse<JqGridData> smartResp = new SmartResponse<JqGridData>();
		Map<String, Object> params = super.getRequestParamMap(request, false);
		if(StringUtils.isNotEmpty(resId)) {
			params.remove("orgIds");
			smartResp = jqGridServ.queryPage(resId, params, page, getStartNum(page), getPerPageSize(rows));
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				jgGridData = (JqGridData)smartResp.getData();
			}
		}
		return jgGridData;
	}
	
	/**
	 * 数据权限过滤查询
	 * @param request
	 * @param searchParam
	 * @param resId
	 * @param gridRequest 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query/filter/{resId}")
	public @ResponseBody JqGridData queryFilter(HttpServletRequest request, @PathVariable String resId, JqGridRequest gridRequest) throws Exception {
		Integer rows = gridRequest.getRows(getPerPageSize());
		Integer page = gridRequest.getPage();
		JqGridData jgGridData = new JqGridData();
		SmartResponse<JqGridData> smartResp = new SmartResponse<JqGridData>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = super.getRequestParamMap(request, true);
			smartResp = jqGridServ.queryPage(resId, params, page, getStartNum(page), getPerPageSize(rows));
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				jgGridData = (JqGridData)smartResp.getData();
			}
		}
		return jgGridData;
	}
	
}
