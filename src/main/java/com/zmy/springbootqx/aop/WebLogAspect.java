package com.zmy.springbootqx.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zmy.springbootqx.annotation.LogAnnotation;
import com.zmy.springbootqx.pojo.SystemLog;
import com.zmy.springbootqx.pojo.User;
import com.zmy.springbootqx.service.SystemLogService;

/**
 *test lvsw
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: WebLogAspect.java
 * @Description: AOP实现日志记录
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:02:48
 */
@Aspect
@Component
public class WebLogAspect {
	private static Logger logger = Logger.getLogger(WebLogAspect.class);
	@Autowired SystemLogService systemLogService;
	
	/**
	 * 在业务方法前执行
	 * @param joinPoint
	 * @param logAnnotation
	 */
	@Before(("execution(* com.zmy.springbootqx.controller.*Controller.*(..)) && @annotation(logAnnotation)"))
	public void doBefore(JoinPoint joinPoint, LogAnnotation logAnnotation) {
		try {
			//创建日志记录对象
			SystemLog systemLog = new SystemLog();
			//获取request对象
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = attributes.getRequest();
	        //获取session会话对象
	        HttpSession session = request.getSession();
	        
	        //判断逻辑：如果session会话中用户对象不为空，则获取到用户信息，并设置进日志记录对象中
	        if (null != session.getAttribute("user")) {
	        	User user = (User) session.getAttribute("user");
	        	systemLog.setUsername(user.getName());
	        }
	        
	        //日志输出当前执行的方法描述
	        logger.info(logAnnotation.desc());
	        
	        //设置执行的方法描述
	        systemLog.setOperation(logAnnotation.desc());
	        //设置执行的具体类名和方法名
	        systemLog.setMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
	        //设置执行方法的ip地址
	        systemLog.setIp(request.getRemoteHost());
	        //设置执行的时间
	        systemLog.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	        //调用日志业务类保存方法进行日志对象保存（写入数据库）
	        systemLogService.save(systemLog);
		} catch (Exception e) {
			//catch住控制台输出日志堆栈
			System.out.println("log error : " + e.getMessage());
		}
	}
}
