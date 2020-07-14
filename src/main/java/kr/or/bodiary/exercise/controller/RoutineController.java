package kr.or.bodiary.exercise.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bodiary.exercise.service.RoutineService;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;


@Controller
public class RoutineController {
	
	private RoutineService routineservice;
	
	@Autowired
	public void setRoutineservice(RoutineService routineservice) {
		this.routineservice = routineservice;
	}
	
	
	@RequestMapping("/insertRoutineBundle")
	public String insertRoutineBundle(RoutineJoinDto routinejoindto, HttpServletRequest request) {
		System.out.println(routinejoindto.getRoutineJoinDtoList().toString());
		String url = routineservice.insertRoutineBundle(routinejoindto, request);
		return url;
	}
}
