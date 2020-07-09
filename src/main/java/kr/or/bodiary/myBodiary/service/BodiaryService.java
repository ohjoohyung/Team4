package kr.or.bodiary.myBodiary.service;

import java.io.FileNotFoundException;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.bodiary.chat.dto.NotYet;
import kr.or.bodiary.myBodiary.dao.BodiaryDao;
import kr.or.bodiary.myBodiary.dto.DailyMealFoodJoinDto;
import kr.or.bodiary.myBodiary.dto.FoodDto;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;
import kr.or.bodiary.myBodiary.dto.bodiaryDTO;
import kr.or.bodiary.myBodiary.dto.dailyMealDTO;
import kr.or.bodiary.user.dao.UserDao;
import kr.or.bodiary.user.dto.UserDto;


@Service
public class BodiaryService {
	
private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	//음식 검색하기
	public List<FoodDto> foodNameSearch(String food_name) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		System.out.println("서비스");
		return bodiarydao.foodNameSearch(food_name);
	}
	
	//루틴 정보 불러오기
	public List<RoutineJoinDto> getRoutine(String routine_cart_seq) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		System.out.println("루틴");
		return bodiarydao.getRoutine(routine_cart_seq);
	}
	
	//식단 카트 번호 추가
	public int insertMealCart(dailyMealDTO dailymealdto) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		return bodiarydao.insertMealCart(dailymealdto);
	}
	
	//식단 작성하기
	public int writeDailyMeal(List<dailyMealDTO> list) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		return bodiarydao.writeDailyMeal(list);
	}
	
	
	//트랜잭션 처리
	@Transactional(rollbackFor=Exception.class)
	public String writeBodiary(dailyMealDTO dailymealdto, bodiaryDTO bodiarydto, HttpServletRequest request) throws IOException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		//실제 글쓰기 처리
		//private CommonsMultipartFile file;
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
			
			List<dailyMealDTO> list = dailymealdto.getDailyMealList();
			bodiarydao.insertMealCart(dailymealdto);
			
			for(dailyMealDTO d : list) {
				d.setMeal_cart_seq(dailymealdto.getMeal_cart_seq());
			}
			
			bodiarydao.writeDailyMeal(list);
			
			bodiarydto.setMeal_cart_seq(dailymealdto.getMeal_cart_seq());
			
			int result = bodiarydao.writeBodiary(bodiarydto);
			
			 System.out.println("일지 번호 : " + bodiarydto.getDiary_seq());
			 System.out.println("식단 카트 번호 : " + dailymealdto.getMeal_cart_seq());
			if(result > 0) { 
				url = "redirect:myBodiaryDetail?diary_seq="+bodiarydto.getDiary_seq(); 
			} else { 
				url = "redirect:myBodiaryForm";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return url;
	}
	
	
	public List<RoutineJoinDto> getRoutineListById() throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		return bodiarydao.getRoutineListById();
	}
	
	//일지 상세정보 
	public bodiaryDTO getBodiary(String diary_seq) throws ClassNotFoundException, SQLException { 
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class); 
		return bodiarydao.getBodiary(diary_seq); 
		}
	
	//식단 불러오기
	public List<DailyMealFoodJoinDto> getDailyMeal(int meal_cart_seq) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		return bodiarydao.getDailyMeal(meal_cart_seq);
	}
	
	//일지 리스트 불러오기
	public List<bodiaryDTO> getBodiaryList(String user_email) throws ClassNotFoundException, SQLException {
		BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class);
		return bodiarydao.getBodiaryList(user_email);
	}
	
	//유저 정보 가져오기 (나중에 시큐리티 사용하면 어떻게 해야될지 생각)
	/*
	 * public userDTO getUser(String user_email) throws ClassNotFoundException,
	 * SQLException { UserDao userdao = sqlsession.getMapper(UserDao.class); return
	 * userdao.getUser(user_email); }
	 */
	//일지 작성
	/*
	 * public String writeBodiary(bodiaryDTO bodiarydto, HttpServletRequest request)
	 * throws ClassNotFoundException, SQLException, IOException {
	 * System.out.println(bodiarydto);
	 * 
	 * List<CommonsMultipartFile> files = bodiarydto.getFiles(); List<String>
	 * filenames = new ArrayList<String>(); //파일명관리
	 * 
	 * if(files != null && files.size() > 0) { //최소 1개의 업로드가 있다면
	 * for(CommonsMultipartFile multifile : files) { String filename =
	 * multifile.getOriginalFilename(); String path =
	 * request.getSession().getServletContext().getRealPath(
	 * "/assets/upload/myBodiaryUpload");
	 * 
	 * String fpath = path + "\\"+ filename;
	 * 
	 * if(!filename.equals("")) { //실 파일 업로드 FileOutputStream fs = new
	 * FileOutputStream(fpath); fs.write(multifile.getBytes()); fs.close(); }
	 * filenames.add(filename); //파일명을 별도 관리 (DB insert) }
	 * 
	 * }
	 * 
	 * //security 바꿨을때 사용 //bodiaryDTO.setWriter(principal.getName());
	 * 
	 * //DB 파일명 저장 bodiarydto.setDiary_main_img(filenames.get(0));
	 * bodiarydto.setDiary_sub_img1(filenames.get(1));
	 * bodiarydto.setDiary_sub_img1(filenames.get(2));
	 * bodiarydto.setDiary_sub_img1(filenames.get(3));
	 * 
	 * BodiaryDao bodiarydao = sqlsession.getMapper(BodiaryDao.class); int result =
	 * bodiarydao.writeBodiary(bodiarydto);
	 * 
	 * String url = ""; if(result > 0) { url =
	 * "redirect:myBodiaryDetail?diary_seq="+bodiarydto.getDiary_seq(); } else { url
	 * = "redirect:myBodiaryForm"; }
	 * 
	 * 
	 * 
	 * return url; }
	 * 
	 * //일지 상세정보 public bodiaryDTO getBodiary(String diary_seq) throws
	 * ClassNotFoundException, SQLException { BodiaryDao bodiarydao =
	 * sqlsession.getMapper(BodiaryDao.class); return
	 * bodiarydao.getBodiary(diary_seq); }
	 */
}
