package kr.or.bodiary.myBodiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BodiaryController {

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

	@RequestMapping("/myBodiaryMain")
	public String myBodiaryMain() {
		return "myBodiary/myBodiaryMain";
	}

	@RequestMapping("/myBodiaryForm")
	public String myBodiaryForm() {
		return "myBodiary/myBodiaryForm";
	}

	@RequestMapping("/myBodiaryDetail")
	public String myBodiaryDetail() {
		return "myBodiary/myBodiaryDetail";
	}

	@RequestMapping("/myBodiaryEdit")
	public String myBodiaryEdit() {
		return "myBodiary/myBodiaryEdit";
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


}
