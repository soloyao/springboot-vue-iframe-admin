package com.zmy.springbootqx.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zmy.springbootqx.annotation.LogAnnotation;

import springfox.documentation.annotations.ApiIgnore;

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
