package kr.or.bodiary.freeBrd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.or.bodiary.freeBrd.dto.FreeBrdReplyDTO;
import kr.or.bodiary.freeBrd.service.FreeBrdReplyService;

@Controller
@RequestMapping("/comment/*")
public class FreeBrdReplyController {
	
	private FreeBrdReplyService freeBrdCmtService;
	
	@Autowired
	public void setFreeBrdService(FreeBrdReplyService freeBrdCmtService) {
		this.freeBrdCmtService = freeBrdCmtService;
	}
	
	//댓글 목록 
	@RequestMapping("list")
	@ResponseBody // @ResponseBody 란? VO(List<FreeBrdReplyDTO>)객체를 JSON으로 바꿔서 HTTP body에 담는 스프링 어노테이션.
	public List<FreeBrdReplyDTO> list(@RequestParam("boardSeq") String boardSeq,ModelAndView mav) throws Exception {
		System.out.println("댓글목록글번호"+boardSeq);
		
	
		return freeBrdCmtService.list(boardSeq);
	}
	
	//댓글 INSERT 
	@RequestMapping("insert")
	@ResponseBody
	public int insert(@RequestParam("boardSeq") String boardSeq,@RequestParam("content") String content,HttpServletRequest request) throws Exception {
		
		System.out.println("댓글 입력시 글번호 출력+"+boardSeq);
		System.out.println("댓글내용+"+content);
		
		int result = freeBrdCmtService.create(boardSeq,content,request);
		System.out.println("insert 결과값(1이면 성공적)"+result);
		
		return result;
	}
	
	@RequestMapping("update") //댓글 수정  
    @ResponseBody
    private int mCommentServiceUpdateProc(@RequestParam int cno, @RequestParam String content) throws Exception{
		
		System.out.println("수정할 댓글번호"+cno);
		System.out.println("수정할 댓글내용"+content);
		
		FreeBrdReplyDTO comment = new FreeBrdReplyDTO();
		comment.setBrd_cmt(content);
		comment.setBrd_cmt_seq(cno);
		
        return freeBrdCmtService.commentUpdateService(comment);
    }

	//댓글 Delete 
	@RequestMapping("delete")
	@ResponseBody
	public int delete(@RequestParam("cno") int cno,HttpServletRequest request) throws Exception {

		System.out.println("---------------------------------삭제할 댓글 번호-----------------------------------"+cno);
		
		return freeBrdCmtService.delete(cno);

	}
}




















