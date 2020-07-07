package kr.or.bodiary.user.service;

import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import kr.or.bodiary.user.dao.UserDao;
import kr.or.bodiary.user.dto.EmailDto;
import kr.or.bodiary.user.dto.UserDto;


@Service
public class UserService {
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
//	public NotYet getUser(String id) throws ClassNotFoundException, SQLException {
//		UserDao userdao = sqlsession.getMapper(UserDao.class);
//		System.out.println(userdao.getUser(id));
//		return userdao.getUser(id);
//	}
	
	//-----------회원가입 서비스-----------
	public String register(UserDto user) {
		System.out.println(user.toString());
		
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		try {
			System.out.println("register try문");
			userdao.insertUser(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "user/login";
	}
	
	//-----------이메일 체크-----------
	public int emailCheck(UserDto user) {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		int result=0;
		try {
			result = userdao.emailCheck(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//------------이메일 발송 서비스------------
	public int sendConfirmEmail(EmailDto emaildto) throws Exception{
        MimeMessage messagedto = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(messagedto, true, "UTF-8");
        
        Random random = new Random(System.currentTimeMillis());
        int confirmation = 0;
        
        while(true){
            confirmation = (random.nextInt(10000));
            if(confirmation <10000 && confirmation>1000){break;}
        }
        
        messageHelper.setFrom("bitcamp155@gmail.com"); // 보내는 메일주소는 수정하자 dispatcher-servlet이랑 맞춰주자.        
        messageHelper.setTo(emaildto.getReceiveMail());
        messageHelper.setSubject("Serendipity : 요청하신 인증번호입니다.");
        messageHelper.setText("요청하신 인증번호는 " + confirmation + "입니다.");
 
        emailSender.send(messagedto);
        
        return confirmation;
    }
	
	
}
