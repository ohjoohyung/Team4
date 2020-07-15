package kr.or.bodiary.myBodiary.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class DailyMealDto {
	private int food_seq;
	private int meal_seq;
	private String meal_cart_seq;
	private int food_count;
	
	private List<DailyMealDto> dailyMealList;
	
}
