package kr.or.bodiary.myBodiary.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.User;


public interface BodiaryDao {
	public User getUser(String id) throws ClassNotFoundException, SQLException;

}
