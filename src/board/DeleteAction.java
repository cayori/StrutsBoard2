package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.File;
import java.io.Reader;
import java.io.IOException;

public class DeleteAction extends ActionSupport {
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	private BoardVO paramClass;
	private BoardVO resultClass;
	
	private CboardVO cParamClass;
	private CboardVO cResultClass;
	
	private int currentPage;
	private String fileUploadPath = "C:\\upload2\\";
	private int no;
	private int originno;
	

	// 생성자
	public DeleteAction() throws IOException {
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	
	// 게시글 삭제
	public String execute() throws Exception {
		// 파라미터와 리절트 객체 생성
		paramClass = new BoardVO();
		resultClass = new BoardVO();
		// 해당 번호의 글을 가져온다.
		resultClass = (BoardVO)sqlMapper.queryForObject("selectOne",getNo());
		// 서버 파일 삭제
		File oldFile = new File(fileUploadPath + resultClass.getFile_savname());
		oldFile.delete();
		// 삭제할 항목 설정
		paramClass.setNo(getNo());
		// 삭제 쿼리 수행
		sqlMapper.update("deleteBoard",paramClass);		
		
		return SUCCESS;
	}
	
	// 댓글 삭제
	public String execute2() throws Exception {
		cParamClass = new CboardVO();
//		cResultClass = new CboardVO();
		
		cParamClass.setNo(getNo());
		sqlMapper.update("deleteComment",cParamClass);
		
		paramClass = new BoardVO();
		paramClass.setNo(getOriginno());
		
		sqlMapper.update("decreaseCcount", paramClass);
		
		return SUCCESS;
	}
	

	public int getOriginno() {
		return originno;
	}
	public void setOriginno(int originno) {
		this.originno = originno;
	}
	public BoardVO getParamClass() {
		return paramClass;
	}
	public void setParamClass(BoardVO paramClass) {
		this.paramClass = paramClass;
	}
	public BoardVO getResultClass() {
		return resultClass;
	}
	public void setResultClass(BoardVO resultClass) {
		this.resultClass = resultClass;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getFileUploadPath() {
		return fileUploadPath;
	}
	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
}
