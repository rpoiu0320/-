package com.blog.domain;

import lombok.Data;

@Data
public class AttachVo 
{
	private String uuid;
	private String uploadPath;
	private String fileName;
	private String uploadFile;
	private String fileType;
	
	private Long bno;
}
