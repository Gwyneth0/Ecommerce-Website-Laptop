package com.poly.service.impl;

import java.io.File;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poly.service.UploadService;

// duy
@Service
public class UploadServiceImpl implements UploadService{
	
	@Override
	public File save(MultipartFile file, String folder) {
		
		System.out.println(folder);

		//File dir = new File(System.getProperty("user.dir")+"/src/main/webapp/assets/admin/"+folder);
		
		ClassLoader classLoader = new ClassLoader() {};
		String str = classLoader.getResource("./static/assets/admin/" + folder).getPath();
		File dir = new File(str);
		
		//File dir = new File(System.getProperty("user.dir")+"/target/classes/static/assets/admin/"+folder);
		
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String s = System.currentTimeMillis() + file.getOriginalFilename();
		String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
		
		try {
			File savedFile = new File(dir, name);
			file.transferTo(savedFile);
			System.out.println("file directory: "+savedFile.getAbsolutePath());
			return savedFile;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
