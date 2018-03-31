/**
 * 
 */
package cn.com.smart.test;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Test;

import cn.com.smart.security.EncryptAlg;

/**
 * 算法测试类
 * @author lmq
 * @date 2015年8月17日
 * @since 1.0
 */
public class EncryptAlgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String value = "123456";
		
		String result = EncryptAlg.MD5(value);
		System.out.println("加密后的值为:"+result);
		
		
		//System.out.println(StringEscapeUtils.unescapeHtml4("测试一下啊<br />哈哈啊测试中"));
		//replace();
	}


	public static void replace() {
		String content = "path14_rect3";
		String taskKey = content.replaceAll("path\\d+_(.*)", "$1");
		System.out.println(taskKey);
	}
	
	@Test
	public void testDESEncode() {
		String encodeStr = "5fqnkWgIoq8=";
		String value = "123456";
		String key = "abcd";
		Assert.assertEquals(encodeStr, EncryptAlg.DESEncode(value, key));
		
	}
	
	@Test
	public void testDESDecode() {
		String encodeStr = "5fqnkWgIoq8=";
		String value = "123456";
		String key = "abcd";
		Assert.assertEquals(encodeStr, EncryptAlg.DESEncode(value, key));
		
	}
	
}
