package kr.or.bodiary.freeBrd.service;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.bodiary.freeBrd.dao.FreeBrdDao;
import kr.or.bodiary.freeBrd.dao.FreeBrdReplyDao;
import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.freeBrd.dto.Search;

import kr.or.bodiary.user.dto.UserDto;
import kr.or.bodiary.utils.DateUtils;


@Service
public class FreeBrdService {
	

	
private SqlSession sqlsession;

@Autowired
public void setSqlsession(SqlSession sqlsession) {
	this.sqlsession = sqlsession;
}

//자유게시판 상위 랭크를 뽑아옴
public List<FreeBrdDTO> highLankFree() throws Exception{
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	List<FreeBrdDTO> list = FreeBrd.highLankFree();
	//날짜변경
	   for(FreeBrdDTO f : list) {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(f.getFree_brd_date());
			String formatDate = DateUtils.formatTimeString(date);
		
			f.setFree_brd_date(formatDate);
		}
	   
	 //해당 게시글의 총댓글 개수 얻어옴 
		FreeBrdReplyDao cmtlist = sqlsession.getMapper(FreeBrdReplyDao.class);

		for(int i=0;i<list.size();i++) {
			//게시글의 번호를 하나씩 얻어와 해당 게시글의 댓글수를 얻어옴 			
			list.get(i).setBrd_cmt_count(cmtlist.commentCount(list.get(i).getFree_brd_seq()));
			System.out.println(list.get(i).getFree_brd_seq()+"번호의 댓글 개수 ->"+list.get(i).getBrd_cmt_count());
		}
	return list;
}
//질문게시판 상위 랭크를 뽑아옴
public List<FreeBrdDTO> highLankQna() throws Exception{
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	List<FreeBrdDTO> list = FreeBrd.highLankQna();
	//날짜변경
	   for(FreeBrdDTO f : list) {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(f.getFree_brd_date());
			String formatDate = DateUtils.formatTimeString(date);
		
			f.setFree_brd_date(formatDate);
		}
	   
	 //해당 게시글의 총댓글 개수 얻어옴 
		FreeBrdReplyDao cmtlist = sqlsession.getMapper(FreeBrdReplyDao.class);

		for(int i=0;i<list.size();i++) {
			//게시글의 번호를 하나씩 얻어와 해당 게시글의 댓글수를 얻어옴 			
			list.get(i).setBrd_cmt_count(cmtlist.commentCount(list.get(i).getFree_brd_seq()));
			System.out.println(list.get(i).getFree_brd_seq()+"번호의 댓글 개수 ->"+list.get(i).getBrd_cmt_count());
		}
	return list;
}
//팁게시판 상위 랭크를 뽑아옴
public List<FreeBrdDTO> highLankTip() throws Exception{
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	List<FreeBrdDTO> list = FreeBrd.highLankTip();
	//날짜변경
	   for(FreeBrdDTO f : list) {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(f.getFree_brd_date());
			String formatDate = DateUtils.formatTimeString(date);
		
			f.setFree_brd_date(formatDate);
		}
	   
	 //해당 게시글의 총댓글 개수 얻어옴 
		FreeBrdReplyDao cmtlist = sqlsession.getMapper(FreeBrdReplyDao.class);

		for(int i=0;i<list.size();i++) {
			//게시글의 번호를 하나씩 얻어와 해당 게시글의 댓글수를 얻어옴 			
			list.get(i).setBrd_cmt_count(cmtlist.commentCount(list.get(i).getFree_brd_seq()));
			System.out.println(list.get(i).getFree_brd_seq()+"번호의 댓글 개수 ->"+list.get(i).getBrd_cmt_count());
		}
	return list;
}


//게시판 제목클릭시 조회수 증가하는 함수
public void freeBrdHit(String seq) throws Exception {
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	FreeBrd.freeBrdHit(seq);
}
	
//자유게시판 총 게시물수 얻어오는 함수 
public int getLibertyCnt(Search search) throws Exception {
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	int result = FreeBrd.getLibertyCnt(search);
	System.out.println("자유게시판 총 게시물수 "+result);
	
	return result;
}

//질문게시판 총 게시물수 얻어오는 함수 
public int getQuestionCnt(Search search) throws Exception {
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	int result = FreeBrd.getQuestionCnt(search);
	System.out.println("질문게시판 총 게시물수 "+result);
	
	return result;
}

//팁게시판 총 게시물수 얻어오는 함수 
public int getTipCnt(Search search) throws Exception {
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	int result = FreeBrd.getTipCnt(search);
	System.out.println("팁게시판 총 게시물수 "+result);
	
	return result;
}

//검색에 해당하는 총 게시물수 얻어오는 함수
public int allFreeBrdCount(Search search) throws Exception {
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	int result = FreeBrd.getFreeBoardListCnt(search);
	System.out.println("검색에 해당되는 총 게시물수 "+result);
	
	return result;
}

	// 해당 카테고리 전체 게시글(자유,팁,궁금) 목록보기
	public List<FreeBrdDTO> allFreeBrd(Search search) throws ParseException {
	
		List<FreeBrdDTO> list = null;
	
		try {
			System.out.println("시작인덱스"+search.getStartIndex());
			System.out.println("한페이지 사이즈"+search.getPageSize());
			System.out.println("카테고리"+search.getCateGory());
			// mapper 를 통한 FreeBrdDao 인터페이스 연결
			FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
			list = FreeBrd.allFreeBrd(search);
			
			//해당 게시글의 총댓글 개수 얻어옴 
			FreeBrdReplyDao cmtlist = sqlsession.getMapper(FreeBrdReplyDao.class);

			for(int i=0;i<list.size();i++) {
				//게시글의 번호를 하나씩 얻어와 해당 게시글의 댓글수를 얻어옴 			
				list.get(i).setBrd_cmt_count(cmtlist.commentCount(list.get(i).getFree_brd_seq()));
				System.out.println(list.get(i).getFree_brd_seq()+"번호의 댓글 개수 ->"+list.get(i).getBrd_cmt_count());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("allFreeBrd() 함수 실행중 오류발생" + e.getMessage());
		}
		//날짜변경
		   for(FreeBrdDTO f : list) {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(f.getFree_brd_date());
				String formatDate = DateUtils.formatTimeString(date);
			
				f.setFree_brd_date(formatDate);
			}
	
		return list;
	}
		
	//전체 카테고리 게시글(자유,팁,궁금) 목록보기
	public List<FreeBrdDTO> allCatFreeBrd(Search search) throws ParseException {
	
		List<FreeBrdDTO> list = null;
	
		try {
			// mapper 를 통한 FreeBrdDao 인터페이스 연결
			FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
			list = FreeBrd.allCatFreeBrd(search);
			
			//해당 게시글의 총댓글 개수 얻어옴 
			FreeBrdReplyDao cmtlist = sqlsession.getMapper(FreeBrdReplyDao.class);
			for(int i=0;i<list.size();i++) {
				//게시글의 번호를 하나씩 얻어와 해당 게시글의 댓글수를 얻어옴 			
				list.get(i).setBrd_cmt_count(cmtlist.commentCount(list.get(i).getFree_brd_seq()));
				System.out.println(list.get(i).getFree_brd_seq()+"번호의 댓글 개수 ->"+list.get(i).getBrd_cmt_count());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("allCatFreeBrd() 함수 실행중 오류발생" + e.getMessage());
		}
		//날짜변경
		   for(FreeBrdDTO f : list) {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(f.getFree_brd_date());
				String formatDate = DateUtils.formatTimeString(date);
			
				f.setFree_brd_date(formatDate);
			}
	
		return list;
	}


	// 글 상세보기 서비스 함수
	public FreeBrdDTO freebrdDetail(String seq) throws ParseException {
		FreeBrdDTO freeBrdDto = null;
		try {
			FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);			
			
			freeBrdDto = FreeBrd.freebrdDetail(seq);
			
			FreeBrdReplyDao list = sqlsession.getMapper(FreeBrdReplyDao.class);
			
			//해당게시글의 총댓글 개수를 얻어와 setter로 주입함 
			freeBrdDto.setBrd_cmt_count(list.commentCount(Integer.parseInt(seq)));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		//날짜변경
		 
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(freeBrdDto.getFree_brd_date());
		String formatDate = DateUtils.formatTimeString(date);
			
		freeBrdDto.setFree_brd_date(formatDate);
		
	
		return freeBrdDto;
	}

	// 글쓰기 처리 서비스 함수
	public String freeBrdFormInsert(FreeBrdDTO n, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
	

		
		//작성자 이메일 
		//로그인한 해당 유저의 정보를 뽑아올수 있음  
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		System.out.println("로그인한 유저 이메일"+user.getUser_email());
		n.setUser_email(user.getUser_email());
		// 카테고리
		n.setFree_cat(Integer.parseInt(request.getParameter("freeBrdCat")));
		// 글제목
		n.setFree_brd_title(request.getParameter("title"));

		// 글내용
		n.setFree_brd_content(request.getParameter("content"));
	
		System.out.println("카테고리->" + n.getFree_cat());
		System.out.println("글제목->" + n.getFree_brd_title());
//		System.out.println("업로드 사진 이름->" + n.getFree_brd_image());
		System.out.println("글내용->" + n.getFree_brd_content());
	
		// DB연동해서 클라이언트가 입력한 데이터들을 DB안에 테이블에 넣어주기
		FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
		int seq=0;
		
		try {
			FreeBrd.freeBrdInsert(n);
			System.out.println("게시글 INSERT 완료");
			 
			//Insert후 맨마지막(가장큰) 글번호를 들고오는 함수 
			seq = FreeBrd.afterInsert_SelectDetail();
			System.out.println("글번호 출력"+seq);
		}catch (Exception e) {
			System.out.println("게시글 INSERT 문제발생..."+e.getMessage());
		}
	
	    redirectAttributes.addAttribute("seq",seq);
		 
		return "redirect:freeBrdDetail";
	}


	
	// 글 수정폼 이동 서비스 함수(게시물 상세랑 비슷)
	public FreeBrdDTO freeBrdEdit(String seq) {
		FreeBrdDTO freeBrdDto = null;
		try {
			FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);			
			
			freeBrdDto = FreeBrd.freebrdDetail(seq);
	
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		return freeBrdDto;
	}


	// 글 수정 서비스 함수
	public String freeBrdEditOk(FreeBrdDTO n,String seq, HttpServletRequest request) throws IOException {
		

	
	
		// DTO에 게시판에서 작성한 값 넣어주기
	
		//글번호
		n.setFree_brd_seq(Integer.parseInt(request.getParameter("seq")));
		// 카테고리
		n.setFree_cat(Integer.parseInt(request.getParameter("freeBrdCat")));
		// 글제목
		n.setFree_brd_title(request.getParameter("title"));


		// 글내용
		n.setFree_brd_content(request.getParameter("content"));
		
		// DB연동해서 클라이언트가 입력한 데이터들을 DB안에 테이블에 넣어주기
		FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
		
		try {
			FreeBrd.freeBrdUpdate(n);
			System.out.println("게시글 Update 완료");
	
		}catch (Exception e) {
			System.out.println("게시글 Update 문제발생..."+e.getMessage());
		}
			
		return "redirect:freeBrdDetail";
	}


	 //글삭제하기 서비스 함수
	 public String freeBrdDelete(String seq) throws ClassNotFoundException, SQLException {
		 	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
		 	
			FreeBrd.freeBrdDelete(seq);
			return "redirect:freeBrdList";
	}
}
