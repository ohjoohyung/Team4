package kr.or.bodiary.admin.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bodiary.admin.service.ChartService;
import kr.or.bodiary.admin.service.ExerciseService;
import kr.or.bodiary.exercise.dto.ExerciseDto;
import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.notice.dto.NoticeDto;
import kr.or.bodiary.notice.service.NoticeService;
import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;
import kr.or.bodiary.user.dto.UserDto;
import kr.or.bodiary.user.service.UserService;
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

		@Autowired
		private UserService userService;
		
		public void setUserService(UserService userService) {
			this.userService = userService;
		}
		

		//유저 관리 페이지 
		@RequestMapping("/adminUserBrdList")
		public String adminUserBrdList(Model model) {
			System.out.println("유저관리 페이지로 이동............");
			//모든 유저를 가져오는 서비스 호출 
			List<UserDto> userList = userService.getUserList();
			model.addAttribute("userList",userList);
			
			return "admin/adminUserBrdList";
		}
		
		//유저 권한 수정 컨트롤러 
		@RequestMapping("/adminRoleUpdate")
		public String adminRoleUpdate(String role,String email) {
			System.out.println("수정할 권한 "+role);
			System.out.println("수정할 권한 이메일 "+email);
			System.out.println("해당 유저 권한 수정중 ............");
			
			//해당 유저의 권한을 수정하는 서비스 호출 
			int result = userService.userRoleUpdate(role,email);
			
			if(result == 1) System.out.println("수정성공(result값이 1이라면)"+result);

			
			return "redirect:adminUserBrdList";
		}
		
		//해당 유저 디테일 페이지
		@RequestMapping("/adminUserBrdDetail")
		public String adminUserBrdDetail(String userEmail,Model model) throws ClassNotFoundException, SQLException {
			System.out.println("해당 유저 세부 페이지로 이동............");
			
			//해당유저의 전체 자유게시물 개수 
			int totalFreeBrdCount = userService.freeBrdCount(userEmail);
			//해당 유저의 전체 루틴자랑 게시물 개수 
			int totalRoutineBrdCount = userService.routineBrdCount(userEmail);		
					
			UserDto getUser = null;
			List<FreeBrdDTO> UserFreeBrdList = null;
			List<RoutineBrdDto> UserRoutineBrdList = null;
			try {		
				getUser = userService.getUser(userEmail);
				//해당 유저의 모든 자유 게시글을 얻어오는 서비스 호출 
				UserFreeBrdList = userService.getUserFreeBrdList(userEmail);
				//해당 유저의 모든 루틴 자랑 게시글을 얻어오는 서비스 호출 
				UserRoutineBrdList = userService.getUserRoutineBrdList(userEmail);
			}catch (Exception e) {
				System.out.println("에러발생...");
			    System.out.println(e.getMessage());
			}
			
			model.addAttribute("UserRoutineBrdList",UserRoutineBrdList);
			model.addAttribute("UserFreeBrdList",UserFreeBrdList);
			model.addAttribute("routineBrdCount",totalRoutineBrdCount);
			model.addAttribute("freeBrdCount",totalFreeBrdCount);
			model.addAttribute("getUser",getUser);
			
			return "admin/adminUserBrdDetail";
		}
		
		//동률 -------------------------------------------------------------------

	
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
		public String noticeList(Model model, 
								@RequestParam(defaultValue="1") int curPage, 
								@RequestParam(defaultValue = "5") int pageSize) throws ClassNotFoundException, SQLException {
			return noticeservice.noticeList(model, curPage, pageSize);
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
//		noticeservice.noticeHitCnt(notice_brd_seq);
		NoticeDto noticedto = noticeservice.noticeDetail(notice_brd_seq);
		//조회수 증가
		UserDto userdto = (UserDto)request.getSession().getAttribute("currentUser");
		System.out.println("유저의 이메일 : " + userdto.getUser_email());
		System.out.println("지금 글의 작성자 이메일 : " + noticedto.getUser_email());
		if(userdto != null && !(userdto.getUser_email().equals(noticedto.getUser_email()))) {
			noticeservice.noticeHitCnt(notice_brd_seq);
		}
		//
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
    //탈퇴한회원수
    @ResponseBody
    @RequestMapping("userDelete")
    public int userDelete(UserDto user, HttpServletRequest request) throws ClassNotFoundException, SQLException {
       
       return chartService.deleteUserCount();
    }
    
    //탈퇴한회원수
    @ResponseBody
    @RequestMapping("countReport")
    public int countReport(UserDto user, HttpServletRequest request) throws ClassNotFoundException, SQLException {
       
       return chartService.countReport();
    }
    //일일가입한
    @ResponseBody
    @RequestMapping("todayUser")
    public int todayUser(UserDto user, HttpServletRequest request) throws ClassNotFoundException, SQLException {
       
       return chartService.todayUser();
    }
    //updating chart
    @ResponseBody
    @RequestMapping("updatingChart")
    public int updatingChart(UserDto user, HttpServletRequest request) throws ClassNotFoundException, SQLException {
       
       return chartService.updatingChart();
    }
    //monthlyCount chart
    @ResponseBody
    @RequestMapping("monthlyCount")
    public ArrayList<Integer> monthlyCount(UserDto user, HttpServletRequest request) throws ClassNotFoundException, SQLException {
       
       return chartService.monthlyCount();
    }
    
  //썸머노트 파일 업로드
    @ResponseBody
	@RequestMapping("/summerImageNotice")
    public void summerImage(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	response.setContentType("text/html;charset=utf-8");
    	PrintWriter out = response.getWriter();
    	// 업로드할 폴더 경로
    	String realFolder = request.getSession().getServletContext().getRealPath("/assets/upload/noticeUpload");
    	UUID uuid = UUID.randomUUID();

    	// 업로드할 파일 이름
    	String org_filename = file.getOriginalFilename();
    	String str_filename = uuid.toString() + org_filename;

    	System.out.println("원본 파일명 : " + org_filename);
    	System.out.println("저장할 파일명 : " + str_filename);

    	String filepath = realFolder +  "\\" + str_filename;
    	System.out.println("파일경로 : " + filepath);

    	File f = new File(filepath);
    	if (!f.exists()) {
    	f.mkdirs();
    	}
    	file.transferTo(f);
    	out.println("assets/upload/noticeUpload/"+str_filename);
    	out.close();
    	}
			
		
	}
