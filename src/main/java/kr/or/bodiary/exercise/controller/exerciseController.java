package kr.or.bodiary.exercise.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public List<exerciseDTO> exerciseNameSearch(String excs_name) throws ClassNotFoundException, SQLException{
		System.out.println("excs_name:"+excs_name);
		exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
		System.out.println("안들어옴");
		List<exerciseDTO> elist = exercisedao.getExerciseListByName(excs_name);
		return elist;
	}
	
	
}
