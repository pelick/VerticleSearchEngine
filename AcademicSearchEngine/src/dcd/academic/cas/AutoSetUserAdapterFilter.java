package dcd.academic.cas;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.validation.Assertion;

import dcd.academic.util.StdOutUtil;

/**
 * CAS单点登陆的过滤器功能类，该类用来自动生成子应用的登陆Session
 * 
 */
public class AutoSetUserAdapterFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AutoSetUserAdapterFilter() {
		StdOutUtil.out("[AutoSetUserAdapterFilter]");
	}
	

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		// _const_cas_assertion_是CAS中存放登录用户名的session标志
		Object object = httpRequest.getSession().getAttribute("_const_cas_assertion_");
	
		if (object != null) {
			Assertion assertion = (Assertion) object;
			String loginName = assertion.getPrincipal().getName();
			StdOutUtil.out("[loginname]: " + loginName);
			
			Map<String, Object> map = assertion.getPrincipal().getAttributes();
			String email = (String) map.get("email");
			String name = (String) map.get("name");
			String username = (String) map.get("username");
			StdOutUtil.out("[email]: " + email);
			StdOutUtil.out("[name]: " + name);
			StdOutUtil.out("[username]: " + username);
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
