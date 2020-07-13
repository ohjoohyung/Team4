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

import kr.or.bodiary.exercise.dao.exerciseDAO;
import kr.or.bodiary.exercise.dto.exerciseDTO;



@RestController
public class exerciseController {
	private SqlSession sqlsession;
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
		
	}
		
	@RequestMapping("/exerciseNameSearch.exer")
	@ResponseBody
	public List<exerciseDTO> exerciseNameSearch(String excs_name) throws ClassNotFoundException, SQLException{
		System.out.println("excs_name:"+excs_name);
		exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
		List<exerciseDTO> elist = exercisedao.getExerciseListByName(excs_name);
		System.out.println(elist.toString());
		return elist;
	}
	
	@RequestMapping("/exercisecNameSearch.exer")
	@ResponseBody
	public List<exerciseDTO> exercisecNameSearch() throws ClassNotFoundException, SQLException{
		exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
		List<exerciseDTO> elist = exercisedao.getExercisecListByName();
		System.out.println(elist.toString());
		return elist;
	}
	
	@RequestMapping("/exercisenNameSearch.exer")
	@ResponseBody
	public List<exerciseDTO> exercisenNameSearch() throws ClassNotFoundException, SQLException{
		exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
		List<exerciseDTO> elist = exercisedao.getExercisenListByName();
		System.out.println(elist.toString());
		return elist;
	}
	@RequestMapping("/exercisepartSearch.exer")
	@ResponseBody
	public List<exerciseDTO> exercisepartSearch(String excs_body_part) throws ClassNotFoundException, SQLException{
		System.out.println(excs_body_part);
		exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
		List<exerciseDTO> elist = exercisedao.exercisepartSearch(excs_body_part);
		System.out.println(elist.toString());
		return elist;
	}
	@RequestMapping("/exerciseEquiSearch.exer")
	@ResponseBody
	public List<exerciseDTO> exerciseEquiSearch(String excs_equipment) throws ClassNotFoundException, SQLException{
		System.out.println(excs_equipment);
		exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
		List<exerciseDTO> elist = exercisedao.exerciseEquiSearch(excs_equipment);
		System.out.println(elist.toString());
		return elist;
	}
	@RequestMapping("/exercisepartequiSearch.exer")
	@ResponseBody
	public List<exerciseDTO> exercisepartequiSearch(String excs_equipment, String excs_body_part) throws ClassNotFoundException, SQLException{
		
		  System.out.println("excs_equipment"+excs_equipment);
		  System.out.println("excs_body_part"+excs_body_part); exerciseDAO exercisedao
		  = sqlsession.getMapper(exerciseDAO.class); Map<String, String>
		  exerciseparteuqi = new HashMap<String,String>();
		  exerciseparteuqi.put("excs_equipment", excs_equipment);
		  exerciseparteuqi.put("excs_body_part", excs_body_part); List<exerciseDTO>
		  elist = exercisedao.exercisepartequiSearch(exerciseparteuqi);
		  System.out.println(elist.toString()); 
		  return elist;
	
	}
	@RequestMapping("/exerciseadd.exer")
	@ResponseBody
	public List<exerciseDTO> exerciseadd(int excs_seq) throws ClassNotFoundException, SQLException{
		System.out.println(excs_seq);
		exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
		List<exerciseDTO> elist = exercisedao.exerciseadd(excs_seq);
		System.out.println(elist.toString());
		return elist;
	}
	
	
}
