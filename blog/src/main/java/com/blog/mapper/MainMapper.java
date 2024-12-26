package com.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.blog.domain.AttachVo;
import com.blog.domain.BoardVo;

@Mapper
public interface MainMapper 
{
	public List<BoardVo> getList();
	public List<AttachVo> findByBon(Long bno);
}
