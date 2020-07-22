package kr.or.bodiary.notice.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.notice.dao.NoticeDao;
import kr.or.bodiary.notice.dto.NoticeDto;
import kr.or.bodiary.user.dto.UserDto;


@Service
public class NoticeService {
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	//리스트
	public List<NoticeDto> noticeList() throws ClassNotFoundException, SQLException {
		List<NoticeDto> nlist = null;
		try {
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
			nlist = noticedao.noticeList();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nlist;
	}
	
	//입력(처리)
	public String noticeForm(NoticeDto noticedto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		noticedto.setUser_email(user.getUser_email());
		noticedao.noticeInsert(noticedto);
		return "redirect:noticeList";
	}
	
	//상세
	public NoticeDto noticeDetail(int notice_brd_seq) throws ClassNotFoundException, SQLException {		
		NoticeDto noticedto = null;
		try {
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
			noticedto = noticedao.noticeSelect(notice_brd_seq);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return noticedto;
	}
	
	//조회수 증가
	public int noticeHitCnt(int notice_brd_seq) throws ClassNotFoundException, SQLException {
		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		return noticedao.noticeHitCnt(notice_brd_seq);
	}
	
	//수정(폼)
	public NoticeDto noticeEdit(int notice_brd_seq) throws ClassNotFoundException, SQLException {
		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		NoticeDto noticedto = noticedao.noticeSelect(notice_brd_seq);
		return noticedto;
	}
	
	//수정(처리)
	public String noticeEdit(NoticeDto noticedto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		noticedao.noticeUpdate(noticedto);
		return "redirect:noticeDetail?notice_brd_seq="+noticedto.getNotice_brd_seq();
	}
	
	//삭제
	public String noticeDelete(int notice_brd_seq) throws ClassNotFoundException, SQLException {
		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		noticedao.noticeDelete(notice_brd_seq);
		return "redirect:noticeList";
	}

}
