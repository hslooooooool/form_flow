package cn.com.smart.validate;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.IdCardValidator;
import com.mixsmart.utils.StringUtils;

/**
 * 执行验证器
 * @author lmq
 *
 */
public class ExecuteValidator implements Validator {
	
	private static final Logger log = Logger.getLogger(ExecuteValidator.class);

	private Object obj;

    private Field[] fields;
	
	public ExecuteValidator(Object obj) {
		this.obj = obj;
		init();
	}
	
	public void init() {
		if(null != obj) {
			fields = obj.getClass().getDeclaredFields();
		}
	}
	
	@Override
	public boolean validate() throws ValidateException {
		boolean is = true;
		if(fields.length == 0) {
			return is;
		}
		for (Field field : fields) {
			if(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			try {
				PropertyDescriptor propertyDesc = new PropertyDescriptor(field.getName(), obj.getClass());
				Method m = propertyDesc.getReadMethod();
				Object value = m.invoke(obj, new Object[]{});
				Validate v = m.getAnnotation(Validate.class);
				if(null == v) {
					continue;
				}
				//判断字段是否可为空
				if (v.nullable()) {
					is = is && true;
				} else {
					if (null != value && StringUtils.isNotEmpty(value.toString())) {
						is = is && true;
					} else {
						is = is && false;
					}
				}
				if (!(is && (value instanceof String) && null != value && StringUtils.isNotEmpty(value.toString()))) {
					break;
				}
				//验证长度
				String len = v.length();
				if (StringUtils.isNotEmpty(len)) {
					is = is && checkLength(value.toString(), len, m.getName());
				}
				//验证数据值范围
				String valueArea = v.valueArea();
				if (StringUtils.isNotEmpty(valueArea)) {
					is = is && checkValueArea(value.toString(), valueArea, m.getName());
				}
				if (!is) break;
				//验证正则表达式
				String regexExpr = v.regexExpr();
				if (StringUtils.isNotEmpty(regexExpr)) {
					try {
						Pattern p = Pattern.compile(regexExpr);
						Matcher mt = p.matcher(value.toString());
						is = is && mt.matches();
						if (!is) {
							log.error("[" + m.getName() + "]表达式匹配失败");
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new ValidateException("[" + m.getName() + "] Validate.regexExpr属性配置错误[正则表达式错误]");
					}
				}
				if (!is) break;
				//验证数据格式
				String formatType = v.dataFormatType();
				if (StringUtils.isNotEmpty(formatType)) {
					is = is && checkDataFormat(value.toString(), formatType, m.getName());
				}
				if (!is) break;
				//判断是否配置了自定义验证类
				String className = v.className();
				if (StringUtils.isNotEmpty(className)) {
					//执行自定义验证类
					Class<?> cstClass = Class.forName(className);
					if (cstClass.isAssignableFrom(Validator.class)) {
						CustomValidator cv = (CustomValidator) cstClass.newInstance();
						cv.setObj(obj);
						cv.setValue(value);
						is = is && cv.validate();
						if (!is) {
							log.error("[" + m.getName() + "]自定义类验证失败");
						}
					}
				}
			}catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new ValidateException(e.getMessage());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new ValidateException(e.getMessage());
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				throw new ValidateException(e.getMessage());
			} catch (IntrospectionException e){
				e.printStackTrace();
				throw new ValidateException(e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new ValidateException(e.getMessage());
			}  catch (InstantiationException e) {
				e.printStackTrace();
				throw new ValidateException(e.getMessage());
			}
		}//for
		return is;
	}
	
	
	/**
	 * 验证长度
	 * @param value
	 * @param len
	 * @param methodName
	 * @return
	 * @throws ValidateException
	 */
	private boolean checkLength(String value,String len,String methodName) throws ValidateException{
		boolean is = false;
		long valueLen = value.length();
		if(len.indexOf("|")>-1) {
			String[] lens = len.split("\\|");
			for (String lenStr : lens) {
				if(StringUtils.isNum(lenStr.trim())) {
					long l = Long.parseLong(lenStr.trim());
					is = (valueLen == l);
				} else {
					throw new ValidateException("["+methodName+"]Validate.length属性配置错误");
				}
				if(is) {
					break;
				}
			}
		} else if(len.indexOf(",")>-1) {
			String[] lens = len.split(",");
			if(lens.length==2) {
				if(StringUtils.isNum(lens[0].trim()) && StringUtils.isNum(lens[1].trim())) {
					long startLen = Long.parseLong(lens[0].trim());
					long endLen = Long.parseLong(lens[1].trim());
					if(startLen<=endLen) {
					    is = (valueLen >= startLen && valueLen<=endLen);
					} else {
						throw new ValidateException("["+methodName+"]Validate.length属性配置错误");
					}
				} else {
					throw new ValidateException("["+methodName+"]Validate.length属性配置错误");
				}
			} else {
				throw new ValidateException("["+methodName+"]Validate.length属性配置错误");
			}
		} else {
			if(StringUtils.isNum(len.trim())) {
				is = is && (valueLen == Long.parseLong(len.trim()));
			} else {
				throw new ValidateException("["+methodName+"]Validate.length属性配置错误");
			}
		}
		if(!is) {
			log.error("["+methodName+"]长度验证失败");
		}
		return is;
	}
	
	
	/**
	 * 验证数据类型
	 * @param value
	 * @param dataFormatType
	 * @param methodName
	 * @return
	 */
	private boolean checkDataFormat(String value,String dataFormatType,String methodName) {
		boolean is = true;
		if(DataFormatType.NUM.equals(dataFormatType)) {
			is = is && StringUtils.isNum(value);
			if(!is) {
				log.error("["+methodName+"]--["+DataFormatType.NUM+"]数据类型验证失败");
			}
		} else if(DataFormatType.CHINESE.equals(dataFormatType)) {
			is = is && StringUtils.isChinese(value);
			if(!is) {
				log.error("["+methodName+"]--["+DataFormatType.CHINESE+"]数据类型验证失败");
			}
		} else if(DataFormatType.ID_CARD.equals(dataFormatType)) {
			IdCardValidator iv = new IdCardValidator();
			is = is && iv.isValidatedAllIdcard(value);
			if(!is) {
				log.error("["+methodName+"]--["+DataFormatType.ID_CARD+"]数据格式验证失败");
			}
		} else if(DataFormatType.DECIMAL.equals(dataFormatType)) {
			is = is && StringUtils.isDecimal(value);
			if(!is) {
				log.error("["+methodName+"]--["+DataFormatType.DECIMAL+"]数据格式验证失败");
			}
		} else if(DataFormatType.EMAIL.equals(dataFormatType)) {
			is = is && StringUtils.isEmail(value);
			if(!is) {
				log.error("["+methodName+"]--["+DataFormatType.EMAIL+"]数据格式验证失败");
			}
		} else if(DataFormatType.FIXED_TELPHONE.equals(dataFormatType)) {
			is = is && StringUtils.isFixedTelephone(value);
			if(!is) {
				log.error("["+methodName+"]--["+DataFormatType.FIXED_TELPHONE+"]数据格式验证失败");
			}
		} else if(DataFormatType.INTEGER.equals(dataFormatType)) {
			is = is && StringUtils.isInteger(value);
			if(!is) {
				log.error("["+methodName+"]--["+DataFormatType.INTEGER+"]数据格式验证失败");
			}
		} else if(DataFormatType.IP.equals(dataFormatType)) {
			is = is && StringUtils.checkIp(value);
			if(!is) {
				log.error("["+methodName+"]--["+DataFormatType.IP+"]数据格式验证失败");
			}
		} else if(DataFormatType.MOBILE_PHONE.equals(dataFormatType)) {
			is = is && StringUtils.isPhoneNO(value);
			if(!is) {
				log.error("["+methodName+"]--["+DataFormatType.MOBILE_PHONE+"]数据格式验证失败");
			}
		} else if(DataFormatType.QQ.equals(dataFormatType)) {
			is = is && StringUtils.isQQ(value);
			if(!is) {
				log.error("["+methodName+"]--["+DataFormatType.QQ+"]数据格式验证失败");
			}
		}
		return is;
	}
	
	
	private boolean checkValueArea(String value,String valueArea,String methodName) {
		boolean is = false;
		if(ArrayUtils.isArrayContains(valueArea, value, ",")) {
			is = true;
		} else {
			is = false;
			log.error("["+methodName+"]--数据值["+value+"]不在规定的范围内--["+valueArea+"]");
		}
		return is;
	}

	public void setObj(Object obj) {
		this.obj = obj;
		init();
	}

}
