package kr.or.bodiary.myBodiary.dto;


import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class bodiaryDTO {
	private int diary_seq;
	private String user_email;
	private int routine_cart_seq;
	private String diary_content;
	private int goal_seq;
	private String user_nickname;
	private String diary_main_img;
	private String diary_sub_img1;
	private String diary_sub_img2;
	private String diary_sub_img3;
	private Date diary_date;
	private String diary_pubchk;
	private int diary_today_weight;
	private int diary_metabolism;
	private int diary_recom;
	private int diary_nut;
	
}
