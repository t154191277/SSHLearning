package tag;
/**
 * el�����ʽ���ԣ��Զ��庯��
 * 1.��Ҫ����.tld�ļ� 2.����WEB.XML
 * @author Administrator
 *
 */
public class Functions {
	
	//��ת
	public static String reverse(String text){
		return new StringBuffer(text).reverse().toString();
	}
	
	public static int countChar(String text){
		return text.length();
	}
}
