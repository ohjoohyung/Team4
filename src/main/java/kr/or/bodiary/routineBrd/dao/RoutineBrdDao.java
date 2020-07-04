package kr.or.bodiary.routineBrd.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.NotYet;


public interface RoutineBrdDao {
	public NotYet getUser(String id) throws ClassNotFoundException, SQLException;

}
