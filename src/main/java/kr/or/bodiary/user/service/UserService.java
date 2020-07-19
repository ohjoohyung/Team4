package kr.or.bodiary.user.service;

import java.io.FileOutputStream;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bodiary.user.dao.UserDao;
import kr.or.bodiary.user.dto.EmailDto;
import kr.or.bodiary.user.dto.UserDto;

@Service
public class UserService {
	private SqlSession sqlsession;

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	// -----------유저찾기 서비스-----------

	public UserDto getUser(String user_email) {
		System.out.println("유저 찾아야됨" + user_email);
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		UserDto currentUser = null;
		try {
			currentUser = userdao.getUser(user_email);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return currentUser;
	}

	// -----------회원가입 서비스-----------
	public String register(UserDto user) {
		System.out.println(user.toString());
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		
		try {
			System.out.println("register try문");
			user.setUser_pwd(bCryptPasswordEncoder.encode(user.getUser_pwd()));
			userdao.insertUser(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/login";
	}
	
	// ----------- 유저 비밀번호 수정 서비스 -----------
	public String updatePwd(UserDto user, HttpServletRequest request) {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		String beforePwd = "";
		String returnUrl = "";
		String newPwd = request.getParameter("user_new_pwd");
		try {
			beforePwd = userdao.selectPwd(request.getParameter("user_email"));
			//String으로 반환됨
			if(bCryptPasswordEncoder.matches(request.getParameter("user_pwd"), beforePwd))  {
				System.out.println("비밀번호 일치");
//				if(request.getParameter("user_new_pwd")) {
				user.setUser_pwd(bCryptPasswordEncoder.encode(newPwd));
				userdao.updatePwd(user);
				returnUrl = "redirect:/myBodiaryMain";
//				}else{
//					returnUrl ="8~20자에 특수문자가 반드시 포함된 영어 대소문자,숫자를 사용하세요";
//				}
				
			}else {
				System.out.println("비밀번호 불일치");
				returnUrl = "redirect:/myPageEdit";
			}
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		return returnUrl;
	}
	
	// ----------- 유저 닉네임 수정 서비스 -----------
	public String updateNick(UserDto user, HttpServletRequest request) {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		int resultInt = 0;
		String returnUrl = "";
 		try {
			
			user.setUser_email(request.getParameter("user_email"));
			user.setUser_nickname(request.getParameter("user_nickname"));
			resultInt = userdao.updateNick(user);
			
			if(resultInt > 0) {
				returnUrl = "redirect:/myPageEdit";
			}else {
				returnUrl = "redirect:/myPageEdit";
			}
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		return returnUrl;
	}
	// ----------- 준회원 유저정보 수정 서비스 -----------
	@Transactional
	public String updateUserAssociate(UserDto user, HttpServletRequest request) {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		String filename = user.getFile().getOriginalFilename();
		String path = request.getSession().getServletContext().getRealPath("assets/upload/userUpload");
		String fpath = path + "\\" + filename;
		System.out.println(fpath);
		FileOutputStream fs = null;
		String resultReturn = null;
		int updateUserResult = 0;
		int updateRoleResult = 0;
		try {
			System.out.println("updateUser try문");
			fs = new FileOutputStream(fpath);
			fs.write(user.getFile().getBytes());
			fs.close();
			System.out.println("filename : " + filename);
			if (filename.isEmpty() && filename == "") {
				user.setUser_img(user.getUser_img());
			} else {
				user.setUser_img(filename);
			}
			updateUserResult = userdao.updateUser(user);

			System.out.println("유저정보 수정 서비스 내 user : " + user);
			if (updateUserResult > 0) {
				System.out.println("롤 업데이트치러 갑니다");
				updateRoleResult = userdao.updateRole(user);
				System.out.println("롤 업데이트 : " + updateRoleResult);
				if (updateUserResult > 0) {
					resultReturn = "redirect:/myProfileDetail";
				} else {
					resultReturn = "redirect:/myProfileEdit";
					return resultReturn;
				}

				HttpSession session = request.getSession(true);
				session.setAttribute("currentUser", user);

			}
		} catch (Exception e) {
		}

		return resultReturn;
	}

	// -----------유저정보 수정 서비스-----------
	public String updateUser(UserDto user, HttpServletRequest request) {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		String filename = user.getFile().getOriginalFilename();
		String path = request.getSession().getServletContext().getRealPath("assets/upload/userUpload");
		String fpath = path + "\\" + filename;
		System.out.println(fpath);
		FileOutputStream fs = null;
		int result = 0;
		try {
			System.out.println("updateUser try문");
			fs = new FileOutputStream(fpath);
			fs.write(user.getFile().getBytes());
			fs.close();
			System.out.println("filename : " + filename);
			if (filename.isEmpty() && filename == "") {
				user.setUser_img(user.getUser_img());
			} else {
				user.setUser_img(filename);
			}
			result = userdao.updateUser(user);
		} catch (Exception e) {
		}
		System.out.println("유저정보 수정 서비스 내 user : " + user);
		String resultReturn = null;
		if (result > 0) {
			HttpSession session = request.getSession(true);
			session.setAttribute("currentUser", user);

			resultReturn = "redirect:/myProfileDetail";
		} else if (result == 0)
			resultReturn = "redirect:/myProfileEdit";

		return resultReturn;
	}
	// -----------유저 탈퇴 요청 처리-----------
	public String updateWithdrawalUser(UserDto user, HttpServletRequest request) {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		int result = 0;
		try {
			result = userdao.updateWithdrawalUser(user);
			System.out.println(result);
		} catch (Exception e) {
			e.getMessage();
		}
		return "redirect:/main";
	}
	

	// -----------이메일 체크-----------
	public int emailCheck(String email) {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		int result = 0;
		try {
			result = userdao.emailCheck(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// ------------이메일 발송 서비스------------
	public int sendConfirmEmail(EmailDto emaildto) throws Exception {
		MimeMessage messagedto = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(messagedto, true, "UTF-8");

		Random random = new Random(System.currentTimeMillis());
		int confirmation = 0;

		while (true) {
			confirmation = (random.nextInt(10000));
			if (confirmation < 10000 && confirmation > 1000) {
				break;
			}
		}

		messageHelper.setFrom("bitcamp155@gmail.com"); // 보내는 메일주소는 수정하자 dispatcher-servlet이랑 맞춰주자.
		messageHelper.setTo(emaildto.getReceiveMail());
		messageHelper.setSubject("바디어리 회원가입을 위해 요청하신 인증번호입니다.");
		messageHelper.setText("요청하신 인증번호는 " + confirmation + "입니다.");

		mailSender.send(messagedto);

		return confirmation;
	}

}
