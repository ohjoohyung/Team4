package kr.or.bodiary.exercise.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bodiary.exercise.dao.ExerciseDao;
import kr.or.bodiary.exercise.dto.RoutineBundleDto;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;
import kr.or.bodiary.user.dto.UserDto;

@Service
public class RoutineService {

	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	
	@Transactional
	public String insertRoutineBundle(RoutineJoinDto routinejoindto, HttpServletRequest request) {
		String url = "";
		
		try {
			ExerciseDao exercisedao = sqlsession.getMapper(ExerciseDao.class);
			System.out.println(routinejoindto.getRoutine_cart_title());
			exercisedao.insertRoutineCart(routinejoindto);
			
			
			List<RoutineJoinDto> routineList = routinejoindto.getRoutineJoinDtoList();
			
			System.out.println("서비스에서 카트 번호 : " + routinejoindto.getRoutine_cart_seq());
			System.out.println("서비스에서 루틴 리스트 : " + routineList.toString());
			
			exercisedao.insertRoutine(routineList);
			
			List<RoutineBundleDto> routinebundle = new ArrayList<RoutineBundleDto>();
			UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
			for(RoutineJoinDto r : routineList) {
				RoutineBundleDto b = new RoutineBundleDto();
				b.setRoutine_cart_seq(routinejoindto.getRoutine_cart_seq());
				b.setRoutine_seq(r.getRoutine_seq());
				b.setUser_email(user.getUser_email());
				routinebundle.add(b);
			}
			
			int result = exercisedao.insertRoutineBundle(routinebundle);
			
			if(result > 0) {
				String referer = request.getHeader("Referer");
				url = "redirect:"+referer;
			}else {
				url = "redirect:search";
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		return url;
	}
}
