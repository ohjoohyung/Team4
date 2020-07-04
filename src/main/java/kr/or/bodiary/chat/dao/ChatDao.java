package kr.or.bodiary.chat.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.NotYet;


public interface ChatDao {
	public NotYet getUser(String id) throws ClassNotFoundException, SQLException;

}
