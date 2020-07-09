package kr.or.bodiary.user.service;

import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bodiary.user.dao.UserDao;
import kr.or.bodiary.user.dto.EmailDto;
import kr.or.bodiary.user.dto.UserDto;


@Service
public class UserService {
	private SqlSession sqlsession;
	
	@Autowired
	private JavaMailSender mailSender; 
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
//	public NotYet getUser(String id) throws ClassNotFoundException, SQLException {
//		UserDao userdao = sqlsession.getMapper(UserDao.class);
//		System.out.println(userdao.getUser(id));
//		return userdao.getUser(id);
//	}
	//-----------유저찾기 서비스-----------

	public UserDto getUser(String user_email) {
		System.out.println("유저 찾아야됨"+user_email);
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		UserDto currentUser = null;
		try {
			currentUser = userdao.getUser(user_email);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return currentUser;
	}
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
		return "redirect:/login";
	}
	
	//-----------이메일 체크-----------
	public int emailCheck(String email) {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		int result=0;
		try {
			result = userdao.emailCheck(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//------------이메일 발송 서비스------------
	public int sendConfirmEmail(EmailDto emaildto) throws Exception{
        MimeMessage messagedto = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(messagedto, true, "UTF-8");
        
        Random random = new Random(System.currentTimeMillis());
        int confirmation = 0;
        
        while(true){
            confirmation = (random.nextInt(10000));
            if(confirmation <10000 && confirmation>1000){break;}
        }
        
        messageHelper.setFrom("bitcamp155@gmail.com"); // 보내는 메일주소는 수정하자 dispatcher-servlet이랑 맞춰주자.        
        messageHelper.setTo(emaildto.getReceiveMail());
        messageHelper.setSubject("바디어리 회원가입을 위해 요청하신 인증번호입니다.");
        messageHelper.setText("요청하신 인증번호는 " + confirmation + "입니다.");
 
        mailSender.send(messagedto);
        
        return confirmation;
    }
	
	
}
