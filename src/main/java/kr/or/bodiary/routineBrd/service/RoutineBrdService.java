package kr.or.bodiary.routineBrd.service;


import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.bodiary.routineBrd.dao.RoutineBrdDao;
import kr.or.bodiary.routineBrd.dto.RoutineBoardCommentDto;
import kr.or.bodiary.routineBrd.dto.RoutineBoardUserJoinDto;
import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;
import kr.or.bodiary.user.dto.UserDto;
import kr.or.bodiary.utils.DateUtils;



@Service
public class RoutineBrdService {
private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

		
	//리스트
	public List<RoutineBoardUserJoinDto> routineBoardList(String cp, String ps) throws ClassNotFoundException, SQLException, ParseException {
		
		List<RoutineBoardUserJoinDto> rlist = null;
		int cpage = 1;
		int pagesize = 8;
		
		if(cp != null && !cp.equals("")) {
			cpage = Integer.parseInt(cp);
		}
		
		if(ps != null && !ps.equals("")) {
			pagesize = Integer.parseInt(ps);
		}
		
		int start = (cpage -1)*pagesize;
		
		
		try {
			RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
			rlist = routinebrddao.routineBoardList(start,pagesize);
			for(RoutineBoardUserJoinDto r : rlist) {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(r.getRoutine_brd_regdate());
				String formatDate = DateUtils.formatTimeString(date);
				System.out.println(formatDate);
				r.setRoutine_brd_regdate(formatDate);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rlist;
	}
	
	//오늘의 게시글 불러오기
	public List<RoutineBoardUserJoinDto> getTodayHit() throws ClassNotFoundException, SQLException, ParseException {
		RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
		List<RoutineBoardUserJoinDto> rlist = routinebrddao.getTodayHit();
		for(RoutineBoardUserJoinDto r : rlist) {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(r.getRoutine_brd_regdate());
			String formatDate = DateUtils.formatTimeString(date);
			System.out.println(formatDate);
			r.setRoutine_brd_regdate(formatDate);
		}
		
		return rlist;
	}
	
	//상세
	public RoutineBrdDto routineBrdDetail(int routine_brd_seq) throws ClassNotFoundException, SQLException, ParseException {		
		RoutineBrdDto routinebrddto = null;
		try {
			RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
			routinebrddto = routinebrddao.routineBoardSelect(routine_brd_seq);
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(routinebrddto.getRoutine_brd_regdate());
			String formatDate = DateUtils.formatTimeString(date);
			routinebrddto.setRoutine_brd_regdate(formatDate);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return routinebrddto;
	}
	
	//입력(처리)
	public String routineBrdInsert(RoutineBrdDto routinebrddto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
		List<CommonsMultipartFile> files = routinebrddto.getFiles();
		List<String> filenames = new ArrayList<String>();
		RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
		
		if(files != null && files.size() > 0) {
			for(CommonsMultipartFile multifile : files) {
				String filename = multifile.getOriginalFilename();
				String path = request.getSession().getServletContext().getRealPath("/assets/upload/routineBrdUpload");
				String fpath = path + "\\"+ filename;
				
				if(!filename.equals("")) {
					FileOutputStream fs = new FileOutputStream(fpath);
					fs.write(multifile.getBytes());
					fs.close();
				}
				filenames.add(filename);			
			}
			
		}
		routinebrddto.setBrd_image1(filenames.get(0));
		routinebrddto.setBrd_image2(filenames.get(1));
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		routinebrddto.setUser_email(user.getUser_email());
		
		
		
		String url="";
		try {
			int result = routinebrddao.routineBoardInsert(routinebrddto);
			System.out.println("insert 정상 처리");
			
			if(result > 0) { 
				url = "redirect:routineBrdDetail?routine_brd_seq="+routinebrddto.getRoutine_brd_seq(); 
			} else { 
				url = "redirect:routineBrdInsert";
			}
		} catch (Exception e) {
			System.out.println("Transaction 문제 발생" + e.getMessage());
		}
		return url;
	}
	
	//수정(폼)
	public RoutineBrdDto routineBrdEdit(int routine_brd_seq) throws ClassNotFoundException, SQLException {
		RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
		RoutineBrdDto routinebrddto = routinebrddao.routineBoardSelect(routine_brd_seq);
		return routinebrddto;
	}
	
	//수정(처리)
	@Transactional
	public String routineBrdEdit(RoutineBrdDto routinebrddto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
		
		List<CommonsMultipartFile> files = routinebrddto.getFiles();
		List<String> filenames = new ArrayList<String>();
		RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
		System.out.println("파일" +files);
		System.out.println("루틴게시판"+routinebrddto.toString());
		
		String filename = null;
		if(files != null && files.size() > 0) {
			for(CommonsMultipartFile multifile : files) {
				filename = multifile.getOriginalFilename();
				String path = request.getSession().getServletContext().getRealPath("/assets/upload/routineBrdUpload");
				String fpath = path + "\\"+ filename;
				
				if(!filename.equals("")) {
					FileOutputStream fs = new FileOutputStream(fpath);
					fs.write(multifile.getBytes());
					fs.close();
				}
				filenames.add(filename);			
			}
			
			if(filename.isEmpty() || filename.equals("")) {
				routinebrddto.setBrd_image1(routinebrddao.routineBoardSelect(routinebrddto.getRoutine_brd_seq()).getBrd_image1());
				routinebrddto.setBrd_image2(routinebrddao.routineBoardSelect(routinebrddto.getRoutine_brd_seq()).getBrd_image2());
				
			}else {
				routinebrddto.setBrd_image1(filenames.get(0));
				routinebrddto.setBrd_image2(filenames.get(1));
			}
		
			
		}
		
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		routinebrddto.setUser_email(user.getUser_email());
		
	
		String url="";
		try {
			int result = routinebrddao.routineBoardEdit(routinebrddto);
			
			
			if(result > 0) { 
				url = "redirect:routineBrdDetail?routine_brd_seq="+routinebrddto.getRoutine_brd_seq(); 
			} else { 
				url = "redirect:routineBrdEdit?routine_brd_seq="+routinebrddto.getRoutine_brd_seq(); 
			}
		} catch (Exception e) {
			System.out.println("Transaction 문제 발생" + e.getMessage());
		}
		return url;
	}
	
	//삭제
	public String routineBoardDelete(int routine_brd_seq) throws ClassNotFoundException, SQLException {
		RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
		routinebrddao.routineBoardDelete(routine_brd_seq);
		return "redirect:routineBrdList";
	}
	
	//조회수 증가
	public int updateHit(int routine_brd_seq) throws ClassNotFoundException, SQLException {
		RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
		return routinebrddao.updateHit(routine_brd_seq);	
	}
	
	
		//좋아요 했는지 체크하기
		public int checkRoutineBrdLike(int routine_brd_seq, String user_email) throws ClassNotFoundException, SQLException {
			RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
			return routinebrddao.checkRoutineBrdLike(routine_brd_seq, user_email);
		}
		//좋아요 추가하기
		@RequestMapping("/addRoutineBrdLike")
		public int addRoutineBrdLike(int routine_brd_seq, String user_email) throws ClassNotFoundException, SQLException {
			RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
			return routinebrddao.addRoutineBrdLike(routine_brd_seq, user_email);
		}
		
		//좋아요 취소하기
		@RequestMapping("/cancleRoutineBrdLike")
		public int cancleRoutineBrdLike(int routine_brd_seq, String user_email) throws ClassNotFoundException, SQLException {
			RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
			int routine_brd_like_seq = routinebrddao.getRoutineBrdLikeSeq(routine_brd_seq, user_email);
			
			return routinebrddao.cancleRoutineBrdLike(routine_brd_like_seq, routine_brd_seq);
		}
		
		//좋아요 수 카운트
		@RequestMapping("/countRoutineBrdLike")
		public int countRoutineBrdLike(int routine_brd_seq) throws ClassNotFoundException, SQLException {
			RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
			return routinebrddao.countRoutineBrdLike(routine_brd_seq);
		}
   
	  
	//------------------------------------------- 댓글 -------------------------------------------------
	   //리스트 불러오기 
		public List<RoutineBoardCommentDto> routineCmtList (int routine_brd_seq) throws IOException, ClassNotFoundException, SQLException{
			RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
			List<RoutineBoardCommentDto> rCmtList = null; 
			try {
				rCmtList = routinebrddao.routineCmtList(routine_brd_seq);
				
				//날짜 변경
				for(RoutineBoardCommentDto r : rCmtList) {
					Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(r.getRoutine_cmt_date());
					String formatDate = DateUtils.formatTimeString(date);
					System.out.println(formatDate);
					r.setRoutine_cmt_date(formatDate);
				}
			} catch (Exception e) {
				e.getMessage();
			}
			
			return rCmtList;
		}

	   //인서트 
	   public List<RoutineBoardCommentDto> routineCmtInsert (RoutineBoardCommentDto routineCmtDto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException, ParseException {
		   RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
//		   int returnselect = 0;
		   System.out.println("댓글 인서트 서비스 try문 직전");
		   
		   routineCmtDto.setRoutine_brd_seq(Integer.parseInt(request.getParameter("routine_brd_seq")));
		   routineCmtDto.setRoutine_cmt(request.getParameter("routine_cmt"));
		   routineCmtDto.setUser_email(request.getParameter("user_email"));
		   routineCmtDto.setUser_nickname(request.getParameter("user_nickname"));
		   
		   try {
			   int cmtinsert = routinebrddao.routineCmtInsert(routineCmtDto);
			   System.out.println("cmt 인서트 했나요? : "+cmtinsert);
			   //인서트 후 refer 번호 update 처리
			   
			   int cmtupdate=routinebrddao.routineCmtReferUpdate(routineCmtDto.getRoutine_cmt_seq());
			   System.out.println("cmt 업데이트 했나요? : "+cmtupdate);
			   
			
			} catch (Exception e) {
				e.getMessage();
			}
		   
		   System.out.println(routinebrddao.routineCmtList(Integer.parseInt(request.getParameter("routine_brd_seq"))));
		   
		   List<RoutineBoardCommentDto> rCmtList = routinebrddao.routineCmtList(Integer.parseInt(request.getParameter("routine_brd_seq")));
		   //날짜변경
		   for(RoutineBoardCommentDto r : rCmtList) {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(r.getRoutine_cmt_date());
				String formatDate = DateUtils.formatTimeString(date);
				System.out.println(formatDate);
				r.setRoutine_cmt_date(formatDate);
			}
		   
		   return rCmtList;
	   }
	   //업데이트
	   public List<RoutineBoardCommentDto> routineCmtModifyUpdate (RoutineBoardCommentDto routineCmtDto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException, ParseException {
		   RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
		   routineCmtDto.setRoutine_cmt(request.getParameter("routine_cmt"));
		   try {
			int cmtUpdate = routinebrddao.routineCmtModifyUpdate(routineCmtDto);
			System.out.println("업데이트 수정 여부 : " + cmtUpdate);
			
		   } catch (Exception e) {
			   e.getMessage();
		   }
		   List<RoutineBoardCommentDto> rCmtList = routinebrddao.routineCmtList(Integer.parseInt(request.getParameter("routine_brd_seq")));
		   //날짜변경
		   for(RoutineBoardCommentDto r : rCmtList) {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(r.getRoutine_cmt_date());
				String formatDate = DateUtils.formatTimeString(date);
				System.out.println(formatDate);
				r.setRoutine_cmt_date(formatDate);
			}
		   
		   return rCmtList;
	   }
	   //삭제
	   public List<RoutineBoardCommentDto> routineCmtDelete (RoutineBoardCommentDto routineCmtDto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException, ParseException {
		   RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
		   routineCmtDto.setRoutine_cmt(request.getParameter("routine_cmt"));
		   try {
			   int cmtDelete = routinebrddao.routineCmtDelete(routineCmtDto);
			   System.out.println("삭제 여부 : " + cmtDelete);
			   
		   } catch (Exception e) {
			   e.getMessage();
		   }
		   List<RoutineBoardCommentDto> rCmtList = routinebrddao.routineCmtList(Integer.parseInt(request.getParameter("routine_brd_seq")));
		   //날짜변경
		   for(RoutineBoardCommentDto r : rCmtList) {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(r.getRoutine_cmt_date());
				String formatDate = DateUtils.formatTimeString(date);
			
				r.setRoutine_cmt_date(formatDate);
			}
		   
		   return rCmtList;
	   }
	   
	   
	   
	   
	   
	   //대댓 인서트 
	   public List<RoutineBoardCommentDto> routineReCmtInsert (RoutineBoardCommentDto routineCmtDto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException, ParseException {
		   RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
		   routineCmtDto.setRoutine_cmt_ref(Integer.parseInt(request.getParameter("routine_cmt_ref")));
		   try {
			   int recmtinsert = routinebrddao.routineReCmtInsert(routineCmtDto);
			   System.out.println("reCmt 인서트 했나요? : " + recmtinsert);

			} catch (Exception e) {
				e.getMessage();
			}
		   List<RoutineBoardCommentDto> rCmtList = routinebrddao.routineCmtList(Integer.parseInt(request.getParameter("routine_brd_seq")));
		   //날짜변경
		   for(RoutineBoardCommentDto r : rCmtList) {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(r.getRoutine_cmt_date());
				String formatDate = DateUtils.formatTimeString(date);
				System.out.println(formatDate);
				r.setRoutine_cmt_date(formatDate);
			}
		   
		   return rCmtList;
	   }


}