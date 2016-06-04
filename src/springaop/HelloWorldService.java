package springaop;

/**
 * Ä¿±êÀà
 * @author Administrator
 *
 */
public class HelloWorldService implements IHelloWorldService{

	@Override
	public boolean sayAfterReturning() {
		 System.out.println("============after returning"); 
		return false;
	}

	@Override
	public void sayHelloAndParam(String param) {
		System.out.println("Hello World! " + param);
	}

	@Override
	public void sayHello(String param) {
		System.out.println("Hello World! " + param);
	}

	@Override
	public void sayAfterThrowing() {
		throw new RuntimeException();
	}

	@Override
	public void sayAround(String param) {
		System.out.println("============around param:" + param);  		
	}


}
