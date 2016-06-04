package springaop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {
	
	/**
	 * Spring AOP将为目标对象创建AOP代理
	 */
//	@Test
	public void testHelloWorld(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHelloWorldService hello = context.getBean("helloWorldService",IHelloWorldService.class);
		hello.sayHello("param");
	}
	
//	@Test
	public void testHelloWorldAndParam(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHelloWorldService hello = context.getBean("helloWorldService",IHelloWorldService.class);
		hello.sayHelloAndParam("param");
	}
	
//	@Test
	public void testHelloWorldAfterReturning(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHelloWorldService hello = context.getBean("helloWorldService",IHelloWorldService.class);
		hello.sayAfterReturning();
	}
	
	@Test  
	public void testAnnotationBeforeAdvice() {  
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");  
	    IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);  
	    helloworldService.sayHello("before");  
	    System.out.println("======================================");  
	}  
}
