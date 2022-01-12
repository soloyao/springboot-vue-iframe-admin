package com.zmy.springbootqx.interceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zmy.springbootqx.annotation.PermissionAnnotation;
import com.zmy.springbootqx.pojo.User;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: LoginInterceptor.java
 * @Description: 登录拦截器
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:07:45
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (null == user) {//校验用户是否登录
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		} else {
			//校验权限
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			PermissionAnnotation permissionAnnotation = method.getAnnotation(PermissionAnnotation.class);
			if (permissionAnnotation == null) {
				return true;
			}
			
			Set<String> perms = (Set<String>) session.getAttribute("perms");
			boolean flag = false;
			for (String perm : perms) {
				if (permissionAnnotation.permName().equals(perm)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				return true;
			} else {
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("无权限访问");
				out.flush();
				out.close();
				return false;
			}
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
