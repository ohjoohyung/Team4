package kr.or.bodiary.admin.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.exercise.dao.exerciseDAO;
import kr.or.bodiary.exercise.dto.exerciseDTO;

@Service
public class ExerciseService {
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

public List<exerciseDTO> exercises() {
	/* public List<exerciseDTO> exercises(String pg , String f , String q) { */
		//default
		int page =1;
		String field = "excs_name";
		String query = "%%";
				
					
		//조건처리
		/*
		 * if(pg != null && !pg.equals("")) { page = Integer.parseInt(pg); }
		 * 
		 * if(f != null && !f.equals("")) { field = f; }
		 * 
		 * if(q != null && !q.equals("")) { query = q; }
		 */
				
		//DAO 데이터 받아오기
				List<exerciseDTO> list=null;
				try {
					//mapper 를 통한 인터페이스 연결
				exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
					//
					list = exercisedao.getExercise();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return list;
	}
}
