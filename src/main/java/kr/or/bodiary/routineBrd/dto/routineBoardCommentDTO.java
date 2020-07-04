package kr.or.bodiary.routineBrd.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class routineBoardCommentDTO {
	private int routine_cmt_seq ;
	private int routine_brd_seq ;
	private String routine_cmt_writer ;
	
	private String routine_cmt ;
	private Date routine_cmt_date ;
	private int routine_cmt_ref ;
	private int routine_cmt_depth ;
	private int routine_cmt_step ;
	
}
