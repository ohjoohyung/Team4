package kr.or.bodiary.routineBrd.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bodiary.chat.dto.NotYet;





@Controller
public class RoutineBrdController {
	
	@RequestMapping("/routineBrdEdit")
	   public String getRoutineBrdEdit() {
	      return "routineBrd/routineBrdEdit";
	   }
	   @RequestMapping("/routineBrdDetail")
	   public String getRoutineBrdDetail() {
	      return "routineBrd/routineBrdDetail";
	   }
	   @RequestMapping("/routineBrdForm")
	   public String getRoutineBrdForm() {
	      return "routineBrd/routineBrdForm";
	   }
	   @RequestMapping("/routineBrdList")
	   public String getRoutineBrdList() {
	      return "routineBrd/routineBrdList";
	   }
	
	
	
	
	
	
	
}
