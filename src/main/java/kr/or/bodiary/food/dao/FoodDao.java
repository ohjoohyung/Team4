package kr.or.bodiary.food.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.NotYet;


public interface FoodDao {
	public NotYet getUser(String id) throws ClassNotFoundException, SQLException;

}
