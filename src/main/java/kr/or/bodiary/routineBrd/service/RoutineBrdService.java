package kr.or.bodiary.routineBrd.service;



import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mysql.cj.protocol.x.Notice;

import kr.or.bodiary.routineBrd.dao.RoutineBrdDao;
import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;

<<<<<<< HEAD
=======
import kr.or.bodiary.user.dao.UserDao;


@Service
public class RoutineBrdService {
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

		
	//리스트 서비스 함수
	public List<RoutineBrdDto> routineBoardList() {
			
		//DAO 데이터 받아오기
		List<RoutineBrdDto> rlist = null;
		try {
			//mapper를 통한 인터페이스 연결
			RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
			rlist = routinebrddao.routineBoardList();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rlist;
	}

}
