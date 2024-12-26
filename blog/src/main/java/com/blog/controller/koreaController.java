package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/korea/*")
@RequiredArgsConstructor
public class koreaController 
{
	@GetMapping("test")
	public String koreaTest()
	{
		return "/korea/korea";
	}
}
