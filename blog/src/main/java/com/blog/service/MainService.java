package com.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.domain.BoardVo;
import com.blog.mapper.MainMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MainService 
{
	private final MainMapper mainMapper;
	
	public List<BoardVo> getList()
	{
		List<BoardVo> list = mainMapper.getList();
		
		for (BoardVo vo : list)
		{
			Long bno = vo.getBno();
			vo.setAttachList(mainMapper.findByBon(bno));
		}
		
		return list;
	}
}
