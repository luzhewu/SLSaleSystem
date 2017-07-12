package org.slsale.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.RedisAPI;
import org.slsale.pojo.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 自定义拦截器，拦截请求(是否有权限访问)
 * @author lzw
 * 2017-7-11
 * 每个人都有自己的梦想。努力拼搏吧!不要让自己后悔!
 */
public class SysInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = Logger.getLogger(SysInterceptor.class);
	@Resource
	private RedisAPI redisAPI;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String urlPath = request.getRequestURI();

		User user = (User) session.getAttribute(Constants.SESSION_USER);
		if (null == user) {// 如果session中user为空，返回登录页面
			response.sendRedirect("/");
			return false;
		} else {
			String key = "Role" + user.getRoleId() + "UrlList";
			String urls = redisAPI.get(key);
			if (urls != null && !"".equals(urls) && urls.indexOf(urlPath) > 0) {
				return true;
			} else {
				response.sendRedirect("/401.html");
				return false;
			}
		}
	}

}
