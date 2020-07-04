package kr.or.bodiary.freeBrd.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.User;


public interface FreeBrdDao {
	public User getUser(String id) throws ClassNotFoundException, SQLException;

}
