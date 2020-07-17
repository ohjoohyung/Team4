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
	public  List<QnaBrdDto> getQnaBrdBySeqAns(int qna_brd_seq) throws ClassNotFoundException, SQLException;
	
	//문의수정
	public int updateQnaBrd(QnaBrdDto QnaBrdDto) throws ClassNotFoundException, SQLException;
	
	//문의삭제
	public int qndBrdDelete(int qna_brd_seq) throws ClassNotFoundException, SQLException;
	
	//관리자 문의 불러오기
	public List<QnaBrdDto> getAdminQnaList(String user_email) throws ClassNotFoundException, SQLException;

	//답변 작성
	public int insertQnaAnsBrd(QnaBrdDto QnaBrdDto) throws ClassNotFoundException, SQLException;
	
	//답변 작성시 YN업데이트
	public int QnaRepYN(int qna_brd_ref) throws ClassNotFoundException, SQLException;
	
		
}
