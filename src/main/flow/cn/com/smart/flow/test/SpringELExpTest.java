package cn.com.smart.flow.test;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author lmq
 * @create 2015年6月12日
 * @version 1.0 
 * @since 
 *
 */
public class SpringELExpTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("age", 54);
		context.setVariable("qt", 90);
		String value =  parser.parseExpression("#age>20?'你已经老了':'你还很年轻哦'").getValue(context,String.class);
		//parser.parseExpression("").getValue(context, String.class);
		System.out.println(value);
	}

}
