package com.blog.util;

import lombok.Data;

@Data
public class PageVO 
{
	private int total;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private Criteria ct;
	
	public PageVO(Criteria ct, int total) 
	{
		this.ct = ct;
		this.total = total;
		this.endPage = (int)Math.ceil((ct.getPageNum() / 10.0)) * 10;
		this.startPage = endPage - 9;
		int realEnd = (int)(Math.ceil((total * 1.0) / ct.getAmount()));
		
		if (realEnd < this.endPage)
			this.endPage = realEnd;
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
		
	};
}