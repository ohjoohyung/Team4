package kr.or.bodiary.freeBrd.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
public class freeBoardCommentDto {
	private int free_cat;
	private String brd_cmt_writer;
	private String brd_cmt;
	private Date brd_cmt_date;
	private int free_board_seq;
	private int brd_cmt_ref;
	private int brd_cmt_depth;
	private int brd_cmt_step;
	
}
