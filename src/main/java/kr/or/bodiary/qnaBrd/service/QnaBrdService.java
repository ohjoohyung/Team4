package kr.or.bodiary.qnaBrd.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bodiary.exercise.dao.ExerciseDao;
import kr.or.bodiary.exercise.dto.ExerciseDto;
import kr.or.bodiary.qnaBrd.dao.QnaBrdDao;
import kr.or.bodiary.qnaBrd.dto.QnaBrdDto;
import kr.or.bodiary.user.dao.UserDao;
import kr.or.bodiary.user.dto.UserDto;


@Service
public class QnaBrdService {
	
private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	//qnd 리스트 서비스함수
	public List<QnaBrdDto> qnalists(String user_email) {
			
						List<QnaBrdDto> list=null;
						try {
							//mapper 를 통한 인터페이스 연결
							QnaBrdDao qnabrddao = sqlsession.getMapper(QnaBrdDao.class);
							//
							list = qnabrddao.getQnaList(user_email);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
	return list;
	}
	/* 질문 insert 웹소켓으로 옮김
	 * //문의 생성 서비스 함수 public String qnaInsert(QnaBrdDto QnaBrdDto ,
	 * HttpServletRequest request) throws Exception {
	 * 
	 * QnaBrdDao QnaBrdDao = sqlsession.getMapper(QnaBrdDao.class);
	 * QnaBrdDao.insertQnaBrd(QnaBrdDto); System.out.println(QnaBrdDto);
	 * QnaBrdDto.setQna_brd_ref(QnaBrdDto.getQna_brd_seq());
	 * QnaBrdDao.updateQnaBrd(QnaBrdDto); return "redirect:myQnaList"; }
	 */
	
	//문의 상세보기 서비스 함수 (상세보기 할때 상태 업데이트 하기에 트랜잭션 추가)
	
	@Transactional
	public QnaBrdDto qnaBrdDetail(int qna_brd_seq) {
				QnaBrdDto QnaBrdDto = null;
				try {
						QnaBrdDao QnaBrdDao = sqlsession.getMapper(QnaBrdDao.class);
						QnaBrdDao.updateAdminRead(qna_brd_seq);
						QnaBrdDao.updateQnaUserRead(qna_brd_seq);
						QnaBrdDto = QnaBrdDao.getQnaBrdBySeq(qna_brd_seq);
						
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
						
				return  QnaBrdDto;
			}
	@Transactional
			//문의 상세보기 서비스 함수 (상세보기 할때 상태 업데이트 하기에 트랜잭션 추가)
			public List<QnaBrdDto> qnaBrdDetailAns(int qna_brd_seq) {
				List<QnaBrdDto> list=null;
				try {
						QnaBrdDao QnaBrdDao = sqlsession.getMapper(QnaBrdDao.class);
						QnaBrdDao.updateAdminRead(qna_brd_seq);
						QnaBrdDao.updateQnaUserRead(qna_brd_seq);
						list = QnaBrdDao.getQnaBrdBySeqAns(qna_brd_seq);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
						
				return  list;
			}
			
	//문의 수정하기 서비스 함수 (update)
	 public String qnaBrdEdit(QnaBrdDto QnaBrdDto , HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
			System.out.println("qnaBrdEdit탑니다: "+QnaBrdDto);
			QnaBrdDao QnaBrdDao = sqlsession.getMapper(QnaBrdDao.class);
			QnaBrdDao.updateQnaBrd(QnaBrdDto);
			QnaBrdDto.setQna_brd_ref(QnaBrdDto.getQna_brd_seq());
			QnaBrdDao.updateQnaBrd(QnaBrdDto);
			return "redirect:myQnaDetail?qna_brd_seq="+QnaBrdDto.getQna_brd_seq();
				}
	 
	//문의 삭제
	public String qndBrdDelete(int qna_brd_seq) throws ClassNotFoundException, SQLException {
						
			QnaBrdDao QnaBrdDao = sqlsession.getMapper(QnaBrdDao.class);
			QnaBrdDao.qndBrdDelete(qna_brd_seq);	
			return  "redirect:myQnaList";
			}
	
	//관리자 문의 리스트
	//qnd 리스트 서비스함수
		public List<QnaBrdDto> adminQnalists(String user_email) {
				
							List<QnaBrdDto> list=null;
							try {
								//mapper 를 통한 인터페이스 연결
								QnaBrdDao qnabrddao = sqlsession.getMapper(QnaBrdDao.class);
								//
								list = qnabrddao.getAdminQnaList(user_email);
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								e.printStackTrace();
							}
		return list;
		}
		/* 답변 insert 웹소켓으로 옮김
		 * //관리자 답변 서비스 함수 (답변 상태 바꿔주는 dao 수정) public String qnaAnsInsert(QnaBrdDto
		 * QnaBrdDto) throws Exception { QnaBrdDao QnaBrdDao =
		 * sqlsession.getMapper(QnaBrdDao.class); QnaBrdDao.insertQnaAnsBrd(QnaBrdDto);
		 * QnaBrdDao.updateQnaRep(QnaBrdDto.getQna_brd_ref()); return
		 * "redirect:adminQnaList"; }
		 */
		
		public QnaBrdDto qnaBrdDetailAnsModify(QnaBrdDto QnaBrdDto) {
			QnaBrdDto list=null;
			try {
					QnaBrdDao QnaBrdDao = sqlsession.getMapper(QnaBrdDao.class);
					list = QnaBrdDao.getQnaBrdBySeqAnsModify(QnaBrdDto);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
					
			return  list;
		}
		//관리자 답변 수정하기 서비스 함수 (update)
		 public String adminQnaModifyOK(QnaBrdDto QnaBrdDto , HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
			QnaBrdDao QnaBrdDao = sqlsession.getMapper(QnaBrdDao.class);
			QnaBrdDao.updateQnaAns(QnaBrdDto);
			return "redirect:adminQnaDetail?qna_brd_seq="+QnaBrdDto.getQna_brd_ref();
		 }
		 
		//관리자 답변  삭제
			public String qndBrdAnsDelete(int qna_brd_seq, int qnago) throws ClassNotFoundException, SQLException {
								
					QnaBrdDao QnaBrdDao = sqlsession.getMapper(QnaBrdDao.class);
					QnaBrdDao.qndBrdDelete(qna_brd_seq);
					int seq = qnago;
					return  "redirect:adminQnaDetail?qna_brd_seq="+seq;
					}

}
