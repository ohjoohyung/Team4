package kr.or.bodiary.myBodiary.dto;

import lombok.Data;

@Data

//routine이랑 routinebunddle이랑 exercise 테이블 조인
public class RoutineJoinDto {
	
	private String excs_kind ;
	private String excs_name ;
	private int excs_cal_per_hour ;
	private String excs_body_part ;
	private String excs_equipment ;
	private String excs_description ;
	private String excs_video_link ;
	private int excs_score;
	
	private String user_email ;
	private int routine_cart_seq;
	private String routine_cart_title;
	private int routine_seq;
	private int excs_seq;
	private int routine_exercise_hour;
	private int routine_set;
	private int routine_reps_per_set;
	
	private String routine_done_check;
}
