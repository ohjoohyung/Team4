package kr.or.bodiary.chat.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.chat.dto.ChatMemberDto;
import kr.or.bodiary.chat.dto.ChatRoomDto;



public interface ChatDao {
	
	//채팅방 만들기
	public int createChatRoom(ChatRoomDto chatroomdto) throws ClassNotFoundException, SQLException;
	
	//채팅방 리스트 가져오기
	public List<ChatRoomDto> getChatList() throws ClassNotFoundException, SQLException;
	
	//채팅방 들어가기
	public ChatRoomDto selectChatRoomByRn(int room_number) throws ClassNotFoundException, SQLException;
	
	//채팅방 멤버 추가하기
	public int addMember(int room_number, String user_email) throws ClassNotFoundException, SQLException;
	
	//채팅방 멤버 카운트
	public int getMemberCount(int room_number) throws ClassNotFoundException, SQLException;
	
	//채팅방 멤버리스트 닉네임 불러오기
	public List<ChatMemberDto> getMemberList(int room_number) throws ClassNotFoundException, SQLException;
	
	//채팅방 비밀번호 불러오기
	public int getRoomPwd(int room_number) throws ClassNotFoundException, SQLException;
	
	//채팅방 멤버 나가기
	public int removeMember(String user_email, int room_number) throws ClassNotFoundException, SQLException;
	
	//채팅방 삭제하기
	public int deleteChatRoom(int room_number) throws ClassNotFoundException, SQLException;
	
	//채팅방에 현재 접속할 유저가 존재하는지 판별
	public int countUserInRoom(int room_number, String user_email) throws ClassNotFoundException, SQLException;
}
