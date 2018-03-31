package cn.com.smart.convert;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.mixsmart.utils.StringUtils;

/**
 * XML与Bean之间的转换类
 * @author lmq
 * @version 1.0
 * @since 1.0
 * 2015年8月22日
 */
public class XmlBeanConvert {

	/**
	 * 转化为xml
	 * @param obj
	 * @param encoding
	 * @return String
	 */
	public static String toXml(Object obj,String encoding) {
		String xml = null;
		if(StringUtils.isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		try {
			JAXBContext jaxbContent= JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = jaxbContent.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			xml = writer.toString();
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"+xml;
			writer = null;
			marshaller = null;
			jaxbContent = null;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;
	}
	
	
	/**
	 * xml转化为Bean
	 * @param xml
	 * @param clazz
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String xml,Class<T> clazz) {
		T t = null;
		try {
			JAXBContext jaxbContent= JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jaxbContent.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return t;
	}
	
}
