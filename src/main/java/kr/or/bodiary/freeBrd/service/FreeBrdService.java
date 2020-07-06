package kr.or.bodiary.freeBrd.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bodiary.freeBrd.dao.FreeBrdDao;
import kr.or.bodiary.freeBrd.dto.FreeBrdDto;


@Service
public class FreeBrdService {
	
	
	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	//전체 게시글(자유,팁,궁금) 목록보기
	public List<FreeBrdDto> allFreeBrd(){
			
		List<FreeBrdDto> list = null;
		
		try {
			//mapper 를 통한 FreeBrdDao 인터페이스 연결
			FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
			list = FreeBrd.allFreeBrd();
			System.out.println("게시판 리스트 주소"+list);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("allFreeBrd() 함수 실행중 오류발생"+e.getMessage());
		}
		
		return list;
	}
	
	//글 상세보기 서비스 함수
	public FreeBrdDto freebrdDetail(String seq) {
		FreeBrdDto freeBrdDto = null;
		try {
			FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
			freeBrdDto = FreeBrd.freebrdDetail(seq);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return freeBrdDto;
	}
	
	
	
	
	
	
}































