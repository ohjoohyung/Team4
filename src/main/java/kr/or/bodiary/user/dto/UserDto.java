package kr.or.bodiary.user.dto;

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
	private int user_age;
	private int user_height;
	private int user_weight;
	private String user_img;
	private int user_reportedcount;
	private int enabled;
	
}
