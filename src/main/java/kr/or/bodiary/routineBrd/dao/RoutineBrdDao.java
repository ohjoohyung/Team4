package kr.or.bodiary.routineBrd.dao;

import java.sql.SQLException;
import java.util.List;
import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;


public interface RoutineBrdDao {
	
	//리스트 가져오기
	public List<RoutineBrdDto> routineBoardList() throws ClassNotFoundException, SQLException;
	
	//상세
	public RoutineBrdDto routineBoardSelect(int routine_brd_seq) throws ClassNotFoundException, SQLException;

	//입력
	public int routineBoardInsert(RoutineBrdDto routineBrdDto) throws ClassNotFoundException, SQLException;
	
	//수정
	public void routineBoardUpdate(RoutineBrdDto routineBrdDto) throws ClassNotFoundException, SQLException;
	
	//삭제
	public int routineBoardDelete(int routine_brd_seq) throws ClassNotFoundException, SQLException;	
	
}
