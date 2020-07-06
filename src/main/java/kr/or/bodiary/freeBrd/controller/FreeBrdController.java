package kr.or.bodiary.freeBrd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bodiary.freeBrd.dto.FreeBrdDto;
import kr.or.bodiary.freeBrd.service.FreeBrdService;

@Controller
public class FreeBrdController {
	
	private FreeBrdService freeBrdService;
	
	@Autowired
	public void setFreeBrdService(FreeBrdService freeBrdService) {
		this.freeBrdService = freeBrdService;
	}
	
	//전체 게시글(자유,팁,궁금) 보기
	@RequestMapping("freeBrdList")
	public String AllFreeBrdList(Model model) {
		
		List<FreeBrdDto> freeBrdList = freeBrdService.allFreeBrd();
		model.addAttribute("freeBrdList",freeBrdList);
		
		System.out.println("자유게시판 목록페이지로 이동");
		
		return "freeBrd/freeBrdList";
	}
	
	//해당 게시글 세부 목록 보기
	@RequestMapping("freeBrdDetail")
	public String FreeBrdDetail(String seq,Model model) {
		System.out.println("글번호:"+seq);
		
		FreeBrdDto freeBrdDetail = freeBrdService.freebrdDetail(seq);
		model.addAttribute("freeBrdDetail",freeBrdDetail);
		
		//System.out.println("자유게시판 목록페이지로 이동");
		
		return "freeBrd/freeBrdDetail";
	}
	
	
}


















