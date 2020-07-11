package kr.or.bodiary.user.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.NotYet;


public interface UserDao {
	public NotYet getUser(String id) throws ClassNotFoundException, SQLException;
}
