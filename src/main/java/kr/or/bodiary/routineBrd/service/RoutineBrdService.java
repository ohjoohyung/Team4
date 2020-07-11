package kr.or.bodiary.routineBrd.service;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.routineBrd.dao.RoutineBrdDao;
import kr.or.bodiary.routineBrd.dto.routineBrdDTO;


@Service
public class RoutineBrdService {
	
private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	public int insertRoutineBrd(routineBrdDTO routineBrdDTO){
		RoutineBrdDao RoutineBrdDao = sqlsession.getMapper(RoutineBrdDao.class);
		int result = 0;
		try {
			result = RoutineBrdDao.insertRoutineBrd(routineBrdDTO);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
}
