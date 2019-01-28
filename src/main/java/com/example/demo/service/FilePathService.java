package com.example.demo.service;


import java.io.File;
import java.io.IOException;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.pojo.FilePath;
import com.example.demo.repository.FilePathRepository;
import com.example.demo.util.FileUtil;
import com.example.demo.util.PlantUmlUtil;

import net.sourceforge.plantuml.asciiart.UmlCharArea;

@Service
public class FilePathService {
	
	
	@Autowired
	private FilePathRepository filePathRepository;
	
	public String Upload(@RequestParam("file") MultipartFile file) {
		if(!file.isEmpty()) {
					
			String fileName = file.getOriginalFilename();		
			
			
			String path = "/Users/luckylyw19930104/eclipse-workspace/plantuml/src/main/resources/static";
 
			try {			
				FileUtil.fileupload(file.getBytes(), path, fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String umlFile = "";
			try {
				umlFile = PlantUmlUtil.Txt2String();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			FilePath biaopath = new FilePath();
			biaopath.setPath("http://localhost:8080/"+fileName);
			biaopath.setUml(umlFile);
			filePathRepository.save(biaopath);
			
		}
		return "success";	
	}
	
	public void Generate(@RequestParam("filePath") String filePath) {
		try {
			PlantUmlUtil.PlantUMLGenerate(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
