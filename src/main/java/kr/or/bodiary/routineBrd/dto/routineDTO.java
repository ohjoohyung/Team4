package kr.or.bodiary.routineBrd.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class routineDTO {
	private int routine_seq;
	private int excs_seq;
	private int routine_exercise_hour;
	private int routine_set;
	private int routine_reps_per_set;
	private String routine_default_check;
	private String routine_done_check;
}
