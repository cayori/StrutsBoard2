<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
	<title>비밀번호 오류</title>
	<link rel="stylesheet" type="text/css" href="StrutsBoard/board/common/css/css.css">
	<script type="text/javascript">
		function errorMessage(){
			alert("비밀번호가 틀립니다");
			history.go(-1);
		}
	</script>
</head>
<body>
	<script>errorMessage()</script>
</body>
</html>