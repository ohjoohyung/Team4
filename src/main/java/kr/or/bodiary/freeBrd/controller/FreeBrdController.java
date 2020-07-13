package kr.or.bodiary.freeBrd.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.bodiary.freeBrd.dto.FreeBrdDto;
import kr.or.bodiary.freeBrd.dto.Pagination;
import kr.or.bodiary.freeBrd.dto.Search;
import kr.or.bodiary.freeBrd.service.FreeBrdService;


@Controller
public class FreeBrdController {
	
	private FreeBrdService freeBrdService;
	
	@Autowired
	public void setFreeBrdService(FreeBrdService freeBrdService) {
		this.freeBrdService = freeBrdService;
	}
	
	
	//전체 게시글(자유,팁,궁금) 보기
	@RequestMapping("freeBrdList")
	public String AllFreeBrdList(Model model
								,@RequestParam(defaultValue = "1")int page
								,@RequestParam(required = false,defaultValue = "title")String searchType
								,@RequestParam(required = false,defaultValue = "0")int cateGory
								,@RequestParam(required = false)String keyword
								,@ModelAttribute("search") Search search
								) throws Exception {
		
		System.out.println("카테고리 번호"+cateGory);
		System.out.println("검색타입"+searchType);
		System.out.println("검색키워드"+keyword);
		
		/*
		//총 게시물수를 받아옴 
		int totalListCnt;
		//카테고리 별로 총게시물을 가져오는 숫자가 다름 
		if(cateGory == 0) {
			totalListCnt = freeBrdService.allFreeBrdCount();
		}else if(cateGory == 1) {
			totalListCnt = freeBrdService.getLibertyCnt(); //자유게시판 총 게시물 
		}else if(cateGory == 2) {
			totalListCnt = freeBrdService.getQuestionCnt(); //질문게시판 총 게시물 
		}else if(cateGory == 3) {
			totalListCnt = freeBrdService.getTipCnt(); //팁게시판 총 게시물 
		}
		*/
		
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
		
		List<FreeBrdDto> freeBrdList = freeBrdService.allFreeBrd(search);
		
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
		
	//해당 게시글 세부 목록 보기
	@RequestMapping("freeBrdDetail")
	public String FreeBrdDetail(String seq,Model model) {
		System.out.println("글번호:"+seq);
		
		FreeBrdDto freeBrdDetail = freeBrdService.freebrdDetail(seq);
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
	public String freeBrdFormInsert(FreeBrdDto n, HttpServletRequest request,MultipartFile image,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		
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
		
		FreeBrdDto freeBrdEdit = freeBrdService.freeBrdEdit(seq);

		model.addAttribute("freeBrdEdit",freeBrdEdit);
		return   "freeBrd/freeBrdEdit";//"noticeEdit.jsp";
	}
	
	
	//글수정하기(처리 단) 뷰단에서 올린 파일을 받아올라면 (MultipartFile image) 이걸써줘서 받아줘야됨
	@RequestMapping(value="freeBrdEdit", method=RequestMethod.POST)
	public String freeBrdEditOk(FreeBrdDto n,String seq, HttpServletRequest request,MultipartFile image,RedirectAttributes redirectAttributes) throws IOException, ClassNotFoundException, SQLException {

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

































