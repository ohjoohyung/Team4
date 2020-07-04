package kr.or.bodiary.chat.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.User;


public interface ChatDao {
	public User getUser(String id) throws ClassNotFoundException, SQLException;

}
