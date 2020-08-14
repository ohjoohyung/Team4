package kr.or.bodiary.main.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.main.service.MainService;
import kr.or.bodiary.notice.dto.NoticeDto;
import kr.or.bodiary.routineBrd.dto.RoutineBoardUserJoinDto;


@Controller
public class MainController {

	@Autowired
	private MainService mainservice;
	
	public void setMainService(MainService mainservice) {
		this.mainservice = mainservice;
	}
	
	//로그인 후 메인 페이지 들어가기
	@RequestMapping(value="/", method =RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	//쿠션 메인 페이지 들어가기
	@RequestMapping("main")
	public String main(Model model) throws Exception {
		List<NoticeDto> noticeMain = mainservice.noticeMain();
		List<RoutineBoardUserJoinDto> routineBrdMain = mainservice.routineBrdMain();
		List<FreeBrdDTO> freeBrdMain = mainservice.freeBrdMain();
		
		model.addAttribute("noticeMain", noticeMain);
		model.addAttribute("routineBrdMain", routineBrdMain);
		model.addAttribute("freeBrdMain", freeBrdMain);
		return "main";
	}
	
}
