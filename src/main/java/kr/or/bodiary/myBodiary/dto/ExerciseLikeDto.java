package kr.or.bodiary.myBodiary.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class ExerciseLikeDto {
	private int excs_like_seq  ;
	private int excs_seq ;
	private String user_email ;
	private String excs_level ;
	
}
