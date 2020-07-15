package kr.or.bodiary.myBodiary.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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


import kr.or.bodiary.chat.dto.NotYet;
import kr.or.bodiary.myBodiary.dto.DailyMealFoodJoinDto;
import kr.or.bodiary.myBodiary.dto.FoodDto;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;
import kr.or.bodiary.myBodiary.dto.bodiaryDTO;
import kr.or.bodiary.myBodiary.dto.dailyMealDTO;
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
	//qna들 빠짐
	


	

	
	@RequestMapping("/myRoutineList")
	public String getMyRoutineList() {
		return "myBodiary/myRoutineList";
	}
	
	
	
	//일지 컨트롤러
	@RequestMapping("/myBodiaryMain")
	public String myBodiaryMain(String user_email, Model model) throws ClassNotFoundException, SQLException {
		/*
		 * //나중에 이메일 바꿔야함 userDTO user = userservice.getUser("1@1");
		 * model.addAttribute("user", user);
		 */
		return "myBodiary/myBodiaryMain";
	}
	
	//일지 폼 페이지 들어가기
	@RequestMapping(value = "/myBodiaryForm", method = RequestMethod.GET)
	public String myBodiaryForm(Model model) throws ClassNotFoundException, SQLException {
		List<RoutineJoinDto> list = bodiaryservice.getRoutineListById();
		model.addAttribute("routineList", list);
		return "myBodiary/myBodiaryForm";
	}
	
	//일지 작성하기
	@RequestMapping(value = "/myBodiaryForm", method = RequestMethod.POST)
	public String myBodiaryForm(dailyMealDTO dailymealdto, bodiaryDTO bodiarydto, HttpServletRequest request) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("안녕");
		System.out.println("컨트롤 : "+bodiarydto.toString());
		System.out.println("컨트롤 : "+dailymealdto.getDailyMealList().toString());
		
		
		
		String url = bodiaryservice.writeBodiary(dailymealdto, bodiarydto, request);
		
		return url;
	}
	
	//일지 상세보기
	@RequestMapping("/myBodiaryDetail")
	public String myBodiaryDetail(String diary_seq, Model model) throws ClassNotFoundException, SQLException {
		bodiaryDTO bodiarydto = bodiaryservice.getBodiary(diary_seq);
		List<DailyMealFoodJoinDto> dailymeal = bodiaryservice.getDailyMeal(bodiarydto.getMeal_cart_seq());
		List<RoutineJoinDto> routinelist = bodiaryservice.getRoutine(bodiarydto.getRoutine_cart_seq());
		System.out.println(bodiarydto.getMeal_cart_seq());
		System.out.println(dailymeal.toString());
		System.out.println(routinelist.toString());
		model.addAttribute("bodiary", bodiarydto);
		model.addAttribute("dailymeallist", dailymeal);
		model.addAttribute("routinelist", routinelist);
		return "myBodiary/myBodiaryDetail";
	}
	
	//일지 수정 페이지 이동
	@RequestMapping(value = "/myBodiaryEdit", method = RequestMethod.GET)
	public String myBodiaryEdit(String diary_seq, Model model) throws ClassNotFoundException, SQLException {
		bodiaryDTO bodiarydto = bodiaryservice.getBodiary(diary_seq);
		System.out.println("수정페이지 이동 전 : "+bodiarydto.toString());
		List<RoutineJoinDto> list = bodiaryservice.getRoutineListById();
		model.addAttribute("routineList", list);
		model.addAttribute("bodiary", bodiarydto);
		
		return "myBodiary/myBodiaryEdit";
	}
	
	//일지 수정
	@RequestMapping(value = "/myBodiaryEdit", method = RequestMethod.POST)
	public String myBodiaryEdit(dailyMealDTO dailymealdto, bodiaryDTO bodiarydto, HttpServletRequest request) throws IOException {
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
		List<RoutineJoinDto> routinelist = bodiaryservice.getRoutine(routine_cart_seq);
		System.out.println(routinelist.toString());
		return routinelist;
	}
	
	//일지 리스트 불러오기
	@ResponseBody
	@RequestMapping("/getBodiaryList")
	public List<bodiaryDTO> getBodiaryList(@RequestParam String user_email) throws ClassNotFoundException, SQLException {
		List<bodiaryDTO> bodiarylist = bodiaryservice.getBodiaryList(user_email);
		return bodiarylist;
	
	}
	
	//식단 불러오기
	@ResponseBody
	@RequestMapping("/getDailyMeal")
	public List<DailyMealFoodJoinDto> getDailyMeal(@RequestParam String meal_cart_seq) throws ClassNotFoundException, SQLException {
		List<DailyMealFoodJoinDto> dailyMealList = bodiaryservice.getDailyMeal(meal_cart_seq);
		return dailyMealList;
		
	}
	
	//일지 삭제하기
	@RequestMapping("/myBodiaryDelete")
	public String myBodiaryDelete(String diary_seq) throws ClassNotFoundException, SQLException {
		return bodiaryservice.myBodiaryDelete(diary_seq);
	}
	


}
