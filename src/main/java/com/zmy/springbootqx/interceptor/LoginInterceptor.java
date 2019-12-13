package com.zmy.springbootqx.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zmy.springbootqx.pojo.User;


@Component
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		System.out.println(uri);
//		if (uri.indexOf("configuration/ui") != -1 || uri.indexOf("swagger") != -1 || uri.indexOf("api-docs") != -1) {
//			return true;
//		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (null == user) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
