package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * �����絽��������ػ�request������ͻ���֮ǰ�ػ�response
 * 1.д��class 2.����
 * @author Administrator
 *
 */
public class LogFilter implements Filter{
	
	private FilterConfig config;

	@Override
	public void destroy() {
		this.config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ServletContext context = this.config.getServletContext();
		long before = System.currentTimeMillis();
		System.out.println("��ʼ����...");
		HttpServletRequest hrequest = (HttpServletRequest)request;
		context.log("Filter�Ѿ��ػ��û�������ĵ�ַ��" + hrequest.getServletPath());
		//ֻ����ʽ����������Ȼ��ת����Ŀ�ĵ�ַ
		chain.doFilter(request, response);
		long after = System.currentTimeMillis();
		context.log("���˽���");
		context.log("���󱻶�λ��" + ((HttpServletRequest)request).getRequestURI() + " ���ѵ�ʱ�䣺" + (after - before) + "ms");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
