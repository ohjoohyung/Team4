package kr.or.bodiary.routineBrd.dto;

import lombok.Data;

@Data
public class RoutineBoardUserJoinDto {
	  private String routine_brd_seq;
	   private String user_email;
	   private String routine_brd_title;
	   private String routine_brd_content;
	   private String routine_cart_seq;
	   private String routine_brd_regdate;
	   private String brd_image1;
	   private String brd_image2;
	   private int routine_brd_hit;
	   private String user_nickname;
}
