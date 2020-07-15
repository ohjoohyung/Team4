package kr.or.bodiary.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.exercise.dao.ExerciseDao;
import kr.or.bodiary.exercise.dto.ExerciseDto;

@Service
public class ExerciseService {
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

		//운동 리스트 서비스함수
		public List<ExerciseDto> exercises() {
		
					List<ExerciseDto> list=null;
					try {
						//mapper 를 통한 인터페이스 연결
					ExerciseDao ExerciseDao = sqlsession.getMapper(ExerciseDao.class);
						//
						list = ExerciseDao.getExercise();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			return list;
		}
	
		//운동 상세보기 서비스 함수
		public ExerciseDto exerciseDetail(int excs_seq) {
			ExerciseDto ExerciseDto = null;
			try {
					ExerciseDao noticedao = sqlsession.getMapper(ExerciseDao.class);
					ExerciseDto = noticedao.getExerciseBySeq(excs_seq);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
					
			return  ExerciseDto;
		}
		//운동 삭제
		public String exerciseDelete(int excs_seq) throws ClassNotFoundException, SQLException {
					
		ExerciseDao ExerciseDao = sqlsession.getMapper(ExerciseDao.class);
		ExerciseDao.exerciseDelete(excs_seq);	
		return  "redirect:/adminExcsList";
		}
		
		//운동 수정하기 서비스 함수 (update)
		 public String exerciseEdit(ExerciseDto ExerciseDto , HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
				
				ExerciseDao ExerciseDao = sqlsession.getMapper(ExerciseDao.class);
				ExerciseDao.updateExercise(ExerciseDto);
				return "redirect:adminExcsDetail?excs_seq="+ExerciseDto.getExcs_seq();
			}
		//운동 생성 서비스 함수
			public String exerciseInsert(ExerciseDto ExerciseDto , HttpServletRequest request) throws Exception {
				
				ExerciseDao ExerciseDao = sqlsession.getMapper(ExerciseDao.class);
				ExerciseDao.insertExercise(ExerciseDto);
				return "redirect:/adminExcsList";
			}
				
}
