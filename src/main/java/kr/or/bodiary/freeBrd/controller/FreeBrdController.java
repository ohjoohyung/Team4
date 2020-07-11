package kr.or.bodiary.freeBrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FreeBrdController {
	@RequestMapping("/freeBrdDetail")
	   public String freeBrdDetail() {
	      return "freeBrd/freeBrdDetail";
	   }
	   
	   @RequestMapping("/freeBrdEdit")
	   public String freeBrdEdit() {
	      return "freeBrd/freeBrdEdit";
	   }
	   
	   @RequestMapping("/freeBrdForm")
	   public String freeBrdForm() {
	      return "freeBrd/freeBrdForm";
	   }
	   
	   @RequestMapping("/freeBrdList")
	   public String freeBrdList() {
	      return "freeBrd/freeBrdList";
	   }
}
