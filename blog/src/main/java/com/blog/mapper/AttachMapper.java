package com.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.blog.domain.AttachVo;

@Mapper
public interface AttachMapper 
{
	public void insert(AttachVo attVo);
	public List<AttachVo> findByBno(Long bno);
	public void delete(Long bno);
}
