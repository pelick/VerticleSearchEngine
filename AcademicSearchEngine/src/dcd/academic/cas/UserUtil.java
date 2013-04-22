package dcd.academic.cas;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dcd.academic.model.User;

/**
 * 该类被用来保存、获取、删除用户的SessionBean信息
 */
public class UserUtil {
	/** 
     *  
     */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户的Session标志
	 */
	public static String USER = "ui";
	/**
	 * 已登录的用户
	 */
	public static Map<String, User> loginUsers = new HashMap<String, User>();

	/**
	 * 保存用户信息到Session
	 * 
	 * @param user
	 */
	public static void saveUserToSession(User user,
			HttpServletRequest httpRequest) {
		httpRequest.getSession().setAttribute("ui", user);
	}

	/**
	 * 获取当前登录的用户
	 * 
	 * @return
	 */
	public static User getCurrentUser(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession();
		if (session != null) {
			Object sessionUser = session.getAttribute("ui");
			if (sessionUser == null) {
				return null;
			}
			User user = (User) sessionUser;
			return user;
		} else {
			return null;
		}
	}

	/**
	 * 获取当前用户的帐号
	 * 
	 * @return 当前用户的帐号
	 */
	public static String getCurrentUserName(HttpServletRequest httpRequest) {
		Object sessionUser = getCurrentUser(httpRequest);
		if (sessionUser == null) {
			return "";
		} else {
			User user = (User) sessionUser;
			return user.getUsername();
		}
	}

	/**
	 * 从session中移除用户
	 */
	public static void removeUserFromSession(HttpServletRequest httpRequest) {
		httpRequest.getSession().removeAttribute("ui");
	}
}