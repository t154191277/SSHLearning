package hellospring;

public class IHelloStaticFactory {
	public static IHello newInstance(String msg){
		return new HelloImpl(msg);
	}
}
