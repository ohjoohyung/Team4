package kr.or.bodiary.chat.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.NotYet;


public interface ChatDao {
	
	//답변이 안된 문의 개수 가져오기
	public int getNotAnswerCount() throws ClassNotFoundException, SQLException;
	
	//답변 

}
