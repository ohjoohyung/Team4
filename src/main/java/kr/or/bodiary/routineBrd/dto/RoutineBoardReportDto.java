package kr.or.bodiary.routineBrd.dto;

import lombok.Data;


@Data
public class RoutineBoardReportDto {
	private int routine_brd_seq;
	private String routine_reporter;
	private String routine_reportee;
	private String routine_report_reason;
}
