package kr.or.bodiary.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

	@RequestMapping("/admin")
	public String adminDashBrd() {
		return "admin/adminDashBrd";
	}

	@RequestMapping("/adminQnaList")
	public String adminQnaList() {
		return "admin/adminQnaList";
	}

	@RequestMapping("/adminQnaDetail")
	public String adminQnaDetail() {
		return "admin/adminQnaDetail";
	}

	@RequestMapping("/adminUserBrdList")
	public String adminUserBrdList() {
		return "admin/adminUserBrdList";
	}

	// 어드민 운동 CRUD
	@RequestMapping("/adminExcsDetail")
	public String adminExcsDetail() {
		return "admin/adminExcsDetail";
	}

	@RequestMapping("/adminExcsEdit")
	public String adminExcsEdit() {
		return "admin/adminExcsEdit";
	}

	@RequestMapping("/adminExcsForm")
	public String adminExcsForm() {
		return "admin/adminExcsForm";
	}

	@RequestMapping("/adminExcsList")
	public String adminExcsList() {
		return "admin/adminExcsList";
	}
	@RequestMapping("/adminReportList")
	public String adminReportList() {
		return "admin/adminReportList";
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
}
