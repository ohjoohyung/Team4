package kr.or.bodiary.exercise.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.or.bodiary.exercise.dto.ExerciseDto;
import kr.or.bodiary.exercise.dto.RoutineBundleDto;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;

public interface ExerciseDao {
	public List<ExerciseDto> getExerciseListByName(String excs_name) throws ClassNotFoundException, SQLException;

	public List<ExerciseDto> exerciseSearch(ExerciseDto ExerciseDto) throws ClassNotFoundException, SQLException;
	
	public List<ExerciseDto> getExercisecListByName() throws ClassNotFoundException, SQLException;

	public List<ExerciseDto> getExercisenListByName() throws ClassNotFoundException, SQLException;

	public List<ExerciseDto> exercisepartSearch(String excs_body_part) throws ClassNotFoundException, SQLException;

	public List<ExerciseDto> exerciseEquiSearch(String excs_body_part) throws ClassNotFoundException, SQLException;

	public List<ExerciseDto> exercisepartequiSearch(Map<String, String> exerciseparteuqi);

	public List<ExerciseDto> exerciseadd(int excs_seq) throws ClassNotFoundException, SQLException;
	
	public List<ExerciseDto> getExercise() throws ClassNotFoundException, SQLException;
	
	// 운동상세보기
	public ExerciseDto getExerciseBySeq(int excs_seq) throws ClassNotFoundException, SQLException;

	// 운동삭제
	public int exerciseDelete(int excs_seq) throws ClassNotFoundException, SQLException;

	// 운동 수정
	public int updateExercise(ExerciseDto ExerciseDto) throws ClassNotFoundException, SQLException;

	// 운동 추가
	public int insertExercise(ExerciseDto ExerciseDto) throws ClassNotFoundException, SQLException;

	// 루틴 장바구니 시퀀스 추가
	public int insertRoutineCart(RoutineJoinDto routinejoindto) throws ClassNotFoundException, SQLException;

	// 루틴 만들기 (운동 몇시간, 운동 몇세트 몇회)
	public int insertRoutine(List<RoutineJoinDto> list) throws ClassNotFoundException, SQLException;

	// 루틴을 하나로 묶어주기
	public int insertRoutineBundle(List<RoutineBundleDto> list) throws ClassNotFoundException, SQLException;
	

	   // 루틴 삭제하기
	   public int deleteRoutineCart(int routine_cart_seq) throws ClassNotFoundException, SQLException; 
	   
	   // 루틴 상태 수정하기
	   public int updateRoutineCart(int routine_cart_seq) throws ClassNotFoundException, SQLException;
	
}
