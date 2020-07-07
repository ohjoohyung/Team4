package kr.or.bodiary.myBodiary.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class FoodDto {
	private String food_seq;
	private String food_name;
	private String food_cal;
}
