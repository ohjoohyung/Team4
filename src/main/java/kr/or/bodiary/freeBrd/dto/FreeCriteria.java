package kr.or.bodiary.freeBrd.dto;

public class FreeCriteria {

	private int page;
	private int perPageNum;
	
	public FreeCriteria() {
		this.page = 1;
		this.perPageNum = 10;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		
		if(page <= 0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		
		if(perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
			
			
		this.perPageNum = perPageNum;
	}
	
	//limit 구문에서 시작위치 지정 
	public int getPageStart() {
		return (this.page -1) * perPageNum;
	}
	
	
	
	
}































