package cn.com.smart.flow.test;

import com.mixsmart.utils.StringUtils;

/**
 * @author lmq
 * @create 2015年6月19日
 * @version 1.0 
 * @since 
 *
 */
public class TestUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String num = "087";
		System.out.println(StringUtils.isInteger(num));
		Integer n = Integer.parseInt(num);
		System.out.println(n);
	}

}
