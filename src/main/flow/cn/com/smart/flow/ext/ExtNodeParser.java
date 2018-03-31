package cn.com.smart.flow.ext;

import org.snaker.engine.parser.NodeParser;

/**
 * 
 * @author lmq
 *
 */
public interface ExtNodeParser extends NodeParser {

	public static final String ATTR_ORG = "orgId";
	
	public static final String ATTR_FORM = "formId";
	
	public static final String ATTR_ATTACHMENT = "attachment";
	
	public static final String ATTR_FLOW_TYPE = "flowType";
	
}
