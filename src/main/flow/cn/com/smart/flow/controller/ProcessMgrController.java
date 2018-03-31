package cn.com.smart.flow.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang3.ArrayUtils;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.NodeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.flow.SnakerEngineFacets;
import cn.com.smart.flow.bean.DataClassify;
import cn.com.smart.flow.bean.JumpNodeInfo;
import cn.com.smart.flow.filter.OrderSearchParam;
import cn.com.smart.flow.helper.DataClassifyHelper;
import cn.com.smart.flow.helper.ProcessHelper;
import cn.com.smart.flow.helper.ShowPageNumHelper;
import cn.com.smart.flow.service.FlowService;
import cn.com.smart.flow.service.ProcessFacade;
import cn.com.smart.utils.StringUtil;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.service.OPAuthService;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.tag.bean.BaseBtn;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * 流程实例--管理
 * @author lmq
 * @create 2015年6月14日
 * @version 1.0 
 * @since 
 *
 */
@Controller
@RequestMapping("/process/mgr")
public class ProcessMgrController extends BaseFlowControler {
	
    private static final String VIEW_DIR = "flow/process/mgr";
	
	@Autowired
	private OPService opServ;
	@Autowired
	private ProcessFacade processFacade;
	@Autowired
	private SnakerEngineFacets facets;
	@Autowired
	private OPAuthService authServ;
	@Autowired
	private FlowService flowServ;

	/**
	 * 流程实例列表
	 * @param request
	 * @param modelView
	 * @param searchParam
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/orderList")
	public ModelAndView orderList(HttpServletRequest request,ModelAndView modelView,
			OrderSearchParam searchParam,RequestPage page) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orgIds", StringUtils.list2Array(getUserInfoFromSession(request).getOrgIds()));
		SmartResponse<Object> chRes = opServ.getDatas("process_order_mgr_list",params,searchParam, page.getStartNum(), page.getPageSize());
		SmartResponse<DataClassify<Object>> smartResp = new SmartResponse<DataClassify<Object>>(); 
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		if(OP_SUCCESS.equals(chRes.getResult())) {
			smartResp.setResult(OP_SUCCESS);
			smartResp.setSize(chRes.getSize());
			smartResp.setTotalNum(chRes.getTotalNum());
			smartResp.setTotalPage(chRes.getTotalPage());
			processFacade.getTaskAndActors(chRes);
			smartResp.setDatas(DataClassifyHelper.orderClassify(chRes.getDatas()));
		} 
		chRes = null;
		params = null;
		ModelMap modelMap = modelView.getModelMap();
		String uri = "process/mgr/orderList";
		String paramStr = null;
		paramStr = (null != searchParam)?searchParam.getParamToString():null;
		String searchUri = uri+"?1=1"+(StringUtils.isNotEmpty(paramStr)?("&"+paramStr):"");
		String refreshUri = searchUri+"&page="+page.getPage();
		refreshUri = URLEncoder.encode(refreshUri, "UTF-8");
		modelMap.put("searchUri", searchUri);
		modelMap.put("refreshUri", refreshUri);
		String target = "#process-order-tab";
		refreshBtn = new RefreshBtn(searchUri, null, target);
		pageParam = new PageParam(uri, target, page.getPage(), page.getPageSize());
		
		modelMap.put("searchParam", searchParam);
		modelMap.put("smartResp", smartResp);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		modelMap.put("target", target);
		modelView.setViewName(VIEW_DIR+"/orderList");
		return modelView;
	}
	
	
	/**
	 * 历史流程实例列表
	 * @param request
	 * @param modelView
	 * @param searchParam
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/histOrderList")
	public ModelAndView histOrderList(HttpServletRequest request,ModelAndView modelView,
			OrderSearchParam searchParam,RequestPage page) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orgIds", StringUtils.list2Array(getUserInfoFromSession(request).getOrgIds()));
		SmartResponse<Object> chRes = opServ.getDatas("process_hist_order_mgr_list",params,searchParam, page.getStartNum(), page.getPageSize());
		SmartResponse<DataClassify<Object>> smartResp = new SmartResponse<DataClassify<Object>>();
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		if(OP_SUCCESS.equals(chRes.getResult())) {
			smartResp.setResult(OP_SUCCESS);
			smartResp.setDatas(DataClassifyHelper.orderClassify(chRes.getDatas()));
			smartResp.setTotalNum(chRes.getTotalNum());
			smartResp.setTotalPage(chRes.getTotalPage());
		}
		chRes = null;
		params = null;
		ModelMap modelMap = modelView.getModelMap();
		String uri = "process/mgr/histOrderList";
		String paramStr = null;
		paramStr = (null != searchParam)?searchParam.getParamToString():null;
		String searchUri = uri+"?1=1"+(StringUtils.isNotEmpty(paramStr)?("&"+paramStr):"");
		modelMap.put("searchUri", searchUri);
		
		String refreshUri = searchUri+"&page="+page.getPage();
		refreshUri = URLEncoder.encode(refreshUri, "UTF-8");
		
		String target = "#process-hist-order-tab";
		refreshBtn = new RefreshBtn(searchUri, null, target);
		pageParam = new PageParam(uri, target, page.getPage(), page.getPageSize());
		delBtn = new DelBtn("process/mgr/delete", "您确定要删除选中的所有流程实例吗？", uri, "#process-order-tab", null);
		
		modelMap.put("searchParam", searchParam);
		modelMap.put("smartResp", smartResp);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		modelMap.put("delBtn", delBtn);
		modelMap.put("target", target);
		modelView.setViewName(VIEW_DIR+"/histOrderList");
		return modelView;
	}
	
	
	/**
	 * 删除流程实例
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public SmartResponse<String> delOrder(String id) throws Exception {
		SmartResponse<String> smartResp = processFacade.deleteOrder(id);
		return smartResp;
	}
	
	
	/**
	 * 任意节点跳转设置
	 * @param modelView
	 * @param processId
	 * @param orderId
	 * @param refreshUri 刷新URI
	 * @return
	 */
	@RequestMapping("/jumpNodeSet")
	public ModelAndView jumpNodeSet(ModelAndView modelView,String processId,String orderId, String refreshUri) {
		SmartResponse<JumpNodeInfo> smartResp = new SmartResponse<JumpNodeInfo>();
		List<NodeModel> models = facets.getProcess(processId).getModel().getModels(NodeModel.class);
		List<JumpNodeInfo> jumpNodeInfos = null;
		List<Task> tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
		String currentKey = null;
		if(null != tasks && tasks.size()>0) {
			currentKey = tasks.get(0).getTaskName();
		}
		if(null != models && models.size()>0) {
			jumpNodeInfos = ProcessHelper.handleJumpNodes(processFacade, models, orderId, currentKey);
			smartResp.setResult(OP_SUCCESS);
			smartResp.setDatas(jumpNodeInfos);
		}
		ModelMap modelMap = modelView.getModelMap();
		if(StringUtils.isNotEmpty(refreshUri)) {
			try {
				refreshUri = URLDecoder.decode(refreshUri, "UTF-8");
				modelMap.put("refreshUri", refreshUri);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		modelMap.put("smartResp", smartResp);
		modelView.setViewName(VIEW_DIR+"/jumpNodeSet");
		return modelView;
	}
	
	
	/**
	 * 任意跳转到指定任务
	 * @param session
	 * @param orderId
	 * @param processId
	 * @param jumpNodeName
	 * @param nextAssigner
	 * @param handleSuggest
	 * @param isBack
	 * @return
	 */
	@RequestMapping("/jumpToTask")
	@ResponseBody
	public SmartResponse<String> jumpToTask(HttpSession session,String processId,String orderId,
			String jumpNodeName, String nextAssigner, String handleSuggest, String isBack) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(jumpNodeName) && StringUtils.isNotEmpty(handleSuggest)) {
			UserInfo userInfo = getUserInfoFromSession(session);
			//判断是否有权限任意跳转(只有设置了相应的权限才能执行改方法)
			if(authServ.isAuth("process/mgr/orderList", new BaseBtn("jump_task"), userInfo.getRoleIds())) {
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("handleSuggest", StringUtil.escapeHtml(handleSuggest));
				params.put("operator", userInfo.getId());
				params.put("isBack", isBack);
				params.put("nextLineName", "jump_"+jumpNodeName);
				Map<String, Object> nextAssigners = null;
				if(StringUtils.isNotEmpty(nextAssigner)) {
					String[] nextAssignerArray = nextAssigner.split(MULTI_VALUE_SPLIT);
					List<String> nextAssignerList = Arrays.asList(nextAssignerArray);
					nextAssigners = new HashMap<String, Object>();
					nextAssigners.put(jumpNodeName, nextAssignerList);
				}
				smartResp = processFacade.executeAndJump(processId,orderId, SnakerEngine.ADMIN, nextAssigners, params, jumpNodeName);
			} else {
				smartResp.setMsg("没有权限执行该方法");
			}
		}
		return smartResp;
	}
	
	/**
	 * 重新唤醒流程实例（即：重新流转流程实例）
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/resume")
	@ResponseBody
	public SmartResponse<String> resume(String orderId) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(orderId)) {
			try {
				Order order = facets.getEngine().order().resume(orderId);
				if(null != order) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("流程实例唤醒成功");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return smartResp;
	}
	
	/**
	 * 获取不能正常流转的流程实例列表 <br/>
	 * 如：流程修改后缺少流程节点的
	 * @param processId 流程ID
	 * @param searchParam 搜索参数
	 * @return
	 */
	@RequestMapping("/abnormal")
	public ModelAndView abnormalOrderList(String processId, OrderSearchParam searchParam, RequestPage page) {
		ModelAndView modelView = new ModelAndView();
		Map<String,Object> params = new HashMap<String, Object>();
		String[] orderIds = flowServ.getAbnormalOrderIds(processId);
		SmartResponse<Object> chRes = new SmartResponse<Object>();
		if(ArrayUtils.isNotEmpty(orderIds)) {
			params.put("orderIds", orderIds);
			chRes = opServ.getDatas("get_abnormal_order_list",params,searchParam,page.getStartNum(), page.getPageSize());
		}
		SmartResponse<DataClassify<Object>> smartResp = new SmartResponse<DataClassify<Object>>(); 
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		if(OP_SUCCESS.equals(chRes.getResult())) {
			smartResp.setResult(OP_SUCCESS);
			smartResp.setSize(chRes.getSize());
			smartResp.setTotalNum(chRes.getTotalNum());
			smartResp.setTotalPage(chRes.getTotalPage());
			//processFacade.getTaskAndActors(chRes);
			smartResp.setDatas(DataClassifyHelper.orderClassify(chRes.getDatas()));
		} 
		chRes = null;
		params = null;
		ModelMap modelMap = modelView.getModelMap();
		String uri = "process/mgr/abnormal";
		String paramStr = null;
		paramStr = (null != searchParam)?searchParam.getParamToString():null;
		String searchUri = uri+"?1=1"+(StringUtils.isNotEmpty(paramStr)?("&"+paramStr):"");
		String refreshUri = searchUri+"&page="+page.getPage();
		try {
			refreshUri = URLEncoder.encode(refreshUri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		modelMap.put("searchUri", searchUri);
		modelMap.put("uri", uri);
		modelMap.put("refreshUri", refreshUri);
		modelMap.put("searchParam", searchParam);
		modelMap.put("smartResp", smartResp);
		modelView.setViewName(VIEW_DIR+"/abnormalOrderList");
		//处理分页数字
		List<String> pageNums = ShowPageNumHelper.showNumHandle(smartResp, page.getPage());
		modelMap.put("pageNums", pageNums.size()>0?pageNums:null);
		modelMap.put("page", page);
		return modelView;
	}

	/**
	 * 批量跳转视图
	 * @return
	 */
	@RequestMapping("/batchJumpView")
	public ModelAndView batchJumpView() {
		ModelAndView modelView = new ModelAndView();
		ModelMap modelMap = modelView.getModelMap();
		QueryFilter queryFilter = new QueryFilter();
		queryFilter.setState(YesNoType.YES.getIndex());
		List<org.snaker.engine.entity.Process> processList =  facets.getEngine().process().getProcesss(queryFilter);
		if(CollectionUtils.isNotEmpty(processList)) {
			modelMap.put("processList", processList);
			List<NodeModel> models = facets.getProcess(processList.get(0).getId()).getModel().getModels(NodeModel.class);
			List<JumpNodeInfo> list = ProcessHelper.handleJumpNodes(processFacade, models, null, null);
			if(CollectionUtils.isNotEmpty(list)) {
				modelMap.put("jumpNodeInfos", list);
			}
		}
		modelView.setViewName(VIEW_DIR+"/batchJumpView");
		return modelView;
	}
	
	/**
	 * 批量跳转提交
	 * @param processId 流程ID
	 * @param jumpNodeName 任务名称（即：任务KEY）
	 * @param batchContent 批量处理内容
	 * @return
	 */
	@RequestMapping(value="/batchJump", method=RequestMethod.POST)
	@ResponseBody
	public SmartResponse<String> batchJump(HttpSession session, String processId, 
			String jumpNodeName, String batchContent, String nextAssigner, String handleSuggest, String isBack) {
		if(StringUtils.isEmpty(processId) || StringUtils.isEmpty(jumpNodeName) 
				|| StringUtils.isEmpty(batchContent)) {
			throw new NullArgumentException("请求参数不能为空");
		}
		UserInfo userInfo = super.getUserInfoFromSession(session);
		SmartResponse<String> smartResp = flowServ.batchJump(processFacade, userInfo.getId(), processId, jumpNodeName, batchContent, nextAssigner, handleSuggest, isBack);
		return smartResp;
	}
	
	/**
	 * 获取跳转节点信息
	 * @param processId 流程ID
	 * @return
	 */
	@RequestMapping("/jumpNodeInfo")
	@ResponseBody
	public SmartResponse<JumpNodeInfo> jumpNodeInfo(String processId) {
		if(StringUtils.isEmpty(processId)) {
			throw new NullArgumentException("请求参数不能为空");
		}
		SmartResponse<JumpNodeInfo> smartResp = new SmartResponse<JumpNodeInfo>();
		List<NodeModel> models = facets.getProcess(processId).getModel().getModels(NodeModel.class);
		List<JumpNodeInfo> list = ProcessHelper.handleJumpNodes(processFacade, models, null, null);
		if(CollectionUtils.isNotEmpty(list)) {
			smartResp.setDatas(list);
			smartResp.setResult(OP_SUCCESS);
			smartResp.setMsg(OP_SUCCESS_MSG);
		}
		return smartResp;
	}
}
