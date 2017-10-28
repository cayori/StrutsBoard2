package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.util.*;
import java.io.Reader;
import java.io.IOException;

import board.PagingAction;

public class ListAction extends ActionSupport {
	public static Reader reader;			// 파일 스트림을 위한 reader
	public static SqlMapClient sqlMapper;	// SqlMapClient API 를 사용하기 위한 sqlMapper 객체
	
	private List<BoardVO> list = new ArrayList<BoardVO>();
	
	private String searchKeyword;
	private int searchNum;
	
	private int currentPage = 1;	// 현재 페이지
	private int totalCount;			// 총 게시물의 수
	private int blockCount = 10;	// 한 페이지의 게시물 수
	private int blockPage = 5;		// 한 화면에 보여줄 페이지 수
	private String pagingHtml;		// 페이징을 구현한 HTML
	private PagingAction page;		// 페이징 클래스
	private int num = 0;			// 이건 왜 추가됐나?
	
	// 생성자
	public ListAction() throws IOException {
		reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // sqlMapConfig.xml 내용 가져옴
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);	// sqlMapConfig.xml 내용을 sqlMapper 에 적용
		reader.close();
	}
	
	// 게시판 LIST 액션 execute 메소드
	public String execute() throws Exception {
		// 모든 글을 가져와 list 에 넣는다.
		list = sqlMapper.queryForList("selectAll");
		// 전체 글 갯수를 구한다.
		totalCount = list.size(); 
		// PagingAction 객체 생성
		page = new PagingAction(currentPage, totalCount, blockCount, blockPage, num, "");
		// html 코드를 문자열로 받아옴
		pagingHtml = page.getPagingHtml().toString();	
		// 현재 페이지에서 보여줄 마지막 글의 번호 설정.
		int lastCount = totalCount;
		// 현재 페이지의 마지막 글의 번호가 전체의 마지막 글 번호보다 작으면 lastCount 를 +1 번호로 설정
		if(page.getEndCount() < totalCount)	lastCount = page.getEndCount() + 1;
		// 전체 리스트에서 현재 페이지 만큼의 리스트만 가져온다.
		list = list.subList(page.getStartCount(), lastCount);
				
		return SUCCESS;
	}

	public String search() throws Exception {
		// searchKeyword = new String(searchKeyword.getBytes("iso-8859-1"),"utf-8");
		// System.out.println(searchKeyword);
		// System.out.println(searchNum);
		if(searchNum == 0)	list = sqlMapper.queryForList("selectSearchName", "%"+getSearchKeyword()+"%");
		if(searchNum == 1)	list = sqlMapper.queryForList("selectSearchSubject", "%"+getSearchKeyword()+"%");
		if(searchNum == 2)	list = sqlMapper.queryForList("selectSearchContent", "%"+getSearchKeyword()+"%");
		
		totalCount = list.size();
		page = new PagingAction(currentPage, totalCount, blockCount, blockPage, searchNum, getSearchKeyword());
		pagingHtml = page.getPagingHtml().toString();
		
		int lastCount = totalCount;
		
		if(page.getEndCount() < totalCount)	lastCount = page.getEndCount() +1;
		
		list = list.subList(page.getStartCount(), lastCount); // 검색된 총 리스트 중에 현재 페이지에 보여줄 갯수로만 재정의
		
		return SUCCESS;
	}
	
	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getSearchNum() {
		return searchNum;
	}

	public void setSearchNum(int searchNum) {
		this.searchNum = searchNum;
	}
	
	public List<BoardVO> getList() {
		return list;
	}

	public void setList(List<BoardVO> list) {
		this.list = list;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}

	public int getBlockPage() {
		return blockPage;
	}

	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}

	public String getPagingHtml() {
		return pagingHtml;
	}

	public void setPagingHtml(String pagingHtml) {
		this.pagingHtml = pagingHtml;
	}

	public PagingAction getPage() {
		return page;
	}

	public void setPage(PagingAction page) {
		this.page = page;
	}	
}
