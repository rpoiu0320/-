package com.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.domain.NoticeVo;

@Controller
@RequestMapping("/thy")
public class ThymeleafController 
{
	@GetMapping("/ex1")
	public String ex1()
	{
		return "sample/thy1";
	}
	
	@GetMapping("/ex2")
	public String ex2(Model model)
	{
		NoticeVo noticeVo = new NoticeVo();
		noticeVo.setIdx(1L);
		noticeVo.setTitle("title");
		noticeVo.setWriter("writer");
		
		model.addAttribute("noticeVo", noticeVo);
		
		return "sample/thy1";
	}
	
	@GetMapping("/ex3")
	public String ex3(Model model)
	{
		List<NoticeVo> list = new ArrayList<NoticeVo>();
		
		for (long i = 1; i <= 10; i++)
		{
			NoticeVo noticeVo = new NoticeVo();
			noticeVo.setIdx(i);
			noticeVo.setTitle("title" + i);
			noticeVo.setWriter("writer" + i);
			list.add(noticeVo);
		}
		
		model.addAttribute("list", list);
		
		return "sample/thy1";
	}
	
	@GetMapping("/ex4")
	public String ex4(Model model)
	{
		List<NoticeVo> list = new ArrayList<NoticeVo>();
		
		for (long i = 1; i <= 10; i++)
		{
			NoticeVo noticeVo = new NoticeVo();
			noticeVo.setIdx(i);
			noticeVo.setTitle("title" + i);
			noticeVo.setWriter("writer" + i);
			list.add(noticeVo);
		}
		
		model.addAttribute("list", list);
		
		return "sample/thy4";
	}
	
	@GetMapping("/ex5")
	public String ex5(Model model)
	{
		return "sample/thy5";
	}
	
	@GetMapping("/ex6")
	public String ex6(@RequestParam("param1") String param1, @RequestParam("param2") String param2, Model model)
	{
		System.out.println(param1);
		System.out.println(param2);
		
		return "sample/thy5";
	}
}
