package kr.or.bodiary.user.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.user.dto.UserDto;

public interface UserDao {

	// ----------- 회원정보 얻기 ------------
	   public UserDto getUser(String user_email) throws ClassNotFoundException, SQLException;

	   public int selectUserByGender(String user_gender) throws ClassNotFoundException, SQLException;

	   public int selectUserCount() throws ClassNotFoundException, SQLException;
	   
	   public int deleteUserCount() throws ClassNotFoundException, SQLException;
	   
	   public int countBodiary() throws ClassNotFoundException, SQLException;
	   
	   public int todayUser() throws ClassNotFoundException, SQLException;
	   
	   public int updatingChart() throws ClassNotFoundException, SQLException;
	   
	   public int monthlyCount0() throws ClassNotFoundException, SQLException;
	   public int monthlyCount1() throws ClassNotFoundException, SQLException;
	   public int monthlyCount2() throws ClassNotFoundException, SQLException;
	   public int monthlyCount3() throws ClassNotFoundException, SQLException;
	   public int monthlyCount4() throws ClassNotFoundException, SQLException;
	   public int monthlyCount5() throws ClassNotFoundException, SQLException;

	// ----------- 회원 가입 ------------
	public int insertUser(UserDto user) throws ClassNotFoundException, SQLException;

	// ----------- 유저 비번 수정전 비번 체크 -----------
	public String selectPwd(String user_email) throws ClassNotFoundException, SQLException;

	// ----------- 유저 비번 수정 -----------
	public int updatePwd(UserDto user) throws ClassNotFoundException, SQLException;

	// ----------- 유저 닉네임 수정 -----------
	public int updateNick(UserDto user) throws ClassNotFoundException, SQLException;

	// ----------- 유저 정보 수정 -----------
	public int updateUser(UserDto user) throws ClassNotFoundException, SQLException;

	// ----------- 유저 롤 권한 승격 -----------
	public int updateRole(UserDto user) throws ClassNotFoundException, SQLException;

	// ----------- 유저 휴면계정 전환 -----------
	public int updateDormantAccount(UserDto user) throws ClassNotFoundException, SQLException;

	// ----------- 유저 탈퇴 요청 -----------
	public int updateWithdrawalUser(UserDto user) throws ClassNotFoundException, SQLException;

	// ----------- 유저 삭제 -----------
	public int deleteUser(UserDto user) throws ClassNotFoundException, SQLException;

	// ----------- 이메일 체크 -----------
	public int emailCheck(String user_email) throws ClassNotFoundException, SQLException;

	// ----------- 전체 회원정보 리스트 가져오기 ------------(동률)
	public List<UserDto> getUserList() throws ClassNotFoundException, SQLException;

	// ----------- 해당 회원권한 수정하기 ------------(동률)
	public int userRoleUpdate(UserDto user) throws ClassNotFoundException, SQLException;

	// ------------ 해당 유저의 총 자유게시물 가져오기------------------ (동률)
	public int freeBrdCount(String user_email) throws ClassNotFoundException, SQLException;

	// ------------ 해당 유저의 총 루틴 자랑 게시물 가져오기------------------ (동률)
	public int routineBrdCount(String user_email) throws ClassNotFoundException, SQLException;
	
	// ------------ 자신의 성별, 키에 해당하는 회원 평균 몸무게 조회 ------------------ (주형)
	public int getAvgWeight(int user_height, String user_gender) throws ClassNotFoundException, SQLException;
}
