package kr.or.bodiary.routineBrd.service;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.chat.dto.NotYet;
import kr.or.bodiary.user.dao.UserDao;


@Service
public class RoutineBrdService {
	
private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	public NotYet getUser(String id) throws ClassNotFoundException, SQLException {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		System.out.println(userdao.getUser(id));
		return userdao.getUser(id);
	}
}
