package kr.or.bodiary.main.service;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.freeBrd.dao.FreeBrdReplyDao;
import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.main.dao.MainDao;
import kr.or.bodiary.myBodiary.dto.BodiaryDto;
import kr.or.bodiary.notice.dto.NoticeDto;
import kr.or.bodiary.routineBrd.dto.RoutineBoardUserJoinDto;
import kr.or.bodiary.utils.DateUtils;


@Service
public class MainService {
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	//메인 루틴
	public List<RoutineBoardUserJoinDto> routineBrdMain() throws ClassNotFoundException, SQLException, ParseException {
		MainDao maindao = sqlsession.getMapper(MainDao.class);
		List<RoutineBoardUserJoinDto> returnResult = null;
		if(maindao.routineBrdMainToday().isEmpty()) {
			returnResult = maindao.routineBrdMain();
		} else {
			returnResult = maindao.routineBrdMainToday();
		}
		for(RoutineBoardUserJoinDto r : returnResult) {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(r.getRoutine_brd_regdate());
			String formatDate = DateUtils.formatTimeString(date);
			System.out.println(formatDate);
			r.setRoutine_brd_regdate(formatDate);
		}
		return returnResult;
		
	}
	
	//메인 프리보드
	public List<FreeBrdDTO> freeBrdMain() throws Exception {
		MainDao maindao = sqlsession.getMapper(MainDao.class);
		List<FreeBrdDTO> list = maindao.freeBrdMain();
		for(FreeBrdDTO f : list) {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(f.getFree_brd_date());
			String formatDate = DateUtils.formatTimeString(date);
			System.out.println(formatDate);
			f.setFree_brd_date(formatDate);
		}
		
		FreeBrdReplyDao cmtlist = sqlsession.getMapper(FreeBrdReplyDao.class);

		for(int i=0;i<list.size();i++) {
			//게시글의 번호를 하나씩 얻어와 해당 게시글의 댓글수를 얻어옴 			
			list.get(i).setBrd_cmt_count(cmtlist.commentCount(list.get(i).getFree_brd_seq()));
			System.out.println(list.get(i).getFree_brd_seq()+"번호의 댓글 개수 ->"+list.get(i).getBrd_cmt_count());
		}
		return list;
	}
	
	//메인 공지(풋터)
	public List<NoticeDto> noticeMain() throws ClassNotFoundException, SQLException {
		MainDao maindao = sqlsession.getMapper(MainDao.class);
		System.out.println("noticeMain 서비스");
		return maindao.noticeMain();
	}
	
}
