package com.example.demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
 
public class FileUtil {
	
	public static void fileupload(byte[] file,String filePath,String fileName) throws IOException {
			
		File targetfile = new File(filePath);
		if(targetfile.exists()) {
			targetfile.mkdirs();
		}
		
		
		FileOutputStream out = new FileOutputStream(filePath+fileName);
	    out.write(file);
	    out.flush();
	    out.close();
	}
}
