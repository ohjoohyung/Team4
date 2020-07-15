package kr.or.bodiary.notice.dto;


import lombok.Data;


@Data
public class NoticeDto {
	private int notice_brd_seq;
	private String user_email;
	private String notice_brd_title;
	private String notice_brd_content;
	private String notice_brd_regdate;
	private int notice_brd_hit;
	
}
