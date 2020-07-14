package kr.or.bodiary.user.dto;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
public class UserDto {
	private String user_email;
	private String user_pwd;
	private String user_nickname;
	private String user_gender;	
	private int user_age; //int
	private int user_height; //int
	private int user_weight; //int
	private String user_img;
	private int user_reportedcount; 
	private int enabled;
	private String user_grade;
	private String user_deletedate;
	//파일 업로드 지원---------
	private CommonsMultipartFile file;
	
	
}
