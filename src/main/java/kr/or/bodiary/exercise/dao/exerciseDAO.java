package kr.or.bodiary.exercise.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.or.bodiary.exercise.dto.exerciseDTO;


public interface exerciseDAO {
	public List<exerciseDTO> getExerciseListByName(String excs_name) throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> getExercisecListByName() throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> getExercisenListByName() throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> exercisepartSearch(String excs_body_part) throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> exerciseEquiSearch(String excs_body_part) throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> exercisepartequiSearch(Map<String, String> exerciseparteuqi);
	public List<exerciseDTO> exerciseadd(int excs_seq) throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> getExercise() throws ClassNotFoundException, SQLException;
}
