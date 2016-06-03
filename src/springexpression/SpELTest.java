package springexpression;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELTest {
//	@Test
	public void helloworld(){
//		1��������������SpELʹ��ExpressionParser�ӿڱ�ʾ���������ṩSpelExpressionParserĬ��ʵ�֣�
        ExpressionParser parser = new SpelExpressionParser();  
//        2���������ʽ��ʹ��ExpressionParser��parseExpression��������Ӧ�ı��ʽΪExpression����
        Expression expression =  parser.parseExpression("('Hello' + ' World').concat(#end)");  
//        3�����������ģ�׼�������������ȵȱ��ʽ��Ҫ�����������ݡ�
        EvaluationContext context = new StandardEvaluationContext();  
//        4����ֵ��ͨ��Expression�ӿڵ�getValue�������������Ļ�ñ��ʽֵ
        context.setVariable("end", "!");  
        Assert.assertEquals("Hello World!", expression.getValue(context));  
	}
	
//	@Test
	public void testParserContext(){
		ExpressionParser parser = new SpelExpressionParser();
		ParserContext context = new ParserContext() {
			
			@Override
			public boolean isTemplate() {
				return true;
			}
			
			@Override
			public String getExpressionSuffix() {
				return "}";
			}
			
			@Override
			public String getExpressionPrefix() {
				return "#{";
			}
		};
		String template = "#{'Hello '}#{'World!'}";
		Expression expression = parser.parseExpression(template,context);
		Assert.assertEquals("Hello World!", expression.getValue());
	}
	/**
	 * �����ͱ��ʽ
	 */
//	@Test
	public void testClassTypeExpression(){
		ExpressionParser parser = new SpelExpressionParser();
		
		//java.lang������� 
		Class<String> result1 = parser.parseExpression("T(String)").getValue(Class.class);
		Assert.assertEquals(String.class, result1);
		
		//�����������  
		String expression2 = "T(springexpression.SpELTest)";
		Class<String> result2 = parser.parseExpression(expression2).getValue(Class.class);
		Assert.assertEquals(SpELTest.class, result2);
		
		 //�ྲ̬�ֶη��� 
		int result3 = parser.parseExpression("T(Integer).MAX_VALUE").getValue(int.class);
		Assert.assertEquals(Integer.MAX_VALUE, result3);
		 
		//�ྲ̬��������  
		int result4 = parser.parseExpression("T(Integer).parseInt('1')").getValue(int.class);
		Assert.assertEquals(1, result4);
	}
	
	/**
	 * ��ʵ����
	 */
//	@Test
	public void testConstructorExpressin(){
		ExpressionParser parser = new SpelExpressionParser();
		
		String result1 = parser.parseExpression("new String('haha')").getValue(String.class);
		Assert.assertEquals("haha", result1);
		
		java.util.Date result2 = parser.parseExpression("new java.util.Date()").getValue(java.util.Date.class);
		Assert.assertNotNull(result2);
	}
	
	/**
	 * �������弰����
	 */
//	@Test
	public void testVariableExpression(){
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("variable", "haha");
		String result1 = parser.parseExpression("#variable").getValue(context,String.class);
		Assert.assertEquals("haha", result1);
		
//		���������Զ��������SpE���������ø����󼰵�ǰ�����Ķ���ʹ�á�#root�����ø�����ʹ�á�#this�����õ�ǰ�����Ķ���
		context = new StandardEvaluationContext("haha");
		String result2 = parser.parseExpression("#root").getValue(context,String.class);
		Assert.assertEquals("haha", result2);
		
		String result3 = parser.parseExpression("#this").getValue(context,String.class);
		Assert.assertEquals("haha", result3);
	}
	
	/**
	 * �Զ��庯��
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
//	@Test
	public void testCustomedMethod() throws NoSuchMethodException, SecurityException{
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		Method parseInt = Integer.class.getDeclaredMethod("parseInt", String.class);
		context.registerFunction("parseInt", parseInt);
		context.setVariable("parseInt1", parseInt);
		String expression = "#parseInt('3') == #parseInt1('3')";
		boolean result = parser.parseExpression(expression).getValue(context,boolean.class);
		Assert.assertEquals(true, result);
	}
	
	/**
	 * ��ֵ���ʽ
	 */
//	@Test  
	public void testAssignExpression() {  
	    ExpressionParser parser = new SpelExpressionParser();  
	    //1.��root����ֵ  
	    EvaluationContext context = new StandardEvaluationContext("aaaa");  
	    String result1 = parser.parseExpression("#root='aaaaa'").getValue(context, String.class);  
	    Assert.assertEquals("aaaaa", result1);  
	    String result2 = parser.parseExpression("#this='aaaa'").getValue(context, String.class);  
	    Assert.assertEquals("aaaa", result2);  
	   
	    //2.���Զ��������ֵ  
	    context.setVariable("#variable", "variable");  
	    String result3 = parser.parseExpression("#variable=#root").getValue(context, String.class);  
	    Assert.assertEquals("aaaa", result3);  
	}  
	
	/**
	 * �������Դ�ȡ����ȫ�������ʽ
	 */
//	@Test
	public void test(){
		ExpressionParser parser = new SpelExpressionParser();  
		//1.����root��������  
		//���ڵ�ǰ�����Ķ������Լ��������ʣ�����ֱ��ʹ�����Ի򷽷������ʣ�����˴�������date���ԡ�year����ע��˴�����������ĸ�����ִ�Сд��
		Date date = new Date();  
		StandardEvaluationContext context = new StandardEvaluationContext(date);  
		int result1 = parser.parseExpression("Year").getValue(context, int.class);  
		Assert.assertEquals(date.getYear(), result1);  
		int result2 = parser.parseExpression("year").getValue(context, int.class);  
		Assert.assertEquals(date.getYear(), result2);   
		
		//2.��ȫ����  
		//SpEL������Groovy�İ�ȫ���������������˴�������Ϊnull��
		//�����������������ʱ�϶��׳���ָ���쳣�������á�?.����ȫ���ʵ�������������׿�ָ���쳣�����Ǽ򵥵ķ���null��
		context.setRootObject(null);  
		Object result3 = parser.parseExpression("#root?.year").getValue(context, Object.class);  
		Assert.assertEquals(null, result3); 
		
		//3.��root�������Ը�ֵ  
		context.setRootObject(date);  
		int result4 = parser.parseExpression("Year = 4").getValue(context, int.class);  
		Assert.assertEquals(4, result4);  
		parser.parseExpression("Year").setValue(context, 5);  
		int result5 = parser.parseExpression("Year").getValue(context, int.class);  
		Assert.assertEquals(5, result5);  
	}
	
	/**
	 * ���󷽷�����
	 */
//	@Test
	public void testObjectFunction(){
		ExpressionParser parser = new SpelExpressionParser(); 
		Date date = new Date();  
		StandardEvaluationContext context = new StandardEvaluationContext(date);  
		int result2 = parser.parseExpression("getYear()").getValue(context, int.class);  
		Assert.assertEquals(date.getYear(), result2);  
	}
	
	/**
	 * Bean����
	 * SpEL֧��ʹ�á�@������������Bean��������Beanʱ��Ҫʹ��BeanResolver�ӿ�ʵ��������Bean��Spring�ṩBeanFactoryResolverʵ�֣�
	 */
	@Test
	public void testBeanExpression() {  
	    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();  
	    ctx.refresh();  
	    ExpressionParser parser = new SpelExpressionParser();  
	    StandardEvaluationContext context = new StandardEvaluationContext();  
	    context.setBeanResolver(new BeanFactoryResolver(ctx));  
	    Properties result1 = parser.parseExpression("@systemProperties").getValue(context, Properties.class);  
	    Assert.assertEquals(System.getProperties(), result1);  
	}  
}
