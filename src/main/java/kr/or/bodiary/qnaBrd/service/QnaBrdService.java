package kr.or.bodiary.qnaBrd.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.chat.dto.NotYet;
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
	//문의 생성 서비스 함수
	public String qnaInsert(QnaBrdDto QnaBrdDto , HttpServletRequest request) throws Exception {
		
	QnaBrdDao QnaBrdDao = sqlsession.getMapper(QnaBrdDao.class);
	QnaBrdDao.insertQnaBrd(QnaBrdDto);
	return "redirect:/myQnaList";
	}
	
	//문의 상세보기 서비스 함수
			public QnaBrdDto qnaBrdDetail(int qna_brd_seq) {
				QnaBrdDto QnaBrdDto = null;
				try {
						QnaBrdDao QnaBrdDao = sqlsession.getMapper(QnaBrdDao.class);
						QnaBrdDto = QnaBrdDao.getQnaBrdBySeq(qna_brd_seq);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
						
				return  QnaBrdDto;
			}
			
	//운동 수정하기 서비스 함수 (update)
	 public String qnaBrdEdit(QnaBrdDto QnaBrdDto , HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException {
				
			QnaBrdDao QnaBrdDao = sqlsession.getMapper(QnaBrdDao.class);
			QnaBrdDao.updateQnaBrd(QnaBrdDto);
			return "redirect:myQnaDetail?qna_brd_seq="+QnaBrdDto.getQna_brd_seq();
				}

}
