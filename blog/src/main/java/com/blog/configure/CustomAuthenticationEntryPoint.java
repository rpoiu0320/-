package com.blog.configure;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	// 인증되지 않은 사용자가 리소스를 요청할 경우 Unauthorized 에러를 발생시킴
	// AuthenticationEntryPoint 인터페이스 구현
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException 
	{
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		//response.sendRedirect("/error/401");
	}

}
