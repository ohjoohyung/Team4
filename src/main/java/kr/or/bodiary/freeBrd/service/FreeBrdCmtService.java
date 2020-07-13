package kr.or.bodiary.freeBrd.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.freeBrd.dao.FreeBrdCmtDao;
import kr.or.bodiary.freeBrd.dao.FreeBrdDao;
import kr.or.bodiary.freeBrd.dto.FreeBrdCmtDto;

@Service
public class FreeBrdCmtService {

	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	//댓글 목록 
	public List<FreeBrdCmtDto> list(String seq) throws Exception{
		FreeBrdCmtDao list = sqlsession.getMapper(FreeBrdCmtDao.class);
		
		List<FreeBrdCmtDto> rList = list.replyList(Integer.parseInt(seq));
		
		for(int i=0;i<rList.size();i++) {
			System.out.println("댓글내용"+rList.get(i).getBrd_cmt());
		}
		
		return rList;
	}
	
	//댓글 작성(insert)
	public void create(String seq,String replytext) throws Exception {
		FreeBrdCmtDto dto = new FreeBrdCmtDto();
		
		//댓글을 작성한 게시글 번호
		dto.setFree_brd_seq(Integer.parseInt(seq));
		//댓글 내용
		dto.setBrd_cmt(replytext);
		
		FreeBrdCmtDao list = sqlsession.getMapper(FreeBrdCmtDao.class);
		list.create(dto);
	}
}












