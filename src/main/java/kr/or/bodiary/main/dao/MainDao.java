package kr.or.bodiary.main.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.notice.dto.NoticeDto;
import kr.or.bodiary.routineBrd.dto.RoutineBoardUserJoinDto;


public interface MainDao {
	
	//오늘의 루틴 top2
	public List<RoutineBoardUserJoinDto> routineBrdMainToday() throws ClassNotFoundException, SQLException;
	
	//오늘의 루틴 없을 때
	public List<RoutineBoardUserJoinDto> routineBrdMain() throws ClassNotFoundException, SQLException;
	
	//보드
	public List<FreeBrdDTO> freeBrdMain() throws ClassNotFoundException, SQLException;
	
	//공지(풋터)
	public List<NoticeDto> noticeMain() throws ClassNotFoundException, SQLException;
	
}
