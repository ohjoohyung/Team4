package kr.or.bodiary.routineBrd.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

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
