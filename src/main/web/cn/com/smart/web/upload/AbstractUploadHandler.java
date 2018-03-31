package cn.com.smart.web.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.constant.IConstant;
import cn.com.smart.init.config.InitSysConfig;
import cn.com.smart.utils.DateUtil;
import cn.com.smart.web.bean.UploadFileInfo;

import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 上传处理者
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractUploadHandler implements IConstant {

	private static final Logger logger = LoggerFactory.getLogger(AbstractUploadHandler.class);
	
	/**
	 * 上传文件
	 * @param obj 文件流(InputStream)或文件对象(File）
	 * @param contentType 文件类型
	 * @param uploadFileName 上传文件名称
	 * @param fileSize 文件大小
	 * @param destFileName 上传到服务器上后的名称
	 * @param uploadDir 文件存放位置（目录）
	 * @return 返回上传结果，如果上传成功，则返回结果中包含了文件的信息
	 */
	private SmartResponse<UploadFileInfo> uploadObj(Object obj,String contentType,String uploadFileName,long fileSize,String destFileName,String uploadDir) {
		SmartResponse<UploadFileInfo> chRes = new SmartResponse<UploadFileInfo>();
		String msg = null;
		if (StringUtils.isNotEmpty(uploadFileName)) {
			String extTmp = StringUtils.getFileSuffix(uploadFileName);
			UploadFileInfo uploadFileInfo = new UploadFileInfo();
			uploadFileInfo.setFileName(uploadFileName);
			uploadFileInfo.setFileSuffix(extTmp);
			
			uploadFileInfo.setFileType(extTmp);
			if(StringUtils.isEmpty(uploadDir)) {
			    if(checkImageType(extTmp)) {
			    	if(StringUtils.isEmpty(uploadDir)) {
			    		uploadDir = getImageDir();
			    	}
			    } else if(checkDocType(extTmp)) {
			    	if(StringUtils.isEmpty(uploadDir)) {
			    	  uploadDir = getDocDir();
			    	}
			    } else if(checkVideoType(extTmp)) {
			    	if(StringUtils.isEmpty(uploadDir)) {
			    	  uploadDir = getVideoDir();
			    	}
			    } else if(checkAudioType(extTmp)) {
			    	if(StringUtils.isEmpty(uploadDir)) {
			    	   uploadDir = getAudioDir();
			    	}
			    } else {
			    	uploadDir = null;
			    }
			}
		    if(StringUtils.isEmpty(uploadDir)) {
		    	msg = "“"+uploadFileName+"”文件类型不支持上传";
		    	LoggerUtils.error(logger, msg);
		    	chRes.setMsg("文件类型不支持上传");
		    } else {
		    	File dirFile = new File(uploadDir);
		    	if(!dirFile.exists()) {
		    		dirFile.mkdirs();
		    	}
		    	dirFile = null;
				if (fileSize > getUploadMaxSize()) {
					msg = "“"+uploadFileName+"”文件太大，不支持上传";
					LoggerUtils.error(logger, msg);
			    	chRes.setMsg("文件太大，不支持上传");
				} else {
					String targetFileName = StringUtils.uuid() + "." + extTmp;
					if(StringUtils.isNotEmpty(destFileName)) {
						targetFileName = destFileName+"."+extTmp;
					}
					//String targetFilePath = uploadDir+ FileUtil.getFileSeparator() + targetFileName;
					String targetFilePath = uploadDir+ "/" + targetFileName;
					File targetFile = new File(targetFilePath);
					boolean is = false;
					if(obj instanceof InputStream) {
						is = saveUploadFile((InputStream)obj, targetFile);
					} else {
						saveUploadFile((File)obj, targetFile);
					}
					if (is) {//保存文件到磁盘
						targetFilePath = targetFilePath.replace(getRootDir(), "");
						uploadFileInfo.setFilePath(targetFilePath);
						uploadFileInfo.setCreateTime(DateUtil.dateToStr(new Date(), null));
						uploadFileInfo.setFileSize(fileSize);
						uploadFileInfo.setFileType(contentType);
						chRes.setResult(OP_SUCCESS);
						msg = "“"+uploadFileName+"”文件上传成功";
						LoggerUtils.info(logger, msg);
				    	chRes.setMsg(msg);
					}
					targetFile = null;
				}
		    }//if/else
		    chRes.setData(uploadFileInfo);
		    uploadFileInfo = null;
		}
		return chRes;
	}
	
	
	/**
	 * 上传文件
	 * @param file 文件对象
	 * @param contentType 文件类型 
	 * @param uploadFileName 上传文件名称
	 * @param destFileName 上传到服务器上后的名称
	 * @param uploadDir 文件存放位置（目录）
	 * @return 返回上传结果，如果上传成功，则返回结果中包含了文件的信息
	 */
	public SmartResponse<UploadFileInfo> upload(File file,String contentType,String uploadFileName,String destFileName,String uploadDir) {
		SmartResponse<UploadFileInfo> chRes = new SmartResponse<UploadFileInfo>();
		if(null != file) {
			long fileSize = file.length();
			chRes = uploadObj(file, contentType, uploadFileName, fileSize, destFileName, uploadDir);
		}
		return chRes;
	}
	
	
	/**
	 * 上传文件
	 * @param file 文件对象
	 * @param contentType 文件类型 
	 * @param uploadFileName 上传文件名称
	 * @return 返回上传结果，如果上传成功，则返回结果中包含了文件的信息
	 */
	public SmartResponse<UploadFileInfo> upload(File file,String contentType,String uploadFileName) {
		SmartResponse<UploadFileInfo> chRes = new SmartResponse<UploadFileInfo>();
		if(null != file) {
			chRes = upload(file, contentType, uploadFileName, null, null);
		}
		return chRes;
	}
	
	
	/**
	 * 上传文件
	 * @param inputStream 文件流
	 * @param contentType 文件类型 
	 * @param uploadFileName 上传文件名称
	 * @param fileSize 文件大小
	 * @param destFileName 上传到服务器上后的名称
	 * @param uploadDir 文件存放位置（目录）
	 * @return 返回上传结果，如果上传成功，则返回结果中包含了文件的信息
	 */
	public SmartResponse<UploadFileInfo> upload(InputStream inputStream,String contentType,String uploadFileName,long fileSize,String destFileName,String uploadDir) {
		SmartResponse<UploadFileInfo> chRes = new SmartResponse<UploadFileInfo>();
		if(null != inputStream) {
			chRes = uploadObj(inputStream, contentType, uploadFileName, fileSize, destFileName, uploadDir);
		}
		return chRes;
	}
	
	/**
	 * 上传文件
	 * @param inputStream 文件流
	 * @param contentType 文件类型 
	 * @param uploadFileName 上传文件名称
	 * @param fileSize 文件大小
	 * @return 返回上传结果，如果上传成功，则返回结果中包含了文件的信息
	 */
	public SmartResponse<UploadFileInfo> upload(InputStream inputStream,String contentType,String uploadFileName,long fileSize) {
		SmartResponse<UploadFileInfo> chRes = new SmartResponse<UploadFileInfo>();
		if(null != inputStream) {
			chRes = upload(inputStream, contentType, uploadFileName, fileSize, null, null);
		}
		return chRes;
	}
	
	
	
	/**
	 * 保存上传的文件到服务器
	 * @param source 源文件
	 * @param target 目标文件
	 * @return 成功返回：true；否则返回：false
	 */
	public boolean saveUploadFile(File source, File target) {
		boolean is = false;
		LoggerUtils.info(logger, "正在保存文件...");
		try {
			FileUtils.copyFile(source, target);
			is = true;
			LoggerUtils.info(logger, "文件保存[成功]....");
		} catch (IOException ex) {
			LoggerUtils.error(logger, "保存文件[异常]...");
			ex.printStackTrace();
		}
		return is;
	}
	
	
	/**
	 * 保存上传的文件到服务器
	 * @param source 源文件
	 * @param target 目标文件
	 * @return 成功返回：true；否则返回：false
	 */
	protected boolean saveUploadFile(InputStream source, File target) {
		boolean is = false;
		LoggerUtils.info(logger, "正在保存文件...");
		try {
			if(null != source) {	
				FileUtils.copyInputStreamToFile(source, target);
				if(null != source) 
					source.close();
		        is = true;
			}
			LoggerUtils.info(logger, "文件保存[成功]....");
		} catch (IOException ex) {
			LoggerUtils.error(logger, "保存文件[异常]...");
			ex.printStackTrace();
		}
		return is;
	}
	
	
	/**
	 * 验证是否为图片类型 <br />
	 * 图片类型从配置文件中获取,属性为:upload.image.type
	 * @param fileSuffix 文件后缀
	 * @return 是返回：true，否则返回：false
	 */
	protected boolean checkImageType(String fileSuffix) {
		boolean is = false;
		if(StringUtils.isNotEmpty(fileSuffix)) {
			String imageType = InitSysConfig.getInstance().getValue("upload.image.type");
			if(StringUtils.isNotEmpty(imageType)) {
				is = ArrayUtils.isArrayContainsIgnoreCase(imageType, fileSuffix, ",");
			} else {
				LoggerUtils.error(logger, "配置文件中没定义[upload.image.type]属性,所以不支持改该类文件的上传");
			}
		}
		return is;
	}
	
	
	/**
	 * 验证是否为文档类型 <br />
	 * 文档类型从配置文件中获取,属性为:upload.doc.type
	 * @param fileSuffix 文件后缀
	 * @return 是返回：true，否则返回：false
	 */
	protected boolean checkDocType(String fileSuffix) {
		boolean is = false;
		if(StringUtils.isNotEmpty(fileSuffix)) {
			String docType = InitSysConfig.getInstance().getValue("upload.doc.type");
			if(StringUtils.isNotEmpty(docType)) {
				is = ArrayUtils.isArrayContainsIgnoreCase(docType, fileSuffix, ",");
			} else {
				LoggerUtils.error(logger, "配置文件中没定义[upload.doc.type]属性,所以不支持改该类文件的上传");
			}
		}
		return is;
	}
	
	/**
	 * 验证是否为视频文件类型 <br />
	 * 视频文件类型从配置文件中获取,属性为:upload.video.type
	 * @param fileSuffix 文件后缀
	 * @return 是返回：true，否则返回：false
	 */
	protected boolean checkVideoType(String fileSuffix) {
		boolean is = false;
		if(StringUtils.isNotEmpty(fileSuffix)) {
			String videoType = InitSysConfig.getInstance().getValue("upload.video.type");
			if(StringUtils.isNotEmpty(videoType)) {
				is = ArrayUtils.isArrayContainsIgnoreCase(videoType, fileSuffix, ",");
			} else {
				LoggerUtils.error(logger, "配置文件中没定义[upload.video.type]属性,所以不支持改该类文件的上传");
			}
		}
		return is;
	}
	
	
	/**
	 * 验证是否为音频文件类型 <br />
	 * 音频文件类型从配置文件中获取,属性为:upload.audio.type
	 * @param fileSuffix 文件后缀
	 * @return 是返回：true，否则返回：false
	 */
	protected boolean checkAudioType(String fileSuffix) {
		boolean is = false;
		if(StringUtils.isNotEmpty(fileSuffix)) {
			String audioType = InitSysConfig.getInstance().getValue("upload.audio.type");
			if(StringUtils.isNotEmpty(audioType)) {
				is = ArrayUtils.isArrayContainsIgnoreCase(audioType, fileSuffix, ",");
			} else {
				LoggerUtils.error(logger, "配置文件中没定义[upload.audio.type]属性,所以不支持改该类文件的上传");
			}
		}
		return is;
	}
	
	
	/**
	 * 验证文件类型 <br />
	 * 文件类型从配置文件中获取,属性为:upload.file.type
	 * @param fileSuffix
	 * @return 验证成功返回：true，否则返回：false
	 */
	protected boolean checkFileType(String fileSuffix) {
		boolean is = false;
		if(StringUtils.isNotEmpty(fileSuffix)) {
			String fileType = InitSysConfig.getInstance().getValue("upload.file.type");
			if(StringUtils.isNotEmpty(fileType)) {
				is = ArrayUtils.isArrayContainsIgnoreCase(fileType, fileSuffix, ",");
			} else {
				LoggerUtils.error(logger, "配置文件中没定义[upload.file.type]属性,无法验证该文件类型");
			}
		}
		return is;
	}
	
	/**
	 * 获取存放图片的路径 <br />
	 * 从配置文件中获取,属性为:upload.images.dir
	 * @return 返回图片存放目录
	 */
	protected String getImageDir() {
		String dirPath = InitSysConfig.getInstance().getValue("upload.images.dir");
		File dir = new File(dirPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		dir = null;
		return dirPath;
	}
	
	
	/**
	 * 获取存放文档的路径 <br />
	 * 从配置文件中获取,属性为:upload.doc.dir
	 * @return 返回文档存放目录
	 */
	protected String getDocDir() {
		String dirPath = InitSysConfig.getInstance().getValue("upload.doc.dir");
		File dir = new File(dirPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		dir = null;
		return dirPath;
	}
	
	/**
	 * 获取存放视频的路径 <br />
	 * 从配置文件中获取,属性为:upload.video.dir
	 * @return 返回视频存放目录
	 */
	protected String getVideoDir() {
		String dirPath = InitSysConfig.getInstance().getValue("upload.video.dir");
		File dir = new File(dirPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		dir = null;
		return dirPath;
	}
	
	/**
	 * 获取存放音频的路径 <br />
	 * 从配置文件中获取,属性为:upload.audio.dir
	 * @return 返回音频文件存放目录
	 */
	protected String getAudioDir() {
		String dirPath = InitSysConfig.getInstance().getValue("upload.audio.dir");
		File dir = new File(dirPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		dir = null;
		return dirPath;
	}
	
	/**
	 * 获取存放其他文件的路径 <br />
	 * 从配置文件中获取,属性为:upload.other.dir
	 * @return 返回其他文件存放目录
	 */
	protected String getOtherDir() {
		String dirPath = InitSysConfig.getInstance().getValue("upload.other.dir");
		File dir = new File(dirPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		dir = null;
		return dirPath;
	}
	
	
	/**
	 * 获取支持上传文件的最大文件大小 <br />
	 * 从配置文件中获取,属性为:upload.max.size
	 * @return 返回支持的最大文件大小
	 */
	protected long getUploadMaxSize() {
		String sizeStr = InitSysConfig.getInstance().getValue("upload.max.size");
		long size = 0;
		try {
			size = Long.parseLong(sizeStr);
		} catch (Exception e) {
			size = 1024*1024*1000;
		}
		return size;
	}
	
	protected String getRootDir() {
		return InitSysConfig.getInstance().getValue("root.dir");
	}

}
