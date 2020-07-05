package kr.or.bodiary.freeBrd.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.freeBrd.dto.FreeBrdDto;


public interface FreeBrdDao {
	
	//전체 게시글 가져오기(팁,자유,궁금)
	public List<FreeBrdDto> allFreeBrd() throws ClassNotFoundException, SQLException;
}
