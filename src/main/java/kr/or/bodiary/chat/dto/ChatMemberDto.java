package kr.or.bodiary.chat.dto;

import lombok.Data;

@Data
public class ChatMemberDto {
	
	private int mem_number;
	private int room_number;
	private String user_email;
	private String user_nickname;
}
