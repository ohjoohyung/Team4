package kr.or.bodiary.freeBrd.dto;


import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class FreeBrdDTO {
	private int free_brd_seq;
	private int free_cat;
	private String user_email;
	private String free_brd_title;
	private String free_brd_image;
	private String free_brd_content;
	private Date free_brd_date;
	private int free_brd_report_num;
	private int free_brd_hits;
	
	//User 테이블에서 조인해서 얻어온 닉네임
	private String user_nickname;
	
	//User 테이블에서 조인해서 얻어온 프로필 사진 
	private String user_img;
	
	//해당 게시글의 댓글 개수
	private int brd_cmt_count;
	
}
