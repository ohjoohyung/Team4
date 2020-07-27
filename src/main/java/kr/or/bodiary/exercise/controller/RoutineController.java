package kr.or.bodiary.exercise.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bodiary.exercise.service.RoutineService;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;
import kr.or.bodiary.myBodiary.service.BodiaryService;


@Controller
public class RoutineController {
	
	private RoutineService routineservice;
	
	@Autowired
	public void setRoutineservice(RoutineService routineservice) {
		this.routineservice = routineservice;
	}
	
	private BodiaryService bodiaryservice;
	  
	  @Autowired public void setBodiaryservice(BodiaryService bodiaryservice) {
	  this.bodiaryservice = bodiaryservice; }
	
	
	@RequestMapping("/insertRoutineBundle")
	public String insertRoutineBundle(RoutineJoinDto routinejoindto, HttpServletRequest request) {
		System.out.println(routinejoindto.getRoutineJoinDtoList().toString());
		String url = routineservice.insertRoutineBundle(routinejoindto, request);
		return url;
	}
	
	
	@RequestMapping(value = "/updateRoutineBundle", method = RequestMethod.GET)
	public String updateRoutineBundle(int routine_cart_seq, Model model) throws ClassNotFoundException, SQLException {
		
		model.addAttribute("routine_cart_seq", routine_cart_seq);
		return "searchExcs/searchExcsEdit";
	}
	
	
	@RequestMapping(value = "/updateRoutineBundle", method = RequestMethod.POST)
	public String updateRoutineBundle(RoutineJoinDto routinejoindto, HttpServletRequest request, String before_routine_cart_seq) {
		System.out.println("루틴 수정 : " +routinejoindto.getRoutineJoinDtoList().toString());
		String url = routineservice.updateRoutineBundle(routinejoindto, request, before_routine_cart_seq);
		return url;
	}
	
	@RequestMapping("/deleteRoutineCart")
	public String deleteRoutineCart(int routine_cart_seq) throws ClassNotFoundException, SQLException {
		System.out.println("루틴 삭제");
		return routineservice.deleteRoutineCart(routine_cart_seq);
	}
	
	
	@RequestMapping("/search")
	public String getSearchExcs() {
		return "searchExcs/searchExcs";
	}
}
