package kr.or.bodiary.qnaBrd.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.chat.dto.NotYet;
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

}
