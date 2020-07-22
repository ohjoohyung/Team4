package kr.or.bodiary.chat.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bodiary.chat.dto.NotYet;





@Controller
public class ChatController {
	

	
	

	@RequestMapping("/search")
	public String getSearchExcs() {
		return "searchExcs/searchExcs";
	}
	@RequestMapping("/chatList")
	public String chatList() {
		return "chat/chatList";
	}
	@RequestMapping("/chatRoom")
	public String chatRoom() {
		return "chat/chatRoom";
	}


	
	
	
	
	
	
	
}
