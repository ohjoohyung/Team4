package kr.or.bodiary.admin.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mysql.cj.ParseInfo;

import kr.or.bodiary.admin.service.ExerciseService;
import kr.or.bodiary.exercise.dto.exerciseDTO;

@Controller
public class AdminController {

	private ExerciseService exerciseservice;
	@Autowired
	public void setExerciseService(ExerciseService exerciseservice) {
		this.exerciseservice = exerciseservice;
	}

	
	@RequestMapping("/admin")
	public String adminDashBrd() {
		return "admin/adminDashBrd";
	}

	@RequestMapping("/adminQnaList")
	public String adminQnaList() {
		return "admin/adminQnaList";
	}

	@RequestMapping("/adminQnaDetail")
	public String adminQnaDetail() {
		return "admin/adminQnaDetail";
	}

	@RequestMapping("/adminUserBrdList")
	public String adminUserBrdList() {
		return "admin/adminUserBrdList";
	}

	// 어드민 운동 CRUD
	@RequestMapping(value="/adminExcsDetail", method=RequestMethod.GET)
	public String adminExcsDetail(String excs_seq, Model model) {
		int seq = Integer.parseInt(excs_seq);
		exerciseDTO exercise = exerciseservice.exerciseDetail(seq);
		model.addAttribute("exercise", exercise);
		System.out.println(exercise);
		return "admin/adminExcsDetail";
	}

	@RequestMapping(value="/adminExcsEdit", method=RequestMethod.GET)
	public String adminExcsEdit(String excs_seq, Model model) {
		int seq = Integer.parseInt(excs_seq);
		System.out.println("adminExcsEdit탐");
		exerciseDTO exercise = exerciseservice.exerciseDetail(seq);
		model.addAttribute("exercise", exercise);
		System.out.println(exercise);
		return "admin/adminExcsEdit";
	}
	@RequestMapping(value="/adminExcsEdit", method=RequestMethod.POST)
	public String adminExcsEditOK(exerciseDTO exercisedto, HttpServletRequest request) throws ClassNotFoundException, IOException, SQLException {
		return exerciseservice.exerciseEdit(exercisedto, request);
	}
	@RequestMapping(value="/adminExcsDelete", method=RequestMethod.GET)
	public String adminExcsDelete(String excs_seq, Model model) throws ClassNotFoundException, SQLException {
		int seq = Integer.parseInt(excs_seq);
		return exerciseservice.exerciseDelete(seq);
	}

	@RequestMapping("/adminExcsForm")
	public String adminExcsForm() {
		return "admin/adminExcsForm";
	}

	@RequestMapping("/adminExcsList")
	public String adminExcsList( Model model) {
		List<exerciseDTO> list = exerciseservice.exercises();
		model.addAttribute("list", list);
		return "admin/adminExcsList";
	}
	
	    
	@RequestMapping("/adminReportList")
	public String adminReportList() {
		return "admin/adminReportList";
	}

	@RequestMapping("/noticeList")
	public String getNoticeList() {
		return "notice/noticeList";
	}

	@RequestMapping("/noticeDetail")
	public String getNoticeDetail() {
		return "notice/noticeDetail";
	}

	@RequestMapping("/noticeEdit")
	public String getNoticeEdit() {
		return "notice/noticeEdit";
	}

	@RequestMapping("/noticeForm")
	public String getNoticeForm() {
		return "notice/noticeForm";
	}
}
