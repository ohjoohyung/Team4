package kr.or.bodiary.food.dao;

import java.sql.SQLException;

import kr.or.bodiary.chat.dto.User;


public interface FoodDao {
	public User getUser(String id) throws ClassNotFoundException, SQLException;

}
