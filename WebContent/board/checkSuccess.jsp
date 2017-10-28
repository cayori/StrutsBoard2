<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
	<title></title>
	<link rel="stylesheet" href="/StrutsBoard2/board/common/css/css.css" type="text/css">
	<script type="text/javascript">
		function locationURL(){
			if(window.name == 'modify'){
				window.opener.parent.location.href='modifyForm.action?no=<s:property value="no"/>&currentPage=<s:property value="currentPage"/>';
			}else if(window.name == 'delete'){
				alert("삭제되었습니다");
				window.opener.parent.location.href='deleteAction.action?no=<s:property value="no"/>&currentPage=<s:property value="currentPage"/>';
			}else if(window.name == 'cdelete'){
				alert("댓글이 삭제되었습니다");
				window.opener.parent.location.href='deleteAction2.action?no=<s:property value="no"/>&originno=<s:property value="originno"/>&currentPage=<s:property value="currentPage"/>';
			}
			window.close();		
		}
	</script>
</head>
<body>
	<script>locationURL()</script>
</body>
</html>