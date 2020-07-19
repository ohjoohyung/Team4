package kr.or.bodiary.user.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bodiary.user.dto.EmailDto;
import kr.or.bodiary.user.dto.UserDto;
import kr.or.bodiary.user.service.UserService;
import kr.or.bodiary.user.service.VerifyRecaptcha;

@Controller
public class UserController {
	
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	//------------- 로그인 --------------
	//로그인 페이지 
	@RequestMapping("/login")
	public String login() {
		
		return "user/login";
	}
	
	@RequestMapping("/nCallback")
	public String naverCallback(UserDto user ,HttpServletRequest request) {
		System.out.println("gd");
		System.out.println();
		
		return "user/nCallback";
	}
	//------------- 회원가입 --------------
	//회원가입 페이지
	@RequestMapping("/register")
	public String register() {
		return "user/register";
	}
	//회원가입 처리
	@RequestMapping(value="/register" , method=RequestMethod.POST)
	public String register(UserDto user) {
//		user.setUser_pwd(bCryptPasswordEncoder.encode(user.getUser_pwd()));
		System.out.println("register 탐");
		return userService.register(user);
	}
	//---------- 준회원 정보수정 -----------
	@RequestMapping("/profileEditAssociate")
	public String profileEditForAssociateUser() {
		return "user/profileEditForAssociateUser";
	}
	@RequestMapping(value="/profileEditAssociate" , method=RequestMethod.POST)
	public String profileEditForAssociateUser(UserDto user ,HttpServletRequest request) {
		
		return userService.updateUserAssociate(user, request);
	}
	//---------- 탈퇴처리 -----------
	@RequestMapping("/myWithdraw")
	   public String getMyWithdraw() {
	   return "myBodiary/myWithdraw";
	}
	@RequestMapping(value="/myWithdraw" , method=RequestMethod.POST)
	public String updateWithdrawalUser(UserDto user ,HttpServletRequest request) {
		return userService.updateWithdrawalUser(user, request);
	}
	
	//---------- 계정정보 수정 -----------
	@RequestMapping("/myPageEdit")
	public String myPageEdit() {
		return "myBodiary/myPageEdit";
	}
	@ResponseBody
	@RequestMapping(value="updatePwd" , method=RequestMethod.POST)
	public String pwdUpdate(UserDto user,HttpServletRequest request) {
		System.out.println("유저 패스워드 수정하러 왔슴다~");
		
		return userService.updatePwd(user,request);
	}
	@ResponseBody
	@RequestMapping(value="updateNick" , method=RequestMethod.POST)
	public String updateNick(UserDto user,HttpServletRequest request) {
		System.out.println("유저 닉네임 정보 수정하러 왔슴다~");
		return userService.updateNick(user, request);
	}
	
	//---------- 계정정보 외 프로필정보 수정 -----------
	@RequestMapping("/myProfileDetail")
	public String myProfileDetail() {
		return "myBodiary/myProfileDetail";
	}
	@RequestMapping("/myProfileEdit")
	public String myProfileEdit() {
		return "myBodiary/myProfileEdit";
	}
	
	
	@RequestMapping(value="/myProfileEdit" , method=RequestMethod.POST)
	public String myProfileEdit(UserDto user,HttpServletRequest request) {
		System.out.println("myProfileEdit 컨트롤러");
		System.out.println("form에서 넘어오는 값 : " + user);
		return userService.updateUser(user, request);
	}
	//------------- 이메일 확인 --------------
	@ResponseBody
	@RequestMapping("/emailCheck")
	public int emailcheck(String user_email) {
		System.out.println(user_email);
		System.out.println(userService.getUser(user_email));
		return userService.emailCheck(user_email);
	}
	//------------- 이메일 인증번호 전송 -------------
	@ResponseBody
	@RequestMapping("/confirmEmail")
	public int sendConfirmEmail(EmailDto emaildto) throws Exception {
        return userService.sendConfirmEmail(emaildto);
    }
	
	
	//------------- 리캡차 --------------
	
	@ResponseBody
	@RequestMapping(value = "VerifyRecaptcha", method = RequestMethod.POST)
	public int VerifyRecaptcha(HttpServletRequest request) {
		VerifyRecaptcha.setSecretKey("6LfpFq4ZAAAAADZmv1ZIqOr0407vgRt2H01KnUWM"); 
		//시크릿키를 넣어준다.
		String gRecaptchaResponse = request.getParameter("recaptcha");
		System.out.println(gRecaptchaResponse);
		//0 = 성공, 1 = 실패, -1 = 오류
		try {
			if(VerifyRecaptcha.verify(gRecaptchaResponse))
				return 0;
			else return 1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	//------------- 리캡차 끝 --------------
	
	@RequestMapping("/deleteAccount")
	public String deleteAccount() {
		return "user/deleteAccount";
	}

}
