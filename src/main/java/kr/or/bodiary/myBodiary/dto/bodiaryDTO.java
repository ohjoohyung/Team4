package kr.or.bodiary.myBodiary.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class bodiaryDTO {

	private int diary_seq;
	private String meal_cart_seq;
	private String diary_today_weight;
	private String diary_content;
	private String routine_cart_seq;
	private String diary_pubchk;
	private String diary_condition;
	
	//String으로 바꾸기
	private String diary_date;

	private int diary_metabolism;
	private int diary_recom;
	private int diary_nut;
	private int goal_seq;
	private String gender;
	private String user_email;
	private String diary_main_img;
	// 파일 업로드 지원
	private CommonsMultipartFile file;
	/*
	 * private String user_nickname; 
	 * 
	 * 
	 * 
	 * private List<CommonsMultipartFile> files;
	 */

}
