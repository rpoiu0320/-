package com.blog.util;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class MailSenderRunner 
{
	private final JavaMailSender sender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	public String sendMail(String mail)
	{
		Random ran = new Random();
		int checkNum = ran.nextInt(888888) + 111111;
		System.out.println("인증번호 : " + checkNum);
		System.out.println(mail);
		
		StringBuilder stb = new StringBuilder();
		stb.append("방문 감사함 <br><br>")
			.append("인증번호 : " + checkNum)
			.append("<br> 인증번호 확인부탁");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(mail);
		message.setSubject("대충 메일 제목");
		message.setText(stb.toString());
		message.setSentDate(new Date());
		sender.send(message);
		String num = Integer.toString(checkNum);
		
		return num;
	}
}
