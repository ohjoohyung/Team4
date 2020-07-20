package kr.or.bodiary.myBodiary.controller;

import java.io.IOException;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



import kr.or.bodiary.myBodiary.dto.DailyMealFoodJoinDto;
import kr.or.bodiary.myBodiary.dto.FoodDto;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;
import kr.or.bodiary.myBodiary.dto.BodiaryDto;
import kr.or.bodiary.myBodiary.dto.DailyMealDto;
import kr.or.bodiary.myBodiary.service.BodiaryService;
import kr.or.bodiary.user.dto.UserDto;
import kr.or.bodiary.user.service.UserService;


@Controller
public class BodiaryController {

	
	/*
	 * private UserService userservice;
	 * 
	 * @Autowired public void setUserservice(UserService userservice) {
	 * this.userservice = userservice; }
	 */
	
	
	  private BodiaryService bodiaryservice;
	  
	  @Autowired public void setBodiaryservice(BodiaryService bodiaryservice) {
	  this.bodiaryservice = bodiaryservice; }
	 
	
	
	
	@RequestMapping("/myGoalForm")
	public String myGoalForm() {
		return "myBodiary/myGoalForm";
	}
	@RequestMapping("/myGoalList")
	public String myGoalList() {
		return "myBodiary/myGoalList";
	}

	@RequestMapping("/myHistory")
	public String myHistory() {
		return "myBodiary/myHistory";
	}

	@RequestMapping("/myHistoryDetail")
	public String myHistoryDetail() {
		return "myBodiary/myHistoryDetail";
	}

	@RequestMapping("/myHistoryEditForm")
	public String myHistoryEditForm() {
		return "myBodiary/myHistoryEditForm";
	}

	@RequestMapping("/myHistoryRoutine")
	public String myHistoryRoutine() {
		return "myHistory/myHistoryRoutine";
	}

	@RequestMapping("/myHistoryFreeBoard")
	public String myHistoryFreeBoard() {
		return "myHistory/myHistoryFreeBoard";
	}

	


	
	@RequestMapping("/myRoutineList")
	public String getMyRoutineList() {
		return "myBodiary/myRoutineList";
	}
	
	
	
	//일지 컨트롤러
	@RequestMapping("/myBodiaryMain")
	public String myBodiaryMain(Model model, HttpServletRequest request) throws ClassNotFoundException, SQLException {
		/*
		 * //나중에 이메일 바꿔야함 userDTO user = userservice.getUser("1@1");
		 * model.addAttribute("user", user);
		 */
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		int todayCount = bodiaryservice.todatBodiaryCount(user.getUser_email());
		
		String pagesize = "4";
		List<BodiaryDto> list = bodiaryservice.getBodiaryList(user.getUser_email(), pagesize);
		
		model.addAttribute("todayCount", todayCount);
		model.addAttribute("list", list);
		return "myBodiary/myBodiaryMain";
	}
	
	//일지 폼 페이지 들어가기
	@RequestMapping(value = "/myBodiaryForm", method = RequestMethod.GET)
	public String myBodiaryForm(Model model , HttpServletRequest request) throws ClassNotFoundException, SQLException {
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
	    String user_email = user.getUser_email();

	    System.out.println("유저 정보 : " +user_email);
	    List<RoutineJoinDto> list = bodiaryservice.getRoutineListById(user_email);
	    model.addAttribute("routineList", list);
	    System.out.println("userdto: "+user);//추가
        model.addAttribute("user", user);//추가
	    return "myBodiary/myBodiaryForm";
	}
	
	//일지 작성하기
	@RequestMapping(value = "/myBodiaryForm", method = RequestMethod.POST)
	public String myBodiaryForm(DailyMealDto dailymealdto, BodiaryDto bodiarydto, HttpServletRequest request) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("안녕");
		System.out.println("컨트롤 : "+bodiarydto.toString());
		/* System.out.println("컨트롤 : "+dailymealdto.getDailyMealList().toString()); */
		
		
		
		String url = bodiaryservice.writeBodiary(dailymealdto, bodiarydto, request);
		
		return url;
	}
	
	//일지 상세보기
	@RequestMapping("/myBodiaryDetail")
	public String myBodiaryDetail(String diary_seq, Model model) throws ClassNotFoundException, SQLException, ParseException {
		BodiaryDto bodiarydto = bodiaryservice.getBodiary(diary_seq);
		List<DailyMealFoodJoinDto> dailymeal = bodiaryservice.getDailyMeal(bodiarydto.getMeal_cart_seq());
		List<RoutineJoinDto> routinelist = bodiaryservice.getRoutine(bodiarydto.getRoutine_cart_seq());
		System.out.println(bodiarydto.getMeal_cart_seq());
		System.out.println(dailymeal.toString());
		System.out.println(routinelist.toString());
		
		
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bodiarydto.getDiary_date()); //String to date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //new format
		String dateNewFormat = sdf.format(date);
		
		
		
		//분을 시간으로 변환
		  for(RoutineJoinDto r : routinelist) {
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
		

		
		bodiarydto.setDiary_date(dateNewFormat);
		
		model.addAttribute("bodiary", bodiarydto);
		model.addAttribute("dailymeallist", dailymeal);
		model.addAttribute("routinelist", routinelist);
		return "myBodiary/myBodiaryDetail";
	}
	
	//일지 수정 페이지 이동
	@RequestMapping(value = "/myBodiaryEdit", method = RequestMethod.GET)
	public String myBodiaryEdit(String diary_seq, Model model) throws ClassNotFoundException, SQLException {
		BodiaryDto bodiarydto = bodiaryservice.getBodiary(diary_seq);
		System.out.println("수정페이지 이동 전 : "+bodiarydto.toString());
		String user_email = bodiarydto.getUser_email();
		List<RoutineJoinDto> list = bodiaryservice.getRoutineListById(user_email);
		model.addAttribute("routineList", list);
		model.addAttribute("bodiary", bodiarydto);
		
		return "myBodiary/myBodiaryEdit";
	}
	
	//일지 수정
	@RequestMapping(value = "/myBodiaryEdit", method = RequestMethod.POST)
	public String myBodiaryEdit(DailyMealDto dailymealdto, BodiaryDto bodiarydto, HttpServletRequest request) throws IOException {
		System.out.println("안녕 수정");
		System.out.println("컨트롤 : "+bodiarydto.toString());
		System.out.println("컨트롤 : "+dailymealdto.getDailyMealList().toString());
		String url = bodiaryservice.updateBodiary(dailymealdto, bodiarydto, request);
		return url;
	}
	
	//음식 검색하기
	@ResponseBody 
	@RequestMapping("/foodNameSearch")
	public List<FoodDto> foodNameSearch(@RequestParam String food_name) throws ClassNotFoundException, SQLException {
		System.out.println("food_name:"+food_name);
		List<FoodDto> foodlist = bodiaryservice.foodNameSearch(food_name);
		System.out.println(foodlist.toString());
		return foodlist;
	}
	
	
	//루틴 불러오기
	@ResponseBody 
	@RequestMapping("/getRoutine")
	public List<RoutineJoinDto> getRoutine(@RequestParam String routine_cart_seq) throws ClassNotFoundException, SQLException {
		System.out.println("routine_cart_seq:"+routine_cart_seq);
		List<RoutineJoinDto> routinelist = bodiaryservice.getRoutine(Integer.parseInt(routine_cart_seq));
		System.out.println(routinelist.toString());
		return routinelist;
	}
	
	//일지 리스트 불러오기
	@ResponseBody
	@RequestMapping("/getBodiaryList")
	public List<BodiaryDto> getBodiaryList(@RequestParam String user_email) throws ClassNotFoundException, SQLException {
		String pagesize = null;
		List<BodiaryDto> bodiarylist = bodiaryservice.getBodiaryList(user_email, pagesize);
		return bodiarylist;
	
	}
	
	//식단 불러오기
	@ResponseBody
	@RequestMapping("/getDailyMeal")
	public List<DailyMealFoodJoinDto> getDailyMeal(@RequestParam String meal_cart_seq) throws ClassNotFoundException, SQLException {
		List<DailyMealFoodJoinDto> dailyMealList = bodiaryservice.getDailyMeal(Integer.parseInt(meal_cart_seq));
		return dailyMealList;
		
	}
	
	//일지 삭제하기
	@RequestMapping("/myBodiaryDelete")
	public String myBodiaryDelete(String diary_seq) throws ClassNotFoundException, SQLException {
		return bodiaryservice.myBodiaryDelete(diary_seq);
	}
	


}
