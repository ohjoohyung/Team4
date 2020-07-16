package kr.or.bodiary.freeBrd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.or.bodiary.freeBrd.dto.FreeBrdReplyDTO;
import kr.or.bodiary.freeBrd.service.FreeBrdReplyService;

@RestController
@RequestMapping("/freeReply/*")
public class FreeBrdReplyController {
	
	private FreeBrdReplyService freeBrdCmtService;
	
	@Autowired
	public void setFreeBrdService(FreeBrdReplyService freeBrdCmtService) {
		this.freeBrdCmtService = freeBrdCmtService;
	}
	
	//댓글 목록 
	@RequestMapping("list")
	public ModelAndView list(@RequestParam("seq") String seq,ModelAndView mav) throws Exception {
		System.out.println("댓글목록글번호"+seq);
		
		List<FreeBrdReplyDTO> list = freeBrdCmtService.list(seq);
		
		//System.out.println("자유게시판 목록페이지로 이동");	

		//이동시킬 뷰에 전달할 데이터 지정
		//mv.addAttribute("list",list);
		
		//return "freeBrd/freeReplyList";
		
		mav.setViewName("freeBrd/freeReplyList");
		mav.addObject("list",list);
		
		return mav;
	}
	
	//댓글 INSERT 
	@RequestMapping("insert")
	public void insert(@RequestParam("seq") String seq,@RequestParam("replytext") String replytext,HttpServletRequest request) throws Exception {
		
		System.out.println("댓글 입력시 글번호 출력+"+seq);
		System.out.println("댓글내용+"+replytext);
		
		freeBrdCmtService.create(seq,replytext,request);

	}
	
	//댓글 Delete 
	@RequestMapping("delete")
	public void delete(@RequestParam("seq") String seq,@RequestParam("cmt") String cmt,HttpServletRequest request) throws Exception {
		
		System.out.println("댓글 원 게시글 번호"+seq);
		System.out.println("댓글 번호"+cmt);
		
		freeBrdCmtService.delete(seq,cmt,request);

	}
}




















