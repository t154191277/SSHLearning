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
		hello.sayHello();
	}
}
