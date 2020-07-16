package kr.or.bodiary.routineBrd.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class RoutineBoardCommentDto {
	private int routine_cmt_seq ;
	private int routine_brd_seq ;
	private String user_email ;
	private String user_nickname ;
	
	private String routine_cmt ;
	private String routine_cmt_date ;
	private int routine_cmt_ref ;
	private int routine_cmt_depth ;
	private int routine_cmt_step ;
	
}
