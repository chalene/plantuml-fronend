package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.repository.FilePathRepository;
import com.example.demo.service.FilePathService;
 
@Controller
public class FileController {
		
		@Autowired
		private FilePathService filePathService;	
		@RequestMapping("/upload")
		@ResponseBody
		public String upload(@RequestParam("file") MultipartFile file) {
			return filePathService.Upload(file);
		}
		
		@RequestMapping("/generate")
		@ResponseBody
		public void generate(@RequestParam("filePath") String filePath) {
			filePathService.Generate(filePath);
		}

		@RequestMapping("/index")
		public String index() {
			return "/index";
		}
}
