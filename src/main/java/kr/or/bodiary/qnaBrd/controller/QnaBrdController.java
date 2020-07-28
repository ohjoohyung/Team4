package kr.or.bodiary.qnaBrd.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bodiary.admin.service.ExerciseService;
import kr.or.bodiary.exercise.dto.ExerciseDto;
import kr.or.bodiary.qnaBrd.dto.QnaBrdDto;
import kr.or.bodiary.qnaBrd.service.QnaBrdService;
import kr.or.bodiary.user.dto.UserDto;


@Controller
public class QnaBrdController {
   private QnaBrdService qnabrdservice;
   @Autowired
   public void setQnabrdservice(QnaBrdService qnabrdservice) {
      this.qnabrdservice = qnabrdservice;
   }
   //유저 문의게시판//
   //리스트
   @RequestMapping("/myQnaList")
   public String getMyQnaList(Model model, HttpServletRequest request) {
      UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
       String user_email = user.getUser_email();
       System.out.println(user_email);
       List<QnaBrdDto> list = qnabrdservice.qnalists(user_email);
      model.addAttribute("list", list);
      return "myBodiary/myQnaList";
   }

   @RequestMapping(value="/myQnaDetail", method=RequestMethod.GET)
   public String myQnaDetail(String qna_brd_seq, Model model) {
      int seq = Integer.parseInt(qna_brd_seq);
      QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
      model.addAttribute("qna", qna);
      List<QnaBrdDto> list = qnabrdservice.qnaBrdDetailAns(seq);
      model.addAttribute("list", list);
      return "myBodiary/myQnaDetail";
   }

   @RequestMapping(value="/myQnaEdit", method=RequestMethod.GET)
   public String qnaBrdEdit(String qna_brd_seq, Model model) {
      int seq = Integer.parseInt(qna_brd_seq);
      System.out.println("qnaBrdEdit탐");
      QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
      model.addAttribute("qna", qna);
      System.out.println(qna);
      return "myBodiary/myQnaEdit";
   }
   @RequestMapping(value="/myQnaEdit", method=RequestMethod.POST)
   public String qnaBrdEditOK(QnaBrdDto QnaBrdDto, HttpServletRequest request) throws ClassNotFoundException, IOException, SQLException {
      return qnabrdservice.qnaBrdEdit(QnaBrdDto, request);
   }
      
   @RequestMapping(value="/myQnaForm", method=RequestMethod.GET)
   public String myQnaForm() {
      System.out.println("myQnaForm 컨트롤러탐");
      return "myBodiary/myQnaForm";
   }

   
   //삭제
   @RequestMapping(value="/myQnaFormDelete", method=RequestMethod.GET)
   public String myQnaFormDelete(String qna_brd_seq, Model model) throws ClassNotFoundException, SQLException {
      int seq = Integer.parseInt(qna_brd_seq);
      return qnabrdservice.qndBrdDelete(seq);
   }
   
      //어드민 Qna시작
      @RequestMapping("/adminQnaList")
      public String adminQnaList(Model model, HttpServletRequest request) {
         UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
          String user_email = user.getUser_email();
          List<QnaBrdDto> list = qnabrdservice.adminQnalists(user_email);
          System.out.println(list);
         model.addAttribute("list", list);
         return "admin/adminQnaList";
      }
      //어드민 detail
      @RequestMapping(value="/adminQnaDetail", method=RequestMethod.GET)
      public String adminQnaDetail(String qna_brd_seq, Model model) {
         int seq = Integer.parseInt(qna_brd_seq);
         QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
         model.addAttribute("qna", qna);
         System.out.println(qna);
         List<QnaBrdDto> list = qnabrdservice.qnaBrdDetailAns(seq);
         model.addAttribute("list", list);
         return "admin/adminQnaDetail";
         
      }
      
    
      
      @RequestMapping(value="/adminQnaModify", method=RequestMethod.POST)
      public String adminQnaModify(QnaBrdDto QnaBrdDto, Model model) {
         int seq = QnaBrdDto.getQna_brd_ref();
         QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
         model.addAttribute("qna", qna);
         System.out.println(qna);
         
         QnaBrdDto list = qnabrdservice.qnaBrdDetailAnsModify(QnaBrdDto);
         model.addAttribute("list", list);
          
         return "admin/adminQnaModify";
      }
      @RequestMapping(value="/adminQnaModifyOK", method=RequestMethod.POST)
      public String adminQnaModifyOK(QnaBrdDto QnaBrdDto, HttpServletRequest request) throws ClassNotFoundException, IOException, SQLException {
         return qnabrdservice.adminQnaModifyOK(QnaBrdDto, request);
      }
      
      @RequestMapping(value="/adminQnaDelete", method=RequestMethod.GET)
      public String adminQnaDelete(String qna_brd_seq, Model model) throws ClassNotFoundException, SQLException {
         int seq = Integer.parseInt(qna_brd_seq);
         QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
         int qnago = qna.getQna_brd_ref();
         return qnabrdservice.qndBrdAnsDelete(seq,qnago);
      }
      
      @RequestMapping(value="/adminQnaModDelete", method=RequestMethod.GET)
      public String adminQnaModDelete(String qna_brd_seq, Model model) throws ClassNotFoundException, SQLException {
         int seq = Integer.parseInt(qna_brd_seq);
         System.out.println(seq);
         QnaBrdDto qna = qnabrdservice.qnaBrdDetail(seq);
         int qnago = qna.getQna_brd_ref();
         return qnabrdservice.qndBrdAnsDelete(seq,qnago);
      }
      

         //썸머노트 파일 업로드
         @ResponseBody
         @RequestMapping("/qnasummerImageFree")
          public void summerImage(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
             response.setContentType("text/html;charset=utf-8");
             PrintWriter out = response.getWriter();
             // 업로드할 폴더 경로
             String realFolder = request.getSession().getServletContext().getRealPath("/assets/upload/qnaBrdUpload");
             UUID uuid = UUID.randomUUID();

             // 업로드할 파일 이름
             String org_filename = file.getOriginalFilename();
             String str_filename = uuid.toString() + org_filename;

             System.out.println("원본 파일명 : " + org_filename);
             System.out.println("저장할 파일명 : " + str_filename);

             String filepath = realFolder +  "\\" + str_filename;
             System.out.println("파일경로 : " + filepath);

             File f = new File(filepath);
             if (!f.exists()) {
             f.mkdirs();
             }
             file.transferTo(f);
             out.println("assets/upload/qnaBrdUpload/"+str_filename);
             out.close();
             }
}