package kr.or.bodiary.user.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;





@Controller
public class UserController {
	
	
	   
	   @RequestMapping("/noticeList")
	   public String getNoticeList() {
	      return "notice/noticeList";
	   }
	   
	   @RequestMapping("/noticeDetail")
	   public String getNoticeDetail() {
	      return "notice/noticeDetail";
	   }
	   
	   @RequestMapping("/noticeEdit")
	   public String getNoticeEdit() {
	      return "notice/noticeEdit";
	   }
	   
	   @RequestMapping("/noticeForm")
	   public String getNoticeForm() {
	      return "notice/noticeForm";
	   }
	   
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
	   @RequestMapping("/deleteAccount")
	   public String deleteAccount() {
		   return "user/deleteAccount";
	   }
	
	
	
}
