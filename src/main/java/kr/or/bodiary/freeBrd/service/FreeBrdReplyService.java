package kr.or.bodiary.freeBrd.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.freeBrd.dao.FreeBrdReplyDao;
import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.freeBrd.dto.FreeBrdReplyDTO;
import kr.or.bodiary.user.dto.UserDto;
import kr.or.bodiary.utils.DateUtils;

@Service
public class FreeBrdReplyService {

	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	//댓글 목록 
	public List<FreeBrdReplyDTO> list(String seq) throws Exception{
		FreeBrdReplyDao list = sqlsession.getMapper(FreeBrdReplyDao.class);
		List<FreeBrdReplyDTO> rList = list.replyList(Integer.parseInt(seq));
	
		

		//날짜변경
		   for(FreeBrdReplyDTO f : rList) {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(f.getBrd_cmt_date());
				String formatDate = DateUtils.formatTimeString(date);
			
				f.setBrd_cmt_date(formatDate);
			}
		
		return rList;
	}
	
	//댓글 수정(Update)
	public int commentUpdateService(FreeBrdReplyDTO freeBrdReplyDTO) throws Exception {
		
		FreeBrdReplyDao list = sqlsession.getMapper(FreeBrdReplyDao.class);
		
		return list.update(freeBrdReplyDTO);
	}
	
	
	
	//댓글 작성(insert)
	public int create(String seq,String replytext,HttpServletRequest request) throws Exception {
		FreeBrdReplyDTO dto = new FreeBrdReplyDTO();
		
		//로그인한 해당 유저의 정보를 뽑아올수 있음  
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		String user_email = user.getUser_email();
		
		//댓글을 작성한 게시글 번호
		dto.setFree_brd_seq(Integer.parseInt(seq));
		//댓글 내용
		dto.setBrd_cmt(replytext);
		//댓글 작성자 
		dto.setUser_email(user_email);
		
		FreeBrdReplyDao list = sqlsession.getMapper(FreeBrdReplyDao.class);
		
		return list.create(dto);
	}
	
	//해당 댓글 삭제(delete) 
	public int delete(int cno) throws NumberFormatException, Exception {
		FreeBrdReplyDao list = sqlsession.getMapper(FreeBrdReplyDao.class);

		return list.delete(cno);
	}
	
}












