<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Oracle MemberList **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h2>** Spring Oracle MemberList_요청경로 Test **</h2>
<br>
<c:if test="${not empty message}">
=> ${message}<br>
</c:if>
<hr>
<!-- <img src="resources/image/bar.gif"><br> -->
<img src="/green/resources/image/bar.gif"><br>
<!-- member/list 사용시에는 절대 경로 -->

<table border="1" width=800>
<tr height="30" bgcolor="GreenYellow">
	<th>ID</th><th>Password</th><th>Name</th><th>Level</th>
	<th>BirthDay</th><th>Point</th><th>Weight</th><th>추천인ID</th>
</tr>
<c:forEach var="list" items="${Banana}">
<tr height="30" align="center">
	<td>${list.id}</td><td>${list.password}</td><td>${list.name}</td><td>${list.lev}</td>
	<td>${list.birthd}</td><td>${list.point}</td><td>${list.weight}</td><td>${list.rid}</td>
</tr>
</c:forEach>
</table>
<hr>
<a href="/green/home">HOME</a> <!-- member/list 사용시 경로 --> 
</body>
</html>