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
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bodiary.admin.service.ChartService;
import kr.or.bodiary.admin.service.ExerciseService;
import kr.or.bodiary.exercise.dto.ExerciseDto;
import kr.or.bodiary.notice.dto.NoticeDto;
import kr.or.bodiary.notice.service.NoticeService;
import kr.or.bodiary.user.dto.UserDto;

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
	
	@Autowired
	private ChartService chartService;
	
	public void setChartService(ChartService chartService) {
		this.chartService = chartService;
	}

	@RequestMapping("/admin")
	public String adminDashBrd() {
		return "admin/adminDashBrd";
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
		
//		@RequestMapping("noticeList")
//		public String noticeList(String ps, String cp, Model model) {
//			//List 페이지
//			if(ps == null || ps.trim().equals("")) {
//				//default 값 설정
//				ps = "5"; //5개씩
//			}
	//	
//			if(cp == null || cp.trim().equals("")) {
//				//default 값 설정
//				cp = "1"; //첫페이지
//			}
//			
//			int pagesize = Integer.parseInt(ps);
//			int cpage = Integer.parseInt(cp);
//			int pagecount = 0;
//			
//			try {
//				NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
//				List<NoticeDto> nlist = noticedao.noticeList(cpage, pagesize);
//				
//				int totalcount = noticedao.totalCount();
//				if(totalcount % pagesize == 0) {
//					pagecount = totalcount / pagesize;
//				} else {
//					pagecount = (totalcount / pagesize) + 1; 
//				}
//				model.addAttribute("noticeList", nlist);
//				model.addAttribute("pageSize", pagesize);
//				model.addAttribute("cPage", cpage);
//				model.addAttribute("pageCount", pagecount);
//				model.addAttribute("totalCount", totalcount);
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//			}
//			return "notice/noticeList";
//		}
			
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
			noticeservice.noticeHitCnt(notice_brd_seq);
//			//조회수 증가
//			UserDto userdto = (UserDto)request.getSession().getAttribute("currentUser");
//			System.out.println("유저의 이메일 : " + userdto.getUser_email());
//			System.out.println("지금 글의 작성자 이메일 : " + noticedto.getUser_email());
//			if(userdto != null && !(userdto.getUser_email().equals(noticedto.getUser_email()))) {
//				noticeservice.noticeHitCnt(notice_brd_seq);
//			}
//			//
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
		//오늘 가입자수
		@ResponseBody
		@RequestMapping("todayUserCount")
		public int newUserCount(UserDto user, HttpServletRequest request) throws ClassNotFoundException, SQLException {
			System.out.println("차트 컨트롤러");
			
			return chartService.todayUserCount(user, request);
		}
		
		@ResponseBody
		@RequestMapping("selectUserCount")
		public int selectUserCount() throws ClassNotFoundException, SQLException {
			System.out.println("회원수 알려주세요");
			
			return chartService.selectUserCount();
		}
		//남녀 성비
		@ResponseBody
		@RequestMapping("genderPer")
		public String genderPer(UserDto user, HttpServletRequest request) throws ClassNotFoundException, SQLException {
			System.out.println("남녀 성비 알려주세요");
			
			return chartService.genderPer();
		}
		
		
		
	}
