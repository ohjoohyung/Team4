package kr.or.bodiary.main.service;


import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.main.dao.MainDao;
import kr.or.bodiary.notice.dto.NoticeDto;
import kr.or.bodiary.routineBrd.dto.RoutineBoardUserJoinDto;


@Service
public class MainService {
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	//메인 루틴
	public List<RoutineBoardUserJoinDto> routineBrdMain() throws ClassNotFoundException, SQLException {
		MainDao maindao = sqlsession.getMapper(MainDao.class);
		return maindao.routineBrdMain();
	}
	
	//메인 프리보드
	public List<FreeBrdDTO> freeBrdMain() throws ClassNotFoundException, SQLException {
		MainDao maindao = sqlsession.getMapper(MainDao.class);
		return maindao.freeBrdMain();
	}
	
	//메인 공지(풋터)
	public List<NoticeDto> noticeMain() throws ClassNotFoundException, SQLException {
		MainDao maindao = sqlsession.getMapper(MainDao.class);
		System.out.println("noticeMain 서비스");
		return maindao.noticeMain();
	}
	
}