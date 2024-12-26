package com.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper	// sql과 관련 처리를 지정하는 역할
public interface SampleMapper 
{
	@Select("select sysdate from dual")	// durl == 가상테이블
	public String getTime1();
	
	public String getTime2();
}
