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
	public String naverCallback() {
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
	
	//------------- 이메일 확인 --------------
	@ResponseBody
	@RequestMapping("/emailCheck")
	public int emailcheck(String email) {
		return userService.emailCheck(email);
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
