<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage = "error.jsp" %>
<html>
<head><title>Home</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css" >
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/axTest01.js"></script>
<script>
function setClock() {
	var now = new Date();
	var t = "* Now : "
		+now.getFullYear()+"년"+(now.getMonth()+1)+"월"+now.getDate()+"일_"
		+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds() ;
	// => t = "Now: 2021년8월9일_12:2:27" 	
	document.getElementById("clock").innerHTML=t;	
	// 시계처럼 표시하려면 1초 단위로 재호출 해야함
	//setTimeout("setClock()",1000) ; // 실행단위: 1/1000 초  -> 둘중 효율 좋음 (확인필요)
	//setInterval("setClock()",1000) ; // 실행단위: 1/1000 초
}
</script>
</head>
<body onload="setClock()">
<h1>Hello Spring Oracle !!!</h1>
<span>* Start : ${serverTime}</span><br>
<span id="clock"></span><br>
<c:if test="${loginID!=null}">
 => ${loginName} 님 안녕하세요 ~~<br>
</c:if>
<c:if test="${message!=null}">
 => ${message}<br> 
</c:if>
<hr>
<img src="resources/image/jerry01.gif" width="600" height="400">
<div id="resultArea"></div>
<hr>
<hr>
<c:if test="${loginID==null}">
	<a href="loginf">로그인</a>&nbsp;&nbsp;
	<a href="joinf">회원가입</a>&nbsp;&nbsp;
	
</c:if>
<c:if test="${loginID!=null}">
	<a href="mdetail">MyInfo</a>&nbsp;&nbsp;
	<a href="logout">로그아웃</a>&nbsp;&nbsp;
	<a href="mdelete">회원탈퇴</a>&nbsp;&nbsp;
</c:if>
<hr>
<a href="mlist">MList</a>&nbsp;
<a href="mpagelist">MPageL</a>&nbsp;
<a href="mcplist">MCPageL</a>&nbsp;<br>

<a href="blist">BList</a>&nbsp;
<a href="bpagelist">BPageL</a>&nbsp;
<a href="bcplist">BCriPageL</a><br>

<a href="logj">Log4J</a>&nbsp;&nbsp;
<a href="member/list">Member2</a>&nbsp;&nbsp;
<a href="member/memberList3">view생략</a>&nbsp;&nbsp;<br>
<a href="bcrypt">BCrypt</a><br>

<!-- Ajax Test -->
<span id="aloginf" class="textLink">axLoginF</span>&nbsp;&nbsp;
<a href="atestf">axTest</a>&nbsp;&nbsp;
<a href="mchecklist">mCheck</a>&nbsp;&nbsp;
<a href="bchecklist">bCheck</a><br>
<a href="echo">WS_Echo</a>&nbsp;&nbsp;
<a href="chat">WS_Chat</a>&nbsp;&nbsp;
<a href="etest">Exception</a>&nbsp;&nbsp;<br>
<a href="greensn">GreenSN</a>&nbsp;&nbsp;
<a href="greenall">GreenAll</a>&nbsp;&nbsp;
<a href="jeju">Jeju</a>&nbsp;&nbsp;
<a href="calendarMain">FullCalendar</a>&nbsp;&nbsp;
<!-- Security
<a href="ssLoginf">ssLogin</a>&nbsp;&nbsp;
<a href="ssLogoutf">ssLogout</a><br> -->
</body>
</html>
