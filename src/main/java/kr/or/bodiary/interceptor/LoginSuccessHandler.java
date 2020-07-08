package kr.or.bodiary.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import kr.or.bodiary.user.dao.UserDao;
import kr.or.bodiary.user.dto.UserDto;
import kr.or.bodiary.user.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
	private UserService userservice;
    
	private String loginEmail;
    private String defaultUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
//		response.sendRedirect("/");
		resultRedirectStrategy(request, response, authentication);
		clearAuthenticationAttributes(request);
//		sessionAdd(request, authentication);
	}
    protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        
        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStratgy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStratgy.sendRedirect(request, response, defaultUrl);
        }
        
    }
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
    //세션 저장
//    protected void sessionAdd(HttpServletRequest request , Authentication authentication) {
//        HttpSession session = request.getSession(true);
//        UserDto currentUser = null;
//        
//        System.out.println(authentication.getName());
//        currentUser= userservice.getUser(authentication.getName());
////        try {	
////        	System.out.println(authentication.getName());
////        currentUser = userdao.getUser(authentication.getName());
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//        System.out.println(currentUser);
//        session.setAttribute("currentUser", currentUser);
//    }

}
