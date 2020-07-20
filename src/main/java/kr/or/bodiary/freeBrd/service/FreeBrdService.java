package kr.or.bodiary.freeBrd.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.bodiary.freeBrd.dao.FreeBrdDao;
import kr.or.bodiary.freeBrd.dao.FreeBrdReplyDao;
import kr.or.bodiary.freeBrd.dto.FreeBrdDTO;
import kr.or.bodiary.freeBrd.dto.Search;
import kr.or.bodiary.user.dto.UserDto;


@Service
public class FreeBrdService {
	

	
private SqlSession sqlsession;

@Autowired
public void setSqlsession(SqlSession sqlsession) {
	this.sqlsession = sqlsession;
}

//자유게시판 상위 랭크를 뽑아옴
public List<FreeBrdDTO> highLankFree() throws Exception{
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	List<FreeBrdDTO> list = FreeBrd.highLankFree();
	return list;
}
//질문게시판 상위 랭크를 뽑아옴
public List<FreeBrdDTO> highLankQna() throws Exception{
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	List<FreeBrdDTO> list = FreeBrd.highLankQna();
	return list;
}
//팁게시판 상위 랭크를 뽑아옴
public List<FreeBrdDTO> highLankTip() throws Exception{
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	List<FreeBrdDTO> list = FreeBrd.highLankTip();
	return list;
}


//게시판 제목클릭시 조회수 증가하는 함수
public void freeBrdHit(String seq) throws Exception {
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	FreeBrd.freeBrdHit(seq);
}
	
//자유게시판 총 게시물수 얻어오는 함수 
public int getLibertyCnt(Search search) throws Exception {
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	int result = FreeBrd.getLibertyCnt(search);
	System.out.println("자유게시판 총 게시물수 "+result);
	
	return result;
}

//질문게시판 총 게시물수 얻어오는 함수 
public int getQuestionCnt(Search search) throws Exception {
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	int result = FreeBrd.getQuestionCnt(search);
	System.out.println("질문게시판 총 게시물수 "+result);
	
	return result;
}

//팁게시판 총 게시물수 얻어오는 함수 
public int getTipCnt(Search search) throws Exception {
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	int result = FreeBrd.getTipCnt(search);
	System.out.println("팁게시판 총 게시물수 "+result);
	
	return result;
}

//검색에 해당하는 총 게시물수 얻어오는 함수
public int allFreeBrdCount(Search search) throws Exception {
	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
	int result = FreeBrd.getFreeBoardListCnt(search);
	System.out.println("검색에 해당되는 총 게시물수 "+result);
	
	return result;
}

	// 전체 게시글(자유,팁,궁금) 목록보기(가짜) 
	public List<FreeBrdDTO> allFreeBrd(Search search) {
	
		List<FreeBrdDTO> list = null;
	
		try {
			System.out.println("시작인덱스"+search.getStartIndex());
			System.out.println("한페이지 사이즈"+search.getPageSize());
			// mapper 를 통한 FreeBrdDao 인터페이스 연결
			FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
			list = FreeBrd.allFreeBrd(search);
			
			//해당 게시글의 총댓글 개수 얻어옴 
			FreeBrdReplyDao cmtlist = sqlsession.getMapper(FreeBrdReplyDao.class);

			for(int i=0;i<list.size();i++) {
				//게시글의 번호를 하나씩 얻어와 해당 게시글의 댓글수를 얻어옴 			
				list.get(i).setBrd_cmt_count(cmtlist.commentCount(list.get(i).getFree_brd_seq()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("allFreeBrd() 함수 실행중 오류발생" + e.getMessage());
		}
	
		return list;
	}
		
// 전체 게시글(자유,팁,궁금) 목록보기(원본)
/*
public List<FreeBrdDto> allFreeBrd(int startIndex,int pageSize) {

	List<FreeBrdDto> list = null;

	try {
		// mapper 를 통한 FreeBrdDao 인터페이스 연결
		FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
		list = FreeBrd.allFreeBrd(startIndex,pageSize);
		
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i).getFree_brd_seq()+"번호");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("allFreeBrd() 함수 실행중 오류발생" + e.getMessage());
	}

	return list;
}
*/

	// 글 상세보기 서비스 함수
	public FreeBrdDTO freebrdDetail(String seq) {
		FreeBrdDTO freeBrdDto = null;
		try {
			FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);			
			
			freeBrdDto = FreeBrd.freebrdDetail(seq);
			
			FreeBrdReplyDao list = sqlsession.getMapper(FreeBrdReplyDao.class);
			
			//해당게시글의 총댓글 개수를 얻어와 setter로 주입함 
			freeBrdDto.setBrd_cmt_count(list.commentCount(Integer.parseInt(seq)));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		return freeBrdDto;
	}

	// 글쓰기 처리 서비스 함수
	public String freeBrdFormInsert(FreeBrdDTO n, HttpServletRequest request, MultipartFile image,RedirectAttributes redirectAttributes) throws IOException {
	
		// 업로드한 이미지 이름 얻어오기 ex)a.PNG
		String imageName = image.getOriginalFilename();
	
		// 글쓰기 작성폼에서 이미지를 업로드한게 있다면 실행 (서버의 실제 업로드 파일 경로에 클라이언트가 올린 이미지를 업로드하는 과정)
		if (!image.isEmpty()) {
			// 클라이언트가 첨부한 이미지를 저장할 실제 서버파일 경로
			// ex)
			// C:\Team4_Project\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\SpringProject_Bodiary\assets/upload\freeBrdUpload
			String path = request.getSession().getServletContext().getRealPath("/assets/upload/freeBrdUpload");
			// 업로드 경로 + 저장할 파일이름(a.PNG)
			// ex)
			// C:\Team4_Project\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\SpringProject_Bodiary\assets/upload\freeBrdUpload\a.PNG
			String fpath = path + "\\" + imageName;
			System.out.println("실경로"+fpath);
			// FileOutput 이용해 실제 업로드하기
			FileOutputStream fs = new FileOutputStream(fpath);
			fs.write(image.getBytes());
			fs.close();
	
		}
	
		// DTO에 게시판에서 작성한 값 넣어주기
		
		//작성자 이메일 
		//로그인한 해당 유저의 정보를 뽑아올수 있음  
		UserDto user = (UserDto)request.getSession().getAttribute("currentUser");
		System.out.println("로그인한 유저 이메일"+user.getUser_email());
		n.setUser_email(user.getUser_email());
		// 카테고리
		n.setFree_cat(Integer.parseInt(request.getParameter("freeBrdCat")));
		// 글제목
		n.setFree_brd_title(request.getParameter("title"));
		// 업로드한 사진이름(글쓰기 뷰단에서 이미지를 첨부안하면 "" 공백으로 인식해서 들어옴)
		if(imageName != null && imageName != "") {
			System.out.println("업로드한 사진있음");
			n.setFree_brd_image(imageName);
		}else {
			System.out.println("업로드한 사진없음");
			n.setFree_brd_image(null);
		}
		// 글내용
		n.setFree_brd_content(request.getParameter("content"));
	
		System.out.println("카테고리->" + n.getFree_cat());
		System.out.println("글제목->" + n.getFree_brd_title());
		System.out.println("업로드 사진 이름->" + n.getFree_brd_image());
		System.out.println("글내용->" + n.getFree_brd_content());
	
		// DB연동해서 클라이언트가 입력한 데이터들을 DB안에 테이블에 넣어주기
		FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
		int seq=0;
		
		try {
			FreeBrd.freeBrdInsert(n);
			System.out.println("게시글 INSERT 완료");
			 
			//Insert후 맨마지막(가장큰) 글번호를 들고오는 함수 
			seq = FreeBrd.afterInsert_SelectDetail();
			System.out.println("글번호 출력"+seq);
		}catch (Exception e) {
			System.out.println("게시글 INSERT 문제발생..."+e.getMessage());
		}
	
	    redirectAttributes.addAttribute("seq",seq);
		 
		return "redirect:freeBrdDetail";
	}


	
	// 글 수정폼 이동 서비스 함수(게시물 상세랑 비슷)
	public FreeBrdDTO freeBrdEdit(String seq) {
		FreeBrdDTO freeBrdDto = null;
		try {
			FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);			
			
			freeBrdDto = FreeBrd.freebrdDetail(seq);
	
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		return freeBrdDto;
	}


	// 글 수정 서비스 함수
	public String freeBrdEditOk(FreeBrdDTO n,String seq, HttpServletRequest request, MultipartFile image) throws IOException {
		
		//새로올린 이미지
		String newImage = "";
		//과거에 올렸던 이미지 
		String pastImage = "";
		
		//파일 업로드시 아무것도 넣지 않으면 "" 공백이 들어감 
		if(image.getOriginalFilename() == null || image.getOriginalFilename() == "") {
			System.out.println("기존에 올린파일"+request.getParameter("image_2"));
			pastImage = request.getParameter("image_2");
		}else {
			System.out.println("새로 올린첨부파일"+image.getOriginalFilename());
			newImage = image.getOriginalFilename();
			// 클라이언트가 첨부한 이미지를 저장할 실제 서버파일 경로
			// ex)
			// C:\Team4_Project\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\SpringProject_Bodiary\assets/upload\freeBrdUpload
			String path = request.getSession().getServletContext().getRealPath("/assets/upload/freeBrdUpload");
			// 업로드 경로 + 저장할 파일이름(a.PNG)
			// ex)
			// C:\Team4_Project\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\SpringProject_Bodiary\assets/upload\freeBrdUpload\a.PNG
			String fpath = path + "\\" + newImage;
			System.out.println("실경로"+fpath);
			// FileOutput 이용해 실제 업로드하기
			FileOutputStream fs = new FileOutputStream(fpath);
			fs.write(image.getBytes());
			fs.close();
		}
	
	
		// DTO에 게시판에서 작성한 값 넣어주기
	
		//글번호
		n.setFree_brd_seq(Integer.parseInt(request.getParameter("seq")));
		// 카테고리
		n.setFree_cat(Integer.parseInt(request.getParameter("freeBrdCat")));
		// 글제목
		n.setFree_brd_title(request.getParameter("title"));
		// 업로드한 사진이름(글쓰기 뷰단에서 이미지를 첨부안하면 "" 공백으로 인식해서 들어옴)
		if(newImage != null && newImage != "") {
			n.setFree_brd_image(newImage); //글 수정할때 새로올린 이미지가 있다면 실행 
		}else {
			n.setFree_brd_image(pastImage); //글 수정할때 새로올린 이미지 없다면 기존이미지를 들고옴 
		}
		// 글내용
		n.setFree_brd_content(request.getParameter("content"));
		
		// DB연동해서 클라이언트가 입력한 데이터들을 DB안에 테이블에 넣어주기
		FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
		
		try {
			FreeBrd.freeBrdUpdate(n);
			System.out.println("게시글 Update 완료");
	
		}catch (Exception e) {
			System.out.println("게시글 Update 문제발생..."+e.getMessage());
		}
			
		return "redirect:freeBrdDetail";
	}


	 //글삭제하기 서비스 함수
	 public String freeBrdDelete(String seq) throws ClassNotFoundException, SQLException {
		 	FreeBrdDao FreeBrd = sqlsession.getMapper(FreeBrdDao.class);
			FreeBrd.freeBrdDelete(seq);
			return "redirect:freeBrdList";
	}
}
