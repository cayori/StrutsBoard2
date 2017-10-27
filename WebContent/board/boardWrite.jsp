<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>

<html>
<head>
	<title>스트럿츠2 게시판</title>
	<link rel="stylesheet" href="/StrutsBoard/board/common/css/css.css" type="text/css">
	<script type="text/javascript">
		function fileName(){
			var fname = document.all.upload.value;
			var arr=("file:///"+fname.replace(/ /gi,"%20").replace(/\\/gi,"/")).split("/");
			return arr[arr.length-1];			
		}
		
		function checkFileName(){
			alert(fileName()+"\n"+fileName().length);	
		}
	
		function validation(){
			var frm = document.writeform;
			
			if(frm.subject.value == ""){
				alert("제목을 입력해주세요");
				return false;
			}else if(frm.name.value == ""){
				alert("이름을 입력해주세요");
				return false;
			}else if(frm.password.value == ""){
				alert("비밀번호를 입력해주세요");
				return false;
			}else if(frm.content.value == ""){
				alert("내용을 입력해주세요");
				return false;
			}else if(fileName().length >= 50){
				alert("파일이름이 50자 이상입니다");
				return false;
			}
		}
	</script>
</head>
<body>
	<table width="600" border="0" cellspacing="0" cellpadding="2">
		<tr>
			<td align="center"><h2>스트럿츠2 게시판</h2></td>
		</tr>
	</table>
	<!-- 답변관련 코드 시작 -->
	<s:if test="reply">
		<form action="replyAction.action" name="writeform" method="post" enctype="multipart/form-data onsubmit="return validation();">
			<s:hidden name="ref" value="%{resultClass.ref}" />
			<s:hidden name="re_level" value="%{resultClass.re_level}" />
			<s:hidden name="re_step" value="%{resultClass.re_step}" />
	</s:if>
	<!-- 답변관련 코드 끝 -->
	<s:elseif test="resultClass == null">
		<form action="writeAction.action" name="writeform" method="post" enctype="multipart/form-data" onsubmit="return validation();">
	</s:elseif>
	<s:else>
		<form action="modifyAction.action" name="writeform" method="post" enctype="multipart/form-data" onsubmit="return validation();">
			<s:hidden name="no" value="%{resultClass.no}" />
			<s:hidden name="currentPage" value="%{currentPage}" />
			<s:hidden name="old_file" value="%{resultClass.file_savname}" />
	</s:else>
	
			<table width="600" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="right" colspan="2"><font color="#FF0000">*</font>는 필수 입력사항입니다.</td>
				</tr>
				<tr bgcolor="#777777">
					<td height="1" colspan="2"></td>
				</tr>
				
				<tr>
					<td width="100" bgcolor="#F4F4F4"><font color="#FF0000">*</font>제목</td>
					<td width="500" bgcolor="#FFFFFF">
						<s:textfield name="subject" theme="simple" value="%{resultClass.subject}" cssStyle="width:370px" maxlength="50" />
					</td>
				</tr>
				<tr bgcolor="#777777">
					<td height="1" colspan="2"></td>
				</tr>
				
				<tr>
					<td bgcolor="#F4F4F4"><font color="#FF0000">*</font>이름</td>
					<td bgcolor="#FFFFFF">
						<s:textfield name="name" theme="simple" value="%{resultClass.name}" cssStyle="width:100px" maxlength="20" />
					</td>
				</tr>
				<tr bgcolor="#777777">
					<td height="1" colspan="2"></td>
				</tr>
				
				<tr>
					<td bgcolor="#F4F4F4"><font color="#FF0000">*</font>비밀번호</td>
					<td bgcolor="#FFFFFF">
						<s:textfield name="password" theme="simple" value="%{resultClass.password}" cssStyle="width:100px" maxlength="20" />
					</td>
				</tr>
				<tr bgcolor="#777777">
					<td height="1" colspan="2"></td>
				</tr>
				
				<tr>
					<td bgcolor="#F4F4F4"><font color="#FF0000">*</font>내용</td>
					<td bgcolor="#FFFFFF">
						<s:textarea name="content" theme="simple" value="%{resultClass.content}" cols="60" rows="10" />
					</td>
				</tr>
				<tr bgcolor="#777777">
					<td height="1" colspan="2"></td>
				</tr>
				
				<tr>
					<td bgcolor="#F4F4F4">첨부파일</td>
					<td bgcolor="#FFFFFF">
						<s:file name="upload" theme="simple" />
						<s:if test="resultClass.file_orgname != null">
							&nbsp;*<s:property value="resultClass.file_orgname" />
							파일이 등록되어 있습니다. 다시 업로드하면 기존의 파일은 삭제됩니다.
						</s:if>
					</td>
				</tr>
				<tr bgcolor="#777777">
					<td height="1" colspan="2"></td>
				</tr>
				
				<tr>
					<td height="10" colspan="2"></td>
				</tr>
				
				<tr>
					<td align="right" colspan="2">
						<input name="check" type="button" value="파일길이" class="inputb" onclick="checkFileName()">&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="submit" type="submit" value="작성완료" class="inputb">
						<input name="list" type="button" value="목록" class="inputb" onclick="javascript:location.href='listAction.action?curentPage=<s:property value="currentPage"/>'">
					</td>
				</tr>
			</table>
		</form>
</body>
</html>









