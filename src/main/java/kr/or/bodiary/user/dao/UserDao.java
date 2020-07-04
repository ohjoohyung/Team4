package kr.or.bodiary.user.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.User;


public interface UserDao {
	public User getUser(String id) throws ClassNotFoundException, SQLException;
}
