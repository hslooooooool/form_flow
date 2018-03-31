package cn.com.smart.web.plugins.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.web.constant.IWebConstant;
import cn.com.smart.web.plugins.JqGridData;
import cn.com.smart.web.plugins.JqGridRows;
import cn.com.smart.web.plugins.JqGridSearch;
import cn.com.smart.web.service.OPService;

import com.mixsmart.utils.StringUtils;

/**
 * JqGrid插件服务类
 * @author lmq
 *
 */
@Service
public class JqGridService {

	@Autowired
	private OPService opServ;
	
	/**
	 * 分页查询
	 * @param resId
	 * @param searchParams
	 * @param page
	 * @param start
	 * @param rows
	 * @return
	 */
	public SmartResponse<JqGridData> queryPage(String resId,JqGridSearch searchParams,int page,int start,int rows) throws ServiceException {
		SmartResponse<JqGridData> smartResp = new SmartResponse<JqGridData>();
		if(StringUtils.isNotEmpty(resId)) {
			SmartResponse<Object> chRes = opServ.getDatas(resId, searchParams, start, rows);
			handleDatas(smartResp, chRes, page);
		}
		return smartResp;
	}
	
	/**
	 * 分页查询
	 * @param resId
	 * @param searchParams
	 * @param page
	 * @param start
	 * @param rows
	 * @return
	 */
	public SmartResponse<JqGridData> queryPage(String resId,Map<String, Object> searchParams,int page,int start,int rows) throws ServiceException {
		SmartResponse<JqGridData> smartResp = new SmartResponse<JqGridData>();
		if(StringUtils.isNotEmpty(resId)) {
			SmartResponse<Object> chRes = opServ.getDatas(resId, searchParams, start, rows);
			handleDatas(smartResp, chRes, page);
		}
		return smartResp;
	}
	
	/**
	 * 处理数据
	 * @param smartResp
	 * @param chRes
	 * @param page
	 */
	private void handleDatas(SmartResponse<JqGridData> smartResp, SmartResponse<Object> chRes, int page) {
		if(IWebConstant.OP_SUCCESS.equals(chRes.getResult())) {
			JqGridData jqGridData = new JqGridData();
			jqGridData.setPage(page);
			jqGridData.setRecords(chRes.getTotalNum());
			jqGridData.setTotal(chRes.getTotalPage());
			List<Object> objs = chRes.getDatas();
			if(null != objs && objs.size()>0) {
				List<JqGridRows> jqGridRows = new ArrayList<JqGridRows>();
				JqGridRows jqGridRow = null;
				for (Object obj : objs) {
					Object[] objArray = (Object[])obj;
					jqGridRow = new JqGridRows();
					jqGridRow.setId(StringUtils.handleNull(objArray[0]));
					jqGridRow.setCell(obj);
					jqGridRows.add(jqGridRow);
				}
				jqGridData.setRows(jqGridRows);
				jqGridRow = null;
				smartResp.setData(jqGridData);
				smartResp.setResult(IWebConstant.OP_SUCCESS);
				smartResp.setMsg(IWebConstant.OP_SUCCESS_MSG);
				jqGridRows = null;
				jqGridData = null;
			}//if
			objs = null;
		}
	}
}
