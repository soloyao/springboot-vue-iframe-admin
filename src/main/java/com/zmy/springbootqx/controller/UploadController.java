package com.zmy.springbootqx.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zmy.springbootqx.annotation.LogAnnotation;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: UploadController.java
 * @Description: 文件控制层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:07:15
 */
@ApiIgnore
@RestController
public class UploadController {
	@PostMapping("/upload")
	@LogAnnotation(desc = "上传文件")
	public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		String fileName = null;
		try {
			fileName = System.currentTimeMillis() + file.getOriginalFilename();
			String path = "D:/springbootUpload";
			File destFile = new File(path + "/" + fileName);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdir();
			}
			file.transferTo(destFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}
}
