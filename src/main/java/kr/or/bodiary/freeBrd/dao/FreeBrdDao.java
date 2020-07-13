package kr.or.bodiary.freeBrd.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bodiary.freeBrd.dto.FreeBrdDto;
import kr.or.bodiary.freeBrd.dto.Pagination;
import kr.or.bodiary.freeBrd.dto.Search;




public interface FreeBrdDao {

	//전체 게시글 가져오기(팁,자유,궁금) 원본
	//public List<FreeBrdDto> allFreeBrd(@Param("startIndex")int startIndex,@Param("pageSize")int pageSize) throws ClassNotFoundException, SQLException;	

	//전체 게시글 가져오기(팁,자유,궁금)
	public List<FreeBrdDto> allFreeBrd(Search search) throws ClassNotFoundException, SQLException;	
	
	//해당 게시글 세부 목록 가져오기 
	public FreeBrdDto freebrdDetail(String seq) throws ClassNotFoundException, SQLException;
	
	//게시물 Insert 
	public FreeBrdDto freeBrdInsert(FreeBrdDto freeBrdDto) throws ClassNotFoundException, SQLException;
	
	//게시물 Insert 
	//public int freeBrdInsert(FreeBrdDto freeBrdDto) throws ClassNotFoundException, SQLException;
	// -> Mapper method 'freeBrdInsert' (interface kr.or.bodiary.freeBrd.dao.FreeBrdDao) 
	//attempted to return null from a method with a primitive return type (int). 이런 에러발생 ?? 왜지??
	
	//게시물 Insert후 생성된 글번호만 가져오기 
	public int afterInsert_SelectDetail() throws ClassNotFoundException, SQLException;
	
	//게시물 Update
	public int freeBrdUpdate(FreeBrdDto freeBrdDto) throws ClassNotFoundException, SQLException;
	
	//해당 게시글 삭제
	public Integer freeBrdDelete(String seq) throws ClassNotFoundException, SQLException;
	
	//게시글 총 개수 가져오기 
	public int getFreeBoardListCnt() throws Exception;
	
	//게시글 총 개수 가져오기 
    public int getFreeBoardListCnt(Search search) throws Exception;
    
    //자유 게시글 총 개수 가져오기 
    public int getLibertyCnt(Search search) throws Exception;
    
    //질문 게시글 총 개수 가져오기 
    public int getQuestionCnt(Search search) throws Exception;
    
    //팁 게시글 총 개수 가져오기 
    public int getTipCnt(Search search) throws Exception;
	
}




















