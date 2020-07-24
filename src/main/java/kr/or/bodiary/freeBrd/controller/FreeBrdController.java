      package kr.or.bodiary.freeBrd.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.freeBrd.dto.Search;
import kr.or.bodiary.freeBrd.service.FreeBrdService;
import kr.or.bodiary.user.dto.UserDto;

@Controller
public class FreeBrdController {
	
	private FreeBrdService freeBrdService;
	
	@Autowired
	public void setFreeBrdService(FreeBrdService freeBrdService) {
		this.freeBrdService = freeBrdService;
	}
	
	// 썸머노트 이미지 넣는 함수
    @RequestMapping(value = "freeBrdImage", method = RequestMethod.POST)
    @ResponseBody
    public void profileUpload(MultipartFile file, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

         PrintWriter out = response.getWriter();
         // PrintWriter out = response.getWriter();
         // 업로드할 폴더 경로
         String realFolder = "C:\\summernote\\";
         // String realFolder =
         // request.getServletContext().getRealPath("/summernote/upload/");

         System.out.println("업로드할 폴더경로 찍어봅니다.");
         System.out.println(realFolder);
         UUID uuid = UUID.randomUUID(); // 랜덤한키 생성해주는 객체

         String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
         String storedFileName = uuid.toString().replaceAll("-", "") + fileExtension;
         System.out.println("파일 익스텐션은 뭐지??"+fileExtension);
         System.out.println("올린 파일 이름"+file.getOriginalFilename());
         System.out.println("우철 : " + storedFileName);
         // String filePath = "C:\\Users\\ksks7\\OneDrive\\바탕
         // 화면\\FinalProject\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\nosangStudy\\board\\profileUpload\\";
         // String filePath = "file:\\C:\\Users\\ksks7\\OneDrive\\바탕
         // 화면\\FinalProject\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\nosangStudy\\board\\profileUpload\\";

         File f = new File(realFolder + storedFileName);
         // File f = new File(filepath);
         if (!f.exists()) {
            f.mkdirs(); // 존재하지 않으면 경로에 폴더를 생성해서 만들어준다.
         }
         Boolean a = f.isAbsolute();
         System.out.println(a);
         Boolean b = f.canExecute();
         System.out.println(b);

         // FileOutputStream fs = new FileOutputStream(filePath + "\\"+
         // file.getOriginalFilename());//
         // fs.write(file.getBytes());//

         file.transferTo(f);
         // out.println(filePath + storedFileName);
         // out.println("profileUpload/"+email+"/"+str_filename);

         int as = storedFileName.lastIndexOf(".");
         String bs = storedFileName.substring(0, as);
         System.out.println("아오 : " + bs);

         response.setContentType("text/html;charset=utf-8");
         System.out.println("sss");
         out.println("/filepath/" + storedFileName);
         out.close();
      }
    
    
	//책 검색 
	@RequestMapping("bookSearch")
	public String bookSearch() {		
		return "freeBrd/bookSearch";
	}
	
	//전체 게시글(자유,팁,궁금) 보기
	@RequestMapping("freeBrdList")
	public String AllFreeBrdList(HttpServletRequest request
								,Model model
								,@RequestParam(defaultValue = "1")int page
								,@RequestParam(required = false,defaultValue = "title")String searchType
								,@RequestParam(required = false,defaultValue = "0")int cateGory
								,@RequestParam(required = false)String keyword
								,@ModelAttribute("search") Search search
								) throws Exception {
		
		System.out.println("카테고리 번호"+cateGory);
		System.out.println("검색타입"+searchType);
		System.out.println("검색키워드"+keyword);
		
		//검색을 했을 경우 (검색한게 없어도 실행) 
		model.addAttribute("search",search);
		search.setSearchType(searchType);
		search.setKeyword(keyword);
		search.setCateGory(cateGory);
		
		//해당 검색에 해당하는 총 게시글 수 (카테고리 별로 분류) 
		int searchListCnt = 0;
		//카테고리 별로 총게시물을 가져오는 숫자가 다름 
		if(cateGory == 0) {
			searchListCnt = freeBrdService.allFreeBrdCount(search); //전체 게시물
		}else if(cateGory == 1) {
			searchListCnt = freeBrdService.getLibertyCnt(search); //자유게시판 총 게시물 			
		}else if(cateGory == 2) {
			searchListCnt = freeBrdService.getQuestionCnt(search); //질문게시판 총 게시물 
		}else if(cateGory == 3) {
			searchListCnt = freeBrdService.getTipCnt(search); //팁게시판 총 게시물 
		}

		
		// ******************* 페이징 처리 ****************************************//
		//Pagination 객체 생성 (파라미터 값으로 총게시물수 , 현재 페이지를 받는다)
//		Pagination pagination = new Pagination();
//		pagination.pageInfo(totalListCnt, page);
//		
//		int startIndex = pagination.getStartIndex();
//		//페이지당 보여지는 게시글 최대 개수 
//		int pageSize = pagination.getPageSize();
		// ******************* 페이징 처리 ****************************************//
		
		//검색후 페이징 처리 계산 (검색한게 없어도 실행) 
		search.pageInfo(searchListCnt, page);
		
		List<FreeBrdDTO> freeBrdList = null;
		if(cateGory == 0) {
			System.out.println("전체게시물중에 해당조건 가져오기----------------------------------------------------");
			freeBrdList = freeBrdService.allCatFreeBrd(search);
		}
		else{
			freeBrdList = freeBrdService.allFreeBrd(search);
		}
		
		//로그인한 해당 유저의 정보를 뽑아올수 있음  
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		String user_email = user.getUser_email();
		
		//자유게시판 상위 랭크를 뽑아옴 
		List<FreeBrdDTO> highLankFree = freeBrdService.highLankFree();
		model.addAttribute("highLankFree",highLankFree);
		
		//질문게시판 상위 랭크를 뽑아옴 
		List<FreeBrdDTO> highLankQna = freeBrdService.highLankQna();
		model.addAttribute("highLankQna",highLankQna);
				
		//팁게시판 상위 랭크를 뽑아옴 
		List<FreeBrdDTO> highLankTip = freeBrdService.highLankTip();
		model.addAttribute("highLankTip",highLankTip);
		
		model.addAttribute("user",user_email);
		model.addAttribute("cateGory",cateGory);
		model.addAttribute("pagination",search);
		model.addAttribute("freeBrdList",freeBrdList);
		
		// ******************* 페이징 처리 ****************************************//
//		List<FreeBrdDto> freeBrdList = freeBrdService.allFreeBrd(startIndex,pageSize);
//		
//		model.addAttribute("pagination",pagination);
//		model.addAttribute("freeBrdList",freeBrdList);
		// ******************* 페이징 처리 ****************************************//
		
		
		return "freeBrd/freeBrdList";
	}
		
	//해당 게시글 세부 목록 보기(게시글 제목을 클릭했을때 조회수 증가가 이루어짐) 글번호는 int로 받았는데 String으로 해도 되네??? 
	@RequestMapping("freeBrdDetail")
	public String FreeBrdDetail(HttpServletRequest request,String seq,Model model) {
	
		FreeBrdDTO freeBrdDetail = null;
		try {
			freeBrdService.freeBrdHit(seq);
			freeBrdDetail = freeBrdService.freebrdDetail(seq);
		}catch (Exception e) {
			System.out.println("에러발생...");
		    System.out.println(e.getMessage());
		}
		
		//로그인한 해당 유저의 정보를 뽑아올수 있음  
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		String user_email = user.getUser_email();
		
		
		model.addAttribute("user",user_email);
		model.addAttribute("freeBrdDetail",freeBrdDetail);
		
		//System.out.println("자유게시판 목록페이지로 이동");
		
		return "freeBrd/freeBrdDetail";
	}
	
	//게시글 등록페이지로 이동하기 (GET 방식)	
	@RequestMapping(value="freeBrdForm",method=RequestMethod.GET)
	public String freeBrdForm() {
		return "freeBrd/freeBrdForm";
	}
	
	//글쓰기 처리(POST 방식)	
	@RequestMapping(value="freeBrdForm",method=RequestMethod.POST)
	public String freeBrdFormInsert(FreeBrdDTO n, HttpServletRequest request,MultipartFile image,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		
		String url="redirect:freeBrd/freeBrdForm";
	
		//트랜잭션 처리 .... 코드 수정...... 글쓰기 처리 서비스 호출 
		try {
					url = freeBrdService.freeBrdFormInsert(n, request, image,redirectAttributes);
		}catch (Exception e) {
					System.out.println("에러발생...");
				    System.out.println(e.getMessage());
		}
		
		//예외 발생에 상관없이 목록 페이지 새로고침 처리
		return url;

	}
	
	//글수정하기 (화면 단)
	@RequestMapping(value="freeBrdEdit", method=RequestMethod.GET)
	public String freeBrdEdit(String seq, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("글번호:"+seq);
		
		FreeBrdDTO freeBrdEdit = freeBrdService.freeBrdEdit(seq);

		model.addAttribute("freeBrdEdit",freeBrdEdit);
		return   "freeBrd/freeBrdEdit";//"noticeEdit.jsp";
	}
	
	
	//글수정하기(처리 단) 뷰단에서 올린 파일을 받아올라면 (MultipartFile image) 이걸써줘서 받아줘야됨
	@RequestMapping(value="freeBrdEdit", method=RequestMethod.POST)
	public String freeBrdEditOk(FreeBrdDTO n,String seq, HttpServletRequest request,MultipartFile image,RedirectAttributes redirectAttributes) throws IOException, ClassNotFoundException, SQLException {

		System.out.println("카테고리번호"+request.getParameter("freeBrdCat"));
		System.out.println("글제목"+request.getParameter("title"));
		System.out.println("글내용"+request.getParameter("content"));
		System.out.println("글번호"+request.getParameter("seq"));
		
		//파일 업로드시 아무것도 넣지 않으면 "" 공백이 들어감 
		if(image.getOriginalFilename() == null || image.getOriginalFilename() == "") {
			System.out.println("기존에 올린파일"+request.getParameter("image_2"));
		}else {
			System.out.println("올린첨부파일"+image.getOriginalFilename());
		}
		
		String url="redirect:freeBrd/freeBrdEdit";
		
		//트랜잭션 처리 .... 코드 수정...... 글수정 서비스 호출 
		try {
					url = freeBrdService.freeBrdEditOk(n,seq,request,image);
					redirectAttributes.addAttribute("seq",seq);
		}catch (Exception e) {
					System.out.println("에러발생...");
				    System.out.println(e.getMessage());
		}
		
		//예외 발생에 상관없이 목록 페이지 새로고침 처리
		return url;
	}
	
	
	
	//글삭제하기 
	@RequestMapping("freeBrdDelete")
	public String freeBrdDelete(String seq) {
		
		String url="redirect:freeBrd/freeBrdList";
		
		//트랜잭션 처리 .... 코드 수정...... 글 삭제 서비스 호출
		try {
					url = freeBrdService.freeBrdDelete(seq);
		}catch (Exception e) {
					System.out.println("에러발생...");
				    System.out.println(e.getMessage());
		}
		
		//예외 발생에 상관없이 목록 페이지 새로고침 처리
		return url;
	}
	
	
	
	
}
