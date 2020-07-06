package wwwwwwww.notice.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import wwwwwwww.notice.dao.NoticeDao;
import wwwwwwww.notice.dto.NoticeDto;

@Service
public class NoticeService {
		
		
		private SqlSession sqlsession;
		@Autowired
		public void setSqlSession(SqlSession sqlsession) {
			this.sqlsession = sqlsession;
		}
		
		//글목록보기
		 public List<NoticeDto> notices(String pg , String f , String q) {
			 
			 //default
			 int page=1;
			 String field = "TITLE";
			 String query = "%%";
			 
			//조건처리
				if(pg != null && !pg.equals("")) {
					page = Integer.parseInt(pg);
				}
				
				if(f != null && !f.equals("")) {
					field = f;
				}
				
				if(q != null && !q.equals("")) {
					query = q;
				}
		
				//DAO 데이터 받아오기
				List<NoticeDto> list=null;
				try {
					//mapper 를 통한 인터페이스 연결
					NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
					//
					list = noticedao.getNotices(page, field, query);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return  list;
		 
		 }
		 
		 
		
	}


