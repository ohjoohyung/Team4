package kr.or.bodiary.freeBrd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.or.bodiary.freeBrd.dto.FreeBrdCmtDto;
import kr.or.bodiary.freeBrd.service.FreeBrdCmtService;
import kr.or.bodiary.freeBrd.service.FreeBrdService;

@RestController
@RequestMapping("/freeReply/*")
public class FreeBrdReplyController {
	
	private FreeBrdCmtService freeBrdCmtService;
	
	@Autowired
	public void setFreeBrdService(FreeBrdCmtService freeBrdCmtService) {
		this.freeBrdCmtService = freeBrdCmtService;
	}
	
	//댓글 목록 
	@RequestMapping(value="list",method=RequestMethod.GET)
	public ModelAndView list(@RequestParam("seq") String seq,ModelAndView mav) throws Exception {
		System.out.println("댓글목록글번호"+seq);
		
		List<FreeBrdCmtDto> list = freeBrdCmtService.list(seq);
		
		//System.out.println("자유게시판 목록페이지로 이동");	

		//이동시킬 뷰에 전달할 데이터 지정
		//mv.addAttribute("list",list);
		
		//return "freeBrd/freeReplyList";
		
		mav.setViewName("freeBrd/freeReplyList");
		mav.addObject("list",list);
		
		return mav;
	}
	
	//댓글 INSERT 
	@RequestMapping(value="insert",method = RequestMethod.POST)
	public void insert(@RequestParam("seq") String seq,@RequestParam("replytext") String replytext) throws Exception {
		
		System.out.println("댓글 입력시 글번호 출력+"+seq);
		System.out.println("댓글내용+"+replytext);
		
		freeBrdCmtService.create(seq,replytext);
		
	}
}






















