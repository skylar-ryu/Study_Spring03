<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>*** Spring Mybatis LoginForm **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/inCheck.js"></script>
<script src="resources/myLib/axTest01.js"></script>
<script>
// 1) 전역변수 정의
// => 스위치 변수들
var iCheck=false;
var pCheck=false;

// 2) 개별컬럼의 무결성 점검
// => JQuery , focusout 이벤트

// $(document).ready(function() { .... })
$(function(){
	$('#id').focus();
	
	$('#id').focusout(function(){
		iCheck=idCheck();
	}); // id_focusout
	
	//password : 길이4이상, 영문자,숫자와 특수문자는 반드시 1개 이상 포함할것
	$('#password').focusout(function() {
		pCheck=pwCheck();
	}); //password_focusout
}); //ready

//3) submit 처리
//=> Error 있으면: return false
//         없으면: return true -> submit 진행
//=> JS function inCheck() ....
function inCheck() {
	// 개별적 오류 확인 : switch 변수
	if (iCheck==false) {
		$('#iMessage').html(' ID 를 입력하세요 ~~');
	}
	if (pCheck==false) {
		$('#pMessage').html(' Password 를 입력하세요 ~~');
	}
	if (iCheck && pCheck) return true; 
	else return false;
} //inCheck

</script>

</head>
<body>
<h2>*** Spring Mybatis LoginForm **</h2>
<br>
<table><form action="login" method="get">
	<tr><td bgcolor="PaleTurquoise ">I D</td>
		<td><input type="text" name="id" id="id"><br>
			<span id="iMessage" class="eMessage"></span></td>
	</tr>
	<tr><td bgcolor="PaleTurquoise ">Password</td>
		<td><input type="password" name="password" id="password" value="12345!"><br>
			<span id="pMessage" class="eMessage"></span></td>
	</tr>
	<tr height="50"><td></td>
		<td><input type="submit" value="Login" onclick="return inCheck()">&nbsp;
			<input type="reset" value="Reset">&nbsp;
			<span id="alogin" class="textLink">axLogin</span>&nbsp;
			<span id="jslogin" class="textLink">jsLogin</span>
		</td>
	</tr>
</form></table>
<c:if test="${message != null}">
	<br>${message}<br><br>	
</c:if>
<hr>
<a href="home">HOME</a>
</body>
</html>