package kr.or.bodiary.routineBrd.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;
import kr.or.bodiary.routineBrd.service.RoutineBrdService;


@Controller
public class RoutineBrdController {
	
	@Autowired
	private RoutineBrdService routinebrdservice;
	
	public void setRoutineBrdservice(RoutineBrdService routinebrdservice) {
		this.routinebrdservice = routinebrdservice;
	}
	
	//리스트
	@RequestMapping("/routineBrdList")
	public String routineBrdList(Model model) throws ClassNotFoundException, SQLException {
		List<RoutineBrdDto> rlist = routinebrdservice.routineBoardList();
		model.addAttribute("routineBoardList", rlist);
		return "routineBrd/routineBrdList";
	}
	
	//상세
	@RequestMapping("/routineBrdDetail")
	public String routineBrdDetail(int routine_brd_seq, Model model) throws ClassNotFoundException, SQLException {
		RoutineBrdDto routinebrddto = routinebrdservice.routineBrdDetail(routine_brd_seq);
		System.out.println(routinebrddto);
		model.addAttribute("routineBoardSelect", routinebrddto);
		return "routineBrd/routineBrdDetail";
	}
	
	//입력(폼)
	@RequestMapping(value = "routineBrdInsert", method = RequestMethod.GET)
	public String routineBrdInsert() {
		return "routineBrd/routineBrdForm";
	}
	
	//입력(처리)
	@RequestMapping(value = "routineBrdInsert", method = RequestMethod.POST)
	public String routineBrdInsert(RoutineBrdDto routinebrddto, HttpServletRequest request, Principal principal) throws IOException, ClassNotFoundException, SQLException {
		String url = "redirect:routineBrdList";
		
		try {
			url = routinebrdservice.routineBrdInsert(routinebrddto, request, principal);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return url;
	}
	
	//수정(폼)
	@RequestMapping(value = "routineBrdEdit", method = RequestMethod.GET)		
	public String routineBrdEdit(int routine_brd_seq, Model model) throws ClassNotFoundException, SQLException {
		RoutineBrdDto routinebrddto = routinebrdservice.routineBrdEdit(routine_brd_seq);
		model.addAttribute("routineBoardUpdate", routinebrddto);
		return "routineBrd/routineBrdEdit";
	}
	
	//수정(처리)
	@RequestMapping(value = "routineBrdEdit", method = RequestMethod.POST)
	public String routineBrdEdit(RoutineBrdDto routinebrddto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
		return routinebrdservice.routineBrdEdit(routinebrddto, request);
	}
	
	//삭제
	@RequestMapping("routineBrdDelete")
	public String routineBrdDelete(int routine_brd_seq) throws ClassNotFoundException, SQLException {
		routinebrdservice.routineBoardDelete(routine_brd_seq);
		return "redirect:routineBrdList";
	}	
	
}
