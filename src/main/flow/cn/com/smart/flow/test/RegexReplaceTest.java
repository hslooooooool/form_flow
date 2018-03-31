package cn.com.smart.flow.test;

/**
 * @author lmq
 * @create 2015年8月2日
 * @version 1.0 
 * @since 
 *
 */
public class RegexReplaceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String content = "rect12(1,2,3)";
		String key = content.replaceAll("(.*)\\((.*)\\)", "$1");
		System.out.println(key);
		String value = content.replaceAll("(.*)\\((.*)\\)", "$2");
		System.out.println(value);
	}

}
