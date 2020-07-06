package wwwwwwww.notice.dao;

import java.sql.SQLException;
import java.util.List;

import wwwwwwww.notice.dto.NoticeDto;





public interface NoticeDao {
	//게시물 개수
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException;
	
	//전체 게시물
	public List<NoticeDto> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException;
	
	//게시물 삭제
	public int delete(String seq) throws ClassNotFoundException, SQLException;
	
	//게시물 수정
	public int update(NoticeDto notice) throws ClassNotFoundException, SQLException;
	
	//게시물 상세
	public NoticeDto getNotice(String seq) throws ClassNotFoundException, SQLException;
	
	//게시물 입력
	public int insert(NoticeDto n) throws ClassNotFoundException, SQLException;

	//조회수
	//사용자가 게시판 글을 보면 조회수 1점을 부여
	public int updateOfView(String userid) throws ClassNotFoundException, SQLException;
}