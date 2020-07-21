package kr.or.bodiary.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class EmailDto {
	
//	private String senderName;    //발신자 이름
//    private String senderMail;    //발신자 이메일 주소
//    private String receiveMail;    //수신자 이메일 주소
//    private String subject;            //제목
//    private String message;            //본문
    
    
   private String mailFrom;
 
   private String mailTo;
 
   private String mailCc;
 
   private String mailBcc;
 
   private String mailSubject;
 
   private String mailContent;
 
   private String templateName;
 
   private String contentType;
 
   public EmailDto() {
      contentType = "text/html";
   }
 
}