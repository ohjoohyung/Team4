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
	
	
	
	
	  //채팅방 리스트 들어가기
	  
	  @RequestMapping("/chatList") 
	  public String chatList(Model model) throws ClassNotFoundException, SQLException { 
		  return "chat/chatList"; 
	  }
	 
	
	@ResponseBody
	@RequestMapping("/getChatList")
	public List<ChatRoomDto> getChatList() throws ClassNotFoundException, SQLException {
		List<ChatRoomDto> chatlist = chatservice.getChatList();
		return chatlist;
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
	
	//채팅방 비밀번호 불러오기
	@ResponseBody
	@RequestMapping("/getRoomPwd")
	public int getRoomPwd(int room_number) throws ClassNotFoundException, SQLException {
		return chatservice.getRoomPwd(room_number);
	}
	
	//채팅방 멤버리스트 닉네임 불러오기
	@ResponseBody
	@RequestMapping("/getMemberList")
	public List<ChatMemberDto> getMemberList(int room_number) throws ClassNotFoundException, SQLException {
		return chatservice.getMemberList(room_number);
	}
	
	
	//채팅방 나가기
	@RequestMapping("/exitChatRoom")
	public String exitChatRoom(String user_email, int room_number) throws ClassNotFoundException, SQLException {
		return chatservice.exitChatRoom(user_email, room_number);
	}
	
	//채팅방 삭제하기
	@RequestMapping("/deleteChatRoom")
	public String deleteChatRoom(int room_number, String user_email) throws ClassNotFoundException, SQLException {
		return chatservice.deleteChatRoom(room_number, user_email);
	}

	
	
	
	
	
	
	
}
