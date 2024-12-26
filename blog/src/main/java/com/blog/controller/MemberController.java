package com.blog.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.domain.MemberVo;
import com.blog.service.SignUpService;
import com.blog.util.MailSenderRunner;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mem")
@Log4j2
public class MemberController 
{
	private final SignUpService service;
	
	private final MailSenderRunner msr;
	private String checkIncode = "";
	
	@GetMapping("terms.do")
	public String memberForm()
	{
		return "/member/memberTerms";
	}
	
	
	
	@GetMapping("mail.do")
	@ResponseBody
	public String mailSend(@RequestParam("email") String email)
	{
		checkIncode = msr.sendMail(email);

		return checkIncode;
	}
	
	@GetMapping("sign.do")
	public String signForm(@RequestParam("incodeCheck") String incodeCheck, Model model)
	{
		/*
		 * if (checkIncode.equals(incodeCheck)) {
		 */
			model.addAttribute("check", "success");

			return "/member/member";
		/*}
		else
		{
			checkIncode = "";
			
			return "/member/mailsend";
		}*/
	}
	
	@PostMapping("/idcheck.do")
	public ResponseEntity<Integer> idCheck(@RequestParam("userName") String userName)
	{
		return  ResponseEntity.ok(service.countMemberByLoginId(userName));
	}
	
	@PostMapping("/memberpro.do")
	@ResponseBody
	public ResponseEntity<Map<String, String>> signUp(MemberVo vo)
	{
		int check = service.saveMember(vo);
		Map<String, String> response = new HashMap<>();
	    response.put("status", check > 0 ? "success" : "not success");

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/login")
	public String loginForm()
	{
		return "/member/login";
	}
	
	@PostMapping("/loginpro.do")	// jsp session 방식으로
	public String login(@RequestParam Map<String, String> loginMap, Model model, HttpServletRequest request)
	{
		String userName = loginMap.get("username");
		String password= loginMap.get("password");
		MemberVo member = service.findByLoginId(userName, password);
		HttpSession session = request.getSession();
		session.setAttribute("member", member);
		
		return "redirect:/";
	}
}
