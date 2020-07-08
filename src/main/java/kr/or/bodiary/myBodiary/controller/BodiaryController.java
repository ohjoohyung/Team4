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
import kr.or.bodiary.myBodiary.dto.FoodDto;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;
import kr.or.bodiary.myBodiary.dto.bodiaryDTO;
import kr.or.bodiary.myBodiary.dto.dailyMealDTO;
import kr.or.bodiary.myBodiary.service.BodiaryService;
import kr.or.bodiary.user.dto.userDTO;
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

	

	@RequestMapping("/myPageEdit")
	public String myPageEdit() {
		return "myBodiary/myPageEdit";
	}

	@RequestMapping("/myProfileDetail")
	public String myProfileDetail() {
		return "myBodiary/myProfileDetail";
	}
	@RequestMapping("/myProfileEdit")
	public String myProfileEdit() {
		return "myBodiary/myProfileEdit";
	}

	@RequestMapping("/myQnaList")
	public String getMyQnaList() {
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

	@RequestMapping("/myQnaForm")
	public String getMyQnaForm() {
		return "myBodiary/myQnaForm";
	}
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

	@RequestMapping(value = "/myBodiaryForm", method = RequestMethod.GET)
	public String myBodiaryForm(Model model) throws ClassNotFoundException, SQLException {
		List<RoutineJoinDto> list = bodiaryservice.getRoutineListById();
		model.addAttribute("routineList", list);
		return "myBodiary/myBodiaryForm";
	}
	
	@RequestMapping(value = "/myBodiaryForm", method = RequestMethod.POST)
	public String myBodiaryForm(dailyMealDTO dailymealdto, bodiaryDTO bodiarydto, HttpServletRequest request) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("안녕");
		System.out.println("컨트롤 : "+bodiarydto.toString());
		System.out.println("컨트롤 : "+dailymealdto.getDailyMealList().toString());
		
		if(bodiarydto.getDiary_pubchk() == null) {
			bodiarydto.setDiary_pubchk("N");
		} else {
			bodiarydto.setDiary_pubchk("Y");
		}
		
		String url = bodiaryservice.writeBodiary(dailymealdto, bodiarydto, request);
		
		return url;
	}

	@RequestMapping("/myBodiaryDetail")
	public String myBodiaryDetail(String diary_seq, Model model) throws ClassNotFoundException, SQLException {
		/* bodiaryDTO bodiarydto = bodiaryservice.getBodiary(diary_seq); */
		/* model.addAttribute("bodiary", bodiarydto); */
		return "myBodiary/myBodiaryDetail";
	}

	@RequestMapping("/myBodiaryEdit")
	public String myBodiaryEdit() {
		return "myBodiary/myBodiaryEdit";
	}
	
	@ResponseBody 
	@RequestMapping("/foodNameSearch")
	public List<FoodDto> foodNameSearch(@RequestParam String food_name) throws ClassNotFoundException, SQLException {
		System.out.println("food_name:"+food_name);
		List<FoodDto> foodlist = bodiaryservice.foodNameSearch(food_name);
		System.out.println(foodlist.toString());
		return foodlist;
	}
	
	
	@ResponseBody 
	@RequestMapping("/getRoutine")
	public List<RoutineJoinDto> getRoutine(@RequestParam String routine_cart_seq) throws ClassNotFoundException, SQLException {
		System.out.println("routine_cart_seq:"+routine_cart_seq);
		List<RoutineJoinDto> routinelist = bodiaryservice.getRoutine(routine_cart_seq);
		System.out.println(routinelist.toString());
		return routinelist;
	}
	


}
