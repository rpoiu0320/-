package com.blog.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.domain.AttachVo;
import com.blog.domain.BoardVo;
import com.blog.domain.ReplyVo;
import com.blog.mapper.AttachMapper;
import com.blog.mapper.BoardMapper;
import com.blog.util.Criteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor	// fianl, @notNull의 생성자 생성, autowired와 유사하지만 사용에 유의
@Log4j2
public class BoardService 
{
	private final BoardMapper mapper;
	private final AttachMapper attMapper;
	
	@Transactional	// oracle서 예외 발생 시 roll back
	public void register(BoardVo vo)
	{
		mapper.insertSelectKey(vo);

		if (vo.getAttachList() == null || vo.getAttachList().size() <= 0)
			return;
		
		vo.getAttachList().forEach(attach -> 
			{
				attach.setBno(vo.getBno());
				attMapper.insert(attach);
			}
		);
	}
	
	@Transactional
	public Integer deletePort(Long bno)
	{
		List<AttachVo> attList = attMapper.findByBno(bno);
		attDelete(attList);
		mapper.deletePolt(bno);
		
		return 1;
	}

	@Transactional
	public void update(BoardVo boardVo, List<AttachVo> newAtt)
	{
		mapper.update(boardVo);
		attMapper.delete(boardVo.getBno());		// DB서 삭제
		attDelete(boardVo.getAttachList());		// 폴더서 삭제
		boardVo.setAttachList(newAtt);
		
		boardVo.getAttachList().forEach(attach -> 
		{
			attach.setBno(boardVo.getBno());
			attMapper.insert(attach);
		});
//		List<AttachVo> list = attMapper.findByBno(boardVo.getBno());
	}
	
	public void attDelete(List<AttachVo> attList)
	{
		if (attList != null && attList.size() >= 1)
		{
			attList.forEach(attach -> 
			{
				try
				{
					StringBuilder strb = new StringBuilder();
					strb.append("D:/upload/")
						.append(attach.getUploadPath())
						.append("/")
						.append(attach.getUploadFile());
					
									// Path
					Files.delete(Paths.get(strb.toString()));
					System.out.println("지울 파일 이름" + Paths.get(strb.toString()));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			});
		}
	}
	
	public List<BoardVo> getList()
	{
		return mapper.getList();
	}
	
	public List<BoardVo> getListPaging(Criteria ct)
	{
		List<BoardVo> list = mapper.getListPaging(ct);
		
		for(BoardVo vo : list)
		{
			List<AttachVo> alist = attMapper.findByBno(vo.getBno());
			vo.setAttachList(alist);
		}
		
		return list;
	}
	
	public List<ReplyVo> getListReply(Long bno)
	{
		return mapper.getListReply(bno);
	}
	
	public void insertReply(Long bno, String id, ReplyVo replyVo)
	{
		mapper.insertReply(bno, id, replyVo);
	}
	
	public int getTotal(Criteria ct)
	{
		return mapper.getTotal(ct);
	}
	
	public BoardVo getRead(Long bno)
	{
		return mapper.getRead(bno);
	}
	
	public List<AttachVo> getAttach(Long bno)
	{
		return attMapper.findByBno(bno);
	}
	
	public BoardVo getNext(Long bno)
	{
		return mapper.getNext(bno);
	}
	
	public BoardVo getPrev(Long bno)
	{
		return mapper.getPrev(bno);
	}
}
