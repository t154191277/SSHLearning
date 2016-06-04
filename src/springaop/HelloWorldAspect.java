package springaop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 切面支持类
 * @author Administrator
 *
 */
public class HelloWorldAspect {
	
	//前置通知
	public void beforeAdvice(String param){
		System.out.println("before advice " + param);
	}
	
	//后置最终通知
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
//	    用于执行目标方法，且目标方法参数被“new Object[] {"replace"}”替换，最后返回“retVal ”返回值。
	    Object retVal = pjp.proceed(new Object[] {"replace"});  
	    System.out.println("===========around after advice");  
	    return retVal;  
	}  
}
