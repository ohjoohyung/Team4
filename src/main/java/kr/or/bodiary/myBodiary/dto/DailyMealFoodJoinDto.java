package kr.or.bodiary.myBodiary.dto;

import lombok.Data;

@Data

//dailymeal과 food 테이블 조인
public class DailyMealFoodJoinDto {
	
	private int meal_seq;
	private String meal_cart_seq;
	private int food_count;
	private String food_name;
	private String food_cal;
	private int food_seq;
}
