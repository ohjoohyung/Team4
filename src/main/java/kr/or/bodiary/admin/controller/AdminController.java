package kr.or.bodiary.admin.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bodiary.admin.service.ExerciseService;
import kr.or.bodiary.exercise.dto.ExerciseDto;
import kr.or.bodiary.notice.dto.NoticeDto;
import kr.or.bodiary.notice.service.NoticeService;

@Controller
public class AdminController {

	private ExerciseService exerciseservice;
	@Autowired
	public void setExerciseService(ExerciseService exerciseservice) {
		this.exerciseservice = exerciseservice;
	}
	@Autowired
	private NoticeService noticeservice;
	
	public void setNoticeService(NoticeService noticeservice) {
		this.noticeservice = noticeservice;
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
		ExerciseDto exercise = exerciseservice.exerciseDetail(seq);
		model.addAttribute("exercise", exercise);
		System.out.println(exercise);
		return "admin/adminExcsDetail";
	}

	@RequestMapping(value="/adminExcsEdit", method=RequestMethod.GET)
	public String adminExcsEdit(String excs_seq, Model model) {
		int seq = Integer.parseInt(excs_seq);
		System.out.println("adminExcsEdit탐");
		ExerciseDto exercise = exerciseservice.exerciseDetail(seq);
		model.addAttribute("exercise", exercise);
		System.out.println(exercise);
		return "admin/adminExcsEdit";
	}
	@RequestMapping(value="/adminExcsEdit", method=RequestMethod.POST)
	public String adminExcsEditOK(ExerciseDto ExerciseDto, HttpServletRequest request) throws ClassNotFoundException, IOException, SQLException {
		return exerciseservice.exerciseEdit(ExerciseDto, request);
	}
	@RequestMapping(value="/adminExcsDelete", method=RequestMethod.GET)
	public String adminExcsDelete(String excs_seq, Model model) throws ClassNotFoundException, SQLException {
		int seq = Integer.parseInt(excs_seq);
		return exerciseservice.exerciseDelete(seq);
	}

	@RequestMapping(value="/adminExcsForm", method=RequestMethod.POST)
	public String adminExcsFormOK(ExerciseDto ExerciseDto, HttpServletRequest request) throws Exception {
		exerciseservice.exerciseInsert(ExerciseDto, request);
		return "redirect:adminExcsList";
	}
	@RequestMapping(value="/adminExcsForm", method=RequestMethod.GET)
	public String adminExcsForm() {
		return "admin/adminExcsForm";
	}

	@RequestMapping("/adminExcsList")
	public String adminExcsList( Model model) {
		List<ExerciseDto> list = exerciseservice.exercises();
		model.addAttribute("list", list);
		return "admin/adminExcsList";
	}
	
	    
	@RequestMapping("/adminReportList")
	public String adminReportList() {
		return "admin/adminReportList";
	}

	//공지사항
		//리스트
		@RequestMapping("noticeList")
		public String noticeList(Model model) throws ClassNotFoundException, SQLException {
			List<NoticeDto> nlist = noticeservice.noticeList();
			model.addAttribute("noticeList", nlist);
			return "notice/noticeList";
		}	
		
		//입력(폼)
		@RequestMapping(value = "noticeForm", method = RequestMethod.GET)
		public String noticeForm() {
			return "notice/noticeForm";
		}
		
		//입력(처리)
		@RequestMapping(value = "noticeForm", method = RequestMethod.POST)
		public String noticeForm(NoticeDto noticedto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
			System.out.println("noticeForm, POST방식");
			String url = noticeservice.noticeForm(noticedto, request);
			return url;
		}
		
		//상세
		@RequestMapping("noticeDetail")
		public String noticeDetail(int notice_brd_seq, Model model, HttpServletRequest request) throws ClassNotFoundException, SQLException {
			NoticeDto noticedto = noticeservice.noticeDetail(notice_brd_seq);
			System.out.println(noticedto);
			model.addAttribute("noticeDetail", noticedto);
			return "notice/noticeDetail";
		}
		
		//수정(폼)
		@RequestMapping(value = "noticeEdit", method = RequestMethod.GET)
		public String noticeEdit(int notice_brd_seq, Model model) throws ClassNotFoundException, SQLException {
			NoticeDto noticedto = noticeservice.noticeDetail(notice_brd_seq);
			model.addAttribute("noticeEdit", noticedto);
			return "notice/noticeEdit";
		}
		
		//수정(처리)
		@RequestMapping(value = "noticeEdit", method = RequestMethod.POST)
		public String noticeEdit(NoticeDto noticedto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
			return noticeservice.noticeEdit(noticedto, request);
		}
		
		//삭제
		@RequestMapping("noticeDelete")
		public String noticeDelete(int notice_brd_seq) throws ClassNotFoundException, SQLException {
			noticeservice.noticeDelete(notice_brd_seq);
			return "redirect:noticeList";
		}
		
}
