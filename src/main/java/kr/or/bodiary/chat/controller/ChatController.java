package kr.or.bodiary.chat.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bodiary.chat.dto.ChatMemberDto;
import kr.or.bodiary.chat.dto.ChatRoomDto;
import kr.or.bodiary.chat.service.ChatService;
import kr.or.bodiary.exercise.service.RoutineService;
import kr.or.bodiary.user.dto.UserDto;







@Controller
public class ChatController {
	
private ChatService chatservice;
	
	@Autowired
	public void setChatservice(ChatService chatservice) {
		this.chatservice = chatservice;
	}
	
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping("/main")
	public String getMain() {
		return "main";
	}
	
	//채팅방 리스트 들어가기
	@RequestMapping("/chatList")
	public String chatList(Model model) throws ClassNotFoundException, SQLException {
		List<ChatRoomDto> chatlist = chatservice.getChatList();
		model.addAttribute("chatlist", chatlist);
		return "chat/chatList";
	}
	
	//채팅방 만들기
	@RequestMapping("/createChatRoom")
	public String createChatRoom(ChatRoomDto chatroomdto, HttpServletRequest request) throws ClassNotFoundException, SQLException {
		return chatservice.createChatRoom(chatroomdto, request);
	}
	
	//채팅방 들어가기
	@RequestMapping("/selectChatRoomByRn")
	public String selectChatRoomByRn(int room_number, Model model, HttpServletRequest request) throws ClassNotFoundException, SQLException {
		return chatservice.selectChatRoomByRn(room_number, request, model);
	}
	
	//채팅방 멤버리스트 닉네임 불러오기
	@ResponseBody
	@RequestMapping("/getMemberList")
	public List<ChatMemberDto> getMemberList(int room_number) throws ClassNotFoundException, SQLException {
		return chatservice.getMemberList(room_number);
	}
	
	


	
	
	
	
	
	
	
}
