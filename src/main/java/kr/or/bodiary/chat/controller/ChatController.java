package kr.or.bodiary.chat.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bodiary.chat.dto.NotYet;





@Controller
public class ChatController {
	

	
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping("/main")
	public String getMain() {
		return "main";
	}
	@RequestMapping("/search")
	public String getSearchExcs() {
		return "searchExcs/searchExcs";
	}


	
	
	
	
	
	
	
}
