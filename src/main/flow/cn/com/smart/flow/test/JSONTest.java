package cn.com.smart.flow.test;

import org.json.JSONArray;

/**
 * @author lmq
 * @create 2015年6月10日
 * @version 1.0 
 * @since 
 *
 */
public class JSONTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//JSONArray jsonArray = new JSONArray("");
		String jsonStr = "[[\"13888198750\",\"0871\",\"4\",\"2015-06-10 09:31:18\"],[\"18987282515\",\"0871\",\"4\",\"2015-06-09 11:40:04\"]]";
		JSONArray jsonArray = new JSONArray(jsonStr);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONArray test = jsonArray.getJSONArray(i);
			for (int j = 0; j < test.length(); j++) {
				System.out.print(test.getString(j)+",");
			}
			System.out.println();
			System.out.println("==========================");
		}
	}

}
