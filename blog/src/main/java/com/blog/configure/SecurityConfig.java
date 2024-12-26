package com.blog.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig 
{
	@Bean
	public PasswordEncoder passwordEncode()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public HttpFirewall getHttpFirewall()
	{
		return new DefaultHttpFirewall();
	}
	
	@Bean
	public SecurityFilterChain sfc(HttpSecurity http) throws Exception 
	{
		http
			.formLogin(formLogin -> formLogin
					.loginPage("/mem/login")				//	로그인 페이지 URL요청 주소
					.usernameParameter("username")				//	로그인 시 사용할 파라미터 이름 설정
					.defaultSuccessUrl("/", true)					//	로그인 성공 시 redirect할 페이지
					.failureForwardUrl("/mem/login/error"))	//	실패 시..
			.logout(logout -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/mem/logout"))	//	로그아웃 페이지 URL요청 주소
					.logoutSuccessUrl("/")												//	로그아웃 성공 시 redirect할 페이지
					.invalidateHttpSession(true))										//	로그아웃 시 session 삭제 여부
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers("/css/**", "/js/**", "/iamges/**", "/fonts/**", "/error")	//	해당 URL 권한 부여
					.permitAll()																//	모두에게 부여
					.requestMatchers("/", "/mem/**", "/port/**")	//	상동
					.permitAll()									//	상동
					.requestMatchers("/admin/**")	// 상동
					.hasRole("ADMIN")				// 관리자만 부여
					.anyRequest().permitAll())
			.exceptionHandling(handling -> handling		// 인증, 인가에서 error(예외) 발생 후 처리
					.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
			.csrf(csrf -> csrf
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
						//	인증되지 않은 유저의 요청,	예외처리 로직
		
		return http.build();
	}
}
