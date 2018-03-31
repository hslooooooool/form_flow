package cn.com.smart.flow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 流程节点控制器类
 * @author lmq
 *
 */
@Controller
@RequestMapping("/flow/node")
public class FlowNodeControler extends BaseFlowControler {

	private static final String VIEW_DIR = "flow/node";
	
	/**
	 * 选择参与者
	 * @param modelView
	 * @return
	 */
	@RequestMapping("/assignee")
	public ModelAndView assignee(ModelAndView modelView,String selectedValues) throws Exception {
		modelView.setViewName(VIEW_DIR+"/assignee");
		modelView.getModelMap().put("selectedValues", selectedValues);
		return modelView;
	}
}
