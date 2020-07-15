<<<<<<< HEAD:src/main/java/kr/or/bodiary/freeBrd/dto/freeBoardCommentDTO.java
package kr.or.bodiary.freeBrd.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class freeBoardCommentDTO {
	private int free_cat;
	private String brd_cmt_writer;
	private String brd_cmt;
	private Date brd_cmt_date;
	private int free_board_seq;
	private int brd_cmt_ref;
	private int brd_cmt_depth;
	private int brd_cmt_step;
	
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

public class FreeBoardCommentDto {
	private int free_cat;
	private String brd_cmt_writer;
	private String brd_cmt;
	private Date brd_cmt_date;
	private int free_board_seq;
	private int brd_cmt_ref;
	private int brd_cmt_depth;
	private int brd_cmt_step;
	
}
>>>>>>> da32414089600156f33d24f98ac26d321f6d372b:src/main/java/kr/or/bodiary/freeBrd/dto/FreeBoardCommentDto.java
