package kr.or.bodiary.myBodiary.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class dailyMealDTO {
	private int food_seq;
	private String food_name;
	private String food_cal;
	
}
