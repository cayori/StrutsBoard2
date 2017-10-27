<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<head>
	<title>스트럿츠2 게시판</title>
	<link rel="stylesheet" href="/StrutsBoard2/board/common/css/css.css" type="text/css">
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
	
	<table width="600" border="0" cellspacing="0" cellpadding="2">
		<tr align="center" bgcolor="#F3F3F3">
			<td width="50"><strong>번호</strong></td>
			<td width="350"><strong>제목</strong></td>
			<td width="70"><strong>글쓴이</strong></td>
			<td width="80"><strong>날짜</strong></td>
			<td width="50"><strong>조회</strong></td>
		</tr>
		<tr bgcolor="#777777">
			<td height="1" colspan="5"></td>
		</tr>
		<!-- iterator 태그로 list 의 항목을 한줄씩 작성하기로 한다. -->
		<s:iterator value="list" status="stat">
			<!-- 연결할 링크URL 은 viewAction 으로 연결하고 파라미터 두개 준비한다 -->
			<s:url id="viewURL" action="viewAction">
				<s:param name="no">
					<s:property value="no" />
				</s:param>
				<s:param name="currentPage">
					<s:property value="currentPage" />
				</s:param>
			</s:url>
			<!-- 이제 실질적인 화면표시내용 -->
			<tr bgcolor="#FFFFFF" align="center">
				<td width="50">
					<s:property value="no" /></td>
				<td align="left" width="350">
					<s:if test="re_level > 0">
						<c:forEach var="i" begin="${re_level }" end="0">└</c:forEach>
					</s:if>
					&nbsp;<s:a href="%{viewURL}"><s:property value="subject"/></s:a>[<s:property value="ccount"/>]</td>
				<td align="center">
					<s:property value="name"/></td>
				<td align="center">
					<s:property value="regdate"/></td>
				<td align="center">
					<s:property value="readhit"/></td>
			</tr>
			<tr bgcolor="#777777">
				<td height="1" colspan="5"></td>
			</tr>
		</s:iterator>
		
		<s:if test="list.size() <= 0">
			<tr bgcolor="#FFFFFF" align="center">
				<td colspan="5">등록된 게시물이 없습니다.</td>
			</tr>
			<tr bgcolor="#777777">
				<td height="1" colspan="5"></td>
			</tr>
		</s:if>
		
		<tr align="center">
			<td colspan="5">
				<!-- escape 를 false 주면 태그가 다 표현됨 -->
				<s:property value="pagingHtml" escape="false" /></td>
		</tr>
		
		<tr align="right">
			<td colspan="5">
				<input type="button" value="글쓰기" class="inputb" onclick="javascript:location.href='writeForm.action?currentPage=<s:property value="currentPage"/>'">
		</tr>
		
		<tr align="center">
			<td colspan="5">
				<form>
					<select name="searchNum">
						<option value="0">작성자</option>
						<option value="1">제목</option>
						<option value="2">내용</option>
					</select>
					<s:textfield theme="simple" name="searchKeyword" value="" cssStyle="width:120px" maxlength="20" />
					<input name="submit" type="submit" value="검색" class="inputb">
				</form>
			</td>
		</tr>
		
	</table>
	
</body>
</html>