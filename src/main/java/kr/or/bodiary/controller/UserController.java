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
	
	@RequestMapping("/myBodiaryMain")
	public String myBodiaryMain() {
		return "myBodiary/myBodiaryMain";
	}

	@RequestMapping("/myBodiaryForm")
	public String myBodiaryForm() {
		return "myBodiary/myBodiaryForm";
	}

	@RequestMapping("/myBodiaryDetail")
	public String myBodiaryDetail() {
		return "myBodiary/myBodiaryDetail";
	}
	
	@RequestMapping("/myBodiaryEdit")
	public String myBodiaryEdit() {
		return "myBodiary/myBodiaryEdit";
	}
	
	@RequestMapping("/myRoutineList")
	public String myRoutineList() {
		return "myBodiary/myRoutineList";
	}
	
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

	@RequestMapping("/myPageEdit")
	public String myPageEdit() {
		return "myBodiary/myPageEdit";
	}
	
	@RequestMapping("/myProfileDetail")
	public String myProfileDetail() {
		return "myBodiary/myProfileDetail";
	}
	
	@RequestMapping("/myProfileEdit")
	public String myProfileEdit() {
		return "myBodiary/myProfileEdit";
	}
	
	@RequestMapping("/deleteAccount")
	public String deleteAccount() {
		return "myBodiary/deleteAccount";
	}
	
	   
	/*
	 * @GetMapping("/login") public String
	 */
	
}
