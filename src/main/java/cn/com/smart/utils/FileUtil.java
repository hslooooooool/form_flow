package cn.com.smart.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

import com.mixsmart.utils.StringUtils;

/**
 * 文件工具类
 * @author lmq
 *
 */
public class FileUtil {

	private static final Logger log = Logger.getLogger(FileUtil.class);
	
	/**
	 * 获取文件分隔符
	 * @return 返回系统对应的分隔符
	 */
	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}
	
	/**
	 * 删除文件
	 * @param filePath
	 * @return 成功返回：true；否则返回：false
	 */
	public static boolean deleteFile(String filePath) {
		boolean is = false;
		log.info("删除文件...");
		if(StringUtils.isNotEmpty(filePath)) {
			File file = new File(filePath);
			if(file.exists()) {
				is = file.delete();
				if(is) {
					log.info("["+filePath+"]文件删除[成功]..");
				} else {
					log.error("["+filePath+"]文件删除[失败]..");
				}
			} else {
				log.error("文件不存在["+filePath+"]");
			}
		} else {
			log.error("文件路径为空");
		}
		return is;
	}
	
	
	/**
	 * 读取文件内容
	 * @param filePath
	 * @param isLine 是否换行符 
	 * @return 返回读取到的字符串
	 */
	public static String readFile(String filePath,boolean isLine) {
		StringBuffer content = null;
		if(StringUtils.isNotEmpty(filePath)) {
			File file = new File(filePath);
			if(!file.exists()) {
				log.error("文件不存在["+filePath+"]");
			} else {
				try {
					BufferedReader buffReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
					String line = null;
					content = new StringBuffer();
					while((line = buffReader.readLine()) != null) {
						if(isLine) {
							line = line+"\n";
						}
						content.append(line);
					}
					buffReader.close();
					buffReader = null;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		} else {
			log.error("文件路径为空");
		}
		return (null == content)?null:content.toString();
	}
	
	
	/**
	 * 内容写入到文件
	 * @param contents
	 * @param filePath
	 * @param cover 如果文件存在，是否覆盖
	 * @return 写入成功返回：true；否则返回：false
	 */
	public static boolean writeFile(String contents,String filePath,boolean cover) {
		boolean is = false;
		if(StringUtils.isNotEmpty(filePath) && StringUtils.isNotEmpty(contents)) {
			File file = new File(filePath);
			if(file.exists()) {
				log.error("文件已存在["+filePath+"]");
				if(cover)
					file.delete();
				else 
					return is;
			}
			File dir = file.getParentFile();
			if(!dir.exists()) {
				dir.mkdirs();
			}
			try {
				BufferedWriter buffWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8"));
				buffWriter.write(contents);
				buffWriter.flush();
				buffWriter.close();
				is = true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			log.error("文件路径或内容为空");
		}
		return is;
	}
	
	
	
	/**
	 * 内容写入到文件中
	 * @param filePath
	 * @param contents
	 * @return 写入成功返回：true；否则返回：false
	 */
	public static boolean writeFile(String filePath,String contents) {
		boolean is = false;
		if(StringUtils.isNotEmpty(filePath)) {
			File file = new File(filePath);
			if(null != file && file.exists()) {
				try {
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
					writer.write(contents);
					writer.flush();
					writer.close();
					is = true;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}  catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				log.error("文件不存在["+file.getAbsolutePath()+"]");
			}
		}
		return is;
	}
	
	
	
	/**
	 * 内容写入到文件中
	 * @param file
	 * @param contents
	 * @return 写入成功返回：true；否则返回：false
	 */
	public static boolean writeFile(File file,String contents) {
		boolean is = false;
		if(null != file && file.exists()) {
			try {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
				writer.write(contents);
				writer.flush();
				writer.close();
				is = true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}  catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			log.error("文件不存在["+file.getAbsolutePath()+"]");
		}
		return is;
	}
}
