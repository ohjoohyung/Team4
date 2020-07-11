package kr.or.bodiary.freeBrd.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.NotYet;


public interface FreeBrdDao {
	public NotYet getUser(String id) throws ClassNotFoundException, SQLException;

}
