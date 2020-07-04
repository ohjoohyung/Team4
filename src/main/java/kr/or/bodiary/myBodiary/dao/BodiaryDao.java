package kr.or.bodiary.myBodiary.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.NotYet;


public interface BodiaryDao {
	public NotYet getUser(String id) throws ClassNotFoundException, SQLException;

}
