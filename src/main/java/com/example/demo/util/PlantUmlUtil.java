package com.example.demo.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

import net.sourceforge.plantuml.*;

public class PlantUmlUtil {

	//private final static String path = "/Users/luckylyw19930104/Documents/test.uml";
	public static void PlantUMLGenerate(String filePath) throws IOException {
		File source = new File(filePath);
		SourceFileReader reader = new SourceFileReader(source);
		List<GeneratedImage> list = reader.getGeneratedImages();
		// Generated files
		File png = list.get(0).getPngFile();
	}

	public static String Txt2String() throws IOException {
		StringBuffer buffer = new StringBuffer();
		BufferedReader bf = new BufferedReader(new FileReader("/Users/luckylyw19930104/Documents/test.txt"));
		String s = null;
		while ((s = bf.readLine()) != null) {//使用readLine方法，一次读一行
			buffer.append(s.trim());
		}
		String xml = buffer.toString();
		return xml;
	}

	public static void String2PNG(String source) throws IOException {
		OutputStream png = null;//"output_path";
		source = "@startuml\n";
		source += "Bob -> Alice : hello\n";
		source += "@enduml\n";

		SourceStringReader reader = new SourceStringReader(source);
		// Write the first image to "png"
		String desc = reader.outputImage(png).getDescription();
		// Return a null string if no generation
	}


	public static void String2SVG(String source) throws IOException {
		source = "@startuml\n";
		source += "Bob -> Alice : hello\n";
		source += "@enduml\n";

		SourceStringReader reader = new SourceStringReader(source);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		// Write the first image to "os"
		String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
		os.close();

		// The XML is stored into svg
		final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));
	}
}