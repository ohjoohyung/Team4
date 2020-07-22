package kr.or.bodiary.routineBrd.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;
import kr.or.bodiary.myBodiary.service.BodiaryService;
import kr.or.bodiary.routineBrd.dto.RoutineBoardCommentDto;
import kr.or.bodiary.routineBrd.dto.RoutineBoardUserJoinDto;
import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;
import kr.or.bodiary.routineBrd.service.RoutineBrdService;
import kr.or.bodiary.user.dto.UserDto;


@Controller
public class RoutineBrdController {
   
	@Autowired
	private RoutineBrdService routinebrdservice;
	
	public void setRoutineBrdservice(RoutineBrdService routinebrdservice) {
		this.routinebrdservice = routinebrdservice;
	}
	
	@Autowired
	private BodiaryService bodiaryservice;
	public void setRoutineBrdservice(BodiaryService bodiaryservice) {
		this.bodiaryservice = bodiaryservice;
	}
	
	//리스트 비동기로 불러오기
	@ResponseBody
	@RequestMapping("/getRoutineBrdList")
	public List<RoutineBoardUserJoinDto> getRoutineBrdList(Model model, String cp, String ps) throws ClassNotFoundException, SQLException, ParseException {
		List<RoutineBoardUserJoinDto> rlist = routinebrdservice.routineBoardList(cp, ps);
		/* model.addAttribute("routineBoardList", rlist); */
		return rlist;
	}
	
	//리스트 페이지 들어가기
	@RequestMapping("/routineBrdList")
	public String routineBrdList(Model model) throws ClassNotFoundException, SQLException, ParseException {
		List<RoutineBoardUserJoinDto> todayHit = routinebrdservice.getTodayHit();
		model.addAttribute("todayHit", todayHit);
		return "routineBrd/routineBrdList";
	}

	
	
	
	//상세
	@RequestMapping("/routineBrdDetail")
	public String routineBrdDetail(int routine_brd_seq, Model model, HttpServletRequest request) throws ClassNotFoundException, SQLException, ParseException {
		RoutineBrdDto routinebrddto = routinebrdservice.routineBrdDetail(routine_brd_seq);
		List<RoutineJoinDto> routine = bodiaryservice.getRoutine(routinebrddto.getRoutine_cart_seq());
		
		System.out.println(routinebrddto);
		
		
		
		//분을 시간으로 변환
		  for(RoutineJoinDto r : routine) {
			if(r.getExcs_kind().equals("C")) { 
				String hour = ""; 
				if((Integer.parseInt(r.getRoutine_exercise_hour()) % 60) == 0) { 
					hour = (Integer.parseInt(r.getRoutine_exercise_hour()) / 60) + "시간"; 
				} else { 
					if((Integer.parseInt(r.getRoutine_exercise_hour()) / 60) == 0) { 
					hour = r.getRoutine_exercise_hour() + "분"; 
				}else { 
					hour = (Integer.parseInt(r.getRoutine_exercise_hour()) / 60) + "시간 30분"; 
					} 
				} r.setRoutine_exercise_hour(hour); 
			
				} 
			}
		
		//조회수 증가
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		System.out.println("유저의 이메일 : " + user.getUser_email());
		System.out.println("지금 글의 작성자 이메일 : " + routinebrddto.getUser_email());
		if(user != null && !(user.getUser_email().equals(routinebrddto.getUser_email()))) {
			routinebrdservice.updateHit(routine_brd_seq);
		}
		
		
		model.addAttribute("routineBoard", routinebrddto);
		model.addAttribute("routine", routine);
		return "routineBrd/routineBrdDetail";
	}
	
	//입력(폼)
	@RequestMapping(value = "routineBrdInsert", method = RequestMethod.GET)
	public String routineBrdInsert(Model model, HttpServletRequest request) throws ClassNotFoundException, SQLException {
		
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		String user_email = user.getUser_email();

		System.out.println("유저 정보 : " +user_email);
		List<RoutineJoinDto> list = bodiaryservice.getRoutineListById(user_email);
		model.addAttribute("routineList", list);
		return "routineBrd/routineBrdForm";
	}
	
	//입력(처리)
	@RequestMapping(value = "routineBrdInsert", method = RequestMethod.POST)
	public String routineBrdInsert(RoutineBrdDto routinebrddto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
		String url = routinebrdservice.routineBrdInsert(routinebrddto, request);
		return url;
	}
	
	//수정(폼)
	@RequestMapping(value = "routineBrdEdit", method = RequestMethod.GET)		
	public String routineBrdEdit(int routine_brd_seq, Model model, HttpServletRequest request) throws ClassNotFoundException, SQLException, ParseException {
		RoutineBrdDto routinebrddto = routinebrdservice.routineBrdDetail(routine_brd_seq);
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		String user_email = user.getUser_email();

		System.out.println("유저 정보 : " +user_email);
		List<RoutineJoinDto> list = bodiaryservice.getRoutineListById(user_email);
		model.addAttribute("routineList", list);
		model.addAttribute("routineBoard", routinebrddto);
		
		return "routineBrd/routineBrdEdit";
	}
	
	//수정(처리)
	@RequestMapping(value = "routineBrdEdit", method = RequestMethod.POST)
	public String routineBrdEdit(RoutineBrdDto routinebrddto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
		String url = routinebrdservice.routineBrdEdit(routinebrddto, request);
		return url;
	}
	
	//삭제
	@RequestMapping("/routineBrdDelete")
	public String routineBrdDelete(int routine_brd_seq) throws ClassNotFoundException, SQLException {
		
		return routinebrdservice.routineBoardDelete(routine_brd_seq);
	}	
   
   

	// ------------------------ 댓글 ------------------------

		// 댓글 불러오기
		@ResponseBody
		@RequestMapping(value = "routineCmtList", method = RequestMethod.POST)
		public List<RoutineBoardCommentDto> routineCmtList(HttpServletRequest request)  throws IOException, ClassNotFoundException, SQLException {
			return routinebrdservice.routineCmtList(Integer.parseInt(request.getParameter("routine_brd_seq")));
		}

		// 댓글 인서트 나중에 리턴값 리팩토링해야함 댓글 불러오기 함수 넣어서...
		@ResponseBody
	   @RequestMapping(value = "routineCmtInsert", method = RequestMethod.POST)
	   public List<RoutineBoardCommentDto> routineCmtInsert(RoutineBoardCommentDto routineCmtDto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException, ParseException {
		   System.out.println("댓글 인서트 컨트롤러 탐");
		   System.out.println(request.getParameter("routine_brd_seq"));
		   System.out.println(request.getParameter("routine_cmt"));
		   System.out.println(request.getParameter("user_email"));
		   
		   return routinebrdservice.routineCmtInsert(routineCmtDto, request);
	   }
		
		@ResponseBody
		@RequestMapping(value = "routineReCmtInsert", method = RequestMethod.POST)
		public List<RoutineBoardCommentDto> routineReCmtInsert(RoutineBoardCommentDto routineCmtDto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException, ParseException {
			System.out.println("대댓글 인서트 컨트롤러 탐");
		
			return routinebrdservice.routineReCmtInsert(routineCmtDto, request);
		}
		@ResponseBody
		@RequestMapping(value = "routineCmtModifyUpdate", method = RequestMethod.POST)
		public List<RoutineBoardCommentDto> routineCmtModifyUpdate (RoutineBoardCommentDto routineCmtDto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException, ParseException {
			
			return routinebrdservice.routineCmtModifyUpdate(routineCmtDto, request);
		}
		@ResponseBody
		@RequestMapping(value = "routineCmtDelete", method = RequestMethod.POST)
		public List<RoutineBoardCommentDto> routineCmtDelete (RoutineBoardCommentDto routineCmtDto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException, ParseException {
			
			return routinebrdservice.routineCmtDelete(routineCmtDto, request);
		}
   
}