package springaop;

/**
 * ����֧����
 * @author Administrator
 *
 */
public class HelloWorldAspect {
	
	//ǰ��֪ͨ
	public void beforeAdvice(){
		System.out.println("before advice");
	}
	
	//��������֪ͨ
	public void afterFinallyAdvice(){
		System.out.println("after finally advice");
	}
}
