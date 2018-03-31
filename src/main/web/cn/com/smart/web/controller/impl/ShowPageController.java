package cn.com.smart.web.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.web.controller.base.BaseController;

import com.mixsmart.utils.StringUtils;

/**
 * 显示页面
 * @author lmq
 *
 */
@Controller
@RequestMapping("/showPage")
public class ShowPageController extends BaseController {

	/**
	 * 显示页面
	 * @param pagePath 页面路径（目录之间用下划线“_”分隔）
	 * @param id ID
	 * @param busiName 业务名称（不建议使用）
	 * @param op 操作标识（如：add、edit等，也不建议使用）
	 * @return 返回页面视图
	 * @throws Exception
	 */
	@RequestMapping("/{pagePath}")
	public ModelAndView index(@PathVariable String pagePath,
			String id,String busiName,String op) throws Exception {
		ModelAndView modelView = new ModelAndView();
		if(StringUtils.isNotEmpty(pagePath)) {
			String[] params = pagePath.split("_");
			if(params.length>0) {
				String dir = "";
				String viewPage = StringUtils.filterFilePath(params[params.length-1]);
				if(params.length<5 && params.length>0){
					for (int i = 0; i < (params.length-1); i++) {
						dir += StringUtils.filterFilePath(params[i])+"/";
					}
					viewPage = dir+viewPage;
				}
				modelView.setViewName(viewPage);
			}
		}
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("id", id);
		if(StringUtils.isNotEmpty(op)) {
			int sortOrder = 0;
			BaseDaoImpl<?> dao = getBaseDao(busiName);
			if(ADD.equals(op)) {
				sortOrder =	(null != dao)?dao.getSortOrder(id):sortOrder;
				modelMap.put("sortOrder", sortOrder);
			} else if(EDIT.equals(op) && null != dao) {
				Object objBean = dao.find(id);
				modelMap.put("objBean", objBean);
			}
		}
		return modelView;
	}
	
}
