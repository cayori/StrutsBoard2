package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.Reader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ViewAction extends ActionSupport {
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	private BoardVO paramClass = new BoardVO();
	private BoardVO resultClass = new BoardVO();
	private List<CboardVO> commentList = new ArrayList<CboardVO>();
	
	private CboardVO cParamClass = new CboardVO();
	private CboardVO cResultClass = new CboardVO();
	
	private int currentPage;
	
	private int no;
	private int originno;
	
	private String password;
	
	private String fileUploadPath="C:\\upload2\\";
	
	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;
	
	// 생성자
	public ViewAction() throws IOException {
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	
	// 상세보기
	public String execute() throws Exception {
		// 해당 글의 조회수 +1
		paramClass.setNo(getNo());
		sqlMapper.update("updateReadHit",paramClass);
		// 해당 번호의 글을 가져온다.
		resultClass = (BoardVO)sqlMapper.queryForObject("selectOne",getNo());
		// 본문의 줄바꿈 처리
		resultClass.setContent(resultClass.getContent().replace("\r\n","<br>"));
		
		return SUCCESS;
	}
	
	// 첨부파일 다운로드
	public String download() throws Exception {
		//해당 번호의 파일 정보를 가져온다.
		resultClass = (BoardVO)sqlMapper.queryForObject("selectOne",getNo());
		// 파일경로와 파일명을 fileInfo 객체에 넣는다
		File fileInfo = new File(fileUploadPath+resultClass.getFile_savname());
		// 다운로드 파일 정보 설정
		setContentLength(fileInfo.length());
		setContentDisposition("attachment;filename="+URLEncoder.encode(resultClass.getFile_orgname(), "UTF-8"));
		setInputStream(new FileInputStream(fileUploadPath+resultClass.getFile_savname()));
		
		return SUCCESS;
	}
	
	// 비밀번호 체크 폼
	public String checkForm() throws Exception {
		return SUCCESS;
	}
	// 비밀번호 체크액션 (글삭제/수정)
	public String checkAction() throws Exception {
		// 비밀번호 입력값 파라미터 설정
		paramClass.setNo(getNo());
		paramClass.setPassword(getPassword());
		// 현재 글의 비밀번호 가져오기.
		resultClass = (BoardVO)sqlMapper.queryForObject("selectPassword",paramClass);
		// 입력한 비밀번호가 틀리면 ERROR 리턴
		if(resultClass == null)		return ERROR;
		return SUCCESS;
	}
	// 비밀번호 체크액션 (댓글삭제)
	public String checkAction2() throws Exception {
		cParamClass.setNo(getNo());
		cParamClass.setPassword(getPassword());
		cParamClass.setOriginno(getOriginno());
		cResultClass = (CboardVO) sqlMapper.queryForObject("selectPassword2", cParamClass);
		
		if(cResultClass == null)
			return ERROR;
		
		return SUCCESS;
	}

	public List<CboardVO> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CboardVO> commentList) {
		this.commentList = commentList;
	}

	public CboardVO getcParamClass() {
		return cParamClass;
	}

	public void setcParamClass(CboardVO cParamClass) {
		this.cParamClass = cParamClass;
	}

	public CboardVO getcResultClass() {
		return cResultClass;
	}

	public void setcResultClass(CboardVO cResultClass) {
		this.cResultClass = cResultClass;
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

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}	
}
