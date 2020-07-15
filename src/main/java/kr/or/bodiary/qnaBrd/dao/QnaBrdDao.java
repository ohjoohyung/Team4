package kr.or.bodiary.qnaBrd.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.qnaBrd.dto.QnaBrdDto;


public interface QnaBrdDao {
	//내가 쓴 문의사항 불러오기
	public List<QnaBrdDto> getQnaList(String user_email) throws ClassNotFoundException, SQLException;
	
}
