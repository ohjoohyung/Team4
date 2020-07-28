package kr.or.bodiary.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
    
    public LoginSuccessHandler() {
    	System.out.println("LoginSuccessHandler 생성");
    }
	

    @Autowired
    private SqlSession sqlsession;
    
	private String loginEmail;
	private String defaultUrl;
	private String associateUrl;
	
	public String getLoginEmail() {
		return loginEmail;
	}
	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}
	public String getDefaultUrl() {
		return defaultUrl;
	}
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
	public String getAssociateUrl() {
		return associateUrl;
	}
	public void setAssociateUrl(String associateUrl) {
		this.associateUrl = associateUrl;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		resultRedirectStrategy(request, response, authentication);
		clearAuthenticationAttributes(request);
		sessionAdd(request, authentication);
	}
    protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStratgy.sendRedirect(request, response, targetUrl);
        } else if(authentication.getAuthorities().toString().equals("[ROLE_ASSOCIATE_USER]")){
        	redirectStratgy.sendRedirect(request, response, associateUrl);
        }else {
            redirectStratgy.sendRedirect(request, response, defaultUrl);
        }
        
    }
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
    
    //세션 저장
    protected void sessionAdd(HttpServletRequest request , Authentication authentication) {
        HttpSession session = request.getSession(true);
        UserDto currentUser = null;
        System.out.println("sqlsession : "+ sqlsession);

      try {	
        	System.out.println("authentication.getName() : "+authentication.getName());
        	UserDao userdao = sqlsession.getMapper(UserDao.class);
        	System.out.println(userdao);
        currentUser = userdao.getUser(authentication.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println("currentUser : "+currentUser);
        session.setAttribute("currentUser", currentUser);
        }
    
}
