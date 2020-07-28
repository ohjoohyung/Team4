package kr.or.bodiary.freeBrd.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.or.bodiary.freeBrd.dto.FreeBrdReplyDTO;
import kr.or.bodiary.routineBrd.dto.RoutineBoardCommentDto;

public interface FreeBrdReplyDao {

	// 댓글목록
	public List<FreeBrdReplyDTO> replyList(int seq) throws Exception;

	// 댓글 INSERT
	public int create(FreeBrdReplyDTO freeBrdReplyDTO) throws Exception;

	// 댓글 Update
	public int update(FreeBrdReplyDTO freeBrdReplyDTO) throws Exception;



	// 댓글 Delete(해당댓글 삭제하기)
	public int delete(int cno) throws Exception;

	// 현재 접속한 유저가쓴 댓글목록 가져오기
	public List<FreeBrdReplyDTO> c_replyList(String user_email) throws Exception;

	// 해당 게시글 댓글 개수
	public int commentCount(int freeBrd) throws Exception;

	// 내히스토리 자유게시판 댓글가져오기
	public List<FreeBrdReplyDTO> getUserFreeBrdReplyList(String user_email) throws ClassNotFoundException, SQLException;

	// 내히스토리 루틴자랑게시판 댓글가져오기
	public List<RoutineBoardCommentDto> getUserRoutineReplyList(String user_email)
			throws ClassNotFoundException, SQLException;
}
