package kr.or.bodiary.chat.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bodiary.chat.dao.ChatDao;
import kr.or.bodiary.chat.dto.ChatMemberDto;
import kr.or.bodiary.chat.dto.ChatRoomDto;
import kr.or.bodiary.user.dao.UserDao;
import kr.or.bodiary.user.dto.UserDto;


@Service
public class ChatService {
	
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	
	//채팅방 리스트
	public List<ChatRoomDto> getChatList() throws ClassNotFoundException, SQLException {
		ChatDao chatdao = sqlsession.getMapper(ChatDao.class);
		return chatdao.getChatList();
	}
	
	
	//채팅방 만들기
	public String createChatRoom(ChatRoomDto chatroomdto, HttpServletRequest requset) throws ClassNotFoundException, SQLException {
		ChatDao chatdao = sqlsession.getMapper(ChatDao.class);
		UserDto user = (UserDto)requset.getSession().getAttribute("currentUser");
		String user_email = user.getUser_email();
		String user_nickname = user.getUser_nickname();
		if(chatroomdto.getRoom_secret() == null){ 
			chatroomdto.setRoom_secret("N"); 
		}else { 
			chatroomdto.setRoom_secret("Y"); 
			}
				
		chatroomdto.setUser_email(user_email);
		chatroomdto.setUser_nickname(user_nickname);
		chatdao.createChatRoom(chatroomdto);
		return "redirect:selectChatRoomByRn?room_number="+chatroomdto.getRoom_number();
	}
	
	//채팅방 들어가기
	public String selectChatRoomByRn(int room_number, HttpServletRequest request, Model model) throws ClassNotFoundException, SQLException {
		ChatDao chatdao = sqlsession.getMapper(ChatDao.class);
		ChatRoomDto chatroomdto = chatdao.selectChatRoomByRn(room_number);
		int currentCount = chatdao.getMemberCount(room_number);
		int totalCount = chatroomdto.getRoom_count();

		if (currentCount < totalCount) {
			
			
			 HttpSession session = request.getSession(); 
			 UserDto user = (UserDto)session.getAttribute("currentUser");
			 
			
			 String user_email = user.getUser_email();
			 
			 int userCount = chatdao.countUserInRoom(room_number, user_email);
			 
			 if(userCount > 0) {
				 chatdao.removeMember(user_email, room_number);
			 }
			 
			 
			chatdao.addMember(room_number, user_email);
			
			model.addAttribute("user_email", user_email);
			 model.addAttribute("chat", chatroomdto);
			
		
			return "chat/chatRoom";
		}
			return "redirect:chatList";
		}
	
		//채팅방 멤버리스트 닉네임 불러오기
		public List<ChatMemberDto> getMemberList(int room_number) throws ClassNotFoundException, SQLException {
			ChatDao chatdao = sqlsession.getMapper(ChatDao.class);
			return chatdao.getMemberList(room_number);
		}
		
		//채팅방 비밀번호 불러오기
		public int getRoomPwd(int room_number) throws ClassNotFoundException, SQLException {
			ChatDao chatdao = sqlsession.getMapper(ChatDao.class);
			return chatdao.getRoomPwd(room_number);
		}
	
	
	
		//채팅방 나가기	
		public String exitChatRoom(String user_email, int room_number) throws ClassNotFoundException, SQLException {
			ChatDao chatdao = sqlsession.getMapper(ChatDao.class);
			chatdao.removeMember(user_email, room_number);
			return "redirect:chatList";
		}
		
		//채팅방 삭제하기
		public String deleteChatRoom(int room_number, String user_email) throws ClassNotFoundException, SQLException {
			ChatDao chatdao = sqlsession.getMapper(ChatDao.class);
			chatdao.removeMember(user_email, room_number);
			chatdao.deleteChatRoom(room_number);
			return "redirect:chatList";
		}
		
	

	}
	
	


