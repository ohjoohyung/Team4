package kr.or.bodiary.routineBrd.dao;

import java.sql.SQLException;

import java.util.List;
import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;


public interface RoutineBrdDao {
	
	//리스트 가져오기
	public List<RoutineBrdDto> routineBoardList() throws ClassNotFoundException, SQLException;
	
	//상세
	public RoutineBrdDto selectRoutineBoard(int routineBrdSeq) throws ClassNotFoundException, SQLException;

	//입력
	public int insertRoutineBrd(RoutineBrdDto routineBrdDto) throws ClassNotFoundException, SQLException;
	
	//수정
	public void updateRoutineBrd(RoutineBrdDto routineBrdDto) throws ClassNotFoundException, SQLException;
	
	//삭제
	public int deleteRoutineBrd(int routineBrdSeq) throws ClassNotFoundException, SQLException;	
	
	
}
