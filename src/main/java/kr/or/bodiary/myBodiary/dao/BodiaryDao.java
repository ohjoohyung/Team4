package kr.or.bodiary.myBodiary.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import kr.or.bodiary.myBodiary.dto.DailyMealFoodJoinDto;
import kr.or.bodiary.myBodiary.dto.FoodDto;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;
import kr.or.bodiary.myBodiary.dto.BodiaryDto;
import kr.or.bodiary.myBodiary.dto.DailyMealDto;



public interface BodiaryDao {
	//일지 작성
	public int writeBodiary(BodiaryDto bodiarydto) throws ClassNotFoundException, SQLException;
	
	//일지 상세정보
	public BodiaryDto getBodiary(int diary_seq) throws ClassNotFoundException, SQLException;
	
	//음식 검색하기
	public List<FoodDto> foodNameSearch(String food_name) throws ClassNotFoundException, SQLException;

	//식단 장바구니 시퀀스 추가
	public int insertMealCart(DailyMealDto dailymealdto) throws ClassNotFoundException, SQLException;
	
	//식단 추가
	public int writeDailyMeal(List<DailyMealDto> list) throws ClassNotFoundException, SQLException;
	
	//루틴 불러오기 (나중에는 루틴 카트 번호를 파라미터로 추가)
	public List<RoutineJoinDto> getRoutine(int routine_cart_seq) throws ClassNotFoundException, SQLException;
	
	//루틴 리스트 불러오기 (나중에는 유저 이메일로 판별하고 불러와야함)
	public List<RoutineJoinDto> getRoutineListById(String user_email) throws ClassNotFoundException, SQLException;
	
	//식단 불러오기
	public List<DailyMealFoodJoinDto> getDailyMeal(int meal_cart_seq) throws ClassNotFoundException, SQLException;
	
	//일지 리스트 불러오기
	public List<BodiaryDto> getBodiaryList(HashMap<String, String> map) throws ClassNotFoundException, SQLException;
	
	//일지 수정하기
	public int updateBodiary(BodiaryDto bodiarydto) throws ClassNotFoundException, SQLException;
	
	//일지 삭제하기
	public int deleteBodiary(int diary_seq) throws ClassNotFoundException, SQLException;
	
	//오늘 일지 작성했는지 확인하기
	public int todatBodiaryCount(String user_email) throws ClassNotFoundException, SQLException;
}
