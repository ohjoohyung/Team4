package kr.or.bodiary.routineBrd.service;


import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.bodiary.routineBrd.dao.RoutineBrdDao;
import kr.or.bodiary.routineBrd.dto.RoutineBoardCommentDto;
import kr.or.bodiary.routineBrd.dto.RoutineBoardUserJoinDto;
import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;
import kr.or.bodiary.user.dto.UserDto;



@Service
public class RoutineBrdService {
private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

		
	//리스트
	public List<RoutineBoardUserJoinDto> routineBoardList(String cp, String ps) throws ClassNotFoundException, SQLException {
		
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rlist;
	}
	
	//오늘의 게시글 불러오기
	public List<RoutineBoardUserJoinDto> getTodayHit() throws ClassNotFoundException, SQLException {
		RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
		return routinebrddao.getTodayHit();
	}
	
	//상세
	public RoutineBrdDto routineBrdDetail(int routine_brd_seq) throws ClassNotFoundException, SQLException {		
		RoutineBrdDto routinebrddto = null;
		try {
			RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
			routinebrddto = routinebrddao.routineBoardSelect(routine_brd_seq);
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
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		routinebrddto.setUser_email(user.getUser_email());
		routinebrddto.setBrd_image1(filenames.get(0));
		routinebrddto.setBrd_image2(filenames.get(1));
		
		RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
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
		
		RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
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
   
   //------------------------------------------- 댓글 -------------------------------------------------
   
   //인서트 
   public RoutineBoardCommentDto routineCmtInsert (RoutineBoardCommentDto routineCmtDto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
	   RoutineBrdDao routinebrddao = sqlsession.getMapper(RoutineBrdDao.class);
//	   int returnselect = 0;
	   System.out.println("댓글 인서트 서비스 try문 직전");
	   
	   routineCmtDto.setRoutine_brd_seq(Integer.parseInt(request.getParameter("routine_brd_seq")));
	   routineCmtDto.setRoutine_cmt(request.getParameter("routine_cmt"));
	   routineCmtDto.setUser_email(request.getParameter("user_email"));
	   
	   try {
		   int cmtinsert = routinebrddao.routineCmtInsert(routineCmtDto);
		   System.out.println("cmt 인서트 했나요? : "+cmtinsert);
		   //인서트 후 refer 번호 update 처리
		   
		   int cmtupdate=routinebrddao.routineCmtReferUpdate(routineCmtDto.getRoutine_cmt_seq());
		   System.out.println("cmt 업데이트 했나요? : "+cmtupdate);
		   
		
		} catch (Exception e) {
			e.getMessage();
		}
	   return routinebrddao.routineCmtSelect(routineCmtDto.getRoutine_cmt_seq());
   }
  

}