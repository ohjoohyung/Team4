package kr.or.bodiary.qnaBrd.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.NotYet;


public interface QnaBrdDao {
	public NotYet getUser(String id) throws ClassNotFoundException, SQLException;

}
