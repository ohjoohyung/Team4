package kr.or.bodiary.main.controller;


import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping("main")
	public String main(Model model) throws ClassNotFoundException, SQLException {
		List<NoticeDto> noticeMain = mainservice.noticeMain();
		List<RoutineBoardUserJoinDto> routineBrdMain = mainservice.routineBrdMain();
		List<FreeBrdDTO> freeBrdMain = mainservice.freeBrdMain();
		
		model.addAttribute("noticeMain", noticeMain);
		model.addAttribute("routineBrdMain", routineBrdMain);
		model.addAttribute("freeBrdMain", freeBrdMain);
		return "main";
	}
	
}