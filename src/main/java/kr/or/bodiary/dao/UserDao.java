package kr.or.bodiary.dao;

import java.sql.SQLException;
import kr.or.bodiary.dto.User;


public interface UserDao {
	public User getUser(String id) throws ClassNotFoundException, SQLException;

}
