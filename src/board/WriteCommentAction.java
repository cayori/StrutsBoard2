package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.util.*;
import java.io.Reader;
import java.io.IOException;

public class WriteCommentAction extends ActionSupport {
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	private BoardVO paramClass;
	
	private CboardVO cParamClass;
	private CboardVO cResultClass;
	
	private int no;
	private int currentPage;
	private String name;
	private String password;
	private String content;
	private int originno;
	
	Calendar today = Calendar.getInstance();
	
	// 생성자
	public WriteCommentAction() throws IOException {
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	
	public String form() throws Exception {
		return SUCCESS;
	}

	public String execute() throws Exception {
		cParamClass = new CboardVO();
		cResultClass = new CboardVO();
		
		cParamClass.setOriginno(getOriginno());
		cParamClass.setName(getName());
		cParamClass.setPassword(getPassword());
		cParamClass.setContent(getContent());
		cParamClass.setRegdate(today.getTime());
		
		sqlMapper.insert("insertComment", cParamClass);
		
		paramClass = new BoardVO();
		paramClass.setNo(getOriginno());
		
		sqlMapper.update("increaseCcount", paramClass);
		
		return SUCCESS;
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

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
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

	public int getOriginno() {
		return originno;
	}

	public void setOriginno(int originno) {
		this.originno = originno;
	}

	public Calendar getToday() {
		return today;
	}

	public void setToday(Calendar today) {
		this.today = today;
	}	
}
