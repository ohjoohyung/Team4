package kr.or.bodiary.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

		//운동 리스트 서비스함수
		public List<exerciseDTO> exercises() {
		
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
	
		//운동 상세보기 서비스 함수
		public exerciseDTO exerciseDetail(int excs_seq) {
			exerciseDTO exercisedto = null;
			try {
					exerciseDAO noticedao = sqlsession.getMapper(exerciseDAO.class);
					exercisedto = noticedao.getExerciseBySeq(excs_seq);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
					
			return  exercisedto;
		}
		//운동 삭제
		public String exerciseDelete(int excs_seq) throws ClassNotFoundException, SQLException {
					
		exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
		exercisedao.exerciseDelete(excs_seq);	
		return  "redirect:/adminExcsList";
		}
		
		//운동 수정하기 서비스 함수 (update)
		 public String exerciseEdit(exerciseDTO exercisedto , HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
				
				exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
				exercisedao.updateExercise(exercisedto);
				return "redirect:adminExcsDetail?excs_seq="+exercisedto.getExcs_seq();
			}
		//운동 생성 서비스 함수
			public String exerciseInsert(exerciseDTO exercisedto , HttpServletRequest request) throws Exception {
				
				exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
				exercisedao.insertExercise(exercisedto);
				return "redirect:/adminExcsList";
			}
				
}
