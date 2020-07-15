package kr.or.bodiary.notice.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.notice.dto.NoticeDto;


public interface NoticeDao {
	
	//리스트 가져오기
	public List<NoticeDto> noticeList() throws ClassNotFoundException, SQLException;
		
	//입력
	public int noticeInsert(NoticeDto noticedto) throws ClassNotFoundException, SQLException;
	
	//상세
	public NoticeDto noticeSelect(int notice_brd_seq) throws ClassNotFoundException, SQLException;
	
	//수정
	public void noticeUpdate(NoticeDto noticedto) throws ClassNotFoundException, SQLException;
	
	//삭제
	public int noticeDelete(int notice_brd_seq) throws ClassNotFoundException, SQLException;	
	
	
	
}
