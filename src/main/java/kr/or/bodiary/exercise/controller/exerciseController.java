package kr.or.bodiary.exercise.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bodiary.exercise.dao.exerciseDAO;
import kr.or.bodiary.exercise.dto.exerciseDTO;



@Controller
public class exerciseController {
	private SqlSession sqlsession;
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
		
	}
		
	@RequestMapping("/exerciseNameSearch.exer")
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
	
	
}
