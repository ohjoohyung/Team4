package kr.or.bodiary.routineBrd.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter

public class routineBoardReportDTO {
	private int routine_brd_seq  ;
	private String routine_reporter  ;
	
	
	private String routine_reportee  ;
	private String routine_report_reason ;
	
}
