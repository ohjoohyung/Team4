package kr.or.bodiary.myBodiary.dto;

import lombok.Data;

@Data
public class DailyMealFoodJoinDto {
	
	private int meal_seq;
	private int meal_cart_seq;
	private int food_count;
	private String food_name;
	private String food_cal;
}
