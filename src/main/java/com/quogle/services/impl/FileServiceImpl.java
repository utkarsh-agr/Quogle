package com.quogle.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.quogle.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException{
	    
		String imageName=file.getOriginalFilename();
		
		String randomId=UUID.randomUUID().toString();
		
		System.out.println("test");
		
		String fileName1=randomId+imageName.substring(imageName.lastIndexOf("."));
		
		System.out.println(fileName1);
		
		String uploadPath=path+File.separator+fileName1;
		
		File f=new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		
		Files.copy(file.getInputStream(),Paths.get(uploadPath));
		
		
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath=path+File.separator+fileName;
		
		InputStream is=new FileInputStream(fullPath);
		
		return is;
	}

}
