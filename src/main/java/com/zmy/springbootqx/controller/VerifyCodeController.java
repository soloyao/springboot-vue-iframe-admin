package com.zmy.springbootqx.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.zmy.springbootqx.util.validatecode.IVerifyCodeGen;
import com.zmy.springbootqx.util.validatecode.SimpleCharVerifyCodeGenImpl;
import com.zmy.springbootqx.util.validatecode.VerifyCode;

@Controller
public class VerifyCodeController {

	private static final Logger logger = LoggerFactory.getLogger(VerifyCodeController.class);
	
	@GetMapping("/verifyCode")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
		IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
		try {
			//设置长宽
			VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
			String code = verifyCode.getCode();
			logger.info(code);
			//将VerifyCode绑定session
			request.getSession().setAttribute("VerifyCode", code);
			//设置响应头
			response.setHeader("Pragma", "no-cache");
			//设置响应头
			response.setHeader("Cache-Control", "no-cache");
			//在代理服务器端防止缓冲
			response.setDateHeader("Expires", 0);
			//设置响应内容类型
			response.setContentType("image/jpeg");
			response.getOutputStream().write(verifyCode.getImgBytes());
			response.getOutputStream().flush();
		} catch (IOException e) {
			logger.info("", e);
		}
	}
	
}
