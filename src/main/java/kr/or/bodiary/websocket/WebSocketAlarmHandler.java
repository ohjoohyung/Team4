package kr.or.bodiary.websocket;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;





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
		String userid = (String)session.getAttributes().get("userid");
		log(userid + " 접속");
		log(session.toString());
		users.put(userid, session); //userid 와 session 저장
	}	
	
	//연결해제
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		String userid = (String)session.getAttributes().get("userid");
		if(session.getId() != null) {
			if(users.containsKey(userid)) {
				users.remove(userid); //연결해제된 id 삭제
				log(userid + " 해제");
			}
		}
	}
	
	//데이터 전송
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		
		if(message.getPayload().equals("login") || message.getPayload().equals("view")) {
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			String userid = (String)session.getAttributes().get("userid");
			
			 int result = empdao.getmsgcount(userid); 
			
			if(users.containsKey(userid)) {
				
				  TextMessage msg = new TextMessage("수신된 쪽지 : " + result + "건");
				  users.get(userid).sendMessage(msg);
				  
				  log(userid + " / " + message.getPayload() + " / " + msg.getPayload());
				 
			}
		}else {
			String fromid = message.getPayload().split(",")[1];
			
			Message savemsg = new Message(message.getPayload().split(",")[0], message.getPayload().split(",")[1], message.getPayload().split(",")[2]);
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			  empdao.insertMessage(savemsg);
			  
			  int result = empdao.getmsgcount(fromid);
			 
			
			if(users.containsKey(fromid)) {
				
				  TextMessage msg = new TextMessage("수신된 쪽지 : " + result + "건");
				  users.get(fromid).sendMessage(msg); 
				  log(fromid + " / " + message.getPayload()
				  + " / " + msg.getPayload());
				 
			}
			
			System.out.println("fromid : " + fromid);
			System.out.println(message.getPayload());
		}
	}
	
	//연결에 문제 발생시
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception{
		String userid = (String)session.getAttributes().get("userid");
		log(userid + " / " + exception.getMessage());
	}

}

