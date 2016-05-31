package tag;
/**
 * el（表达式语言）自定义函数
 * 1.需要配置.tld文件 2.配置WEB.XML
 * @author Administrator
 *
 */
public class Functions {
	
	//翻转
	public static String reverse(String text){
		return new StringBuffer(text).reverse().toString();
	}
	
	public static int countChar(String text){
		return text.length();
	}
}
