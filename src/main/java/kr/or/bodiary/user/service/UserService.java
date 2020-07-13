package kr.or.bodiary.user.service;

import java.io.FileOutputStream;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
	//----------- 준회원 유저정보 수정 서비스 -----------
	public String updateUserAssociate(UserDto user, HttpServletRequest request) {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		String filename = user.getFile().getOriginalFilename();
		String path = request.getSession().getServletContext().getRealPath("assets/upload/userUpload");
		String fpath = path +"\\" +filename;
		System.out.println(fpath);
		FileOutputStream fs = null;
		int updateUserResult = 0;
		int updateRoleResult = 0;
		try {
			System.out.println("updateUser try문");
			fs= new FileOutputStream(fpath);
			fs.write(user.getFile().getBytes());
			fs.close();
			System.out.println("filename : "+filename);
			if(filename.isEmpty() && filename == "") {
				user.setUser_img(user.getUser_img());
			}else {
				user.setUser_img(filename);
			}
			updateUserResult = userdao.updateUser(user);
		} catch (Exception e) {
		}
		System.out.println("유저정보 수정 서비스 내 user : "+user);
		String resultReturn = null;
		if (updateUserResult >0) {
			try {
				System.out.println("롤 업데이트치러 갑니다");
				updateRoleResult = userdao.updateRole(user);
				System.out.println("롤 업데이트 : "+updateRoleResult);
				if(updateUserResult >0) {
					resultReturn= "redirect:/myProfileDetail";
				}else {
					resultReturn="redirect:/myProfileEdit";
					return resultReturn;
				}
			} catch (Exception e) {
				e.getMessage();
			}
			
			HttpSession session = request.getSession(true);
			session.setAttribute("currentUser", user);
			
		}
		else if (updateUserResult == 0) resultReturn="redirect:/myProfileEdit";
		
		return resultReturn;
	}
	//-----------유저정보 수정 서비스-----------
	public String updateUser(UserDto user, HttpServletRequest request ) {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		String filename = user.getFile().getOriginalFilename();
		String path = request.getSession().getServletContext().getRealPath("assets/upload/userUpload");
		String fpath = path +"\\" +filename;
		System.out.println(fpath);
		FileOutputStream fs = null;
		int result = 0;
		try {
			System.out.println("updateUser try문");
			fs= new FileOutputStream(fpath);
			fs.write(user.getFile().getBytes());
			fs.close();
			System.out.println("filename : "+filename);
			if(filename.isEmpty() && filename == "") {
				user.setUser_img(user.getUser_img());
			}else {
				user.setUser_img(filename);
			}
			result = userdao.updateUser(user);
		} catch (Exception e) {
		}
		System.out.println("유저정보 수정 서비스 내 user : "+user);
		String resultReturn = null;
		if (result >0) {
			HttpSession session = request.getSession(true);
			session.setAttribute("currentUser", user);
			
			resultReturn= "redirect:/myProfileDetail";
		}
		else if (result == 0) resultReturn="redirect:/myProfileEdit";
		
		return resultReturn;
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
