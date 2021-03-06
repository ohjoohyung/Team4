package kr.or.bodiary.routineBrd.dto;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Data;


@Data
public class RoutineBrdDto {
   private int routine_brd_seq;
   private String user_email;
   private String routine_brd_title;
   private String routine_brd_content;
   private int routine_cart_seq;
   private String routine_brd_regdate;
   private String brd_image1;
   private String brd_image2;
   private int routine_brd_hit;
   private List<CommonsMultipartFile> files; //다중 파일 업로드
   
}