package com.blog.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class BoardVo 
{
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private LocalDate regdate;
	private LocalDate updateDate;
	
	private List<AttachVo> attachList;
}
