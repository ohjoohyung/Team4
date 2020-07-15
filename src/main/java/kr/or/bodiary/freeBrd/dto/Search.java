package kr.or.bodiary.freeBrd.dto;

public class Search extends Pagination{


	private String searchType;

	private String keyword;	

	private int cateGory;
	

	public int getCateGory() {
		return cateGory;
	}



	public void setCateGory(int cateGory) {
		this.cateGory = cateGory;
	}



	public String getSearchType() {

		return searchType;

	}

	

	public void setSearchType(String searchType) {

		this.searchType = searchType;

	}

	

	public String getKeyword() {

		return keyword;

	}



	public void setKeyword(String keyword) {

		this.keyword = keyword;

	}

}
