package kr.or.bodiary.myBodiary.dto; 

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class GoalControlDto {
	private int goal_seq   ;
	private String user_email  ;
	private int goal_ctrl_num ;
	private int routine_cart_seq ;
	private Date goal_start_date ;
	private Date goal_end_date ;
	
	
}
