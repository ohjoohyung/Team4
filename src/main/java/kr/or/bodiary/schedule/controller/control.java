package kr.or.bodiary.schedule.controller;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

public class control { 
		
	
	//@Scheduled(cron = "0 0 1 ? * 6L")
	//@Scheduled(cron="0 0 0/1 * * *") 
	// 0 0 20 ? * MON-FRI

	@Scheduled(cron="0 0 20 ? * FRI") 
	public void autoUpdate() {
		
		  int start_num = 1; int end_num = 300; 
		  ControlFoodDAO fDao=new ControlFoodDAO(); 
		  try {
			  for(int i = 0; i<100;i++) {
				  
				  System.out.println("start_num: "+start_num); 
				  System.out.println("end_num: "+ end_num); 
				  ControlFoodJson fJson = new ControlFoodJson(); // 기상데이터를 JSON형태로 받아  VillageWeather에 저장 
				  List<FoodDTO> flist = fJson.getFood(start_num, end_num);
				  // 데이터베이스에 접속에 관련하는객체를 만들고 데이터베이스에 입력 //System.out.println("flist: "+flist);
				  fDao = new ControlFoodDAO(); fDao.insertFood(flist); start_num = end_num+1;
				  end_num = 300*(i+2); System.out.println("start_num: "+start_num);
				  System.out.println("end_num: "+ end_num); }
		  }catch (Exception e) {
			// TODO: handle exception
		}
		  
		 
		}
	}


