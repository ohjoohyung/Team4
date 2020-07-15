package kr.or.bodiary.qnaBrd.controller;

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
	public String adminExcsDetail(String qna_brd_seq, Model model) {
		int seq = Integer.parseInt(qna_brd_seq);
		QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
		model.addAttribute("qna", qna);
		System.out.println(qna);
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
		
	@RequestMapping(value="/myQnaForm", method=RequestMethod.GET)
	public String myQnaForm() {
		System.out.println("myQnaForm 컨트롤러탐");
		return "myBodiary/myQnaForm";
	}
	@RequestMapping(value="/myQnaForm", method=RequestMethod.POST)
	public String adminExcsFormOK(QnaBrdDto QnaBrdDto, HttpServletRequest request) throws Exception {
		qnabrdservice.qnaInsert(QnaBrdDto, request);
		return "redirect:adminExcsList";
	}
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
