package kr.or.bodiary.exercise.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.exercise.dto.exerciseDTO;


public interface exerciseDAO {
	public List<exerciseDTO> getExerciseListByName(String excs_name) throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> getExercisecListByName() throws ClassNotFoundException, SQLException;
	public List<exerciseDTO> getExercisenListByName() throws ClassNotFoundException, SQLException;
}
