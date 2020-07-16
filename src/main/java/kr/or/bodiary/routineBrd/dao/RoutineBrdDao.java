package kr.or.bodiary.routineBrd.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.bodiary.routineBrd.dto.RoutineBoardCommentDto;
import kr.or.bodiary.routineBrd.dto.RoutineBoardUserJoinDto;
import kr.or.bodiary.routineBrd.dto.RoutineBrdDto;


public interface RoutineBrdDao {
   
   //리스트 가져오기
   public List<RoutineBoardUserJoinDto> routineBoardList(int start, int ps) throws ClassNotFoundException, SQLException;
   
   //상세
   public RoutineBrdDto routineBoardSelect(int routine_brd_seq) throws ClassNotFoundException, SQLException;

   //입력
   public int routineBoardInsert(RoutineBrdDto routineBrdDto) throws ClassNotFoundException, SQLException;
   
   //수정
   public int routineBoardEdit(RoutineBrdDto routineBrdDto) throws ClassNotFoundException, SQLException;
   
   //삭제
   public int routineBoardDelete(int routine_brd_seq) throws ClassNotFoundException, SQLException;   
   
   //조회수 증가
   public int updateHit(int routine_brd_seq) throws ClassNotFoundException, SQLException;
   
   //오늘의 Hit 게시글 가져오기
   public List<RoutineBoardUserJoinDto> getTodayHit() throws ClassNotFoundException, SQLException;
   
 // ---------------------- 댓글 -------------------------
   
   //입력
   public int routineCmtInsert(RoutineBoardCommentDto routineCmtDto) throws ClassNotFoundException, SQLException;
   //대댓 입력
   public int routineReCmtInsert(RoutineBoardCommentDto routineCmtDto) throws ClassNotFoundException, SQLException;
   
   //레퍼...
   public int routineCmtReferUpdate(int routine_cmt_ref) throws ClassNotFoundException, SQLException;
   //스탭 
   public int routineCmtStepUpdate(RoutineBoardCommentDto routineCmtDto) throws ClassNotFoundException, SQLException;
   
   //상세
   public RoutineBoardCommentDto routineCmtSelect(int routine_cmt_seq) throws ClassNotFoundException, SQLException;
   
   //리스트
   public List<RoutineBoardCommentDto> routineCmtList(int routine_brd_seq) throws ClassNotFoundException, SQLException;
}