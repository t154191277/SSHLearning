package springaop;

public interface IHelloWorldService {
	public void sayHelloAndParam(String param);
	public void sayHello(String param);
	public boolean sayAfterReturning();
	public void sayAfterThrowing();
	public void sayAround(String param);
}
