package kr.or.bodiary.websocket;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.or.bodiary.qnaBrd.dao.QnaBrdDao;
import kr.or.bodiary.qnaBrd.dto.QnaBrdDto;
import kr.or.bodiary.user.dto.UserDto;




//문의 질문, 답변 insert 전부 여기로 바꿈
public class WebSocketAlarmHandler extends TextWebSocketHandler{
	
	
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
		
	}

	
	private static Map<String, WebSocketSession> users = new HashMap<String, WebSocketSession>();

	private void log(String msg) {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = simple.format(new Date());
		System.out.println(date + " : " + msg);
	}
	
	//연결
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("하이");
		String user_email = (String)session.getAttributes().get("user_email");
		log(user_email + " 접속");
		log(session.toString());
		users.put(user_email, session); //userid 와 session 저장
	}	
	
	//연결해제
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		String user_email = (String)session.getAttributes().get("user_email");
		if(session.getId() != null) {
			if(users.containsKey(user_email)) {
				users.remove(user_email); //연결해제된 id 삭제
				log(user_email + " 해제");
			}
		}
	}
	
	//데이터 전송
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		
		JSONParser parser = new JSONParser();
		System.out.println(message.getPayload());
		JSONObject obj = (JSONObject) parser.parse(message.getPayload());
		String type = (String) obj.get("type");
		System.out.println(type);
		String user_email = (String)session.getAttributes().get("user_email");
		QnaBrdDao qnabrddao = sqlsession.getMapper(QnaBrdDao.class);
		String adminEmail = "admin@bodiary.or.kr";
		if(type.equals("view")) {
			
			
			int count = 0;
			//나중에 어드민으로 정한 이메일 또는 role이 어드민일 경우로 바꾸자
			if(users.containsKey(user_email) && user_email.equals(adminEmail)) {
				count = qnabrddao.getCountAdminNotRead();
				if(count > 0) {
					obj.put("text", "새로운 문의가 도착했습니다.");
				} else {
					obj.put("text", "새로운 알림이 없습니다.");
				}
				obj.put("count", count);
				obj.put("now", "admin");
				System.out.println(obj.toJSONString());
				TextMessage msg = new TextMessage(obj.toJSONString());
				 users.get(user_email).sendMessage(msg);
				  log(user_email + " / " + message.getPayload() + " / " + msg.getPayload());
				  
			} else if(users.containsKey(user_email) && !user_email.equals(adminEmail)) {
				count = qnabrddao.getCountUserNotRead(user_email);
				System.out.println("유저 카운트 : " +count);
				if(count > 0) {
					obj.put("text", "문의 답변이 도착했습니다.");
				} else {
					obj.put("text", "새로운 알림이 없습니다.");
				}
				
				obj.put("count", count);
				obj.put("now", "user");
				System.out.println(obj.toJSONString());
				TextMessage msg = new TextMessage(obj.toJSONString());
				 users.get(user_email).sendMessage(msg);
				  log(user_email + " / " + message.getPayload() + " / " + msg.getPayload());
			}
		} else if(type.equals("user")) {
			String title = (String)obj.get("qna_brd_title");
			String content = (String)obj.get("qna_brd_content");
			log(title + " / " + content);
			
			QnaBrdDto qnabrddto = new QnaBrdDto();
			qnabrddto.setUser_email(user_email);
			qnabrddto.setQna_brd_title(title);
			qnabrddto.setQna_brd_content(content);
			
			qnabrddao.insertQnaBrd(qnabrddto);
			qnabrddto.setQna_brd_ref(qnabrddto.getQna_brd_seq());
			qnabrddao.updateQnaBrd(qnabrddto);
			
			int count = 0;
			count = qnabrddao.getCountAdminNotRead();
			
			obj.put("count", count);
			System.out.println(obj.toJSONString());
			TextMessage msg = new TextMessage(obj.toJSONString());
			users.get(adminEmail).sendMessage(msg);
			log(adminEmail + " / " + message.getPayload() + " / " + msg.getPayload());
		} else {
			String title = (String)obj.get("qna_brd_title");
			String content = (String)obj.get("qna_brd_content");
			String ref = (String)obj.get("qna_brd_ref");
			String to_user_email = (String)obj.get("to_user_email");

			log(title + " / " + content + " / " + ref + " / " + to_user_email);
			
			QnaBrdDto qnabrddto = new QnaBrdDto();
			qnabrddto.setUser_email(user_email);
			qnabrddto.setQna_brd_title(title);
			qnabrddto.setQna_brd_content(content);
			qnabrddto.setQna_brd_ref(Integer.parseInt(ref));
			
			qnabrddao.insertQnaAnsBrd(qnabrddto);
			qnabrddao.updateQnaRep(Integer.parseInt(ref));
			
			int count = qnabrddao.getCountUserNotRead(to_user_email);
			System.out.println("유저 카운트 : " +count);
			obj.put("count", count);
			System.out.println(obj.toJSONString());
			
			TextMessage msg = new TextMessage(obj.toJSONString());
			 users.get(to_user_email).sendMessage(msg);
			  
			  log(to_user_email + " / " + message.getPayload() + " / " + msg.getPayload());
		}
		
		

	}
	
	//연결에 문제 발생시
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception{
		String user_email = (String)session.getAttributes().get("user_email");
		log(user_email + " / " + exception.getMessage());
	}

}

