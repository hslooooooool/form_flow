package cn.com.smart.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.constant.IConstant;
import cn.com.smart.exception.DaoException;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.helper.ObjectHelper;
import cn.com.smart.helper.ObjectTreeHelper;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.service.impl.BaseServiceImpl;
import cn.com.smart.web.bean.AutoComplete;
import cn.com.smart.web.helper.PageHelper;
import cn.com.smart.web.plugins.ZTreeData;
import cn.com.smart.web.plugins.service.ZTreeService;

/**
 * 
 * @author lmq
 *
 */
@Service("opServ")
public class OPService extends BaseServiceImpl implements IOPService, IConstant {
	
	@Autowired
	private ObjectTreeHelper treeHelper;
	@Autowired
	private ZTreeService zTreeServ;
	
	@Override
	public SmartResponse<Object> getDatas(String resId) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		try {
			if(StringUtils.isNotEmpty(resId)) {
				List<Object> objs = getOPDao().queryDatas(resId);
				if(null != objs && objs.size()>0) {
					objs = ObjectHelper.handleObjDate(objs);
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg(OP_SUCCESS_MSG);
					smartResp.setDatas(objs);
					smartResp.setSize(objs.size());
					smartResp.setTotalNum(objs.size());
				} else {
					smartResp.setResult(OP_NOT_DATA_SUCCESS);
					smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
				}
				objs = null;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<Object> getDatas(String resId,Map<String,Object> params) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		try {
			if(StringUtils.isNotEmpty(resId)) {
				List<Object> objs = getOPDao().queryDatas(resId, params);
				if(null != objs && objs.size()>0) {
					objs = ObjectHelper.handleObjDate(objs);
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg(OP_SUCCESS_MSG);
					smartResp.setDatas(objs);
					smartResp.setSize(objs.size());
					smartResp.setTotalNum(objs.size());
				} else {
					smartResp.setResult(OP_NOT_DATA_SUCCESS);
					smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
				}
				objs = null;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<Object> getDatas(String resId,FilterParam params) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		try {
			if(StringUtils.isNotEmpty(resId)) {
				List<Object> objs = getOPDao().queryDatas(resId, params);
				if(null != objs && objs.size()>0) {
					objs = ObjectHelper.handleObjDate(objs);
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg(OP_SUCCESS_MSG);
					smartResp.setDatas(objs);
					smartResp.setSize(objs.size());
					smartResp.setTotalNum(objs.size());
				} else {
					smartResp.setResult(OP_NOT_DATA_SUCCESS);
					smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
				}
				objs = null;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<Object> getDatas(String resId,int start,int rows) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		smartResp.setResult(OP_NOT_DATA_SUCCESS);
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		try {
			if(StringUtils.isNotEmpty(resId)) {
				long totalNum = getOPDao().count(resId);
				if(start<=totalNum) {
					List<Object> objs = getOPDao().queryDatas(resId,start,rows);
					if(null != objs && objs.size()>0) {
						objs = ObjectHelper.handleObjDate(objs);
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(objs);
						smartResp.setPerPageSize(rows);
						smartResp.setTotalNum(totalNum);
						smartResp.setTotalPage(PageHelper.getTotalPage(totalNum, rows));
						smartResp.setSize(objs.size());
					} 
				   objs = null;
			  }
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<Object> getDatas(String resId,Map<String,Object> params,int start,int rows) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		smartResp.setResult(OP_NOT_DATA_SUCCESS);
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		try {
			if(StringUtils.isNotEmpty(resId)) {
				long totalNum = getOPDao().count(resId, params);
				if(start<=totalNum) {
					List<Object> objs = getOPDao().queryDatas(resId, params,start,rows);
					if(null != objs && objs.size()>0) {
						objs = ObjectHelper.handleObjDate(objs);
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(objs);
						smartResp.setPerPageSize(rows);
						smartResp.setTotalNum(totalNum);
						smartResp.setTotalPage(PageHelper.getTotalPage(totalNum, rows));
						smartResp.setSize(objs.size());
					}
					objs = null;
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<Object> getDatas(String resId,FilterParam params,int start,int rows) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		smartResp.setResult(OP_NOT_DATA_SUCCESS);
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		try {
			if(StringUtils.isNotEmpty(resId)) {
				long totalNum = getOPDao().count(resId, params);
				if(start<=totalNum) {
					List<Object> objs = getOPDao().queryDatas(resId, params,start,rows);
					if(null != objs && objs.size()>0) {
						objs = ObjectHelper.handleObjDate(objs);
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(objs);
						smartResp.setPerPageSize(rows);
						smartResp.setTotalNum(totalNum);
						smartResp.setTotalPage(PageHelper.getTotalPage(totalNum, rows));
						smartResp.setSize(objs.size());
					}
					objs = null;
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<Object> getDatas(String resId,Map<String,Object> params,FilterParam filterParam,int start,int rows) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		smartResp.setResult(OP_NOT_DATA_SUCCESS);
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		try {
			if(StringUtils.isNotEmpty(resId)) {
				long totalNum = getOPDao().count(resId, params,filterParam);
				if(start<=totalNum) {
					List<Object> objs = getOPDao().queryDatas(resId, params,filterParam,start,rows);
					if(null != objs && objs.size()>0) {
						objs = ObjectHelper.handleObjDate(objs);
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(objs);
						smartResp.setPerPageSize(rows);
						smartResp.setTotalNum(totalNum);
						smartResp.setTotalPage(PageHelper.getTotalPage(totalNum, rows));
						smartResp.setSize(objs.size());
					}
					objs = null;
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	
	@Override
	public SmartResponse<Object> getTreeDatas(String resId,Map<String,Object> params) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		try {
			if(StringUtils.isNotEmpty(resId)) {
				List<Object> objs = getOPDao().queryDatas(resId, params);
				if(null != objs && objs.size()>0) {
					try {
						objs = treeHelper.outPutTree(objs);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(null != objs && objs.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(objs);
						smartResp.setTotalNum(objs.size());
					}
				}
				objs = null;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<Object> getTreeDatas(String resId) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		smartResp.setResult(OP_NOT_DATA_SUCCESS);
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		try {
			if(StringUtils.isNotEmpty(resId)) {
				List<Object> objs = getOPDao().queryDatas(resId);
				if(null != objs && objs.size()>0) {
					try {
						objs = treeHelper.outPutTree(objs);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(null != objs && objs.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(objs);
						smartResp.setTotalNum(objs.size());
					}
				}
				objs = null;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<ZTreeData> getZTreeDatas(String resId,Map<String,Object> params) {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		List<ZTreeData> lists = null;
		try {
			if(StringUtils.isNotEmpty(resId)) {
				Object async = params.get("isAsync");
				params.remove("isAsync");
				Boolean isAsync = false;
				if(null != async) {
					try {
						isAsync = Boolean.parseBoolean(async.toString());
					} catch (Exception e) {
						isAsync = false;
					}
					String key = "id";
					Object obj = params.get(key);
					if(null == obj) {
						key = "parentId";
						obj = params.get(key);
					}
					if(null != obj && obj.getClass().isArray()) {
						Object[] objs = (Object[])obj;
						params.put(key, objs[objs.length-1]);
					}
				}
				
				List<Object> objs = getOPDao().queryDatas(resId, params);
				if(null != objs && objs.size()>0) {
					try {
						objs = treeHelper.outPutTree(objs);
						if(null != objs && objs.size()>0) {
							lists = zTreeServ.convert(objs,isAsync);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						objs = null;
					}
					if(null != lists && lists.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(lists);
						smartResp.setTotalNum(lists.size());
					}
					lists = null;
				}
				objs = null;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<ZTreeData> getZTreeDatas(String resId) {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		List<ZTreeData> lists = null;
		try {
			if(StringUtils.isNotEmpty(resId)) {
				List<Object> objs = getOPDao().queryDatas(resId);
				if(null != objs && objs.size()>0) {
					try {
						objs = treeHelper.outPutTree(objs);
						if(null != objs && objs.size()>0) {
							lists = zTreeServ.convert(objs,false);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						objs = null;
					}
					if(null != lists && lists.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(lists);
						smartResp.setTotalNum(lists.size());
					}
					lists = null;
				}
				objs = null;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<AutoComplete> getAutoCompleteDatas(String resId,Map<String,Object> params) {
		SmartResponse<AutoComplete> smartResp = new SmartResponse<AutoComplete>();
		try {
			if(StringUtils.isNotEmpty(resId)) {
				List<Object> objs = getOPDao().queryDatas(resId, params,0,10);
				if(null != objs && objs.size()>0) {
					List<AutoComplete> autoCompleteList = new ArrayList<AutoComplete>();
					AutoComplete autoComplete = null;
					for (Object obj : objs) {
						Object[] objArray = (Object[]) obj;
						autoComplete = new AutoComplete();
						if(objArray.length>=3) {
							autoComplete.setId(StringUtils.handleNull(objArray[0]));
							autoComplete.setValue(StringUtils.handleNull(objArray[1]));
							autoComplete.setLabel(StringUtils.handleNull(objArray[2]));
						} else if(objArray.length==2) {
							autoComplete.setId(StringUtils.handleNull(objArray[0]));
							autoComplete.setValue(StringUtils.handleNull(objArray[1]));
							autoComplete.setLabel(StringUtils.handleNull(objArray[1]));
						} else if(objArray.length==1) {
							autoComplete.setId(StringUtils.handleNull(objArray[0]));
							autoComplete.setValue(StringUtils.handleNull(objArray[0]));
							autoComplete.setLabel(StringUtils.handleNull(objArray[0]));
						} 
						if(objArray.length>3) {
							List<Object> otherValues = new ArrayList<Object>();
							for (int i = 3; i < objArray.length; i++) {
								otherValues.add(objArray[i]);
							}
							autoComplete.setOtherValue(otherValues);
						}
						autoCompleteList.add(autoComplete);
					}
					if(autoCompleteList.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(autoCompleteList);
						smartResp.setSize(autoCompleteList.size());
						smartResp.setTotalNum(autoCompleteList.size());
					}
					autoCompleteList = null;
					autoComplete = null;
				}
				objs = null;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<String> execute(String resId,Map<String,Object> params) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(StringUtils.isNotEmpty(resId)) {
				//判断处理是否有逗号分割的多条数据组合
				for (String key : params.keySet()) {
					Object objValue = params.get(key);
					if(null != objValue && objValue.getClass().isArray()) {
						String value = StringUtils.handleNull(objValue);
						if(StringUtils.isNotEmpty(value) && value.indexOf(MULTI_VALUE_SPLIT)>-1) {
							String[] values = value.split(MULTI_VALUE_SPLIT);
							params.put(key, values);
						}
					}
				}
				if(getOPDao().execute(resId, params)) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg(OP_SUCCESS_MSG);
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smartResp;
	}

	@Override
	public <E> SmartResponse<E> getDatas(String resId, Map<String, Object> params, Class<?> clazz) {
		SmartResponse<E> smartRes = new SmartResponse<E>();
		List<E> lists = getOPDao().queryDatas(resId, params, clazz);
		if(CollectionUtils.isNotEmpty(lists)) {
			smartRes.setResult(OP_SUCCESS);
			smartRes.setDatas(lists);
			smartRes.setMsg(OP_SUCCESS_MSG);
			smartRes.setSize(lists.size());
			smartRes.setTotalNum(lists.size());
		}
		return smartRes;
	}
	
	@Override
	public SmartResponse<Long> count(String resId, Map<String, Object> params) {
		SmartResponse<Long> smartResp = new SmartResponse<Long>();
		if(StringUtils.isEmpty(resId)) {
			return smartResp;
		}
		String sql = SQLResUtil.getOpSqlMap().getSQL(resId);
		if(StringUtils.isNotEmpty(sql)) {
			long num = getOPDao().exeCountSql(sql, params);
			smartResp.setResult(OP_SUCCESS);
			smartResp.setData(num);
			smartResp.setMsg(OP_SUCCESS_MSG);
		}
		return smartResp;
	}
}
