package com.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.blog.domain.BoardVo;
import com.blog.domain.ReplyVo;
import com.blog.util.Criteria;

@Mapper
public interface BoardMapper 
{
	public Integer insertSelectKey(BoardVo boardVo);
	public Integer deletePolt(Long bno);
	public List<BoardVo> getList();
	public List<BoardVo> getListPaging(Criteria ct);
	public int getTotal(Criteria ct);
	public BoardVo getRead(Long bno);
	public BoardVo getNext(Long bno);
	public BoardVo getPrev(Long bno);
	public void update(BoardVo boardVo);
	public List<ReplyVo> getListReply(Long bno);
	public String insertReply(Long bno, String id, ReplyVo replyVo);
}
