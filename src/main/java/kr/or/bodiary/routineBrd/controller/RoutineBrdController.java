package kr.or.bodiary.routineBrd.controller;

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
	public List<RoutineBoardUserJoinDto> getRoutineBrdList(Model model, String cp, String ps) throws ClassNotFoundException, SQLException {
		List<RoutineBoardUserJoinDto> rlist = routinebrdservice.routineBoardList(cp, ps);
		/* model.addAttribute("routineBoardList", rlist); */
		return rlist;
	}
	
	//리스트 페이지 들어가기
	@RequestMapping("/routineBrdList")
	public String routineBrdList() {
		return "routineBrd/routineBrdList";
	}
	
	
	
	//상세
	@RequestMapping("/routineBrdDetail")
	public String routineBrdDetail(int routine_brd_seq, Model model) throws ClassNotFoundException, SQLException {
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
	public String routineBrdEdit(int routine_brd_seq, Model model, HttpServletRequest request) throws ClassNotFoundException, SQLException {
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
   
   
   //------------------------ 댓글 ------------------------
   
   //댓글 인서트
   @ResponseBody
   @RequestMapping(value = "routineCmtInsert", method = RequestMethod.POST)
   public RoutineBoardCommentDto routineCmtInsert(RoutineBoardCommentDto routineCmtDto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
	   System.out.println("댓글 인서트 컨트롤러 탐");
	   System.out.println(request.getParameter("routine_brd_seq"));
	   System.out.println(request.getParameter("routine_cmt"));
	   System.out.println(request.getParameter("user_email"));
	   
	   return routinebrdservice.routineCmtInsert(routineCmtDto, request);
   }
   
   
}