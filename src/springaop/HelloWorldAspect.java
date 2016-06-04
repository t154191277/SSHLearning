package springaop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * ����֧����
 * @author Administrator
 *
 */
public class HelloWorldAspect {
	
	//ǰ��֪ͨ
	public void beforeAdvice(String param){
		System.out.println("before advice " + param);
	}
	
	//��������֪ͨ
	public void afterFinallyAdvice(String param){
		System.out.println("after finally advice " + param);
	}
	
	//
	public void afterReturningAdvice(Object retVal){
		System.out.println("===========after returning advice retVal:" + retVal); 
	}
	
	public void afterThrowing(Exception exception){
		System.out.println(exception);
	}
	
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {  
	    System.out.println("===========around before advice");  
//	    ����ִ��Ŀ�귽������Ŀ�귽����������new Object[] {"replace"}���滻����󷵻ء�retVal ������ֵ��
	    Object retVal = pjp.proceed(new Object[] {"replace"});  
	    System.out.println("===========around after advice");  
	    return retVal;  
	}  
}
