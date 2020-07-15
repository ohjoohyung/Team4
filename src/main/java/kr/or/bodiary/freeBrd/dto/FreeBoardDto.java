<<<<<<< HEAD:src/main/java/kr/or/bodiary/freeBrd/dto/freeBoardDTO.java
package kr.or.bodiary.freeBrd.dto;


import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class freeBoardDTO {
	private int free_board_seq;
	private int free_cat;
	private String user_email;
	private String free_brd_title;
	private String profile_image;
	private String free_brd_content;
	private Date free_brd_date;
	private int free_brd_report_num;
	
}
=======
package kr.or.bodiary.freeBrd.dto;


import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class FreeBoardDto {
	private int free_board_seq;
	private int free_cat;
	private String user_email;
	private String free_brd_title;
	private String profile_image;
	private String free_brd_content;
	private Date free_brd_date;
	private int free_brd_report_num;
	
}
>>>>>>> da32414089600156f33d24f98ac26d321f6d372b:src/main/java/kr/or/bodiary/freeBrd/dto/FreeBoardDto.java
