<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Oracle MemberPageList **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h2>** Spring Oracle MemberPageList **</h2>
<br>
<c:if test="${not empty message}">
=> ${message}<br>
</c:if>
<hr>
<table border="1" width=800>
<tr height="30" bgcolor="Azure">
	<th>ID</th><th>Password</th><th>Name</th><th>Level</th>
	<th>BirthDay</th><th>Point</th><th>Weight</th><th>추천인ID</th>
</tr>
<c:forEach var="list" items="${Banana}">
<tr height="30" align="center">
	<td>${list.id}</td><td>${list.password}</td><td>${list.name}</td><td>${list.lev}</td>
	<td>${list.birthd}</td><td>${list.point}</td><td>${list.weight}</td><td>${list.rid}</td>
	<td><img src="${list.uploadfile}" width="100" height="100"></td>
</tr>
</c:forEach>
</table>
<hr>
<div align="center">
	<!-- Paging : PageBlock 적용 
		=> 기호 사용  < &lt;   > &gt; -->
	<c:choose>
		<c:when test="${sPageNo>pageNoCount}">
			<a href="mpagelist?currPage=1">FF</a>&nbsp;
			<a href="mpagelist?currPage=${sPageNo-1}">&lt;</a>&nbsp;&nbsp;
		</c:when>
		<c:otherwise>
			<font color="gray">FF&nbsp;&lt;</font>&nbsp;&nbsp;
		</c:otherwise>
	</c:choose>
	
	<c:forEach var="i" begin="${sPageNo}" end="${ePageNo}">
		<c:if test="${i==currPage}">
			<font size="5" color="Orange">${i}</font>&nbsp;
		</c:if>
		<c:if test="${i!=currPage}">
			<a href="mpagelist?currPage=${i}">${i}</a>&nbsp;
		</c:if>
	</c:forEach>
	&nbsp;
	<c:choose>
		<c:when test="${ePageNo<totalPageNo}">
			<a href="mpagelist?currPage=${ePageNo+1}">&gt;</a>&nbsp;
			<a href="mpagelist?currPage=${totalPageNo}">LL</a>
		</c:when>
		<c:otherwise>
			<font color="gray">&gt;&nbsp;LL</font>
		</c:otherwise>
	</c:choose>
</div>
<hr>
<a href="home">HOME</a> 
</body>
</html>