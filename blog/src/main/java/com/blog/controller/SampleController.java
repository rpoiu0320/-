package com.blog.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.blog.domain.sampleDto;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sample/*")
@Log4j2
public class SampleController 
{
	@RequestMapping("/basic")
	public void basic()	// @controller에서의 void는 경로의 jsp를 실행
	{
		System.out.println("basic 호출됨");
	}
	
	@GetMapping("/getCount")
	public void getCount()
	{
		System.out.println("getCount 호출됨");
	}
	
	@GetMapping("ex01")	// 파라미터 자동 수집
	public void ex01(sampleDto dto)
	{
		System.out.println("ex01 Name : " + dto.getName());
		System.out.println("ex01 Age : " + dto.getAge());
	}
	
	//@GetMapping("ex02")
	public void ex02(@RequestParam String name, int age)
	{
		System.out.println("ex02 Name : " + name);
		System.out.println("ex02 Age : " + age);
	}
	
	// 두 코드 중 위의 코드는 자동 바인딩 안됌, 명시적 차이
	
	@GetMapping("ex02")	
	public String getEx02(@RequestParam("name") String name, @RequestParam("age") int age)
	{
		log.info("name : " + name);
		log.info("age : " + age);
	
		return null;
	}
	
	//@RequestMapping("ex03") // html등에서 ajax, 비동기로 json을 보낼때, json만 가능
	public void ex03(@RequestBody sampleDto dto)
	{
		System.out.println("dto" + dto);
		System.out.println("ex03 Name : " + dto.getName());
		System.out.println("ex03 Age : " + dto.getAge());
	}
	
	@GetMapping("ex03")	// ex03?ids=111&ids=112&ids=113
	public String getEx03(@RequestParam("ids") ArrayList<String> ids)
	{
		for (String i : ids)
		{
			log.info(i);
		}
		
		return "redirect:/";	// 초기페이지(index)로 이동, /를 인식하여 index로 이동
	}
	
	@GetMapping("ex03_")	// 상동
	public String getEx03_(@RequestParam("ids") String[] ids)
	{
		for (String i : ids)
		{
			log.info(i);
		}
		
		return "redirect:/";	// 초기페이지(index)로 이동, /를 인식하여 index로 이동
	}
	
	@GetMapping("ex04")	// sample/ex04?name=aaa&age=19&regdate=2024/11/13
	public String getEx04(sampleDto dto)
	{
		log.info(dto.getName());
		log.info(dto.getAge());
		log.info(dto.getRegdate());
		
		return "redirect:/";
	}
	
	@GetMapping("ex04_1")	// sample/ex04_1?name=aaa&age=19&regdate=2024/11/13
	public String getEx04_1(sampleDto dto, sampleDto dto1)	// 동일한 변수명을 가진 객체가 2개 이상일 때는 모두 같은 변수를 가짐
	{
		log.info(dto.getName());
		log.info(dto1.getName());
		log.info(dto.getAge());
		log.info(dto1.getAge());
		log.info(dto.getRegdate());
		log.info(dto1.getRegdate());
		
		return null;
	}

	@GetMapping("ex04_2")	// sample/ex04_2?dto.name=aaa&dto.age=19&dto1.name=bbb&dto1.age=25
	public String getEx04_2(@ModelAttribute sampleDto dto,@ModelAttribute sampleDto dto1)	// 값을 각각 다르게 넣고싶을때 ???
	{
		log.info(dto.getName());
		log.info(dto1.getName());
		log.info(dto.getAge());
		log.info(dto1.getAge());
		
		return null;
	}
	
	// sample/ex05?name=aaa&age=20&page=10		
	@GetMapping("ex05")		// 기본 자료형은 포워딩(넘겨주기) 안됨
	public String getEx05(sampleDto dto, int page, Model model)
	{
		model.addAttribute("page", page);
		
		return "/sample/ex05";
	}
	
	@GetMapping("ex05_1")		// 기본 자료형은 포워딩(넘겨주기) 안됨
	public String getEx05_1(sampleDto dto, int page, Model model)
	{
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		
		return "/sample/ex05";
	}
	
	@GetMapping("ex06")
	public @ResponseBody sampleDto getEx06()
	{
		sampleDto dto = new sampleDto();
		dto.setName("김팔순");
		dto.setAge(50);
		
		return dto;	// @ResponseBody를 붙이면 json 형식으로 return (호출한 곳으로)
	}

	@GetMapping("ex07")
	public ResponseEntity<String> getEx07()	// json타입 이라는 헤더 메시지와 200(OK의 상태코드) 라는 상태코드를 전송
	{
		String msg = "{\"name\":\"김철수\"}";
		HttpHeaders header = new HttpHeaders();
		header.add("content-type", "application/json;charset=utf-8");
		
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	
	@GetMapping("upload")
	public String getUploadForm()
	{
		return "/sample/upload";
	}
	
	@PostMapping("ex08")
	public String getEx08(ArrayList<MultipartFile> files)
	{
		String uploadFolder = "D:/upload";
		File uploadPath = new File(uploadFolder, getFolder());

		if (!uploadPath.exists())
			uploadPath.mkdirs();
		
		files.forEach(file -> {
			/*
			 * log.info("file name : " + file.getOriginalFilename());
			 * log.info("file Name : " + file.getName()); log.info("file Size : " +
			 * file.getSize()); log.info("file Cont : " + file.getContentType());
			 * log.info("file Clas : " + file.getClass()); log.info("file Reso : " +
			 * file.getResource());
			 */
			//File saveFile = new File(uploadPath, file.getOriginalFilename());
			UUID uuid = UUID.randomUUID();
			String uploadFileName = uuid.toString() + "_" + file.getOriginalFilename();
			File saveFile = new File(uploadPath, uploadFileName);
			
			try
			{
				file.transferTo(saveFile);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
		
		return "/sample/upload";
	}
	
	@GetMapping("/ajax")
	public String getAjax()
	{
		return "/sample/ajaxForm";
	}
	
	@PostMapping("/ajax")
	public ResponseEntity<String> getAjaxForm(ArrayList<MultipartFile> uploadFile)
	{
		String uploadFolder = "D:/upload";
		
		uploadFile.forEach(file -> {
			
			log.info("file name : " + file.getOriginalFilename());
			log.info("file Name : " + file.getName()); log.info("file Size : " +file.getSize()); 
			log.info("file Cont : " + file.getContentType());
			log.info("file Clas : " + file.getClass()); log.info("file Reso : " +
			file.getResource());
			
			File saveFile = new File(uploadFolder, file.getOriginalFilename());
			
			try
			{
				file.transferTo(saveFile);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});

		String msg = "{\"msg\":\"전송완료\"}";
		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "application/json;charset=utf-8");
		
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	
	public String getFolder()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);	// 운영체제에 맞게끔 파일 구분자로 치환
	}
}
