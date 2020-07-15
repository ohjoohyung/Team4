package kr.or.bodiary.qnaBrd.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.exercise.dto.ExerciseDto;
import kr.or.bodiary.qnaBrd.dto.QnaBrdDto;


public interface QnaBrdDao {
	//내가 쓴 문의사항 불러오기
	public List<QnaBrdDto> getQnaList(String user_email) throws ClassNotFoundException, SQLException;
	
	//문의사항 작성하기
	public int insertQnaBrd(QnaBrdDto QnaBrdDto) throws ClassNotFoundException, SQLException;
	
	//문의상세보기
	public QnaBrdDto getQnaBrdBySeq(int qna_brd_seq) throws ClassNotFoundException, SQLException;
}
