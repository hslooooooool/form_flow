/**
 * 
 */
package cn.com.smart.res.sqlmap;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author lmq
 * @version 1.0 2015年9月16日
 * @since 1.0
 * 
 */
public class ClasspathEntityResolver implements EntityResolver {
 
	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		if(null != systemId) {
			int index = systemId.lastIndexOf("/");
			String path = systemId;
			if(index>-1) {
				path = systemId.substring(systemId.lastIndexOf("/")+1);
			}
			InputStream is = this.getClass().getResourceAsStream("/sql/"+path);
			if(null != is) {
				return new InputSource(is);
			}
		}
		return null;
	}

}
