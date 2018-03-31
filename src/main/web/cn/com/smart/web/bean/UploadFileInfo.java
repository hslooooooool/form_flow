package cn.com.smart.web.bean;

/**
 * 上传文件的信息
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public class UploadFileInfo {

	private String id;
	
	private String fileName;
	
	private String fileType;
	
	private String fileSuffix;//文件后缀；如：jpg,doc
	
	private String filePath;
	
	private long fileSize;//文件大小(单位:Byte)
	
	private String createTime;

	/**
	 * 文件名称
	 * @return 文件名称
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置文件名称
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 获取文件路径
	 * @return 文件路径
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * 设置文件路径
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 文件创建时间
	 * @return 文件创建时间
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * 设置文件创建时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取文件类型
	 * @return 返回文件类型
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * 设置文件类型
	 * @param fileType
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * 获取文件大小
	 * @return 返回文件大小；单位为：byte
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * 设置文件大小
	 * @param fileSize 单位为：byte
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 获取文件的唯一标识符
	 * @return 返回文件ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置文件ID
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取文件后缀名
	 * @return 返回文件后缀名，不含.
	 */
	public String getFileSuffix() {
		return fileSuffix;
	}

	/**
	 * 设置文件后缀名
	 * @param fileSuffix
	 */
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}
	
}
