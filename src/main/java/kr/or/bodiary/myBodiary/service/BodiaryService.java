package kr.or.bodiary.myBodiary.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bodiary.freeBrd.dao.FreeBrdDao;
import kr.or.bodiary.freeBrd.dao.FreeBrdReplyDao;
import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.freeBrd.dto.FreeBrdReplyDTO;
import kr.or.bodiary.freeBrd.dto.Pagination;
import kr.or.bodiary.myBodiary.dao.BodiaryDao;
import kr.or.bodiary.myBodiary.dto.BodiaryDto;
import kr.or.bodiary.myBodiary.dto.DailyMealDto;
import kr.or.bodiary.myBodiary.dto.DailyMealFoodJoinDto;
import kr.or.bodiary.myBodiary.dto.FoodDto;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;
import kr.or.bodiary.routineBrd.dao.RoutineBrdDao;
import kr.or.bodiary.routineBrd.dto.RoutineBoardUserJoinDto;
import kr.or.bodiary.user.dto.UserDto;


@Service
public class BodiaryService {
	
private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	//카테고리(자유) 게시판 전체 게시물 총개수 불러오기 
	public int allFreeBrdCount_M(String user_email) throws Exception {
		FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
		return FreeBrd.getFreeBoardListCnt_M(user_email);
	}
	
	//카테고리(자유) 게시판 전체 게시물 List 형태로 불러오기 
	public List<FreeBrdDTO> allFreeBrd_P(Pagination pagination) throws Exception {
		FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
		return FreeBrd.allFreeBrd_P(pagination);
	}
	
	 //내가쓴글(자유)게시판 글삭제하기 서비스 함수
	 public String freeBrdDelete(String seq) throws ClassNotFoundException, SQLException {
		 	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
			FreeBrd.freeBrdDelete(seq);
			return "redirect:myHistory";
	}
	 
	 //내가쓴 댓글(자유게시판) 전체 가져오기 
	 public List<FreeBrdReplyDTO> replyList(String user_email) throws Exception {
		 	FreeBrdReplyDao FreeBrdReply = sqlsession.getMapper(FreeBrdReplyDao.class);
			return FreeBrdReply.c_replyList(user_email);
	}
	 
	//루틴 자랑 게시판 리스트 가져오기 
	public List<RoutineBoardUserJoinDto> routineBoardList(String user_email) throws ClassNotFoundException, SQLException {
		
		List<RoutineBoardUserJoinDto> rlist = null;
		
		try {
			BodiaryDao routinebrddao = sqlsession.getMapper(BodiaryDao.class);
			rlist = routinebrddao.MyroutineBoardList(user_email);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rlist;
	} 
	 
	//음식 검색하기
	public List<FoodDto> foodNameSearch(String food_name) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		System.out.println("서비스");
		return bodiarydao.foodNameSearch(food_name);
	}
	
	//루틴 정보 불러오기
	public List<RoutineJoinDto> getRoutine(int routine_cart_seq) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		System.out.println("루틴");
		return bodiarydao.getRoutine(routine_cart_seq);
	}
	
	//식단 카트 번호 추가
	public int insertMealCart(DailyMealDto dailymealdto) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		return bodiarydao.insertMealCart(dailymealdto);
	}
	
	//식단 작성하기
	public int writeDailyMeal(List<DailyMealDto> list) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		return bodiarydao.writeDailyMeal(list);
	}
	
	
	//트랜잭션 처리
	//일지 작성
	@Transactional(rollbackFor = {RuntimeException.class, SQLException.class})

	public String writeBodiary(DailyMealDto dailymealdto, BodiaryDto bodiarydto, HttpServletRequest request) throws IOException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
	
		
		String filename = bodiarydto.getFile().getOriginalFilename();
		
		System.out.println(filename);
		String path = request.getSession().getServletContext().getRealPath("/assets/upload/myBodiaryUpload");
				
		String fpath = path + "\\" + filename;
		FileOutputStream fs = new FileOutputStream(fpath);
		fs.write(bodiarydto.getFile().getBytes());
		fs.close();
		String url = "";	
		//파일명
		bodiarydto.setDiary_main_img(filename);
		try {
			
			List<DailyMealDto> list = dailymealdto.getDailyMealList();
			
			bodiarydao.insertMealCart(dailymealdto);
			
			for(DailyMealDto d : list) {
				d.setMeal_cart_seq(dailymealdto.getMeal_cart_seq());
			}
			
			bodiarydao.writeDailyMeal(list);
			
			bodiarydto.setMeal_cart_seq(dailymealdto.getMeal_cart_seq());
			
			UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
			bodiarydto.setUser_email(user.getUser_email());
			int diary_seq = bodiarydao.writeBodiary(bodiarydto);
			System.out.println(diary_seq);
			user.setUser_weight(bodiarydto.getDiary_today_weight());
			url = "redirect:myBodiaryDetail?diary_seq="+diary_seq;
			/*
			 * System.out.println("일지 번호 : " + bodiarydto.getDiary_seq());
			 * System.out.println("식단 카트 번호 : " + dailymealdto.getMeal_cart_seq());
			 * if(result > 0) { url =
			 * "redirect:myBodiaryDetail?diary_seq="+bodiarydto.getDiary_seq(); } else { url
			 * = "redirect:myBodiaryForm"; }
			 */

		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		
		return url;
	}
	

	//일지 수정하기
	@Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
	public String updateBodiary(DailyMealDto dailymealdto, BodiaryDto bodiarydto, HttpServletRequest request) throws IOException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		/*
		 * if(bodiarydto.getDiary_pubchk() == null) { bodiarydto.setDiary_pubchk("N"); }
		 * else { bodiarydto.setDiary_pubchk("Y"); }
		 */
		String filename = bodiarydto.getFile().getOriginalFilename();
		
		System.out.println(filename);
		String path = request.getSession().getServletContext().getRealPath("/assets/upload/myBodiaryUpload");
				
		String fpath = path + "\\" + filename;
		FileOutputStream fs = new FileOutputStream(fpath);
		fs.write(bodiarydto.getFile().getBytes());
		fs.close();
		String url = "";	
		//파일명
		bodiarydto.setDiary_main_img(filename);
		System.out.println(bodiarydto.getDiary_main_img());
		try {
			
			List<DailyMealDto> list = dailymealdto.getDailyMealList();
			bodiarydao.insertMealCart(dailymealdto);
			
			for(DailyMealDto d : list) {
				d.setMeal_cart_seq(dailymealdto.getMeal_cart_seq());
			}
			System.out.println(list.toString());
			bodiarydao.writeDailyMeal(list);
			
			bodiarydto.setMeal_cart_seq(dailymealdto.getMeal_cart_seq());
			UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
			bodiarydto.setUser_email(user.getUser_email());
			System.out.println(bodiarydto.toString());
			int result = bodiarydao.updateBodiary(bodiarydto);
			
			 System.out.println("식단 카트 번호 : " + dailymealdto.getMeal_cart_seq());
			if(result > 0) { 
				url = "redirect:myBodiaryDetail?diary_seq="+bodiarydto.getDiary_seq(); 
			} else { 
				String referer = request.getHeader("Referer");
				url = "redirect:"+referer;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		return url;
	}
	
	//일지 삭제하기
	public String myBodiaryDelete(String diary_seq) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		bodiarydao.deleteBodiary(Integer.parseInt(diary_seq));
		return "redirect:myBodiaryMain";
	}
	
	
	//루틴 리스트 아이디로 불러오기
	public List<RoutineJoinDto> getRoutineListById(String user_email) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		return bodiarydao.getRoutineListById(user_email);
	}
	
	//일지 상세정보 
	public BodiaryDto getBodiary(String diary_seq) throws ClassNotFoundException, SQLException { 
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);		
		return bodiarydao.getBodiary(Integer.parseInt(diary_seq)); 
		}
	
	//식단 불러오기
	public List<DailyMealFoodJoinDto> getDailyMeal(int meal_cart_seq) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		return bodiarydao.getDailyMeal(meal_cart_seq);
	}
	
	//일지 리스트 불러오기 (동적쿼리로 메인 뿌려줄건 최신순 4개만)
	public List<BodiaryDto> getBodiaryList(String user_email, String pagesize) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_email", user_email);
		map.put("pagesize", pagesize);
		return bodiarydao.getBodiaryList(map);
	}
	
	//오늘 일지 개수 확인하기
	public int todatBodiaryCount(String user_email) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		return bodiarydao.todatBodiaryCount(user_email);
	}
	
	
	
}
