package kr.or.bodiary.myBodiary.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.myBodiary.dto.DailyMealFoodJoinDto;
import kr.or.bodiary.myBodiary.dto.FoodDto;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;
import kr.or.bodiary.myBodiary.dto.bodiaryDTO;
import kr.or.bodiary.myBodiary.dto.dailyMealDTO;



public interface BodiaryDao {
	//일지 작성
	public int writeBodiary(bodiaryDTO bodiarydto) throws ClassNotFoundException, SQLException;
	
	//일지 상세정보
	public bodiaryDTO getBodiary(String diary_seq) throws ClassNotFoundException, SQLException;
	
	//음식 검색하기
	public List<FoodDto> foodNameSearch(String food_name) throws ClassNotFoundException, SQLException;

	//식단 장바구니 시퀀스 추가
	public int insertMealCart(dailyMealDTO dailymealdto) throws ClassNotFoundException, SQLException;
	
	//식단 추가
	public int writeDailyMeal(List<dailyMealDTO> list) throws ClassNotFoundException, SQLException;
	
	//루틴 불러오기 (나중에는 루틴 카트 번호를 파라미터로 추가)
	public List<RoutineJoinDto> getRoutine(String routine_cart_seq) throws ClassNotFoundException, SQLException;
	
	//루틴 리스트 불러오기 (나중에는 유저 이메일로 판별하고 불러와야함)
	public List<RoutineJoinDto> getRoutineListById(String user_email) throws ClassNotFoundException, SQLException;
	
	//식단 불러오기
	public List<DailyMealFoodJoinDto> getDailyMeal(String meal_cart_seq) throws ClassNotFoundException, SQLException;
	
	//일지 리스트 불러오기
	public List<bodiaryDTO> getBodiaryList(String user_email) throws ClassNotFoundException, SQLException;
	
	//일지 수정하기
	public int updateBodiary(bodiaryDTO bodiarydto) throws ClassNotFoundException, SQLException;
	
	//일지 삭제하기
	public int deleteBodiary(String diary_seq) throws ClassNotFoundException, SQLException;
}
