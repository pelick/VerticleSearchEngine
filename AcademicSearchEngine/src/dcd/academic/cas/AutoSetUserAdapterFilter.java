package dcd.academic.cas;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.validation.Assertion;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.UserDAO;
import dcd.academic.model.User;
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
		Object object = httpRequest.getSession().getAttribute(
				"_const_cas_assertion_");
		StdOutUtil.out("[_const_cas_assertion_]: " + object);
		if (object != null) {
			Assertion assertion = (Assertion) object;
			String loginName = assertion.getPrincipal().getName();
			StdOutUtil.out("[loginname]: " + loginName);
			User user = UserUtil.getCurrentUser(httpRequest);

			// 第一次登录系统
			if (user == null) {
				DAOfactory factory = new DAOfactory();
				UserDAO dao = factory.getUserDAO();
				user = dao.getUser(loginName);
				// 保存用户信息到Session
				UserUtil.saveUserToSession(user, httpRequest);
			}

		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
