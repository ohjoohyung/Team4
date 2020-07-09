package kr.or.bodiary.user.dao;

import java.sql.SQLException;

import kr.or.bodiary.user.dto.UserDto;


public interface UserDao {
	
	//----------- 회원정보 얻기 ------------
	public UserDto getUser(String user_email) throws ClassNotFoundException, SQLException;

	//----------- 회원 가입 ------------
	public int insertUser(UserDto user) throws ClassNotFoundException, SQLException;

	//----------- 이메일 체크 -----------
	public int emailCheck(String user_email) throws ClassNotFoundException, SQLException;
}
