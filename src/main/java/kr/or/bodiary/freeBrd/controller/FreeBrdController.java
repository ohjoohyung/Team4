package kr.or.bodiary.freeBrd.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bodiary.freeBrd.dto.FreeBrdDto;
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
	public String AllFreeBrdList(Model model) {
		
		List<FreeBrdDto> freeBrdList = freeBrdService.allFreeBrd();
		model.addAttribute("freeBrdList",freeBrdList);
		
		System.out.println("자유게시판 목록페이지로 이동");
		
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
	public String freeBrdFormInsert(FreeBrdDto n, HttpServletRequest request,MultipartFile image) throws UnsupportedEncodingException {
		
		String url="redirect:freeBrd/freeBrdForm";
	
		//트랜잭션 처리 .... 코드 수정...... 글쓰기 처리 서비스 호출 
		try {
					url = freeBrdService.freeBrdFormInsert(n, request, image);
		}catch (Exception e) {
					System.out.println("에러발생...");
				    System.out.println(e.getMessage());
		}
		
		//예외 발생에 상관없이 목록 페이지 새로고침 처리
		return url;

	}
	
}
































