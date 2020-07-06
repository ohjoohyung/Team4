package kr.or.bodiary.exercise.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class exerciseDTO {
	private int excs_seq ;
	private String excs_kind ;
	private String excs_name ;
	private int excs_cal_per_minweight ;
	private String excs_body_part ;
	private String excs_equipment ;
	private String excs_description ;
	private String excs_video_link ;
	private int excs_level ;
	
}
