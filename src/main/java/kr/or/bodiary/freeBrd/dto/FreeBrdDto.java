package kr.or.bodiary.freeBrd.dto;


import java.sql.Date;



public class FreeBrdDto {
	private int free_brd_seq;
	private int free_cat;
	private String user_email;
	private String free_brd_title;
	private String free_brd_image;
	private String free_brd_content;
	private Date free_brd_date;
	private int free_brd_report_num;
	
	//User 테이블에서 조인해서 얻어온 닉네임
	private String user_nickname;
	
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	
	//User 테이블에서 조인해서 얻어온 프로필 사진 
	private String user_img;
	
	public String getUser_img() {
		return user_img;
	}
	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}
	
	
	
	public int getFree_brd_seq() {
		return free_brd_seq;
	}
	public void setFree_brd_seq(int free_brd_seq) {
		this.free_brd_seq = free_brd_seq;
	}
	public int getFree_cat() {
		return free_cat;
	}
	public void setFree_cat(int free_cat) {
		this.free_cat = free_cat;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getFree_brd_title() {
		return free_brd_title;
	}
	public void setFree_brd_title(String free_brd_title) {
		this.free_brd_title = free_brd_title;
	}
	public String getFree_brd_image() {
		return free_brd_image;
	}
	public void setFree_brd_image(String free_brd_image) {
		this.free_brd_image = free_brd_image;
	}
	public String getFree_brd_content() {
		return free_brd_content;
	}
	public void setFree_brd_content(String free_brd_content) {
		this.free_brd_content = free_brd_content;
	}
	public Date getFree_brd_date() {
		return free_brd_date;
	}
	public void setFree_brd_date(Date free_brd_date) {
		this.free_brd_date = free_brd_date;
	}
	public int getFree_brd_report_num() {
		return free_brd_report_num;
	}
	public void setFree_brd_report_num(int free_brd_report_num) {
		this.free_brd_report_num = free_brd_report_num;
	}
	
}
