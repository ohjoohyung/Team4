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
	
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("selectUserCount") public int selectUserCount() throws
	 * ClassNotFoundException, SQLException { System.out.println("회원수 알려주세요");
	 * 
	 * return chartService.selectUserCount(); } //남녀 성비
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("genderPer") public String genderPer(UserDto user,
	 * HttpServletRequest request) throws ClassNotFoundException, SQLException {
	 * System.out.println("남녀 성비 알려주세요");
	 * 
	 * return chartService.genderPer(); } //탈퇴한회원수
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("userDelete") public int userDelete(UserDto user,
	 * HttpServletRequest request) throws ClassNotFoundException, SQLException {
	 * 
	 * return chartService.deleteUserCount(); }
	 * 
	 * //탈퇴한회원수
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("countReport") public int countReport(UserDto user,
	 * HttpServletRequest request) throws ClassNotFoundException, SQLException {
	 * 
	 * return chartService.countReport(); } //일일가입한
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("todayUser") public int todayUser(UserDto user,
	 * HttpServletRequest request) throws ClassNotFoundException, SQLException {
	 * 
	 * return chartService.todayUser(); } //updating chart
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("updatingChart") public int updatingChart(UserDto user,
	 * HttpServletRequest request) throws ClassNotFoundException, SQLException {
	 * 
	 * return chartService.updatingChart(); } //monthlyCount chart
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("monthlyCount") public ArrayList<Integer>
	 * monthlyCount(UserDto user, HttpServletRequest request) throws
	 * ClassNotFoundException, SQLException {
	 * 
	 * return chartService.monthlyCount(); }
	 */
	}
