package kr.or.bodiary.exercise.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.or.bodiary.exercise.dto.RoutineBundleDto;
import kr.or.bodiary.exercise.dto.RoutineCartDto;
import kr.or.bodiary.exercise.dto.RoutineDto;
import kr.or.bodiary.exercise.dto.exerciseDTO;
import kr.or.bodiary.myBodiary.dto.dailyMealDTO;


public interface exerciseDAO {
	public List<exerciseDTO> getExerciseListByName(String excs_name) throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> getExercisecListByName() throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> getExercisenListByName() throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> exercisepartSearch(String excs_body_part) throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> exerciseEquiSearch(String excs_body_part) throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> exercisepartequiSearch(Map<String, String> exerciseparteuqi);
	public List<exerciseDTO> exerciseadd(int excs_seq) throws ClassNotFoundException, SQLException;
	
	
	//루틴 장바구니 시퀀스 추가
	public int insertRoutineCart(RoutineCartDto routinecartdto) throws ClassNotFoundException, SQLException;
	
	//루틴 만들기 (운동 몇시간, 운동 몇세트 몇회)
	public int insertRoutine(RoutineDto routinedto) throws ClassNotFoundException, SQLException;
	
	//루틴을 하나로 묶어주기
	public int insertRoutineBundle(List<RoutineBundleDto> list) throws ClassNotFoundException, SQLException;
}
