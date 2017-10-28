<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>

<html>
<head>
	<title>스트럿츠2 게시판</title>
	<link rel="stylesheet" href="/StrutsBoard2/board/common/css/css.css" type="text/css">
	<script type="text/javascript">
		function open_win_noresizable(url,name) {
			var oWin = window.open(url,name,"scrollbars=no,status=no,width=330,height=150");
		}
	</script>
</head>
<body>
	<table width="600" border="0" cellspacing="0" cellpadding="2">
		<tr>
			<td align="center"><h2>스트럿츠2 게시판</h2></td>
		</tr>
		<tr>
			<td height="20"></td>
		</tr>
	</table>
	
	<table width="600" border="0" cellspacing="0" cellpadding="0">
		<tr bgcolor="#777777">
			<td height="1" colspan="2"></td>
		</tr>
		<tr>
			<td bgcolor="#F4F4F4">번호</td>
			<td bgcolor="#FFFFFF">
				&nbsp;&nbsp;<s:property value="resultClass.no" />
			</td>
		</tr>
		<tr bgcolor="#777777">
			<td height="1" colspan="2"></td>
		</tr>
		<tr>
			<td width="100" bgcolor="#F4F4F4">제목</td>
			<td width="500" bgcolor="#FFFFFF">
				&nbsp;&nbsp;<s:property value="resultClass.subject" />
			</td>
		</tr>
		<tr bgcolor="#777777">
			<td height="1" colspan="2"></td>
		</tr>
		<tr>
			<td bgcolor="#F4F4F4">글쓴이</td>
			<td bgcolor="#FFFFFF">
				&nbsp;&nbsp;<s:property value="resultClass.name" />
			</td>
		</tr>
		<tr bgcolor="#777777">
			<td height="1" colspan="2"></td>
		</tr>
		<tr>
			<td bgcolor="#F4F4F4">내용</td>
			<td bgcolor="#FFFFFF" >
<!--				&nbsp;&nbsp;${resultClass.content};						-->
 			&nbsp;&nbsp;<s:property value="resultClass.content" escape="false" />  	
			</td>
		</tr>
		<tr bgcolor="#777777">
			<td height="1" colspan="2"></td>
		</tr>
		<tr>
			<td bgcolor="#F4F4F4">조회수</td>
			<td bgcolor="#FFFFFF">
				&nbsp;&nbsp;<s:property value="resultClass.readhit" />
			</td>
		</tr>
		<tr bgcolor="#777777">
			<td height="1" colspan="2"></td>
		</tr>
		<tr>
			<td bgcolor="#F4F4F4">등록날짜</td>
			<td bgcolor="#FFFFFF">
				&nbsp;&nbsp;<s:property value="resultClass.regdate" />
			</td>
		</tr>
		<tr bgcolor="#777777">
			<td height="1" colspan="2"></td>
		</tr>
		<tr>
			<td bgcolor="#F4F4F4">첨부파일</td>
			<td bgcolor="#FFFFFF">
				&nbsp;&nbsp;
				<s:url id="download" action="fileDownloadAction">
					<s:param name="no">
						<s:property value="no"/>
					</s:param>
				</s:url>
				<s:a href="%{download}"><s:property value="resultClass.file_orgname"/></s:a>
			</td>
		</tr>
		<tr bgcolor="#777777">
			<td height="1" colspan="2"></td>
		</tr>
		
		<tr>
			<td height="10" colspan="2"></td>
		</tr>
		
		<!-- 댓글쓰기 시작 -->
		<tr>
			<td colspan="2" height="10">
				<form action="writeCommentAction.action" method="post">
					<table>
						<tr>
							<td width="170">
								이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름<s:textfield name="name" theme="simple" value="" cssStyle="width:100px" maxlength="20"/><br>
								비밀번호<s:textfield name="password" theme="simple" value="" cssStyle="width:100px" maxlength="20"/>
							</td>
							<s:hidden name="originno" value="%{resultClass.no}"/>
							<!-- !!수정!! ... 근데 뭘? -->
							<s:hidden name="no" value="%{resultClass.no}"/>
							<s:hidden name="currentPage" value="%{currentPage}"/>
							<!-- !!수정!! ... 근데 뭘? -->
							<td align="left">
								<s:textarea name="content" theme="simple" value="" cols="60" rows="3" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<input type="submit" name="submit" value="작성완료" class="inputb">
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
		<tr bgcolor="#777777">
			<td height="1" colspan="2"></td>
		</tr>
		<!-- 댓글쓰기 완료 -->
		<!-- 댓글보기 시작 -->
		<s:iterator value="commentList" status="stat">
			<tr>
				<td height="10" width="130" align="center">
					<s:property value="name"/><br>
					<s:property value="regdate"/><br><br>
				</td>
				<td>
					<s:property value="content"/>
					<a href="javascript:open_win_noresizable('checkForm2.action?no=<s:property value="no" />&originno=<s:property value="originno"/>&currentPage=<s:property value="currentPage"/>','cdelete')"><u>[x]</u></a>
				</td>
			</tr>
			<tr bgcolor="#777777">
				<td colspan="2" height="1"></td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="2" height="10">
				<s:if test="commentList.size() <= 0">
				댓글없음
				</s:if>
			</td>
		</tr>
		<tr bgcolor="#777777">
			<td height="1" colspan="2"></td>
		</tr>
		<tr>
			<td height="10" colspan="2"></td>
		</tr>
		
		
		<!-- 댓글보기기 완료 -->
		
		
		
		<tr>
			<td align="right" colspan="2">
				<s:url id="modifyURL" action="modifyForm">
					<s:param name="no">
						<s:property value="no"/>
					</s:param>
				</s:url>
				<s:url id="deleteURL" action="deleteAction">
					<s:param name="no">
						<s:property value="no"/>
					</s:param>
				</s:url>
				<input type="button" name="comm" value="답글달기" class="inputb"
					onclick="javascript:location.href='replyForm.action?no=<s:property value="no"/>&currentPage=<s:property value="currentPage"/>'">
				<input type="button" name="list" value="수정" class="inputb" 
					onclick="javascript:open_win_noresizable('checkForm.action?no=<s:property value="resultClass.no"/>&currentPage=<s:property value="currentPage"/>','modify')">
				<input type="button" name="list" value="삭제" class="inputb" 
					onclick="javascript:open_win_noresizable('checkForm.action?no=<s:property value="resultClass.no"/>&currentPage=<s:property value="currentPage"/>','delete')">
				<input type="button" name="list" value="목록" class="inputb"
					onclick="javascript:location.href='listAction.action?currentPage=<s:property value="currentPage"/>'">
					
			</td>
		</tr>
	</table>
</body>
</html>