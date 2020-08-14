package kr.or.bodiary.myBodiary.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.freeBrd.dto.FreeBrdReplyDTO;
import kr.or.bodiary.myBodiary.dto.BodiaryDto;
import kr.or.bodiary.myBodiary.dto.DailyMealDto;
import kr.or.bodiary.myBodiary.dto.DailyMealFoodJoinDto;
import kr.or.bodiary.myBodiary.dto.FoodDto;
import kr.or.bodiary.myBodiary.dto.RoutineJoinDto;
import kr.or.bodiary.myBodiary.service.BodiaryService;
import kr.or.bodiary.routineBrd.dto.RoutineBoardCommentDto;
import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;
import kr.or.bodiary.routineBrd.service.RoutineBrdService;
import kr.or.bodiary.user.dto.UserDto;
import kr.or.bodiary.utils.DateUtils;


@Controller
public class BodiaryController {

     private BodiaryService bodiaryservice;
     
     @Autowired 
     public void setBodiaryservice(BodiaryService bodiaryservice) {
    	 this.bodiaryservice = bodiaryservice; 
     }
     
   //내가 쓴글,댓글 보기(루틴자랑) 
    /**
     * @Method Name : myHistoryRoutine
     * @작성일 : 2020. 7. 18.
     * @작성자 : 오동률
     * @변경이력 :
     * @Method 설명 : 자신이 작성한 루틴 자랑게시판 글과, 댓글을 들고 페이지로 이동
     * @param request
     * @param model
     * @return myBodiary/myHistoryRoutine
     **/
     @RequestMapping("/myHistoryRoutine")
     public String myHistoryRoutine(HttpServletRequest request,Model model) throws Exception {
       UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
        String user_email = user.getUser_email();
        System.out.println("유저 이메일"+user_email);
        
       //내가 쓴글 가져오기(루틴자랑게시판)
        List<RoutineBrdDto> routineBrdList = bodiaryservice.myHistoryRoutineBrd(user_email);
        
        //내가 댓글 가져오기(루틴자랑게시판 댓글)
        List<RoutineBoardCommentDto> routineBrdReplyList = bodiaryservice.myHistoryRoutineBrdReply(user_email);
        
        model.addAttribute("routineBrdList",routineBrdList);
        model.addAttribute("routineBrdReplyList",routineBrdReplyList);
        
        return "myBodiary/myHistoryRoutine";
     }
     
     
     //내가 쓴글,댓글 보기(자유게시판) 
     /**
      * @Method Name : myHistory
      * @작성일 : 2020. 7. 18.
      * @작성자 : 오동률
      * @변경이력 :
      * @Method 설명 : 자신이 작성한 자유게시판 글과, 댓글을 들고 페이지로 이동
      * @param request
      * @param model
      * @return String
      **/
     @RequestMapping("/myHistory")
     public String myHistory(HttpServletRequest request
                       ,Model model
                       ) throws Exception {
        
        UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
        String user_email = user.getUser_email();
        System.out.println("유저 이메일"+user_email);
        

     
        //내가쓴 글 가져오기(자유게시판)
        List<FreeBrdDTO> freeBrdList = bodiaryservice.myHistoryFreeBrd(user_email);
        
        //내가쓴 댓글 가져오기(자유게시판 댓글)
        List<FreeBrdReplyDTO> freeBrdReplyList = bodiaryservice.myHistoryFreeBrdReply(user_email);


        model.addAttribute("freeBrdReplyList",freeBrdReplyList);
        model.addAttribute("freeBrdList",freeBrdList);
        
        
        return "myBodiary/myHistory";
     }

         //내가 쓴글 삭제(자유게시판)
     /**
      * @Method Name : myHistoryDelete
      * @작성일 : 2020. 7. 18.
      * @작성자 : 오동률
      * @변경이력 :
      * @Method 설명 : 자신이 작성한 자유게시판 글 삭제
      * @param seq
      * @return String
      **/
         @RequestMapping("/myHistoryDelete")
         public String myHistoryDelete(String seq) {
            String url="redirect:myBodiary/myHistory";
            
            try {
                     url = bodiaryservice.freeBrdDelete(seq);
            }catch (Exception e) {
                     System.out.println("에러발생...");
                      System.out.println(e.getMessage());
            }
            
            //예외 발생에 상관없이 목록 페이지 새로고침 처리
            return url;
         }
         
         
     
   
   //일지 컨트롤러
         /**
          * @Method Name : myBodiaryMain
          * @작성일 : 2020. 7. 6.
          * @작성자 : 오주형
          * @변경이력 :
          * @Method 설명 : 오늘 작성한 일지 수와 가장 최근 작성한 일지 4개를 가지고 일지 메인으로 들어가기
          * @param model
          * @param request
          * @return String
          **/
   @RequestMapping("/myBodiaryMain")
   public String myBodiaryMain(Model model, HttpServletRequest request) throws ClassNotFoundException, SQLException, ParseException {

      UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
      int todayCount = bodiaryservice.todatBodiaryCount(user.getUser_email());
      
      String pagesize = "4";
      List<BodiaryDto> list = bodiaryservice.getBodiaryList(user.getUser_email(), pagesize);
      for(BodiaryDto b : list) {
         Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(b.getDiary_date());
         String formatDate = DateUtils.formatTimeString(date);
         System.out.println(formatDate);
         b.setDiary_date(formatDate);
      }
      model.addAttribute("todayCount", todayCount);
      model.addAttribute("list", list);
      return "myBodiary/myBodiaryMain";
   }
   
   //일지 폼 페이지 들어가기
   /**
    * @Method Name : myBodiaryForm
    * @작성일 : 2020. 7. 6.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 등록된 루틴 리스트와 현재 유저 데이터를 들고 폼 페이지로 이동
    * @param model
    * @param request
    * @return String
    **/
   @RequestMapping(value = "/myBodiaryForm", method = RequestMethod.GET)
   public String myBodiaryForm(Model model , HttpServletRequest request) throws ClassNotFoundException, SQLException {
      UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
       String user_email = user.getUser_email();
       System.out.println("유저 정보 : " +user_email);
       List<RoutineJoinDto> list = bodiaryservice.getRoutineListById(user_email);
       model.addAttribute("routineList", list);
       System.out.println("userdto: "+user);//추가
        model.addAttribute("user", user);//추가
       return bodiaryservice.goBodiaryForm(user.getUser_email());
   }
   
   //일지 작성하기
   /**
    * @Method Name : myBodiaryForm
    * @작성일 : 2020. 7. 6.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 일지 작성하기
    * @param dailymealdto
    * @param bodiarydto
    * @param request
    * @return String
    **/
   @RequestMapping(value = "/myBodiaryForm", method = RequestMethod.POST)
   public String myBodiaryForm(DailyMealDto dailymealdto, BodiaryDto bodiarydto, HttpServletRequest request) throws ClassNotFoundException, SQLException, IOException {
      System.out.println("일지 작성하기 : "+bodiarydto.toString());
      String url = bodiaryservice.writeBodiary(dailymealdto, bodiarydto, request);   
      return url;
   }
   
   //일지 상세보기
   /**
    * @Method Name : myBodiaryDetail
    * @작성일 : 2020. 7. 7.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 일지 상세보기 페이지로 이동
    * @param seq
    * @param model
    * @return String
    **/
   @RequestMapping("/myBodiaryDetail")
   public String myBodiaryDetail(String diary_seq, Model model) throws ClassNotFoundException, SQLException, ParseException {
      BodiaryDto bodiarydto = bodiaryservice.getBodiary(diary_seq);
      List<DailyMealFoodJoinDto> dailymeal = bodiaryservice.getDailyMeal(bodiarydto.getMeal_cart_seq());
      List<RoutineJoinDto> routinelist = bodiaryservice.getRoutine(bodiarydto.getRoutine_cart_seq());
      System.out.println(bodiarydto.getMeal_cart_seq());
      System.out.println(dailymeal.toString());
      System.out.println(routinelist.toString());
      
      
      Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bodiarydto.getDiary_date()); //String to date
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //new format
      String dateNewFormat = sdf.format(date);    
      //분을 시간으로 변환
        for(RoutineJoinDto r : routinelist) {
         if(r.getExcs_kind().equals("C")) { 
            String hour = ""; 
            if((Integer.parseInt(r.getRoutine_exercise_hour()) % 60) == 0) { 
               hour = (Integer.parseInt(r.getRoutine_exercise_hour()) / 60) + "시간"; 
            } else { 
               if((Integer.parseInt(r.getRoutine_exercise_hour()) / 60) == 0) { 
               hour = r.getRoutine_exercise_hour() + "분"; 
            }else { 
               hour = (Integer.parseInt(r.getRoutine_exercise_hour()) / 60) + "시간 30분"; 
               } 
            } r.setRoutine_exercise_hour(hour); 
         
            } 
         }   
      bodiarydto.setDiary_date(dateNewFormat);    
      model.addAttribute("bodiary", bodiarydto);
      model.addAttribute("dailymeallist", dailymeal);
      model.addAttribute("routinelist", routinelist);
      return "myBodiary/myBodiaryDetail";
   }
   
   //일지 수정 페이지 이동
   /**
    * @Method Name : myBodiaryEdit
    * @작성일 : 2020. 7. 8.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 일지 수정페이지로 이동
    * @param diary_seq
    * @param request
    * @return String
    **/
   @RequestMapping(value = "/myBodiaryEdit", method = RequestMethod.GET)
   public String myBodiaryEdit(String diary_seq, Model model, HttpServletRequest request) throws ClassNotFoundException, SQLException {
      BodiaryDto bodiarydto = bodiaryservice.getBodiary(diary_seq);
      System.out.println("수정페이지 이동 전 : "+bodiarydto.toString());
      String user_email = bodiarydto.getUser_email();
      List<RoutineJoinDto> list = bodiaryservice.getRoutineListById(user_email);
      model.addAttribute("routineList", list);
      model.addAttribute("bodiary", bodiarydto);
      UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
      System.out.println("userdto: "+user);//추가
        model.addAttribute("user", user);//추가
      
      return "myBodiary/myBodiaryEdit";
   }
   
   //일지 수정
   /**
    * @Method Name : myBodiaryEdit
    * @작성일 : 2020. 7. 8.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 작성한 일지를 수정한다
    * @param dailymealdto
    * @param bodiarydto
    * @param request
    * @return String
    **/
   @RequestMapping(value = "/myBodiaryEdit", method = RequestMethod.POST)
   public String myBodiaryEdit(DailyMealDto dailymealdto, BodiaryDto bodiarydto, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
      System.out.println("안녕 수정");
      System.out.println("컨트롤 : "+bodiarydto.toString());
      System.out.println("컨트롤 : "+dailymealdto.getDailyMealList().toString());
      String url = bodiaryservice.updateBodiary(dailymealdto, bodiarydto, request);
      return url;
   }
   
   //음식 검색하기
   /**
    * @Method Name : foodNameSearch
    * @작성일 : 2020. 7. 6.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 일지 작성 및 수정 시 음식 검색을 할 때 비동기로 db에 데이터를 가져오게 한다
    * @param food_name
    * @param curIndex
    * @return List
    **/
   @ResponseBody 
   @RequestMapping("/foodNameSearch")
   public List<FoodDto> foodNameSearch(@RequestParam String food_name, @RequestParam int curIndex) throws ClassNotFoundException, SQLException {
      System.out.println("food_name:"+food_name);
      System.out.println("Startindex: "+curIndex);
      List<FoodDto> foodlist = bodiaryservice.foodNameSearch(food_name, curIndex);
      System.out.println(foodlist.toString());
      return foodlist;
   }
   
   
   //루틴 불러오기
   /**
    * @Method Name : getRoutine
    * @작성일 : 2020. 7. 6.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 일지 작성 및 수정 시 등록된 루틴 선택할 때 비동기로 db에 데이터를 가져오게 한다
    * @param routine_cart_seq
    * @return List
    **/
   @ResponseBody 
   @RequestMapping("/getRoutine")
   public List<RoutineJoinDto> getRoutine(@RequestParam String routine_cart_seq) throws ClassNotFoundException, SQLException {
      System.out.println("routine_cart_seq:"+routine_cart_seq);
      List<RoutineJoinDto> routinelist = bodiaryservice.getRoutine(Integer.parseInt(routine_cart_seq));
      System.out.println(routinelist.toString());
      return routinelist;
   }
   
   //일지 리스트 불러오기
   /**
    * @Method Name : getBodiaryList
    * @작성일 : 2020. 7. 15.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 일지 리스트를 비동기로 불러온다
    * @param user_email
    * @return List
    **/
   @ResponseBody
   @RequestMapping("/getBodiaryList")
   public List<BodiaryDto> getBodiaryList(@RequestParam String user_email) throws ClassNotFoundException, SQLException {
      String pagesize = null;
      List<BodiaryDto> bodiarylist = bodiaryservice.getBodiaryList(user_email, pagesize);
      return bodiarylist;
   
   }
   
   //식단 불러오기
   /**
    * @Method Name : getDailyMeal
    * @작성일 : 2020. 7. 10.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 작성된 식단을 비동기로 불러온다
    * @param meal_cart_seq
    * @return List
    **/
   @ResponseBody
   @RequestMapping("/getDailyMeal")
   public List<DailyMealFoodJoinDto> getDailyMeal(@RequestParam String meal_cart_seq) throws ClassNotFoundException, SQLException {
      List<DailyMealFoodJoinDto> dailyMealList = bodiaryservice.getDailyMeal(Integer.parseInt(meal_cart_seq));
      return dailyMealList;
      
   }
   
   //일지 삭제하기
   /**
    * @Method Name : myBodiaryDelete
    * @작성일 : 2020. 7. 9.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 작성한 일지 삭제
    * @param diary_seq
    * @return String
    **/
   @RequestMapping("/myBodiaryDelete")
   public String myBodiaryDelete(String diary_seq) throws ClassNotFoundException, SQLException {
      return bodiaryservice.myBodiaryDelete(diary_seq);
   }
   
   
   
   //루틴 리스트 모아보기
   /**
    * @Method Name : myRoutineList
    * @작성일 : 2020. 7. 24.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 자신이 등록한 루틴 리스트를 가지고 페이지 이동
    * @param model
    * @param request
    * @return String
    **/
   @RequestMapping("/myRoutineList")
   public String myRoutineList(Model model, HttpServletRequest request) throws ClassNotFoundException, SQLException {
      List<RoutineJoinDto> list = bodiaryservice.myRoutineList(request);
      model.addAttribute("routineList", list);
      return "myBodiary/myRoutineList";
   }
   
   //루틴 상세보기
   /**
    * @Method Name : myRouitneDetail
    * @작성일 : 2020. 7. 24.
    * @작성자 : 오주형
    * @변경이력 :
    * @Method 설명 : 자신이 등록한 루틴 상세보기
    * @param model
    * @param routine_cart_seq
    * @return String
    **/
   @RequestMapping("/myRoutineDetail")
   public String myRouitneDetail(Model model, int routine_cart_seq) throws ClassNotFoundException, SQLException {
      List<RoutineJoinDto> routine = bodiaryservice.getRoutine(routine_cart_seq);
      
      //분을 시간으로 변환
        for(RoutineJoinDto r : routine) {
         if(r.getExcs_kind().equals("C")) { 
            String hour = ""; 
            if((Integer.parseInt(r.getRoutine_exercise_hour()) % 60) == 0) { 
               hour = (Integer.parseInt(r.getRoutine_exercise_hour()) / 60) + "시간"; 
            } else { 
               if((Integer.parseInt(r.getRoutine_exercise_hour()) / 60) == 0) { 
               hour = r.getRoutine_exercise_hour() + "분"; 
            }else { 
               hour = (Integer.parseInt(r.getRoutine_exercise_hour()) / 60) + "시간 30분"; 
               } 
            } r.setRoutine_exercise_hour(hour); 
         
            } 
         }
      model.addAttribute("routine", routine);
      return "myBodiary/myRoutineDetail";
   }
   
   
   
   
   


}