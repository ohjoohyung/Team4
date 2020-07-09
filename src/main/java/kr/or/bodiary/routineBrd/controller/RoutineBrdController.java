package kr.or.bodiary.routineBrd.controller;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
<<<<<<< HEAD

import kr.or.bodiary.chat.dto.NotYet;
import kr.or.bodiary.routineBrd.dao.RoutineBrdDao;
import kr.or.bodiary.routineBrd.dto.routineBrdDTO;
import kr.or.bodiary.routineBrd.service.RoutineBrdService;


=======
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;
import kr.or.bodiary.routineBrd.service.RoutineBrdService;


@Controller
public class RoutineBrdController {
	@Autowired
	private RoutineBrdService routineBrdService;
	
	private RoutineBrdService routinebrdservice;
	@Autowired
	public void setRoutineBrdservice(RoutineBrdService routinebrdservice) {
		this.routinebrdservice = routinebrdservice;
	}
	
	//리스트
	@RequestMapping("routineBoardList.rtn")
	public String getRoutineBoardList(Model model) {
		model.addAttribute("routineBoardList", routinebrdservice.routineBoardList());
		return "routineBrd/routineBrdList";
	}
	
	//상세
	@RequestMapping("/routineBrdDetail")
	public String getRoutineBrdDetail() {
		return "routineBrd/routineBrdDetail";
	}
	
	//입력
	@RequestMapping("/routineBrdForm")
	public String getRoutineBrdForm() {
		return "routineBrd/routineBrdForm";
	}
	
	//수정
	@RequestMapping("/routineBrdEdit")
	public String getRoutineBrdEdit() {
		return "routineBrd/routineBrdEdit";
	}

	
	//삭제

}
