package cn.com.smart.test;


import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapJsonTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			test1();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void test2() throws Exception {
		String contents = "{\"name\":\"123456\",\"age\":\"65\"}";
		ObjectMapper objMap = new ObjectMapper();
		TestBean testBean = objMap.readValue(contents, TestBean.class);
		if(null != testBean) {
			System.out.println("name:"+testBean.getName()+";age:"+testBean.getAge());
		}
		System.out.println("===================");
	}
	
	private static void test1() {
		String contents = "{\"123456\":\"{'name':'123456','age':'65'}\",\"8869\":\"{'name':'5556','age':'77'}\"}";
		ObjectMapper objMap = new ObjectMapper();
		try {
			Map<String, String> datas = objMap.readValue(contents, Map.class);
			if(null != datas) {
				Set<String> keys = datas.keySet();
				for (String key : keys) {
					System.out.println("KEY:"+key+";VALUE:"+datas.get(key));
					//ObjectMapper objMap2 = new ObjectMapper();
					String value = datas.get(key);
					value = value.replace("'", "\"");
					TestBean testBean = objMap.readValue(value, TestBean.class);
					if(null != testBean) {
						System.out.println("name:"+testBean.getName()+";age:"+testBean.getAge());
					}
					System.out.println("===================");
				}
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
