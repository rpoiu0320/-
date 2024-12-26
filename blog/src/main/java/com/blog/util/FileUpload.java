package com.blog.util;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blog.domain.AttachVo;

@Component
public class FileUpload 
{
	final String uploadFolder = Paths.get("D:", "upload").toString();
	
	private String getFolder()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
	}
	
	public List<com.blog.domain.AttachVo> uploadFiles(
			@RequestParam("filename") MultipartFile fileName, @RequestParam("uploadFile") List<MultipartFile> uploadFile)
	{
		String uploadFolderPath = getFolder();
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		if (!uploadPath.exists()) 
			uploadPath.mkdirs();
		
		List<AttachVo> list = new ArrayList<>();
		String originalFileName = UUID.randomUUID().toString() + "_" + fileName.getOriginalFilename();
		File saveFile = new File(uploadPath, originalFileName);

		try
		{
			fileName.transferTo(saveFile);
			// AttachVo.setFileName(fileName.getOriginalFilename());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		for (MultipartFile multiFile : uploadFile)
		{
			AttachVo attachVo = new AttachVo();
			String multifileName = UUID.randomUUID().toString() + "_" + multiFile.getOriginalFilename();
			saveFile = new File(uploadPath, multifileName);
			
			try
			{
				multiFile.transferTo(saveFile);
				attachVo.setUuid(multifileName.substring(0, originalFileName.indexOf("_")));
				attachVo.setUploadPath(uploadFolderPath);
				attachVo.setFileName(multiFile.getOriginalFilename());
				attachVo.setUploadFile(multifileName);
				attachVo.setFileType("1");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			list.add(attachVo);
		}
		
		return list;
	}
}
