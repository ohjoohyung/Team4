package kr.or.bodiary.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bodiary.dto.User;
import kr.or.bodiary.service.UserService;




@Controller
public class UserController {
	
	private UserService userservice;
	@Autowired
	public void setUserservice(UserService userservice) {
		this.userservice = userservice;
	}
	
	@RequestMapping("/getUser")
	public String getUser(Model model, String id) throws ClassNotFoundException, SQLException {
		User user = userservice.getUser(id);
		System.out.println(user);
		model.addAttribute("user", user);
		return "test";
	}
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping("/main")
	public String getMain() {
		return "main";
	}
	
	@RequestMapping("/routineBrdEdit")
	public String getRoutineBrdEdit() {
		return "routineBrdEdit";
	}
	@RequestMapping("/routineBrdDetail")
	public String getRoutineBrdDetail() {
		return "routineBrdDetail";
	}
	@RequestMapping("/routineBrdForm")
	public String getRoutineBrdForm() {
		return "routineBrdForm";
	}
	@RequestMapping("/routineBrdList")
	public String getRoutineBrdList() {
		return "routineBrdList";
	}
	
	/*
	 * @GetMapping("/login") public String
	 */
	
	
	
	
	
	
	
}
