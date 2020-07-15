package kr.or.bodiary.exercise.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bodiary.exercise.dao.ExerciseDao;
import kr.or.bodiary.exercise.dto.ExerciseDto;



@RestController
public class ExerciseController {
	private SqlSession sqlsession;
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
		
	}
		
	@RequestMapping("/exerciseNameSearch.exer")
	@ResponseBody
	public List<ExerciseDto> exerciseNameSearch(String excs_name) throws ClassNotFoundException, SQLException{
		System.out.println("excs_name:"+excs_name);
		ExerciseDao exercisedao = sqlsession.getMapper(ExerciseDao.class);
		List<ExerciseDto> elist = exercisedao.getExerciseListByName(excs_name);
		System.out.println(elist.toString());
		return elist;
	}
	
	@RequestMapping("/exercisecNameSearch.exer")
	@ResponseBody
	public List<ExerciseDto> exercisecNameSearch() throws ClassNotFoundException, SQLException{
		ExerciseDao exercisedao = sqlsession.getMapper(ExerciseDao.class);
		List<ExerciseDto> elist = exercisedao.getExercisecListByName();
		System.out.println(elist.toString());
		return elist;
	}
	
	@RequestMapping("/exercisenNameSearch.exer")
	@ResponseBody
	public List<ExerciseDto> exercisenNameSearch() throws ClassNotFoundException, SQLException{
		ExerciseDao exercisedao = sqlsession.getMapper(ExerciseDao.class);
		List<ExerciseDto> elist = exercisedao.getExercisenListByName();
		System.out.println(elist.toString());
		return elist;
	}
	@RequestMapping("/exercisepartSearch.exer")
	@ResponseBody
	public List<ExerciseDto> exercisepartSearch(String excs_body_part) throws ClassNotFoundException, SQLException{
		System.out.println(excs_body_part);
		ExerciseDao exercisedao = sqlsession.getMapper(ExerciseDao.class);
		List<ExerciseDto> elist = exercisedao.exercisepartSearch(excs_body_part);
		System.out.println(elist.toString());
		return elist;
	}
	@RequestMapping("/exerciseEquiSearch.exer")
	@ResponseBody
	public List<ExerciseDto> exerciseEquiSearch(String excs_equipment) throws ClassNotFoundException, SQLException{
		System.out.println(excs_equipment);
		ExerciseDao exercisedao = sqlsession.getMapper(ExerciseDao.class);
		List<ExerciseDto> elist = exercisedao.exerciseEquiSearch(excs_equipment);
		System.out.println(elist.toString());
		return elist;
	}
	@RequestMapping("/exercisepartequiSearch.exer")
	@ResponseBody
	public List<ExerciseDto> exercisepartequiSearch(String excs_equipment, String excs_body_part) throws ClassNotFoundException, SQLException{
		
		  System.out.println("excs_equipment"+excs_equipment);
		  System.out.println("excs_body_part"+excs_body_part); ExerciseDao exercisedao
		  = sqlsession.getMapper(ExerciseDao.class); Map<String, String>
		  exerciseparteuqi = new HashMap<String,String>();
		  exerciseparteuqi.put("excs_equipment", excs_equipment);
		  exerciseparteuqi.put("excs_body_part", excs_body_part); List<ExerciseDto>
		  elist = exercisedao.exercisepartequiSearch(exerciseparteuqi);
		  System.out.println(elist.toString()); 
		  return elist;
	
	}
	@RequestMapping("/exerciseadd.exer")
	@ResponseBody
	public List<ExerciseDto> exerciseadd(int excs_seq) throws ClassNotFoundException, SQLException{
		System.out.println(excs_seq);
		ExerciseDao exercisedao = sqlsession.getMapper(ExerciseDao.class);
		List<ExerciseDto> elist = exercisedao.exerciseadd(excs_seq);
		System.out.println(elist.toString());
		return elist;
	}
	
	
}
