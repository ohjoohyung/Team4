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
import kr.or.bodiary.exercise.dto.exerciseDTO;
import kr.or.bodiary.qnaBrd.dto.QnaBrdDto;
import kr.or.bodiary.qnaBrd.service.QnaBrdService;
import kr.or.bodiary.user.dto.UserDto;


@Controller
public class QnaBrdController {
	private QnaBrdService qnabrdservice;
	@Autowired
	public void setExerciseService(ExerciseService exerciseservice) {
		this.qnabrdservice = qnabrdservice;
	}
	@RequestMapping("/myQnaList")
	public String getMyQnaList(Model model, HttpServletRequest request) {
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
	    String user_email = user.getUser_email();
	    List<QnaBrdDto> list = qnabrdservice.qnalists(user_email);
		model.addAttribute("list", list);
		return "myBodiary/myQnaList";
	}

	@RequestMapping("/myQnaDetail")
	public String getMyQnaDetail() {
		return "myBodiary/myQnaDetail";
	}

	@RequestMapping("/myQnaEdit")
	public String getMyQnaEdit() {
		return "myBodiary/myQnaEdit";
	}
		
	@RequestMapping(value="/myQnaForm")
	public String myQnaForm() {
		return "mybodiary/myQnaForm";
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
