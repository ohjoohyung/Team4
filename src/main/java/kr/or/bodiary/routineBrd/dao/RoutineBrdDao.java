package kr.or.bodiary.routineBrd.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.User;


public interface RoutineBrdDao {
	public User getUser(String id) throws ClassNotFoundException, SQLException;

}
