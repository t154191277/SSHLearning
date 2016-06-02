package helloworld;


public class HelloImpl implements IHello{
	
	private String msg;
	
	public HelloImpl()
	{
		this.msg = "Hello World!";
	}
	public HelloImpl(String msg){
		this.msg = msg;
	}
	
	@Override
	public void sayHello() {
		System.out.println(msg);
	}
	
}
