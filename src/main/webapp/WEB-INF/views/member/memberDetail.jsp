<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Oracle MemberDetail **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h2>** Spring Oracle MemberDetail **</h2>
<table>
	<tr height="40">
		<td bgcolor="Lavender">I  D</td><td>${Apple.id}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">Password</td><td>${Apple.password}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">Name</td><td>${Apple.name}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">Level</td><td>${Apple.lev}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">Birthday</td><td>${Apple.birthd}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">Point</td><td>${Apple.point}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">Weight</td><td>${Apple.weight}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">추천인ID</td><td>${Apple.rid}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">Image</td>
		<td><img src="${Apple.uploadfile}" width="100" height="100"></td>
	</tr>
</table>
<hr>
<a href="mdetail?id=${Apple.id}&jcode=U">내정보수정</a>&nbsp;&nbsp;
<!-- ** 내정보수정
		=> 내정보를 표시하는 폼이 출력 (mdetail) -> 수정후, 수정버튼 -> 수정 서블릿 
-->
<a href="mdelete?id=${Apple.id}">회원탈퇴</a>
	<!-- 관리자가 삭제하는 경우 id 가 필요함 -->
<hr>
<a href="home">HOME</a>
</body>
</html>