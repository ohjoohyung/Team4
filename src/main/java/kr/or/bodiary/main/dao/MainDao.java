package kr.or.bodiary.main.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.notice.dto.NoticeDto;
import kr.or.bodiary.routineBrd.dto.RoutineBoardUserJoinDto;


public interface MainDao {
	
	//메인 루틴
	public List<RoutineBoardUserJoinDto> routineBrdMain() throws ClassNotFoundException, SQLException;
	
	//메인 프리보드 HOT3
	public List<FreeBrdDTO> freeBrdMain() throws ClassNotFoundException, SQLException;
	
	//메인 공지(풋터)
	public List<NoticeDto> noticeMain() throws ClassNotFoundException, SQLException;
}
