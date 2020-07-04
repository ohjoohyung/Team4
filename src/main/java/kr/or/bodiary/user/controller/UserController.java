package kr.or.bodiary.user.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bodiary.chat.dto.NotYet;





@Controller
public class UserController {
	
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
	   
	   @RequestMapping("/adminQna")
	   public String getAdminQna() {
	      return "admin/adminQna";
	   }
	   
	   @RequestMapping("/adminQnaDetail")
	   public String getAdminQnaDetail() {
	      return "admin/adminQnaDetail";
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
	
	
	
}
