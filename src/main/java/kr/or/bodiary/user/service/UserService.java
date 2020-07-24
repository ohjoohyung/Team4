package kr.or.bodiary.user.service;

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;
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

import kr.or.bodiary.freeBrd.dao.FreeBrdDao;
import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.freeBrd.dto.Pagination;
import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;
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

	//------------ 모든 유저 리스트 가져오기 ------------------ (동률)
	public List<UserDto> getUserList() {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		List<UserDto> userList = null;
		try {
			userList = userdao.getUserList();

		} catch (Exception e) {
			e.printStackTrace();
		}	
		return userList;
	}
	
	//------------ 해당 유저 권한 수정하기------------------ (동률)
	public int userRoleUpdate(String role,String email) {
		UserDto user = new UserDto();
		user.setRole_name(role);
		user.setUser_email(email);
		
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		int userRoleUpdate = 0;
		try {
			userRoleUpdate = userdao.userRoleUpdate(user);

		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return userRoleUpdate;
	}
	
	//------------ 해당 유저의 총 자유게시물 개수 가져오기------------------ (동률)
	public int freeBrdCount(String user_email) throws ClassNotFoundException, SQLException {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		return userdao.freeBrdCount(user_email);
	}
	
	//------------ 해당 유저의 총 루틴 자랑 게시물 개수 가져오기------------------ (동률)
	public int routineBrdCount(String user_email) throws ClassNotFoundException, SQLException {
		UserDao userdao = sqlsession.getMapper(UserDao.class);
		return userdao.routineBrdCount(user_email);
	}
	
	//------------ 해당 유저 자유게시판 리스트 가져오기 ------------------ (동률)
	public List<FreeBrdDTO> getUserFreeBrdList(String user_email) {
		FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
		
		List<FreeBrdDTO> userFreeBrdList = null;
		try {
			userFreeBrdList = FreeBrd.getUserFreeBrdList(user_email);
			for(int i=0;i<userFreeBrdList.size();i++) {
				System.out.println("글번호"+userFreeBrdList.get(i).getFree_brd_seq());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return userFreeBrdList;
	}
	//------------ 해당 유저 루틴 자랑게시판 리스트 가져오기 ------------------ (동률)
	public List<RoutineBrdDto> getUserRoutineBrdList(String user_email) {
		FreeBrdDao routineBrd = sqlsession.getMapper(FreeBrdDao.class);
		
		List<RoutineBrdDto> userRoutineBrdList = null;
		try {
			userRoutineBrdList = routineBrd.getUserRoutineBrdList(user_email);

		} catch (Exception e) {
			e.printStackTrace();
		}	
		return userRoutineBrdList;
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
