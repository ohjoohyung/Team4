package kr.or.bodiary.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import kr.or.bodiary.user.dto.UserDto;
import kr.or.bodiary.user.service.UserService;

public class LoginFailureHandler implements AuthenticationFailureHandler {
	
	private String loginEmail;
    private String loginPwd;
    private String errormsgname;
    private String defaultFailureUrl;
    
    
	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getErrormsgname() {
		return errormsgname;
	}

	public void setErrormsgname(String errormsgname) {
		this.errormsgname = errormsgname;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		System.out.println("로그인 실패 핸들러 탔음");
		
		String username = request.getParameter(loginEmail);
	    String password = request.getParameter(loginPwd);
	    String errormsg = null;
	    
	    
	    if(exception instanceof BadCredentialsException) {
	    	
//	    	if(userdto.getUser_grade() == "withdrawal") {
//	    		errormsg = "탈퇴한 회원입니다. 한달 후 재가입이 가능합니다.";
//	    	}else { 
	    	errormsg = "비밀번호 또는 아이디가 일치하지 않습니다. 다시 확인해주세요.";
//	    	}
        } else if(exception instanceof InternalAuthenticationServiceException) {
            errormsg = "비밀번호 또는 아이디가 일치하지 않습니다. 다시 확인해주세요.";
        } else if(exception instanceof DisabledException) {
            errormsg = "계정이 비활성화되었습니다. 관리자에게 문의하세요.";
        } else if(exception instanceof CredentialsExpiredException) {
            errormsg = "비밀번호 유효기간이 만료되었습니다. 관리자에게 문의하세요.";
        } else if(exception instanceof LockedException) {
        	errormsg = "계정이 잠겨있습니다. 관리자에게 문의하세요.";
        } 

	    System.out.println(errormsg);
	    
        request.setAttribute("errormsgname", errormsg);
        request.setAttribute("user_email", username);
        System.out.println(username);
        request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}

}


