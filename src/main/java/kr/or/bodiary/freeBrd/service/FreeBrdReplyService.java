package kr.or.bodiary.freeBrd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.freeBrd.dao.FreeBrdReplyDao;
import kr.or.bodiary.freeBrd.dto.FreeBrdReplyDTO;
import kr.or.bodiary.user.dto.UserDto;

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
	
		for(int i=0;i<rList.size();i++) {
			//게시글의 번호를 하나씩 얻어와 해당 게시글의 댓글수를 얻어옴 			
			rList.get(i).setBrd_cmt_count(list.commentCount(rList.get(i).getFree_brd_seq()));
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
		
//		Map map = new HashMap();
//		map.put("seq",seq);
//		map.put("cmt",cmt);
		
		return list.delete(cno);
	}
	
}












