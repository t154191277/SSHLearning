package springaop;

/**
 * Ŀ����
 * @author Administrator
 *
 */
public class HelloWorldService implements IHelloWorldService{

	@Override
	public void sayHello() {
		System.out.println("Hello World!");
	}

}
