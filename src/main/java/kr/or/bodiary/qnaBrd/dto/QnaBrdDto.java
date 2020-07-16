package kr.or.bodiary.qnaBrd.dto;


import java.sql.Date;

import lombok.Data;


@Data
public class QnaBrdDto {
	private int qna_brd_seq;
	private String user_email;
	private String qna_brd_title;
	private String qna_brd_content;
	private Date qna_brd_regdate;
	private int qna_brd_ref;
	private int qna_brd_depth;
	private int qna_brd_step;
	
}
