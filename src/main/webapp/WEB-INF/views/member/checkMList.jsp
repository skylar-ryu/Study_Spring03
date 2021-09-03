<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Mybatis Check_MemberList **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h2>** Spring Mybatis Check_MemberList **</h2>
<br>
<c:if test="${not empty message}">
=> ${message}<br>
</c:if>
<hr>
<div id="searchBar">
  <form action="mchecklist" method="get">
  	<b>Level : </b>
  	<input type="checkbox" name="check" value="A">관리자&nbsp;
  	<input type="checkbox" name="check" value="B">나무&nbsp;
  	<input type="checkbox" name="check" value="C">잎새&nbsp;
  	<input type="checkbox" name="check" value="D">새싹&nbsp;
  	<input type="submit" value="검색">
  	<input type="reset" value="취소">
  </form>
</div><br>
<hr>
<table border="1" width=800>
<tr height="30" bgcolor="Azure">
	<th>ID</th><th>Password</th><th>Name</th><th>Level</th>
	<th>BirthDay</th><th>Point</th><th>Weight</th><th>추천인ID</th><th>Image</th>
</tr>
<c:forEach var="list" items="${Banana}">
<tr height="30" align="center">
	<td>${list.id}</td><td>${list.password}</td><td>${list.name}</td><td>${list.lev}</td>
	<td>${list.birthd}</td><td>${list.point}</td><td>${list.weight}</td><td>${list.rid}</td>
	<td><img src="${list.uploadfile}" width=100 height=100></td>
</tr>
</c:forEach>
</table>
<hr>
<a href="home">HOME</a> 
</body>
</html>