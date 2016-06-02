package helloworld;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloTest {
	
	@Test
	public void TestHelloWorld(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHello hello = context.getBean("hello",IHello.class);
		hello.sayHello();
	}
	
	@Test
	public void TestHelloWorldByStaticFactory(){
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHello hello = factory.getBean("bean",IHello.class);
		hello.sayHello();
	}
	
	@Test
	public void TestHelloWorldByInstanceFactory(){
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xnl");
		IHello hello = factory.getBean("bean1", IHello.class);
		hello.sayHello();
	}
}
