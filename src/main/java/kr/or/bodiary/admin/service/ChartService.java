package kr.or.bodiary.admin.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.user.dao.UserDao;
import kr.or.bodiary.user.dto.UserDto;

@Service
public class ChartService {

	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	//실시간 가입자수
	public int todayUserCount(UserDto user, HttpServletRequest request) {
		int resultNum = 0;
		
		return resultNum;
	}
	// 토탈가입자
	public int selectUserCount() {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		int returnNum = 0;
		try {
			returnNum = userdao.selectUserCount();
		} catch (Exception e) {
			e.getMessage();
		}
		return returnNum;
	}
	// 젠더 성비 궁금
	public String genderPer() {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		int fGender = 0;
		int mGender = 0;
		int total = 0;
		try {
			fGender = userdao.selectUserByGender("여");
			mGender = userdao.selectUserByGender("남");
			total = fGender+mGender;
		} catch (Exception e) {
			e.getMessage();
		}
		
		int fGenderPer = (int)(((float)fGender/total)*100);
		int mGenderPer = (int)(((float)mGender/total)*100);
		System.out.println(mGenderPer);
		
		String resultGender =  mGenderPer+" : "+fGenderPer;
		return resultGender;
	}
}
