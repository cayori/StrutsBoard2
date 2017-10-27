package board;

public class PagingAction {
	private int currentPage;	// 현재페이지
	private int totalCount;		// 전체 게시물 수
	private int totalPage;		// 전체 페이지 수
	private int blockCount;		// 한 페이지의 게시물 수
	private int blockpage;		// 한 화면에 보여줄 페이지 수
	private int startCount;		// 한 페이지에서 보여줄 게시글의 시작번호 (게시판에서의 제일 윗놈)
	private int endCount;		// 한 페이지에서 보여줄 게시글의 끝 번호
	private int startPage;		// 시작 페이지
	private int endPage;		// 마지막 페이지
	
	private StringBuffer pagingHtml;
	
	// 페이징 생성자
	public PagingAction(int currentPage, int totalCount, int blockCount, int blockPage, int searchNum, String isSearch) {
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.blockCount = blockCount;
		this.blockpage = blockPage;

		// 전체 페이지 수
		totalPage = (int)Math.ceil((double)totalCount/blockCount);
		if(totalPage == 0)	totalPage = 1;

		// 현재 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if(currentPage > totalPage)	currentPage = totalPage;
		
		// 현재 페이지의 처음과 마지막 글의 번호 가져오기 ... 이건 봐도봐도 모르겠다.. 나중에 찍어보면서 체크
		// 체크해본 결과... start, end 가 내가 생각한거랑 반대로.
		// 디비에서 순서를 다 정렬해서 가져온걸, 가장 상단에(최근) 게시물을 start 로 설정.
		startCount = (currentPage-1) * blockCount;
		endCount = startCount + blockCount - 1;
		
		// 시작페이지와 마지막 페이지 값 구하기. ... 
		startPage = (int)((currentPage-1) / blockPage) * blockPage + 1;
		endPage = startPage + blockPage - 1;
		
		// 마지막 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if(endPage > totalPage)	endPage = totalPage;
		
		// 이전 블록 페이지
		pagingHtml = new StringBuffer();
		if(currentPage > blockPage) {
			pagingHtml.append("<a href='listAction.action?currentPage="+(startPage-1));
			if(isSearch != "")
				pagingHtml.append("&searchNum="+searchNum+"&searchKeyword"+isSearch);
			pagingHtml.append("'>");
			pagingHtml.append("[이전]");
			pagingHtml.append("</a>");
		}
		
		// 블록 좌측 세로줄 긋기
		pagingHtml.append("&nbsp;|&nbsp;");
		
		// 페이지 번호. 현재 페이지는 빨간색으로 강조하고 링크를 제거
		for(int i=startPage; i<=endPage; i++) {
			if(i > totalPage)	break;
			if(i == currentPage) {
				pagingHtml.append("&nbsp;<b><font color='red'>"+i+"</font></b>");
			}else {				
				pagingHtml.append("&nbsp;<a href='listAction.action?currentPage=");
				pagingHtml.append(i);
				if(isSearch != "")
					pagingHtml.append("&seachKeyword="+isSearch);
				pagingHtml.append("'>");
				pagingHtml.append(i);
				pagingHtml.append("</a>");
			}
			pagingHtml.append("&nbsp;");
		}
		
		// 블록 우측 세로줄 긋기
		pagingHtml.append("&nbsp;&nbsp;|&nbsp;&nbsp;");
		
		// 다음 블록페이지
		if(totalPage - startPage >= blockPage) {
			pagingHtml.append("&nbsp;<a href='listAction.action?currentPage=");
			pagingHtml.append(endPage+1);
			if(isSearch != "")
				pagingHtml.append("&searchKeyword="+isSearch);
			pagingHtml.append("'>");
			pagingHtml.append("[다음]");
			pagingHtml.append("</a>");
		}
		printValues();
	}

	// 각 페이지에서 세팅된 값 출력하기
	public void printValues() {
		System.out.println("==================================================");
		System.out.println("currentPage	: "+currentPage+" --현재페이지"); 
		System.out.println("totalCount	: "+totalCount+" --전체 게시물 수");
		System.out.println("totalPage	: "+totalPage+" --전체 페이지 수");
		System.out.println("blockCount	: "+blockCount+" --한 페이지의 게시물 수");
		System.out.println("blockpage	: "+blockpage+" --한 화면에 보여줄 페이지 수");
		System.out.println("startCount	: "+startCount+" --한 페이지에서 보여줄 게시글의 시작번호 (게시판에서의 제일 윗놈)");
		System.out.println("endCount	: "+endCount+" --한 페이지에서 보여줄 게시글의 끝 번호");
		System.out.println("startPage	: "+startPage+" --시작 페이지");
		System.out.println("endPage		: "+endPage+" --마지막 페이지");
		System.out.println("==================================================");
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

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}

	public int getBlockpage() {
		return blockpage;
	}

	public void setBlockpage(int blockpage) {
		this.blockpage = blockpage;
	}

	public int getStartCount() {
		return startCount;
	}

	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}

	public int getEndCount() {
		return endCount;
	}

	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public StringBuffer getPagingHtml() {
		return pagingHtml;
	}

	public void setPagingHtml(StringBuffer pagingHtml) {
		this.pagingHtml = pagingHtml;
	}	
}
