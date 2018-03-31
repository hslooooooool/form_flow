package cn.com.smart.res.sqlmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mixsmart.utils.StringUtils;

/**
 * 过滤处理sql语句中有可选条件的变量参数 <br />
 * sql 语句中可以的条件用中括号 控起来，如: <br />
 * select * form t_table t [where t.name=:name] order by t.seq_num asc <br />
 * 其中[where t.name=:name]是可选的条件，当参数中有name变量时，该条件就变为可用，否则过滤
 * @author lmq
 *
 */
public class SQLVarParamFilter {

	private String sql;
	
	private Map<String,Object> params; 
	
	@SuppressWarnings("unchecked")
	public SQLVarParamFilter(String sql,Map<String,Object> params) {
		this.sql = sql;
		if(null != params && params.size()>0) {
			if(params instanceof HashMap) {
				this.params = (Map<String, Object>) ((HashMap<String, Object>)params).clone();
			}
		}
		initParamBySql();
	}

	
	/**
	 * 根据SQL语句中的有的参数，重新初始化params参数；<br />
	 * 避免sql语句中没有的参数出现在params变量参数里面，防止SQL语句报错
	 */
	protected void initParamBySql() {
		if(StringUtils.isNotEmpty(sql)) {
			String regex = "(:\\w+)";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(sql);
			Set<String> sqlInParams = new HashSet<String>();
			while(matcher.find()) {
				String param = matcher.group();
				if(StringUtils.isNotEmpty(param)) {
					param = param.substring(1,param.length());
					sqlInParams.add(param);
				}  
			}
			if(null != params && params.size()>0 && sqlInParams.size()>0) {
				Set<String> keys = params.keySet();
				boolean isExist = false;
				Set<String> removeKeySet = new HashSet<String>();
				for (String key : keys) {
					isExist = false;
					for(String sqlKey:sqlInParams) {
						if(key.equals(sqlKey)) {
							isExist = true;
							break;
						}
					}
					if(!isExist) {
						removeKeySet.add(key);
					}
				}
				if(removeKeySet.size()>0) {
					for (String key : removeKeySet) {
						params.remove(key);
					}
				}
				removeKeySet = null;
			} else if(sqlInParams.size()==0) {
				params = null;
			}
			sqlInParams = null;
			pattern = null;
			matcher = null;
		}	
	}
	
	/**
	 * 获取参数变量名
	 * @return 返回参数
	 */
	protected List<String> getVarParam() {
		List<String> varParams = null;
		if(null != params && params.size()>0) {
			varParams = new ArrayList<String>();
			Set<String> sets = params.keySet();
			for (String key : sets) {
				Object value = this.params.get(key);
				if(null != value && StringUtils.isNotEmpty(StringUtils.handleNull(value))) {
					varParams.add(key);
				}
			}
			sets = null;
		}
		return varParams;
	}
	
	/**
	 * 获取可选条件(语句)
	 * @return 返回可选条件集合
	 */
	protected List<String> getOptionSql() {
		List<String> options = null;
		if(StringUtils.isNotEmpty(sql)) {
			options = new ArrayList<String>();
			String regex = "(?<=\\[)[^\\[]+(?=\\])";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(sql);
			while(matcher.find()) {
				options.add(matcher.group());
			}
			pattern = null;
			matcher = null;
		}
		return (options != null && options.size()>0)?options:null;
	}
	
	/**
	 * 根据参数变量过滤可选条件
	 * @param options
	 * @param varParams
	 * @return List
	 */
	protected List<String> filterOptionSql(List<String> options,List<String> varParams) {
		List<String> hasSqls = new ArrayList<String>();
		for (String option : options) {
			for (String varParam : varParams) {
				//if(option.matches(".*?:"+varParam+"[\\s*|\\]|%|']*?")) {
			    if(option.matches(".*:\\b"+varParam+"\\b.*?")) {
					hasSqls.add(option);
				}
			}
		}
		return (hasSqls != null && hasSqls.size()>0)?hasSqls:null;
	}
	
	/**
	 * 开始过滤
	 * @return 返回过滤后的sql语句
	 */
	public String filter() {
		List<String> options = getOptionSql();
		if(null != options && options.size()>0) {
			List<String> varParams = getVarParam();
			if(null != varParams && varParams.size()>0) {
				List<String> hasSqls = filterOptionSql(options, varParams);
				if(null != hasSqls && hasSqls.size()>0) {
					for (String hasSql : hasSqls) {
						sql = sql.replace("["+hasSql+"]", hasSql);
					}
				}
				hasSqls = null;
			}
			varParams = null;
		}
		options = null;
		sql = sql.replaceAll("\\[([^\\]]+)\\]", "");
		
		//like处理
		if(sql.indexOf("%")>-1 && null != params && params.size()>0) {
			Set<String> sets = params.keySet();
			Map<String, Object> newParam = new HashMap<String, Object>();
			Pattern pattern = null;
			Matcher matcher = null;
			Pattern patternUnlike = null;
			Matcher matcherUnlike = null;
			
			String group = null;
			String paramValue = null;
			String groupReplaceValue = null;
			String likeName = null;
			String newKey = null;
			boolean isMatcher = false;
			
			for (String key : sets) {
				String regex = "('%?:"+key+"%?')";
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(sql);
				int count = 0;
				isMatcher = false;
				while(matcher.find()) {
					count++;
					isMatcher = true;
					group = matcher.group();
					paramValue = params.get(key).toString();
					groupReplaceValue = group.replace(":"+key, paramValue);
					if(count>1) {
						likeName = ":"+key+count;
						newKey = key+count;
					} else {
						String regexUnlike = "([^%]:"+key+"[^%]?\\s+)";
						patternUnlike = Pattern.compile(regexUnlike);
						matcherUnlike = patternUnlike.matcher(sql);
						if(matcherUnlike.find()) {
							likeName = ":"+key+count;
							newKey = key+count;
						} else {
							likeName = ":"+key;
							newKey = key;
						}
					}
					sql = sql.replaceFirst(group, likeName);
					groupReplaceValue = groupReplaceValue.replaceAll("'", "");
					newParam.put(newKey, groupReplaceValue);
				}
				if(!isMatcher) {
					newParam.put(key, params.get(key));
				}
			}
			params.putAll(newParam);
			
			sets = null;
			newParam = null;
			pattern = null;
			matcher = null;
			patternUnlike = null;
			matcherUnlike = null;
			
			groupReplaceValue = null;
			group = null;
			paramValue = null;
			likeName = null;
			newKey = null;
		}
		return sql;
	}
	
	
	public String getSql() {
		return sql;
	}

	public Map<String, Object> getParams() {
		return params;
	}
	
}
