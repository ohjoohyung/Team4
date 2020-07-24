
package kr.or.bodiary.user.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.google.connect.GoogleOAuth2Template;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;

import kr.or.bodiary.user.dto.AuthInfo;
import kr.or.bodiary.user.dto.UserDto;
import kr.or.bodiary.user.service.UserService;
import kr.or.bodiary.user.service.VerifyRecaptcha;
import kr.or.bodiary.utils.NaverLoginBO;

@Controller
public class UserController {
	
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	/* NaverLoginBO */
	private NaverLoginBO naverLoginBO;
	private String apiResult = null;

	@Autowired
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}
	
	/* GoogleLogin */
	@Inject
	private AuthInfo authInfo;

	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;

	@Autowired
	private GoogleOAuth2Template googleOAuth2Template;

	@Autowired
	private OAuth2Parameters googleOAuth2Parameters;

	//------------- 로그인 --------------
		//로그인 페이지 
		@RequestMapping("/login")
		public String login(@RequestParam(value ="errormsg", required = false)Object errormsg, 
				@RequestParam(value ="user_email", required = false) Object user_email, 
				HttpServletRequest request, ModelMap modelmap) {
			
			// 구글 로그인 인증을 위한 url 생성
			OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
			String googleAuthUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
			
			Map<String,?> redirectMap = RequestContextUtils.getInputFlashMap(request);
			String newerrormsg = "";
			//네이버 url
			String naverAuthUrl = naverLoginBO.getAuthorizationUrl(request.getSession());
			
			System.out.println(naverAuthUrl);
			if ( errormsg != null ) {
				System.out.println(user_email);
				newerrormsg = (String)redirectMap.get("errormsg");
				System.out.println(newerrormsg);
				modelmap.put("errormsg", newerrormsg);
			} else {
				modelmap.put("naver_url", naverAuthUrl); 
				modelmap.put("google_url", googleAuthUrl); 
				// 확인용 출력문
				System.out.println("네이버:" + naverAuthUrl);
				System.out.println("구글" + googleAuthUrl);
			}
			
			
			return "user/login";
		}
		@RequestMapping("/loginFail")
		public String login(HttpServletRequest request, RedirectAttributes redirect) {
			System.out.println("에러 컨트롤러 탐");
			Object user_email = request.getAttribute("user_email");
			Object errormsg = userService.getWithdrawalUser(user_email,request.getAttribute("errormsgname"));
			System.out.println(errormsg);
			System.out.println(user_email);
			redirect.addFlashAttribute("errormsg", errormsg);
			redirect.addFlashAttribute("user_email", user_email);
			return "redirect:/login";
		}
		@RequestMapping(value = "/gCallback", method = { RequestMethod.GET, RequestMethod.POST })
		public String googlecallback(Model model, HttpSession session, HttpServletRequest request) throws Exception {
		      String code = request.getParameter("code");
		      
		      //RestTemplate을 사용하여 Access Token 및 profile을 요청한다.
		      RestTemplate restTemplate = new RestTemplate();
		      MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		        parameters.add("code", code);
		        parameters.add("client_id", authInfo.getClientId());
		        parameters.add("client_secret", authInfo.getClientSecret());
		        parameters.add("redirect_uri", googleOAuth2Parameters.getRedirectUri());
		        parameters.add("grant_type", "authorization_code");      
		        
		        
		        HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
		        ResponseEntity<Map> responseEntity = restTemplate.exchange("https://www.googleapis.com/oauth2/v4/token", HttpMethod.POST, requestEntity, Map.class);
		        Map<String, Object> responseMap = responseEntity.getBody();
		        
		        // id_token 라는 키에 사용자가 정보가 존재한다.
		        // 받아온 결과는 JWT (Json Web Token) 형식으로 받아온다. 콤마 단위로 끊어서 첫 번째는 현 토큰에 대한 메타 정보, 두 번째는 우리가 필요한 내용이 존재한다.
		        // 세번째 부분에는 위변조를 방지하기 위한 특정 알고리즘으로 암호화되어 사이닝에 사용한다.
		        //Base 64로 인코딩 되어 있으므로 디코딩한다.
		        
		        String[] tokens = ((String)responseMap.get("id_token")).split("\\.");
		        Base64 base64 = new Base64(true);
		        String body = new String(base64.decode(tokens[1]));
		       
		        System.out.println(tokens.length);
		        System.out.println(new String(Base64.decodeBase64(tokens[0]), "utf-8"));
		        System.out.println(new String(Base64.decodeBase64(tokens[1]), "utf-8"));
		        
		        //Jackson을 사용한 JSON을 자바 Map 형식으로 변환
		        ObjectMapper mapper = new ObjectMapper();
		        Map<String, String> result = mapper.readValue(body, Map.class);
		        System.out.println(result);
		        System.out.println("너는 어디에 찍히는값인가: "+result.get(""));
		        
		      String user_email = (String) result.get("email");
		        
		      //return "login/naverSuccess"; 여기까지가 성공
		        
		        if(userService.getUser(user_email)==null) {
		         
		         model.addAttribute("user_email", user_email);  //회원가입 시 id로 활용
		         model.addAttribute("user_snstype", "google"); //snstype 파악을 위해
		         return "user/SNSRegister";  //나중에 redirect화 하자
		      }else if(userService.getUser(user_email).getUser_snstype() != null) {
		      
		      //스프링 시큐리티 수동 로그인을 위한 작업//
		      //로그인 세션에 들어갈 권한을 설정
		         List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		         list.add(new SimpleGrantedAuthority("ROLE_REGULAR_USER"));
		      
		         SecurityContext sc = SecurityContextHolder.getContext();
		      //아이디, 패스워드, 권한을 설정. 아이디는 Object단위로 넣어도 무방하며
		      //패스워드는 null로 하여도 값이 생성.
		         sc.setAuthentication(new UsernamePasswordAuthenticationToken(user_email, null, list));
		         session = request.getSession(true);
		      
		      //위에서 설정한 값을 Spring security에서 사용할 수 있도록 세션에 설정
		         session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
		      //스프링 시큐리티 수동 로그인을 위한 작업 끝//
		      
		      //로그인 유저 정보 가져와서 세션객체에 저장  
		          UserDto user = userService.getUser(user_email);
		         
		          session = request.getSession();
		          session.setAttribute("currentUser", user);
		      //로그인 유저 정보 가져와서 세션객체에 저장 끝//      
		         
		      return "user/callback";
		      }else {
		    	  return "redirect:/login?error";
		      }
		      
		}
		
		
		//네이버 로그인 성공시 callback호출 메소드
		@RequestMapping(value = "/nCallback", method = { RequestMethod.GET, RequestMethod.POST })
		public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session, HttpServletRequest request)
				throws IOException, ParseException {
			System.out.println("여기는 callback");
			OAuth2AccessToken oauthToken;
			oauthToken = naverLoginBO.getAccessToken(session, code, state);
			//1. 로그인 사용자 정보를 읽어온다.
			apiResult = naverLoginBO.getUserProfile(oauthToken); // String형식의 json데이터
			
			//2. String형식인 apiResult를 json형태로 바꿈
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(apiResult);
			JSONObject jsonObj = (JSONObject) obj;
			
			//3. 데이터 파싱
			//Top레벨 단계 _response 파싱
			JSONObject response_obj = (JSONObject) jsonObj.get("response");
			//response의 nickname값 파싱
			String user_email = (String) response_obj.get("email");
			String user_nickname = (String) response_obj.get("nickname");
			System.out.println(user_email);
			
			//4.파싱 닉네임 세션으로 저장
			session.setAttribute("user_email", user_email); // 세션 생성
			model.addAttribute("result", apiResult);
			
			//5.회원가입한 아이디 여부 판단하여 회원가입으로 보낼지 로그인 시킬지 결정
	        if(userService.getUser(user_email)==null) {
	           
	           model.addAttribute("user_email", user_email);  //회원가입 시 id로 활용
	           model.addAttribute("user_nickname", user_nickname);  //회원가입 시 id로 활용
	           model.addAttribute("user_snstype", "naver"); //snstype 파악을 위해
	           
	           return "user/SNSRegister";  //나중에 redirect화 하자
	           
	        }else if(userService.getUser(user_email).getUser_snstype() != null) {

	        //스프링 시큐리티 수동 로그인을 위한 작업//
	        //로그인 세션에 들어갈 권한을 설정
	        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
	        list.add(new SimpleGrantedAuthority("ROLE_REGULAR_USER"));
	  
	        SecurityContext sc = SecurityContextHolder.getContext();
	        //아이디, 패스워드, 권한을 설정. 아이디는 Object단위로 넣어도 무방하며
	        //패스워드는 null로 하여도 값이 생성.
	        sc.setAuthentication(new UsernamePasswordAuthenticationToken(user_email, null, list));
	        session = request.getSession(true);
	  
	        //위에서 설정한 값을 Spring security에서 사용할 수 있도록 세션에 설정
	        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
	        //스프링 시큐리티 수동 로그인을 위한 작업 끝//
	  
	        //로그인 유저 정보 가져와서 세션객체에 저장  
	        UserDto user = userService.getUser(user_email);
	     
	        session = request.getSession();
	        session.setAttribute("currentUser", user);
	        //로그인 유저 정보 가져와서 세션객체에 저장 끝//      
			return "user/callback";
	        }else {
	        	return "redirect:/login?error";
	        }
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
	@RequestMapping(value="updateNick" , method=RequestMethod.POST, produces = "application/text; charset=utf8")
	public String updateNick(UserDto user,HttpServletRequest request) {
		System.out.println("유저 닉네임 정보 수정하러 왔슴다~");
		String result = "";
		UserDto currentUser = null;
		try {
			result = userService.updateNick(user, request);
			currentUser = userService.getUser(request.getParameter("user_email"));
			
			HttpSession session = request.getSession(true);
			session.setAttribute("currentUser", currentUser);
		} catch (Exception e) {
			e.getMessage();
		}
		
		System.out.println(result);
		return result;
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
		String result = "";
		UserDto currentUser = null;
		try {
			result = userService.updateUser(user, request);
			currentUser = userService.getUser(request.getParameter("user_email"));
			
			HttpSession session = request.getSession(true);
			session.setAttribute("currentUser", currentUser);
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
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
	public String sendConfirmEmail(String user_email) throws Exception {
        return userService.sendConfirmEmail(user_email);
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
	
	
	
	//-------------- 차트 ------------------
	
	
	//자신의 성별, 키에 해당하는 회원 평균 몸무게 조회
	@ResponseBody
	@RequestMapping("/getAvgWeight")
	public int getAvgWeight(int user_height, String user_gender) throws ClassNotFoundException, SQLException {
		return userService.getAvgWeight(user_height, user_gender);
	}
	

}
