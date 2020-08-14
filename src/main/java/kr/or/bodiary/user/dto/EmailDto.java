package kr.or.bodiary.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class EmailDto {

	
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