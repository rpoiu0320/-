package com.blog.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.domain.MemberVo;
import com.blog.mapper.SignUpMapper;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

//	DB서 회원정보 가져오는 역할
@Service
@Transactional
@RequiredArgsConstructor
@Builder
@Log4j2
public class MyUserDetailsService implements UserDetailsService 
{
	private final SignUpMapper mapper;
	
	@Override //	회원정보 담기 위해 사용하는 interface
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		MemberVo vo =  mapper.findByLoginId(username);
		String encodePassword = (vo == null) ? "" : vo.getPassword();
		
		if (vo == null)
			throw new UsernameNotFoundException(username);
		
		System.out.println("~~~~~~");
		
		return User.builder()	//	UserDetails 구현
				.username(vo.getUserName())
				.password(encodePassword)
				.build();
	}
}
