package kr.or.bodiary.qnaBrd.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.qnaBrd.dto.QnaBrdDto;


public interface QnaBrdDao {
	//내가 쓴 문의사항 불러오기
	public List<QnaBrdDto> getQnaList(String user_email) throws ClassNotFoundException, SQLException;
	
	//문의사항 작성하기
	public int insertQnaBrd(QnaBrdDto QnaBrdDto) throws ClassNotFoundException, SQLException;
	
	//문의사항 ref값 입렵
	public int updaterefQnaBrd(int qna_brd_ref) throws ClassNotFoundException, SQLException;
	
	//문의상세보기
	public QnaBrdDto getQnaBrdBySeq(int qna_brd_seq) throws ClassNotFoundException, SQLException;
	
	//문의상세보기 답변
	public List<QnaBrdDto> getQnaBrdBySeqAns(int qna_brd_seq) throws ClassNotFoundException, SQLException;
	
	//문의수정
	public int updateQnaBrd(QnaBrdDto QnaBrdDto) throws ClassNotFoundException, SQLException;
	
	//문의삭제
	public int qndBrdDelete(int qna_brd_seq) throws ClassNotFoundException, SQLException;
	
	//관리자 문의 불러오기
	public List<QnaBrdDto> getAdminQnaList(String user_email) throws ClassNotFoundException, SQLException;

	//답변 작성
	public int insertQnaAnsBrd(QnaBrdDto QnaBrdDto) throws ClassNotFoundException, SQLException;
	
	//답변 작성시 YN업데이트 (밑에 알림 관련 dao에 수정해서 추가)
	/*
	 * public int QnaRepYN(int qna_brd_ref) throws ClassNotFoundException,
	 * SQLException;
	 */
	
	//문의수정상세보기
	public QnaBrdDto getQnaBrdBySeqAnsModify(QnaBrdDto QnaBrdDto) throws ClassNotFoundException, SQLException;
		
	//문의답변수정
	public int updateQnaAns(QnaBrdDto QnaBrdDto) throws ClassNotFoundException, SQLException;
	
	
	//////알림 관련 dao/////////////////////////////////////////////////////////////////////////
	
	//사용자가 어드민한테 문의했으나 아직 읽지 않은 모든 글 카운트
	public int getCountAdminNotRead()throws ClassNotFoundException, SQLException;	
	
	//어드민이 알림을 읽거나 문의게시판 관리 페이지 들어가면 개수 줄어들게 하기 위해 업데이트
	public int updateAdminRead(int qna_brd_seq)throws ClassNotFoundException, SQLException;	
	
	//어드민이 답변을 했을 경우 사용자에게 알림이 가기 위해 상태 업데이트
	public int updateQnaRep(int qna_brd_ref) throws ClassNotFoundException, SQLException;	

	//사용자 입장에서 답변이 왔지만 읽지 않은 문의글 개수 카운트 (참조 수 + 자기 이메일 아닌거로 해야할듯)
	public int getCountUserNotRead(String user_email) throws ClassNotFoundException, SQLException;	

	//사용자가 답변을 읽었을 경우 상태 업데이트
	public int updateQnaUserRead(int qna_brd_ref) throws ClassNotFoundException, SQLException;	
	
}
