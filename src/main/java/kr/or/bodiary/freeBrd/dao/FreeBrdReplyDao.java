package kr.or.bodiary.freeBrd.dao;

import java.util.List;
import java.util.Map;

import kr.or.bodiary.freeBrd.dto.FreeBrdReplyDTO;

public interface FreeBrdReplyDao {
	
	//댓글목록
	public List<FreeBrdReplyDTO> replyList(int seq) throws Exception;
	
	//댓글 INSERT
	public void create(FreeBrdReplyDTO freeBrdReplyDTO) throws Exception;
	
	//댓글 Delete
	public void delete(Map map) throws Exception;
}
