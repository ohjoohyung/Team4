package kr.or.bodiary.routineBrd.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

<<<<<<< HEAD
@Data
public class RoutineBrdDto {
   private int routine_brd_seq;
   private String user_email;
   private String routine_brd_title;
   private String routine_brd_content;
   private int routine_cart_seq;
   private String routine_brd_regdate;
   private String brd_image1;
   private String brd_image2;
   private int routine_brd_hit;
   private List<CommonsMultipartFile> files; //다중 파일 업로드
   
}
=======
public class routineBrdDTO {
	private int routine_brd_seq;
	private String user_email;
	private int routine_cart_seq;
	private String image_open;
	private String gender_open;
	private String brd_image1;
	private String brd_image2;
	private Date brd_start;
	private Date brd_end;
	private int brd_rec;
		
}
>>>>>>> d2b6ce4a2701985f2c080a6c56beb87be62b1b33
