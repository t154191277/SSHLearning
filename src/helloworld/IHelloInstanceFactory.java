package helloworld;

public class IHelloInstanceFactory {
	public IHello newInstance(String msg){
		return new HelloImpl(msg);
	}
}
