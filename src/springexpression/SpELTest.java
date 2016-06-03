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
//		1）创建解析器：SpEL使用ExpressionParser接口表示解析器，提供SpelExpressionParser默认实现；
        ExpressionParser parser = new SpelExpressionParser();  
//        2）解析表达式：使用ExpressionParser的parseExpression来解析相应的表达式为Expression对象。
        Expression expression =  parser.parseExpression("('Hello' + ' World').concat(#end)");  
//        3）构造上下文：准备比如变量定义等等表达式需要的上下文数据。
        EvaluationContext context = new StandardEvaluationContext();  
//        4）求值：通过Expression接口的getValue方法根据上下文获得表达式值
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
	 * 类类型表达式
	 */
//	@Test
	public void testClassTypeExpression(){
		ExpressionParser parser = new SpelExpressionParser();
		
		//java.lang包类访问 
		Class<String> result1 = parser.parseExpression("T(String)").getValue(Class.class);
		Assert.assertEquals(String.class, result1);
		
		//其他包类访问  
		String expression2 = "T(springexpression.SpELTest)";
		Class<String> result2 = parser.parseExpression(expression2).getValue(Class.class);
		Assert.assertEquals(SpELTest.class, result2);
		
		 //类静态字段访问 
		int result3 = parser.parseExpression("T(Integer).MAX_VALUE").getValue(int.class);
		Assert.assertEquals(Integer.MAX_VALUE, result3);
		 
		//类静态方法调用  
		int result4 = parser.parseExpression("T(Integer).parseInt('1')").getValue(int.class);
		Assert.assertEquals(1, result4);
	}
	
	/**
	 * 类实例化
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
	 * 变量定义及引用
	 */
//	@Test
	public void testVariableExpression(){
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("variable", "haha");
		String result1 = parser.parseExpression("#variable").getValue(context,String.class);
		Assert.assertEquals("haha", result1);
		
//		除了引用自定义变量，SpE还允许引用根对象及当前上下文对象，使用“#root”引用根对象，使用“#this”引用当前上下文对象；
		context = new StandardEvaluationContext("haha");
		String result2 = parser.parseExpression("#root").getValue(context,String.class);
		Assert.assertEquals("haha", result2);
		
		String result3 = parser.parseExpression("#this").getValue(context,String.class);
		Assert.assertEquals("haha", result3);
	}
	
	/**
	 * 自定义函数
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
	 * 赋值表达式
	 */
//	@Test  
	public void testAssignExpression() {  
	    ExpressionParser parser = new SpelExpressionParser();  
	    //1.给root对象赋值  
	    EvaluationContext context = new StandardEvaluationContext("aaaa");  
	    String result1 = parser.parseExpression("#root='aaaaa'").getValue(context, String.class);  
	    Assert.assertEquals("aaaaa", result1);  
	    String result2 = parser.parseExpression("#this='aaaa'").getValue(context, String.class);  
	    Assert.assertEquals("aaaa", result2);  
	   
	    //2.给自定义变量赋值  
	    context.setVariable("#variable", "variable");  
	    String result3 = parser.parseExpression("#variable=#root").getValue(context, String.class);  
	    Assert.assertEquals("aaaa", result3);  
	}  
	
	/**
	 * 对象属性存取及安全导航表达式
	 */
//	@Test
	public void test(){
		ExpressionParser parser = new SpelExpressionParser();  
		//1.访问root对象属性  
		//对于当前上下文对象属性及方法访问，可以直接使用属性或方法名访问，比如此处根对象date属性“year”，注意此处属性名首字母不区分大小写。
		Date date = new Date();  
		StandardEvaluationContext context = new StandardEvaluationContext(date);  
		int result1 = parser.parseExpression("Year").getValue(context, int.class);  
		Assert.assertEquals(date.getYear(), result1);  
		int result2 = parser.parseExpression("year").getValue(context, int.class);  
		Assert.assertEquals(date.getYear(), result2);   
		
		//2.安全访问  
		//SpEL引入了Groovy的安全导航运算符，比如此处根对象为null，
		//所以如果访问其属性时肯定抛出空指针异常，而采用“?.”安全访问导航运算符将不抛空指针异常，而是简单的返回null。
		context.setRootObject(null);  
		Object result3 = parser.parseExpression("#root?.year").getValue(context, Object.class);  
		Assert.assertEquals(null, result3); 
		
		//3.给root对象属性赋值  
		context.setRootObject(date);  
		int result4 = parser.parseExpression("Year = 4").getValue(context, int.class);  
		Assert.assertEquals(4, result4);  
		parser.parseExpression("Year").setValue(context, 5);  
		int result5 = parser.parseExpression("Year").getValue(context, int.class);  
		Assert.assertEquals(5, result5);  
	}
	
	/**
	 * 对象方法调用
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
	 * Bean引用
	 * SpEL支持使用“@”符号来引用Bean，在引用Bean时需要使用BeanResolver接口实现来查找Bean，Spring提供BeanFactoryResolver实现；
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
