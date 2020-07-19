package kr.or.bodiary.qnaBrd.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bodiary.admin.service.ExerciseService;
import kr.or.bodiary.exercise.dto.ExerciseDto;
import kr.or.bodiary.qnaBrd.dto.QnaBrdDto;
import kr.or.bodiary.qnaBrd.service.QnaBrdService;
import kr.or.bodiary.user.dto.UserDto;


@Controller
public class QnaBrdController {
	private QnaBrdService qnabrdservice;
	@Autowired
	public void setQnabrdservice(QnaBrdService qnabrdservice) {
		this.qnabrdservice = qnabrdservice;
	}
	//유저 문의게시판//
	//리스트
	@RequestMapping("/myQnaList")
	public String getMyQnaList(Model model, HttpServletRequest request) {
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
	    String user_email = user.getUser_email();
	    System.out.println(user_email);
	    List<QnaBrdDto> list = qnabrdservice.qnalists(user_email);
		model.addAttribute("list", list);
		return "myBodiary/myQnaList";
	}

	@RequestMapping(value="/myQnaDetail", method=RequestMethod.GET)
	public String myQnaDetail(String qna_brd_seq, Model model) {
		int seq = Integer.parseInt(qna_brd_seq);
		QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
		model.addAttribute("qna", qna);
		List<QnaBrdDto> list = qnabrdservice.qnaBrdDetailAns(seq);
		model.addAttribute("list", list);
		return "myBodiary/myQnaDetail";
	}

	@RequestMapping(value="/myQnaEdit", method=RequestMethod.GET)
	public String qnaBrdEdit(String qna_brd_seq, Model model) {
		int seq = Integer.parseInt(qna_brd_seq);
		System.out.println("qnaBrdEdit탐");
		QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
		model.addAttribute("qna", qna);
		System.out.println(qna);
		return "myBodiary/myQnaEdit";
	}
	@RequestMapping(value="/myQnaEdit", method=RequestMethod.POST)
	public String qnaBrdEditOK(QnaBrdDto QnaBrdDto, HttpServletRequest request) throws ClassNotFoundException, IOException, SQLException {
		return qnabrdservice.qnaBrdEdit(QnaBrdDto, request);
	}
		
	@RequestMapping(value="/myQnaForm", method=RequestMethod.GET)
	public String myQnaForm() {
		System.out.println("myQnaForm 컨트롤러탐");
		return "myBodiary/myQnaForm";
	}

	/* 질문 insert 웹소켓으로 옮김
	 * @RequestMapping(value="/myQnaForm", method=RequestMethod.POST) public String
	 * myQnaFormOK(QnaBrdDto QnaBrdDto, HttpServletRequest request) throws Exception
	 * { UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
	 * String user_email = user.getUser_email();
	 * QnaBrdDto.setUser_email(user_email); qnabrdservice.qnaInsert(QnaBrdDto,
	 * request); return "redirect:myQnaList"; }
	 */
	
	//삭제
	@RequestMapping(value="/myQnaFormDelete", method=RequestMethod.GET)
	public String myQnaFormDelete(String qna_brd_seq, Model model) throws ClassNotFoundException, SQLException {
		int seq = Integer.parseInt(qna_brd_seq);
		return qnabrdservice.qndBrdDelete(seq);
	}
	
		//어드민 Qna시작
		@RequestMapping("/adminQnaList")
		public String adminQnaList(Model model, HttpServletRequest request) {
			UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		    String user_email = user.getUser_email();
		    List<QnaBrdDto> list = qnabrdservice.adminQnalists(user_email);
		    System.out.println(list);
			model.addAttribute("list", list);
			return "admin/adminQnaList";
		}
		//어드민 detail
		@RequestMapping(value="/adminQnaDetail", method=RequestMethod.GET)
		public String adminQnaDetail(String qna_brd_seq, Model model) {
			int seq = Integer.parseInt(qna_brd_seq);
			QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
			model.addAttribute("qna", qna);
			System.out.println(qna);
			List<QnaBrdDto> list = qnabrdservice.qnaBrdDetailAns(seq);
			model.addAttribute("list", list);
			return "admin/adminQnaDetail";
			
		}
		
		/*
		 * //어드민 답변완료 답변 insert 웹소켓으로 옮김
		 * 
		 * @RequestMapping(value="/adminQnaDetailOK", method=RequestMethod.POST) public
		 * String adminQnaAnsOK(QnaBrdDto QnaBrdDto, HttpServletRequest request) throws
		 * Exception { UserDto user =
		 * (UserDto)request.getSession().getAttribute("currentUser"); String user_email
		 * = user.getUser_email(); QnaBrdDto.setUser_email(user_email); return
		 * qnabrdservice.qnaAnsInsert(QnaBrdDto);
		 * 
		 * }
		 */
		
		@RequestMapping(value="/adminQnaModify", method=RequestMethod.POST)
		public String adminQnaModify(QnaBrdDto QnaBrdDto, Model model) {
			int seq = QnaBrdDto.getQna_brd_ref();
			QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
			model.addAttribute("qna", qna);
			System.out.println(qna);
			
			QnaBrdDto list = qnabrdservice.qnaBrdDetailAnsModify(QnaBrdDto);
			model.addAttribute("list", list);
			 
			return "admin/adminQnaModify";
		}
		@RequestMapping(value="/adminQnaModifyOK", method=RequestMethod.POST)
		public String adminQnaModifyOK(QnaBrdDto QnaBrdDto, HttpServletRequest request) throws ClassNotFoundException, IOException, SQLException {
			return qnabrdservice.adminQnaModifyOK(QnaBrdDto, request);
		}
		
		@RequestMapping(value="/adminQnaDelete", method=RequestMethod.GET)
		public String adminQnaDelete(String qna_brd_seq, Model model) throws ClassNotFoundException, SQLException {
			int seq = Integer.parseInt(qna_brd_seq);
			QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
			int qnago = qna.getQna_brd_ref();
			return qnabrdservice.qndBrdAnsDelete(seq,qnago);
		}
		
		@RequestMapping(value="/adminQnaModDelete", method=RequestMethod.GET)
		public String adminQnaModDelete(String qna_brd_seq, Model model) throws ClassNotFoundException, SQLException {
			int seq = Integer.parseInt(qna_brd_seq);
			System.out.println(seq);
			QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
			int qnago = qna.getQna_brd_ref();
			return qnabrdservice.qndBrdAnsDelete(seq,qnago);
		}
		
		//어드민 Qna끝
	/*
	 * @RequestMapping("/myQnaForm")
	 * 
	 * @ResponseBody public List<QnaBoardDTO> myQnaFormAdd(int excs_seq) throws
	 * ClassNotFoundException, SQLException{ System.out.println(excs_seq);
	 * exerciseDAO exercisedao = sqlsession.getMapper(exerciseDAO.class);
	 * List<exerciseDTO> elist = exercisedao.exerciseadd(excs_seq);
	 * System.out.println(elist.toString()); return elist; }
	 */
}
