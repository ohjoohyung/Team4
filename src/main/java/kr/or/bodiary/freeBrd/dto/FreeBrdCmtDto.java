package kr.or.bodiary.freeBrd.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class FreeBrdCmtDto {
	private int brd_cmt_seq; //댓글번호
	private int free_brd_seq; //게시글 번호
	private int free_cat; //카테고리
	private String user_email; //이메일
	private String brd_cmt; //댓글내용
	private String brd_cmt_date; //댓글작성날짜
	private String brd_cmt_ref ; 
	private String brd_cmt_depth; 
	private String brd_cmt_step;
	
	public int getBrd_cmt_seq() {
		return brd_cmt_seq;
	}
	public void setBrd_cmt_seq(int brd_cmt_seq) {
		this.brd_cmt_seq = brd_cmt_seq;
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
	public String getBrd_cmt() {
		return brd_cmt;
	}
	public void setBrd_cmt(String brd_cmt) {
		this.brd_cmt = brd_cmt;
	}
	public String getBrd_cmt_date() {
		return brd_cmt_date;
	}
	public void setBrd_cmt_date(String brd_cmt_date) {
		this.brd_cmt_date = brd_cmt_date;
	}
	public String getBrd_cmt_ref() {
		return brd_cmt_ref;
	}
	public void setBrd_cmt_ref(String brd_cmt_ref) {
		this.brd_cmt_ref = brd_cmt_ref;
	}
	public String getBrd_cmt_depth() {
		return brd_cmt_depth;
	}
	public void setBrd_cmt_depth(String brd_cmt_depth) {
		this.brd_cmt_depth = brd_cmt_depth;
	}
	public String getBrd_cmt_step() {
		return brd_cmt_step;
	}
	public void setBrd_cmt_step(String brd_cmt_step) {
		this.brd_cmt_step = brd_cmt_step;
	} 
	
	
	
}
