package kr.or.bodiary.freeBrd.dao;

import java.util.List;

import kr.or.bodiary.freeBrd.dto.FreeBrdCmtDto;

public interface FreeBrdCmtDao {
	
	//댓글목록
	public List<FreeBrdCmtDto> replyList(int seq) throws Exception;
	
	//댓글 INSERT
	public void create(FreeBrdCmtDto freeBrdCmtDto) throws Exception;
}
