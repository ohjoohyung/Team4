package kr.or.bodiary.admin.controller;


import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bodiary.admin.dto.AdminDashDto;
import kr.or.bodiary.admin.service.ChartService;


@Controller
public class AdminDashController {

	
	@Autowired
	private ChartService chartService;
	
	public void setChartService(ChartService chartService) {
		this.chartService = chartService;
	}
			
	  @ResponseBody
	  @RequestMapping("dailyUser") 
	  public List<AdminDashDto> dailyUser() throws  ClassNotFoundException, SQLException { 
		  System.out.println("회원수 알려주세요");
	  return chartService.dailyUser(); } //남녀 성비
	
	
	}
