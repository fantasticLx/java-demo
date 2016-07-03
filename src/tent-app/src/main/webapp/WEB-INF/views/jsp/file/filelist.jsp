<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<title>下载文件显示页面</title>
</head>

<body>
	<!-- 遍历Map集合 -->
	<c:forEach var="me" items="${fileNameMap}">
		<c:url
			value="/file/query"
			var="downurl">
			<c:param name="filename" value="${me.key}"></c:param>
		</c:url>
        ${me.value}<a href="${downurl}">下载</a>
		<br />
	</c:forEach>
</body>
</html>