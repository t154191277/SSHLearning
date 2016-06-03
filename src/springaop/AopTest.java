package springaop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {
	
	/**
	 * Spring AOP��ΪĿ����󴴽�AOP����
	 */
//	@Test
	public void testHelloWorld(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHelloWorldService hello = context.getBean("helloWorldService",IHelloWorldService.class);
		hello.sayHello();
	}
}
