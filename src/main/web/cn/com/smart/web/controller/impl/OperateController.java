package cn.com.smart.web.controller.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.web.bean.AutoComplete;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.plugins.ZTreeData;
import cn.com.smart.web.service.OPService;

import com.mixsmart.utils.StringUtils;

/**
 * 操作控制器类
 * @author lmq
 * @version 1.0 2015年8月23日
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/op")
public class OperateController extends BaseController {

	@Autowired
	private OPService opServ;
	
	/**
	 * 删除（不建议使用）
	 * @param busiName dao名称，但不含后缀Dao；<br />
	 * 如：dictDao，则busiName为“dict”,dao的命名规则为：名称+Dao
	 * @param id
	 * @return 返回JSON格式
	 * @throws Exception
	 */
	@Deprecated
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> del(String busiName,String id) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(busiName) && StringUtils.isNotEmpty(id)) {
			BaseDaoImpl<?> dao = getBaseDao(busiName);
			if(null != dao && dao.delete(id)) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
			}
		}
		return smartResp;
	}
	
	
	/**
	 * 删除 （不建议使用）
	 * @param busiName dao名称，但不含后缀Dao；<br />
	 * 如：dictDao，则busiName为“dict”,dao的命名规则为：名称+Dao
	 * @param id
	 * @return 返回JSON格式
	 * @throws Exception
	 */
	@Deprecated
	@RequestMapping(value="/del/{resId}",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> delByResId(@PathVariable String resId,String id) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(resId) && StringUtils.isNotEmpty(id)) {
			Map<String,Object> params = new HashMap<String, Object>(1);
			params.put("id", id);
			smartResp = opServ.execute(resId, params);
		}
		return smartResp;
	}
	
	
	/**
	 * 根据多个条件删除数据  （不建议使用）
	 * @param request
	 * @param busiName dao名称，但不含后缀Dao；<br />
	 * 如：dictDao，则busiName为“dict”,dao的命名规则为：名称+Dao
	 * @param id
	 * @return 返回JSON格式
	 * @throws Exception
	 */
	@Deprecated
	@RequestMapping(value="/moreParamDel",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> moreParamDel(HttpServletRequest request,String busiName,String id) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(busiName) && StringUtils.isNotEmpty(id)) {
			Map<String,Object> paramMaps = getRequestParamMap(request, false);
			paramMaps.remove("busiName");
			BaseDaoImpl<?> dao = getBaseDao(busiName);
			if(null != dao && dao.delete(paramMaps)) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
			}
			paramMaps = null;
		}
		return smartResp;
	}
	
	
	/**
	 * 执行resId对于的SQL语句(参数通过HttpServletRequest获取)
	 * @param request 
	 * @param resId SQL语句名称
	 * @return 返回JSON格式数据
	 */
	@Deprecated
	@RequestMapping(value="/executeReq/{resId}",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> execute(HttpServletRequest request,@PathVariable String resId) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String,Object> paramMaps = getRequestParamMap(request, false);
			smartResp = opServ.execute(resId, paramMaps);
		}
		return smartResp;
	}
	
	
	/**
	 * 执行resId对于的语句(SQL语句)
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@Deprecated
	@RequestMapping(value="/execute/{resId}",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> execute(HttpSession session,@PathVariable String resId,
			String paramName,String paramValue) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getParamMaps(session, paramName, paramValue, false);
			smartResp = opServ.execute(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	
	/**
	 * 查询数据
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/query/{resId}")
	public @ResponseBody SmartResponse<Object> query(HttpSession session,@PathVariable String resId,
			String paramName,String paramValue) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getParamMaps(session, paramName, paramValue, false);
			smartResp = opServ.getDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 查询数据；支持按部门过滤
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/query/filter/{resId}")
	public @ResponseBody SmartResponse<Object> queryFilter(HttpSession session,@PathVariable String resId,
			String paramName,String paramValue) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getParamMaps(session, paramName, paramValue, true);
			smartResp = opServ.getDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 查询数据(参数通过HttpServletRequest获取)
	 * @param session
	 * @param resId
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/queryReq/{resId}")
	public @ResponseBody SmartResponse<Object> queryReq(HttpServletRequest request,@PathVariable String resId) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getRequestParamMap(request, false);
			smartResp = opServ.getDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 查询数据(参数通过HttpServletRequest获取)；支持按部门过滤
	 * @param session
	 * @param resId
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/queryReq/filter/{resId}")
	public @ResponseBody SmartResponse<Object> queryReqFilter(HttpServletRequest request,@PathVariable String resId) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getRequestParamMap(request, true);
			smartResp = opServ.getDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 查询数据(分页)
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @param page 
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/queryPage/{resId}")
	public @ResponseBody SmartResponse<Object> queryPage(HttpSession session,@PathVariable String resId,
			String paramName,String paramValue, RequestPage page) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getParamMaps(session, paramName, paramValue, false);
			smartResp = opServ.getDatas(resId, params, page.getStartNum(), page.getPageSize());
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 查询数据(分页)；支持按部门过滤
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @param page 
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/queryPage/filter/{resId}")
	public @ResponseBody SmartResponse<Object> queryPageFilter(HttpSession session,@PathVariable String resId,
			String paramName,String paramValue,RequestPage page) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getParamMaps(session, paramName, paramValue, true);
			smartResp = opServ.getDatas(resId, params, page.getStartNum(), page.getPageSize());
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 查询数据(分页)(参数通过HttpServletRequest获取)
	 * @param session
	 * @param resId
	 * @param page
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/queryReqPage/{resId}")
	public @ResponseBody SmartResponse<Object> queryReqPage(HttpServletRequest request,@PathVariable String resId,RequestPage page) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getRequestParamMap(request, false);
			params.put("orgIds", StringUtils.list2Array(getUserInfoFromSession(request).getOrgIds()));
			smartResp = opServ.getDatas(resId, params, page.getStartNum(), page.getPageSize());
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 查询数据(分页)(参数通过HttpServletRequest获取)；支持按部门过滤
	 * @param session
	 * @param resId
	 * @param page
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/queryReqPage/filter/{resId}")
	public @ResponseBody SmartResponse<Object> queryReqPageFilter(HttpServletRequest request,@PathVariable String resId,RequestPage page) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getRequestParamMap(request, true);
			smartResp = opServ.getDatas(resId, params, page.getStartNum(), page.getPageSize());
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 查询树结构
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/queryTree/{resId}")
	public @ResponseBody SmartResponse<ZTreeData> queryTree(HttpServletRequest request,@PathVariable String resId) throws Exception {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getRequestParamMap(request, false);
			smartResp = opServ.getZTreeDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 查询树结构；支持按部门过滤
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/queryTree/filter/{resId}")
	public @ResponseBody SmartResponse<ZTreeData> queryTreeFilter(HttpServletRequest request,@PathVariable String resId) throws Exception {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getRequestParamMap(request, true);
			smartResp = opServ.getZTreeDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 查询树结构
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/queryTreeReq/{resId}")
	@Deprecated
	public @ResponseBody SmartResponse<ZTreeData> queryTree(HttpSession session,@PathVariable String resId,
			String paramName,String paramValue) throws Exception {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getParamMaps(session, paramName, paramValue, false);
			smartResp = opServ.getZTreeDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 查询树结构；支持按部门过滤
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/queryTreeReq/filter/{resId}")
	@Deprecated
	public @ResponseBody SmartResponse<ZTreeData> queryTreeFilter(HttpSession session,@PathVariable String resId,
			String paramName,String paramValue) throws Exception {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getParamMaps(session, paramName, paramValue, true);
			smartResp = opServ.getZTreeDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	@RequestMapping("/treeAsync/{resId}")
	@ResponseBody
	public SmartResponse<ZTreeData> treeAsync(HttpServletRequest request,@PathVariable String resId) {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getRequestParamMap(request, false);
			params.put("isAsync", true);
			Object obj = params.get("id");
			if(null != obj && obj.getClass().isArray()) {
				Object[] objs = (Object[])obj;
				params.put("id",objs[objs.length-1]);
			}
			smartResp = opServ.getZTreeDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	@RequestMapping("/treeAsync/filter/{resId}")
	@ResponseBody
	public SmartResponse<ZTreeData> treeAsyncFilter(HttpServletRequest request,@PathVariable String resId) {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getRequestParamMap(request, true);
			params.put("isAsync", true);
			Object obj = params.get("id");
			if(null != obj && obj.getClass().isArray()) {
				Object[] objs = (Object[])obj;
				params.put("id",objs[objs.length-1]);
			}
			smartResp = opServ.getZTreeDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	
	/**
	 * 自动完成(输入提示)
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/autoComplete/{resId}")
	public @ResponseBody SmartResponse<AutoComplete> autoComplete(HttpSession session,@PathVariable String resId,
			String paramName,String paramValue) throws Exception {
		SmartResponse<AutoComplete> smartResp = new SmartResponse<AutoComplete>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getParamMaps(session, paramName, paramValue, false);
			smartResp = opServ.getAutoCompleteDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 自动完成(输入提示)
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/autoComplete/filter/{resId}")
	public @ResponseBody SmartResponse<AutoComplete> autoCompleteFilter(HttpSession session,@PathVariable String resId,
			String paramName,String paramValue) throws Exception {
		SmartResponse<AutoComplete> smartResp = new SmartResponse<AutoComplete>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getParamMaps(session, paramName, paramValue, true);
			smartResp = opServ.getAutoCompleteDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 自动完成(输入提示)
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/autoCompleteReq/{resId}")
	public @ResponseBody SmartResponse<AutoComplete> autoCompleteReq(HttpServletRequest request,@PathVariable String resId) throws Exception {
		SmartResponse<AutoComplete> smartResp = new SmartResponse<AutoComplete>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getRequestParamMap(request, false);
			smartResp = opServ.getAutoCompleteDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
	
	/**
	 * 自动完成(输入提示) 支持按部门过滤
	 * @param session
	 * @param resId
	 * @param paramName
	 * @param paramValue
	 * @return 返回JSON格式数据
	 * @throws Exception
	 */
	@RequestMapping("/autoCompleteReq/filter/{resId}")
	public @ResponseBody SmartResponse<AutoComplete> autoCompleteReqFilter(HttpServletRequest request,@PathVariable String resId) throws Exception {
		SmartResponse<AutoComplete> smartResp = new SmartResponse<AutoComplete>();
		if(StringUtils.isNotEmpty(resId)) {
			Map<String, Object> params = getRequestParamMap(request, true);
			smartResp = opServ.getAutoCompleteDatas(resId, params);
			params = null;
		}
		return smartResp;
	}
}
