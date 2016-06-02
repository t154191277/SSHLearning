package hellospring;

public class IHelloInstanceFactory {
	public IHello newInstance(String msg){
		return new HelloImpl(msg);
	}
}
