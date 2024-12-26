package com.blog.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.domain.MemberVo;
import com.blog.mapper.SignUpMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService 
{
	private final SignUpMapper mapper;
	private final PasswordEncoder encoder;
	
	public int countMemberByLoginId(final String userName)
	{
		return mapper.countByLoginId(userName);
	}
	
	@Transactional
	public int saveMember(MemberVo vo)
	{
		//System.out.println("암호화 전 : " + vo.getPassword());
		
		vo.setPassword(encoder.encode(vo.getPassword()));
		
		//System.out.println("암호화 후 : " + vo.getPassword()); 
		
		return  mapper.save(vo);
	}

	@Transactional
	public MemberVo findByLoginId(String name, String password)
	{
		MemberVo vo =  mapper.findByLoginId(name);
		
		if (vo != null && encoder.matches(password, vo.getPassword()))
			return vo;
		else
			return null;
		
	}
}
