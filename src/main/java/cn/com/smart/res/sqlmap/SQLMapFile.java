package cn.com.smart.res.sqlmap;

import java.util.HashMap;
import java.util.Map;

/**
 * SQL映射文件
 * @author lmq
 * @version 1.0
 * @since JDK1.6
 * 2015年8月22日
 */
public class SQLMapFile {

	private String filePath;
	
	private long fileModifyTime;
	
	private Map<String,String> sqlMaps;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileModifyTime() {
		return fileModifyTime;
	}

	public void setFileModifyTime(long fileModifyTime) {
		this.fileModifyTime = fileModifyTime;
	}

	public Map<String, String> getSqlMaps() {
		return sqlMaps;
	}

	public void setSqlMaps(Map<String, String> sqlMaps) {
		this.sqlMaps = sqlMaps;
	}
	
	public void addSqlMaps(Map<String, String> sqlMaps) {
		if(null == this.sqlMaps) {
			this.sqlMaps = new HashMap<String, String>();
		}
		this.sqlMaps.putAll(sqlMaps);
	}
	
}
