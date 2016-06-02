package hellospring;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloTest {
	
//	@Test
	public void TestHelloWorld(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHello hello = context.getBean("hello",IHello.class);
		hello.sayHello();
	}
	
//	@Test
	public void TestHelloWorldByStaticFactory(){
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHello hello = factory.getBean("bean",IHello.class);
		hello.sayHello();
	}
	
//	@Test
	public void TestHelloWorldByInstanceFactory(){
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xnl");
		IHello hello = factory.getBean("bean1", IHello.class);
		hello.sayHello();
	}
	
//	@Test
	public void TestConstructDI(){
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHello hello = factory.getBean("bean2", IHello.class);
		hello.sayHello();
		
		IHello hello2 = factory.getBean("bean3", IHello.class);
		hello2.sayHello();
		
		IHello hello3 = factory.getBean("bean4", IHello.class);
		hello3.sayHello();
	}
	
//	@Test
	public void TestSetterDI(){
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHello hello = factory.getBean("bean5",IHello.class);
		hello.sayHello();
	}
	
//	@Test
	public void TestBeanDI(){
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHello hello = factory.getBean("bean12",IHello.class);
		hello.sayHello();
		
		hello = factory.getBean("bean13",IHello.class);
		hello.sayHello();
	}
	
//	@Test
	public void TestDependsOn() throws IOException{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		context.registerShutdownHook();
		SonImpl son = context.getBean("sonImpl",SonImpl.class);
		son.write("aaa");
	}
	
	@Test
	public void testAutoWrite(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		IHello hello = context.getBean("bean15",HelloImpl2.class);
		hello.sayHello();
	}
}
