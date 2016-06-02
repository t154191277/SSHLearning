package hellospring;

import java.util.List;
import java.util.Map;
import java.util.Properties;


public class HelloImpl2 implements IHello{
	private IHello hello1;

	public void setHello1(IHello hello1) {
		this.hello1 = hello1;
	}

	public HelloImpl2(IHello hello1) {
		super();
		this.hello1 = hello1;
	}

	public HelloImpl2() {
		super();
	}

	@Override
	public void sayHello() {
		hello1.sayHello();
	}
	
}
