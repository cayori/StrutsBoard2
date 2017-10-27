package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.util.*;
import java.io.Reader;
import java.io.IOException;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class WriteAction extends ActionSupport {
	public static Reader reader;			// 파일 스트림을 위한 reader
	public static SqlMapClient sqlMapper;	// SqlMapClient API 를 사용하기 위한 sqlMapper 객체
	
	private BoardVO paramClass;		// 파라미터 저장할 객체
	private BoardVO resultClass;	// 쿼리 결과 값을 저장할 객체
	
	private int currentPage;		// 현재 페이지
	
	private int no;
	private String subject;
	private String name;
	private String password;
	private String content;
	private String file_orgName;				// 업로드 파일의 원래 이름
	private String file_savName;				// 서버에 저장할 업로드 파일의 이름. 글번호 이용해서 고유값으로 만듬
	Calendar today = Calendar.getInstance(); 	// 오늘 날짜 구하기
	
	private File upload;								// 파일객체
	private String uploadContentType;					// 컨텐츠 타입
	private String uploadFileName;						// 파일이름
	private String fileUploadPath = "C:\\upload2\\";		// 업로드 경로
	
	private int ref;
	private int re_step;
	private int re_level;
	
	boolean reply = false;
	



	//생성자
	public WriteAction() throws IOException {
		reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // sqlMapConfig.xml 내용 가져옴
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);	// sqlMapConfig.xml 내용을 sqlMapper 에 적용
		reader.close();
	}
	
	public String form() throws Exception {
		// 등록 폼으로 이동시키기 위한 액션메소드
		return SUCCESS;
	}
	
	// 답글달기 replayAction --> 답글 달 소스를 만들어서 던져주고 실제 입력은 execute 이용해서 진행
	public String reply() throws Exception{
		reply = true;
		resultClass = new BoardVO();
		
		resultClass = (BoardVO) sqlMapper.queryForObject("selectOne",getNo());
		resultClass.setSubject("[답변] "+resultClass.getSubject());
		resultClass.setPassword("");
		resultClass.setName("");
		resultClass.setContent("");
		resultClass.setFile_orgname(null);
		resultClass.setFile_savname(null);
		
		return SUCCESS;
	}
	
	//게시판 write 액션
	public String execute() throws Exception {
		// 파라미터와 리절트 객체 생성
		paramClass = new BoardVO();
		resultClass = new BoardVO();
		// 등록할 항목 설정.
		paramClass.setSubject(getSubject());
		paramClass.setName(getName());
		paramClass.setPassword(getPassword());
		paramClass.setContent(getContent());
		paramClass.setRegdate(today.getTime());
		// 등록 쿼리 수행
		sqlMapper.insert("insertBoard", paramClass);
		// 첨부파일을 선택했다면 파일을 업로드한다
		if(getUpload() != null) {
			// 등록한 글번호 가져오기
			resultClass = (BoardVO) sqlMapper.queryForObject("selectLastNo");
			// 실제 서버에 저장될 파일 이름과 확장자 설정.
			String file_name = "file_"+resultClass.getNo();
			String file_ext = getUploadFileName().substring(getUploadFileName().lastIndexOf('.')+1);
			// 서버에 파일 저장
			File destFile = new File(fileUploadPath + file_name +"."+file_ext);
			FileUtils.copyFile(getUpload(), destFile);
			// 파일 정보 파라미터 설정
			paramClass.setNo(resultClass.getNo());
			paramClass.setFile_orgname(getUploadFileName());
			paramClass.setFile_savname(file_name+"."+file_ext);
			// 파일 정보 업데이트
			sqlMapper.update("updateFile",paramClass);
		}
		return SUCCESS;
	}

	public boolean isReply() {
		return reply;
	}

	public void setReply(boolean reply) {
		this.reply = reply;
	}
	
	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public int getRe_step() {
		return re_step;
	}

	public void setRe_step(int re_step) {
		this.re_step = re_step;
	}

	public int getRe_level() {
		return re_level;
	}

	public void setRe_level(int re_level) {
		this.re_level = re_level;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFile_orgName() {
		return file_orgName;
	}

	public void setFile_orgName(String file_orgName) {
		this.file_orgName = file_orgName;
	}

	public String getFile_savName() {
		return file_savName;
	}

	public void setFile_savName(String file_savName) {
		this.file_savName = file_savName;
	}

	public Calendar getToday() {
		return today;
	}

	public void setToday(Calendar today) {
		this.today = today;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}
	
	
	
	
}









