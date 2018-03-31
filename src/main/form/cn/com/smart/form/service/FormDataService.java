package cn.com.smart.form.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.constant.IConstant;
import cn.com.smart.dao.impl.OPDao;
import cn.com.smart.exception.DaoException;
import cn.com.smart.form.bean.QueryFormData;
import cn.com.smart.form.bean.TableFieldMap;
import cn.com.smart.form.enums.FormPluginType;
import cn.com.smart.form.helper.FormDataHelper;
import cn.com.smart.form.interceptor.SubmitFormContext;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.utils.DateUtil;
import cn.com.smart.web.constant.IWebConstant;

/**
 * 处理表单数据
 * @author lmq <br />
 * 2015年7月7日
 * @version 1.0
 * @since 1.0
 */
@Service
public class FormDataService implements IFormDataService {
    
	@Autowired
	private OPDao opDao;
	@Autowired
	private FormTableService formTableServ;
	@Autowired
	private FormTableFieldServ formTableFieldServ;
	
	@Override
	public SmartResponse<QueryFormData> getFormData(String formDataId, String formId,String userId) {
		SmartResponse<QueryFormData> smartResp = new SmartResponse<QueryFormData>();
		List<TableFieldMap> tfMaps = formTableServ.tableFieldMap(formId);
		if(null != tfMaps && tfMaps.size()>0) {
			try {
				querySqlAndExe(tfMaps, formDataId, userId);//处理tfMaps
				List<QueryFormData> formDatas = classifyComposite(tfMaps);
				if(CollectionUtils.isNotEmpty(formDatas)) {
					smartResp.setResult(IWebConstant.OP_SUCCESS);
					smartResp.setMsg(IWebConstant.OP_SUCCESS_MSG);
					smartResp.setDatas(formDatas);
					smartResp.setSize(formDatas.size());
					smartResp.setTotalNum(formDatas.size());
				}
			} catch(DaoException ex) {
				ex.printStackTrace();
			}
		}
		return smartResp;
	}
	
	@Override
    public SmartResponse<String> saveOrUpdateForm(Map<String, Object> datas, String formDataId, String formId,
            String userId, Integer formState) {
	    SmartResponse<String> smartResp = new SmartResponse<String>();
        //判断数据是否存在
        if(null == datas || StringUtils.isEmpty(formId)) {
            return smartResp;
        }
        if(StringUtils.isEmpty(formDataId) || 
                formDataId.startsWith(FormDataHelper.APP_NEW_PREFIX)) {
            String id = this.saveForm(datas, formId, FormDataHelper.handleFormDataId(formDataId), userId, formState);
            if(StringUtils.isNotEmpty(id)) {
                smartResp.setResult(IWebConstant.OP_SUCCESS);
                smartResp.setMsg("表单数据保存成功");
                smartResp.setData(id);
            }
        } else {
            boolean is = SubmitFormContext.getInstance().before(formId, formDataId, datas, userId);
            if(is && this.updateForm(datas, formId, formDataId, userId, formState)){
                smartResp.setResult(IWebConstant.OP_SUCCESS);
                smartResp.setMsg("表单数据保存成功");
                smartResp.setData(formDataId);
            }
        }
        YesNoType state = YesNoType.NO;
        if(IWebConstant.OP_SUCCESS.equals(smartResp.getResult())) {
            state = YesNoType.YES;
        }
        SubmitFormContext.getInstance().after(state, formId, formDataId, datas, userId);
        return smartResp;
    }
	
	@Override
	public SmartResponse<QueryFormData> getFormDataByFormDataId(String formDataId,String formId) {
		SmartResponse<QueryFormData> smartResp = new SmartResponse<QueryFormData>();
		List<TableFieldMap> tfMaps = formTableServ.tableFieldMap(formId);
		if(null != tfMaps && tfMaps.size()>0) {
			try {
				querySqlAndExeByFormDataId(tfMaps, formDataId);//处理tfMaps
				List<QueryFormData> formDatas = classifyComposite(tfMaps);
				if(CollectionUtils.isNotEmpty(formDatas)) {
					smartResp.setResult(IWebConstant.OP_SUCCESS);
					smartResp.setMsg(IWebConstant.OP_SUCCESS_MSG);
					smartResp.setDatas(formDatas);
					smartResp.setSize(formDatas.size());
					smartResp.setTotalNum(formDatas.size());
				}
			} catch(DaoException ex) {
				ex.printStackTrace();
			}
		}
		return smartResp;
	}
	
	@Override
    public String saveForm(Map<String,Object> datas,String formId, String formDataId ,String userId,Integer formState) {
        if(StringUtils.isEmpty(formDataId)) {
            formDataId = StringUtils.createSerialNum();
        } else {
            formDataId = FormDataHelper.handleFormDataId(formDataId);
        }
        //表单保存前拦截
        boolean is = SubmitFormContext.getInstance().before(formId, formDataId, datas, userId);
        if (!is)
            return null;
        List<TableFieldMap> tfMaps = formTableServ.tableFieldMap(formId);
        if(null == tfMaps || tfMaps.size()<1) {
            return null;
        }
        Map<String,List<TableFieldMap>> tableMaps = assignmentFormData(datas, tfMaps);
        tfMaps = null;
        //拼SQL语句
        try {
            for (String key : tableMaps.keySet()) {
                insertData(key, tableMaps.get(key), userId, formState, formDataId);
            }//for
        } catch (DaoException ex) {
            ex.printStackTrace();
            formDataId = null;
        } finally {
            tableMaps = null;
        }
        return formDataId;
    }
	
	@Override
	public String saveForm(Map<String,Object> datas,String formId,String userId,Integer formState) {
		return saveForm(datas, formId, null, userId, formState);
	}
	
	@Override
	public boolean updateForm(Map<String,Object> datas,String formId,String formDataId,String userId,Integer formState) {
		boolean is = false;
		List<TableFieldMap> tfMaps = formTableServ.tableFieldMap(formId);
		if(null == tfMaps || tfMaps.size()<1) {
			return is;
		}
		Map<String,List<TableFieldMap>> tableMaps = assignmentFormData(datas, tfMaps);
		tfMaps = null;
		StringBuilder fieldBuild = null;
		StringBuilder sqlBuild = null;
		List<TableFieldMap> tfList = null;
		Map<String, Object> params = null;
		//拼SQL语句
		try {
			for (String key : tableMaps.keySet()) { //key为表名
				params = new HashMap<String, Object>();
				tfList = tableMaps.get(key);
				TableFieldMap tableFieldMap = tfList.get(0);
				if(FormPluginType.Listctrl.getValue().equals(tableFieldMap.getPlugin())) {
					String tableId = tableFieldMap.getTableId();
					String delParamName = tableId+"_del";
					String changeParamName = tableId+"_change";
					String idParamName = tableId+"_id";
					String delId = StringUtils.handleNull(datas.get(delParamName));
					Object id = datas.get(idParamName);
					String[] idArray = null;
					if(null != id) {
						if(id.getClass().isArray()) {
							Object[] ids = (Object[])id;
							idArray = new String[ids.length];
							for (int i = 0; i < ids.length; i++) {
								idArray[i] = StringUtils.handleNull(ids[i]);
							}
						} else {
							idArray = new String[]{StringUtils.handleNull(id)};
						}
					}
					//删除的
					if(StringUtils.isNotEmpty(delId)) {
						String deleteSql = "delete from "+key+" where id in(:ids)";
						String[] delIdArray = delId.split(IWebConstant.MULTI_VALUE_SPLIT);
						params.put("ids", delIdArray);
						opDao.executeSql(deleteSql, params);
					}
					//更新
					String changeId = StringUtils.handleNull(datas.get(changeParamName));
					if(StringUtils.isNotEmpty(changeId)) {
						String[] changeIdArray = changeId.split(IWebConstant.MULTI_VALUE_SPLIT);
						if(null != idArray) {
							sqlBuild = new StringBuilder();
							fieldBuild = new StringBuilder();
							sqlBuild.append("update "+key+" set ");
							for (int i = 1; i < tfList.size(); i++) {
								fieldBuild.append(tfList.get(i).getTableFieldName()+"=:"+tfList.get(i).getTableFieldName()+",");
							}
							fieldBuild = fieldBuild.delete(fieldBuild.length()-1, fieldBuild.length());
							sqlBuild.append(fieldBuild.toString()+" where id=:id");
							List<Integer> indexs = new ArrayList<Integer>(changeIdArray.length);
							List<Map<String, Object>> paramList = new ArrayList<Map<String,Object>>();
							boolean isValueNull = true; //判断值是否都为空
							Set<String> valueNullIds = new HashSet<String>(changeIdArray.length);
							for (int i = 0; i < changeIdArray.length; i++) {
								int index = Arrays.binarySearch(idArray, changeIdArray[i]);
								isValueNull = true;
								if(index > -1) {
									indexs.add(index);
									Map<String, Object> param = new HashMap<String, Object>();
									for (int j = 1; j < tfList.size(); j++) {
										TableFieldMap fieldMap = tfList.get(j);
										if(null != fieldMap.getValue() && index == 0) {
											param.put(fieldMap.getTableFieldName(), fieldMap.getValue());
											if(StringUtils.isNotEmpty(StringUtils.handleNull(fieldMap.getValue()))) {
												isValueNull = isValueNull && false;
											} else {
												isValueNull = isValueNull && true;
											}
										} else {
											if(null != fieldMap.getValues() && fieldMap.getValues().size() > index) {
												Object value = fieldMap.getValues().get(index);
												param.put(fieldMap.getTableFieldName(), value);
												if(StringUtils.isNotEmpty(StringUtils.handleNull(value))) {
													isValueNull = isValueNull && false;
												} else {
													isValueNull = isValueNull && true;
												}
											}
										}
									} //for
									param.put("id", changeIdArray[i]);
									if (!isValueNull) 
										paramList.add(param);
									else {
										valueNullIds.add(changeIdArray[i]);
									}
								}//if
							}//for
							//删除ID不为空，值全为空的记录
							if(CollectionUtils.isNotEmpty(valueNullIds)) {
								String deleteSql = "delete from "+key+" where id in(:ids)";
								params.clear();
								params.put("ids", valueNullIds.toArray());
								opDao.executeSql(deleteSql, params);
							}
							if(CollectionUtils.isNotEmpty(paramList)) {
								opDao.executeSql(sqlBuild.toString(), paramList);
							}
							if(CollectionUtils.isNotEmpty(indexs)) {
								tfList = delFieldValue(tfList, indexs);
							}
						}//if
					}
					//插入
					if(CollectionUtils.isNotEmpty(tfList)) {
						TableFieldMap idFieldMap = tfList.get(0);
						Object value = idFieldMap.getValue();
						List<Integer> indexs = new ArrayList<Integer>();
						//修复ID值为“”时，无法保存数据问题
						if(null != value && StringUtils.isNotEmpty(value.toString())) {
							indexs.add(0);
						} else {
							List<Object> list = idFieldMap.getValues();
							for (int i = 0; i < list.size(); i++) {
								if(StringUtils.isNotEmpty(StringUtils.handleNull(list.get(i)))) 
									indexs.add(i);
							}
						}
						tfList = delFieldValue(tfList, indexs);
						if(null != tfList && tfList.size() > 0) {
							tfList.remove(0);
							insertData(key, tfList, userId, formState, formDataId);
						}
					}
				} else if(this.isExistData(key,formDataId)) {
					fieldBuild = new StringBuilder();
					sqlBuild = new StringBuilder();
					sqlBuild.append("update "+key+" set ");
					for (TableFieldMap tf : tfList) {
						if(null != tf.getValue()) {
							fieldBuild.append(tf.getTableFieldName()+"=:"+tf.getTableFieldName()+",");
							params.put(tf.getTableFieldName(), tf.getValue());
						} //if
					} //for
					params.put("state", formState);
					params.put("formDataId", formDataId);
					sqlBuild.append(fieldBuild.toString()+"state=:state where form_data_id=:formDataId");
					opDao.executeSql(sqlBuild.toString(), params);
				} else {
					insertData(key, tfList, userId, formState, formDataId);
				}
			}//for
			is = true;
		} catch (DaoException ex) {
			ex.printStackTrace();
		}
		return is;
	}
	
	/**
     * 分类组合数据
     * @param tfMaps
     * @return
     */
    private List<QueryFormData> classifyComposite(List<TableFieldMap> tfMaps) {
        List<QueryFormData> formDatas = new ArrayList<QueryFormData>();
        QueryFormData formData = null;
        //表单分类
        Map<String,List<TableFieldMap>> formTypeMaps =  formTypelassify(tfMaps);
        for(String key : formTypeMaps.keySet()) {
            tfMaps = formTypeMaps.get(key);
            //针对特殊的表单类型进行处理
            if(key.startsWith(FormPluginType.Listctrl.getValue())) { 
                formData = new QueryFormData();
                formData.setName(tfMaps.get(0).getTableId());
                
                formData.setFieldId(tfMaps.get(0).getTableFieldId());
                formData.setFieldName(tfMaps.get(0).getTableFieldName());
                formData.setFieldRemark(tfMaps.get(0).getTableFieldRemark());
                
                formData.setValueSize(1);
                List<QueryFormData> subFormDatas = new ArrayList<QueryFormData>();
                QueryFormData subFormData = null;
                for (TableFieldMap tfMap : tfMaps) {
                    subFormData = new QueryFormData();
                    subFormData.setName(tfMap.getTableFieldId());
                    subFormData.setFieldId(tfMap.getTableFieldId());
                    subFormData.setFieldName(tfMap.getTableFieldName());
                    subFormData.setFieldRemark(tfMap.getTableFieldRemark());
                    subFormData.setValueSize(1);
                    if(null != tfMap.getValues() && tfMap.getValues().size()>0) {
                        List<Object> objs = new ArrayList<Object>();
                        for (int i = 0; i < tfMap.getValues().size(); i++) {
                            if(null != tfMap.getValues().get(i)){ 
                                objs.add(StringUtils.repaceSpecialChar(tfMap.getValues().get(i).toString()));
                            } else {
                                objs.add("");
                            }
                        }
                        subFormData.setValue(objs);
                        subFormData.setValueSize(tfMap.getValues().size());
                    } else {
                        subFormData.setValue(StringUtils.repaceSpecialChar(StringUtils.nullToStr(tfMap.getValue())));
                    }
                    subFormDatas.add(subFormData);
                }
                formData.setNameMoreValues(subFormDatas);
                formDatas.add(formData);
            } else {
                for (TableFieldMap tfMap : tfMaps) {
                     formData = new QueryFormData();
                     formData.setName(tfMap.getTableFieldId());
                     formData.setFieldId(tfMap.getTableFieldId());
                     formData.setFieldName(tfMap.getTableFieldName());
                     formData.setFieldRemark(tfMap.getTableFieldRemark());
                     if(null != tfMap.getValues() && tfMap.getValues().size()>0) {
                         formData.setValue(tfMap.getValues());
                     } else {
                         formData.setValue(StringUtils.nullToStr(tfMap.getValue()));
                     }
                     formData.setValueSize(1);
                     formDatas.add(formData);
                }
            }
        }//for
        formData = null;
        tfMaps = null;
        return formDatas;
    }
	
	/**
	 * 把字段归类到表
	 * @param tfMaps
	 * @return
	 */
	private Map<String,List<TableFieldMap>> fieldClassifyToTable(List<TableFieldMap> tfMaps) {
		Map<String,List<TableFieldMap>> tableMaps = new HashMap<String, List<TableFieldMap>>();
		List<TableFieldMap> tfMapList = null;
		List<TableFieldMap> newMapList = new ArrayList<TableFieldMap>();
		for (TableFieldMap tfMap : tfMaps) {
			tfMapList = tableMaps.get(tfMap.getTableName());
			if(null == tfMapList) {
				tfMapList = new ArrayList<TableFieldMap>();
				TableFieldMap tmp = new TableFieldMap();
				tmp.setId("");
				tmp.setTableFieldId(tfMap.getTableId()+"_id");
				tmp.setTableFieldName("id");
				tmp.setTableId(tfMap.getTableId());
				tmp.setTableName(tfMap.getTableName());
				tmp.setPlugin(tfMap.getPlugin());
				tfMapList.add(tmp);
				newMapList.add(tmp);
				tableMaps.put(tfMap.getTableName(), tfMapList);
			}
			tfMapList.add(tfMap);
		}
		tfMaps.addAll(newMapList);
		return tableMaps.size()>0?tableMaps:null;
	}
	
	/**
	 * 表单类型分类(如：text,textArea,listctrl等)
	 * @param tfMaps
	 * @return
	 */
	private Map<String,List<TableFieldMap>> formTypelassify(List<TableFieldMap> tfMaps) {
		Map<String,List<TableFieldMap>> tableMaps = new HashMap<String, List<TableFieldMap>>();
		List<TableFieldMap> tfMapList = null;
		for (TableFieldMap tfMap : tfMaps) {
			tfMapList = tableMaps.get(tfMap.getPlugin()+"_"+tfMap.getTableId());
			if(null == tfMapList) {
				tfMapList = new ArrayList<TableFieldMap>();
				tableMaps.put(tfMap.getPlugin()+"_"+tfMap.getTableId(), tfMapList);
			}
			tfMapList.add(tfMap);
		}
		return tableMaps.size()>0?tableMaps:null;
	}
	
	/**
	 * 生成查询SQL语句并执行
	 * @param tfMaps
	 * @param formDataId
	 * @param userId
	 */
	private void querySqlAndExe(List<TableFieldMap> tfMaps,String formDataId, String userId) throws DaoException {
		//分开表---同一表的字段放在一个List(归类字段)
		Map<String,List<TableFieldMap>> tableMaps = fieldClassifyToTable(tfMaps);
		//生成SQL语句
		List<TableFieldMap> tfMapList = null;
		Map<String, Object> param = new HashMap<String, Object>();
		for (String key : tableMaps.keySet()) {
			StringBuilder sqlBuild = new StringBuilder();
			sqlBuild.append("select ");
			tfMapList = tableMaps.get(key);
			for (TableFieldMap tfMap : tfMapList) {
				sqlBuild.append(tfMap.getTableFieldName()+",");
			}
			//去掉组合语句时多余的那个逗号","
			sqlBuild.delete(sqlBuild.length()-1, sqlBuild.length());
			param.clear();
			if(StringUtils.isEmpty(formDataId)) {
				sqlBuild.append(" from "+key+" where state='1' and creator=:userId");
				param.put("userId", userId);
			} else {
				sqlBuild.append(" from "+key+" where form_data_id=:formDataId");
				param.put("formDataId", formDataId);
			}
			this.queryAndCompositeData(sqlBuild.toString(), param, tfMapList);
		}//for
		tableMaps = null;
		tfMapList = null;
	}
	
	/**
	 * 生成查询SQL语句并执行
	 * @param tfMaps
	 * @param formDataId
	 */
	private void querySqlAndExeByFormDataId(List<TableFieldMap> tfMaps,String formDataId) throws DaoException {
		//分开表---同一表的字段放在一个List(归类字段)
		Map<String,List<TableFieldMap>> tableMaps = fieldClassifyToTable(tfMaps);
		//生成SQL语句
		List<TableFieldMap> tfMapList = null;
		Map<String, Object> param = new HashMap<String, Object>();
		for (String key : tableMaps.keySet()) {
			StringBuilder sqlBuild = new StringBuilder();
			sqlBuild.append("select ");
			tfMapList = tableMaps.get(key);
			for (TableFieldMap tfMap : tfMapList) {
				sqlBuild.append(tfMap.getTableFieldName()+",");
			}
			//去掉组合语句时多余的那个逗号","
			sqlBuild.delete(sqlBuild.length()-1, sqlBuild.length());
			param.clear();
			sqlBuild.append(" from "+key+" where form_data_id=:formDataId");
			param.put("formDataId", formDataId);
			queryAndCompositeData(sqlBuild.toString(), param, tfMapList);
		}//for
		tableMaps = null;
		tfMapList = null;
	}
	
	/**
	 * 查询并组合数据
	 * @param sql
	 * @param param
	 * @param tfMapList
	 */
	private void queryAndCompositeData(String sql, Map<String, Object> param, List<TableFieldMap> tfMapList) {
		if(StringUtils.isEmpty(sql)) {
		    return;
		}
	    List<Object> objs = opDao.queryObjSql(sql, param);
		if(CollectionUtils.isEmpty(objs)) {
		    return;
		}
		//对查询出来的值进行处理
		if(objs.size()==1) { //当查询结果只有一条时
			Object[] objArray = null;
			if(objs.get(0).getClass().isArray()) {
				objArray = (Object[])objs.get(0);
			} else {
				objArray = new Object[]{objs.get(0)};
			}
			if(null != objArray) {
				for (int i = 0; i < objArray.length; i++) {
					tfMapList.get(i).setValue(objArray[i]);
				}
			}
		} else { //当查询结果有多条时，把对应的值放到一个list里面
			for(Object obj : objs) {
				Object[] objArray = null;
				if(objs.get(0).getClass().isArray()) {
					objArray = (Object[])obj;
				} else {
					objArray = new Object[]{obj};
				}
				if(null != objArray) {
				    for (int i = 0; i < objArray.length; i++) {
                        tfMapList.get(i).getValues().add(objArray[i]);
                    }  
				}
			}//for
		}
	}
	
	
	/**
	 * 提交表单时，把表单数据赋值到tfMaps对象里面 <br />
	 * 修改时间：2016年08月27日；<br />
	 * 修改内容：支持一个字段多个值的情况，多值之间用英文逗号分隔
	 * @param datas
	 * @param tfMaps
	 * @return 返回结果为表名对应字段；如：key为表名,value为一个List表示该表中字段信息
	 */
	private Map<String,List<TableFieldMap>> assignmentFormData(Map<String,Object> datas,List<TableFieldMap> tfMaps) {
		Map<String,List<TableFieldMap>> tableMaps = fieldClassifyToTable(tfMaps);
		if(null == tableMaps || tableMaps.size() == 0) {
		    return tableMaps;
		}
		List<TableFieldMap> tfList = null;
		for(String key : tableMaps.keySet()) {
			tfList = tableMaps.get(key); 
			for (TableFieldMap tf : tfList) {
			    if(!datas.containsKey(tf.getTableFieldId())) {
			        continue;
			    }
				Object value = datas.get(tf.getTableFieldId());
				if(null == value) {
				    tf.setValue(value);
				    continue;
				}
				if(FormPluginType.Listctrl.getValue().equals(tf.getPlugin())) {
				    if(value.getClass().isArray()) {
				        Object[] array = (Object[])value;
						List<Object> values = new ArrayList<Object>(array.length);
						for (Object obj : array) {
							values.add(obj);
						}
						tf.setValues(values);
					} else {
						tf.setValue(value);
					}
				} else if(value.getClass().isArray()) {
					Object[] values = (Object[])value;
					//修改时间：2016年12月07日；同一个表单有相同字段时；只取第一个值，避免出现重复的值
					if(FormPluginType.Text.getValue().equals(tf.getPlugin())) {
						tf.setValue(StringUtils.handleNull(values[0]));
					} else {
						tf.setValue(ArrayUtils.arrayToString(values, IWebConstant.MULTI_VALUE_SPLIT));
					}
				} else {
					tf.setValue(value);
				}
			}//for
		}//for
		return tableMaps;
	}
	
	/**
	 * 插入数据
	 * @param tableName
	 * @param tfList
	 * @param userId
	 * @param formState 表单状态 
	 * <p>1--保存(但未提交) </p>
	 * <p>0-- 保存（并提交）</p>
	 * @param formDataId
	 * @return
	 * @throws DaoException
	 */
	private boolean insertData(String tableName,List<TableFieldMap> tfList,
			String userId,Integer formState,String formDataId) throws DaoException {
		boolean is = true;
		StringBuilder fieldBuild = new StringBuilder();
		StringBuilder valueBuild = new StringBuilder();
		StringBuilder sqlBuild = new StringBuilder();
		
		Map<String,List<Object>> valueMaps = new HashMap<String, List<Object>>(tfList.size());
		sqlBuild.append("insert into "+tableName);
		int valueSize = 0;
		boolean isValueEmpty = true;
		TableFieldMap tableFieldMap = tfList.get(0);
		if("id".equals(tableFieldMap.getTableFieldName())) {
			tfList.remove(tableFieldMap);
		}
		for (TableFieldMap tf : tfList) {
			if(null != tf.getValue() || (null != tf.getValues() && tf.getValues().size()>0)) {
				fieldBuild.append(tf.getTableFieldName()+",");
				valueBuild.append(":"+tf.getTableFieldName()+",");
			}
			//有多个值时(分多条数据插入)
			if(null != tf.getValues() && tf.getValues().size()>0) {
				valueMaps.put(tf.getTableFieldName(), tf.getValues());
				valueSize = tf.getValues().size();
				if(!isEmptyListValue(tf.getValues())) {
					isValueEmpty = isValueEmpty && false;
				}
			} else if(null != tf.getValue()) {
				valueMaps.put(tf.getTableFieldName(),Arrays.asList(new Object[]{tf.getValue()}));
				valueSize = 1;
				if(StringUtils.isNotEmpty(tf.getValue().toString())) {
					isValueEmpty = isValueEmpty && false;
				}
			}
		}
		//当要插入的值都为空时，退出该方法（即:不插入数据）
		if(isValueEmpty) {
			return false;
		}
		sqlBuild.append("(id,form_data_id,"+fieldBuild.toString()+"state,creator,create_time) ");
		sqlBuild.append("values(:id,:formDataId,"+valueBuild.toString()+" :state,:creator,:createTime)");
		Map<String, Object> params = new HashMap<String, Object>(valueMaps.size()+5);
		for (int i = 0; i < valueSize; i++) {
			params.put("id", StringUtils.createSerialNum());
			params.put("formDataId", formDataId);
			params.put("state", formState);
			params.put("createTime", DateUtil.dateToStr(new Date(), null));
			params.put("creator", userId);
			Set<Map.Entry<String, List<Object>>> items = valueMaps.entrySet();
			/*for (String fieldNameKey : valueMaps.keySet()) {
				params.put(fieldNameKey, valueMaps.get(fieldNameKey).get(i));
			}*/
			isValueEmpty = true;
			for (Map.Entry<String, List<Object>> item : items) {
				String value = StringUtils.handleNull(item.getValue().get(i));
				if(StringUtils.isNotEmpty(value)) {
					isValueEmpty = isValueEmpty && false;
				}
				params.put(item.getKey(), value);
			}
			//当要插入的值都为空时，跳出本次循环（即：不插入本次数据）
			if(isValueEmpty) {
				//params.clear();
				continue;
			}
			is = is && opDao.executeSql(sqlBuild.toString(), params)>0?true:false;
			params.clear();
		}
		return is;
	}
	
	
	/**
	 * 判断formDataId对应的数据是否已经存在<br />
	 * 判断有没有与formDataId对应的数据（如果存在返回true；否则返回false）
	 * @param tableName
	 * @param formDataId
	 * @return 如果存在返回:true；否则返回:false
	 */
	private boolean isExistData(String tableName,String formDataId) {
		boolean is = false;
		if(StringUtils.isNotEmpty(formDataId) && StringUtils.isNotEmpty(tableName)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("formDataId", formDataId);
			String sql = "select count(id) from "+tableName+" where form_data_id=:formDataId";
			if(opDao.exeCountSql(sql, param)>0) {
				is = true;
			}
		} 
		return is;
	}
	
	/**
	 * 判断列表中的值是否都为空；如果都为空；则返回：true；否则返回：false
	 * @param objs
	 * @return
	 */
	private boolean isEmptyListValue(List<Object> objs) {
		boolean is = true;
		if(null == objs || objs.size() == 0) {
			return is;
		}
		for(Object obj : objs) {
			if(null == obj || StringUtils.isEmpty(obj.toString())) {
				is = is && true;
			} else {
				is = is && false;
			}
		}
		return is;
	}
	
	/**
	 * 删除字段值
	 * @param tfList
	 * @param indexs
	 * @return
	 */
	private List<TableFieldMap> delFieldValue(List<TableFieldMap> tfList, List<Integer> indexs) {
		if(CollectionUtils.isEmpty(tfList) || null == indexs || indexs.size() ==0) {
			return tfList;
		}
		for (int i = 0; i < tfList.size(); i++) {
			if(null == tfList.get(i)) {
				break;
			}
			if(null == tfList.get(i).getValue() && CollectionUtils.isNotEmpty(tfList.get(i).getValues())) {
				int count = 0;
				for (Integer index : indexs) {
					if(CollectionUtils.isNotEmpty(tfList.get(i).getValues())) {
						tfList.get(i).getValues().remove(index.intValue() - count);
					} else {
						break;
					}
					count++;
				}
			} else {
				if(indexs.size() == 1) {
					tfList = null;
					break;
				}
			}
		}
		boolean isNull = true;
		if(CollectionUtils.isNotEmpty(tfList)) {
			for (int i = 1; i < tfList.size(); i++) {
				if(null != tfList.get(i) && CollectionUtils.isEmpty(tfList.get(i).getValues())) {
					isNull = isNull && true;
				} else {
					isNull = isNull && false;
				}
			}
		}
		if(isNull) {
			tfList = null;
		}
		return tfList;
	}

    @Override
    public SmartResponse<String> getFieldInAttIds(String formId, String[] plugins, String formDataId) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        if(StringUtils.isEmpty(formId) || null == plugins || plugins.length==0 
                || StringUtils.isEmpty(formDataId)) {
            return smartResp;
        }
        Map<String, List<String>> dataMap = formTableFieldServ.getTableFieldByPugin(formId, plugins);
        if(null == dataMap || dataMap.size() == 0) {
            return smartResp;
        }
        Set<Map.Entry<String, List<String>>> sets = dataMap.entrySet();
        List<String> attIds = new ArrayList<String>();
        String sql = SQLResUtil.getOpSqlMap().getSQL("get_field_value");
        Map<String, Object> param = new HashMap<String, Object>(1);
        param.put("formDataId", formDataId);
        for (Map.Entry<String, List<String>> set : sets) {
            String tableName = set.getKey();
            List<String> fieldNames = set.getValue();
            int fieldLen = fieldNames.size();
            String tmpSql = sql.replace("${tableName}", tableName);
            tmpSql = tmpSql.replace("${fieldName}", StringUtils.collection2String(fieldNames, IConstant.MULTI_VALUE_SPLIT));
            List<Object> list = formTableServ.getDao().queryObjSql(tmpSql, param);
            if(null != list && list.size()>0) {
                for (Object obj : list) {
                    if(fieldLen == 1) {
                        attIds.add(StringUtils.handleNull(obj));
                    } else {
                        Object[] objArray = (Object[]) obj;
                        for (int i = 0; i < fieldLen; i++) {
                            attIds.add(StringUtils.handleNull(objArray[i]));
                        }
                    }
                }//for
            }//if
        }//for
        if(attIds.size()>0) {
            smartResp.setDatas(attIds);
            smartResp.setResult(IConstant.OP_SUCCESS);
            smartResp.setMsg(IConstant.OP_SUCCESS_MSG);
        }
        return smartResp;
    }

}
