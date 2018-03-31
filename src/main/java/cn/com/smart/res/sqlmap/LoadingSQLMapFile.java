package cn.com.smart.res.sqlmap;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mixsmart.utils.StringUtils;

/**
 * 加载sql配置文件
 * @author lmq
 *
 */
public class LoadingSQLMapFile {

	private static final Logger log = Logger.getLogger(LoadingSQLMapFile.class);
	
	/**
	 * 数据库中定义的SQL资源
	 */
	private static final String DB_SQL_MAP = "db_sql_map";
	
	private static Map<String,SQLMapFile> SQL_MAP_FILES;
	
	private static LoadingSQLMapFile _instance;
	
	private List<String> pathList;
	
	
	private LoadingSQLMapFile(){
		SQL_MAP_FILES = new HashMap<String, SQLMapFile>();
		SQL_MAP_FILES.put(DB_SQL_MAP, new SQLMapFile());
	}
	
	/**
	 * 获取实例
	 * @return SQL配置文件实例
	 * @throws SQLMapException
	 */
	public synchronized static LoadingSQLMapFile getInstance() throws SQLMapException {
		if(null == _instance) {
			_instance = new LoadingSQLMapFile();
		} 
		return _instance;
	}
	
	/**
	 * 获取数据库中定义的SQL资源
	 * @return
	 */
	public SQLMapFile getDbSqlMap() {
	    return SQL_MAP_FILES.get(DB_SQL_MAP);
	}
	
	/**
	 * 载入SQL配置文件
	 * @param xmlPath
	 * @return 成功返回 SQL映射文件对象，失败返回 null
	 * @throws SQLMapException
	 */
	public synchronized SQLMapFile loadFile(String xmlPath) throws SQLMapException {
		SQLMapFile sqlMapFile = null;
		if(StringUtils.isEmpty(xmlPath)) {
			throw new SQLMapException("xml路径为空");
		} else {
			if(null != SQL_MAP_FILES && SQL_MAP_FILES.size()>0) {
				sqlMapFile = SQL_MAP_FILES.get(xmlPath);
				if(null == sqlMapFile) {
					sqlMapFile = initSQLMapFile(null,xmlPath,0);
					SQL_MAP_FILES.put(xmlPath, sqlMapFile);
					pathList = null;
				} /*else {
					String devModel = InitSysConfig.getInstance().getValue("project.devModel");
					if(StringUtils.isNotEmpty(devModel) && IConstant.PROJECT_DEV_MODEL.equals(devModel)) {
						URL path = this.getClass().getResource(xmlPath);
						File file = new File(path.getFile());
						long lastModifyTime = file.lastModified();
						//判断文件是否被修改过，如果文件已修改，则重新初始化文件
						if(lastModifyTime>sqlMapFile.getFileModifyTime()) {
							sqlMapFile = initSQLMapFile(sqlMapFile, xmlPath, 0);
							SQL_MAP_FILES.put(xmlPath, sqlMapFile);
							pathList = null;
						}
						
					}
				}*/
			} else {
				sqlMapFile = initSQLMapFile(null,xmlPath,0);
				SQL_MAP_FILES.put(xmlPath, sqlMapFile);
				pathList = null;
			}
			//sqlMapFile = SQL_MAP_FILES.get(xmlPath);
		}
		return sqlMapFile;
	}
	
	/**
	 * 初始化文件(解析xml文件)
	 * @param xmlPath
	 * @return 成功返回 SQL映射文件对象，失败返回 null
	 * @throws SQLMapException
	 */
	private SQLMapFile initSQLMapFile(SQLMapFile sqlMapFile,String xmlPath,int count) throws SQLMapException {
		log.info("初始化sql配置文件["+xmlPath+"]");
		InputStream is = null;
		//解析xml
		try {
			//URL path = this.getClass().getResource(xmlPath);
			is = this.getClass().getResourceAsStream(xmlPath);
			if(null == is) {
				throw new SQLMapException("["+xmlPath+"]文件未找到");
			}
			//File file = new File(path.toURI().getPath());
			SAXReader saxReader = new SAXReader();
			saxReader.setEntityResolver(new ClasspathEntityResolver());
			Document document = saxReader.read(is);
			Element sqlMaps = document.getRootElement();
			if(null == sqlMapFile) {
				sqlMapFile = new SQLMapFile();
				//sqlMapFile.setFileModifyTime(file.lastModified());
				sqlMapFile.setFilePath(xmlPath);
			}
			Map<String,String> sqlMapValues = new HashMap<String, String>();
			for(Iterator<?> i = sqlMaps.elementIterator(); i.hasNext();){
				Element element = (Element) i.next();
				if("include".equals(element.getName())) {
					String fileStr = element.attributeValue("file");
					if(StringUtils.isNotEmpty(fileStr)) {
						if(fileStr.startsWith("./")) {
							String parentDir = xmlPath.substring(0,xmlPath.lastIndexOf("/")+1);
							fileStr = fileStr.replace("./", parentDir);
						}
						//URL urlFile = this.getClass().getResource(fileStr);
						//File includeFile = new File(urlFile.toURI().getPath());
						InputStream subIs = this.getClass().getResourceAsStream(fileStr);
						if(null != subIs) {
							if(null == pathList)
								pathList = new ArrayList<String>();
							if(!isRepeat(fileStr)) 
								pathList.add(fileStr);
							subIs.close();
						}
					}
				} else {
					String key = element.attributeValue("name");
					String value = element.elementText("sql");
					sqlMapValues.put(key, value);
				}
			}
			if(sqlMapValues.size()>0) {
				sqlMapFile.addSqlMaps(sqlMapValues);
				sqlMapValues=null;
			}
			sqlMaps = null;
			log.info("初始化sql配置文件[成功]");
			document.clearContent();
			saxReader = null;
			document = null;
		} catch (DocumentException e) {
			//e.printStackTrace();
			log.error("初始化sql配置文件["+xmlPath+"]--[失败]");
			throw new SQLMapException(e.getMessage());
			
		} catch (Exception e) {
			log.error("初始化sql配置文件["+xmlPath+"]--[失败]");
			throw new SQLMapException(e.getMessage());
		} finally {
			try {
				if(null != is)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(null != pathList && pathList.size()>0 && pathList.size()>count) {
			String filePath = pathList.get(count);
			count++;
			initSQLMapFile(sqlMapFile, filePath, count);
		}
		return sqlMapFile;
	}
	
	
	/**
	 * 判断是否有重复的
	 * @param fileStr
	 * @return 有重复返回：true，否则返回：false
	 */
	private boolean isRepeat(String fileStr) {
		boolean is = false;
		if(null != pathList && pathList.size()>0 && StringUtils.isNotEmpty(fileStr)) {
			for (String path:pathList) {
				if(fileStr.equals(path)) {
					is = true;
					break;
				}
			}
		}
		return is;
	}
}
