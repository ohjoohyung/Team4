package kr.or.bodiary.myBodiary.dao;

import java.sql.SQLException;
import java.util.List;


import kr.or.bodiary.myBodiary.dto.FoodDto;
import kr.or.bodiary.myBodiary.dto.bodiaryDTO;


public interface BodiaryDao {
	//일지 작성
	public int writeBodiary(bodiaryDTO bodiarydto) throws ClassNotFoundException, SQLException;
	
	//일지 상세정보
	public bodiaryDTO getBodiary(String diary_seq) throws ClassNotFoundException, SQLException;
	
	//음식 검색하기
	public List<FoodDto> foodNameSearch(String food_name) throws ClassNotFoundException, SQLException;

}
