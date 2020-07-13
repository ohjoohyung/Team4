package kr.or.bodiary.user.dao;

import java.sql.SQLException;

import kr.or.bodiary.user.dto.UserDto;


public interface UserDao {
	
	//----------- 회원정보 얻기 ------------
	public UserDto getUser(String user_email) throws ClassNotFoundException, SQLException;

	//----------- 회원 가입 ------------
	public int insertUser(UserDto user) throws ClassNotFoundException, SQLException;

	//----------- 유저 정보 수정 -----------
	public int updateUser(UserDto user) throws ClassNotFoundException, SQLException;
	
	//----------- 유저 롤 권한 승격 -----------
	public int updateRole(UserDto user) throws ClassNotFoundException, SQLException;
	
	//----------- 유저 휴면계정 전환 -----------
	public int updateDormantAccount(UserDto user) throws ClassNotFoundException, SQLException;
	
	//----------- 유저 탈퇴 요청 -----------
	public int updateWithdrawalUser(UserDto user) throws ClassNotFoundException, SQLException;
	
	//----------- 유저 삭제 -----------
	public int deleteUser(UserDto user) throws ClassNotFoundException, SQLException;
	
	//----------- 이메일 체크 -----------
	public int emailCheck(String user_email) throws ClassNotFoundException, SQLException;
	

}
