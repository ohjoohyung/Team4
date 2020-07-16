package kr.or.bodiary.freeBrd.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class FreeBrdReplyDTO {
	private int brd_cmt_seq; //댓글번호
	private int free_brd_seq; //게시글 번호
	private int free_cat; //카테고리
	private String user_email; //이메일
	private String brd_cmt; //댓글내용
	private String brd_cmt_date; //댓글작성날짜
	private String brd_cmt_ref ; 
	private String brd_cmt_depth; 
	private String brd_cmt_step;
	
}
