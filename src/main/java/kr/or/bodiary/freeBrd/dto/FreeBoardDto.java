package kr.or.bodiary.freeBrd.dto;


import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class FreeBoardDto {
	private int free_board_seq;
	private int free_cat;
	private String user_email;
	private String free_brd_title;
	private String profile_image;
	private String free_brd_content;
	private Date free_brd_date;
	private int free_brd_report_num;
	
}
