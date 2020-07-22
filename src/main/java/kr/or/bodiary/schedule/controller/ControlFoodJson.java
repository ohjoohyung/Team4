package kr.or.bodiary.schedule.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;





public class ControlFoodJson {

		List<FoodDTO> flist = new ArrayList<FoodDTO>();   
		public List<FoodDTO> getFood(int start_num, int end_num) {
			// JSON데이터를 요청하는 URLstr을 만듭니다.
			System.out.println("start_num: "+start_num+"end_num: "+end_num);
	       System.out.println("getFood탐");
	       String urlStr = "http://openapi.foodsafetykorea.go.kr/api/72965fdf49ec43d6a829/I2790/json/"+start_num+"/"+end_num;
			
	        // 결과 데이터를 저장할 동내기상객체를 만듭니다.
	       System.out.println("FoodDTO 생성");
	        try {
	        	URL url = new URL(urlStr); // 완성된 urlStr을 사용해서 URL 만들어 해당 데이터를 가져옵니다.
	            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
	            String line = "";
	            String result="";
	            //버퍼에 있는 정보를 문자열로 변환.
	            while((line=bf.readLine())!=null){ //bf 에 있는값을 읽어와서 하나의 문자열로 만듭니다.
	                result=result.concat(line);
	            }
	            //System.out.println(result);
	            //System.out.println("parsing 시작");
	            //문자열을 JSON으로 파싱합니다. 마지막 배열형태로 저장된 데이터까지 파싱해냅니다.
	            JSONParser jsonParser = new JSONParser();
	            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);
	            
	            System.out.println("JsonObj:"+jsonObj);
	            
	            JSONObject parse_response = (JSONObject) jsonObj.get("I2790");
	            System.out.println(parse_response);
	            JSONArray foodInfo = (JSONArray) parse_response.get("row");// items로 부터 itemlist 를 받아오기 itemlist : 뒤에 [ 로 시작하므로 jsonarray이다.
	            System.out.println(foodInfo);
	            System.out.println("parsing 끝");
//	            
	    		
	    		 		
	            JSONObject obj;
	            for(int i = 0; i < foodInfo.size(); i++) {
	            	FoodDTO f = new FoodDTO();
	            	obj = (JSONObject) foodInfo.get(i); // 해당 item을 가져옵니다.
//	            	System.out.println(obj.get("DESC_KOR"));
//	            	System.out.println(obj.get("NUTR_CONT1"));
	            	f.setFood_name(obj.get("DESC_KOR").toString());	
	            	f.setFood_cal(obj.get("NUTR_CONT1").toString());
	            	System.out.println(f.getFood_name());
	            	System.out.println(f.getFood_cal());
	            	flist.add(f);
	            	
	            }
	            System.out.println("flist add 끝");
	            
			} catch (MalformedURLException e) {
				System.out.println("MalformedURLException : " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IOException : " + e.getMessage());
			} catch (ParseException e) {
				System.out.println("ParseException : " + e.getMessage());		
			}
	        
	        
			return flist;// 모든값이 저장된 VillageWeather객체를 반환합니다.
	    }
}
