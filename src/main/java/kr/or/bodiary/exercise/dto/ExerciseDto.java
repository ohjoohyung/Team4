package kr.or.bodiary.exercise.dto;



import lombok.Data;



@Data
public class ExerciseDto {
	private int excs_seq ;
	private String excs_kind ;
	private String excs_name ;
	private float excs_cal_per_minweight;
	private String excs_body_part ;
	private String excs_equipment ;
	private String excs_description ;
	private String excs_video_link ;
	private float excs_level ;
	
	
}
