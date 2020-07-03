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
	
	
	@RequestMapping("/myQnaList")
	public String getMain() {
		return "myQnaList";
	}
	
	@RequestMapping("/")
	public String index() {
		return "main";
	}
	
	@RequestMapping("/myQnaDetail")
	public String index2() {
		return "myQnaDetail";
	}
	
	@RequestMapping("/myQnaEdit")
	public String index3() {
		return "myQnaEdit";
	}
	
	@RequestMapping("/myQnaForm")
	public String qna() {
		return "myQnaForm";
	}
	
	@RequestMapping("/noticeList")
	public String a() {
		return "noticeList";
	}
	
	@RequestMapping("/noticeDetail")
	public String b() {
		return "noticeDetail";
	}
	
	@RequestMapping("/noticeEdit")
	public String b2() {
		return "noticeEdit";
	}
	
	@RequestMapping("/noticeForm")
	public String b3() {
		return "noticeForm";
	}
	/*
	 * @GetMapping("/login") public String
	 */
	
	
	
	
	
	
	
}
