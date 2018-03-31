package cn.com.smart.form.test;

/**
 * @author lmq
 * @create 2015年7月4日
 * @version 1.0 
 * @since 
 *
 */
public class ReplaceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String contents = "<span leipiplugins=\"select\"><select fieldflow=\"1\" orgwidth=\"150\" class=\"form-control\" data-value=\"D141770362251230637\" from-data=\"dict\" orgvalue=\"\" orgtitle=\"测试下拉框\" bind_table_field=\"U143565107107067285\" bind_table=\"U143565107106990385\" style=\"width: 150px;\" "+
	"leipiplugins=\"select\" name=\"data_1\"><option value=\"1\">有效</option><option value=\"0\">无效</option></select>&nbsp;&nbsp;</span>";
		contents = contents.replaceAll("(leipiplugins=\".*?\")|(field.*?=\".*?\")|(org.*?=\".*?\")|(data-value=\".*?\")|(from.*?=\".*?\")|(bind_.*?=\".*?\")", "");
		contents = contents.replaceAll("name=\".*?\"", "name=\"M123\"");
		System.out.println(contents);

	}

}
