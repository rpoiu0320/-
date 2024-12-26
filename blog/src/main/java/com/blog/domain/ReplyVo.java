package com.blog.domain;

import lombok.Data;

@Data
public class ReplyVo 
{
	private int reply_num;
    private int id;
    private int bno;
    private String content;
    private String regdate;
}
