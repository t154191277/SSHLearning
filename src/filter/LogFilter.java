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
 * 可以早到达服务器截获request，到达客户端之前截获response
 * 1.写好class 2.配置
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
		System.out.println("开始过滤...");
		HttpServletRequest hrequest = (HttpServletRequest)request;
		context.log("Filter已经截获到用户的请求的地址：" + hrequest.getServletPath());
		//只是链式处理，请求仍然从转发到目的地址
		chain.doFilter(request, response);
		long after = System.currentTimeMillis();
		context.log("过滤结束");
		context.log("请求被定位到" + ((HttpServletRequest)request).getRequestURI() + " 花费的时间：" + (after - before) + "ms");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
