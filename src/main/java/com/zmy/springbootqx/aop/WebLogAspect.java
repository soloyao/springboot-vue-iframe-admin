package com.zmy.springbootqx.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zmy.springbootqx.annotation.LogAnnotation;
import com.zmy.springbootqx.pojo.SystemLog;
import com.zmy.springbootqx.pojo.User;
import com.zmy.springbootqx.service.SystemLogService;

@Aspect
@Component
public class WebLogAspect {
	@Autowired SystemLogService systemLogService;
	
	@After(("execution(* com.zmy.springbootqx.controller.*Controller.*(..)) && @annotation(logAnnotation)"))
	public void doAfter(JoinPoint joinPoint, LogAnnotation logAnnotation) {
		try {
			SystemLog systemLog = new SystemLog();
			//接收到请求，记录请求内容
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = attributes.getRequest();
	        
	        HttpSession session = request.getSession();
	        
	        if (null != session.getAttribute("user")) {
	        	User user = (User) session.getAttribute("user");
	        	systemLog.setUsername(user.getName());
	        }
	        
	        systemLog.setOperation(logAnnotation.desc());
	        systemLog.setMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
	        systemLog.setIp(request.getRemoteHost());
	        systemLog.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//	        systemLogService.save(systemLog);
		} catch (Exception e) {
			System.out.println("log error : " + e.getMessage());
		}
	}
}
