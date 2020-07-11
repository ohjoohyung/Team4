package kr.or.bodiary.routineBrd.controller;

import java.security.Principal;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bodiary.chat.dto.NotYet;
import kr.or.bodiary.routineBrd.dao.RoutineBrdDao;
import kr.or.bodiary.routineBrd.dto.routineBrdDTO;
import kr.or.bodiary.routineBrd.service.RoutineBrdService;





@Controller
public class RoutineBrdController {
	@Autowired
	private RoutineBrdService routineBrdService;
	
	@RequestMapping("/routineBrdEdit")
	   public String getRoutineBrdEdit() {
	      return "routineBrd/routineBrdEdit";
	   }
	   @RequestMapping("/routineBrdDetail")
	   public String getRoutineBrdDetail() {
	      return "routineBrd/routineBrdDetail";
	   }
	   @RequestMapping(value="/routineBrdForm", method=RequestMethod.GET)
	   public String getRoutineBrdForm() {
	      return "routineBrd/routineBrdForm";
	   }
	   @RequestMapping(value="/routineBrdForm", method=RequestMethod.POST)
	   public String getRoutineBrdForm(Model model, routineBrdDTO routineBrdDTO, Principal principal){
	      
		   return "routineBrd/routineBrdForm";
	   }
	   @RequestMapping("/routineBrdList")
	   public String getRoutineBrdList() {
	      return "routineBrd/routineBrdList";
	   }
	
	
	
	
	
	
	
}
