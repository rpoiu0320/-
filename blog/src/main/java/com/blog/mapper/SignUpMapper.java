package com.blog.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.blog.domain.MemberVo;

@Mapper
public interface SignUpMapper 
{
	public int countByLoginId(String userName);
	public int save(MemberVo vo);
	public MemberVo findByLoginId(String userName);
}
