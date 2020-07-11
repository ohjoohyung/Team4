package kr.or.bodiary.routineBrd.dao;

import java.sql.SQLException;

import kr.or.bodiary.routineBrd.dto.routineBrdDTO;


public interface RoutineBrdDao {
	public int insertRoutineBrd(routineBrdDTO routineBrdDto) throws ClassNotFoundException, SQLException;

}
