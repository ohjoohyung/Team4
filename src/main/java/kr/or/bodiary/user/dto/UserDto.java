package kr.or.bodiary.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class userDTO {
	private String user_email;
	private int role_seq;
	private String user_pwd;
	private String user_nickname;
	private String user_gender;
	private int user_age;
	private int user_height;
	private int user_weight;
	private String user_img;
<<<<<<< HEAD
	private int user_reportedcount; 
	private int enabled;
	private String user_grade;
	private String user_deletedate;
	private String user_insertdate;
	private String user_snstype;
	//파일 업로드 지원---------
	private CommonsMultipartFile file;
	//롤 권한 이름 
	private String role_name;
=======
	private int user_reportedcount;
	
>>>>>>> d2b6ce4a2701985f2c080a6c56beb87be62b1b33
}
