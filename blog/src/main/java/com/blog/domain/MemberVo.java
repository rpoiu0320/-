package com.blog.domain;

import lombok.Data;

@Data
public class MemberVo 
{
	private long id;
	private String userName;
	private String password;
	private String email;
	private String role;
}
