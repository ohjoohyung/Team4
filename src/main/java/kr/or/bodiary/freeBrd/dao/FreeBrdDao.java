package kr.or.bodiary.freeBrd.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.freeBrd.dto.Pagination;
import kr.or.bodiary.freeBrd.dto.Search;
import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;


public interface FreeBrdDao {
		//관리자 페이지 유저관리 (해당 유저 루틴자랑 게시판 리스트 가져오기)
		public List<RoutineBrdDto> getUserRoutineBrdList(String user_email) throws ClassNotFoundException, SQLException;
	
		//관리자 페이지 유저관리 (해당 유저 자유게시판 리스트 가져오기)
		public List<FreeBrdDTO> getUserFreeBrdList(String user_email) throws ClassNotFoundException, SQLException;
		
	    //모든 카테고리 전체 게시글 가져오기(팁,자유,궁금) 
		public List<FreeBrdDTO> allCatFreeBrd(Search search) throws ClassNotFoundException, SQLException;	

		//해당카테고리의 전체 게시글 가져오기(팁,자유,궁금)
		public List<FreeBrdDTO> allFreeBrd(Search search) throws ClassNotFoundException, SQLException;	
		
		//전체 게시글 가져오기(팁,자유,궁금)
		public List<FreeBrdDTO> allFreeBrd_P(Pagination pagination) throws ClassNotFoundException, SQLException;	
				
		//해당 게시글 세부 목록 가져오기 
		public FreeBrdDTO freebrdDetail(String seq) throws ClassNotFoundException, SQLException;
		
		//게시물 Insert 
		public FreeBrdDTO freeBrdInsert(FreeBrdDTO freeBrdDto) throws ClassNotFoundException, SQLException;
		

		
		//게시물 Insert후 생성된 글번호만 가져오기 
		public int afterInsert_SelectDetail() throws ClassNotFoundException, SQLException;
		
		//게시물 Update
		public int freeBrdUpdate(FreeBrdDTO freeBrdDto) throws ClassNotFoundException, SQLException;
		
		//해당 게시글 삭제
		public Integer freeBrdDelete(String seq) throws ClassNotFoundException, SQLException;
		
		//게시글 총 개수 가져오기 
		public int getFreeBoardListCnt() throws Exception;
		
		//(내가쓴글댓글)게시글 총 개수 가져오기 
		public int getFreeBoardListCnt_M(String user_email) throws Exception;
		
		//게시글 총 개수 가져오기 
	    public int getFreeBoardListCnt(Search search) throws Exception;
	    
	    //자유 게시글 총 개수 가져오기 
	    public int getLibertyCnt(Search search) throws Exception;
	    
	    //질문 게시글 총 개수 가져오기 
	    public int getQuestionCnt(Search search) throws Exception;
	    
	    //팁 게시글 총 개수 가져오기 
	    public int getTipCnt(Search search) throws Exception;
	    
	    //게시판 조회수 증가
	    public void freeBrdHit(String seq) throws Exception;
	    
	    //자유게시판 상위 랭크를 뽑아옴 
	    public List<FreeBrdDTO> highLankFree() throws Exception;
	    
	    //질문게시판 상위 랭크를 뽑아옴 
	    public List<FreeBrdDTO> highLankQna() throws Exception;
	    
	    //팁게시판 상위 랭크를 뽑아옴 
	    public List<FreeBrdDTO> highLankTip() throws Exception;

}





















