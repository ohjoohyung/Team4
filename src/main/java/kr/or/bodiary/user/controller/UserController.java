package kr.or.bodiary.user.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;





@Controller
public class UserController {
	
	   
	   @RequestMapping("/deleteAccount")
	   public String deleteAccount() {
		   return "user/deleteAccount";
	   }
	
	
	
}
